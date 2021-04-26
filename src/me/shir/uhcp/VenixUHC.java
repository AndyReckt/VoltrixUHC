package me.shir.uhcp;

import org.bukkit.plugin.java.*;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.*;
import me.shir.uhcp.util.*;
import me.shir.uhcp.teams.request.*;
import java.io.*;
import org.bukkit.command.*;
import me.shir.uhcp.configs.*;
import me.shir.uhcp.scoreboard.*;
import me.shir.uhcp.lang.*;
import me.shir.uhcp.cmds.*;
import me.shir.uhcp.teams.commands.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.event.*;
import me.shir.uhcp.barrier.*;
import me.shir.uhcp.listeners.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.world.*;
import me.shir.uhcp.world.WorldCreator;
import me.shir.uhcp.game.*;
import me.shir.uhcp.game.ScoreboardManager;

public class VenixUHC extends JavaPlugin
{
    public static VenixUHC instance;
    public static Plugin plugin;
    private final Map<Player, Scoreboard> lobbyScoreboardMap;
    private VisualiseHandler visualiseHandler;
    private SQL sql;
    
    public VenixUHC() {
        this.lobbyScoreboardMap = new HashMap<Player, Scoreboard>();
    }
    
    public static VenixUHC getInstance() {
        return VenixUHC.instance;
    }
    
    public Map<Player, Scoreboard> getLobbyScoreboardMap() {
        return this.lobbyScoreboardMap;
    }
    
    public SQL getSQL() {
        return this.sql;
    }
    
    public void onEnable() {
        (VenixUHC.instance = this).loadConfiguration();
        GameManager.setInstance();
        ScenarioManager.getInstance().createAllScenarios();
        this.registerCommands();
        this.registerEvents();
        this.loadScoreboard();
        ProtocolLibHook.hook(this, this);
        this.visualiseHandler = new VisualiseHandler();
        new RequestManager().register(this);
        this.registerItems();
        if (!this.exists(GameManager.getGameManager().getUhcWorldName())) {
            GameManager.getGameManager().setRestarted(false);
        }
        this.startRunnable();
        if (this.getConfig().getBoolean("stats.enabled")) {
            (this.sql = new SQL()).openConnection();
            this.sql.createTables();
        }
    }
    
    private boolean exists(final String path) {
        final File file = new File(path);
        return file.exists();
    }
    
    public VisualiseHandler getVisualiseHandler() {
        return this.visualiseHandler;
    }
    
    public void onDisable() {
        if (GameManager.getGameManager().isGameRunning()) {
            Bukkit.getServer().getPluginManager().callEvent((Event)new GameStopEvent("On disable (game running)"));
            GameManager.getGameManager().setRestarted(false);
            GameManager.getGameManager().setWorldWasUsed(true);
        }
        if (GameManager.getGameManager().isMapGenerating()) {
            GameManager.getGameManager().setRestarted(false);
        }
        if (this.getConfig().getBoolean("stats.enabled")) {
            this.sql.closeConnection();
        }
        VenixUHC.instance = null;
    }
    
