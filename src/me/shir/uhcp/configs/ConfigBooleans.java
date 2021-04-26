package me.shir.uhcp.configs;

import org.bukkit.command.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.util.*;

public enum ConfigBooleans
{
    NETHER("NETHER", 0, true), 
    STRENGTH_1("STRENGTH_1", 1, false), 
    STRENGTH_2("STRENGTH_2", 2, false), 
    INVISIBILITY_POTIONS("INVISIBILITY_POTIONS", 3, false), 
    REGENERATION_POTIONS("REGENERATION_POTIONS", 4, false), 
    ENDERPEARL_DAMAGE("ENDERPEARL_DAMAGE", 5, false), 
    ABSORPTION("ABSORPTION", 6, true), 
    GOD_APPLES("GOD_APPLES", 7, false), 
    HORSES("HORSES", 8, false), 
    BEDBOMBS("BEDBOMBS", 9, true), 
    SPEED("SPEED", 10, true), 
    POISON("POISON", 11, true), 
    HORSE_HEALING("HORSE_HEALING", 12, false), 
    HORSE_ARMOUR("HORSE_ARMOUR", 13, false), 
    HEAD_POST("HEAD_POST", 14, true), 
    GOLDEN_HEADS("GOLDEN_HEADS", 15, true), 
    NATURAL_REGENERATION("NATURAL_REGENERATION", 16, false);
    
    private boolean bool;
    
    private ConfigBooleans(final String s, final int n, final boolean b) {
        this.bool = b;
    }
    
    public void enable(final CommandSender sender) {
        this.bool = true;
        System.out.println();
        Bukkit.getServer().broadcastMessage(String.valueOf(GameManager.getGameManager().getConfigPrefix()) + GameManager.getGameManager().getMainColor() + sender.getName() + " has set " + GameManager.getGameManager().getSecondaryColor() + configToString(this) + GameManager.getGameManager().getMainColor() + " to " + "\u00c2§aTrue");
    }
    
    public void disable(final CommandSender sender) {
        this.bool = false;
        Bukkit.getServer().broadcastMessage(String.valueOf(GameManager.getGameManager().getConfigPrefix()) + GameManager.getGameManager().getMainColor() + sender.getName() + " has set " + GameManager.getGameManager().getSecondaryColor() + configToString(this) + GameManager.getGameManager().getMainColor() + " to " + "\u00c2§cFalse");
    }
    
    public boolean isEnabled() {
        return this.bool;
    }
    
    public static ConfigBooleans fromString(final String config) {
        ConfigBooleans[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ConfigBooleans conf = values[i];
            if (conf.toString().equalsIgnoreCase(config.replace(" ", "_"))) {
                return conf;
            }
            if (config.equalsIgnoreCase(conf.toString().replace("_", ""))) {
                return conf;
            }
        }
        return null;
    }
    
    public static String configToString(final ConfigBooleans config) {
        String s = config.toString().replace("_", " ").toLowerCase();
        s = WUtil.capitalizeFully(s);
        return s;
    }
}
