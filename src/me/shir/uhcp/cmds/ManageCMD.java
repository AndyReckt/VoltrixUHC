package me.shir.uhcp.cmds;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.teams.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class ManageCMD implements CommandExecutor
{
    private final GameManager gameManager;
    private final ChatColor color;
    public static ManageCMD instance;
    
    public ManageCMD() {
        this.gameManager = GameManager.getGameManager();
        this.color = this.gameManager.getMainColor();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        ManageCMD.instance = this;
        if (cmd.getName().equalsIgnoreCase("uhcmanage") && !sender.hasPermission("uhc.host")) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permission to execute this command!");
            return true;
        }
        final Player player = (Player)sender;
        this.UHCManage(player);
        return false;
    }
    
    private void UHCManage(final Player player) {
        final Inventory uhcmanage = Bukkit.createInventory((InventoryHolder)null, 54, GameManager.getGameManager().getMainColor() + "§lUHC Manage");
        final ItemStack scenarios = new ItemStack(Material.BEACON);
        final ItemMeta scenariosM = scenarios.getItemMeta();
        scenariosM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lScenarios");
        scenarios.setItemMeta(scenariosM);
        final ItemStack food = new ItemStack(Material.COOKED_BEEF);
        final ItemMeta foodM = food.getItemMeta();
        foodM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStarter Food: §f" + ConfigIntegers.STARTER_FOOD.get());
        food.setItemMeta(foodM);
        final ItemStack timings = new ItemStack(Material.WATCH);
        final ItemMeta timingsM = timings.getItemMeta();
        timingsM.setDisplayName(this.color + "§lTIMINGS");
        timings.setItemMeta(timingsM);
        final ItemStack nether = new ItemStack(Material.NETHER_BRICK);
        final ItemMeta netherM = nether.getItemMeta();
        netherM.setDisplayName(this.color + "§lNETHER INFO");
        nether.setItemMeta(netherM);
        final ItemStack netherb = new ItemStack(Material.NETHERRACK);
        final ItemMeta netherbM = netherb.getItemMeta();
        netherbM.setDisplayName(this.color + "§lNether: §f" + ConfigBooleans.NETHER.isEnabled());
        netherb.setItemMeta(netherbM);
        final ItemStack speed = new ItemStack(Material.POTION, 1, (short)2);
        final ItemMeta speedM = speed.getItemMeta();
        if (ConfigBooleans.NETHER.isEnabled()) {
            speedM.setDisplayName(this.color + "§lSpeed I/II: §f" + ConfigBooleans.SPEED.isEnabled());
        }
        else {
            speedM.setDisplayName(this.color + "§lSpeed I/II: §ffalse");
        }
        speed.setItemMeta(speedM);
        final ItemStack strength = new ItemStack(Material.POTION, 1, (short)8201);
        final ItemMeta strengthM = strength.getItemMeta();
        if (ConfigBooleans.NETHER.isEnabled()) {
            strengthM.setDisplayName(this.color + "§lStrength I: §f" + ConfigBooleans.STRENGTH_1.isEnabled());
        }
        else {
            strengthM.setDisplayName(this.color + "§lStrength I: §ffalse");
        }
        strength.setItemMeta(strengthM);
        final ItemStack poison = new ItemStack(Material.POTION, 1, (short)16388);
        final ItemMeta poisonM = poison.getItemMeta();
        if (ConfigBooleans.NETHER.isEnabled()) {
            poisonM.setDisplayName(this.color + "§lPoison: §f" + ConfigBooleans.POISON.isEnabled());
        }
        else {
            poisonM.setDisplayName(this.color + "§lPoison: §ffalse");
        }
        poison.setItemMeta(poisonM);
        final ItemStack border = new ItemStack(Material.BEDROCK);
        final ItemMeta borderM = border.getItemMeta();
        borderM.setDisplayName(this.color + "§lBORDER INFO");
        border.setItemMeta(borderM);
        final ItemStack heal = new ItemStack(Material.POTION, 1, (short)1);
        final ItemMeta healM = heal.getItemMeta();
        healM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lHeal: §f" + ConfigIntegers.HEAL_TIME.get() + "m");
        heal.setItemMeta(healM);
        final ItemStack pvp = new ItemStack(Material.DIAMOND_SWORD);
        final ItemMeta pvpM = pvp.getItemMeta();
        pvpM.setDisplayName(this.color + "§lPvP: §f" + ConfigIntegers.PVP_TIME.get() + "m");
        pvp.setItemMeta(pvpM);
        final ItemStack bTime = new ItemStack(Material.BEDROCK);
        final ItemMeta bTimeM = bTime.getItemMeta();
        bTimeM.setDisplayName(this.color + "§lBorder Time: §f" + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m");
        bTime.setItemMeta(bTimeM);
        final ItemStack absorption = new ItemStack(Material.GOLDEN_APPLE);
        final ItemMeta absorptionM = absorption.getItemMeta();
        absorptionM.setDisplayName(this.color + "§lAbsorption: §f" + ConfigBooleans.ABSORPTION.isEnabled());
        absorption.setItemMeta(absorptionM);
        final ItemStack heads = new ItemStack(Material.GOLDEN_APPLE);
        final ItemMeta headsM = heads.getItemMeta();
        headsM.setDisplayName(this.color + "§lGolden Heads: §f" + ConfigBooleans.GOLDEN_HEADS.isEnabled());
        heads.setItemMeta(headsM);
        final ItemStack god = new ItemStack(Material.GOLDEN_APPLE, 1, (short)1);
        final ItemMeta godM = god.getItemMeta();
        godM.setDisplayName(this.color + "§lGod Apples: §f" + ConfigBooleans.GOD_APPLES.isEnabled());
        god.setItemMeta(godM);
        final ItemStack bSize = new ItemStack(Material.BEDROCK);
        final ItemMeta bSizeM = bSize.getItemMeta();
        bSizeM.setDisplayName(this.color + "§lBorder Size: §f" + this.gameManager.getCurrentBorder());
        bSize.setItemMeta(bSizeM);
        final ItemStack bed = new ItemStack(Material.BED);
        final ItemMeta bedM = bed.getItemMeta();
        if (ConfigBooleans.NETHER.isEnabled()) {
            bedM.setDisplayName(this.color + "§lBedbombs: §f" + ConfigBooleans.BEDBOMBS.isEnabled());
        }
        else {
            bedM.setDisplayName(this.color + "§lBedbombs: §ffalse");
        }
        bed.setItemMeta(bedM);
        final ItemStack goldenapples = new ItemStack(Material.GOLDEN_APPLE);
        final ItemMeta goldenapplesM = goldenapples.getItemMeta();
        goldenapplesM.setDisplayName(this.color + "§lGOLDEN APPLES INFO");
        goldenapples.setItemMeta(goldenapplesM);
        final ItemStack deathmatch = new ItemStack(Material.SKULL_ITEM);
        final ItemMeta deathmatchM = deathmatch.getItemMeta();
        deathmatchM.setDisplayName(this.color + "§lDeathmatch: §ftrue");
        deathmatch.setItemMeta(deathmatchM);
        final ItemStack shears = new ItemStack(Material.SHEARS);
        final ItemMeta shearsM = shears.getItemMeta();
        shearsM.setDisplayName(this.color + "§lShears: §ftrue");
        shears.setItemMeta(shearsM);
        final ItemStack teamsize = new ItemStack(Material.NAME_TAG);
        final ItemMeta teamsizeM = teamsize.getItemMeta();
        if (TeamManager.getInstance().isTeamsEnabled()) {
            teamsizeM.setDisplayName(this.color + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
        }
        else {
            teamsizeM.setDisplayName(this.color + "§lTeam Size: §fFFA");
        }
        teamsize.setItemMeta(teamsizeM);
        final ItemStack maxplayers = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta maxplayersM = maxplayers.getItemMeta();
        maxplayersM.setDisplayName(this.color + "§lMaxplayers: §f" + ConfigIntegers.MAX_PLAYERS.get());
        maxplayers.setItemMeta(maxplayersM);
        final ItemStack stats = new ItemStack(Material.DIAMOND);
        final ItemMeta statsM = stats.getItemMeta();
        if (!this.gameManager.isStatsEnabled()) {
            statsM.setDisplayName(this.color + "§lStats: §ffalse");
        }
        else {
            statsM.setDisplayName(this.color + "§lStats: §ftrue");
        }
        stats.setItemMeta(statsM);
        uhcmanage.setItem(0, scenarios);
        uhcmanage.setItem(2, food);
        uhcmanage.setItem(4, teamsize);
        uhcmanage.setItem(6, maxplayers);
        uhcmanage.setItem(8, stats);
        uhcmanage.setItem(10, heal);
        uhcmanage.setItem(12, pvp);
        uhcmanage.setItem(14, bTime);
        uhcmanage.setItem(18, netherb);
        uhcmanage.setItem(20, speed);
        uhcmanage.setItem(22, strength);
        uhcmanage.setItem(24, poison);
        uhcmanage.setItem(26, bed);
        uhcmanage.setItem(28, bTime);
        uhcmanage.setItem(30, bSize);
        uhcmanage.setItem(38, absorption);
        uhcmanage.setItem(40, heads);
        uhcmanage.setItem(42, god);
        player.openInventory(uhcmanage);
    }
    
    public void StarterFood(final Player player) {
        final Inventory starterfood = Bukkit.createInventory((InventoryHolder)null, 18, GameManager.getGameManager().getMainColor() + "§lStarter Food");
        final ItemStack food = new ItemStack(Material.COOKED_BEEF);
        final ItemMeta foodM = food.getItemMeta();
        foodM.setDisplayName(GameManager.getGameManager().getMainColor() + "§lStarter Food: §f" + ConfigIntegers.STARTER_FOOD.get());
        food.setItemMeta(foodM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+5");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-5");
        fived.setItemMeta(fivedM);
        starterfood.setItem(4, food);
        starterfood.setItem(12, five);
        starterfood.setItem(14, fived);
        player.openInventory(starterfood);
    }
    
    public void TeamSize(final Player player) {
        final Inventory teamsize = Bukkit.createInventory((InventoryHolder)null, 18, GameManager.getGameManager().getMainColor() + "§lTeam Size");
        final ItemStack team = new ItemStack(Material.NAME_TAG);
        final ItemMeta teamM = team.getItemMeta();
        if (TeamManager.getInstance().isTeamsEnabled()) {
            teamM.setDisplayName(this.color + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
        }
        else {
            teamM.setDisplayName(this.color + "§lTeam Size: §fFFA");
        }
        team.setItemMeta(teamM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        teamsize.setItem(4, team);
        teamsize.setItem(12, tr);
        teamsize.setItem(14, fl);
        player.openInventory(teamsize);
    }
    
    public void TeamSize2(final Player player) {
        final Inventory teamsize2 = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lTeam Size Manage");
        final ItemStack team = new ItemStack(Material.NAME_TAG);
        final ItemMeta teamM = team.getItemMeta();
        if (TeamManager.getInstance().isTeamsEnabled()) {
            teamM.setDisplayName(this.color + "§lTeam Size: §fTo" + TeamManager.getInstance().getMaxSize());
        }
        else {
            teamM.setDisplayName(this.color + "§lTeam Size: §fFFA");
        }
        team.setItemMeta(teamM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+1");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-1");
        fived.setItemMeta(fivedM);
        teamsize2.setItem(4, team);
        teamsize2.setItem(12, five);
        teamsize2.setItem(14, fived);
        player.openInventory(teamsize2);
    }
    
    public void Maxplayers(final Player player) {
        final Inventory maxplayers = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lMaxplayers");
        final ItemStack max = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta maxM = max.getItemMeta();
        maxM.setDisplayName(this.color + "§lMaxplayers: §f" + ConfigIntegers.MAX_PLAYERS.get());
        max.setItemMeta(maxM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+5");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-5");
        fived.setItemMeta(fivedM);
        maxplayers.setItem(4, max);
        maxplayers.setItem(12, five);
        maxplayers.setItem(14, fived);
        player.openInventory(maxplayers);
    }
    
    public void HealTime(final Player player) {
        final Inventory heal = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lHeal Time");
        final ItemStack h = new ItemStack(Material.POTION, 1, (short)1);
        final ItemMeta hM = h.getItemMeta();
        hM.setDisplayName(this.color + "§lHeal: §f" + ConfigIntegers.HEAL_TIME.get() + "m");
        h.setItemMeta(hM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+5");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-5");
        fived.setItemMeta(fivedM);
        heal.setItem(4, h);
        heal.setItem(12, five);
        heal.setItem(14, fived);
        player.openInventory(heal);
    }
    
    public void PvpTime(final Player player) {
        final Inventory pvp = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lPvP Time");
        final ItemStack p = new ItemStack(Material.DIAMOND_SWORD);
        final ItemMeta pM = p.getItemMeta();
        pM.setDisplayName(this.color + "§lPvP: §f" + ConfigIntegers.PVP_TIME.get() + "m");
        p.setItemMeta(pM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+5");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-5");
        fived.setItemMeta(fivedM);
        pvp.setItem(4, p);
        pvp.setItem(12, five);
        pvp.setItem(14, fived);
        player.openInventory(pvp);
    }
    
    public void BorderTime(final Player player) {
        final Inventory border = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lBorder Time");
        final ItemStack b = new ItemStack(Material.BEDROCK);
        final ItemMeta bM = b.getItemMeta();
        bM.setDisplayName(this.color + "§lBorder Time: §f" + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m");
        b.setItemMeta(bM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+5");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-5");
        fived.setItemMeta(fivedM);
        border.setItem(4, b);
        border.setItem(12, five);
        border.setItem(14, fived);
        player.openInventory(border);
    }
    
    public void Nether(final Player player) {
        final Inventory nether = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lNether");
        final ItemStack n = new ItemStack(Material.NETHERRACK);
        final ItemMeta nM = n.getItemMeta();
        nM.setDisplayName(this.color + "§lNether: §f" + ConfigBooleans.NETHER.isEnabled());
        n.setItemMeta(nM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        nether.setItem(4, n);
        nether.setItem(12, tr);
        nether.setItem(14, fl);
        player.openInventory(nether);
    }
    
    public void Border(final Player player) {
        final Inventory border = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lBorder Size");
        final ItemStack b = new ItemStack(Material.BEDROCK);
        final ItemMeta bM = b.getItemMeta();
        bM.setDisplayName(this.color + "§lBorder Size: §f" + this.gameManager.getCurrentBorder());
        b.setItemMeta(bM);
        final ItemStack five = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta fiveM = five.getItemMeta();
        fiveM.setDisplayName("§a+500");
        five.setItemMeta(fiveM);
        final ItemStack fived = new ItemStack(Material.INK_SACK, 1, (short)1);
        final ItemMeta fivedM = fived.getItemMeta();
        fivedM.setDisplayName("§c-500");
        fived.setItemMeta(fivedM);
        border.setItem(4, b);
        border.setItem(12, five);
        border.setItem(14, fived);
        player.openInventory(border);
    }
    
    public void Stats(final Player player) {
        final Inventory stats = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lStats");
        final ItemStack s = new ItemStack(Material.DIAMOND);
        final ItemMeta sM = s.getItemMeta();
        if (!this.gameManager.isStatsEnabled()) {
            sM.setDisplayName(this.color + "§lStats: §ffalse");
        }
        else {
            sM.setDisplayName(this.color + "§lStats: §ftrue");
        }
        s.setItemMeta(sM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        stats.setItem(4, s);
        stats.setItem(12, tr);
        stats.setItem(14, fl);
        player.openInventory(stats);
    }
    
    public void Bedbombs(final Player p) {
        final Inventory bedbombs = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lBedbombs");
        final ItemStack b = new ItemStack(Material.BED);
        final ItemMeta bM = b.getItemMeta();
        bM.setDisplayName(this.color + "§lBedbombs: §f" + ConfigBooleans.BEDBOMBS.isEnabled());
        b.setItemMeta(bM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        bedbombs.setItem(4, b);
        bedbombs.setItem(12, tr);
        bedbombs.setItem(14, fl);
        p.openInventory(bedbombs);
    }
    
    public void Speed(final Player p) {
        final Inventory speed = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lSpeed I/II");
        final ItemStack s = new ItemStack(Material.POTION, 1, (short)2);
        final ItemMeta sM = s.getItemMeta();
        sM.setDisplayName(this.color + "§lSpeed I/II: §f" + ConfigBooleans.SPEED.isEnabled());
        s.setItemMeta(sM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        speed.setItem(4, s);
        speed.setItem(12, tr);
        speed.setItem(14, fl);
        p.openInventory(speed);
    }
    
    public void Strength(final Player p) {
        final Inventory strength = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lStrength I");
        final ItemStack s = new ItemStack(Material.POTION, 1, (short)8201);
        final ItemMeta sM = s.getItemMeta();
        sM.setDisplayName(this.color + "§lStrength I: §f" + ConfigBooleans.STRENGTH_1.isEnabled());
        s.setItemMeta(sM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        strength.setItem(4, s);
        strength.setItem(12, tr);
        strength.setItem(14, fl);
        p.openInventory(strength);
    }
    
    public void Poison(final Player p) {
        final Inventory poison = Bukkit.createInventory((InventoryHolder)null, 18, this.color + "§lPoison");
        final ItemStack po = new ItemStack(Material.POTION, 1, (short)16388);
        final ItemMeta poM = po.getItemMeta();
        poM.setDisplayName(this.color + "§lPoison: §f" + ConfigBooleans.POISON.isEnabled());
        po.setItemMeta(poM);
        final ItemStack tr = new ItemStack(Material.WOOL, 1, (short)5);
        final ItemMeta trM = tr.getItemMeta();
        trM.setDisplayName("§aTrue");
        tr.setItemMeta(trM);
        final ItemStack fl = new ItemStack(Material.WOOL, 1, (short)14);
        final ItemMeta flM = fl.getItemMeta();
        flM.setDisplayName("§cFalse");
        fl.setItemMeta(flM);
        poison.setItem(4, po);
        poison.setItem(12, tr);
        poison.setItem(14, fl);
        p.openInventory(poison);
    }
}
