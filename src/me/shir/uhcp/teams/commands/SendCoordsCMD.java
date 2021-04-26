package me.shir.uhcp.teams.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.teams.*;

public class SendCoordsCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("sendcoords")) {
            if (!TeamManager.getInstance().isTeamsEnabled()) {
                sender.sendMessage("\u00c2§cTeams are currently disabled!");
                return true;
            }
            if (sender instanceof Player) {
                final OfflinePlayer player = (OfflinePlayer)sender;
                final Team team = TeamManager.getInstance().getTeam(player);
                if (team != null) {
                    final String location = "\u00c2§b x: \u00c2§e" + ((Player)sender).getLocation().getBlockX() + "\u00c2§b y: \u00c2§e" + ((Player)sender).getLocation().getBlockY() + "\u00c2§b z: \u00c2§e" + ((Player)sender).getLocation().getBlockZ();
                    team.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + ChatColor.GREEN + player.getName() + ":" + location);
                }
                else {
                    sender.sendMessage("\u00c2§cYou are not in a team!");
                }
            }
        }
        return false;
    }
}