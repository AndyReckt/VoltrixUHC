package me.shir.uhcp.scenarios;

import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.*;

public class ScenarioManager
{
    private static ScenarioManager instance;
    private Map<String, Scenario> scenarios;
    private final Inventory scenariosInv;
    private final Set<String> activeScenarios;
    
    public ScenarioManager() {
        this.scenarios = new HashMap<String, Scenario>();
        this.scenariosInv = Bukkit.createInventory((InventoryHolder)null, 18, "§aCurrent Scenarios:");
        this.activeScenarios = new HashSet<String>();
    }
    
    public static ScenarioManager getInstance() {
        if (ScenarioManager.instance == null) {
            ScenarioManager.instance = new ScenarioManager();
        }
        return ScenarioManager.instance;
    }
    
    public Inventory getScenariosInventory() {
        this.scenariosInv.clear();
        for (final Scenario scenario : this.getScenarios().values()) {
            if (scenario.isEnabled()) {
                this.scenariosInv.addItem(new ItemStack[] { scenario.getScenarioItemStack() });
            }
        }
        return this.scenariosInv;
    }
    
    public Map<String, Scenario> getScenarios() {
        return this.scenarios;
    }
    
    public Scenario getScenarioIgnoreCase(final String name) {
        for (final Scenario scenarios : this.scenarios.values()) {
            if (scenarios.getName().equalsIgnoreCase(name)) {
                return scenarios;
            }
        }
        return this.scenarios.get(name);
    }
    
    public Scenario getScenarioExact(final String name) {
        return this.scenarios.get(name);
    }
    
    public Set<String> getActiveScenarios() {
        return this.activeScenarios;
    }
    
    boolean doesScenarioExists(final String name) {
        for (final Scenario scenarios : this.scenarios.values()) {
            if (scenarios.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return this.scenarios.containsKey(name);
    }
    
    public void newScenario(final String name, final Material itemStack, final String... desc) {
        this.scenarios.put(name, new Scenario(name, itemStack, desc));
    }
    
    public void createAllScenarios() {
        this.newScenario("CutClean", Material.IRON_INGOT, "Ores drop smelted.", "Food drop cooked.", "Flint, Leather and Feathers", "drop rates are 100%");
        this.newScenario("RiskyRetrieval", Material.ENDER_CHEST, "All the gold/diamonds you mine,", "will go to the enderchest which is placed in 0,0.");
        this.newScenario("TripleOres", Material.EMERALD, "Food and ores are tripled when mined / harvested.", "All TripleOres games are CutClean.");
        this.newScenario("BareBones", Material.BONE, "Enchantment tables/Anvils can't be crafted or used,", "Golden apples can't be crafted either.", "The Nether is disabled.", "Players drop 1 Diamond, 2 Golden apples", "32 Arrows and 2 String on death.");
        this.newScenario("TimeBomb", Material.TNT, "When player dies,", "their loot will drop into a chest.", "After 30s, the chest will explode.");
        this.newScenario("ExtraInventory", Material.CHEST, "Use - /extrainv ", "to open your extra inventory.");
        this.newScenario("NoFallDamage", Material.DIAMOND_BOOTS, "You cannot take fall damage.");
        this.newScenario("Fireless", Material.FIRE, "You cannot take fire damage.");
        this.newScenario("Soup", Material.MUSHROOM_SOUP, "Mushroom Stew heals 2 hearts.");
        this.newScenario("BackPacks", Material.CHEST, "Use - /backpack ", "to open the team inventory.");
        this.newScenario("Diamondless", Material.DIAMOND_ORE, "You cannot mine diamonds.", "Players drop 1 diamond on death.");
        this.newScenario("Goldless", Material.GOLD_ORE, "You cannot mine gold.", "Players drop 8 gold on death.");
        this.newScenario("Bowless", Material.BOW, "Bows cannot be crafted/used.");
        this.newScenario("Rodless", Material.FISHING_ROD, "Fishing rods cannot be crafted/used.");
        this.newScenario("Vanilla+", Material.FLINT, "Flint and Apple rates are up.");
        this.newScenario("Timber", Material.DIAMOND_AXE, "");
        this.newScenario("GoldenRetriever", Material.GOLDEN_APPLE, "Players drop 1 golden head on death.");
        this.newScenario("BloodDiamonds", Material.DIAMOND, "Every time you mine diamonds,", "you take 0.5 heart of damage.");
        this.newScenario("LuckyLeaves", Material.GOLDEN_APPLE, "There is a small chance of a golden apple", "to drop from trees.");
        this.newScenario("NoClean", Material.DIAMOND_SWORD, "When you kill a player you get", "20 seconds of Invincibility");
        this.newScenario("ColdWeapons", Material.ICE, "You can't get fire aspect or flame");
        this.newScenario("TripleXP", Material.EXP_BOTTLE, "");
        this.newScenario("LuckySheep", Material.STRING, "There is a 1% that a sheep", "drop you string");
        this.newScenario("DoubleOres", Material.EMERALD_BLOCK, "Food and ores are doubled when mined / harvested.");
        this.newScenario("OreFrenzy", Material.REDSTONE_ORE, "If you mine Lapis Ore you get a splash potion of healing", "If you mine Emerald Ore you get 32 arrows", "If you mine Redstone Ore you get an unenchanted book", "If you mine Diamond Ore you get a diamond and 4 bottles of xp", "If you mine Quartz Ore you get a block of TNT");
        this.newScenario("BuildUHC", Material.LAVA_BUCKET, "You get a build uhc kit at start.");
        this.newScenario("Rush", Material.DIAMOND_PICKAXE, "The game is shorter than normal.");
        this.newScenario("Switcheroo", Material.ENDER_PEARL, "Every time you shoot someone with a bow,", "you both switch your current locations.");
        this.newScenario("GoneFishing", Material.FISHING_ROD, "You start with 64 anvils, infinite levels ", "and a fishing rod with luck of the sea 250.");
        this.newScenario("Statless", Material.DIAMOND, "Stats are not enabled");
        this.newScenario("LuckyRoulette", Material.GOLD_NUGGET, "Every 2 minutes a random player gets a ", "random item that can help you into UHC");
        this.newScenario("DoNotDisturb", Material.IRON_SWORD, "");
        this.newScenario("FlowerPower", Material.YELLOW_FLOWER, "When you break a flower you get something special");
        this.newScenario("MeleeFun", Material.FEATHER, "There is no delay between hits. However fast you click is how fast you hit someone.");
        this.newScenario("NightmareMode", Material.DIAMOND_BLOCK, "Variety of changes to mobs to make them more difficult.");
        this.newScenario("Popeye", Material.SUGAR_CANE, "Popeye Scenario :D.");
    }
}
