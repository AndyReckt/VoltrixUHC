package me.shir.uhcp.game;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.event.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.listeners.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.scenarios.*;
import me.shir.uhcp.teams.*;
import java.util.*;
import org.bukkit.entity.*;

public class GRunnable extends BukkitRunnable
{
    public static int seconds;
    public static boolean broadcasted;
    private final TasksManager tasksManager;
    private final ScoreboardM sbM;
    private final GameManager gameManager;
    
    static {
        GRunnable.seconds = 0;
        GRunnable.broadcasted = false;
    }
    
    public GRunnable() {
        this.tasksManager = TasksManager.getInstance();
        this.sbM = new ScoreboardM();
        this.gameManager = GameManager.getGameManager();
    }
    
    public void run() {
        ++GRunnable.seconds;
        if (GRunnable.seconds % 1 == 0) {
            if (TeamManager.getInstance().isTeamsEnabled() && !GRunnable.broadcasted && TeamManager.getInstance().getTeamsAlive() == 1) {
                final Team team = TeamManager.getInstance().getLastTeam();
                Bukkit.getPluginManager().callEvent((Event)new GameWinTeamListener(team));
                GRunnable.broadcasted = true;
            }
            if (!GRunnable.broadcasted && PlayerManager.getPlayerManager().alivePlayers() == 1) {
                for (final UHCPlayer uhcPlayer : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
                    if (uhcPlayer.isPlayerAlive() && !GRunnable.broadcasted) {
                        Bukkit.getServer().getPluginManager().callEvent((Event)new GameWinListener(uhcPlayer.getName(), uhcPlayer));
                        GRunnable.broadcasted = true;
                    }
                }
            }
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player players = onlinePlayers[i];
                this.tasksManager.checkBorder(players);
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 300) {
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player p = onlinePlayers2[j];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §605:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 180) {
                Player[] onlinePlayers3;
                for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                    final Player p = onlinePlayers3[k];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §603:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 60) {
                Player[] onlinePlayers4;
                for (int length4 = (onlinePlayers4 = Bukkit.getServer().getOnlinePlayers()).length, l = 0; l < length4; ++l) {
                    final Player p = onlinePlayers4[l];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §601:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 30) {
                Player[] onlinePlayers5;
                for (int length5 = (onlinePlayers5 = Bukkit.getServer().getOnlinePlayers()).length, n = 0; n < length5; ++n) {
                    final Player p = onlinePlayers5[n];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:30 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 10) {
                Player[] onlinePlayers6;
                for (int length6 = (onlinePlayers6 = Bukkit.getServer().getOnlinePlayers()).length, n2 = 0; n2 < length6; ++n2) {
                    final Player p = onlinePlayers6[n2];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:10 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 5) {
                Player[] onlinePlayers7;
                for (int length7 = (onlinePlayers7 = Bukkit.getServer().getOnlinePlayers()).length, n3 = 0; n3 < length7; ++n3) {
                    final Player p = onlinePlayers7[n3];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:05 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 4) {
                Player[] onlinePlayers8;
                for (int length8 = (onlinePlayers8 = Bukkit.getServer().getOnlinePlayers()).length, n4 = 0; n4 < length8; ++n4) {
                    final Player p = onlinePlayers8[n4];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:04 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 3) {
                Player[] onlinePlayers9;
                for (int length9 = (onlinePlayers9 = Bukkit.getServer().getOnlinePlayers()).length, n5 = 0; n5 < length9; ++n5) {
                    final Player p = onlinePlayers9[n5];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:03 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 2) {
                Player[] onlinePlayers10;
                for (int length10 = (onlinePlayers10 = Bukkit.getServer().getOnlinePlayers()).length, n6 = 0; n6 < length10; ++n6) {
                    final Player p = onlinePlayers10[n6];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:02 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds + 1) {
                Player[] onlinePlayers11;
                for (int length11 = (onlinePlayers11 = Bukkit.getServer().getOnlinePlayers()).length, n7 = 0; n7 < length11; ++n7) {
                    final Player p = onlinePlayers11[n7];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The Final Heal §fWill be §6Give §fto the players in §600:01 §fsecond(s)");
                }
            }
            if (ConfigIntegers.HEAL_TIME.get() * 60 == GRunnable.seconds) {
                this.gameManager.healAll();
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 600) {
                Player[] onlinePlayers12;
                for (int length12 = (onlinePlayers12 = Bukkit.getServer().getOnlinePlayers()).length, n8 = 0; n8 < length12; ++n8) {
                    final Player p = onlinePlayers12[n8];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §610:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 300) {
                Player[] onlinePlayers13;
                for (int length13 = (onlinePlayers13 = Bukkit.getServer().getOnlinePlayers()).length, n9 = 0; n9 < length13; ++n9) {
                    final Player p = onlinePlayers13[n9];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §605:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 180) {
                Player[] onlinePlayers14;
                for (int length14 = (onlinePlayers14 = Bukkit.getServer().getOnlinePlayers()).length, n10 = 0; n10 < length14; ++n10) {
                    final Player p = onlinePlayers14[n10];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §603:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 60) {
                Player[] onlinePlayers15;
                for (int length15 = (onlinePlayers15 = Bukkit.getServer().getOnlinePlayers()).length, n11 = 0; n11 < length15; ++n11) {
                    final Player p = onlinePlayers15[n11];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §601:00 §fminute(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 30) {
                Player[] onlinePlayers16;
                for (int length16 = (onlinePlayers16 = Bukkit.getServer().getOnlinePlayers()).length, n12 = 0; n12 < length16; ++n12) {
                    final Player p = onlinePlayers16[n12];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:30 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 10) {
                Player[] onlinePlayers17;
                for (int length17 = (onlinePlayers17 = Bukkit.getServer().getOnlinePlayers()).length, n13 = 0; n13 < length17; ++n13) {
                    final Player p = onlinePlayers17[n13];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:10 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 5) {
                Player[] onlinePlayers18;
                for (int length18 = (onlinePlayers18 = Bukkit.getServer().getOnlinePlayers()).length, n14 = 0; n14 < length18; ++n14) {
                    final Player p = onlinePlayers18[n14];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:5 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 4) {
                Player[] onlinePlayers19;
                for (int length19 = (onlinePlayers19 = Bukkit.getServer().getOnlinePlayers()).length, n15 = 0; n15 < length19; ++n15) {
                    final Player p = onlinePlayers19[n15];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:04 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 3) {
                Player[] onlinePlayers20;
                for (int length20 = (onlinePlayers20 = Bukkit.getServer().getOnlinePlayers()).length, n16 = 0; n16 < length20; ++n16) {
                    final Player p = onlinePlayers20[n16];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:03 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 2) {
                Player[] onlinePlayers21;
                for (int length21 = (onlinePlayers21 = Bukkit.getServer().getOnlinePlayers()).length, n17 = 0; n17 < length21; ++n17) {
                    final Player p = onlinePlayers21[n17];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:02 §fsecond(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds + 1) {
                Player[] onlinePlayers22;
                for (int length22 = (onlinePlayers22 = Bukkit.getServer().getOnlinePlayers()).length, n18 = 0; n18 < length22; ++n18) {
                    final Player p = onlinePlayers22[n18];
                    p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§6The PvP §fWill be §6Enabled §fIn §600:01 §fsegundo(s)");
                }
            }
            if (ConfigIntegers.PVP_TIME.get() * 60 == GRunnable.seconds) {
                this.gameManager.setPvP(true);
            }
            if (ScenarioManager.getInstance().getScenarioExact("BuildUHC").isEnabled()) {
                if (ConfigIntegers.BORDER_SHRINK_TIME.get() * 60 == GRunnable.seconds) {
                    this.gameManager.enablePermaDay();
                    this.gameManager.startBorderShrink();
                }
            }
            else if (ConfigIntegers.BORDER_SHRINK_TIME.get() * 60 - 300 == GRunnable.seconds) {
                this.gameManager.enablePermaDay();
                this.gameManager.startBorderShrink();
            }
            if (GRunnable.seconds == 30) {
                this.gameManager.setCanUseRescatter();
            }
        }
        else {
            Player[] onlinePlayers23;
            for (int length23 = (onlinePlayers23 = Bukkit.getServer().getOnlinePlayers()).length, n19 = 0; n19 < length23; ++n19) {
                final Player players = onlinePlayers23[n19];
                if (GRunnable.seconds <= 5) {
                    if (players.getLocation().getBlockY() < this.gameManager.getUHCWorld().getHighestBlockAt(players.getLocation()).getLocation().getBlockY()) {
                        players.teleport(players.getLocation().getWorld().getHighestBlockAt(players.getLocation()).getLocation().add(0.0, 1.0, 0.0));
                    }
                    players.setHealth(20.0);
                }
                this.tasksManager.checkBorder(players);
            }
        }
    }
}
