package me.shir.uhcp.game;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.util.*;
import org.bukkit.scoreboard.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class ScoreboardHandler implements Listener
{
    private Map<Player, ScoreboardHelper> boardHelper;
    private WeakHashMap<Player, ScoreboardHelper> helper;
    
    public ScoreboardHandler() {
        this.boardHelper = new HashMap<Player, ScoreboardHelper>();
        this.helper = new WeakHashMap<Player, ScoreboardHelper>();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)VenixUHC.getInstance());
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player player = onlinePlayers[i];
            this.handleScoreboard(player);
        }
    }
    
    public ScoreboardHelper getScoreboardFor(final Player player) {
        return this.helper.get(player);
    }
    
    public void registerScoreboards(final Player player) {
        final Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(sb);
        this.resendTab(player);
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player other = onlinePlayers[i];
            if (other != player && this.getScoreboardFor(other) != null) {
                final Scoreboard scoreboard = this.getScoreboardFor(other).getScoreBoard();
                final Team otherTeam = this.getTeam(scoreboard, "other", ("§c"));
                otherTeam.addEntry(player.getName());
            }
        }
    }
    
    public void resendTab(final Player player) {
        if (this.getScoreboardFor(player) == null) {
            return;
        }
        final Scoreboard scoreboard = this.getScoreboardFor(player).getScoreBoard();
        this.unregister(scoreboard, "player");
        this.unregister(scoreboard, "other");
        final Team playerTeam = this.getTeam(scoreboard, "player", ("§2"));
        final Team otherTeam = this.getTeam(scoreboard, "other", ("§c"));
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player other = onlinePlayers[i];
            if (other == player) {
                playerTeam.addEntry(other.getName());
            }
            else {
                otherTeam.addEntry(other.getName());
            }
        }
    }
    
    public Team getTeam(final Scoreboard board, final String name, final String prefix) {
        Team team = board.getTeam(name);
        if (team == null) {
            team = board.registerNewTeam(name);
        }
        team.setPrefix(prefix);
        return team;
    }
    
    public void unregister(final Scoreboard board, final String name) {
        final Team team = board.getTeam(name);
        if (team != null) {
            team.unregister();
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.handleScoreboard(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        this.boardHelper.remove(event.getPlayer());
        this.helper.remove(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.boardHelper.remove(event.getPlayer());
        this.helper.remove(event.getPlayer());
    }
    
    private void handleScoreboard(final Player player) {
        final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
        final ScoreboardHelper helper = new ScoreboardHelper(board, "§c§lPloverUHC");
        this.boardHelper.put(player, helper);
    }
    
    public ScoreboardHelper getScoreboard(final Player player) {
        return this.boardHelper.get(player);
    }
}
