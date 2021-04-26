package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.lang.*;
import me.shir.uhcp.scenarios.*;
import me.shir.uhcp.teams.*;
import com.nametagedit.plugin.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import me.shir.uhcp.util.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;

public class StartGameCMD implements CommandExecutor
{
    private final GameManager gameManager;
    private int i;
    public static int starttime;
    public static final List<Player> scatter;
    public static boolean invencibility;
    public static int inventime;
    public ScoreboardHandler scoreboardHandler;
    public static StartGameCMD instance;
    private int an;
    private final int t;
    
    static {
        scatter = new ArrayList<Player>();
        StartGameCMD.invencibility = false;
        StartGameCMD.inventime = 15;
    }
    
    public StartGameCMD() {
        this.gameManager = GameManager.getGameManager();
        this.scoreboardHandler = new ScoreboardHandler();
        this.an = 300;
        this.t = this.gameManager.getScatterTicks();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("startuhc")) {
            final Player playersender = (Player)sender;
            if (!sender.hasPermission("uhc.start") && !this.gameManager.getHostName().equalsIgnoreCase(sender.getName())) {
                if (LangManager.esEspanol(playersender)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c¡No tienes permisos para ejecutar este comando!");
                }
                else if (LangManager.isEnglish(playersender)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                }
                return true;
            }
            if (this.gameManager.isScattering() || this.gameManager.isGameRunning()) {
                if (LangManager.esEspanol(playersender)) {
                    sender.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§c¡Un UHC ya esta en juego!");
                }
                else if (LangManager.isEnglish(playersender)) {
                    sender.sendMessage("§7§m------------------------");
                    sender.sendMessage("\u2013 §cThe UHC Is Currently Running!");
                    sender.sendMessage("§7§m------------------------");
                }
                return true;
            }
            this.gameManager.setScattering(true);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl on");
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wl all");
            playersender.chat("/practice off");
            if (LangManager.esEspanol(playersender)) {
                sender.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§b§lEmpezando el esparcimiento...");
            }
            else if (LangManager.isEnglish(playersender)) {
                sender.sendMessage("§7§m------------------------");
                sender.sendMessage("\u2013 §6Starting Scatter..!");
                sender.sendMessage("§7§m------------------------");
            }
            if (ScenarioManager.getInstance().getScenarioExact("RiskyRetrieval").isEnabled()) {
                this.gameManager.getUHCWorld().getHighestBlockAt(0, 0).getLocation().add(0.0, 1.0, 0.0).getBlock().setType(Material.ENDER_CHEST);
            }
            if (this.gameManager.lobbyScoreboard()) {
                Player[] onlinePlayers;
                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                    final Player player = onlinePlayers[i];
                    this.gameManager.addWhitelist((OfflinePlayer)player);
                    final ScoreboardHelper board = this.scoreboardHandler.getScoreboard(player);
                    board.clear();
                }
            }
            this.gameManager.setCanBorderShrink(true);
            if (TeamManager.getInstance().isTeamsEnabled()) {
                TeamManager.getInstance().autoPlace();
            }
            Player[] onlinePlayers2;
            for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                final Player playere = onlinePlayers2[j];
                if (TeamManager.getInstance().isTeamsEnabled() && !this.gameManager.getModerators().contains(playere)) {
                    NametagEdit.getApi().setNametag(playere, TeamManager.getInstance().getTeam((OfflinePlayer)playere).getTeamColor().toString(), "");
                }
            }
            for (final Player players : this.gameManager.getSpawnLocation().getWorld().getPlayers()) {
                if (players != null && !this.gameManager.getModerators().contains(players)) {
                    StartGameCMD.scatter.add(players);
                }
            }
            this.i = StartGameCMD.scatter.size() - 1;
            Bukkit.getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    StartGameCMD.this.start();
                }
            }, 80L);
            StartGameCMD.starttime = this.startsIn(this.i + 40);
            new BukkitRunnable() {
                public void run() {
                    if (StartGameCMD.starttime > 1) {
                        --StartGameCMD.starttime;
                    }
                    else {
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
            if (ScenarioManager.getInstance().getScenarioExact("BuildUHC").isEnabled()) {
                playersender.chat("/config healtime 1");
                playersender.chat("/config pvptime 1");
                playersender.chat("/config bordershrinktime 1");
                GameManager.getGameManager().setStats(false);
            }
        }
        return false;
    }
    
    private void start() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (LangManager.esEspanol(p)) {
                p.sendMessage(String.valueOf(this.gameManager.getPrefix()) + "§eIniciando UHC en " + this.startsIn(this.i + 30) + " segundo(s)");
            }
            else if (LangManager.isEnglish(p)) {
                p.sendMessage("§a\u2013 §fThe §6UHC §fWill §6Starts §fin§6 " + this.startsIn(this.i + 30) + " §fSeconds!");
            }
        }
        new BukkitRunnable() {
            public void run() {
                if (StartGameCMD.this.i < 0) {
                    this.cancel();
                    new BukkitRunnable() {
                        public void run() {
                            StartGameCMD.this.gameManager.setWorldWasUsed(true);
                            StartGameCMD.this.gameManager.setScattering(false);
                            Player[] onlinePlayers;
                            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                                final Player players = onlinePlayers[i];
                                if (players != null) {
                                    players.setFoodLevel(20);
                                    players.setLevel(0);
                                    players.setHealth(20.0);
                                    players.getActivePotionEffects().clear();
                                    if (ScenarioManager.getInstance().getScenarioExact("GoneFishing").isEnabled() && !StartGameCMD.this.gameManager.getModerators().contains(players)) {
                                        final ItemStack ist = new ItemStack(Material.FISHING_ROD);
                                        ist.addUnsafeEnchantment(Enchantment.DURABILITY, 150);
                                        ist.addUnsafeEnchantment(Enchantment.LUCK, 250);
                                        players.getInventory().addItem(new ItemStack[] { ist });
                                        players.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ANVIL, 64) });
                                        players.setLevel(999999);
                                    }
                                    if (ScenarioManager.getInstance().getScenarioExact("BuildUHC").isEnabled()) {
                                        new BukkitRunnable() {
                                            public void run() {
                                                if (!StartGameCMD.this.gameManager.getModerators().contains(players)) {
                                                    StartGameCMD.this.giveBuildUHCKit(players);
                                                }
                                            }
                                        }.runTaskLater((Plugin)VenixUHC.getInstance(), 5L);
                                    }
                                    for (final PotionEffect potionEffect : players.getActivePotionEffects()) {
                                        players.removePotionEffect(potionEffect.getType());
                                    }
                                    for (final Entity ent : Bukkit.getWorld("uhcworld").getEntities()) {
                                        if (ent instanceof Pig) {
                                            final Pig vehicle = (Pig)ent;
                                            if (!vehicle.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                                                continue;
                                            }
                                            vehicle.remove();
                                        }
                                    }
                                    Player[] onlinePlayers2;
                                    for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                                        final Player player = onlinePlayers2[j];
                                        if (player.getVehicle() != null && player.getVehicle() instanceof Pig) {
                                            final Pig vehicle2 = (Pig)player.getVehicle();
                                            vehicle2.remove();
                                        }
                                    }
                                }
                            }
                            new BukkitRunnable() {
                                public void run() {
                                    if (ScenarioManager.getInstance().getScenarioExact("LuckyRoulette").isEnabled()) {
                                        final List<Player> roulette = new ArrayList<Player>();
                                        Player[] onlinePlayers;
                                        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                                            final Player all = onlinePlayers[i];
                                            if (all.getGameMode().equals((Object)GameMode.SURVIVAL) && all.getWorld().equals(Bukkit.getWorld("uhcworld")) && all.isOnline()) {
                                                roulette.add(all);
                                            }
                                        }
                                        Player[] onlinePlayers2;
                                        for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                                            final Player p = onlinePlayers2[j];
                                            if (LangManager.esEspanol(p)) {
                                                p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§bSe apr\u00f3xima una ronda de la ruleta...");
                                            }
                                            else if (LangManager.isEnglish(p)) {
                                                p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§bA LuckyRoulette round is coming...");
                                            }
                                        }
                                        new BukkitRunnable() {
                                            public void run() {
                                                final Random r = new Random();
                                                final int x = r.nextInt(roulette.size());
                                                final Player player = roulette.get(x);
                                                final Random lap = new Random();
                                                final int[] randomnumber = { 1, 2, 3, 4, 5 };
                                                final int e = lap.nextInt(5);
                                                final Random enchant = new Random();
                                                final Enchantment[] enchantments = { Enchantment.DAMAGE_ALL, Enchantment.PROTECTION_ENVIRONMENTAL };
                                                final int ea = enchant.nextInt(2);
                                                final ItemStack apple = new ItemStack(Material.APPLE, randomnumber[e]);
                                                final ItemStack goldenapple = new ItemStack(Material.GOLDEN_APPLE, randomnumber[e]);
                                                final ItemStack book = new ItemStack(Material.BOOK, randomnumber[e] * 3);
                                                final ItemStack leather = new ItemStack(Material.LEATHER, randomnumber[e] * 3);
                                                final ItemStack string = new ItemStack(Material.STRING, randomnumber[e]);
                                                final ItemStack diamond = new ItemStack(Material.DIAMOND, randomnumber[e]);
                                                final ItemStack gold = new ItemStack(Material.GOLD_INGOT, randomnumber[e] * 3);
                                                final ItemStack anvil = new ItemStack(Material.ANVIL, 1);
                                                final ItemStack enchants = new ItemStack(Material.ENCHANTMENT_TABLE, 1);
                                                final ItemStack bookenchanted = new ItemStack(Material.ENCHANTED_BOOK, 1);
                                                final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)bookenchanted.getItemMeta();
                                                meta.addStoredEnchant(enchantments[ea], 1, true);
                                                bookenchanted.setItemMeta((ItemMeta)meta);
                                                final ItemStack coal = new ItemStack(Material.COAL, 64);
                                                final ItemMeta coalM = coal.getItemMeta();
                                                coalM.setDisplayName("§cBad luck! :(");
                                                coal.setItemMeta(coalM);
                                                final ItemStack hola = new ItemStack(Material.YELLOW_FLOWER, 1);
                                                final ItemMeta holaM = hola.getItemMeta();
                                                holaM.setDisplayName("§cBad luck! :(");
                                                hola.setItemMeta(holaM);
                                                final ItemStack cobweb = new ItemStack(Material.WEB, randomnumber[e]);
                                                final ItemStack sugarcane = new ItemStack(Material.SUGAR_CANE, randomnumber[e] * 3);
                                                final ItemStack arrow = new ItemStack(Material.ARROW, 32);
                                                final ItemStack redstoneore = new ItemStack(Material.REDSTONE, 64);
                                                final ItemMeta redstoneoreM = redstoneore.getItemMeta();
                                                redstoneoreM.setDisplayName("§cBad luck! :(");
                                                redstoneore.setItemMeta(redstoneoreM);
                                                final ItemStack[] randomitem = { apple, goldenapple, book, leather, string, diamond, gold, anvil, enchants, bookenchanted, coal, hola, cobweb, sugarcane, arrow, redstoneore };
                                                final Random randomitema = new Random();
                                                final int randomiteme = randomitema.nextInt(16);
                                                player.getInventory().addItem(new ItemStack[] { randomitem[randomiteme] });
                                                Player[] onlinePlayers;
                                                for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                                                    final Player p = onlinePlayers[i];
                                                    if (LangManager.esEspanol(p)) {
                                                        p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§b" + player.getName() + " §eobtuvo un(a) §a" + WUtil.capitalize(randomitem[randomiteme].getType().toString().toLowerCase().replaceAll("_", " ")) + " §egracias a la ruleta.");
                                                    }
                                                    else if (LangManager.isEnglish(p)) {
                                                        p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§b" + player.getName() + " §egets a(n) §a" + WUtil.capitalize(randomitem[randomiteme].getType().toString().toLowerCase().replaceAll("_", " ")) + " §ethanks to the roulette.");
                                                    }
                                                }
                                                Player[] onlinePlayers2;
                                                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                                                    final Player ell = onlinePlayers2[j];
                                                    ell.playSound(ell.getLocation(), Sound.ORB_PICKUP, 2.0f, 1.0f);
                                                }
                                            }
                                        }.runTaskLater((Plugin)VenixUHC.getInstance(), 100L);
                                    }
                                    else {
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer((Plugin)VenixUHC.getInstance(), 2400L, 2400L);
                            StartGameCMD.this.gameManager.getUHCWorld().setPVP(false);
                            StartGameCMD.this.gameManager.setGameRunning(true);
                            StartGameCMD.this.gameManager.setWasGenerated(false);
                            new GRunnable().runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
                            StartGameCMD.invencibility = true;
                            Player[] onlinePlayers3;
                            for (int length3 = (onlinePlayers3 = Bukkit.getServer().getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                                final Player p = onlinePlayers3[k];
                                if (LangManager.esEspanol(p)) {
                                    p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§eUn UHC ha empezado. ¡Buena suerte y pasala bueno!");
                                }
                                else if (LangManager.isEnglish(p)) {
                                    p.sendMessage("§7§m------------------------");
                                    p.sendMessage("\u2013º §6The UHC Has Started!");
                                    p.sendMessage("\u2013º §6Have Good Luck in the Match!");
                                    p.sendMessage("\u2013º §aUse /helpop por §c§lHELP");
                                    p.sendMessage("§7§m------------------------");
                                }
                            }
                            new BukkitRunnable() {
                                public void run() {
                                    if (StartGameCMD.inventime > 1) {
                                        --StartGameCMD.inventime;
                                    }
                                    else {
                                        this.cancel();
                                        StartGameCMD.invencibility = false;
                                        StartGameCMD.inventime = 15;
                                        Player[] onlinePlayers;
                                        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                                            final Player p = onlinePlayers[i];
                                            if (LangManager.esEspanol(p)) {
                                                p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§eHas perdido tu protecci\u00f3n final. ¡Buena suerte!");
                                            }
                                            else if (LangManager.isEnglish(p)) {
                                                p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§eYou have lost your final protection. Good luck!");
                                            }
                                        }
                                        Player[] onlinePlayers2;
                                        for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                                            final Player players = onlinePlayers2[j];
                                            players.playSound(players.getLocation(), Sound.ORB_PICKUP, 2.0f, 1.0f);
                                        }
                                    }
                                }
                            }.runTaskTimer((Plugin)VenixUHC.getInstance(), 20L, 20L);
                        }
                    }.runTaskLater((Plugin)VenixUHC.getInstance(), 100L);
                }
                else {
                    final StartGameCMD this$0 = StartGameCMD.this;
                    StartGameCMD.access$4(this$0, this$0.an - StartGameCMD.this.gameManager.getScatterTicks());
                    if (StartGameCMD.this.an <= 0) {
                        StartGameCMD.access$4(StartGameCMD.this, 300);
                        Player[] onlinePlayers;
                        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                            final Player p = onlinePlayers[i];
                            if (LangManager.esEspanol(p)) {
                                p.sendMessage(String.valueOf(StartGameCMD.this.gameManager.getPrefix()) + "§eIniciando UHC en " + StartGameCMD.this.startsIn(StartGameCMD.this.i + 30) + " segundo(s)");
                            }
                            else if (LangManager.isEnglish(p)) {
                                p.sendMessage("§eThe UHC Will Starts in " + StartGameCMD.this.startsIn(StartGameCMD.this.i + 30) + " Seconds!");
                            }
                        }
                    }
                    try {
                        StartGameCMD.scatter.get(StartGameCMD.this.i);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        try {
                            StartGameCMD.scatter.remove(StartGameCMD.this.i);
                        }
                        catch (ArrayIndexOutOfBoundsException aiofbe) {
                            System.out.println("Null remove player scatter.");
                        }
                    }
                    try {
                        if (StartGameCMD.scatter.get(StartGameCMD.this.i) != null && StartGameCMD.scatter.get(StartGameCMD.this.i).isOnline()) {
                            final Player player = StartGameCMD.scatter.get(StartGameCMD.this.i);
                            if (player.getWorld().equals(StartGameCMD.this.gameManager.getSpawnLocation().getWorld())) {
                                TasksManager.getInstance().scatterPlayer(player);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 999));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 999));
                                Player[] onlinePlayers2;
                                for (int length2 = (onlinePlayers2 = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                                    final Player players = onlinePlayers2[j];
                                    players.showPlayer(player);
                                    if (!StartGameCMD.this.gameManager.getModerators().contains(players)) {
                                        player.showPlayer(players);
                                    }
                                }
                            }
                            else {
                                StartGameCMD.scatter.remove(StartGameCMD.this.i);
                            }
                        }
                        final StartGameCMD this$2 = StartGameCMD.this;
                        StartGameCMD.access$5(this$2, this$2.i - 1);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Null player scatter.");
                    }
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), (long)this.t, (long)this.t);
    }
    
    public int startsIn(final int toScatter) {
        return toScatter * this.gameManager.getScatterTicks() / 20;
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
    
    static /* synthetic */ void access$4(final StartGameCMD startGameCMD, final int an) {
        startGameCMD.an = an;
    }
    
    static /* synthetic */ void access$5(final StartGameCMD startGameCMD, final int i) {
        startGameCMD.i = i;
    }
}
