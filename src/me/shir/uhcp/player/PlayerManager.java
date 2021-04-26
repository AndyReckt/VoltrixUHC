package me.shir.uhcp.player;

import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import me.shir.uhcp.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import java.util.*;

public class PlayerManager
{
    private static PlayerManager playerManager;
    private final GameManager gameManager;
    private Map<UUID, UHCPlayer> players;
    
    public PlayerManager() {
        this.gameManager = GameManager.getGameManager();
        this.players = new HashMap<UUID, UHCPlayer>();
    }
    
    public static PlayerManager getPlayerManager() {
        if (PlayerManager.playerManager == null) {
            PlayerManager.playerManager = new PlayerManager();
        }
        return PlayerManager.playerManager;
    }
    
    public void setSpectating(final boolean b, final Player player) {
        final UHCPlayer uhcPlayer = this.getUHCPlayer(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                uhcPlayer.setSpec(b);
                if (b) {
                    uhcPlayer.setPlayerAlive(false);
                    player.setGameMode(GameMode.CREATIVE);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    if (player.isOnline()) {
                        Player[] onlinePlayers;
                        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                            final Player players = onlinePlayers[i];
                            players.hidePlayer(player);
                        }
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.getActivePotionEffects().clear();
                        player.getInventory().clear();
                        player.getInventory().setArmorContents((ItemStack[])null);
                        player.getInventory().setItem(1, PlayerManager.this.gameManager.getRandomPlayer());
                        if (player.hasPermission("uhc.spec") || player.hasPermission("uhc.host")) {
                            player.getInventory().setItem(0, PlayerManager.this.gameManager.getItemInventory());
                        }
                        if (GameManager.getGameManager().getModerators().contains(player)) {
                            player.getInventory().setItem(2, PlayerManager.this.gameManager.getStaffonline());
                            player.getInventory().setItem(4, PlayerManager.this.gameManager.getFreezeItem());
                            player.getInventory().setItem(7, PlayerManager.this.gameManager.getXalerts());
                            player.getInventory().setItem(6, PlayerManager.this.gameManager.getTopDia());
                            player.getInventory().setItem(8, PlayerManager.this.gameManager.getPlayersNether());
                        }
                        Player[] onlinePlayers2;
                        for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                            final Player staff = onlinePlayers2[j];
                            for (final Player mods : GameManager.getGameManager().getModerators()) {
                                if (mods != null && GameManager.getGameManager().getModerators().contains(player)) {
                                    mods.showPlayer(player);
                                    player.showPlayer(mods);
                                    if (PlayerManager.this.gameManager.isGameRunning()) {
                                        continue;
                                    }
                                    player.showPlayer(staff);
                                }
                            }
                        }
                        player.spigot().setCollidesWithEntities(false);
                        player.teleport(PlayerManager.this.gameManager.getUHCWorld().getSpawnLocation());
                        player.setGameMode(GameMode.CREATIVE);
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§aYou are now vanished!");
                        new BukkitRunnable() {
                            public void run() {
                                player.setGameMode(GameMode.CREATIVE);
                            }
                        }.runTaskLater((Plugin)VenixUHC.getInstance(), 5L);
                    }
                }
                if (!b && player.isOnline()) {
                    Player[] onlinePlayers3;
                    for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                        final Player players = onlinePlayers3[k];
                        players.showPlayer(player);
                    }
                    if (PlayerManager.this.gameManager.getModerators().contains(player)) {
                        PlayerManager.this.gameManager.getModerators().remove(player);
                    }
                    Player[] onlinePlayers4;
                    for (int length4 = (onlinePlayers4 = Bukkit.getServer().getOnlinePlayers()).length, l = 0; l < length4; ++l) {
                        final Player staff = onlinePlayers4[l];
                        for (final Player mods : GameManager.getGameManager().getModerators()) {
                            if (mods != null) {
                                player.hidePlayer(mods);
                                if (PlayerManager.this.gameManager.isGameRunning()) {
                                    continue;
                                }
                                player.hidePlayer(staff);
                                staff.hidePlayer(player);
                            }
                        }
                    }
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.getActivePotionEffects().clear();
                    player.getInventory().clear();
                    player.getInventory().setArmorContents((ItemStack[])null);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.spigot().setCollidesWithEntities(true);
                    player.teleport(PlayerManager.this.gameManager.getSpawnLocation());
                    player.sendMessage(String.valueOf(PlayerManager.this.gameManager.getPrefix()) + "§aYou are no longer vanished!");
                }
            }
        }, 25L);
    }
    
    public Map<UUID, UHCPlayer> getUHCPlayers() {
        return this.players;
    }
    
    public UHCPlayer getUHCPlayer(final UUID uuid) {
        return this.players.get(uuid);
    }
    
    public boolean doesUHCPlayerExsists(final UUID uuid) {
        return this.players.containsKey(uuid);
    }
    
    public void createUHCPlayer(final UUID uuid) {
        this.players.put(uuid, new UHCPlayer(this.gameManager.isStatsEnabled(), uuid));
    }
    
    public int alivePlayers() {
        int i = 0;
        for (final UHCPlayer pl : this.players.values()) {
            if (pl.isPlayerAlive()) {
                ++i;
            }
        }
        return i;
    }
    
    public int online() {
        int i = 0;
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length; ++j) {
            final Player players = onlinePlayers[j];
            ++i;
        }
        return i;
    }
    
    public int deadPlayers() {
        int i = 0;
        for (final UHCPlayer pl : this.players.values()) {
            if (pl.didPlayerDie() && pl.isOnline()) {
                ++i;
            }
        }
        return i;
    }
    
    public int scattering() {
        int i = 0;
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length; ++j) {
            final Player players = onlinePlayers[j];
            if (players.getWorld().equals(Bukkit.getWorld("MineMexMap"))) {
                ++i;
            }
        }
        return i;
    }
    
    public int spectators() {
        int i = 0;
        for (final UHCPlayer pl : this.players.values()) {
            if (pl.isSpectating()) {
                ++i;
            }
        }
        return i;
    }
    
    public Set<UHCPlayer> uhcPlayersSet(final Set<UUID> s) {
        final Set<UHCPlayer> set = new HashSet<UHCPlayer>();
        for (final UUID uuid : s) {
            set.add(this.getUHCPlayer(uuid));
        }
        return set;
    }
}