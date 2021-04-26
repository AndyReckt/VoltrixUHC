package me.shir.uhcp.game;

import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.*;
import org.bukkit.inventory.*;
import me.shir.uhcp.*;
import me.shir.uhcp.teams.*;
import org.bukkit.inventory.meta.*;
import me.shir.uhcp.player.*;
import com.nametagedit.plugin.*;
import java.util.*;
import org.bukkit.*;
import me.shir.uhcp.configs.*;
import org.bukkit.plugin.*;

public class GameManager
{
    private static GameManager instance;
    private final FileConfiguration config;
    private final Map<Player, Scoreboard> scoreboardMap;
    private HashSet<String> whitelist;
    private boolean whitelistEnabled;
    private String name;
    private String scoreboardTitle;
    private String scoreboardIP;
    private String prefix;
    private String borderPrefix;
    private String configPrefix;
    private String errorPrefix;
    private String xrayPrefix;
    private String helpopPrefix;
    private String hostPrefix;
    private String moderatorPrefix;
    private String spectatorPrefix;
    private String kickDeathMessage;
    private String ts;
    private String restartCommand;
    private String uhcWorld;
    private final ChatColor mainColor;
    private final ChatColor secondaryC;
    private World spawnWorld;
    private int defaultMapSize;
    private int currentBorder;
    private int killOnQuitTime;
    private int helpopCoolDown;
    private int randomShrinkAtAndBelow;
    private int deathKickTime;
    private int scatterTicks;
    private boolean borderShrinks;
    private boolean canOpenInv;
    private boolean statsEnabled;
    private boolean canUseRescatter;
    private boolean gameRunning;
    private boolean scattering;
    private boolean pvpEnabled;
    private boolean mapGenerating;
    private boolean restarted;
    private boolean worldUsed;
    private boolean generated;
    private boolean lobbySb;
    private boolean kickPlayerOnDeath;
    private boolean restartOnEnd;
    private final Set<Player> moderators;
    private final Set<Chunk> chunks;
    private final Material starterFood;
    private final ItemStack playersAlive;
    private final ItemStack freeze;
    private final ItemStack inventory;
    private final ItemStack topdia;
    private final ItemStack netherplayers;
    private final ItemStack staffonline;
    private final ItemStack xalerts;
    private final ItemStack randomPlayer;
    private Player host;
    
    public GameManager() {
        this.name = "PloverUHC";
        this.scoreboardTitle = "§a§lVoltrix §f§lNetwork";
        this.scoreboardIP = "§b@VoltrixPvP";
        this.prefix = "§6§lVoltrix §8» ";
        this.borderPrefix = "§7[§6§lBorder§7] ";
        this.configPrefix = "§e§lConfig §8» ";
        this.errorPrefix = "§e§lError §8» ";
        this.xrayPrefix = "§e§lX-Ray §8» ";
        this.helpopPrefix = "§8(§eHelpop§8) ";
        this.hostPrefix = "§7§l[§6§lGame-Host&7&l]§6";
        this.moderatorPrefix = "§7§l[§3§lGame-Moderator§7§l]§3";
        this.spectatorPrefix = "§e[Spectator]§f";
        this.kickDeathMessage = "§6§lVoltrix §8» §aThanks for playing! Better luck next time.";
        this.ts = "voltrix.ts.io";
        this.restartCommand = "/stop";
        this.uhcWorld = "uhcworld";
        this.killOnQuitTime = 3;
        this.helpopCoolDown = 60;
        this.randomShrinkAtAndBelow = 500;
        this.deathKickTime = 30;
        this.scatterTicks = 1;
        this.lobbySb = true;
        this.kickPlayerOnDeath = true;
        this.restartOnEnd = true;
        this.freeze = this.newItem(Material.PACKED_ICE, "§bFreeze Player", 0);
        this.inventory = this.newItem(Material.BOOK, "§cOpen Inventory", 0);
        this.topdia = this.newItem(Material.DIAMOND_ORE, "§bTop Diamonds", 0);
        this.netherplayers = this.newItem(Material.NETHERRACK, "§cPlayers in Nether", 0);
        this.staffonline = this.newItem(Material.BEACON, "§3Staff Online", 3);
        this.xalerts = this.newItem(Material.REDSTONE_COMPARATOR, "§4Alerts", 0);
        this.randomPlayer = this.newItem(Material.COMPASS, "§aRandom Player", 0);
        this.config = VenixUHC.getInstance().getConfig();
        this.scoreboardMap = new HashMap<Player, Scoreboard>();
        this.whitelist = new HashSet<String>();
        this.whitelistEnabled = true;
        this.name = "BrunoUHC";
        this.scoreboardTitle = "§a§lVoltrix §f§lNetwork";
        this.scoreboardIP = "§b@Voltrix";
        this.prefix = "§6§lVoltrix §8» ";
        this.borderPrefix = "§7[§6§lBorder§7] ";
        this.configPrefix = "§e§lConfig §8» ";
        this.errorPrefix = "§e§lError §8» ";
        this.xrayPrefix = "§e§lX-Ray §8» ";
        this.helpopPrefix = "§8(§eHelpop§8) ";
        this.hostPrefix = "§7§l[§6§lGame-Host&7&l]§6";
        this.moderatorPrefix = "§7§l[§3§lGame-Moderator§7§l]§3";
        this.spectatorPrefix = "§e[Spectator]§f";
        this.kickDeathMessage = "§6§lVoltrix §8» §aThanks for playing! Better luck next time.";
        this.ts = "voltrix.ts.io";
        this.restartCommand = "/stop";
        this.uhcWorld = "uhcworld";
        this.mainColor = ChatColor.valueOf("GREEN".toUpperCase());
        this.secondaryC = ChatColor.valueOf("WHITE".toUpperCase());
        this.spawnWorld = Bukkit.getWorld("MineMexMap");
        this.defaultMapSize = 2000;
        this.currentBorder = this.defaultMapSize;
        this.killOnQuitTime = 3;
        this.helpopCoolDown = 60;
        this.randomShrinkAtAndBelow = 500;
        this.deathKickTime = 30;
        this.scatterTicks = 1;
        this.borderShrinks = true;
        this.canOpenInv = true;
        this.statsEnabled = this.config.getBoolean("stats.enabled");
        this.canUseRescatter = true;
        this.gameRunning = false;
        this.scattering = false;
        this.pvpEnabled = false;
        this.mapGenerating = false;
        this.restarted = this.config.getBoolean("DO-NOT-TOUCH.wrs");
        this.worldUsed = this.config.getBoolean("DO-NOT-TOUCH.wmu");
        this.generated = this.config.getBoolean("DO-NOT-TOUCH.wgen");
        this.lobbySb = true;
        this.kickPlayerOnDeath = true;
        this.restartOnEnd = false;
        this.moderators = new HashSet<Player>();
        this.chunks = new HashSet<Chunk>();
        this.starterFood = Material.valueOf("COOKED_BEEF");
        this.playersAlive = this.newItem(Material.SKULL_ITEM, this.mainColor + "Players Alive", 3);
    }
    
