package me.shir.uhcp.game;

import org.bukkit.scheduler.*;
import me.shir.uhcp.configs.*;
import org.bukkit.command.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.potion.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.teams.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class TasksManager
{
    private static TasksManager instance;
    private final GameManager gameManager;
    
    public TasksManager() {
        this.gameManager = GameManager.getGameManager();
    }
    
    public static TasksManager getInstance() {
        if (TasksManager.instance == null) {
            TasksManager.instance = new TasksManager();
        }
        return TasksManager.instance;
    }
    
    void startSeconds() {
        new BukkitRunnable() {
            int i = 10;
            
            public void run() {
                --this.i;
                if (this.i >= 1) {
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player p = onlinePlayers[i];
                        p.sendMessage(String.valueOf(TasksManager.this.gameManager.getBorderPrefix()) + "§fThe World Border Shrinks In §6" + this.i + " seconds!");
                    }
                }
                else if (this.i == 0) {
                    if (TasksManager.this.gameManager.getCurrentBorder() > 500) {
                        TasksManager.this.gameManager.setCurrentBorder(TasksManager.this.gameManager.getCurrentBorder() - ConfigIntegers.BORDER_SHRINK_BY.get());
                        TasksManager.this.shrinkBorder(TasksManager.this.gameManager.getCurrentBorder(), this);
                        Player[] onlinePlayers2;
                        for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                            final Player p = onlinePlayers2[j];
                            p.sendMessage(String.valueOf(TasksManager.this.gameManager.getBorderPrefix()) + TasksManager.this.gameManager.getMainColor() + "The world border has shrunk to " + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder() + TasksManager.this.gameManager.getMainColor() + "x" + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder());
                        }
                        Player[] onlinePlayers3;
                        for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                            final Player players = onlinePlayers3[k];
                            players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 1.0f);
                        }
                    }
                    else if (TasksManager.this.gameManager.getCurrentBorder() <= 500 && TasksManager.this.gameManager.getCurrentBorder() > 100) {
                        TasksManager.this.gameManager.setCurrentBorder(100);
                        TasksManager.this.shrinkBorder(TasksManager.this.gameManager.getCurrentBorder(), this);
                        Player[] onlinePlayers4;
                        for (int length4 = (onlinePlayers4 = Bukkit.getServer().getOnlinePlayers()).length, l = 0; l < length4; ++l) {
                            final Player p = onlinePlayers4[l];
                            p.sendMessage(String.valueOf(TasksManager.this.gameManager.getBorderPrefix()) + TasksManager.this.gameManager.getMainColor() + "The world border has shrunk to " + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder() + TasksManager.this.gameManager.getMainColor() + "x" + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder());
                        }
                        Player[] onlinePlayers5;
                        for (int length5 = (onlinePlayers5 = Bukkit.getServer().getOnlinePlayers()).length, n = 0; n < length5; ++n) {
                            final Player players = onlinePlayers5[n];
                            players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 1.0f);
                        }
                    }
                    else if (TasksManager.this.gameManager.getCurrentBorder() == 100) {
                        TasksManager.this.gameManager.setCurrentBorder(50);
                        TasksManager.this.shrinkBorder(TasksManager.this.gameManager.getCurrentBorder(), this);
                        Player[] onlinePlayers6;
                        for (int length6 = (onlinePlayers6 = Bukkit.getServer().getOnlinePlayers()).length, n2 = 0; n2 < length6; ++n2) {
                            final Player p = onlinePlayers6[n2];
                            p.sendMessage(String.valueOf(TasksManager.this.gameManager.getBorderPrefix()) + TasksManager.this.gameManager.getMainColor() + "The world border has shrunk to " + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder() + TasksManager.this.gameManager.getMainColor() + "x" + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder());
                        }
                        Player[] onlinePlayers7;
                        for (int length7 = (onlinePlayers7 = Bukkit.getServer().getOnlinePlayers()).length, n3 = 0; n3 < length7; ++n3) {
                            final Player players = onlinePlayers7[n3];
                            players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 1.0f);
                        }
                    }
                    else if (TasksManager.this.gameManager.getCurrentBorder() == 50) {
                        TasksManager.this.gameManager.setCurrentBorder(25);
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "deathmatch start");
                        TasksManager.this.shrinkBorder(TasksManager.this.gameManager.getCurrentBorder(), this);
                        Player[] onlinePlayers8;
                        for (int length8 = (onlinePlayers8 = Bukkit.getServer().getOnlinePlayers()).length, n4 = 0; n4 < length8; ++n4) {
                            final Player p = onlinePlayers8[n4];
                            p.sendMessage(String.valueOf(TasksManager.this.gameManager.getBorderPrefix()) + TasksManager.this.gameManager.getMainColor() + "The world border has shrunk to " + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder() + TasksManager.this.gameManager.getMainColor() + "x" + TasksManager.this.gameManager.getSecondaryColor() + TasksManager.this.gameManager.getCurrentBorder());
                        }
                        Player[] onlinePlayers9;
                        for (int length9 = (onlinePlayers9 = Bukkit.getServer().getOnlinePlayers()).length, n5 = 0; n5 < length9; ++n5) {
                            final Player players = onlinePlayers9[n5];
                            players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 1.0f);
                        }
                    }
                    new ScoreboardM().updateBorder();
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
    }
    
    public void scatterPlayer(final Player player) {
        UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        if (uhcPlayer == null) {
            PlayerManager.getPlayerManager().createUHCPlayer(player.getUniqueId());
            uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        }
        final ItemStack is = new ItemStack(this.gameManager.getStarterFood(), ConfigIntegers.STARTER_FOOD.get());
        for (final PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
        if (this.gameManager.isScattering()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 999));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 999));
        }
        player.setFallDistance(0.0f);
        uhcPlayer.setPlayerAlive(true);
        uhcPlayer.setDied(false);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setHealth(20.0);
        player.getInventory().clear();
        player.getInventory().setArmorContents((ItemStack[])null);
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setAllowFlight(false);
        player.setItemInHand(is);
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BOOK, 1) });
        uhcPlayer.setPlayerAlive(true);
        if (ScenarioManager.getInstance().getScenarioExact("RiskyRetrieval").isEnabled()) {
            player.getEnderChest().clear();
        }
        if (ScenarioManager.getInstance().getScenarioExact("ExtraInventory").isEnabled()) {
            player.getEnderChest().clear();
        }
        if (ScenarioManager.getInstance().getScenarioExact("GoneFishing").isEnabled() && !this.gameManager.getModerators().contains(player)) {
            final ItemStack ist = new ItemStack(Material.FISHING_ROD);
            ist.addUnsafeEnchantment(Enchantment.DURABILITY, 150);
            ist.addUnsafeEnchantment(Enchantment.LUCK, 250);
            player.getInventory().addItem(new ItemStack[] { ist });
            player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ANVIL, 64) });
            player.setLevel(999999);
        }
        if (ScenarioManager.getInstance().getScenarioExact("BuildUHC").isEnabled() && !this.gameManager.getModerators().contains(player)) {
            this.giveBuildUHCKit(player);
        }
        if (TeamManager.getInstance().isTeamsEnabled()) {
            Team team = TeamManager.getInstance().getTeam((OfflinePlayer)player);
            if (team == null) {
                TeamManager.getInstance().createTeam(player);
                team = TeamManager.getInstance().getTeam((OfflinePlayer)player);
                final Location teamLoc = team.getScatterLocation();
                player.teleport(teamLoc);
                player.setHealth(20.0);
                player.teleport(teamLoc);
                player.setHealth(20.0);
                final Location location = teamLoc;
                final Pig vehicle = (Pig)location.getWorld().spawnEntity(location, EntityType.PIG);
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127));
                new BukkitRunnable() {
                    public void run() {
                        vehicle.setPassenger((Entity)player);
                    }
                }.runTaskLater((Plugin)VenixUHC.getInstance(), 2L);
            }
            else {
                final Location teamLoc = team.getScatterLocation();
                player.teleport(teamLoc);
                player.setHealth(20.0);
                player.teleport(teamLoc);
                player.setHealth(20.0);
                final Location location = teamLoc;
                final Pig vehicle = (Pig)location.getWorld().spawnEntity(location, EntityType.PIG);
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127));
                new BukkitRunnable() {
                    public void run() {
                        vehicle.setPassenger((Entity)player);
                    }
                }.runTaskLater((Plugin)VenixUHC.getInstance(), 2L);
            }
        }
        else {
            final Location location2 = this.gameManager.getScatterLocation();
            player.teleport(location2);
            player.setHealth(20.0);
            player.teleport(location2);
            player.setHealth(20.0);
            final Pig vehicle2 = (Pig)location2.getWorld().spawnEntity(location2, EntityType.PIG);
            vehicle2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            vehicle2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127));
            vehicle2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
            vehicle2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127));
            new BukkitRunnable() {
                public void run() {
                    vehicle2.setPassenger((Entity)player);
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 2L);
        }
        player.setFallDistance(0.0f);
    }
    
    void checkBorder(final Player p) {
        final int size = this.gameManager.getCurrentBorder();
        final World w = p.getWorld();
        if (!w.getName().equals(this.gameManager.getSpawnWorld().getName())) {
            if (w.getEnvironment().equals((Object)World.Environment.NETHER)) {
                return;
            }
            if (p.getLocation().getBlockX() > size) {
                p.teleport(new Location(w, (double)(size - 2), (double)p.getLocation().getBlockY(), (double)p.getLocation().getBlockZ()));
                if (p.getLocation().getBlockY() < w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) {
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)(w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) + 2), (double)p.getLocation().getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You have reached the edge of this world!");
                }
            }
            if (p.getLocation().getBlockZ() > size) {
                p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)p.getLocation().getBlockY(), (double)(size - 2)));
                if (p.getLocation().getBlockY() < w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) {
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)(w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) + 2), (double)p.getLocation().getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You have reached the edge of this world!");
                }
            }
            if (p.getLocation().getBlockX() < -size) {
                p.teleport(new Location(w, (double)(-size + 2), (double)p.getLocation().getBlockY(), (double)p.getLocation().getBlockZ()));
                if (p.getLocation().getBlockY() < w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) {
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)(w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) + 2), (double)p.getLocation().getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You have reached the edge of this world!");
                }
            }
            if (p.getLocation().getBlockZ() < -size) {
                p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)p.getLocation().getBlockY(), (double)(-size + 2)));
                if (p.getLocation().getBlockY() < w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) {
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), (double)(w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) + 2), (double)p.getLocation().getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You have reached the edge of this world!");
                }
            }
        }
    }
    
    public void shrinkBorder(final int size, final BukkitRunnable runnable) {
        new BorderRunnable().runTaskTimerAsynchronously((Plugin)VenixUHC.getInstance(), 200L, 200L);
        runnable.cancel();
        this.gameManager.setCurrentBorder(size);
        final World w = this.gameManager.getUHCWorld();
        if (size > this.gameManager.getRandomShrinkAtAndBelow()) {
            for (final Player p : this.gameManager.getUHCWorld().getPlayers()) {
                if (p.getLocation().getBlockX() > size) {
                    p.setNoDamageTicks(59);
                    p.setFallDistance(0.0f);
                    p.teleport(new Location(w, (double)(size - 4), w.getHighestBlockYAt(size - 4, p.getLocation().getBlockZ()) + 0.5, (double)p.getLocation().getBlockZ()));
                    p.setFallDistance(0.0f);
                    p.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p.getLocation().getBlockZ()));
                }
                if (p.getLocation().getBlockZ() > size) {
                    p.setNoDamageTicks(59);
                    p.setFallDistance(0.0f);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockYAt(p.getLocation().getBlockX(), size - 4) + 0.5, (double)(size - 4)));
                    p.setFallDistance(0.0f);
                    p.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p.getLocation().getBlockZ()));
                }
                if (p.getLocation().getBlockX() < -size) {
                    p.setNoDamageTicks(59);
                    p.setFallDistance(0.0f);
                    p.teleport(new Location(w, (double)(-size + 4), w.getHighestBlockYAt(-size + 4, p.getLocation().getBlockZ()) + 0.5, (double)p.getLocation().getBlockZ()));
                    p.setFallDistance(0.0f);
                    p.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p.getLocation().getBlockZ()));
                }
                if (p.getLocation().getBlockZ() < -size) {
                    p.setNoDamageTicks(59);
                    p.setFallDistance(0.0f);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockYAt(p.getLocation().getBlockX(), -size + 4) + 0.5, (double)(-size + 4)));
                    p.setFallDistance(0.0f);
                    p.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                    p.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                    p.teleport(new Location(w, (double)p.getLocation().getBlockX(), w.getHighestBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p.getLocation().getBlockZ()));
                }
            }
        }
        else if (TeamManager.getInstance().isTeamsEnabled()) {
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player p2 = onlinePlayers[i];
                if (!p2.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && (p2.getLocation().getBlockX() > size || p2.getLocation().getBlockZ() > size || p2.getLocation().getBlockX() < -size || p2.getLocation().getBlockZ() < -size || p2.getWorld().getName().equalsIgnoreCase(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether"))) {
                    final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)p2);
                    team.setScatterLocation(this.gameManager.getScatterLocation());
                    for (final UUID teamPlayers : team.getPlayers()) {
                        final Player player = Bukkit.getServer().getPlayer(teamPlayers);
                        if (player != null) {
                            p2.setNoDamageTicks(59);
                            p2.setFallDistance(0.0f);
                            player.teleport(team.getScatterLocation());
                            p2.setFallDistance(0.0f);
                            p2.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                            p2.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                            p2.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                            p2.teleport(new Location(w, (double)p2.getLocation().getBlockX(), w.getHighestBlockAt(p2.getLocation().getBlockX(), p2.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p2.getLocation().getBlockZ()));
                            p2.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You were shrunk in the border!");
                        }
                    }
                }
            }
        }
        else {
            Player[] onlinePlayers2;
            for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                final Player p2 = onlinePlayers2[j];
                if (!p2.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && (p2.getLocation().getBlockX() > size || p2.getLocation().getBlockZ() > size || p2.getLocation().getBlockX() < -size || p2.getLocation().getBlockZ() < -size || p2.getWorld().getName().equalsIgnoreCase(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether"))) {
                    p2.setNoDamageTicks(59);
                    p2.setFallDistance(0.0f);
                    p2.teleport(this.gameManager.getScatterLocation());
                    p2.setFallDistance(0.0f);
                    p2.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                    p2.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                    p2.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                    p2.teleport(new Location(w, (double)p2.getLocation().getBlockX(), w.getHighestBlockAt(p2.getLocation().getBlockX(), p2.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, (double)p2.getLocation().getBlockZ()));
                    p2.sendMessage(String.valueOf(this.gameManager.getBorderPrefix()) + this.gameManager.getMainColor() + "You were shrunk in the border!");
                }
            }
        }
        this.buildWalls(size, Material.BEDROCK, 4, w);
    }
    
    public void buildWalls(final int size, final Material mat, final int h, final World w) {
        final Location loc = new Location(w, 0.0, 59.0, 0.0);
        for (int i = h; i < h + h; ++i) {
            for (int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; ++x) {
                for (int y = 58; y <= 58; ++y) {
                    for (int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; ++z) {
                        if (x == loc.getBlockX() - size || x == loc.getBlockX() + size || z == loc.getBlockZ() - size || z == loc.getBlockZ() + size) {
                            final Location loc2 = new Location(w, (double)x, (double)y, (double)z);
                            loc2.setY((double)w.getHighestBlockYAt(loc2));
                            loc2.getBlock().setType(mat);
                        }
                    }
                }
            }
        }
    }
    
    public void giveBuildUHCKit(final Player player) {
        final ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        final ItemStack chestplace = new ItemStack(Material.DIAMOND_CHESTPLATE);
        final ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        final ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        final ItemStack bow = new ItemStack(Material.BOW);
        final ItemStack fishingrod = new ItemStack(Material.FISHING_ROD);
        final ItemStack axe = new ItemStack(Material.DIAMOND_PICKAXE);
        final ItemStack pickaxe = new ItemStack(Material.DIAMOND_AXE);
        final ItemStack arrow = new ItemStack(Material.ARROW, 64);
        final ItemStack wood = new ItemStack(Material.WOOD, 64);
        final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 64);
        final ItemStack water = new ItemStack(Material.WATER_BUCKET);
        final ItemStack lava = new ItemStack(Material.LAVA_BUCKET);
        final ItemStack goldenapple = new ItemStack(Material.GOLDEN_APPLE, 6);
        helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        chestplace.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, fishingrod);
        player.getInventory().setItem(2, bow);
        player.getInventory().setItem(4, goldenapple);
        player.getInventory().setItem(5, this.getGoldenHead(3));
        player.getInventory().setItem(6, pickaxe);
        player.getInventory().setItem(7, axe);
        player.getInventory().setItem(8, wood);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplace);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().addItem(new ItemStack[] { cobblestone });
        player.getInventory().addItem(new ItemStack[] { water });
        player.getInventory().addItem(new ItemStack[] { lava });
        player.getInventory().addItem(new ItemStack[] { cobblestone });
        player.getInventory().addItem(new ItemStack[] { water });
        player.getInventory().addItem(new ItemStack[] { lava });
        player.getInventory().addItem(new ItemStack[] { wood });
        player.getInventory().addItem(new ItemStack[] { arrow });
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, 64) });
        player.updateInventory();
    }
    
    private ItemStack getGoldenHead(final int amount) {
        final ItemStack ist = new ItemStack(Material.GOLDEN_APPLE, amount);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName("§6Golden Head");
        final List<String> al = new ArrayList<String>();
        al.add("§5Some say consuming the head of a");
        al.add("§5fallen foe strengthens the blood");
        im.setLore((List)al);
        ist.setItemMeta(im);
        return ist;
    }
}
