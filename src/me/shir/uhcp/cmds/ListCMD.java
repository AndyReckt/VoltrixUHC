package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;

public class ListCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            final ChatColor color1 = GameManager.getGameManager().getMainColor();
            final ChatColor color2 = GameManager.getGameManager().getSecondaryColor();
            sender.sendMessage(color2 + "§7§m----------------");
            sender.sendMessage("             §6§lLIST");
            sender.sendMessage(" ");
            sender.sendMessage("§4Host: §f" + GameManager.getGameManager().getHostName());
            sender.sendMessage("§5Total Online: §f" + PlayerManager.getPlayerManager().online());
            sender.sendMessage("§aPlayers Alive: §f" + PlayerManager.getPlayerManager().alivePlayers());
            sender.sendMessage("§bSpectators: §f" + PlayerManager.getPlayerManager().spectators());
            sender.sendMessage("§3Moderators: §f" + GameManager.getGameManager().getModeratorsNames().toString());
            sender.sendMessage(" ");
            sender.sendMessage(color2 + "§7§m----------------");
        }
        return false;
    }
}