    public boolean canBorderShrink() {
        return this.borderShrinks;
    }
    
    public void setCanBorderShrink(final boolean borderShrinks) {
        this.borderShrinks = borderShrinks;
    }
    
    public String getRestartCommand() {
        return "/stop";
    }
    
    public String gameType() {
        if (TeamManager.getInstance().isTeamsEnabled()) {
            return "To" + TeamManager.getInstance().getMaxSize();
        }
        return "FFA";
    }
    
    public int getScatterTicks() {
        return 1;
    }
    
    public String getTS() {
        return "voltrix.ts.io";
    }
    
    public World getSpawnWorld() {
        return this.spawnWorld;
    }
    
    public static GameManager getGameManager() {
        return GameManager.instance;
    }
    
    public Set<Chunk> getChunks() {
        return this.chunks;
    }
    
    public ItemStack newItem(final Material mat, final String s, final int data) {
        final ItemStack ist = new ItemStack(mat, 1, (short)data);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName(s);
        ist.setItemMeta(im);
        return ist;
    }
    
    public HashSet getWhitelist() {
        return this.whitelist;
    }
    
    public boolean addWhitelist(final OfflinePlayer p) {
        return this.whitelist.add(p.getName().toLowerCase());
    }
    
    public boolean removeWhitelist(final OfflinePlayer p) {
        return this.whitelist.remove(p.getName().toLowerCase());
    }
    
    public boolean isWhitelisted(final OfflinePlayer p) {
        return this.whitelist.contains(p.getName().toLowerCase());
    }
    
    public boolean isWhitelistEnabled() {
        return this.whitelistEnabled;
    }
    
    public void setWhitelist(final boolean b) {
        this.whitelistEnabled = b;
    }
    
    public boolean lobbyScoreboard() {
        return true;
    }
    
    public boolean openInvOnRightClick() {
        return this.canOpenInv;
    }
    
    public ItemStack getTopDia() {
        return this.topdia;
    }
    
    public boolean canUseRescatter() {
        return this.canUseRescatter;
    }
    
    void setCanUseRescatter() {
        this.canUseRescatter = false;
    }
    
    public String getSpectatorPrefix() {
        return "§e[Spectator]§f";
    }
    
    public ItemStack getFreezeItem() {
        return this.freeze;
    }
    
    public ItemStack getPlayersNether() {
        return this.netherplayers;
    }
    
