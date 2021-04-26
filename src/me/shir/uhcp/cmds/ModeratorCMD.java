package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;

public class ModeratorCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("moderator")) {
            return false;
        }
        final Player player = (Player)sender;
        if (!sender.hasPermission("uhc.host")) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
            return true;
        }
        if (args.length < 1) {
            if (GameManager.getGameManager().getModerators().contains(player)) {
                GameManager.getGameManager().setModerator(player, false);
                return true;
            }
            GameManager.getGameManager().setModerator(player, true);
            return true;
        }
        else {
            final Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cThis player doesn't exist!");
                return true;
            }
            if (GameManager.getGameManager().getModerators().contains(target)) {
                GameManager.getGameManager().setModerator(target, false);
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§a" + target.getName() + " §fis no longer " + GameManager.getGameManager().getMainColor() + "modding §fthis game!");
                return true;
            }
            GameManager.getGameManager().setModerator(target, true);
            sender.sendMessage("§a" + target.getName() + " §fis now " + GameManager.getGameManager().getMainColor() + "modding §fthis game!");
            return true;
        }
    }
}
