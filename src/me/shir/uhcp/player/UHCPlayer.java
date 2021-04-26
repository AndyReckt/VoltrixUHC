package me.shir.uhcp.player;

import org.bukkit.inventory.meta.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import java.sql.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.game.*;
import java.util.*;

public class UHCPlayer
{
    private UUID uuid;
    private int kills;
    private int diamondsMinedGame;
    private int spawnersMined;
    private boolean playerAlive;
    private boolean playerDied;
    private boolean spectator;
    private boolean teamChatToggled;
    private ItemStack[] armour;
    private ItemStack[] items;
    private Location respawnLocation;
    private boolean usedCommand;
    private Inventory statsInventory;
    private int totalKills;
    private int totalDeath;
    private int highestKillStreak;
    private int wins;
    private int arrowsShot;
    private int arrowsHit;
    private int goldenApplesEaten;
    private int goldenHeadsEaten;
    private int heartsHealed;
    private int zombiesKilled;
    private int creepersKilled;
    private int skeletonsKilled;
    private int caveSpidersKilled;
    private int spidersKilled;
    private int blazesKilled;
    private int ghastsKilled;
    private int cowsKilled;
    private int pigsKilled;
    private int chickensKilled;
    private int horsesKilled;
    private int witchesKilled;
    private int netherEntrances;
    private int horsesTamed;
    private int xpLevelsEarned;
    private int totalDiamondsMined;
    private int totalGoldMined;
    private int totalRedstoneMined;
    private int totalLapisMined;
    private int totalIronMined;
    private int totalCoalMined;
    private int totalQuartzMined;
    private int totalSpawnersMined;
    
    private ItemStack buildItem(final Material type, final String name, final List<String> lore, final int data) {
        final ItemStack ist = new ItemStack(type, 1, (short)data);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName(name);
        im.setLore((List)lore);
        ist.setItemMeta(im);
        return ist;
    }
    
    public void toggleTeamChat(final CommandSender sender) {
        this.teamChatToggled = !this.teamChatToggled;
        sender.sendMessage("\u00ef申\u00ef申aSuccessfully toggled team chat (" + this.teamChatToggled + ")");
    }
    
    public boolean hasTeamChatToggled() {
        return this.teamChatToggled;
    }
    
    public int getSpawnersMined() {
        return this.spawnersMined;
    }
    
    public void addSpawnersMined() {
        ++this.spawnersMined;
    }
    
    public boolean didUseCommand() {
        return this.usedCommand;
    }
    
    public void setUsedCommand(final boolean bool) {
        this.usedCommand = bool;
    }
    
    public void addArrowHit() {
        ++this.arrowsHit;
    }
    
    public void addArrowShot() {
        ++this.arrowsShot;
    }
    
    public void addHorsesTamed() {
        ++this.horsesTamed;
    }
    
    public void addTotalQuartzMined() {
        ++this.totalQuartzMined;
    }
    
    public void addTotalSpawnersMined() {
        ++this.totalSpawnersMined;
    }
    
    public void addHeartsHealed(final int heartsHealed) {
        this.heartsHealed += heartsHealed;
    }
    
    public void addTotalDiamondsMined() {
        ++this.totalDiamondsMined;
    }
    
    public void addTotalRedstoneMined() {
        ++this.totalRedstoneMined;
    }
    
    public void addTotalLapisMined() {
        ++this.totalLapisMined;
    }
    
    public void addTotalIronMined() {
        ++this.totalIronMined;
    }
    
    public void addTotalCoalMined() {
        ++this.totalCoalMined;
    }
    
    public void addTotalGoldMined() {
        ++this.totalGoldMined;
    }
    
