package me.shir.uhcp.teams.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.teams.*;

public class TeamListCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("teamlist")) {
            if (!TeamManager.getInstance().isTeamsEnabled()) {
                sender.sendMessage("\u00c2§cTeams are currently disabled!");
                return true;
            }
            if (args.length <= 0) {
                final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)sender);
                if (team == null) {
                    sender.sendMessage("\u00c2§cYou are not part of any team!");
                    return true;
                }
                sender.sendMessage(team.toString());
                return true;
            }
            else if (args.length >= 1) {
                final OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
                if (target == null) {
                    sender.sendMessage("\u00c2§cCould not find player!");
                    return true;
                }
                if (PlayerManager.getPlayerManager().getUHCPlayer(target.getUniqueId()) == null) {
                    sender.sendMessage("\u00c2§cThis player hasn't played this UHC!");
                    return true;
                }
                final Team team2 = TeamManager.getInstance().getTeam(target);
                if (team2 == null) {
                    sender.sendMessage("\u00c2§cThis player is not part of any team!");
                    return true;
                }
                sender.sendMessage(team2.toString());
                return true;
            }
        }
        return false;
    }
}
