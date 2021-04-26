package me.shir.uhcp.teams.commands;

import org.bukkit.command.*;
import me.shir.uhcp.player.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.teams.*;

public class TeamChatCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("teamchat")) {
            if (!TeamManager.getInstance().isTeamsEnabled()) {
                sender.sendMessage("\u00c2§cTeams are currently disabled!");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
                PlayerManager.getPlayerManager().getUHCPlayer(((Player)sender).getUniqueId()).toggleTeamChat(sender);
                return true;
            }
            if (args.length > 0) {
                if (sender instanceof Player) {
                    final OfflinePlayer player = (OfflinePlayer)sender;
                    final Team team = TeamManager.getInstance().getTeam(player);
                    if (team != null) {
                        final StringBuilder messageBuilder = new StringBuilder();
                        for (int i = 0; i < args.length; ++i) {
                            final String arg = args[i];
                            if (i != 0) {
                                messageBuilder.append(" ");
                            }
                            messageBuilder.append(arg);
                        }
                        team.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + GameManager.getGameManager().getSecondaryColor() + sender.getName() + ": " + GameManager.getGameManager().getMainColor() + messageBuilder.toString());
                    }
                }
            }
            else {
                sender.sendMessage("\u00c2§c/teamchat <message>");
            }
        }
        return false;
    }
}
