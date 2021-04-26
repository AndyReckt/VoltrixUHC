package me.shir.uhcp.listeners;

import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.game.*;
import org.bukkit.event.*;
import me.shir.uhcp.util.*;

public class PlayerQuitListener implements Listener
{
    private final GameManager gameManager;
    
    public PlayerQuitListener() {
        this.gameManager = GameManager.getGameManager();
    }
    
    @EventHandler
    public void onPlayerQuitEvent(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        if (this.gameManager.isGameRunning() || this.gameManager.isScattering()) {
            new BukkitRunnable() {
                public void run() {
                    if (!Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
                        uhcPlayer.setPlayerAlive(false);
                        uhcPlayer.setDied(true);
                        player.setWhitelisted(false);
                        if (player.hasPermission("uhc.spectate")) {
                            PlayerManager.getPlayerManager().setSpectating(true, player);
                            player.setWhitelisted(true);
                        }
                    }
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), (long)(1200 * this.gameManager.getKillOnQuitTime()));
        }
        if (uhcPlayer.isPlayerAlive()) {
            if ((this.gameManager.isGameRunning() || this.gameManager.isScattering()) && !uhcPlayer.didPlayerDie() && player.getWorld().equals(this.gameManager.getUHCWorld())) {
                uhcPlayer.setLastArmour(player.getInventory().getArmorContents());
                uhcPlayer.setLastInventory(player.getInventory().getContents());
                if (this.gameManager.isScattering() && player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld())) {
                    TasksManager.getInstance().scatterPlayer(player);
                }
                uhcPlayer.setRespawnLocation(player.getLocation());
            }
            if (this.gameManager.getHostName().equalsIgnoreCase(player.getName())) {
                this.gameManager.setHost(null);
            }
            if (this.gameManager.getModerators().contains(player)) {
                this.gameManager.setModerator(player, false);
            }
        }
    }
    
    @EventHandler
    public void onStaffLeave(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player all = onlinePlayers[i];
            if (player.hasPermission("uhc.host") && all.hasPermission("uhc.host")) {
                all.sendMessage("§6§lStaff §8» §b" + player.getName() + " §fhas §6§lLeave");
            }
        }
    }
    
    @EventHandler
    public void onDonatorLeave(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player all = onlinePlayers[i];
            if (player.hasPermission("uhc.vip") && all.hasPermission("essentials.msg")) {
                all.sendMessage("§6§lDonators §7» §a§n" + player.getName() + " §fhas §6§lLeave");
            }
        }
    }
}
