package me.shir.uhcp.scenarios;

import org.bukkit.entity.*;
import org.bukkit.scoreboard.*;
import me.shir.uhcp.*;
import org.bukkit.inventory.*;
import me.shir.uhcp.game.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;

public class Scenario
{
    private Map<Player, Scoreboard> plsScoreboard;
    private String name;
    private String[] description;
    private boolean enabled;
    private Material scenarioItem;
    private Material scenarioItem2;
    private Player player;
    
    Scenario(final String name, final Material scenarioItem, final String... description) {
        this.plsScoreboard = VenixUHC.getInstance().getLobbyScoreboardMap();
        this.description = new String[10];
        this.enabled = false;
        this.name = name;
        this.description = description;
        this.scenarioItem = scenarioItem;
        this.scenarioItem2 = scenarioItem;
    }
    
    ItemStack getScenarioItemStack() {
        final ItemStack ist = new ItemStack(this.scenarioItem);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName(GameManager.getGameManager().getMainColor() + this.name);
        final List<String> dec = new ArrayList<String>();
        final ChatColor c = GameManager.getGameManager().getSecondaryColor();
        String[] description;
        for (int length = (description = this.description).length, i = 0; i < length; ++i) {
            final String s = description[i];
            dec.add(c + s);
        }
        im.setLore((List)dec);
        ist.setItemMeta(im);
        return ist;
    }
    
    ItemStack getScenarioIST() {
        final ItemStack ist = new ItemStack(this.scenarioItem2);
        final ItemMeta im = ist.getItemMeta();
        im.setDisplayName("§b" + this.name);
        final List<String> l = new ArrayList<String>();
        if (this.enabled) {
            l.add(" ");
            l.add("§7» §aEnabled");
        }
        else {
            l.add(" ");
            l.add("§7» §cDisabled");
        }
        im.setLore((List)l);
        ist.setItemMeta(im);
        l.clear();
        return ist;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            ScenarioManager.getInstance().getActiveScenarios().add(this.getName());
            Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + GameManager.getGameManager().getMainColor() + this.getName() + GameManager.getGameManager().getSecondaryColor() + " scenario has been §aenabled!");
        }
        else {
            ScenarioManager.getInstance().getActiveScenarios().remove(this.getName());
            Bukkit.broadcastMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + GameManager.getGameManager().getMainColor() + this.getName() + GameManager.getGameManager().getSecondaryColor() + " scenario has been §cdisabled!");
        }
        this.enabled = enabled;
        this.getScenarioIST();
    }
    
    public String getName() {
        return this.name;
    }
    
    public static Scenario[] values() {
        return null;
    }
}
