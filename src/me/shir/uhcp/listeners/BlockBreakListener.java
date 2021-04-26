package me.shir.uhcp.listeners;

import me.shir.uhcp.game.*;
import org.bukkit.event.block.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import me.shir.uhcp.cmds.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.block.*;
import me.shir.uhcp.player.*;
import org.bukkit.event.*;
import org.bukkit.enchantments.*;
import java.util.*;

public class BlockBreakListener implements Listener
{
    private final GameManager gameManager;
    double EXP_MULTIPLIER_LOOTING_PER_LEVEL;
    double EXP_MULTIPLIER_FORTUNE_PER_LEVEL;
    double EXP_MULTIPLIER_GENERAL;
    double EXP_MULTIPLIER_FISHING;
    double EXP_MULTIPLIER_LUCK_PER_LEVEL;
    double EXP_MULTIPLIER_SMELTING;
    
    public BlockBreakListener() {
        this.gameManager = GameManager.getGameManager();
        this.EXP_MULTIPLIER_LOOTING_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_FORTUNE_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_GENERAL = 3.0;
        this.EXP_MULTIPLIER_FISHING = 3.0;
        this.EXP_MULTIPLIER_LUCK_PER_LEVEL = 3.0;
        this.EXP_MULTIPLIER_SMELTING = 3.0;
    }
    
