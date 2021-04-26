package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;

public class SpectatorChatCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("spectatorchat")) {
            if (!GameManager.getGameManager().getModerators().contains(((Player)sender).getPlayer())) {
                sender.sendMessage("§cOnly moderators can use this command!");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("§c/spectatorchat <message>");
                return true;
            }
            PlayerManager.getPlayerManager().getUHCPlayer(((Player)sender).getUniqueId()).setUsedCommand(true);
            final StringBuilder msg = new StringBuilder();
            for (final String string : args) {
                msg.append(string).append(" ");
            }
            ((Player)sender).chat(msg.toString());
        }
        return false;
    }
}
