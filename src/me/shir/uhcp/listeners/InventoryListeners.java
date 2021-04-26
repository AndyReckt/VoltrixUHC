package me.shir.uhcp.listeners;

import org.bukkit.event.inventory.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.shir.uhcp.scenarios.*;
import java.util.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.util.*;
import me.shir.uhcp.teams.*;
import org.bukkit.command.*;
import me.shir.uhcp.scoreboard.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.cmds.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;

public class InventoryListeners implements Listener
{
    private static FileUtil lang;
    private static FileUtil scoreboard;
    
    static {
        InventoryListeners.scoreboard = new FileUtil("scoreboard.yml");
        InventoryListeners.lang = new FileUtil("lang.yml");
    }
    
    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent e) {
        final Inventory inv = e.getInventory();
        final ItemStack ist = e.getCurrentItem();
        final Player player = (Player)e.getWhoClicked();
        if (inv != null && ist != null) {
            if (inv.getTitle().equalsIgnoreCase("§aCurrent Scenarios:") || inv.getTitle().equalsIgnoreCase(ChatColor.GREEN + "STATS:") || inv.getTitle().equalsIgnoreCase("§6Scenarios Manager:") || inv.getTitle().equalsIgnoreCase("§aPlayers Alive:") || inv.getTitle().equalsIgnoreCase("§aEquipped")) {
                e.setCancelled(true);
                if (inv.getTitle().equalsIgnoreCase("§aPlayers Alive:") && ist.getType().equals((Object)Material.SKULL_ITEM)) {
                    final Player target = Bukkit.getServer().getPlayer(ist.getItemMeta().getDisplayName().replace("§a", ""));
                    if (target != null) {
                        player.teleport((Entity)target);
                    }
                }
                if (inv.getTitle().equalsIgnoreCase("§6Scenarios Manager:")) {
                    if (ist.getType() != Material.AIR) {
                        final String name = ist.getItemMeta().getDisplayName().replace("§b", "");
                        final List<String> lore = new ArrayList<String>();
                        final boolean enabled = ist.getItemMeta().getLore().contains("§7» §aEnabled");
                        if (enabled) {
                            ScenarioManager.getInstance().getScenarioExact(name).setEnabled(false);
                            lore.add(" ");
                            lore.add("§7» §cDisabled");
                            final ItemMeta im = ist.getItemMeta();
                            im.setLore((List)lore);
                            ist.setItemMeta(im);
                        }
                        else {
                            ScenarioManager.getInstance().getScenarioExact(name).setEnabled(true);
                            lore.add(" ");
                            lore.add("§7» §aEnabled");
                            final ItemMeta im = ist.getItemMeta();
                            im.setLore((List)lore);
                            ist.setItemMeta(im);
                        }
                    }
                    if (ist.getType() == Material.DIAMOND_PICKAXE) {
                        final boolean enabled2 = ist.getItemMeta().getLore().contains("§7» §aEnabled");
                        if (enabled2) {
                            player.chat("/config healtime 5");
                            player.chat("/config pvptime 10");
                            player.chat("/config bordershrinktime 25");
                        }
                        else {
                            player.chat("/config healtime 10");
                            player.chat("/config pvptime 20");
                            player.chat("/config bordershrinktime 45");
                        }
                    }
                    if (ist.getType() == Material.DIAMOND && ist.getItemMeta().getDisplayName().equalsIgnoreCase("§bStatless")) {
                        final boolean enabled2 = ist.getItemMeta().getLore().contains("§7» §aEnabled");
                        if (enabled2) {
                            GameManager.getGameManager().setStats(false);
                            Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fThe §cStats Status §fis now §cfalse§f!");
                        }
                        else {
                            GameManager.getGameManager().setStats(true);
                            Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fThe §cStats Status §fis now §atrue§f!");
                        }
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lUHC Config")) {
                e.setCancelled(true);
                if (ist.getType() == Material.BEACON) {
                    player.closeInventory();
                    player.chat("/scenarios");
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lUHC Manage")) {
                e.setCancelled(true);
                if (ist.getType() == Material.BEACON) {
                    player.closeInventory();
                    player.chat("/scenario manage");
                }
                if (ist.getType() == Material.COOKED_BEEF) {
                    player.closeInventory();
                    ManageCMD.instance.StarterFood(player);
                }
                if (ist.getType() == Material.NAME_TAG) {
                    player.closeInventory();
                    ManageCMD.instance.TeamSize(player);
                }
                if (ist.getType() == Material.SKULL_ITEM && ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lMaxplayers: §f")) {
                    player.closeInventory();
                    ManageCMD.instance.Maxplayers(player);
                }
                if (ist.getType() == Material.POTION && ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lHeal: §f" + ConfigIntegers.HEAL_TIME.get() + "m")) {
                    player.closeInventory();
                    ManageCMD.instance.HealTime(player);
                }
                if (ist.getType() == Material.DIAMOND_SWORD) {
                    player.closeInventory();
                    ManageCMD.instance.PvpTime(player);
                }
                if (ist.getType() == Material.BEDROCK) {
                    ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lBorder Size: §f" + GameManager.getGameManager().getCurrentBorder());
                    player.closeInventory();
                    ManageCMD.instance.Border(player);
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lBorder Time: §f" + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m")) {
                        player.closeInventory();
                        ManageCMD.instance.BorderTime(player);
                    }
                }
                if (ist.getType() == Material.DIAMOND) {
                    player.closeInventory();
                    ManageCMD.instance.Stats(player);
                }
                if (ist.getType() == Material.NETHERRACK) {
                    player.closeInventory();
                    ManageCMD.instance.Nether(player);
                }
                if (ist.getType() == Material.BED) {
                    if (ConfigBooleans.NETHER.isEnabled()) {
                        player.closeInventory();
                        ManageCMD.instance.Bedbombs(player);
                    }
                    else {
                        player.closeInventory();
                        player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + ("§cYou can't edit this because nether is currently disabled"));
                    }
                }
                if (ist.getType() == Material.POTION) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lSpeed I/II: §f" + ConfigBooleans.SPEED.isEnabled())) {
                        if (ConfigBooleans.NETHER.isEnabled()) {
                            player.closeInventory();
                            ManageCMD.instance.Speed(player);
                        }
                        else {
                            player.closeInventory();
                            player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + ("§cYou can't edit this because nether is currently disabled"));
                        }
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lStrength I: §f" + ConfigBooleans.STRENGTH_1.isEnabled())) {
                        if (ConfigBooleans.NETHER.isEnabled()) {
                            player.closeInventory();
                            ManageCMD.instance.Strength(player);
                        }
                        else {
                            player.closeInventory();
                            player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + ("§cYou can't edit this because nether is currently disabled"));
                        }
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lPoison: §f" + ConfigBooleans.POISON.isEnabled())) {
                        if (ConfigBooleans.NETHER.isEnabled()) {
                            player.closeInventory();
                            ManageCMD.instance.Poison(player);
                        }
                        else {
                            player.closeInventory();
                            player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + ("§cYou can't edit this because nether is currently disabled"));
                        }
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lStarter Food")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                        final int starterfoodplus1 = ConfigIntegers.STARTER_FOOD.get() + 5;
                        player.chat("/config starterfood 0");
                        final ItemStack food = new ItemStack(Material.COOKED_BEEF);
                        final ItemMeta foodM = food.getItemMeta();
                        foodM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStarter Food: §f" + ConfigIntegers.STARTER_FOOD.get());
                        food.setItemMeta(foodM);
                        inv.setItem(4, food);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                        final int starterfood = ConfigIntegers.STARTER_FOOD.get() - 5;
                        player.chat("/config starterfood " + starterfood);
                        inv.getItem(4).getItemMeta().setDisplayName("§lStarter Food: §f" + ConfigIntegers.STARTER_FOOD.get());
                        final ItemStack food = new ItemStack(Material.COOKED_BEEF);
                        final ItemMeta foodM = food.getItemMeta();
                        foodM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStarter Food: §f" + ConfigIntegers.STARTER_FOOD.get());
                        food.setItemMeta(foodM);
                        inv.setItem(4, food);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lTeam Size")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/teams true");
                        final ItemStack team = new ItemStack(Material.NAME_TAG);
                        final ItemMeta teamM = team.getItemMeta();
                        if (TeamManager.getInstance().isTeamsEnabled()) {
                            teamM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
                        }
                        else {
                            teamM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fFFA");
                        }
                        team.setItemMeta(teamM);
                        inv.setItem(4, team);
                        player.closeInventory();
                        ManageCMD.instance.TeamSize2(player);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/teams false");
                        final ItemStack team = new ItemStack(Material.NAME_TAG);
                        final ItemMeta teamM = team.getItemMeta();
                        if (TeamManager.getInstance().isTeamsEnabled()) {
                            teamM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
                        }
                        else {
                            teamM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fFFA");
                        }
                        team.setItemMeta(teamM);
                        inv.setItem(4, team);
                        player.closeInventory();
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lTeam Size Manage")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+1")) {
                        final int size = TeamManager.getInstance().getMaxSize() + 1;
                        player.chat("/team size " + size);
                        final ItemStack team2 = new ItemStack(Material.NAME_TAG);
                        final ItemMeta teamM2 = team2.getItemMeta();
                        if (TeamManager.getInstance().isTeamsEnabled()) {
                            teamM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
                        }
                        else {
                            teamM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fFFA");
                        }
                        team2.setItemMeta(teamM2);
                        inv.setItem(4, team2);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-1")) {
                        final int sizes = TeamManager.getInstance().getMaxSize() - 1;
                        player.chat("/team size " + sizes);
                        final ItemStack team2 = new ItemStack(Material.NAME_TAG);
                        final ItemMeta teamM2 = team2.getItemMeta();
                        if (TeamManager.getInstance().isTeamsEnabled()) {
                            teamM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
                        }
                        else {
                            teamM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lTeam Size: §fFFA");
                        }
                        team2.setItemMeta(teamM2);
                        inv.setItem(4, team2);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lMaxplayers")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                        final int maxxd = ConfigIntegers.MAX_PLAYERS.get() + 5;
                        player.chat("/config maxplayers " + maxxd);
                        final ItemStack max = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        final ItemMeta maxM = max.getItemMeta();
                        maxM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lMaxplayers: §f" + ConfigIntegers.MAX_PLAYERS.get());
                        max.setItemMeta(maxM);
                        inv.setItem(4, max);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                        final int mpl = ConfigIntegers.MAX_PLAYERS.get() - 5;
                        player.chat("/config maxplayers " + mpl);
                        final ItemStack max = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        final ItemMeta maxM = max.getItemMeta();
                        maxM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lMaxplayers: §f" + ConfigIntegers.MAX_PLAYERS.get());
                        max.setItemMeta(maxM);
                        inv.setItem(4, max);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lHeal Time")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                        final int healplus = ConfigIntegers.HEAL_TIME.get() + 5;
                        player.chat("/config healtime " + healplus);
                        final ItemStack h = new ItemStack(Material.POTION, 1, (short)1);
                        final ItemMeta hM = h.getItemMeta();
                        hM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lHeal: §f" + ConfigIntegers.HEAL_TIME.get() + "m");
                        h.setItemMeta(hM);
                        inv.setItem(4, h);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                        final int healless = ConfigIntegers.HEAL_TIME.get() - 5;
                        player.chat("/config healtime " + healless);
                        final ItemStack h = new ItemStack(Material.POTION, 1, (short)1);
                        final ItemMeta hM = h.getItemMeta();
                        hM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lHeal: §f" + ConfigIntegers.HEAL_TIME.get() + "m");
                        h.setItemMeta(hM);
                        inv.setItem(4, h);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lPvP Time")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                        final int pvpplus = ConfigIntegers.PVP_TIME.get() + 5;
                        player.chat("/config pvptime " + pvpplus);
                        final ItemStack p = new ItemStack(Material.DIAMOND_SWORD);
                        final ItemMeta pM = p.getItemMeta();
                        pM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lPvP: §f" + ConfigIntegers.PVP_TIME.get() + "m");
                        p.setItemMeta(pM);
                        inv.setItem(4, p);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                        final int pvpless = ConfigIntegers.PVP_TIME.get() - 5;
                        player.chat("/config pvptime " + pvpless);
                        final ItemStack p = new ItemStack(Material.DIAMOND_SWORD);
                        final ItemMeta pM = p.getItemMeta();
                        pM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lPvP: §f" + ConfigIntegers.PVP_TIME.get() + "m");
                        p.setItemMeta(pM);
                        inv.setItem(4, p);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lBorder Time")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                        final int borderplus = ConfigIntegers.BORDER_SHRINK_TIME.get() + 5;
                        player.chat("/config bordershrinktime " + borderplus);
                        final ItemStack b = new ItemStack(Material.BEDROCK);
                        final ItemMeta bM = b.getItemMeta();
                        bM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBorder Time: §f" + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m");
                        b.setItemMeta(bM);
                        inv.setItem(4, b);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                        final int borderless = ConfigIntegers.BORDER_SHRINK_TIME.get() - 5;
                        player.chat("/config bordershrinktime " + borderless);
                        final ItemStack b = new ItemStack(Material.BEDROCK);
                        final ItemMeta bM = b.getItemMeta();
                        bM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBorder Time: §f" + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m");
                        b.setItemMeta(bM);
                        inv.setItem(4, b);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lNether")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/config nether true");
                        final ItemStack n = new ItemStack(Material.NETHERRACK);
                        final ItemMeta nM = n.getItemMeta();
                        nM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lNether: §f" + ConfigBooleans.NETHER.isEnabled());
                        n.setItemMeta(nM);
                        inv.setItem(4, n);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/config nether false");
                        final ItemStack n = new ItemStack(Material.NETHERRACK);
                        final ItemMeta nM = n.getItemMeta();
                        nM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lNether: §f" + ConfigBooleans.NETHER.isEnabled());
                        n.setItemMeta(nM);
                        inv.setItem(4, n);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lBorder Size")) {
                e.setCancelled(true);
                if (ist.getType() == Material.INK_SACK) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§a+500")) {
                        if (GameManager.getGameManager().getCurrentBorder() > 1999) {
                            player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cBorder cannot be bigger than 2000");
                        }
                        else {
                            final int bordersizeplus = GameManager.getGameManager().getCurrentBorder() + 500;
                            GameManager.getGameManager().setCurrentBorder(bordersizeplus);
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb uhcworld set " + bordersizeplus + " 0 0");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb uhcworld_nether set " + bordersizeplus / 2 + " 0 0");
                            Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getConfigPrefix()) + "§fThe §cBorder Size §fis now §a" + bordersizeplus);
                            final ItemStack b = new ItemStack(Material.BEDROCK);
                            final ItemMeta bM = b.getItemMeta();
                            bM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBorder Size: §f" + GameManager.getGameManager().getCurrentBorder());
                            b.setItemMeta(bM);
                            inv.setItem(4, b);
                        }
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§c-500")) {
                        final int bordersizeless = GameManager.getGameManager().getCurrentBorder() - 500;
                        GameManager.getGameManager().setCurrentBorder(bordersizeless);
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb uhcworld set " + bordersizeless + " 0 0");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb uhcworld_nether set " + bordersizeless / 2 + " 0 0");
                        Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getConfigPrefix()) + "§fThe §cBorder Size §fis now §a" + bordersizeless);
                        final ItemStack b = new ItemStack(Material.BEDROCK);
                        final ItemMeta bM = b.getItemMeta();
                        bM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBorder Size: §f" + GameManager.getGameManager().getCurrentBorder());
                        b.setItemMeta(bM);
                        inv.setItem(4, b);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lBedbombs")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/config bedbombs true");
                        final ItemStack b2 = new ItemStack(Material.BED);
                        final ItemMeta bM2 = b2.getItemMeta();
                        bM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBedbombs: §f" + ConfigBooleans.BEDBOMBS.isEnabled());
                        b2.setItemMeta(bM2);
                        inv.setItem(4, b2);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/config bedbombs false");
                        final ItemStack b2 = new ItemStack(Material.BED);
                        final ItemMeta bM2 = b2.getItemMeta();
                        bM2.setDisplayName(GameManager.getGameManager().getMainColor() + "§lBedbombs: §f" + ConfigBooleans.BEDBOMBS.isEnabled());
                        b2.setItemMeta(bM2);
                        inv.setItem(4, b2);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lSpeed I/II")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/config speed true");
                        final ItemStack s = new ItemStack(Material.POTION, 1, (short)2);
                        final ItemMeta sM = s.getItemMeta();
                        sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSpeed I/II: §f" + ConfigBooleans.SPEED.isEnabled());
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/config speed false");
                        final ItemStack s = new ItemStack(Material.POTION, 1, (short)2);
                        final ItemMeta sM = s.getItemMeta();
                        sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSpeed I/II: §f" + ConfigBooleans.SPEED.isEnabled());
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lStrength I")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/config strength1 true");
                        final ItemStack s = new ItemStack(Material.POTION, 1, (short)8201);
                        final ItemMeta sM = s.getItemMeta();
                        sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStrength I: §f" + ConfigBooleans.STRENGTH_1.isEnabled());
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/config strength1 false");
                        final ItemStack s = new ItemStack(Material.POTION, 1, (short)8201);
                        final ItemMeta sM = s.getItemMeta();
                        sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStrength I: §f" + ConfigBooleans.STRENGTH_1.isEnabled());
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lPoison")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        player.chat("/config poison true");
                        final ItemStack po = new ItemStack(Material.POTION, 1, (short)16388);
                        final ItemMeta poM = po.getItemMeta();
                        poM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lPoison: §f" + ConfigBooleans.POISON.isEnabled());
                        po.setItemMeta(poM);
                        inv.setItem(4, po);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        player.chat("/config poison false");
                        final ItemStack po = new ItemStack(Material.POTION, 1, (short)16388);
                        final ItemMeta poM = po.getItemMeta();
                        poM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lPoison: §f" + ConfigBooleans.POISON.isEnabled());
                        po.setItemMeta(poM);
                        inv.setItem(4, po);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase(GameManager.getGameManager().getMainColor() + "§lStats")) {
                e.setCancelled(true);
                if (ist.getType() == Material.WOOL) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aTrue")) {
                        GameManager.getGameManager().setStats(true);
                        Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fThe §cStats Status §fare now §atrue§f!");
                        final ItemStack s = new ItemStack(Material.DIAMOND);
                        final ItemMeta sM = s.getItemMeta();
                        if (!GameManager.getGameManager().isStatsEnabled()) {
                            sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSTATS: §ffalse");
                        }
                        else {
                            sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSTATS: §ftrue");
                        }
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cFalse")) {
                        GameManager.getGameManager().setStats(false);
                        Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§fThe §cStats Status §fare now §cfalse§f!");
                        final ItemStack s = new ItemStack(Material.DIAMOND);
                        final ItemMeta sM = s.getItemMeta();
                        if (!GameManager.getGameManager().isStatsEnabled()) {
                            sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSTATS: §ffalse");
                        }
                        else {
                            sM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lSTATS: §ftrue");
                        }
                        s.setItemMeta(sM);
                        inv.setItem(4, s);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase("§aPlayers in Nether:")) {
                e.setCancelled(true);
                if (ist.getType() == Material.SKULL_ITEM) {
                    final Player target = Bukkit.getServer().getPlayer(ist.getItemMeta().getDisplayName().replace("§a", ""));
                    if (target != null) {
                        player.teleport((Entity)target);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase("§aStaff Online:")) {
                e.setCancelled(true);
                if (ist.getType() == Material.SKULL_ITEM) {
                    final Player target = Bukkit.getServer().getPlayer(ist.getItemMeta().getDisplayName().replace("§c§l", ""));
                    if (target != null) {
                        player.teleport((Entity)target);
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase("§cScoreboards")) {
                e.setCancelled(true);
                if (ist.getType() == Material.STAINED_CLAY) {
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§cVoltrix")) {
                        ScoreManager.removeultra(player);
                        ScoreManager.removebadlion(player);
                        ScoreManager.addplover(player);
                        InventoryListeners.scoreboard.set(player.getName(), "Voltrix");
                        InventoryListeners.scoreboard.getConfig().set(player.getName(), (Object)"Voltrix");
                        InventoryListeners.scoreboard.saveFile();
                        InventoryListeners.scoreboard.loadFile();
                        player.sendMessage("§aYour Scoreboard is now Voltrix");
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§aBadlion")) {
                        ScoreManager.removeultra(player);
                        ScoreManager.removeplover(player);
                        ScoreManager.addbadlion(player);
                        InventoryListeners.scoreboard.set(player.getName(), "Badlion");
                        InventoryListeners.scoreboard.getConfig().set(player.getName(), (Object)"Badlion");
                        InventoryListeners.scoreboard.saveFile();
                        InventoryListeners.scoreboard.loadFile();
                        player.sendMessage("§aYour Scoreboard is now Badlion");
                    }
                    if (ist.getItemMeta().getDisplayName().equalsIgnoreCase("§6Ultra")) {
                        ScoreManager.removebadlion(player);
                        ScoreManager.removeplover(player);
                        ScoreManager.addultra(player);
                        InventoryListeners.scoreboard.set(player.getName(), "Ultra");
                        InventoryListeners.scoreboard.getConfig().set(player.getName(), (Object)"Ultra");
                        InventoryListeners.scoreboard.saveFile();
                        InventoryListeners.scoreboard.loadFile();
                        player.sendMessage("§aYour Scoreboard is now Ultra");
                    }
                }
            }
            if (inv.getTitle().equalsIgnoreCase("§aAlerts:")) {
                e.setCancelled(true);
                if (ist.getType() == Material.DIAMOND_PICKAXE) {
                    player.chat("/xalerts");
                    final ItemStack mining = new ItemStack(Material.DIAMOND_PICKAXE);
                    final ItemMeta miningM = mining.getItemMeta();
                    if (ToggleAlerts.xalerts.contains(player.getUniqueId())) {
                        miningM.setDisplayName(GameManager.getGameManager().getMainColor() + "Mining Alerts: §8(§cOFF§8)");
                    }
                    else {
                        miningM.setDisplayName(GameManager.getGameManager().getMainColor() + "Mining Alerts: §8(§aON§8)");
                    }
                    mining.setItemMeta(miningM);
                    inv.setItem(10, mining);
                }
                if (ist.getType() == Material.DIAMOND_ORE) {
                    player.chat("/pvpalerts");
                    final ItemStack pvp = new ItemStack(Material.DIAMOND_ORE);
                    final ItemMeta pvpM = pvp.getItemMeta();
                    if (ToggleAlerts.pvpalerts.contains(player.getUniqueId())) {
                        pvpM.setDisplayName(GameManager.getGameManager().getMainColor() + "PvP Alerts: §8(§cOFF§8)");
                    }
                    else {
                        pvpM.setDisplayName(GameManager.getGameManager().getMainColor() + "PvP Alerts: §8(§aON§8)");
                    }
                    pvp.setItemMeta(pvpM);
                    inv.setItem(13, pvp);
                }
                if (ist.getType() == Material.PACKED_ICE) {
                    player.chat("/abusealerts");
                    final ItemStack abuse = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    final ItemMeta abuseM = abuse.getItemMeta();
                    if (ToggleAlerts.abusealerts.contains(player.getUniqueId())) {
                        abuseM.setDisplayName(GameManager.getGameManager().getMainColor() + "Abuse Alerts: §8(§cOFF§8)");
                    }
                    else {
                        abuseM.setDisplayName(GameManager.getGameManager().getMainColor() + "Abuse Alerts: §8(§aON§8)");
                    }
                    abuse.setItemMeta(abuseM);
                    inv.setItem(16, abuse);
                }
            }
            if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
                e.setCancelled(true);
            }
            if (FreezeCMD.freeze.contains(player.getUniqueId())) {
                e.setCancelled(true);
            }
            if (!ConfigBooleans.HORSE_ARMOUR.isEnabled() && ist.getType() != Material.AIR && (ist.getType().equals((Object)Material.IRON_BARDING) || ist.getType().equals((Object)Material.GOLD_BARDING) || ist.getType().equals((Object)Material.DIAMOND_BARDING))) {
                e.setCancelled(true);
                player.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cHorse armour is currently disabled!");
                ist.setType(Material.AIR);
            }
        }
    }
}
