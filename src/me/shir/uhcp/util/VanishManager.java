package me.shir.uhcp.util;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;

public class VanishManager
{
    private static VanishManager ins;
    private final Set<Player> vanished;
    
    public VanishManager() {
        this.vanished = new HashSet<Player>();
    }
    
    public static VanishManager getIns() {
        if (VanishManager.ins == null) {
            VanishManager.ins = new VanishManager();
        }
        return VanishManager.ins;
    }
    
    public Set<Player> getVanished() {
        return this.vanished;
    }
    
    public void setVanish(final Player pl) {
        this.vanished.add(pl);
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player players = onlinePlayers[i];
            players.hidePlayer(pl);
        }
    }
    
    public void setUnVanish(final Player pl) {
        this.vanished.remove(pl);
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player players = onlinePlayers[i];
            players.showPlayer(pl);
        }
    }
}
