package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;

public class HostCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("host")) {
            if (!sender.hasPermission("uhc.host")) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (args.length < 1) {
                if (GameManager.getGameManager().getHostName().equalsIgnoreCase(p.getName())) {
                    GameManager.getGameManager().setHost(null);
                    GameManager.getGameManager().setModerator(p, false);
                    p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fYou are no longer " + GameManager.getGameManager().getMainColor() + "hosting §fthis game!");
                    return true;
                }
                GameManager.getGameManager().setHost(p);
                GameManager.getGameManager().setModerator(p, true);
            }
            if (args.length == 1) {
                final Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
                    return true;
                }
                if (GameManager.getGameManager().getHostName().equalsIgnoreCase(target.getName())) {
                    GameManager.getGameManager().setHost(null);
                    GameManager.getGameManager().setModerator(target, false);
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§a" + target.getName() + " §fis no longer " + GameManager.getGameManager().getMainColor() + "hosting §fthis game!");
                    target.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fYou are no longer " + GameManager.getGameManager().getMainColor() + "hosting §fthis game!");
                    return true;
                }
                GameManager.getGameManager().setHost(target);
                GameManager.getGameManager().setModerator(target, true);
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§a" + target.getName() + " §fis now " + GameManager.getGameManager().getMainColor() + "hosting §fthis game!");
                return true;
            }
        }
        return false;
    }
}