    private void registerCommands() {
        this.getCommand("map").setExecutor((CommandExecutor)new MapGeneratedCMD());
        this.getCommand("heal").setExecutor((CommandExecutor)new HealCMD());
        this.getCommand("rescatter").setExecutor((CommandExecutor)new ReScatterCMD());
        this.getCommand("bestkillers").setExecutor((CommandExecutor)new BestKillersCMD());
        this.getCommand("createuhc").setExecutor((CommandExecutor)new CreateCMD());
        this.getCommand("game").setExecutor((CommandExecutor)new FriendAddCMD());
        this.getCommand("health").setExecutor((CommandExecutor)new HealthCMD());
        this.getCommand("helpop").setExecutor((CommandExecutor)new HelpOpCMD());
        this.getCommand("killcount").setExecutor((CommandExecutor)new KillCountCMD());
        this.getCommand("border").setExecutor((CommandExecutor)new BorderCMD());
        this.getCommand("respawn").setExecutor((CommandExecutor)new RespawnCMD());
        this.getCommand("uhcmanage").setExecutor((CommandExecutor)new ManageCMD());
        this.getCommand("startuhc").setExecutor((CommandExecutor)new StartGameCMD());
        this.getCommand("stopuhc").setExecutor((CommandExecutor)new StopGameCMD());
        this.getCommand("config").setExecutor((CommandExecutor)new ConfigCMD());
        this.getCommand("stats").setExecutor((CommandExecutor)new StatsCMD());
        this.getCommand("host").setExecutor((CommandExecutor)new HostCMD());
        this.getCommand("freeze").setExecutor((CommandExecutor)new FreezeCMD());
        this.getCommand("tweetapoyo").setExecutor((CommandExecutor)new TweetCMD());
        this.getCommand("board").setExecutor((CommandExecutor)new ScoreboardCMD());
        this.getCommand("xalerts").setExecutor((CommandExecutor)new ToggleAlerts());
        this.getCommand("tweetgame").setExecutor((CommandExecutor)new TweetCMD());
        this.getCommand("clearcenter").setExecutor((CommandExecutor)new ClearCenterCMD());
        this.getCommand("pvpalerts").setExecutor((CommandExecutor)new ToggleAlerts());
        this.getCommand("board").setExecutor((CommandExecutor)new ScoreboardCMD());
        this.getCommand("abusealerts").setExecutor((CommandExecutor)new ToggleAlerts());
        this.getCommand("moderator").setExecutor((CommandExecutor)new ModeratorCMD());
        this.getCommand("confirmstart").setExecutor((CommandExecutor)new ConfirmStart());
        this.getCommand("staffonlinegui").setExecutor((CommandExecutor)new StaffOnlineCMD());
        this.getCommand("playersinnether").setExecutor((CommandExecutor)new PlayersNetherCMD());
        this.getCommand("xalertsgui").setExecutor((CommandExecutor)new XalertGUICMD());
        this.getCommand("scatter").setExecutor((CommandExecutor)new ReviveCMD());
        this.getCommand("cambiarlenguajexdxdxd").setExecutor((CommandExecutor)new LangCMD());
        this.getCommand("deathmatchcountdown").setExecutor((CommandExecutor)new DeathMatchCountdown());
        this.getCommand("staffchat").setExecutor((CommandExecutor)new StaffChatCMD());
        this.getCommand("spectator").setExecutor((CommandExecutor)new SpectatorCMD());
        this.getCommand("spectatorchat").setExecutor((CommandExecutor)new SpectatorChatCMD());
        this.getCommand("settitle").setExecutor((CommandExecutor)new SetTittleCMD());
        final InvseeCMD invseeCMD = new InvseeCMD();
        this.getCommand("invsee").setExecutor((CommandExecutor)invseeCMD);
        this.getCommand("armour").setExecutor((CommandExecutor)invseeCMD);
        Bukkit.getServer().getPluginCommand("whitelist").setExecutor((CommandExecutor)new WhitelistCMD());
        Bukkit.getServer().getPluginCommand("list").setExecutor((CommandExecutor)new ListCMD());
        Bukkit.getServer().getPluginCommand("teleport").setExecutor((CommandExecutor)new TeleportCMD());
        this.getCommand("team").setExecutor((CommandExecutor)new TeamCMD());
        this.getCommand("teamlist").setExecutor((CommandExecutor)new TeamListCMD());
        this.getCommand("sendcoords").setExecutor((CommandExecutor)new SendCoordsCMD());
        this.getCommand("teamchat").setExecutor((CommandExecutor)new TeamChatCMD());
        this.getCommand("scenario").setExecutor((CommandExecutor)new ScenarioCMD());
        this.getCommand("backpack").setExecutor((CommandExecutor)new BackPackCMD());
        this.getCommand("extrainventory").setExecutor((CommandExecutor)new ExtraInventoryCMD());
    }
    
