package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.lang.*;
import org.bukkit.*;
import me.shir.uhcp.util.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

public class DeathMatchCountdown implements CommandExecutor
{
    private final ChatColor error;
    public static int timelobby;
    public static boolean timelobby2;
    private ArrayList<UUID> host;
    
    static {
        DeathMatchCountdown.timelobby2 = false;
    }
    
    public DeathMatchCountdown() {
        this.error = ChatColor.RED;
        this.host = new ArrayList<UUID>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("deathmatchcountdown")) {
            if (!sender.hasPermission("uhc.host")) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage("§7§m---------------------------------------------");
                    sender.sendMessage("§eDeathMatch: §cYou don't have permissions to execute this command!");
                    sender.sendMessage("§7§m---------------------------------------------");
                }
                return true;
            }
            if (DeathMatchCountdown.timelobby2) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage("§7§m---------------------------------------------");
                    sender.sendMessage("§eDeathMatch: §cThe Deathmatch Countdown has been started!");
                    sender.sendMessage("§7§m---------------------------------------------");
                }
                return true;
            }
            DeathMatchCountdown.timelobby = 300;
            DeathMatchCountdown.timelobby2 = true;
            final Player player = (Player)sender;
            this.host.add(player.getUniqueId());
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player p2 = onlinePlayers[i];
                Bukkit.broadcastMessage("");
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
                            Bukkit.broadcastMessage(" ");
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
        return false;
    }
}