    public void addNetherE() {
        ++this.netherEntrances;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public void addWin() {
        ++this.wins;
    }
    
    public void addGoldenApplesEaten() {
        ++this.goldenApplesEaten;
    }
    
    public void addGoldenHeadsEaten() {
        ++this.goldenHeadsEaten;
    }
    
    public void addHorsesKilled() {
        ++this.horsesKilled;
    }
    
    public void addWitchesKilled() {
        ++this.witchesKilled;
    }
    
    public void addCowsKilled() {
        ++this.cowsKilled;
    }
    
    public void addPigsKilled() {
        ++this.pigsKilled;
    }
    
    public void addChickensKilled() {
        ++this.chickensKilled;
    }
    
    public void addBlazesKilled() {
        ++this.blazesKilled;
    }
    
    public void addCaveSpiderKilled() {
        ++this.caveSpidersKilled;
    }
    
    public void addSpidersKilled() {
        ++this.spidersKilled;
    }
    
    public void addZombiesKilled() {
        ++this.zombiesKilled;
    }
    
    public void addCreepersKilled() {
        ++this.creepersKilled;
    }
    
    public void addSkeletonsKilled() {
        ++this.skeletonsKilled;
    }
    
    public void addGhastsKilled() {
        ++this.ghastsKilled;
    }
    
    public void addXPLevel() {
        ++this.xpLevelsEarned;
    }
    
    public int getHighestKillStreak() {
        return this.highestKillStreak;
    }
    
    public double getKd() {
        double kd = 0.0;
        if (this.totalKills > 0 && this.totalDeath == 0) {
            kd = this.totalKills;
        }
        else if (this.totalKills == 0 && this.totalDeath == 0) {
            kd = 0.0;
        }
        else {
            kd = this.totalKills / this.totalDeath;
        }
        return kd;
    }
    
    public void setHighestKillStreak(final int highestKillStreak) {
        this.highestKillStreak = highestKillStreak;
    }
    
    public void addTotalDeath() {
        ++this.totalDeath;
    }
    
    public void addTotalKill() {
        ++this.totalKills;
    }
    
    public UHCPlayer(final boolean loadData, final UUID uuid) {
        this.kills = 0;
        this.diamondsMinedGame = 0;
        this.spawnersMined = 0;
        this.playerAlive = false;
        this.playerDied = false;
        this.spectator = false;
        this.teamChatToggled = false;
        this.usedCommand = false;
        this.statsInventory = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.GREEN + "STATS:");
        this.totalKills = 0;
        this.totalDeath = 0;
        this.highestKillStreak = 0;
        this.wins = 0;
        this.arrowsShot = 0;
        this.arrowsHit = 0;
        this.goldenApplesEaten = 0;
        this.goldenHeadsEaten = 0;
        this.heartsHealed = 0;
        this.zombiesKilled = 0;
        this.creepersKilled = 0;
        this.skeletonsKilled = 0;
        this.caveSpidersKilled = 0;
        this.spidersKilled = 0;
        this.blazesKilled = 0;
        this.ghastsKilled = 0;
        this.cowsKilled = 0;
        this.pigsKilled = 0;
        this.chickensKilled = 0;
        this.horsesKilled = 0;
        this.witchesKilled = 0;
        this.netherEntrances = 0;
        this.horsesTamed = 0;
        this.xpLevelsEarned = 0;
        this.totalDiamondsMined = 0;
        this.totalGoldMined = 0;
        this.totalRedstoneMined = 0;
        this.totalLapisMined = 0;
        this.totalIronMined = 0;
        this.totalCoalMined = 0;
        this.totalQuartzMined = 0;
        this.totalSpawnersMined = 0;
        this.uuid = uuid;
        if (loadData) {
            this.loadData();
        }
    }
    
    public String getName() {
        return Bukkit.getServer().getOfflinePlayer(this.uuid).getName();
    }
    
    public Location getRespawnLocation() {
        return this.respawnLocation;
    }
    
    public void setRespawnLocation(final Location location) {
        this.respawnLocation = location;
    }
    
    public ItemStack[] lastInventory() {
        return this.items;
    }
    
    public ItemStack[] lastArmour() {
        return this.armour;
    }
    
    public void setLastInventory(final ItemStack[] im) {
        this.items = im;
    }
    
    public void setLastArmour(final ItemStack[] im) {
        this.armour = im;
    }
    
    public int getDiamondsMined() {
        return this.diamondsMinedGame;
    }
    
