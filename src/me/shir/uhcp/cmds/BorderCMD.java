package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

public class BorderCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("border")) {
            return false;
        }
        if (!GameManager.getGameManager().getModerators().contains(sender)) {
            sender.sendMessage("§e§lBorder §7:|: §cYou Dont have Permission!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§e§lBorder §7:|: §c/border <size>");
            return true;
        }
        final int x = Integer.parseInt(args[0]);
        if (!this.checkNumber(args[0])) {
            sender.sendMessage("§e§lBorder §7:|: §cThe size must be a number!");
            return true;
        }
        if (x < 5) {
            sender.sendMessage("§e§lBorder §7:|: §cThe size cannot be smaller than 5!");
            return true;
        }
        sender.sendMessage("§e§lBorder §7:|: §aShrinking border to " + x + "x" + x + "...");
        this.startSeconds(x);
        return true;
    }
    
    private boolean checkNumber(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    private void startSeconds(final int size) {
        final GameManager gm = GameManager.getGameManager();
        gm.setCanBorderShrink(false);
        new BukkitRunnable() {
            int i = 11;
            
            public void run() {
                --this.i;
                if (this.i >= 1) {
                    Bukkit.broadcastMessage(String.valueOf(gm.getBorderPrefix()) + gm.getMainColor() + " Border shrinking in " + gm.getSecondaryColor() + this.i + gm.getMainColor() + " seconds!");
                }
                else if (this.i == 0) {
                    TasksManager.getInstance().shrinkBorder(size, this);
                    Bukkit.getServer().broadcastMessage(String.valueOf(gm.getBorderPrefix()) + gm.getMainColor() + " The world border has shrunk to " + gm.getSecondaryColor() + size + gm.getMainColor() + "x" + gm.getSecondaryColor() + size);
                    new ScoreboardM().updateBorder();
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
    }
}
