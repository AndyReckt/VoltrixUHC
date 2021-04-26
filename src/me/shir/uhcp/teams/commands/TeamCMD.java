package me.shir.uhcp.teams.commands;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.teams.*;
import me.shir.uhcp.teams.request.*;

public class TeamCMD implements CommandExecutor
{
    private final GameManager gameManager;
    
    public TeamCMD() {
        this.gameManager = GameManager.getGameManager();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (args.length < 1) {
                sender.sendMessage(this.gameManager.getSecondaryColor() + "-------------------------------");
                sender.sendMessage(new StringBuilder().append(this.gameManager.getMainColor()).append(ChatColor.UNDERLINE).append("Team commands: ").toString());
                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.GRAY + "1)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team create" + ChatColor.RED + " to create a team.");
                sender.sendMessage(ChatColor.GRAY + "2)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team solo" + ChatColor.RED + " to be alone in your team.");
                sender.sendMessage(ChatColor.GRAY + "3)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team invite <player>" + ChatColor.RED + " to invite players to your team.");
                sender.sendMessage(ChatColor.GRAY + "4)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team accept" + ChatColor.RED + " to accept a team invitation.");
                sender.sendMessage(ChatColor.GRAY + "5)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team deny" + ChatColor.RED + " to deny a team invitation.");
                sender.sendMessage(ChatColor.GRAY + "6)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team leave" + ChatColor.RED + " to leave your current team.");
                sender.sendMessage(ChatColor.GRAY + "7)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team kick <player>" + ChatColor.RED + " to kick a player from your team.");
                sender.sendMessage(ChatColor.GRAY + "8)" + ChatColor.RED + " Use - " + ChatColor.RED + "/team list <player>" + ChatColor.RED + " to list someone's team.");
                sender.sendMessage(this.gameManager.getSecondaryColor() + "-------------------------------");
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                Player target = (Player)sender;
                if (args.length == 2) {
                    target = Bukkit.getPlayer(args[1]);
                }
                if (target == null) {
                    sender.sendMessage("\u00c2§cCouldn't find player.");
                    return true;
                }
                final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)target);
                if (team != null) {
                    sender.sendMessage(team.toString());
                    return true;
                }
                if (target == sender) {
                    sender.sendMessage(ChatColor.RED + "You are not in a team!");
                    sender.sendMessage(this.gameManager.getMainColor() + "Use - " + this.gameManager.getSecondaryColor() + "/team create" + this.gameManager.getMainColor() + " to create a team");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + target.getName() + " is not in a team!");
                return true;
            }
            else {
                if (this.gameManager.isScattering() || this.gameManager.isGameRunning()) {
                    sender.sendMessage("\u00c2§cYou cannot edit teams now!");
                    return true;
                }
                final Player playerS = (Player)sender;
                if (sender.hasPermission("uhc.teams.edit") || this.gameManager.getHostName().equalsIgnoreCase(playerS.getName())) {
                    if (args[0].equalsIgnoreCase("reset")) {
                        TeamManager.getInstance().clearTeams();
                        sender.sendMessage("\u00c2§aSuccessfully restarted all teams!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("friendlyfire") || args[0].equalsIgnoreCase("damageteam") || args[0].equalsIgnoreCase("damageteammembers")) {
                        if (args.length == 1) {
                            sender.sendMessage(ChatColor.RED + "/team damageteam [true/false]");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("enable")) {
                            TeamManager.getInstance().setCanDamageTeamMembers(true);
                            Bukkit.getServer().broadcastMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + this.gameManager.getMainColor() + sender.getName() + " has set " + this.gameManager.getSecondaryColor() + "Damage Team Members" + this.gameManager.getMainColor() + " to " + ChatColor.GREEN + "True");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("disable")) {
                            TeamManager.getInstance().setCanDamageTeamMembers(false);
                            Bukkit.getServer().broadcastMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + this.gameManager.getMainColor() + sender.getName() + " has set " + this.gameManager.getSecondaryColor() + "Damage Team Members" + this.gameManager.getMainColor() + " to " + ChatColor.RED + "False");
                            return true;
                        }
                    }
                    if (args[0].equalsIgnoreCase("size")) {
                        if (args.length == 1) {
                            sender.sendMessage(ChatColor.RED + "/team size <size>");
                            return true;
                        }
                        try {
                            final int x = Integer.parseInt(args[1]);
                            TeamManager.getInstance().setMaxSize(x);
                            Bukkit.getServer().broadcastMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + this.gameManager.getMainColor() + sender.getName() + " has set the " + this.gameManager.getSecondaryColor() + "Team Size" + this.gameManager.getMainColor() + " to " + ChatColor.GREEN + x);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "The team size must be a number!");
                        }
                        return true;
                    }
                    else {
                        if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("enable")) {
                            TeamManager.getInstance().setTeamsEnabled(true);
                            Bukkit.getServer().broadcastMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + this.gameManager.getMainColor() + sender.getName() + " has set " + this.gameManager.getSecondaryColor() + "Teams" + this.gameManager.getMainColor() + " to " + ChatColor.GREEN + "True");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("disable")) {
                            TeamManager.getInstance().setTeamsEnabled(false);
                            Bukkit.getServer().broadcastMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + this.gameManager.getMainColor() + sender.getName() + " has set " + this.gameManager.getSecondaryColor() + "Teams" + this.gameManager.getMainColor() + " to " + ChatColor.RED + "False");
                            return true;
                        }
                    }
                }
                if (!TeamManager.getInstance().isTeamsEnabled()) {
                    sender.sendMessage(ChatColor.RED + "Teams are currently disabled!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("solo")) {
                    if (TeamManager.getInstance().getTeam((OfflinePlayer)playerS) != null) {
                        sender.sendMessage("\u00c2§cYou are already in a team, use /team leave to leave your current team.");
                        return true;
                    }
                    TeamManager.getInstance().createTeam(playerS);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("accept")) {
                    final Request request = RequestManager.getInstance().getRequest(playerS);
                    if (request == null) {
                        sender.sendMessage(ChatColor.RED + "You don't have any pending team invites!");
                        return true;
                    }
                    TeamManager.getInstance().registerTeam(playerS, request.getTeam());
                    RequestManager.getInstance().requestMap.remove(playerS);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("deny")) {
                    final Request request = RequestManager.getInstance().getRequest(playerS);
                    if (request == null) {
                        playerS.sendMessage(ChatColor.RED + "You don't have any pending invites.");
                        return true;
                    }
                    RequestManager.getInstance().declined(playerS);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length == 1) {
                        playerS.sendMessage(ChatColor.RED + "/team invite <player>");
                        return true;
                    }
                    final Player target2 = Bukkit.getServer().getPlayer(args[1]);
                    if (target2 == null) {
                        playerS.sendMessage(ChatColor.RED + "Could not find player!");
                        return true;
                    }
                    if (target2 == playerS) {
                        playerS.sendMessage(ChatColor.RED + "You cannot invite yourself to the team!");
                        return true;
                    }
                    Team team2 = TeamManager.getInstance().getTeam((OfflinePlayer)playerS);
                    if (team2 == null) {
                        TeamManager.getInstance().createTeam(playerS);
                        team2 = TeamManager.getInstance().getTeam((OfflinePlayer)playerS);
                        RequestManager.getInstance().sendRequest(playerS, target2, team2);
                        return true;
                    }
                    if (team2.getOwner() != playerS) {
                        sender.sendMessage(ChatColor.RED + "You must be the team leader to invite players to the team!");
                        return true;
                    }
                    RequestManager.getInstance().sendRequest(playerS, target2, team2);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("kick")) {
                    if (args.length == 1) {
                        playerS.sendMessage(ChatColor.RED + "/team kick <player>");
                        return true;
                    }
                    final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)playerS);
                    if (team == null) {
                        playerS.sendMessage(ChatColor.RED + "You are not in a team!");
                        return true;
                    }
                    if (team.getOwner() != playerS) {
                        sender.sendMessage(ChatColor.RED + "You must be the team leader to kick players out of the team!");
                        return true;
                    }
                    final OfflinePlayer target3 = Bukkit.getServer().getOfflinePlayer(args[1]);
                    if (target3 == null) {
                        sender.sendMessage(ChatColor.RED + "Could not find player!");
                        return true;
                    }
                    if (target3 == playerS) {
                        sender.sendMessage(ChatColor.RED + "You cannot kick yourself out of the team, use /team leave to leave the team!");
                        return true;
                    }
                    if (!team.getPlayers().contains(target3.getUniqueId())) {
                        sender.sendMessage(ChatColor.RED + "This player is not part of your team!");
                        return true;
                    }
                    TeamManager.getInstance().unregisterTeam(target3.getUniqueId());
                    if (target3.isOnline()) {
                        target3.getPlayer().sendMessage(ChatColor.RED + "You were kicked from the team by " + sender.getName());
                    }
                    team.sendMessage(ChatColor.RED + target3.getName() + " has been kicked from the team.");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("leave")) {
                    final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)playerS);
                    if (team == null) {
                        sender.sendMessage(ChatColor.RED + "You are not part of any team!");
                        return true;
                    }
                    TeamManager.getInstance().unregisterTeam(playerS.getUniqueId());
                    return true;
                }
            }
        }
        return false;
    }
}