package me.shir.uhcp.cmds;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import java.util.*;

public class HelpOpCMD implements CommandExecutor
{
    private final Set<CommandSender> cooldown;
    private final GameManager gameManager;
    
    public HelpOpCMD() {
        this.cooldown = new HashSet<CommandSender>();
        this.gameManager = GameManager.getGameManager();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("helpop")) {
            if (this.cooldown.contains(sender)) {
                sender.sendMessage("\u00c2§cDo not spam /helpop.");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("\u00c2§c/helpop <message>");
                return true;
            }
            final StringBuilder hoMsg = new StringBuilder();
            for (final String s : args) {
                hoMsg.append(s).append(" ");
            }
            for (final Player mods : this.gameManager.getModerators()) {
                if (mods != null) {
                    mods.sendMessage(String.valueOf(this.gameManager.getHelpopPrefix()) + this.gameManager.getSecondaryColor() + sender.getName() + ": " + this.gameManager.getMainColor() + (Object)hoMsg);
                }
            }
            sender.sendMessage(this.gameManager.getMainColor() + "HelpOp message sent!");
            this.cooldown.add(sender);
            Bukkit.getServer().getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    HelpOpCMD.this.cooldown.remove(sender);
                }
            }, (long)(20 * this.gameManager.getHelpopCoolDown()));
        }
        return false;
    }
}