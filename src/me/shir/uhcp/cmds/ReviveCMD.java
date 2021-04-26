package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import me.shir.uhcp.game.*;
import org.bukkit.plugin.*;

public class ReviveCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("scatter")) {
            return false;
        }
        if (!sender.hasPermission("uhc.host") && !GameManager.getGameManager().getModerators().contains(sender)) {
            sender.sendMessage("§cNo Permission!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/revive <player>");
            return true;
        }
        final Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cCould not find player!");
            return true;
        }
        if (!GameManager.getGameManager().isGameRunning()) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cA UHC is not currently running!");
            return true;
        }
        Bukkit.getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                TasksManager.getInstance().scatterPlayer(player);
            }
        }, 60L);
        sender.sendMessage(GameManager.getGameManager().getMainColor() + "Successfully revived: " + GameManager.getGameManager().getSecondaryColor() + player.getName());
        return true;
    }
}