    public int getWins() {
        return this.wins;
    }
    
    public void addDiamond() {
        ++this.diamondsMinedGame;
    }
    
    public void addKill() {
        ++this.kills;
    }
    
    public int getKills() {
        return this.kills;
    }
    
    public void setPlayerAlive(final boolean b) {
        this.playerAlive = b;
    }
    
    public boolean isPlayerAlive() {
        return this.playerAlive;
    }
    
    public boolean didPlayerDie() {
        return this.playerDied;
    }
    
    public void setDied(final boolean playerDead) {
        this.playerDied = playerDead;
    }
    
    public boolean isSpectating() {
        return this.spectator;
    }
    
    void setSpec(final boolean bool) {
        this.spectator = bool;
    }
    
    private void loadData() {
        if (this.hasData()) {
            new BukkitRunnable() {
                public void run() {
                    try {
                        final PreparedStatement statement = VenixUHC.getInstance().getSQL().getConnection().prepareStatement("SELECT * FROM `" + VenixUHC.getInstance().getSQL().table + "` WHERE `uuid` = ?;");
                        statement.setString(1, UHCPlayer.this.uuid.toString());
                        statement.executeQuery();
                        final ResultSet rs = statement.getResultSet();
                        if (rs.isBeforeFirst()) {
                            while (rs.next()) {
                                UHCPlayer.access$1(UHCPlayer.this, rs.getInt("wins"));
                                UHCPlayer.access$2(UHCPlayer.this, rs.getInt("kills"));
                                UHCPlayer.access$3(UHCPlayer.this, rs.getInt("deaths"));
                                UHCPlayer.access$4(UHCPlayer.this, rs.getInt("higheststreak"));
                                UHCPlayer.access$5(UHCPlayer.this, rs.getInt("arrowsshot"));
                                UHCPlayer.access$6(UHCPlayer.this, rs.getInt("arrowshit"));
                                UHCPlayer.access$7(UHCPlayer.this, rs.getInt("goldenappleseaten"));
                                UHCPlayer.access$8(UHCPlayer.this, rs.getInt("goldenheadseaten"));
                                UHCPlayer.access$9(UHCPlayer.this, rs.getInt("heartshealed"));
                                UHCPlayer.access$10(UHCPlayer.this, rs.getInt("zombieskilled"));
                                UHCPlayer.access$11(UHCPlayer.this, rs.getInt("creeperskilled"));
                                UHCPlayer.access$12(UHCPlayer.this, rs.getInt("skeletonskilled"));
                                UHCPlayer.access$13(UHCPlayer.this, rs.getInt("cavespiderskilled"));
                                UHCPlayer.access$14(UHCPlayer.this, rs.getInt("spiderskilled"));
                                UHCPlayer.access$15(UHCPlayer.this, rs.getInt("blazeskilled"));
                                UHCPlayer.access$16(UHCPlayer.this, rs.getInt("ghastskilled"));
                                UHCPlayer.access$17(UHCPlayer.this, rs.getInt("cowskilled"));
                                UHCPlayer.access$18(UHCPlayer.this, rs.getInt("pigskilled"));
                                UHCPlayer.access$19(UHCPlayer.this, rs.getInt("chickenskilled"));
                                UHCPlayer.access$20(UHCPlayer.this, rs.getInt("horseskilled"));
                                UHCPlayer.access$21(UHCPlayer.this, rs.getInt("witcheskilled"));
                                UHCPlayer.access$22(UHCPlayer.this, rs.getInt("netherentrances"));
                                UHCPlayer.access$23(UHCPlayer.this, rs.getInt("horsestamed"));
                                UHCPlayer.access$24(UHCPlayer.this, rs.getInt("xplevelsearned"));
                                UHCPlayer.access$25(UHCPlayer.this, rs.getInt("diamondsmined"));
                                UHCPlayer.access$26(UHCPlayer.this, rs.getInt("goldmined"));
                                UHCPlayer.access$27(UHCPlayer.this, rs.getInt("redstonemined"));
                                UHCPlayer.access$28(UHCPlayer.this, rs.getInt("lapismined"));
                                UHCPlayer.access$29(UHCPlayer.this, rs.getInt("ironmined"));
                                UHCPlayer.access$30(UHCPlayer.this, rs.getInt("coalmined"));
                                UHCPlayer.access$31(UHCPlayer.this, rs.getInt("quartzmined"));
                                UHCPlayer.access$32(UHCPlayer.this, rs.getInt("spawnersmined"));
                            }
                        }
                        rs.close();
                        statement.close();
                    }
                    catch (SQLException e) {
                        System.out.print(e.getMessage());
                    }
                }
            }.runTaskAsynchronously((Plugin)VenixUHC.getInstance());
        }
        else {
            System.out.println("Could not find data for UHCPlayer: " + this.getUuid());
        }
    }
    
