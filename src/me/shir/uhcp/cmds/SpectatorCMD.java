package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.player.*;

public class SpectatorCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("spectator")) {
            if (!GameManager.getGameManager().getModerators().contains(sender)) {
                sender.sendMessage("§cYou must be a moderator to use this command!");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/spectator [add/remove] [player]");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                final Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cCould not find player!");
                    return true;
                }
                PlayerManager.getPlayerManager().setSpectating(true, target);
                sender.sendMessage(GameManager.getGameManager().getMainColor() + "Successfully added " + target.getName() + " to the spectators!");
                return true;
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                final Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cCould not find player!");
                    return true;
                }
                PlayerManager.getPlayerManager().setSpectating(false, target);
                sender.sendMessage(GameManager.getGameManager().getMainColor() + "Successfully removed " + target.getName() + " from the spectators!");
                return true;
            }
        }
        return false;
    }
}
