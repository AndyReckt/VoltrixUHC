package me.shir.uhcp.game;

import me.shir.uhcp.player.*;
import org.bukkit.entity.*;
import me.shir.uhcp.teams.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;
import java.util.*;

public class ScoreboardM
{
    private final PlayerManager playerManager;
    private final GameManager gameManager;
    
    public ScoreboardM() {
        this.playerManager = PlayerManager.getPlayerManager();
        this.gameManager = GameManager.getGameManager();
    }
    
    public void newScoreboard(final Player player) {
        final ChatColor color = this.gameManager.getMainColor();
        final ChatColor color2 = this.gameManager.getSecondaryColor();
        if (player != null) {
            final ScoreboardManager manager = (ScoreboardManager)Bukkit.getScoreboardManager();
            final Scoreboard scoreboard = ((org.bukkit.scoreboard.ScoreboardManager)manager).getNewScoreboard();
            final Objective healthPList = scoreboard.registerNewObjective("h", "health");
            final Objective healthName = scoreboard.registerNewObjective("h1", "health");
            final Objective obj = scoreboard.registerNewObjective("uhc", "dummy");
            obj.setDisplayName(this.gameManager.getScoreboardTitle());
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            healthPList.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            healthName.setDisplayName("§4\u2764");
            healthName.setDisplaySlot(DisplaySlot.BELOW_NAME);
            final Team newLine = scoreboard.registerNewTeam(ChatColor.GRAY.toString());
            newLine.addEntry("§7§m---------");
            newLine.setSuffix("§7§m---------");
            obj.getScore("§7§m---------").setScore(12);
            final Team timer = scoreboard.registerNewTeam(ChatColor.GOLD.toString());
            timer.addEntry(color2 + "Game Time:");
            timer.setSuffix(color + " 00:00:00");
            obj.getScore(color2 + "Game Time:").setScore(11);
            final Team alive = scoreboard.registerNewTeam(ChatColor.AQUA.toString());
            alive.addEntry(color2 + "Kills:");
            alive.setSuffix(color + " " + this.playerManager.getUHCPlayer(player.getUniqueId()).getKills());
            obj.getScore(color2 + "Kills:").setScore(10);
            final Team newLine2 = scoreboard.registerNewTeam(ChatColor.DARK_GRAY.toString());
            newLine2.addEntry("§e");
            obj.getScore("§e").setScore(8);
            final Team kill = scoreboard.registerNewTeam(ChatColor.BLUE.toString());
            kill.addEntry(color2 + "Players:");
            kill.setSuffix(color + " " + this.playerManager.alivePlayers());
            obj.getScore(color2 + "Players:").setScore(7);
            final Team spectators = scoreboard.registerNewTeam(ChatColor.BLACK.toString());
            spectators.addEntry(color2 + "Spectators:");
            spectators.setSuffix(color + " " + this.playerManager.spectators());
            obj.getScore(color2 + "Spectators:").setScore(5);
            final Team newLine3 = scoreboard.registerNewTeam(ChatColor.RED.toString());
            newLine3.addEntry("§f");
            obj.getScore("§f").setScore(4);
            final Team border = scoreboard.registerNewTeam(ChatColor.GREEN.toString());
            border.addEntry(color2 + "Border:");
            border.setSuffix(color + " " + this.gameManager.getCurrentBorder());
            obj.getScore(color2 + "Border:").setScore(3);
            if (TeamManager.getInstance().isTeamsEnabled()) {
                final me.shir.uhcp.teams.Team team = TeamManager.getInstance().getTeam((OfflinePlayer)player);
                final Team tkill = scoreboard.registerNewTeam(ChatColor.DARK_GREEN.toString());
                tkill.addEntry(color2 + "Team Kills:");
                tkill.setSuffix(color + " " + team.getKills());
                obj.getScore(color2 + "Team Kills:").setScore(9);
                final Team tleft = scoreboard.registerNewTeam(ChatColor.YELLOW.toString());
                tleft.addEntry(color2 + "Teams:");
                tleft.setSuffix(color + " " + TeamManager.getInstance().getTeamsAlive());
                obj.getScore(color2 + "Teams:").setScore(6);
            }
            final Team newLine4 = scoreboard.registerNewTeam(ChatColor.DARK_BLUE.toString());
            newLine4.addEntry("§7§m--------");
            newLine4.setSuffix("§7§m----------");
            obj.getScore("§7§m--------").setScore(2);
            final String title = this.gameManager.getScoreboardIP();
            final int i = title.length() / 2;
            final String[] part = { title.substring(0, i), title.substring(i) };
            final Team footer = scoreboard.registerNewTeam(ChatColor.DARK_RED.toString());
            footer.addEntry(part[0]);
            footer.setSuffix(part[1]);
            obj.getScore(part[0]).setScore(1);
            this.gameManager.getScoreboardMap().put(player, scoreboard);
            player.setScoreboard((Scoreboard)this.gameManager.getScoreboardMap().get(player));
        }
    }
    
    void updateScoreboard(final Player player, final int seconds) {
        if (player != null) {
            if (!this.gameManager.getScoreboardMap().containsKey(player)) {
                this.newScoreboard(player);
            }
            else {
                final ChatColor color = this.gameManager.getMainColor();
                final Team t = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.GOLD.toString());
                t.setSuffix(color + " " + this.convert(seconds));
                final Team pl = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.BLUE.toString());
                pl.setSuffix(color + " " + this.playerManager.alivePlayers());
                final Team sp = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.BLACK.toString());
                sp.setSuffix(color + " " + this.playerManager.spectators());
                if (TeamManager.getInstance().isTeamsEnabled()) {
                    final Team teamsLeft = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.YELLOW.toString());
                    teamsLeft.setSuffix(color + " " + TeamManager.getInstance().getTeamsAlive());
                }
            }
        }
    }
    
    public void updateBorder() {
        final ChatColor color = GameManager.getGameManager().getMainColor();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player player = onlinePlayers[i];
        }
    }
    
    public void updateKills(final Player player) {
        final ChatColor color = GameManager.getGameManager().getMainColor();
        if (player != null) {
            if (!this.gameManager.getScoreboardMap().containsKey(player)) {
                this.newScoreboard(player);
            }
            else {
                final Team kill = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.AQUA.toString());
                kill.setSuffix(color + " " + this.playerManager.getUHCPlayer(player.getUniqueId()).getKills());
            }
        }
    }
    
    public void updateTeamKills(final me.shir.uhcp.teams.Team team) {
        final ChatColor color = GameManager.getGameManager().getMainColor();
        for (final UUID players : team.getPlayers()) {
            final Player player = Bukkit.getServer().getPlayer(players);
            if (player != null) {
                if (!this.gameManager.getScoreboardMap().containsKey(player)) {
                    this.newScoreboard(player);
                }
                else {
                    final Team teamKills = this.gameManager.getScoreboardMap().get(player).getTeam(ChatColor.DARK_GREEN.toString());
                    teamKills.setSuffix(color + " " + team.getKills());
                }
            }
        }
    }
    
    private String convert(final int seconds) {
        final int h = seconds / 3600;
        final int i = seconds - h * 3600;
        final int m = i / 60;
        final int s = i - m * 60;
        String timeF = "";
        if (h < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + h + ":";
        if (m < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + m + ":";
        if (s < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + s;
        return timeF;
    }
}