    private void registerEvents() {
        final PluginManager plm = Bukkit.getServer().getPluginManager();
        plm.registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
        plm.registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
        plm.registerEvents((Listener)new InventoryListeners(), (Plugin)this);
        plm.registerEvents((Listener)new PlayerDeathListener(), (Plugin)this);
        plm.registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
        plm.registerEvents((Listener)new ScoreboardHandler(), (Plugin)this);
        plm.registerEvents((Listener)new PlayerQuitListener(), (Plugin)this);
        plm.registerEvents((Listener)new Listeners(), (Plugin)this);
        plm.registerEvents((Listener)new WallBorderListener(), (Plugin)this);
        if (this.getConfig().getBoolean("settings.using-world-border-plugin")) {
            plm.registerEvents((Listener)new WorldBorderFillListener(), (Plugin)this);
        }
    }
    
    private void loadConfiguration() {
        final String path = "settings.";
        this.getConfig().addDefault("DO-NOT-TOUCH.wmu", (Object)false);
        this.getConfig().addDefault("DO-NOT-TOUCH.wrs", (Object)false);
        this.getConfig().addDefault("DO-NOT-TOUCH.wgen", (Object)false);
        this.getConfig().addDefault(String.valueOf(String.valueOf("settings.")) + "spawnLocation.x", (Object)0);
        this.getConfig().addDefault(String.valueOf(String.valueOf("settings.")) + "spawnLocation.y", (Object)64);
        this.getConfig().addDefault(String.valueOf(String.valueOf("settings.")) + "spawnLocation.z", (Object)0);
        this.getConfig().addDefault(String.valueOf(String.valueOf("settings.")) + "using-world-border-plugin", (Object)false);
        this.getConfig().addDefault("stats.enabled", (Object)false);
        this.getConfig().addDefault("stats.SQL.ip", (Object)"localhost");
        this.getConfig().addDefault("stats.SQL.database", (Object)"databasehere");
        this.getConfig().addDefault("stats.SQL.table", (Object)"tablenamehere");
        this.getConfig().addDefault("stats.SQL.user", (Object)"userforDatabase");
        this.getConfig().addDefault("stats.SQL.password", (Object)"passForUser");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    private void registerItems() {
        final ItemStack ist = new ItemStack(Material.GOLDEN_APPLE, 1);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName("\u00c2§6Golden Head");
        final List<String> al = new ArrayList<String>();
        al.add("\u00c2§5Some say consuming the head of a");
        al.add("\u00c2§5fallen foe strengthens the blood");
        im.setLore((List)al);
        ist.setItemMeta(im);
        final ShapedRecipe sr = new ShapedRecipe(ist);
        sr.shape(new String[] { "EEE", "ERE", "EEE" });
        sr.setIngredient('E', Material.GOLD_INGOT).setIngredient('R', Material.SKULL_ITEM, 3);
        Bukkit.getServer().addRecipe((Recipe)sr);
        final ShapelessRecipe glmelon = new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON, 1));
        glmelon.addIngredient(Material.GOLD_BLOCK);
        glmelon.addIngredient(Material.MELON);
        Bukkit.getServer().addRecipe((Recipe)glmelon);
        final ItemStack abc = new ItemStack(Material.BOOK, 1);
        final ShapelessRecipe abcr = new ShapelessRecipe(abc);
        abcr.addIngredient(1, Material.ENCHANTED_BOOK);
        abcr.addIngredient(1, Material.ENCHANTED_BOOK);
        Bukkit.getServer().addRecipe((Recipe)abcr);
    }
    
    private void startRunnable() {
        new BukkitRunnable() {
            public void run() {
                if (VenixUHC.this.getConfig().getBoolean("DO-NOT-TOUCH.wmu")) {
                    new WorldCreator(true, true);
                }
                else {
                    new WorldCreator(true, false);
                }
            }
        }.runTaskLater((Plugin)this, 300L);
    }
    
    private void loadScoreboard() {
        new ScoreboardManager().runTaskTimerAsynchronously((Plugin)this, 20L, 20L);
    }
}