    @EventHandler
    public void onBlockBreakEvent(final BlockBreakEvent e) {
        final Block b = e.getBlock();
        final Player player = e.getPlayer();
        if (player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && !player.hasPermission("uhc.host")) {
            e.setCancelled(true);
            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou cannot break blocks here!");
        }
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        if (uhcPlayer.isSpectating()) {
            e.setCancelled(true);
        }
        if (!e.isCancelled()) {
            final boolean sEnabled = this.gameManager.isStatsEnabled();
            switch (b.getType()) {
                case DIAMOND_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("BloodDiamonds").isEnabled()) {
                        player.damage(1.0);
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("Diamondless").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(4);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("RiskyRetrieval").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(4);
                        player.getEnderChest().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND) });
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(2);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.DIAMOND, 3));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(8);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.DIAMOND));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(4);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.DIAMOND, 2));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.EXP_BOTTLE, 4));
                    }
                    uhcPlayer.addDiamond();
                    for (final Player players : this.gameManager.getModerators()) {
                        if (players != null && uhcPlayer.getDiamondsMined() % 5 == 0 && !ToggleAlerts.xalerts.contains(players.getUniqueId())) {
                            final TextComponent ala = new TextComponent("");
                            TextComponent json = new TextComponent("");
                            json = new TextComponent(String.valueOf(this.gameManager.getXrayPrefix()) + this.gameManager.getMainColor() + player.getName() + " §7is mining §bdiamonds" + this.gameManager.getMainColor() + " (T: §b" + uhcPlayer.getDiamondsMined() + this.gameManager.getMainColor() + ")");
                            json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tele " + player.getName()));
                            json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick to teleport!").create()));
                            ala.addExtra((BaseComponent)json);
                            players.spigot().sendMessage((BaseComponent)ala);
                        }
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalDiamondsMined();
                        break;
                    }
                    break;
                }
                case GOLD_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("Goldless").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(3);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("RiskyRetrieval").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(4);
                        player.getEnderChest().addItem(new ItemStack[] { new ItemStack(Material.GOLD_INGOT) });
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(2);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(3);
                    }
                    else if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(4);
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalGoldMined();
                        break;
                    }
                    break;
                }
                case IRON_ORE: {
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalIronMined();
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("BareBones").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(2);
                        break;
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT, 3));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(5);
                        break;
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(2);
                        break;
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT, 2));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(3);
                        break;
                    }
                    break;
                }
                case REDSTONE_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.BOOK));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalRedstoneMined();
                        break;
                    }
                    break;
                }
                case GLOWING_REDSTONE_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.BOOK));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalRedstoneMined();
                        break;
                    }
                    break;
                }
                case QUARTZ_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.TNT));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalQuartzMined();
                        break;
                    }
                    break;
                }
                case LAPIS_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.POTION, 1, (short)16453));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(6);
                    }
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalLapisMined();
                        break;
                    }
                    break;
                }
                case EMERALD_ORE: {
                    if (ScenarioManager.getInstance().getScenarioExact("OreFrenzy").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.ARROW, 32));
                        ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), (Class)ExperienceOrb.class)).setExperience(8);
                    }
                }
                case COAL_ORE: {
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalCoalMined();
                        break;
                    }
                    break;
                }
                case MOB_SPAWNER: {
                    if (sEnabled && uhcPlayer.isPlayerAlive()) {
                        uhcPlayer.addTotalSpawnersMined();
                    }
                    uhcPlayer.addSpawnersMined();
                    for (final Player players : this.gameManager.getModerators()) {
                        if (players != null && !ToggleAlerts.xalerts.contains(players.getUniqueId())) {
                            players.sendMessage(String.valueOf(this.gameManager.getXrayPrefix()) + this.gameManager.getMainColor() + player.getName() + " §7is mining a §6mob spawner" + this.gameManager.getMainColor() + "(T: " + uhcPlayer.getSpawnersMined() + this.gameManager.getMainColor() + ")");
                        }
                    }
                    break;
                }
                case GRAVEL: {
                    if (ScenarioManager.getInstance().getScenarioExact("TripleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT, 3));
                        break;
                    }
                    if (uhcPlayer.isPlayerAlive() && ScenarioManager.getInstance().getScenarioExact("Vanilla+").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT));
                        break;
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("CutClean").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT));
                        break;
                    }
                    if (ScenarioManager.getInstance().getScenarioExact("DoubleOres").isEnabled()) {
                        e.setCancelled(true);
                        b.setType(Material.AIR);
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT, 2));
                        break;
                    }
                    break;
                }
                case LOG: {
                    if (uhcPlayer.isPlayerAlive() && ScenarioManager.getInstance().getScenarioExact("Timber").isEnabled()) {
                        Block bUp = b.getRelative(BlockFace.UP);
                        Block bDown = b.getRelative(BlockFace.DOWN);
                        while (bUp.getType() == Material.LOG || bUp.getType() == Material.LOG_2) {
                            bUp.breakNaturally();
                            bUp = bUp.getRelative(BlockFace.UP);
                        }
                        while (bDown.getType() == Material.LOG || bDown.getType() == Material.LOG_2) {
                            bDown.breakNaturally();
                            bDown = bDown.getRelative(BlockFace.DOWN);
                        }
                        break;
                    }
                    break;
                }
                case LOG_2: {
                    if (uhcPlayer.isPlayerAlive() && ScenarioManager.getInstance().getScenarioExact("Timber").isEnabled()) {
                        Block bUp = b.getRelative(BlockFace.UP);
                        Block bDown = b.getRelative(BlockFace.DOWN);
                        while (bUp.getType() == Material.LOG || bUp.getType() == Material.LOG_2) {
                            bUp.breakNaturally();
                            bUp = bUp.getRelative(BlockFace.UP);
                        }
                        while (bDown.getType() == Material.LOG || bDown.getType() == Material.LOG_2) {
                            bDown.breakNaturally();
                            bDown = bDown.getRelative(BlockFace.DOWN);
                        }
                    }
                }
                case LEAVES: {
                    if (uhcPlayer.isPlayerAlive() && Math.random() <= 0.025) {
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE, 1));
                        break;
                    }
                }
                case LEAVES_2: {
                    if (uhcPlayer.isPlayerAlive() && Math.random() <= 0.025) {
                        b.getState().update();
                        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.APPLE, 1));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @EventHandler
    public void onTripleXPBreak(final BlockBreakEvent e) {
        if (!ScenarioManager.getInstance().getScenarioExact("TripleXP").isEnabled()) {
            return;
        }
        final double amount = e.getExpToDrop();
        final Player player = e.getPlayer();
        final ItemStack stack = player.getItemInHand();
        if (stack != null && stack.getType() != Material.AIR && amount > 0.0) {
            final int enchantmentLevel = stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if (enchantmentLevel > 0) {
                final double multiplier = enchantmentLevel * this.EXP_MULTIPLIER_FORTUNE_PER_LEVEL;
                final int result = (int)Math.ceil(amount * multiplier);
                e.setExpToDrop(result);
            }
        }
    }
    
    @EventHandler
    public void onFlowerPowerBreakEvent(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        final Block b = e.getBlock();
        if (e.isCancelled()) {
            return;
        }
        if (b.getType() == Material.YELLOW_FLOWER && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
            final Material[] allItems = Material.values();
            final ArrayList<Material> drop = new ArrayList<Material>();
            Material[] array;
            for (int length = (array = allItems).length, i = 0; i < length; ++i) {
                final Material val = array[i];
                drop.add(val);
            }
            final int random = new Random().nextInt(drop.size());
            final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
            final int randomn = new Random().nextInt(64);
            final Material dropped = drop.get(random);
            final int howmany = numbers[randomn];
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
        }
        if (b.getType() == Material.RED_ROSE) {
            if (b.getData() == 0 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array2;
                for (int length2 = (array2 = allItems).length, j = 0; j < length2; ++j) {
                    final Material val = array2[j];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 1 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array3;
                for (int length3 = (array3 = allItems).length, k = 0; k < length3; ++k) {
                    final Material val = array3[k];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 2 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array4;
                for (int length4 = (array4 = allItems).length, l = 0; l < length4; ++l) {
                    final Material val = array4[l];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 3 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array5;
                for (int length5 = (array5 = allItems).length, n = 0; n < length5; ++n) {
                    final Material val = array5[n];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 4 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array6;
                for (int length6 = (array6 = allItems).length, n2 = 0; n2 < length6; ++n2) {
                    final Material val = array6[n2];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 5 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array7;
                for (int length7 = (array7 = allItems).length, n3 = 0; n3 < length7; ++n3) {
                    final Material val = array7[n3];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 6 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array8;
                for (int length8 = (array8 = allItems).length, n4 = 0; n4 < length8; ++n4) {
                    final Material val = array8[n4];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 7 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array9;
                for (int length9 = (array9 = allItems).length, n5 = 0; n5 < length9; ++n5) {
                    final Material val = array9[n5];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
            if (b.getData() == 8 && ScenarioManager.getInstance().getScenarioExact("FlowerPower").isEnabled()) {
                final Material[] allItems = Material.values();
                final ArrayList<Material> drop = new ArrayList<Material>();
                Material[] array10;
                for (int length10 = (array10 = allItems).length, n6 = 0; n6 < length10; ++n6) {
                    final Material val = array10[n6];
                    drop.add(val);
                }
                final int random = new Random().nextInt(drop.size());
                final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64 };
                final int randomn = new Random().nextInt(64);
                final Material dropped = drop.get(random);
                final int howmany = numbers[randomn];
                b.setType(Material.AIR);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(dropped, howmany));
            }
        }
    }
}
