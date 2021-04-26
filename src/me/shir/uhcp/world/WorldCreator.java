package me.shir.uhcp.world;

import me.shir.uhcp.game.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import java.util.*;
import java.io.*;

public class WorldCreator
{
    private final GameManager gameManager;
    
    public WorldCreator(final boolean create, final boolean force) {
        (this.gameManager = GameManager.getGameManager()).setMapGenerating(true);
        if (force && !create) {
            this.deleteWorld(false);
            return;
        }
        if (force && create) {
            this.deleteWorld(true);
            return;
        }
        if (!force && !create) {
            this.deleteWorld(false);
            return;
        }
        if (!force && create) {
            if (!this.gameManager.wasWorldUsed()) {
                this.createWorld();
            }
            else {
                this.deleteWorld(true);
            }
        }
    }
    
    private void createWorld() {
        final int size = this.gameManager.getDefaultMapSize();
        final World w = Bukkit.getServer().createWorld(new org.bukkit.WorldCreator(this.gameManager.getUhcWorldName()).environment(World.Environment.NORMAL).type(WorldType.NORMAL));
        w.setTime(0L);
        w.setPVP(false);
        this.gameManager.setPvP(false);
        w.setDifficulty(Difficulty.NORMAL);
        w.setGameRuleValue("doDaylightCycle", "false");
        TasksManager.getInstance().buildWalls(this.gameManager.getDefaultMapSize(), Material.BEDROCK, 4, w);
        w.setSpawnLocation(0, 100, 0);
        final Location location = new Location(Bukkit.getWorld("uhcworld"), 0.0, 100.0, 0.0);
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mvimport " + this.gameManager.getUhcWorldName() + " normal");
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mvimport " + this.gameManager.getUhcWorldName() + "_nether nether");
        this.gameManager.setMapGenerating(false);
        this.gameManager.setWorldWasUsed(false);
        final World w_nether = Bukkit.getServer().createWorld(new org.bukkit.WorldCreator(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether").environment(World.Environment.NETHER).type(WorldType.NORMAL));
        w_nether.setPVP(false);
        new BukkitRunnable() {
            public void run() {
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl clear");
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl on");
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "clearcenter");
            }
        }.runTaskLater((Plugin)VenixUHC.getInstance(), 30L);
        final Location location2 = new Location(Bukkit.getWorld("uhcworld"), 0.0, 100.0, 0.0);
        final Block b = w.getBlockAt(location2);
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.EXTREME_HILLS_PLUS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.EXTREME_HILLS_PLUS_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.EXTREME_HILLS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.EXTREME_HILLS_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.TAIGA) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.TAIGA_HILLS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.TAIGA_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.COLD_TAIGA_HILLS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.COLD_TAIGA_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.MEGA_SPRUCE_TAIGA_HILLS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.MEGA_TAIGA_HILLS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.SWAMPLAND) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.SWAMPLAND_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (b.getWorld().getBiome(b.getX(), b.getZ()) == Biome.SMALL_MOUNTAINS) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "createuhc");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 25L);
            Bukkit.broadcastMessage("§cThe UHC Map generating incorrectly, generating other world!");
        }
        if (VenixUHC.getInstance().getConfig().getBoolean("settings.using-world-border-plugin") && !this.gameManager.wasGenerated()) {
            this.gameManager.setMapGenerating(true);
            new BukkitRunnable() {
                public void run() {
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb whoosh off");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb denypearl on");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb " + w.getName() + " setcorners " + size + " -" + size + " -" + size + " " + size);
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb " + w_nether.getName() + " setcorners " + size / 8 + " -" + size / 8 + " -" + size / 8 + " " + size / 8);
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb shape square");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb setmsg \"\"");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb portal on");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb knockback 2");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb " + w.getName() + " fill 600");
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb " + w.getName() + " fill confirm");
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 100L);
        }
        if (!this.gameManager.isGameRunning()) {
            new BukkitRunnable() {
                public void run() {
                    if (!WorldCreator.this.gameManager.isGameRunning()) {
                        Player[] onlinePlayers;
                        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                            final Player player = onlinePlayers[i];
                        }
                    }
                    else {
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously((Plugin)VenixUHC.getInstance(), 20L, 20L);
        }
    }
    
    private void deleteWorld(final boolean createAfter) {
        this.gameManager.setRestarted(false);
        final World w = Bukkit.getServer().getWorld(this.gameManager.getUhcWorldName());
        final World w_nether = Bukkit.getServer().getWorld(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether");
        if (w != null) {
            for (final Player players : w.getPlayers()) {
                players.teleport(this.gameManager.getSpawnLocation());
            }
            Bukkit.getServer().unloadWorld(w, false);
            this.deleteFile(w.getWorldFolder());
            this.deleteFile(w.getWorldFolder());
        }
        if (w_nether != null) {
            for (final Player players : w_nether.getPlayers()) {
                players.teleport(this.gameManager.getSpawnLocation());
            }
            Bukkit.getServer().unloadWorld(w_nether, false);
            this.deleteFile(w_nether.getWorldFolder());
            this.deleteFile(w_nether.getWorldFolder());
        }
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mvdelete " + this.gameManager.getUhcWorldName());
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mv confirm");
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mvdelete " + this.gameManager.getUhcWorldName() + "_nether");
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "multiverse-core:mvconfirm");
        if (createAfter) {
            new BukkitRunnable() {
                public void run() {
                    WorldCreator.this.createWorld();
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 120L);
        }
    }
    
    private boolean deleteFile(final File file) {
        if (file.isDirectory()) {
            File[] listFiles;
            for (int length = (listFiles = file.listFiles()).length, i = 0; i < length; ++i) {
                final File subfile = listFiles[i];
                if (!this.deleteFile(subfile)) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
