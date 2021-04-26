package me.shir.uhcp.listeners;

import me.shir.uhcp.game.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.scenarios.*;
import com.nametagedit.plugin.*;
import java.text.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import me.shir.uhcp.teams.*;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.server.v1_7_R4.Material;

import org.bukkit.inventory.*;
import org.bukkit.potion.*;
import org.spigotmc.event.entity.*;
import org.bukkit.command.*;
import org.bukkit.event.block.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import org.bukkit.block.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.enchantments.*;
import org.bukkit.projectiles.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.event.player.*;
import me.shir.uhcp.cmds.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import me.shir.uhcp.util.*;
import java.util.*;
import org.bukkit.event.weather.*;
import org.bukkit.event.world.*;
import org.bukkit.event.entity.*;

public class Listeners implements Listener
{
    private final GameManager gameManager;
    public static ArrayList<UUID> innoclean;
    double EXP_MULTIPLIER_LOOTING_PER_LEVEL;
    double EXP_MULTIPLIER_FORTUNE_PER_LEVEL;
    double EXP_MULTIPLIER_GENERAL;
    double EXP_MULTIPLIER_FISHING;
    double EXP_MULTIPLIER_LUCK_PER_LEVEL;
    double EXP_MULTIPLIER_SMELTING;
    public static ArrayList<UUID> aylmao;
    public static int donotdisturbtime;
    private final Inventory alive;
    
    static {
        Listeners.innoclean = new ArrayList<UUID>();
        Listeners.aylmao = new ArrayList<UUID>();
        Listeners.donotdisturbtime = 15;
    }
    
    public Listeners() {
        this.gameManager = GameManager.getGameManager();
        this.EXP_MULTIPLIER_LOOTING_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_FORTUNE_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_GENERAL = 3.0;
        this.EXP_MULTIPLIER_FISHING = 3.0;
        this.EXP_MULTIPLIER_LUCK_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_SMELTING = 3.0;
        this.alive = Bukkit.createInventory((InventoryHolder)null, 54, "§aPlayers Alive:");
    }
    
