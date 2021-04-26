package me.shir.uhcp.teams;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import java.util.*;

public class TeamManager implements Listener
{
    private static TeamManager instance;
    private boolean teamsEnabled;
    private boolean damageTeamMembers;
    public static final Map<UUID, Team> teams;
    private int maxSize;
    private int currentTeams;
    private final String prefix = "§6§lTeams §8» ";
    public static HashMap<Integer, String> teamcolor;
    
    static {
        TeamManager.instance = new TeamManager();
        teams = new HashMap<UUID, Team>();
        TeamManager.teamcolor = new HashMap<Integer, String>();
    }
    
    public TeamManager() {
        this.teamsEnabled = false;
        this.damageTeamMembers = true;
        this.maxSize = 2;
        this.currentTeams = 0;
    }
    
    public static TeamManager getInstance() {
        return TeamManager.instance;
    }
    
    public boolean canDamageTeamMembers() {
        return this.damageTeamMembers;
    }
    
    public void setCanDamageTeamMembers(final boolean damageTeamMembers) {
        this.damageTeamMembers = damageTeamMembers;
    }
    
    public boolean isTeamsEnabled() {
        return this.teamsEnabled;
    }
    
    public void setTeamsEnabled(final boolean teamsEnabled) {
        this.teamsEnabled = teamsEnabled;
    }
    
    public void clearTeams() {
        TeamManager.teams.clear();
        for (final UUID uuid : TeamManager.teams.keySet()) {
            this.unregisterTeam(uuid);
        }
        this.currentTeams = 0;
    }
    
    public void unregisterTeam(final UUID uuid) {
        TeamManager.teams.get(uuid).removePlayer(Bukkit.getOfflinePlayer(uuid));
        TeamManager.teams.remove(uuid);
    }
    
    public Map<UUID, Team> getTeams() {
        return TeamManager.teams;
    }
    
    public void autoPlace() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player players = onlinePlayers[i];
            final Team team = TeamManager.teams.get(players.getUniqueId());
            if (team == null) {
                this.createTeam(players);
            }
        }
    }
    
    public void createTeam(final Player owner) {
        ++this.currentTeams;
        final Team currentTeam = TeamManager.teams.get(owner.getUniqueId());
        if (currentTeam != null) {
            currentTeam.removePlayer((OfflinePlayer)owner);
        }
        this.registerTeam(owner, new Team(owner, this.currentTeams));
        final String[] color = { "§a", "§b", "§c", "§e", "§6", "§7", "§8", "§9", "§2", "§8", "§d", "§2", "§3", "§4", "§5" };
        final int randomcolor = new Random().nextInt(15);
        getInstance().getTeam((OfflinePlayer)owner).setTeamColor(owner, color[randomcolor]);
    }
    
    public void registerTeam(final Player player, final Team team) {
        if (team.getSize() == this.getMaxSize()) {
            player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cTeam size cannot be bigger than " + this.maxSize);
            return;
        }
        team.addPlayer(player);
        TeamManager.teams.put(player.getUniqueId(), team);
    }
    
    public void disbandTeam(final Team team) {
        for (final UUID uuid : team.getPlayers()) {
            this.unregisterTeam(uuid);
        }
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }
    
    public Set<Team> getTeamSet() {
        final Set<Team> teams = new HashSet<Team>();
        teams.addAll(TeamManager.teams.values());
        return teams;
    }
    
    public int getTeamsAlive() {
        int i = 0;
        for (final Team team : this.getTeamSet()) {
            if (team.isAlive()) {
                ++i;
            }
        }
        return i;
    }
    
    public String getTeamsPrefix() {
        return "§6§lTeams §8» ";
    }
    
    public Team getLastTeam() {
        if (this.getTeamsAlive() == 1) {
            for (final Team teams : this.getTeamSet()) {
                if (teams.isAlive()) {
                    return teams;
                }
            }
        }
        return null;
    }
    
    public Team getTeam(final OfflinePlayer player) {
        return getInstance().getTeams().get(player.getUniqueId());
    }
}
