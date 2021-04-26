package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class HealCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("heal")) {
            return false;
        }
        if (!GameManager.getGameManager().getModerators().contains(sender)) {
            sender.sendMessage("§cNo Permission!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§c/heal <player/all>");
            return true;
        }
        if (args[0].equalsIgnoreCase("all")) {
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player player = onlinePlayers[i];
                player.setHealth(20.0);
                player.sendMessage("§aYou have been healed!");
            }
            sender.sendMessage(GameManager.getGameManager().getMainColor() + "Healed all!");
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cCould not find player!");
            return true;
        }
        target.setHealth(20.0);
        target.sendMessage("§aYou have been healed!");
        sender.sendMessage(GameManager.getGameManager().getMainColor() + "Healed: " + target.getName());
        return true;
    }
}
