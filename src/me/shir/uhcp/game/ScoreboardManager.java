package me.shir.uhcp.game;

import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;
import me.shir.uhcp.teams.*;
import org.bukkit.scoreboard.*;
import java.text.*;
import me.shir.uhcp.scoreboard.*;
import com.wimbli.WorldBorder.*;
import me.shir.uhcp.util.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.scenarios.*;
import me.shir.uhcp.listeners.*;
import me.shir.uhcp.cmds.*;
import java.util.*;

public class ScoreboardManager extends BukkitRunnable
{
    private ScoreboardHandler scoreboardHandler;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private String lines;
    private Map<UUID, Long> noclean;
    private HashMap<String, Integer> cooldown;
    private Player player2;
    private UHCPlayer winner;
    private Team team2;
    private OfflinePlayer team3;
    private static int time;
    private String borderInfo;
    
    static {
        ScoreboardManager.time = 15;
    }
    
    public ScoreboardManager() {
        this.scoreboardHandler = new ScoreboardHandler();
        this.playerManager = PlayerManager.getPlayerManager();
        this.gameManager = GameManager.getGameManager();
        this.lines = "&7&m---------------------";
        this.noclean = new HashMap<UUID, Long>();
        this.cooldown = new HashMap<String, Integer>();
    }
    
