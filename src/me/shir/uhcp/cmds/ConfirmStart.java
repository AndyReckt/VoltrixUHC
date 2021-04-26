package me.shir.uhcp.cmds;

import java.util.*;
import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.lang.*;
import org.bukkit.*;
import me.shir.uhcp.util.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

public class ConfirmStart implements CommandExecutor
{
    private final ChatColor error;
    private final String prefix;
    private final String errorprefix;
    public static int timelobby;
    public static boolean timelobby2;
    private ArrayList<UUID> host;
    
    static {
        ConfirmStart.timelobby2 = false;
    }
    
    public ConfirmStart() {
        this.error = ChatColor.RED;
        this.prefix = GameManager.getGameManager().getPrefix();
        this.errorprefix = GameManager.getGameManager().getErrorPrefix();
        this.host = new ArrayList<UUID>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("confirmstart")) {
            if (!sender.hasPermission("uhc.host")) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                }
                return true;
            }
            if (ConfirmStart.timelobby2) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(this.errorprefix) + this.error + "A task is already running!");
                }
                return true;
            }
            ConfirmStart.timelobby = 300;
            ConfirmStart.timelobby2 = true;
            final Player player = (Player)sender;
            this.host.add(player.getUniqueId());
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player p2 = onlinePlayers[i];
                Bukkit.broadcastMessage(String.valueOf(this.prefix) + ("§e§lStarting Match in 05:00 minute(s)"));
            }
        }
        new BukkitRunnable() {
            public void run() {
                if (ConfirmStart.timelobby > 0) {
                    --ConfirmStart.timelobby;
                }
                if (ConfirmStart.timelobby == 300) {
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player p2 = onlinePlayers[i];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 05:00 minute(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 240) {
                    Player[] onlinePlayers2;
                    for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                        final Player p2 = onlinePlayers2[j];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 04:00 minute(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 180) {
                    Player[] onlinePlayers3;
                    for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                        final Player p2 = onlinePlayers3[k];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 03:00 minute(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 120) {
                    Player[] onlinePlayers4;
                    for (int length4 = (onlinePlayers4 = Bukkit.getServer().getOnlinePlayers()).length, l = 0; l < length4; ++l) {
                        final Player p2 = onlinePlayers4[l];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 02:00 minute(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 60) {
                    Player[] onlinePlayers5;
                    for (int length5 = (onlinePlayers5 = Bukkit.getServer().getOnlinePlayers()).length, n = 0; n < length5; ++n) {
                        final Player p2 = onlinePlayers5[n];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 01:00 minute(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 30) {
                    Player[] onlinePlayers6;
                    for (int length6 = (onlinePlayers6 = Bukkit.getServer().getOnlinePlayers()).length, n2 = 0; n2 < length6; ++n2) {
                        final Player p2 = onlinePlayers6[n2];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:30 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 10) {
                    Player[] onlinePlayers7;
                    for (int length7 = (onlinePlayers7 = Bukkit.getServer().getOnlinePlayers()).length, n3 = 0; n3 < length7; ++n3) {
                        final Player p2 = onlinePlayers7[n3];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:10 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 5) {
                    Player[] onlinePlayers8;
                    for (int length8 = (onlinePlayers8 = Bukkit.getServer().getOnlinePlayers()).length, n4 = 0; n4 < length8; ++n4) {
                        final Player p2 = onlinePlayers8[n4];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:05 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 4) {
                    Player[] onlinePlayers9;
                    for (int length9 = (onlinePlayers9 = Bukkit.getServer().getOnlinePlayers()).length, n5 = 0; n5 < length9; ++n5) {
                        final Player p2 = onlinePlayers9[n5];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:04 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 3) {
                    Player[] onlinePlayers10;
                    for (int length10 = (onlinePlayers10 = Bukkit.getServer().getOnlinePlayers()).length, n6 = 0; n6 < length10; ++n6) {
                        final Player p2 = onlinePlayers10[n6];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:03 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 2) {
                    Player[] onlinePlayers11;
                    for (int length11 = (onlinePlayers11 = Bukkit.getServer().getOnlinePlayers()).length, n7 = 0; n7 < length11; ++n7) {
                        final Player p2 = onlinePlayers11[n7];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:02 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 1) {
                    Player[] onlinePlayers12;
                    for (int length12 = (onlinePlayers12 = Bukkit.getServer().getOnlinePlayers()).length, n8 = 0; n8 < length12; ++n8) {
                        final Player p2 = onlinePlayers12[n8];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("§e§lStarting Match in 00:01 second(s)"));
                        }
                    }
                }
                else if (ConfirmStart.timelobby == 0) {
                    this.cancel();
                    Player[] onlinePlayers13;
                    for (int length13 = (onlinePlayers13 = Bukkit.getServer().getOnlinePlayers()).length, n9 = 0; n9 < length13; ++n9) {
                        final Player p2 = onlinePlayers13[n9];
                        if (LangManager.isEnglish(p2)) {
                            Bukkit.broadcastMessage(String.valueOf(ConfirmStart.this.prefix) + ("&e&lThe Game Is Going to Start , Please dont relog until the Scatter!"));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
        return false;
    }
}
