package me.shir.uhcp.listeners;

import me.shir.uhcp.game.*;
import org.bukkit.scheduler.*;
import org.bukkit.command.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.inventory.*;
import java.text.*;
import com.nametagedit.plugin.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.teams.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;
import org.bukkit.event.player.*;
import me.shir.uhcp.configs.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.block.*;
import de.inventivegames.hologram.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class PlayerDeathListener implements Listener
{
    private final ScoreboardM sb;
    private final GameManager gameManager;
    private boolean clearDrops;
    public static int nocleantime;
    double EXP_MULTIPLIER_LOOTING_PER_LEVEL;
    double EXP_MULTIPLIER_FORTUNE_PER_LEVEL;
    double EXP_MULTIPLIER_GENERAL;
    double EXP_MULTIPLIER_FISHING;
    double EXP_MULTIPLIER_LUCK_PER_LEVEL;
    double EXP_MULTIPLIER_SMELTING;
    
    static {
        PlayerDeathListener.nocleantime = 20;
    }
    
    public PlayerDeathListener() {
        this.sb = new ScoreboardM();
        this.gameManager = GameManager.getGameManager();
        this.clearDrops = false;
        this.EXP_MULTIPLIER_LOOTING_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_FORTUNE_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_GENERAL = 3.0;
        this.EXP_MULTIPLIER_FISHING = 3.0;
        this.EXP_MULTIPLIER_LUCK_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_SMELTING = 3.0;
    }
    
    @EventHandler
    public void onPlayerDeathEvent(final PlayerDeathEvent e) {
        if (this.gameManager.isGameRunning() && e.getEntity().getWorld() != this.gameManager.getSpawnLocation().getWorld()) {
            final Player playerVictim = e.getEntity();
            final Player playerKiller = e.getEntity().getKiller();
            final UHCPlayer uhcPlayerVictim = PlayerManager.getPlayerManager().getUHCPlayer(playerVictim.getUniqueId());
            uhcPlayerVictim.setRespawnLocation(e.getEntity().getLocation());
            uhcPlayerVictim.setLastArmour(e.getEntity().getInventory().getArmorContents());
            uhcPlayerVictim.setLastInventory(e.getEntity().getInventory().getContents());
            uhcPlayerVictim.setPlayerAlive(false);
            uhcPlayerVictim.setDied(true);
            e.setDeathMessage((String)null);
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl remove " + playerVictim.getName());
                    PlayerDeathListener.this.gameManager.removeWhitelist((OfflinePlayer)playerVictim);
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 20L);
            if (this.gameManager.isStatsEnabled()) {
                uhcPlayerVictim.addTotalDeath();
            }
            this.deathScenarios(playerVictim, uhcPlayerVictim, e.getEntity().getLocation());
            if (this.clearDrops) {
                e.getDrops().clear();
            }
            else {
                if (ScenarioManager.getInstance().getScenarioExact("GoldenRetriever").isEnabled()) {
                    e.getDrops().add(this.getGoldenHead());
                }
                if (ScenarioManager.getInstance().getScenarioExact("Diamondless").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.DIAMOND, 1));
                }
                if (ScenarioManager.getInstance().getScenarioExact("Goldless").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.GOLD_INGOT, 8));
                    e.getDrops().add(this.getGoldenHead());
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2));
                    e.getDrops().add(new ItemStack(Material.DIAMOND));
                    e.getDrops().add(new ItemStack(Material.ARROW, 32));
                    e.getDrops().add(new ItemStack(Material.STRING, 2));
                }
                this.spawnHead(playerVictim);
            }
            if (!(e.getEntity().getKiller() instanceof Player)) {
                Player[] onlinePlayers;
                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                    final Player p = onlinePlayers[i];
                    p.sendMessage("§c" + playerVictim.getName() + "§7[§f" + uhcPlayerVictim.getKills() + "§7] §eDead due to a fall");
                }
            }
            if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
                final UHCPlayer uhcPlayerKiller = PlayerManager.getPlayerManager().getUHCPlayer(playerKiller.getUniqueId());
                uhcPlayerKiller.addKill();
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player p2 = onlinePlayers2[j];
                    p2.sendMessage("§c" + playerVictim.getName() + "§7[§f" + uhcPlayerVictim.getKills() + "§7] §ewas slain by §a" + e.getEntity().getKiller().getName() + "§7[§f" + uhcPlayerKiller.getKills() + "§7]");
                }
                this.Cohete(e.getEntity());
                final DecimalFormat df = new DecimalFormat("#.#");
                final Player playerS = playerKiller;
                final double health = playerS.getHealthScale() / 2.0;
                playerVictim.sendMessage("§7§m---------------------");
                playerVictim.sendMessage(this.gameManager.getSecondaryColor() + "You have been killed by " + this.gameManager.getMainColor() + playerKiller.getName());
                playerVictim.sendMessage(this.gameManager.getMainColor() + playerKiller.getName() + "'s Health: " + df.format(health) + "§4\u2764");
                playerVictim.sendMessage(this.gameManager.getMainColor() + playerKiller.getName() + "'s Kills: " + uhcPlayerKiller.getKills());
                playerVictim.sendMessage("§7§m---------------------");
                if (TeamManager.getInstance().isTeamsEnabled()) {
                    final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)playerKiller);
                    team.addKill();
                }
                if (this.gameManager.isStatsEnabled()) {
                    uhcPlayerKiller.addTotalKill();
                    if (uhcPlayerKiller.getKills() > uhcPlayerKiller.getHighestKillStreak()) {
                        uhcPlayerKiller.setHighestKillStreak(uhcPlayerKiller.getKills());
                    }
                }
                if (ScenarioManager.getInstance().getScenarioExact("NoClean").isEnabled()) {
                    Listeners.innoclean.add(playerKiller.getUniqueId());
                    playerKiller.sendMessage("§6§lNoClean §8» §eYou have been protected for 20 seconds!");
                    playerKiller.setPlayerListName("§c" + playerKiller.getName());
                    NametagEdit.getApi().setNametag(playerKiller, "§c", "");
                    new BukkitRunnable() {
                        public void run() {
                            if (PlayerDeathListener.nocleantime > 1) {
                                --PlayerDeathListener.nocleantime;
                            }
                            else {
                                this.cancel();
                                Listeners.innoclean.remove(playerKiller.getUniqueId());
                                NametagEdit.getApi().clearNametag(playerKiller);
                                playerKiller.setPlayerListName("§f" + playerKiller.getName());
                                playerKiller.sendMessage("§6§lNoClean §8» §eYou have lost your protection!");
                                PlayerDeathListener.nocleantime = 20;
                            }
                        }
                    }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        if (ScenarioManager.getInstance().getScenarioExact("NightmareMode").isEnabled()) {
            if (event.getEntity() instanceof Creeper) {
                if (new Random().nextInt(99) + 1 >= 50) {
                    event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
                }
            }
            else if (event.getEntity() instanceof Zombie) {
                final Skeleton skelly = (Skeleton)event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Skeleton.class);
                skelly.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                skelly.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 0));
            }
            else if (event.getEntity() instanceof Spider) {
                final Location loc = event.getEntity().getLocation();
                final Block b = loc.getBlock();
                b.getRelative(BlockFace.EAST).setType(Material.WEB);
                b.getRelative(BlockFace.WEST).setType(Material.WEB);
                b.getRelative(BlockFace.SOUTH).setType(Material.WEB);
                b.getRelative(BlockFace.NORTH).setType(Material.WEB);
                b.getRelative(BlockFace.NORTH_EAST).setType(Material.REDSTONE_WIRE);
                b.getRelative(BlockFace.NORTH_WEST).setType(Material.REDSTONE_WIRE);
                b.getRelative(BlockFace.SOUTH_EAST).setType(Material.REDSTONE_WIRE);
                b.getRelative(BlockFace.SOUTH_WEST).setType(Material.REDSTONE_WIRE);
            }
        }
    }
    
    @EventHandler
    public void onPlayerRespawnEvent(final PlayerRespawnEvent e) {
        final Player player = e.getPlayer();
        if (this.gameManager.isGameRunning()) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
            if (player.hasPermission("uhc.spectate")) {
                e.setRespawnLocation(this.gameManager.getUHCWorld().getSpawnLocation());
                PlayerManager.getPlayerManager().setSpectating(true, player);
                if (!uhcPlayer.isSpectating()) {
                    PlayerManager.getPlayerManager().setSpectating(true, player);
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
            else {
                e.setRespawnLocation(this.gameManager.getSpawnLocation());
                if (this.gameManager.kickPlayerOnDeath()) {
                    Bukkit.getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            player.kickPlayer(PlayerDeathListener.this.gameManager.getKickDeathMessage());
                            new BukkitRunnable() {
                                public void run() {
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl remove " + player.getName());
                                    PlayerDeathListener.this.gameManager.removeWhitelist((OfflinePlayer)player);
                                }
                            }.runTaskLater((Plugin)VenixUHC.getInstance(), 20L);
                        }
                    }, (long)(20 * this.gameManager.getDeathKickTime()));
                }
            }
        }
        else {
            e.setRespawnLocation(this.gameManager.getSpawnLocation());
        }
    }
    
    @EventHandler
    public void onEntityDeathEvent(final EntityDeathEvent e) {
        final EntityType entity = e.getEntity().getType();
        final Player playerKiller = e.getEntity().getKiller();
        UHCPlayer uhcPlayer = null;
        final boolean killer = e.getEntity().getKiller() != null;
        final boolean sEnabled = this.gameManager.isStatsEnabled();
        if (e.getEntity().getKiller() != null) {
            uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getEntity().getKiller().getUniqueId());
        }
        switch (entity) {
            case GHAST: {
                if (!ConfigBooleans.REGENERATION_POTIONS.isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GOLD_INGOT));
                }
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addGhastsKilled();
                    break;
                }
                break;
            }
            case ZOMBIE: {
                try {
                    if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addZombiesKilled();
                    }
                    final Zombie zombie = (Zombie)e.getEntity();
                    if (!zombie.getCustomName().equalsIgnoreCase(null) && TeamManager.getInstance().isTeamsEnabled()) {
                        final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)playerKiller);
                        team.addKill();
                        break;
                    }
                    break;
                }
                catch (NullPointerException ex) {}
            }
            case SHEEP: {
                e.getDrops().add(new ItemStack(Material.LEATHER, 2));
                if (ScenarioManager.getInstance().getScenarioExact("LuckySheep").isEnabled() && uhcPlayer.isPlayerAlive() && Math.random() <= 0.25) {
                    e.getDrops().add(new ItemStack(Material.STRING));
                    break;
                }
                break;
            }
            case CREEPER: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addCreepersKilled();
                    break;
                }
                break;
            }
            case SKELETON: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addSkeletonsKilled();
                    break;
                }
                break;
            }
            case CAVE_SPIDER: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addCaveSpiderKilled();
                    break;
                }
                break;
            }
            case SPIDER: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addSpidersKilled();
                }
                if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.STRING, 3));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.STRING));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.STRING));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                    e.getDrops().add(new ItemStack(Material.STRING, 2));
                    break;
                }
                break;
            }
            case BLAZE: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addBlazesKilled();
                    break;
                }
                break;
            }
            case COW: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addCowsKilled();
                }
                if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 3));
                    e.getDrops().add(new ItemStack(Material.LEATHER, 3));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
                    e.getDrops().add(new ItemStack(Material.LEATHER));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
                    e.getDrops().add(new ItemStack(Material.LEATHER));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
                    e.getDrops().add(new ItemStack(Material.LEATHER, 2));
                    break;
                }
                break;
            }
            case PIG: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addPigsKilled();
                }
                if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GRILLED_PORK, 3));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GRILLED_PORK, 2));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GRILLED_PORK, 2));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GRILLED_PORK, 2));
                    break;
                }
                break;
            }
            case CHICKEN: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addChickensKilled();
                }
                if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 3));
                    e.getDrops().add(new ItemStack(Material.FEATHER, 3));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 2));
                    e.getDrops().add(new ItemStack(Material.FEATHER, 2));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
                    e.getDrops().add(new ItemStack(Material.FEATHER));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 2));
                    e.getDrops().add(new ItemStack(Material.FEATHER, 2));
                    break;
                }
                break;
            }
            case HORSE: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addHorsesKilled();
                }
                if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.LEATHER, 3));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.LEATHER));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.LEATHER));
                    break;
                }
                if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.LEATHER, 2));
                    break;
                }
                break;
            }
            case WITCH: {
                if (sEnabled && killer && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                    uhcPlayer.addWitchesKilled();
                    break;
                }
                break;
            }
        }
    }
    
    @EventHandler
    public void onTripleXP(final EntityDeathEvent e) {
        if (!ScenarioManager.getInstance().getScenarioExact("TripleXP").isEnabled()) {
            return;
        }
        final double amount = e.getDroppedExp();
        final Player killer = e.getEntity().getKiller();
        if (killer != null && amount > 0.0) {
            final ItemStack stack = killer.getItemInHand();
            if (stack != null && stack.getType() != Material.AIR) {
                final int enchantmentLevel = stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                if (enchantmentLevel > 0L) {
                    final double multiplier = enchantmentLevel * this.EXP_MULTIPLIER_LOOTING_PER_LEVEL;
                    final int result = (int)Math.ceil(amount * multiplier);
                    e.setDroppedExp(result);
                }
            }
        }
    }
    
    private void spawnHead(final Player playerVictim) {
        playerVictim.getLocation().getBlock().setType(Material.NETHER_FENCE);
        playerVictim.getWorld().getBlockAt(playerVictim.getLocation().add(0.0, 1.0, 0.0)).setType(Material.SKULL);
        final Skull skull = (Skull)playerVictim.getLocation().add(0.0, 1.0, 0.0).getBlock().getState();
        skull.setOwner(playerVictim.getName());
        skull.update();
        final Block block = playerVictim.getLocation().add(0.0, 1.0, 0.0).getBlock();
        block.setData((byte)1);
    }
    
    private ItemStack getGoldenHead() {
        final ItemStack ist = new ItemStack(Material.GOLDEN_APPLE, 1);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName("§6Golden Head");
        final List<String> al = new ArrayList<String>();
        al.add("§5Some say consuming the head of a");
        al.add("§5fallen foe strengthens the blood");
        im.setLore((List)al);
        ist.setItemMeta(im);
        return ist;
    }
    
    private void deathScenarios(final Player victim, final UHCPlayer uhcPlayerVictim, final Location where) {
        if (ScenarioManager.getInstance().getScenarioExact("TimeBomb").isEnabled()) {
            this.clearDrops = true;
            where.getBlock().setType(Material.CHEST);
            final Chest chest = (Chest)where.getBlock().getState();
            where.add(1.0, 0.0, 0.0).getBlock().setType(Material.CHEST);
            where.add(0.0, 1.0, 0.0).getBlock().setType(Material.AIR);
            where.add(1.0, 1.0, 0.0).getBlock().setType(Material.AIR);
            chest.getInventory().addItem(new ItemStack[] { this.getGoldenHead() });
            chest.getInventory().addItem(uhcPlayerVictim.lastArmour());
            ItemStack[] lastInventory;
            for (int length = (lastInventory = uhcPlayerVictim.lastInventory()).length, i = 0; i < length; ++i) {
                final ItemStack itemStack = lastInventory[i];
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    chest.getInventory().addItem(new ItemStack[] { itemStack });
                }
            }
            if (ScenarioManager.getInstance().getScenarioExact("GoldenRetriever").isEnabled()) {
                chest.getInventory().addItem(new ItemStack[] { this.getGoldenHead() });
            }
            if (ScenarioManager.getInstance().getScenarioExact("Diamondless").isEnabled()) {
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND, 1) });
            }
            if (ScenarioManager.getInstance().getScenarioExact("Goldless").isEnabled()) {
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_INGOT, 8) });
                chest.getInventory().addItem(new ItemStack[] { this.getGoldenHead() });
            }
            if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, 2) });
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND) });
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW, 32) });
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STRING, 2) });
            }
            final String name = victim.getName();
            final Hologram holo = HologramAPI.createHologram(chest.getLocation().clone().add(1.0, 1.3, 0.5), "§a30s");
            holo.spawn();
            new BukkitRunnable() {
                int time = 30;
                
                public void run() {
                    --this.time;
                    if (this.time == 0) {
                        this.cancel();
                        holo.despawn();
                        where.getBlock().setType(Material.AIR);
                        Bukkit.broadcastMessage("§7[§a§lTimeBomb§7] §c§l" + name + "'s §fCorpse has exploded!");
                        if (PlayerDeathListener.this.gameManager.getCurrentBorder() > 100) {
                            where.getWorld().createExplosion(where.getBlockX() + 0.5, (double)(where.getBlockY() + 1), where.getBlockZ() + 0.5, 5.0f, false, true);
                        }
                        else {
                            chest.getLocation().getBlock().setType(Material.AIR);
                            chest.getLocation().add(1.0, 0.0, 0.0).getBlock().setType(Material.AIR);
                        }
                        where.getWorld().strikeLightning(where);
                    }
                    else if (this.time > 20) {
                        holo.setText("§a" + this.time + "s");
                    }
                    else if (this.time > 10) {
                        holo.setText("§e" + this.time + "s");
                    }
                    else if (this.time > 0) {
                        holo.setText("§c" + this.time + "s");
                    }
                }
            }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
        }
        else {
            this.clearDrops = false;
        }
        if (ScenarioManager.getInstance().getScenarioExact("ExtraInventory").isEnabled()) {
            where.add(1.0, 1.0, 0.0).getBlock().setType(Material.CHEST);
            final Chest chest = (Chest)where.add(1.0, 1.0, 0.0).getBlock().getState();
            chest.getInventory().setContents(victim.getEnderChest().getContents());
        }
    }
    
    private void Cohete(final Player player) {
        final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
        final FireworkMeta fwm = fw.getFireworkMeta();
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        fwm.addEffect(builder.flicker(true).withColor(Color.BLUE).build());
        fwm.addEffect(builder.trail(true).build());
        fwm.addEffect(builder.withFade(Color.ORANGE).build());
        fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
        fwm.setPower(2);
        fw.setFireworkMeta(fwm);
    }
}