    public void run() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player player = onlinePlayers[i];
            final ScoreboardHelper board = this.scoreboardHandler.getScoreboard(player);
            final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)player);
            board.clear();
            board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§a§lVoltrix §f§lNetwork");
            if (this.gameManager.getModerators().contains(player)) {
                final double tps = Bukkit.spigot().getTPS()[0];
                final DecimalFormat decimalFormat = new DecimalFormat("0.0");
                board.add("§f§lTPS §7:|:§a " + decimalFormat.format(tps));
            }
            if (this.gameManager.isMapGenerating()) {
                if (ScoreManager.isplover(player)) {
                    board.add(this.lines);
                    board.add("&f Generating...");
                    board.add(this.lines);
                    final DecimalFormat df = new DecimalFormat("0.0");
                    final double tps2 = Bukkit.spigot().getTPS()[0];
                    board.add("&f Wait please.");
                    board.add(this.lines);
                    board.add("&f TPS: §a" + df.format(tps2) + " TPS");
                    board.add(this.lines);
                }
                else if (ScoreManager.isbadlion(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§bVoltrix UHC");
                    board.add("&fGenerating...");
                    board.add("");
                    final DecimalFormat df = new DecimalFormat("0.0");
                    final double tps2 = Bukkit.spigot().getTPS()[0];
                    board.add("&a Wait please.");
                    board.add("");
                    board.add("&aTPS: §f" + df.format(tps2) + " TPS");
                    board.add("");
                    board.add("&b@VoltrixPvP");
                }
                else if (ScoreManager.isultra(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§6§lVoltrix §f§lNetwork");
                    board.add(this.lines);
                    board.add("&f Generating...");
                    board.add(this.lines);
                    final DecimalFormat df = new DecimalFormat("0.0");
                    final double tps2 = Bukkit.spigot().getTPS()[0];
                    board.add("&f Wait please.");
                    board.add(this.lines);
                    board.add("&f TPS: §6" + df.format(tps2) + " TPS");
                    board.add(this.lines);
                }
            }
            else if (!this.gameManager.isScattering() && !this.gameManager.isGameRunning()) {
                if (ScoreManager.isplover(player)) {
                    board.add(this.lines);
                    board.add("&f§ &fType: §6" + this.gameManager.gameType());
                    if (ConfirmStart.timelobby2) {
                        board.add("§aStarting in: §f" + TimeUtils.setFormat(ConfirmStart.timelobby));
                    }
                    board.add("");
                    board.add("&f§ &aPlayers: §f" + this.playerManager.online() + "/" + ConfigIntegers.MAX_PLAYERS.get());
                    board.add("");
                    board.add("&6§ &aScenarios: ");
                    for (final String sce : ScenarioManager.getInstance().getActiveScenarios()) {
                        board.add("§7- &f" + sce);
                    }
                    board.add(this.lines);
                }
                else if (ScoreManager.isbadlion(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§bVoltrix UHC");
                    board.add("");
                    board.add("&aType: §f" + this.gameManager.gameType());
                    board.add("");
                    board.add("&a§ &aScenarios: ");
                    for (final String sce : ScenarioManager.getInstance().getActiveScenarios()) {
                        board.add("§7- &f" + sce);
                    }
                    board.add("");
                    board.add("&a§ &aPlayers: §f" + this.playerManager.online());
                    board.add("");
                    board.add("&b@VoltrixPvP");
                }
                else if (ScoreManager.isultra(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§6§lVoltrix §f§lNetwork");
                    board.add(this.lines);
                    board.add("&fType: §6" + this.gameManager.gameType());
                    board.add("&fPlayers: §6" + this.playerManager.online() + "/" + ConfigIntegers.MAX_PLAYERS.get());
                    board.add(this.lines);
                }
            }
            if (this.gameManager.isScattering()) {
                if (ScoreManager.isplover(player)) {
                    board.add(this.lines);
                    board.add("&a Scattering...");
                    board.add(" ");
                    board.add("&a Starting in: §f" + StartGameCMD.starttime + "s");
                    board.add("");
                    board.add("&a Scattered: §f" + this.playerManager.alivePlayers());
                    board.add(" ");
                    board.add("&a Scattering: §f" + this.playerManager.scattering());
                    board.add(this.lines);
                }
                else if (ScoreManager.isbadlion(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§bVoltrix UHC");
                    board.add(" ");
                    board.add("&aPlayers: §f" + this.playerManager.online());
                    board.add(" ");
                    board.add("&b@VoltrixPvP");
                }
                else if (ScoreManager.isultra(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§6§lVoltrix §f§lNetwork");
                    board.add(this.lines);
                    board.add("&aPlayers Left: §f" + this.playerManager.alivePlayers());
                    board.add(this.lines);
                }
            }
            if (this.gameManager.isGameRunning()) {
                if (ScoreManager.isplover(player)) {
                    board.add(this.lines);
                    board.add("&aGame Time: §f" + this.convert(GRunnable.seconds));
                    board.add(" ");
                    board.add("&aYour Kills: §f" + this.playerManager.getUHCPlayer(player.getUniqueId()).getKills());
                    if (TeamManager.getInstance().isTeamsEnabled()) {
                        board.add("&aTeam Kills: §f" + team.getKills());
                    }
                    if (Listeners.innoclean.contains(player.getUniqueId())) {
                        board.add("&aNo Clean: §f" + PlayerDeathListener.nocleantime + "s");
                    }
                    board.add(" ");
                    if (DeathMatchCountdown.timelobby2) {
                        board.add("§aDeathMatch: §f" + TimeUtils.setFormat(DeathMatchCountdown.timelobby));
                        board.remove("&aBorder: §f" + this.gameManager.getCurrentBorder());
                    }
                    board.add("&aPlayers Left: §f" + this.playerManager.alivePlayers());
                    board.add("&aBorder: §f" + this.gameManager.getCurrentBorder());
                    board.add(this.lines);
                }
                else if (ScoreManager.isbadlion(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§bVoltrix UHC");
                    board.add(" ");
                    board.add("&aGame Time: §f" + this.convert(GRunnable.seconds));
                    board.add(" ");
                    board.add("&aYour Kills: §f" + this.playerManager.getUHCPlayer(player.getUniqueId()).getKills());
                    if (TeamManager.getInstance().isTeamsEnabled()) {
                        board.add("&aTeam Kills: §f" + team.getKills());
                    }
                    if (Listeners.innoclean.contains(player.getUniqueId())) {
                        board.add("&aNo Clean: §f" + PlayerDeathListener.nocleantime + "s");
                    }
                    board.add("&aPlayers Left: §f" + this.playerManager.alivePlayers());
                    board.add(" ");
                    board.add("&6Current Border: §f" + this.gameManager.getCurrentBorder());
                    board.add(" ");
                    board.add("&b@VoltrixPvP");
                }
                else if (ScoreManager.isultra(player)) {
                    board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§6§lPlover §7:|:§f§l " + this.gameManager.getHostName());
                    board.add(this.lines);
                    board.add("&fGame Time: §6" + this.convert(GRunnable.seconds));
                    board.add("&fPlayers Left: §6" + this.playerManager.alivePlayers());
                    board.add("&fYour Kills: §6" + this.playerManager.getUHCPlayer(player.getUniqueId()).getKills());
                    if (TeamManager.getInstance().isTeamsEnabled()) {
                        board.add("&fTeam Kills: §6" + team.getKills());
                    }
                    board.add("&fBorder: §6" + this.gameManager.getCurrentBorder());
                    board.add(this.lines);
                }
            }
            board.update(player);
        }
    }
    
    private String convert(final int seconds) {
        final int h = seconds / 3600;
        final int i = seconds - h * 3600;
        final int m = i / 60;
        final int s = i - m * 60;
        String timeF = "";
        if (h > 0) {
            if (h < 10) {
                timeF = String.valueOf(String.valueOf(timeF)) + "0";
            }
            timeF = String.valueOf(String.valueOf(timeF)) + h + ":";
        }
        if (m < 10) {
            timeF = String.valueOf(String.valueOf(timeF)) + "0";
        }
        timeF = String.valueOf(String.valueOf(timeF)) + m + ":";
        if (s < 10) {
            timeF = String.valueOf(String.valueOf(timeF)) + "0";
        }
        timeF = String.valueOf(String.valueOf(timeF)) + s;
        return timeF;
    }
}