    public void createData() {
        new BukkitRunnable() {
            public void run() {
                PreparedStatement statement = null;
                try {
                    statement = VenixUHC.getInstance().getSQL().getConnection().prepareStatement("INSERT INTO `" + VenixUHC.getInstance().getSQL().table + "` (uuid, wins, kills, deaths, kd, higheststreak, arrowsshot, arrowshit, goldenappleseaten, goldenheadseaten, " + "heartshealed, zombieskilled, creeperskilled, skeletonskilled, cavespiderskilled, spiderskilled, blazeskilled, ghastskilled, cowskilled, pigskilled, chickenskilled, " + "horseskilled, witcheskilled, netherentrances, horsestamed, xplevelsearned, diamondsmined, goldmined, redstonemined, lapismined, ironmined, coalmined, quartzmined, spawnersmined) VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                    statement.setString(1, UHCPlayer.this.uuid.toString());
                    statement.setInt(2, UHCPlayer.this.wins);
                    statement.setInt(3, UHCPlayer.this.totalKills);
                    statement.setInt(4, UHCPlayer.this.totalDeath);
                    statement.setDouble(5, UHCPlayer.this.getKd());
                    statement.setInt(6, UHCPlayer.this.highestKillStreak);
                    statement.setInt(7, UHCPlayer.this.arrowsShot);
                    statement.setInt(8, UHCPlayer.this.arrowsHit);
                    statement.setInt(9, UHCPlayer.this.goldenApplesEaten);
                    statement.setInt(10, UHCPlayer.this.goldenHeadsEaten);
                    statement.setInt(11, UHCPlayer.this.heartsHealed);
                    statement.setInt(12, UHCPlayer.this.zombiesKilled);
                    statement.setInt(13, UHCPlayer.this.creepersKilled);
                    statement.setInt(14, UHCPlayer.this.skeletonsKilled);
                    statement.setInt(15, UHCPlayer.this.caveSpidersKilled);
                    statement.setInt(16, UHCPlayer.this.spidersKilled);
                    statement.setInt(17, UHCPlayer.this.blazesKilled);
                    statement.setInt(18, UHCPlayer.this.ghastsKilled);
                    statement.setInt(19, UHCPlayer.this.cowsKilled);
                    statement.setInt(20, UHCPlayer.this.pigsKilled);
                    statement.setInt(21, UHCPlayer.this.chickensKilled);
                    statement.setInt(22, UHCPlayer.this.horsesKilled);
                    statement.setInt(23, UHCPlayer.this.witchesKilled);
                    statement.setInt(24, UHCPlayer.this.netherEntrances);
                    statement.setInt(25, UHCPlayer.this.horsesTamed);
                    statement.setInt(26, UHCPlayer.this.xpLevelsEarned);
                    statement.setInt(27, UHCPlayer.this.totalDiamondsMined);
                    statement.setInt(28, UHCPlayer.this.totalGoldMined);
                    statement.setInt(29, UHCPlayer.this.totalRedstoneMined);
                    statement.setInt(30, UHCPlayer.this.totalLapisMined);
                    statement.setInt(31, UHCPlayer.this.totalIronMined);
                    statement.setInt(32, UHCPlayer.this.totalCoalMined);
                    statement.setInt(33, UHCPlayer.this.totalQuartzMined);
                    statement.setInt(34, UHCPlayer.this.totalSpawnersMined);
                    statement.executeUpdate();
                }
                catch (SQLException ex) {}
            }
        }.runTaskAsynchronously((Plugin)VenixUHC.getInstance());
    }
    
    public void saveData() {
        if (this.hasData()) {
            new BukkitRunnable() {
                public void run() {
                    PreparedStatement statement = null;
                    try {
                        statement = VenixUHC.getInstance().getSQL().getConnection().prepareStatement("UPDATE `" + VenixUHC.getInstance().getSQL().table + "` SET `wins` = ?, `kills` = ?, `deaths` = ?, `kd` = ?, `higheststreak` = ?, `arrowsshot` = ?, " + "`arrowshit` = ?,`goldenappleseaten` = ?, `goldenheadseaten` = ?, `heartshealed` = ?, `zombieskilled` = ?, " + "`creeperskilled` = ?, `skeletonskilled` = ?, `cavespiderskilled` = ?, `spiderskilled` = ?, `blazeskilled` = ?, " + "`ghastskilled` = ?, `cowskilled` = ?, `pigskilled` = ?, `chickenskilled` = ?, `horseskilled` = ?, `witcheskilled` = ?, " + "`netherentrances` = ?, `horsestamed` = ?, `xplevelsearned` = ?, `diamondsmined` = ?, `goldmined` = ?, `redstonemined` = ?, " + "`lapismined` = ?, `ironmined` = ?, `coalmined` = ?, `quartzmined` = ?, `spawnersmined` = ? WHERE `uuid` = ?;");
                        statement.setInt(1, UHCPlayer.this.wins);
                        statement.setInt(2, UHCPlayer.this.totalKills);
                        statement.setInt(3, UHCPlayer.this.totalDeath);
                        statement.setDouble(4, UHCPlayer.this.getKd());
                        statement.setInt(5, UHCPlayer.this.highestKillStreak);
                        statement.setInt(6, UHCPlayer.this.arrowsShot);
                        statement.setInt(7, UHCPlayer.this.arrowsHit);
                        statement.setInt(8, UHCPlayer.this.goldenApplesEaten);
                        statement.setInt(9, UHCPlayer.this.goldenHeadsEaten);
                        statement.setInt(10, UHCPlayer.this.heartsHealed);
                        statement.setInt(11, UHCPlayer.this.zombiesKilled);
                        statement.setInt(12, UHCPlayer.this.creepersKilled);
                        statement.setInt(13, UHCPlayer.this.skeletonsKilled);
                        statement.setInt(14, UHCPlayer.this.caveSpidersKilled);
                        statement.setInt(15, UHCPlayer.this.spidersKilled);
                        statement.setInt(16, UHCPlayer.this.blazesKilled);
                        statement.setInt(17, UHCPlayer.this.ghastsKilled);
                        statement.setInt(18, UHCPlayer.this.cowsKilled);
                        statement.setInt(19, UHCPlayer.this.pigsKilled);
                        statement.setInt(20, UHCPlayer.this.chickensKilled);
                        statement.setInt(21, UHCPlayer.this.horsesKilled);
                        statement.setInt(22, UHCPlayer.this.witchesKilled);
                        statement.setInt(23, UHCPlayer.this.netherEntrances);
                        statement.setInt(24, UHCPlayer.this.horsesTamed);
                        statement.setInt(25, UHCPlayer.this.xpLevelsEarned);
                        statement.setInt(26, UHCPlayer.this.totalDiamondsMined);
                        statement.setInt(27, UHCPlayer.this.totalGoldMined);
                        statement.setInt(28, UHCPlayer.this.totalRedstoneMined);
                        statement.setInt(29, UHCPlayer.this.totalLapisMined);
                        statement.setInt(30, UHCPlayer.this.totalIronMined);
                        statement.setInt(31, UHCPlayer.this.totalCoalMined);
                        statement.setInt(32, UHCPlayer.this.totalQuartzMined);
                        statement.setInt(33, UHCPlayer.this.totalSpawnersMined);
                        statement.setString(34, UHCPlayer.this.uuid.toString());
                        statement.executeUpdate();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            assert statement != null;
                            statement.close();
                        }
                        catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        return;
                    }
                    finally {
                        try {
                            assert statement != null;
                            statement.close();
                        }
                        catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                    try {
                        assert statement != null;
                        statement.close();
                    }
                    catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                }
            }.runTaskAsynchronously((Plugin)VenixUHC.getInstance());
        }
        else {
            this.createData();
        }
    }
    
    public boolean hasData() {
        PreparedStatement statement1 = null;
        try {
            statement1 = VenixUHC.getInstance().getSQL().getConnection().prepareStatement("SELECT `kills` FROM `" + VenixUHC.getInstance().getSQL().table + "` WHERE `uuid` = ?;");
            statement1.setString(1, this.uuid.toString());
            statement1.executeQuery();
            final ResultSet rs = statement1.getResultSet();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            try {
                assert statement1 != null;
                statement1.close();
            }
            catch (SQLException e2) {
                System.out.print(e2.getMessage());
            }
            return false;
        }
        finally {
            try {
                assert statement1 != null;
                statement1.close();
            }
            catch (SQLException e2) {
                System.out.print(e2.getMessage());
            }
        }
        try {
            assert statement1 != null;
            statement1.close();
        }
        catch (SQLException e2) {
            System.out.print(e2.getMessage());
        }
        return false;
    }
    
    public Inventory getStatsInventory() {
        final ChatColor color = GameManager.getGameManager().getMainColor();
        final ChatColor color2 = GameManager.getGameManager().getSecondaryColor();
        this.statsInventory.clear();
        this.statsInventory.setItem(0, this.buildItem(Material.DIAMOND, color + "Wins: " + color2 + this.wins, null, 0));
        this.statsInventory.setItem(1, this.buildItem(Material.DIAMOND_SWORD, color + "Kills: " + color2 + this.totalKills, null, 0));
        this.statsInventory.setItem(2, this.buildItem(Material.SKULL_ITEM, color + "Deaths: " + color2 + this.totalDeath, null, 0));
        this.statsInventory.setItem(3, this.buildItem(Material.BLAZE_ROD, color + "KD: " + color2 + this.getKd(), null, 0));
        this.statsInventory.setItem(4, this.buildItem(Material.DIAMOND_AXE, color + "Highest Kill Streak: " + color2 + this.highestKillStreak, null, 0));
        final List<String> lore = new ArrayList<String>();
        lore.add(color + "Arrows Shot: " + color2 + this.arrowsShot);
        lore.add(color + "Arrows Hit: " + color2 + this.arrowsHit);
        this.statsInventory.setItem(5, this.buildItem(Material.BOW, "\u00ef申\u00ef申aBow Statistics", lore, 0));
        lore.clear();
        lore.add(color + "Golden Apples Eaten: " + color2 + this.goldenApplesEaten);
        lore.add(color + "Golden Heads Eaten: " + color2 + this.goldenHeadsEaten);
        this.statsInventory.setItem(6, this.buildItem(Material.GOLDEN_APPLE, "\u00ef申\u00ef申aItems Consumed", lore, 0));
        this.statsInventory.setItem(7, this.buildItem(Material.POTION, color + "Hearts Healed: " + color2 + this.heartsHealed, null, 3738261));
        this.statsInventory.setItem(8, this.buildItem(Material.EXP_BOTTLE, color + "XP Levels Earned: " + color2 + this.xpLevelsEarned, null, 0));
        this.statsInventory.setItem(9, this.buildItem(Material.SADDLE, color + "Horses Tamed: " + color2 + this.horsesTamed, null, 0));
        lore.clear();
        lore.add(color + "Zombies Killed: " + color2 + this.zombiesKilled);
        lore.add(color + "Creepers Killed: " + color2 + this.creepersKilled);
        lore.add(color + "Skeletons Killed: " + color2 + this.skeletonsKilled);
        lore.add(color + "Cave Spiders Killed: " + color2 + this.caveSpidersKilled);
        lore.add(color + "Spiders Killed: " + color + this.spidersKilled);
        lore.add(color + "Blazes Killed: " + color2 + this.blazesKilled);
        lore.add(color + "Ghasts Killed: " + color2 + this.ghastsKilled);
        lore.add(color + "Witches Killed: " + color2 + this.witchesKilled);
        lore.add(color + "Cows Killed: " + color2 + this.cowsKilled);
        lore.add(color + "Pigs Killed: " + color2 + this.pigsKilled);
        lore.add(color + "Chickens Killed: " + color2 + this.chickensKilled);
        lore.add(color + "Horses Killed: " + color2 + this.horsesKilled);
        this.statsInventory.setItem(10, this.buildItem(Material.MONSTER_EGG, "\u00ef申\u00ef申aMobs Killed", lore, 54));
        lore.clear();
        lore.add(color + "Diamonds Mined: " + color2 + this.totalDiamondsMined);
        lore.add(color + "Gold Mined: " + color2 + this.totalGoldMined);
        lore.add(color + "Iron Mined: " + color2 + this.totalIronMined);
        lore.add(color + "Coal Mined: " + color2 + this.totalCoalMined);
        lore.add(color + "Redstone Mined: " + color2 + this.totalRedstoneMined);
        lore.add(color + "Lapis Mined: " + color2 + this.totalLapisMined);
        lore.add(color + "Quartz Mined: " + color2 + this.totalQuartzMined);
        lore.add(color + "Spawners Mined: " + color2 + this.totalSpawnersMined);
        this.statsInventory.setItem(11, this.buildItem(Material.DIAMOND_ORE, "\u00ef申\u00ef申aOres Mined", lore, 0));
        this.statsInventory.setItem(12, this.buildItem(Material.NETHER_BRICK, "\u00ef申\u00ef申aNether Entrances: " + color2 + this.netherEntrances, null, 0));
        return this.statsInventory;
    }
    
    public boolean isOnline() {
        return false;
    }
    
    static /* synthetic */ void access$1(final UHCPlayer uhcPlayer, final int wins) {
        uhcPlayer.wins = wins;
    }
    
    static /* synthetic */ void access$2(final UHCPlayer uhcPlayer, final int totalKills) {
        uhcPlayer.totalKills = totalKills;
    }
    
    static /* synthetic */ void access$3(final UHCPlayer uhcPlayer, final int totalDeath) {
        uhcPlayer.totalDeath = totalDeath;
    }
    
    static /* synthetic */ void access$4(final UHCPlayer uhcPlayer, final int highestKillStreak) {
        uhcPlayer.highestKillStreak = highestKillStreak;
    }
    
    static /* synthetic */ void access$5(final UHCPlayer uhcPlayer, final int arrowsShot) {
        uhcPlayer.arrowsShot = arrowsShot;
    }
    
    static /* synthetic */ void access$6(final UHCPlayer uhcPlayer, final int arrowsHit) {
        uhcPlayer.arrowsHit = arrowsHit;
    }
    
    static /* synthetic */ void access$7(final UHCPlayer uhcPlayer, final int goldenApplesEaten) {
        uhcPlayer.goldenApplesEaten = goldenApplesEaten;
    }
    
    static /* synthetic */ void access$8(final UHCPlayer uhcPlayer, final int goldenHeadsEaten) {
        uhcPlayer.goldenHeadsEaten = goldenHeadsEaten;
    }
    
    static /* synthetic */ void access$9(final UHCPlayer uhcPlayer, final int heartsHealed) {
        uhcPlayer.heartsHealed = heartsHealed;
    }
    
    static /* synthetic */ void access$10(final UHCPlayer uhcPlayer, final int zombiesKilled) {
        uhcPlayer.zombiesKilled = zombiesKilled;
    }
    
    static /* synthetic */ void access$11(final UHCPlayer uhcPlayer, final int creepersKilled) {
        uhcPlayer.creepersKilled = creepersKilled;
    }
    
    static /* synthetic */ void access$12(final UHCPlayer uhcPlayer, final int skeletonsKilled) {
        uhcPlayer.skeletonsKilled = skeletonsKilled;
    }
    
    static /* synthetic */ void access$13(final UHCPlayer uhcPlayer, final int caveSpidersKilled) {
        uhcPlayer.caveSpidersKilled = caveSpidersKilled;
    }
    
    static /* synthetic */ void access$14(final UHCPlayer uhcPlayer, final int spidersKilled) {
        uhcPlayer.spidersKilled = spidersKilled;
    }
    
    static /* synthetic */ void access$15(final UHCPlayer uhcPlayer, final int blazesKilled) {
        uhcPlayer.blazesKilled = blazesKilled;
    }
    
    static /* synthetic */ void access$16(final UHCPlayer uhcPlayer, final int ghastsKilled) {
        uhcPlayer.ghastsKilled = ghastsKilled;
    }
    
    static /* synthetic */ void access$17(final UHCPlayer uhcPlayer, final int cowsKilled) {
        uhcPlayer.cowsKilled = cowsKilled;
    }
    
    static /* synthetic */ void access$18(final UHCPlayer uhcPlayer, final int pigsKilled) {
        uhcPlayer.pigsKilled = pigsKilled;
    }
    
    static /* synthetic */ void access$19(final UHCPlayer uhcPlayer, final int chickensKilled) {
        uhcPlayer.chickensKilled = chickensKilled;
    }
    
    static /* synthetic */ void access$20(final UHCPlayer uhcPlayer, final int horsesKilled) {
        uhcPlayer.horsesKilled = horsesKilled;
    }
    
    static /* synthetic */ void access$21(final UHCPlayer uhcPlayer, final int witchesKilled) {
        uhcPlayer.witchesKilled = witchesKilled;
    }
    
    static /* synthetic */ void access$22(final UHCPlayer uhcPlayer, final int netherEntrances) {
        uhcPlayer.netherEntrances = netherEntrances;
    }
    
    static /* synthetic */ void access$23(final UHCPlayer uhcPlayer, final int horsesTamed) {
        uhcPlayer.horsesTamed = horsesTamed;
    }
    
    static /* synthetic */ void access$24(final UHCPlayer uhcPlayer, final int xpLevelsEarned) {
        uhcPlayer.xpLevelsEarned = xpLevelsEarned;
    }
    
    static /* synthetic */ void access$25(final UHCPlayer uhcPlayer, final int totalDiamondsMined) {
        uhcPlayer.totalDiamondsMined = totalDiamondsMined;
    }
    
    static /* synthetic */ void access$26(final UHCPlayer uhcPlayer, final int totalGoldMined) {
        uhcPlayer.totalGoldMined = totalGoldMined;
    }
    
    static /* synthetic */ void access$27(final UHCPlayer uhcPlayer, final int totalRedstoneMined) {
        uhcPlayer.totalRedstoneMined = totalRedstoneMined;
    }
    
    static /* synthetic */ void access$28(final UHCPlayer uhcPlayer, final int totalLapisMined) {
        uhcPlayer.totalLapisMined = totalLapisMined;
    }
    
    static /* synthetic */ void access$29(final UHCPlayer uhcPlayer, final int totalIronMined) {
        uhcPlayer.totalIronMined = totalIronMined;
    }
    
    static /* synthetic */ void access$30(final UHCPlayer uhcPlayer, final int totalCoalMined) {
        uhcPlayer.totalCoalMined = totalCoalMined;
    }
    
    static /* synthetic */ void access$31(final UHCPlayer uhcPlayer, final int totalQuartzMined) {
        uhcPlayer.totalQuartzMined = totalQuartzMined;
    }
    
    static /* synthetic */ void access$32(final UHCPlayer uhcPlayer, final int totalSpawnersMined) {
        uhcPlayer.totalSpawnersMined = totalSpawnersMined;
    }
}