    @EventHandler
    public void onPlayerPortalEvent(final PlayerPortalEvent e) {
        if (!ConfigBooleans.NETHER.isEnabled()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§c¡El nether esta desactivado!");
        }
        if (this.gameManager.getCurrentBorder() <= 500) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can only go to the nether before the 500 border shrink!");
        }
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId());
        if (this.gameManager.isStatsEnabled() && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
            uhcPlayer.addNetherE();
        }
        if (!e.isCancelled() && e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (e.getFrom().getWorld().getName().equalsIgnoreCase(this.gameManager.getUhcWorldName())) {
                final Player p = e.getPlayer();
                final double x = p.getLocation().getX() / 8.0;
                final double y = p.getLocation().getY();
                final double z = p.getLocation().getZ() / 8.0;
                e.setTo(e.getPortalTravelAgent().findOrCreate(new Location(Bukkit.getServer().getWorld(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether"), x, y, z)));
            }
            else if (e.getFrom().getWorld().getName().equalsIgnoreCase(String.valueOf(this.gameManager.getUhcWorldName()) + "_nether")) {
                final Player p = e.getPlayer();
                final double x = p.getLocation().getX() * 8.0;
                final double y = p.getLocation().getY();
                final double z = p.getLocation().getZ() * 8.0;
                e.setTo(e.getPortalTravelAgent().findOrCreate(new Location(Bukkit.getServer().getWorld(this.gameManager.getUhcWorldName()), x, y, z)));
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (e.getEntity().getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) || this.gameManager.isScattering()) {
            e.setCancelled(true);
        }
        if (e.getEntity() instanceof Player) {
            if (ScenarioManager.getInstance().getScenarioExact("Fireless").isEnabled() && (e.getCause().equals((Object)EntityDamageEvent.DamageCause.LAVA) || e.getCause().equals((Object)EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals((Object)EntityDamageEvent.DamageCause.FIRE_TICK))) {
                e.setCancelled(true);
            }
            if (PlayerManager.getPlayerManager().getUHCPlayer(e.getEntity().getUniqueId()).isSpectating()) {
                e.setCancelled(true);
            }
            if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.FALL) && ScenarioManager.getInstance().getScenarioExact("NoFallDamage").isEnabled()) {
                e.setCancelled(true);
            }
            if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.LIGHTNING)) {
                e.setCancelled(true);
            }
            if (ScenarioManager.getInstance().getScenarioExact("NoClean").isEnabled() && Listeners.innoclean.contains(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
            if (FreezeCMD.freeze.contains(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
            if (this.gameManager.isScattering()) {
                e.setCancelled(true);
            }
            if (StartGameCMD.invencibility) {
                e.setCancelled(true);
            }
            if (RespawnCMD.respawned.contains(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onGamemodeChange(final PlayerGameModeChangeEvent e) {
        if (PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId()).isSpectating() && e.getNewGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (ScenarioManager.getInstance().getScenarioExact("NightmareMode").isEnabled() && event.getEntity() instanceof Creeper) {
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Silverfish.class);
        }
    }
    
    @EventHandler
    public void onDamageNoClean(final EntityDamageByEntityEvent e) {
        if (ScenarioManager.getInstance().getScenarioExact("NoClean").isEnabled() && e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (Listeners.innoclean.contains(e.getDamager().getUniqueId())) {
                Listeners.innoclean.remove(e.getDamager().getUniqueId());
                if (TeamManager.getInstance().isTeamsEnabled()) {
                    NametagEdit.getApi().setNametag(((Player)e.getDamager()).getPlayer(), TeamManager.getInstance().getTeam((OfflinePlayer)((Player)e.getDamager()).getPlayer()).getTeamColor(), "");
                    ((Player)e.getDamager()).setPlayerListName(String.valueOf(TeamManager.getInstance().getTeam((OfflinePlayer)((Player)e.getDamager()).getPlayer()).getTeamColor()) + ((Player)e.getDamager()).getName());
                }
                else {
                    NametagEdit.getApi().clearNametag(((Player)e.getDamager()).getPlayer());
                    ((Player)e.getDamager()).setPlayerListName("§f" + ((Player)e.getDamager()).getName());
                }
                ((Player)e.getDamager()).sendMessage("§c§lNoClean §8» §eYou have lost your protection!");
            }
            if (Listeners.innoclean.contains(e.getEntity().getUniqueId())) {
                ((Player)e.getDamager()).sendMessage("§c§lNoClean §8» §eYou can't hurt this player!");
            }
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (FreezeCMD.freeze.contains(e.getDamager().getUniqueId())) {
                e.setCancelled(true);
            }
            if (FreezeCMD.freeze.contains(e.getEntity().getUniqueId())) {
                ((Player)e.getDamager()).sendMessage("§a§lFreeze §8» §eYou can't hurt frozen players!");
            }
        }
    }
    
    @EventHandler
    public void onDamageAlert(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player && !e.isCancelled()) {
            final DecimalFormat df = new DecimalFormat("#.#");
            final double health = ((Player)e.getDamager()).getHealthScale() / 2.0;
            final double health2 = ((Player)e.getEntity()).getHealthScale() / 2.0;
            final String a = df.format(health);
            final String f = df.format(health2);
            for (final Player players : this.gameManager.getModerators()) {
                if (players != null && !ToggleAlerts.pvpalerts.contains(players.getUniqueId()) && !PlayerManager.getPlayerManager().getUHCPlayer(e.getDamager().getUniqueId()).isSpectating()) {
                    final TextComponent ala = new TextComponent("");
                    TextComponent json = new TextComponent("");
                    json = new TextComponent("§c§lPvP §8» §a" + ((Player)e.getDamager()).getName() + "§7[" + a + "§4\u2764§7] §fis fighting against §c" + ((Player)e.getEntity()).getName() + "§7[" + f + "§4\u2764§7]");
                    json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tele " + ((Player)e.getDamager()).getName()));
                    json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick to teleport!").create()));
                    ala.addExtra((BaseComponent)json);
                    players.spigot().sendMessage((BaseComponent)ala);
                }
            }
        }
    }
    
    @EventHandler
    public void onDamageSpec(final EntityDamageByEntityEvent e) {
        if (!ConfigBooleans.ENDERPEARL_DAMAGE.isEnabled() && e.getDamager().getType().equals((Object)EntityType.ENDER_PEARL)) {
            e.setCancelled(true);
        }
        if (e.getDamager() instanceof Player && PlayerManager.getPlayerManager().getUHCPlayer(e.getDamager().getUniqueId()).isSpectating()) {
            e.setCancelled(true);
        }
        if (!TeamManager.getInstance().canDamageTeamMembers() && e.getDamager() instanceof Player && e.getEntity() instanceof Player && TeamManager.getInstance().isTeamsEnabled()) {
            final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)e.getDamager());
            if (team.getPlayers().contains(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
        }
        if (!e.isCancelled() && e.getDamager() instanceof Arrow && e.getEntity() instanceof Player && ((Arrow)e.getDamager()).getShooter() instanceof Player) {
            final Player victim = (Player)e.getEntity();
            final Player shooter = (Player)((Arrow)e.getDamager()).getShooter();
            final String health = new DecimalFormat("#.#").format(victim.getHealthScale() / 2.0);
            final EntityLiving eplayer = (EntityLiving)((CraftPlayer)victim).getHandle();
            final double ab = eplayer.getAbsorptionHearts() / 2.0f;
            final String abs = new DecimalFormat("#.#").format(ab);
            if (eplayer.getAbsorptionHearts() > 0.0f) {
                shooter.sendMessage(String.valueOf(this.gameManager.getPrefix()) + org.bukkit.ChatColor.AQUA + victim.getName() + "'s Health: §a" + health + "§4 \u2764 §e" + abs + " \u2764");
            }
            shooter.sendMessage(String.valueOf(this.gameManager.getPrefix()) + org.bukkit.ChatColor.AQUA + victim.getName() + "'s Health: §a" + health + "§4 \u2764");
            if (ScenarioManager.getInstance().getScenarioExact("Switcheroo").isEnabled()) {
                final Location loc = shooter.getLocation();
                final Location loc2 = victim.getLocation();
                shooter.teleport(loc2);
                victim.teleport(loc);
                shooter.sendMessage("§aSwitched locations!");
                victim.sendMessage("§aSwitched locations!");
            }
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(shooter.getUniqueId());
            if (this.gameManager.isStatsEnabled() && uhcPlayer != null && !e.isCancelled() && uhcPlayer.isPlayerAlive()) {
                uhcPlayer.addArrowHit();
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent e) {
        if (PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId()).isSpectating()) {
            final Player p = e.getPlayer();
            final ItemStack item = p.getItemInHand();
            final Player target = (Player)e.getRightClicked();
            e.setCancelled(true);
            if (e.getRightClicked() instanceof Player) {
                if (item == null) {
                    return;
                }
                if ((p.hasPermission("plover.invsee") || p.hasPermission("uhc.host")) && item.getType() == Material.BOOK) {
                    p.performCommand("invsee " + target.getName());
                }
                if (this.gameManager.getModerators().contains(p) && item.getType() == Material.PACKED_ICE) {
                    p.performCommand("freeze " + target.getName());
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerLevelChangeEvent(final PlayerLevelChangeEvent e) {
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId());
        if (e.getNewLevel() > e.getOldLevel() && this.gameManager.isStatsEnabled() && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
            uhcPlayer.addXPLevel();
        }
    }
    
    @EventHandler
    public void onPlayerDropItemEvent(final PlayerDropItemEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && !player.hasPermission("uhc.spawnprotection.bypass")) {
            e.setCancelled(true);
        }
        if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityShootBowEvent(final EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(((Player)e.getEntity()).getUniqueId());
            if (this.gameManager.isStatsEnabled() && uhcPlayer != null && !e.isCancelled() && uhcPlayer.isPlayerAlive()) {
                uhcPlayer.addArrowShot();
            }
        }
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (ScenarioManager.getInstance().getScenarioExact("NightmareMode").isEnabled()) {
            if (event.getEntity() instanceof Zombie) {
                if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) && new Random().nextInt(99) + 1 >= 50) {
                    event.setCancelled(true);
                }
                return;
            }
            if (event.getEntity() instanceof Enderman && new Random().nextInt(99) + 1 <= 10) {
                event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)Blaze.class);
            }
        }
    }
    
    @EventHandler
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        if (ScenarioManager.getInstance().getScenarioExact("NightmareMode").isEnabled()) {
            if (event.getEntity() instanceof Witch) {
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 4));
            }
            else if (event.getEntity() instanceof Spider) {
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 3));
            }
            else if (event.getEntity() instanceof Zombie) {
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1726272000, 1));
            }
            else if (event.getEntity() instanceof Creeper) {
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 1));
            }
        }
    }
    
    @EventHandler
    public void onPlayerPickupItemEvent(final PlayerPickupItemEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && !player.hasPermission("uhc.host")) {
            e.setCancelled(true);
        }
        if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerCommandBackEvent(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] split = e.getMessage().toLowerCase().split(" ");
        final String check = split[0].toLowerCase();
        if (check.contains("/back")) {
            e.setCancelled(true);
            p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + ("§cUnknown command, try using /helpop for help."));
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final Location x = e.getTo();
        final Location y = e.getFrom();
        if ((x.getBlockX() != y.getBlockX() || x.getBlockZ() != y.getBlockZ()) && FreezeCMD.freeze.contains(p.getUniqueId())) {
            e.setTo(e.getFrom());
        }
    }
    
    @EventHandler
    public void onEntityRegainHealthEvent(final EntityRegainHealthEvent e) {
        if (!ConfigBooleans.NATURAL_REGENERATION.isEnabled() && e.getEntity().getType() == EntityType.PLAYER && e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            e.setCancelled(true);
        }
        if (!ConfigBooleans.HORSE_HEALING.isEnabled() && e.getEntity().getType() == EntityType.HORSE) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityTameEvent(final EntityTameEvent e) {
        if (e.getEntity() instanceof Horse) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(((Player)e.getOwner()).getUniqueId());
            if (this.gameManager.isStatsEnabled() && uhcPlayer != null && !e.isCancelled() && uhcPlayer.isPlayerAlive()) {
                uhcPlayer.addHorsesTamed();
            }
        }
    }
    
    @EventHandler
    public void onEntityMountEvent(final EntityMountEvent e) {
        if (!ConfigBooleans.HORSES.isEnabled() && e.getMount().getType() == EntityType.HORSE) {
            e.setCancelled(true);
        }
        if (e.getEntity().getPassenger() instanceof Player) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getEntity().getPassenger().getUniqueId());
            if (uhcPlayer.isSpectating() || !uhcPlayer.isPlayerAlive()) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onVehicleEnterEvent(final VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getEntered().getUniqueId());
            if (uhcPlayer.isSpectating() || !uhcPlayer.isPlayerAlive()) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onGameStopEvent(final GameStopEvent e) {
        this.gameManager.setRestarted(false);
        this.gameManager.setWorldWasUsed(true);
        this.gameManager.setWasGenerated(false);
    }
    
    @EventHandler
    public void onCraftItemEvent(final CraftItemEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            final Player player = (Player)e.getWhoClicked();
            if (!ConfigBooleans.GOD_APPLES.isEnabled() && e.getCurrentItem().getType().equals((Object)Material.GOLDEN_APPLE) && e.getCurrentItem().getDurability() == 1) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cGod Apples are currently disabled!");
            }
            if (!ConfigBooleans.GOLDEN_HEADS.isEnabled() && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Golden Head")) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cGolden Heads are currently disabled!");
            }
            if (!ConfigBooleans.STRENGTH_1.isEnabled() && e.getCurrentItem().getType().equals((Object)Material.BLAZE_POWDER)) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cStrength 1 potions are currently disabled!");
            }
            if (ScenarioManager.getInstance().getScenarioExact("Bowless").isEnabled() && e.getCurrentItem().getType().equals((Object)Material.BOW)) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cBows are currently disabled!");
            }
            if (ScenarioManager.getInstance().getScenarioExact("Rodless").isEnabled() && e.getCurrentItem().getType().equals((Object)Material.FISHING_ROD)) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cFishing rods are currently disabled!");
            }
            if (ScenarioManager.getInstance().getScenarioExact("GoneFishing").isEnabled() && e.getCurrentItem().getType().equals((Object)Material.ENCHANTMENT_TABLE)) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cEnchantment tables are currently disabled!");
            }
            if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                if (e.getCurrentItem().getType().equals((Object)Material.ENCHANTMENT_TABLE)) {
                    e.setCancelled(true);
                    player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cEnchantment tables are currently disabled!");
                }
                if (e.getCurrentItem().getType().equals((Object)Material.ANVIL)) {
                    e.setCancelled(true);
                    player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cAnvils are currently disabled!");
                }
                if (e.getCurrentItem().getType().equals((Object)Material.GOLDEN_APPLE)) {
                    e.setCancelled(true);
                    player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cGolden Apples are currently disabled!");
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteractEvent(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final ItemStack item = player.getItemInHand();
        if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType().equals((Object)Material.SKULL_ITEM)) {
                    player.openInventory(this.getAlive());
                }
                else if (item.getType().equals((Object)Material.COMPASS)) {
                    this.randomPlayer(player);
                }
                else if (item.getType().equals((Object)Material.DIAMOND_ORE)) {
                    e.getPlayer().sendMessage("§7§m----------------");
                    e.getPlayer().sendMessage(this.gameManager.getMainColor() + "           §lTop 10 Miners:           ");
                    e.getPlayer().sendMessage(" ");
                    this.getTopDiamonds(10, (CommandSender)e.getPlayer());
                    e.getPlayer().sendMessage("§7§m----------------");
                }
                else if (item.getType().equals((Object)Material.NETHERRACK)) {
                    e.getPlayer().chat("/playersinnether");
                }
                else if (item.getType().equals((Object)Material.BEACON)) {
                    e.getPlayer().chat("/staffonlinegui");
                }
                else if (item.getType().equals((Object)Material.REDSTONE_COMPARATOR)) {
                    e.getPlayer().chat("/xalertsgui");
                }
            }
        }
        if (FreezeCMD.freeze.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
        if (item != null) {
            if (ScenarioManager.getInstance().getScenarioExact("Bowless").isEnabled() && item.getType() == Material.BOW) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cBows are currently disabled!");
            }
            if (ScenarioManager.getInstance().getScenarioExact("Soup").isEnabled() && e.getItem().getType() == Material.MUSHROOM_SOUP && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
                player.getItemInHand().setType(Material.BOWL);
                if (player.getHealthScale() > 16.0 && player.getHealthScale() <= 20.0) {
                    player.setHealth(20);
                }
                else {
                    player.setHealth(player.getHealthScale() + 4.0);
                }
            }
            if (item.getType() == Material.POTION) {
                if (!ConfigBooleans.INVISIBILITY_POTIONS.isEnabled()) {
                    switch (item.getDurability()) {
                        case 8238:
                        case 8270:
                        case 16430:
                        case 16462: {
                            e.setCancelled(true);
                            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cInvisibility potions are currently disabled!");
                            item.setDurability((short)0);
                            break;
                        }
                    }
                }
                if (!ConfigBooleans.STRENGTH_2.isEnabled()) {
                    switch (item.getDurability()) {
                        case 8233:
                        case 16425: {
                            e.setCancelled(true);
                            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cStrength 2 potions are currently disabled!");
                            item.setDurability((short)0);
                            break;
                        }
                    }
                }
                if (!ConfigBooleans.SPEED.isEnabled()) {
                    switch (item.getDurability()) {
                        case 8194:
                        case 8226:
                        case 16386:
                        case 16418: {
                            e.setCancelled(true);
                            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cSpeed potions are currently disabled!");
                            item.setDurability((short)0);
                            break;
                        }
                    }
                }
                if (!ConfigBooleans.POISON.isEnabled()) {
                    switch (item.getDurability()) {
                        case 8196:
                        case 8228:
                        case 16388:
                        case 16420: {
                            e.setCancelled(true);
                            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cPoison potions are currently disabled!");
                            item.setDurability((short)0);
                            break;
                        }
                    }
                }
            }
            if (!this.gameManager.isGameRunning()) {
                if (item.getType() == Material.PAPER) {
                    player.chat("/config");
                }
                if (item.getType() == Material.FEATHER) {
                    player.chat("/uhcmanage");
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerCommandEvent(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String[] split = e.getMessage().toLowerCase().split(" ");
        final String check = split[0].toLowerCase();
        if (p.hasPermission("uhc.host")) {
            if (check.contains("/gamemode") || check.contains("/gm") || check.contains("/gmc")) {
                Player[] onlinePlayers;
                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                    final Player staffsuperior = onlinePlayers[i];
                    if (staffsuperior.hasPermission("uhc.host") && !ToggleAlerts.abusealerts.contains(staffsuperior.getUniqueId())) {
                        final TextComponent ala = new TextComponent("");
                        TextComponent json = new TextComponent("");
                        json = new TextComponent("§c§lAbuse §8» §b" + p.getName() + " §fis trying to §c§lABUSE! §7(" + check + "§7)");
                        json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tele " + p.getName()));
                        json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport!").color(ChatColor.GREEN).create()));
                        ala.addExtra((BaseComponent)json);
                        staffsuperior.spigot().sendMessage((BaseComponent)ala);
                    }
                }
                final StringBuilder hoMsg = new StringBuilder();
                for (int x = 1; x < split.length; ++x) {
                    hoMsg.append(String.valueOf(split[x]) + " ");
                }
            }
            if (check.contains("/give")) {
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player staffsuperior = onlinePlayers2[j];
                    if (staffsuperior.hasPermission("uhc.host") && !ToggleAlerts.abusealerts.contains(staffsuperior.getUniqueId())) {
                        final TextComponent ala = new TextComponent("");
                        TextComponent json = new TextComponent("");
                        json = new TextComponent("§c§lAbuse §8» §b" + p.getName() + " §fis trying to §c§lABUSE! §7(" + check + "§7)");
                        json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tele " + p.getName()));
                        json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick to teleport!").create()));
                        ala.addExtra((BaseComponent)json);
                        staffsuperior.spigot().sendMessage((BaseComponent)ala);
                    }
                }
                final StringBuilder hoMsg = new StringBuilder();
                for (int x = 1; x < split.length; ++x) {
                    hoMsg.append(String.valueOf(split[x]) + " ");
                }
            }
        }
    }
    
    @EventHandler
    public void onLeavesDecayEvent(final LeavesDecayEvent e) {
        if ((ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled() || ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled() || ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled() || ScenarioManager.getInstance().getScenarioExact("Vanilla+").isEnabled()) && Math.random() * 100.0 <= 2.0) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.APPLE));
        }
        if (ScenarioManager.getInstance().getScenarioExact("LuckyLeaves").isEnabled() && Math.random() * 100.0 <= 0.5) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLDEN_APPLE));
        }
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent paramPlayerInteractEvent) {
        if (paramPlayerInteractEvent.isCancelled()) {
            return;
        }
        if (ScenarioManager.getInstance().getScenarioExact("Popeye").isEnabled()) {
            return;
        }
        if (!paramPlayerInteractEvent.getAction().name().contains("RIGHT")) {
            return;
        }
        if (paramPlayerInteractEvent.getPlayer().getInventory().getItemInHand().getType().equals((Object)Material.INK_SACK) && paramPlayerInteractEvent.getPlayer().getInventory().getItemInHand().getDurability() == 2) {
            paramPlayerInteractEvent.getPlayer().getInventory().remove(new ItemStack(Material.INK_SACK, 1, (short)2));
            paramPlayerInteractEvent.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
        }
    }
    
    @EventHandler
    public void onPlayerItemConsumeEvent(final PlayerItemConsumeEvent e) {
        if (e.getItem() != null && e.getItem().getType().equals((Object)Material.GOLDEN_APPLE)) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId());
            if (!e.isCancelled() && this.gameManager.isStatsEnabled() && uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                if (e.getItem().getItemMeta() != null && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Golden Head")) {
                    uhcPlayer.addGoldenHeadsEaten();
                    uhcPlayer.addHeartsHealed(4);
                }
                else {
                    uhcPlayer.addGoldenApplesEaten();
                    uhcPlayer.addHeartsHealed(2);
                }
            }
            if (e.getItem().getItemMeta() != null && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Golden Head")) {
                e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            }
            if (!ConfigBooleans.ABSORPTION.isEnabled()) {
                new BukkitRunnable() {
                    public void run() {
                        e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                    }
                }.runTaskLaterAsynchronously((Plugin)VenixUHC.getInstance(), 3L);
            }
        }
    }
    
    @EventHandler
    public void onSpecMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final Location loc = p.getLocation();
        final World w = loc.getWorld();
        final Block b = w.getBlockAt(loc);
        if (PlayerManager.getPlayerManager().getUHCPlayer(p.getUniqueId()).isSpectating() && !this.gameManager.getModerators().contains(p)) {
            if (!p.getWorld().equals(Bukkit.getWorld("uhcworld"))) {
                return;
            }
            if (p.hasPermission("uhc.vip")) {
                if (b.getZ() > 99) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), 97.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() > 99) {
                    p.teleport(new Location(p.getWorld(), 97.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getZ() < -99) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), -97.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() < -99) {
                    p.teleport(new Location(p.getWorld(), -97.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getY() < 40) {
                    p.teleport(new Location(p.getWorld(), 0.0, 100.0, 0.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this level.");
                }
            }
            if (p.hasPermission("donator+.spec")) {
                if (b.getZ() > 499) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), 497.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() > 499) {
                    p.teleport(new Location(p.getWorld(), 497.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getZ() < -499) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), -497.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() < -499) {
                    p.teleport(new Location(p.getWorld(), -497.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getY() < 40) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), 100.0, (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this level.");
                }
            }
            if (p.hasPermission("Mayor.spec")) {
                if (b.getZ() > 999) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), 997.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() > 999) {
                    p.teleport(new Location(p.getWorld(), 997.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getZ() < -999) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), -997.0));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getX() < -999) {
                    p.teleport(new Location(p.getWorld(), -997.0, (double)loc.getBlockY(), (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this coords.");
                }
                if (b.getY() < 40) {
                    p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), 100.0, (double)loc.getBlockZ()));
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this level.");
                }
            }
            if (p.hasPermission("Titan.spec") && b.getY() < 20) {
                p.teleport(new Location(p.getWorld(), (double)loc.getBlockX(), 100.0, (double)loc.getBlockZ()));
                p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou can't spec at this level.");
            }
        }
    }
    
    @EventHandler
    public void onXPTripleXP(final PlayerExpChangeEvent e) {
        if (!ScenarioManager.getInstance().getScenarioExact("TripleXP").isEnabled()) {
            return;
        }
        final double amount = e.getAmount();
        if (amount > 0.0) {
            final int result = (int)Math.ceil(amount * this.EXP_MULTIPLIER_GENERAL);
            e.setAmount(result);
        }
    }
    
    @EventHandler
    public void onDismountScatter(final VehicleExitEvent e) {
        if (e.getExited() instanceof Player && this.gameManager.isScattering()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFishTripleXP(final PlayerFishEvent e) {
        if (!ScenarioManager.getInstance().getScenarioExact("TripleXP").isEnabled()) {
            return;
        }
        double amount = e.getExpToDrop();
        if (amount > 0.0) {
            amount = Math.ceil(amount * this.EXP_MULTIPLIER_FISHING);
            final ProjectileSource projectileSource = (ProjectileSource)e.getHook().getShooter();
            if (projectileSource instanceof Player) {
                final ItemStack stack = ((Player)projectileSource).getItemInHand();
                final int enchantmentLevel = stack.getEnchantmentLevel(Enchantment.LUCK);
                if (enchantmentLevel > 0L) {
                    amount = Math.ceil(amount * (enchantmentLevel * this.EXP_MULTIPLIER_LUCK_PER_LEVEL));
                }
            }
            e.setExpToDrop((int)amount);
        }
    }
    
    @EventHandler
    public void onFishFreeze(final PlayerFishEvent e) {
        final Player player = e.getPlayer();
        if (e.getCaught() instanceof Player && player.getItemInHand().getType() == Material.FISHING_ROD) {
            if (FreezeCMD.freeze.contains(e.getCaught().getUniqueId())) {
                e.setCancelled(true);
            }
            if (FreezeCMD.freeze.contains(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onCaughtEvent(final PlayerFishEvent e) {
        final Player p = e.getPlayer();
        if (e.getCaught() instanceof Player && p.getItemInHand().getType() == Material.FISHING_ROD) {
            if (e.isCancelled()) {
                return;
            }
            final String health = new DecimalFormat("#.#").format(((Player)e.getCaught()).getHealthScale() / 2.0);
            final EntityLiving eplayer = (EntityLiving)((CraftPlayer)e.getCaught()).getHandle();
            final double ab = eplayer.getAbsorptionHearts() / 2.0f;
            final String abs = new DecimalFormat("#.#").format(ab);
            if (eplayer.getAbsorptionHearts() > 0.0f) {
                p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + org.bukkit.ChatColor.AQUA + ((Player)e.getCaught()).getName() + "'s Health: " + health + "§4 \u2764 §e" + abs + " \u2764");
            }
        }
    }
    
    @EventHandler
    public void onFurnaceTripleXP(final FurnaceExtractEvent e) {
        if (!ScenarioManager.getInstance().getScenarioExact("TripleXP").isEnabled()) {
            return;
        }
        final double amount = e.getExpToDrop();
        if (amount > 0.0) {
            final double multiplier = this.EXP_MULTIPLIER_SMELTING;
            final int result = (int)Math.ceil(amount * multiplier);
            e.setExpToDrop(result);
        }
    }
    
    @EventHandler
    public void onGameWinEvent(final GameWinListener e) {
        final int wins = e.getUHCPlayer().getWins() + 1;
        final Player player = Bukkit.getServer().getPlayer(e.getWinner());
        final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
        final FireworkMeta fwm = fw.getFireworkMeta();
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
        fwm.addEffect(builder.trail(true).build());
        fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
        fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
        fwm.setPower(2);
        fw.setFireworkMeta(fwm);
        new BukkitRunnable() {
            int i = 6;
            
            public void run() {
                --this.i;
                if (this.i == 6) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 5) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 4) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 3) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 2) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 1) {
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                }
                else if (this.i == 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
        final String tweetwinner = "tweet Congratulations for Winning The Game!||- Winner: " + e.getWinner() + "|- Winner Kills: " + e.getUHCPlayer().getKills() + "|- Wins of " + e.getWinner() + ": [" + wins + "]||Thanks for Support our Games!";
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§fCongratulations to §b" + e.getWinner() + " §ffor winning the UHC!, Thanks for Support our games!");
        }
        if (GameManager.getGameManager().isStatsEnabled()) {
            e.getUHCPlayer().addWin();
            Player[] onlinePlayers2;
            for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                final Player p = onlinePlayers2[j];
                p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + this.gameManager.getMainColor() + "Saving stats to database...");
            }
            new BukkitRunnable() {
                public void run() {
                    for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
                        uhcPlayers.saveData();
                    }
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player p = onlinePlayers[i];
                        p.sendMessage(String.valueOf(Listeners.this.gameManager.getPrefix()) + "§aSuccessfully saved stats!");
                    }
                }
            }.runTaskAsynchronously((Plugin)VenixUHC.getInstance());
        }
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), tweetwinner);
    }
    
    @EventHandler
    public void onGameWinTeamEvent(final GameWinTeamListener e) {
        String names = "";
        for (final UUID uuid : e.getUUIDs()) {
            names = String.valueOf(names) + Bukkit.getOfflinePlayer(uuid).getName() + " ";
        }
        int kills = 0;
        int teamid = 0;
        for (final UUID winners : e.getUUIDs()) {
            final OfflinePlayer team = Bukkit.getOfflinePlayer(winners);
            kills = TeamManager.getInstance().getTeam(team).getKills();
            teamid = TeamManager.getInstance().getTeam(team).getId();
        }
        for (final UUID abc : e.getUUIDs()) {
            for (final UHCPlayer lmao : PlayerManager.getPlayerManager().uhcPlayersSet(e.getUUIDs())) {
                if (lmao.isPlayerAlive()) {
                    final Player player = Bukkit.getPlayer(lmao.getName());
                    final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect.Builder builder = FireworkEffect.builder();
                    fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                    fwm.addEffect(builder.trail(true).build());
                    fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                    fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                    fwm.setPower(2);
                    fw.setFireworkMeta(fwm);
                    new BukkitRunnable() {
                        int i = 6;
                        
                        public void run() {
                            --this.i;
                            if (this.i == 6) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 5) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 4) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 3) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 2) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 1) {
                                final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), (Class)Firework.class);
                                final FireworkMeta fwm = fw.getFireworkMeta();
                                final FireworkEffect.Builder builder = FireworkEffect.builder();
                                fwm.addEffect(builder.flicker(true).withColor(org.bukkit.Color.BLUE).build());
                                fwm.addEffect(builder.trail(true).build());
                                fwm.addEffect(builder.withFade(org.bukkit.Color.ORANGE).build());
                                fwm.addEffect(builder.with(FireworkEffect.Type.CREEPER).build());
                                fwm.setPower(2);
                                fw.setFireworkMeta(fwm);
                            }
                            else if (this.i == 0) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
                }
            }
        }
        final String tweetwinner = "tweet Congratulations To the Team #" + teamid + " For Win!" + "||- Winners: " + names + "|- Team Kills: " + kills + "||Thanks for Support our Games!";
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§fCongratulations to §b" + names + " §ffor winning the UHC!");
        }
        Player[] onlinePlayers2;
        for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
            final Player players = onlinePlayers2[j];
            players.playSound(players.getLocation(), Sound.WITHER_HURT, 2.0f, 1.0f);
        }
        if (this.gameManager.isStatsEnabled()) {
            for (final UHCPlayer uhcPlayer : PlayerManager.getPlayerManager().uhcPlayersSet(e.getUUIDs())) {
                uhcPlayer.addWin();
            }
            Player[] onlinePlayers3;
            for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                final Player p = onlinePlayers3[k];
                p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + this.gameManager.getMainColor() + "Saving stats to database...");
            }
            new BukkitRunnable() {
                public void run() {
                    for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
                        uhcPlayers.saveData();
                    }
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player p = onlinePlayers[i];
                        p.sendMessage(String.valueOf(Listeners.this.gameManager.getPrefix()) + "§aSuccessfully saved stats!");
                    }
                }
            }.runTaskAsynchronously((Plugin)VenixUHC.getInstance());
        }
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), tweetwinner);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEventHIGH(final AsyncPlayerChatEvent e) {
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(e.getPlayer().getUniqueId());
        final OfflinePlayer player = Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId());
        if (uhcPlayer.isSpectating() && uhcPlayer.didUseCommand()) {
            e.setCancelled(true);
            this.sendSpecMessage(e.getFormat().replace("%1$s", String.valueOf(this.gameManager.getSpectatorPrefix()) + e.getPlayer().getName()).replace("%2$s", e.getMessage()));
            uhcPlayer.setUsedCommand(false);
        }
        else if (uhcPlayer.hasTeamChatToggled()) {
            final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)e.getPlayer());
            e.setCancelled(true);
            team.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + GameManager.getGameManager().getSecondaryColor() + uhcPlayer.getName() + ": " + GameManager.getGameManager().getMainColor() + e.getMessage());
        }
        else if (this.gameManager.getHostName().equalsIgnoreCase(e.getPlayer().getName())) {
            e.setFormat(e.getFormat().replace("%1$s", String.valueOf(this.gameManager.getHostPrefix()) + e.getPlayer().getDisplayName()));
            if (StaffChatCMD.instaffchat.contains(e.getPlayer().getUniqueId())) {
                Player[] onlinePlayers;
                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                    final Player staff = onlinePlayers[i];
                    if (staff.hasPermission("uhc.host")) {
                        e.setCancelled(true);
                        staff.sendMessage("§b(Staff) " + e.getPlayer().getName() + ": §f" + e.getMessage());
                    }
                }
            }
        }
        else if (this.gameManager.getModerators().contains(e.getPlayer())) {
            e.setFormat(e.getFormat().replace("%1$s", String.valueOf(this.gameManager.getModeratorPrefix()) + e.getPlayer().getDisplayName()));
            if (StaffChatCMD.instaffchat.contains(e.getPlayer().getUniqueId())) {
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player staff = onlinePlayers2[j];
                    if (staff.hasPermission("uhc.host")) {
                        e.setCancelled(true);
                        staff.sendMessage("§b(Staff) " + e.getPlayer().getName() + ": §f" + e.getMessage());
                    }
                }
            }
        }
        else if (uhcPlayer.isSpectating()) {
            e.setCancelled(true);
            this.sendSpecMessage(e.getFormat().replace("%1$s", String.valueOf(this.gameManager.getSpectatorPrefix()) + e.getPlayer().getDisplayName()).replace("%2$s", e.getMessage()));
        }
        else {
            if (TeamManager.getInstance().isTeamsEnabled()) {
                try {
                    final int team2 = TeamManager.getInstance().getTeam(player).getId();
                    e.setFormat(e.getFormat().replace("%1$s", String.valueOf(TeamManager.getInstance().getTeam(player).getTeamColor()) + "[Team #" + team2 + "]" + e.getPlayer().getDisplayName()));
                    return;
                }
                catch (NullPointerException e2) {
                    return;
                }
            }
            if (FreezeCMD.freeze.contains(player.getUniqueId())) {
                e.setFormat(e.getFormat().replace("%1$s", "§e[Frozen] " + e.getPlayer().getDisplayName()));
            }
        }
    }
    
    private void sendSpecMessage(final String message) {
        for (final UHCPlayer uhcPlayer : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayer.isSpectating()) {
                final Player player = Bukkit.getServer().getPlayer(uhcPlayer.getName());
                if (player == null) {
                    continue;
                }
                player.sendMessage(message);
            }
        }
    }
    
    private Inventory getAlive() {
        this.alive.clear();
        for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayers.isPlayerAlive()) {
                final Player player = Bukkit.getServer().getPlayer(uhcPlayers.getName());
                if (player == null) {
                    continue;
                }
                this.alive.addItem(new ItemStack[] { this.gameManager.newItem(Material.SKULL_ITEM, "§a" + player.getName(), 3) });
            }
        }
        return this.alive;
    }
    
    private void randomPlayer(final Player executor) {
        if (this.gameManager.isGameRunning()) {
            final List<Player> tp = new ArrayList<Player>();
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player players = onlinePlayers[i];
                tp.add(players);
            }
            final Random r = new Random();
            final int x = r.nextInt(tp.size());
            final Player player = tp.get(x);
            if (player == executor) {
                executor.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cDon't find a random player to teleport!");
                return;
            }
            if (player == null) {
                executor.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cDon't find a random player to teleport!");
                return;
            }
            executor.teleport(player.getLocation());
            executor.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§fYou have been teleported to §a" + player.getName());
            executor.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cThe game hasn't started yet!");
        }
    }
    
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (ScenarioManager.getInstance().getScenarioExact("NightmareMode").isEnabled() && event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Arrow) {
                if (((Arrow)event.getDamager()) instanceof Skeleton) {
                    ((Player)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1));
                }
                return;
            }
            if (event.getDamager() instanceof Enderman) {
                event.getEntity().setFireTicks(60);
            }
        }
        if (ScenarioManager.getInstance().getScenarioExact("MeleeFun").isEnabled()) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }
            if (!(event.getDamager() instanceof Player)) {
                return;
            }
            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                return;
            }
            final Player player = (Player)event.getEntity();
            event.setDamage(event.getDamage() * 0.5);
            Bukkit.getScheduler().runTaskLater(VenixUHC.plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    player.setNoDamageTicks(0);
                }
            }, 1L);
        }
    }
    
    private void getTopDiamonds(final int amount, final CommandSender sender) {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        final ValueComparator bvc = new ValueComparator(map);
        final TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayers.getDiamondsMined() > 0) {
                if (uhcPlayers.isPlayerAlive()) {
                    map.put(org.bukkit.ChatColor.GREEN + uhcPlayers.getName(), uhcPlayers.getDiamondsMined());
                }
                else {
                    map.put(org.bukkit.ChatColor.RED + uhcPlayers.getName(), uhcPlayers.getDiamondsMined());
                }
            }
        }
        final Player player = (Player)sender;
        sorted_map.putAll(map);
        int i = 1;
        for (final String s : sorted_map.keySet()) {
            if (i > amount) {
                break;
            }
            final TextComponent ala = new TextComponent("");
            TextComponent json = new TextComponent("");
            json = new TextComponent("§b" + i + ") " + s + " §7- §b" + map.get(s) + " diamonds mined!");
            json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tele " + s.replace("§c", "").replace("§a", "")));
            json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport!").color(ChatColor.GREEN).create()));
            ala.addExtra((BaseComponent)json);
            player.spigot().sendMessage((BaseComponent)ala);
            ++i;
        }
    }
    
    @EventHandler
    public void onWeatherChangeEvent(final WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onChunkUnloadEvent(final ChunkUnloadEvent e) {
        if (this.gameManager.getChunks().contains(e.getChunk()) || this.gameManager.isMapGenerating()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFoodLevelChangeEvent(final FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getEntity().getWorld().equals(this.gameManager.getSpawnWorld())) {
                e.setCancelled(true);
            }
            if (this.gameManager.isGameRunning() && e.getFoodLevel() < ((Player)e.getEntity()).getFoodLevel() && new Random().nextInt(100) > 4) {
                e.setCancelled(true);
            }
        }
    }
}