    public ItemStack getStaffonline() {
        return this.staffonline;
    }
    
    public ItemStack getXalerts() {
        return this.xalerts;
    }
    
    public String getErrorPrefix() {
        return "§e§lError §8» ";
    }
    
    public boolean kickPlayerOnDeath() {
        return true;
    }
    
    public int getDeathKickTime() {
        return 30;
    }
    
    public String getKickDeathMessage() {
        return "§6§lVoltrix §8» §aThanks for playing! Better luck next time.";
    }
    
    public String getXrayPrefix() {
        return "§e§lX-Ray §8» ";
    }
    
    public ItemStack getItemInventory() {
        return this.inventory;
    }
    
    public int getHelpopCoolDown() {
        return 60;
    }
    
    public int getKillOnQuitTime() {
        return 3;
    }
    
    String getScoreboardIP() {
        return "§b@VoltrixPvP";
    }
    
    String getScoreboardTitle() {
        return "§a§lVoltrix §f§lNetwork";
    }
    
    Material getStarterFood() {
        return this.starterFood;
    }
    
    public boolean isStatsEnabled() {
        return this.statsEnabled;
    }
    
    public static void setInstance() {
        GameManager.instance = new GameManager();
    }
    
    public boolean restartOnEnd() {
        return true;
    }
    
    public Map<Player, Scoreboard> getScoreboardMap() {
        return this.scoreboardMap;
    }
    
    public String getHostPrefix() {
        return "§7§l[§6§lGame-Host§7§l]§3";
    }
    
    public String getModeratorPrefix() {
        return "§7§l[§3§lGame-Moderator§7§l]§3";
    }
    
    public void setHost(final Player host) {
        if (host != null) {
            host.sendMessage(String.valueOf(String.valueOf(this.getPrefix())) + "§fYou are now " + this.getMainColor() + "hosting §fthis game!");
        }
        this.host = host;
    }
    
    public boolean wasWorldUsed() {
        return this.worldUsed;
    }
    
    public void setWorldWasUsed(final boolean worldUsed) {
        VenixUHC.getInstance().reloadConfig();
        VenixUHC.getInstance().getConfig().set("DO-NOT-TOUCH.wmu", (Object)worldUsed);
        VenixUHC.getInstance().saveConfig();
        this.worldUsed = worldUsed;
    }
    
    public boolean wasRestarted() {
        return this.restarted;
    }
    
    public void setRestarted(final boolean wasRestarted) {
        VenixUHC.getInstance().reloadConfig();
        VenixUHC.getInstance().getConfig().set("DO-NOT-TOUCH.wrs", (Object)wasRestarted);
        VenixUHC.getInstance().saveConfig();
        this.restarted = wasRestarted;
    }
    
    public void setStats(final boolean stats) {
        this.statsEnabled = stats;
    }
    
    public String getHostName() {
        if (this.host == null) {
            return "";
        }
        return this.host.getName();
    }
    
    public String getHelpopPrefix() {
        return "§8(§eHelpop§8) ";
    }
    
    public Set<String> getModeratorsNames() {
        final Set<String> mods = new HashSet<String>();
        for (final Player p : this.moderators) {
            mods.add(p.getName());
        }
        return mods;
    }
    
    public Set<Player> getModerators() {
        return this.moderators;
    }
    
    public void setModerator(final Player player, final boolean b) {
        if (b) {
            PlayerManager.getPlayerManager().setSpectating(true, player);
            this.moderators.add(player);
            player.sendMessage(String.valueOf(String.valueOf(this.getPrefix())) + "§fYou are now " + this.getMainColor() + "modding §fthis game!");
            NametagEdit.getApi().setNametag(player, "§e", "");
        }
        else {
            PlayerManager.getPlayerManager().setSpectating(false, player);
            this.moderators.remove(player);
            player.sendMessage(String.valueOf(String.valueOf(this.getPrefix())) + "§fYou are no longer " + this.getMainColor() + "modding §fthis game!");
            NametagEdit.getApi().clearNametag(player);
        }
    }
    
    public Location getScatterLocation() {
        final Random r = new Random();
        final int x = r.nextInt(this.currentBorder * 2) - this.currentBorder;
        final int z = r.nextInt(this.currentBorder * 2) - this.currentBorder;
        return new Location(this.getUHCWorld(), (double)x, this.getUHCWorld().getHighestBlockYAt(x, z) + 0.5, (double)z);
    }
    
    public Location getSpawnLocation() {
        return new Location(this.spawnWorld, (double)this.config.getInt("settings.spawnLocation.x"), (double)this.config.getInt("settings.spawnLocation.y"), (double)this.config.getInt("settings.spawnLocation.z"));
    }
    
    public boolean isPvpEnabled() {
        return this.pvpEnabled;
    }
    
