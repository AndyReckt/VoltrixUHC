package me.shir.uhcp.game;

import me.shir.uhcp.player.*;
import java.util.*;
import org.bukkit.entity.*;
import me.shir.uhcp.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;

public class LobbyScoreboard
{
    private final PlayerManager playerManager;
    private final GameManager gameManager;
    private Map<Player, Scoreboard> plsScoreboard;
    
    public LobbyScoreboard() {
        this.playerManager = PlayerManager.getPlayerManager();
        this.gameManager = GameManager.getGameManager();
        this.plsScoreboard = VenixUHC.getInstance().getLobbyScoreboardMap();
    }
    
    public void newScoreboard(final Player player) {
        final ChatColor color = this.gameManager.getMainColor();
        if (player != null) {
            final ScoreboardManager manager = (ScoreboardManager)Bukkit.getScoreboardManager();
            final Scoreboard scoreboard = ((org.bukkit.scoreboard.ScoreboardManager)manager).getNewScoreboard();
            final Objective obj = scoreboard.registerNewObjective("lobby", "dummy");
            obj.setDisplayName(this.gameManager.getScoreboardTitle());
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            final Team utcTime = scoreboard.registerNewTeam(ChatColor.WHITE.toString());
            utcTime.addEntry(color + "UTC Tim");
            utcTime.setSuffix(color + "e: " + ChatColor.WHITE + "Loading");
            obj.getScore(color + "UTC Tim").setScore(6);
            final Team online = scoreboard.registerNewTeam(ChatColor.BLUE.toString());
            online.addEntry(color + "Players Onli");
            online.setSuffix("ne: \u00c2§f" + this.playerManager.online());
            obj.getScore(color + "Players Onli").setScore(5);
            final Team newLine8 = scoreboard.registerNewTeam(ChatColor.LIGHT_PURPLE.toString());
            newLine8.addEntry("\u00c2§a");
            obj.getScore("\u00c2§a").setScore(4);
            final Team timer = scoreboard.registerNewTeam(ChatColor.AQUA.toString());
            timer.addEntry(color + "Host: ");
            obj.getScore(color + "Host: ").setScore(3);
            final String title = this.gameManager.getScoreboardIP();
            final int i = title.length() / 2;
            final String[] part = { title.substring(0, i), title.substring(i) };
            final Team footer = scoreboard.registerNewTeam(ChatColor.DARK_RED.toString());
            footer.addEntry(part[0]);
            footer.setSuffix(part[1]);
            obj.getScore(part[0]).setScore(1);
            this.plsScoreboard.put(player, scoreboard);
            player.setScoreboard((Scoreboard)this.plsScoreboard.get(player));
        }
    }
    
    public void updateScoreboard(final Player player) {
        if (player != null) {
            if (!this.plsScoreboard.containsKey(player)) {
                this.newScoreboard(player);
            }
            else {
                final ChatColor color = this.gameManager.getMainColor();
                final Team online = this.plsScoreboard.get(player).getTeam(ChatColor.BLUE.toString());
                online.setSuffix(color + "ne: \u00c2§f" + this.playerManager.online());
                final Team host = this.plsScoreboard.get(player).getTeam(ChatColor.AQUA.toString());
                host.setSuffix(ChatColor.WHITE + this.gameManager.getHostName());
                final Team utcTime = this.plsScoreboard.get(player).getTeam(ChatColor.WHITE.toString());
                utcTime.setSuffix(color + "e: " + ChatColor.WHITE + VenixUHC.getInstance().getName());
            }
        }
    }
}
