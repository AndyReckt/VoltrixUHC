package me.shir.uhcp.configs;

import org.bukkit.command.*;
import org.bukkit.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.util.*;

public enum ConfigIntegers
{
    MAX_PLAYERS("MAX_PLAYERS", 0, 200), 
    BORDER_SHRINK_TIME("BORDER_SHRINK_TIME", 1, 45), 
    HEAL_TIME("HEAL_TIME", 2, 10), 
    PVP_TIME("PVP_TIME", 3, 20), 
    STARTER_FOOD("STARTER_FOOD", 4, 20), 
    BORDER_SHRINK_BY("BORDER_SHRINK_BY", 5, 500), 
    BORDER_SHRINK_EVERY("BORDER_SHRINK_EVERY", 6, 5), 
    BORDER_SHRINK_UNTIL("BORDER_SHRINK_UNTIL", 7, 25);
    
    private int i;
    
    private ConfigIntegers(final String s, final int n, final int i) {
        this.i = i;
    }
    
    public void set(final int i, final CommandSender sender) {
        this.i = i;
        Bukkit.getServer().broadcastMessage(String.valueOf(GameManager.getGameManager().getConfigPrefix()) + GameManager.getGameManager().getMainColor() + sender.getName() + " has set the " + GameManager.getGameManager().getSecondaryColor() + configToString(this) + GameManager.getGameManager().getMainColor() + " to \u00c2§a" + i);
    }
    
    public int get() {
        return this.i;
    }
    
    public static ConfigIntegers fromString(final String config) {
        ConfigIntegers[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ConfigIntegers conf = values[i];
            if (conf.toString().equalsIgnoreCase(config.replace(" ", "_"))) {
                return conf;
            }
            if (config.equalsIgnoreCase(conf.toString().replace("_", ""))) {
                return conf;
            }
        }
        return null;
    }
    
    public static String configToString(final ConfigIntegers config) {
        String s = config.toString().replace("_", " ").toLowerCase();
        s = WUtil.capitalizeFully(s);
        return s;
    }
}