    public void setPvP(final boolean enabled) {
        if (this.getUHCWorld() != null) {
            this.pvpEnabled = enabled;
            this.getUHCWorld().setPVP(enabled);
            if (Bukkit.getWorld("uhcworld_nether") != null) {
                Bukkit.getWorld("uhcworld_nether").setPVP(enabled);
            }
            if (enabled) {
                Player[] onlinePlayers;
                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                    final Player p = onlinePlayers[i];
                    p.sendMessage("§6§lVoltrix §8» " + this.mainColor + "PvP §fhas been " + this.mainColor + "enabled! §fGood luck!");
                }
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player all = onlinePlayers2[j];
                    all.playSound(all.getLocation(), Sound.ORB_PICKUP, 2.0f, 1.0f);
                }
            }
            else {
                Player[] onlinePlayers3;
                for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                    final Player p = onlinePlayers3[k];
                    p.sendMessage("§6§lVoltrix §8» " + this.mainColor + "PvP §fhas been " + this.mainColor + "disabled!");
                }
            }
        }
    }
    
    void healAll() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player pl = onlinePlayers[i];
            pl.setHealth(20.0);
            pl.sendMessage("§6§lVoltrix §8» " + this.mainColor + "Players §fhave received their " + this.mainColor + "Final Heal!");
            Player[] onlinePlayers2;
            for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                final Player all = onlinePlayers2[j];
                all.playSound(all.getLocation(), Sound.ORB_PICKUP, 2.0f, 1.0f);
            }
        }
    }
    
    void enablePermaDay() {
        this.getUHCWorld().setTime(0L);
        this.getUHCWorld().setGameRuleValue("doDaylightCycle", "false");
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            p.sendMessage("§6§lVoltrix §8» " + this.mainColor + " Permanent day is now enabled!");
        }
    }
    
    public void setScattering(final boolean bool) {
        this.scattering = bool;
    }
    
    public World getUHCWorld() {
        return Bukkit.getWorld("uhcworld");
    }
    
    public String getUhcWorldName() {
        return "uhcworld";
    }
    
    public boolean isScattering() {
        return this.scattering;
    }
    
    public String getPrefix() {
        return "§6§lVoltrix §8» ";
    }
    
    public ChatColor getMainColor() {
        return this.mainColor;
    }
    
    public ChatColor getSecondaryColor() {
        return this.secondaryC;
    }
    
    public String getBorderPrefix() {
        return "§7[§6§lBorder§7] ";
    }
    
    public String getConfigPrefix() {
        return "§e§lConfig §8» ";
    }
    
    public String getName() {
        return "BrunoUHC";
    }
    
    public boolean isGameRunning() {
        return this.gameRunning;
    }
    
    public void setGameRunning(final boolean v) {
        this.gameRunning = v;
    }
    
    public int getDefaultMapSize() {
        return this.defaultMapSize;
    }
    
    public int getCurrentBorder() {
        return this.currentBorder;
    }
    
    public boolean isMapGenerating() {
        return this.mapGenerating;
    }
    
    public void setMapGenerating(final boolean mapGenerating) {
        this.mapGenerating = mapGenerating;
    }
    
    void startBorderShrink() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            p.sendMessage("§7[§6§lBorder§7]" + this.mainColor + "The world border will shrink every " + this.secondaryC + ConfigIntegers.BORDER_SHRINK_EVERY.get() + this.mainColor + " minutes, by " + this.secondaryC + ConfigIntegers.BORDER_SHRINK_BY.get() + this.mainColor + " blocks, until " + this.secondaryC + ConfigIntegers.BORDER_SHRINK_UNTIL.get() + this.mainColor + "x" + this.secondaryC + ConfigIntegers.BORDER_SHRINK_UNTIL.get());
        }
        new BorderRunnable().runTaskTimerAsynchronously((Plugin)VenixUHC.getInstance(), 200L, 200L);
    }
    
    public void setStatsEnabled(final boolean b) {
        this.statsEnabled = b;
    }
    
    public ItemStack getPlayersAlive() {
        return this.playersAlive;
    }
    
    public ItemStack getRandomPlayer() {
        return this.randomPlayer;
    }
    
    int getRandomShrinkAtAndBelow() {
        return 500;
    }
    
    public void setCurrentBorder(final int currentBorder) {
        this.currentBorder = currentBorder;
    }
    
    public void setWasGenerated(final boolean wasGenerated) {
        VenixUHC.getInstance().reloadConfig();
        VenixUHC.getInstance().getConfig().set("DO-NOT-TOUCH.wgen", (Object)wasGenerated);
        VenixUHC.getInstance().saveConfig();
        this.generated = wasGenerated;
    }
    
    public boolean wasGenerated() {
        return this.generated;
    }
}
