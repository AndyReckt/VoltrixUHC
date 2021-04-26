package me.shir.uhcp.listeners;

import com.wimbli.WorldBorder.Events.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import org.bukkit.scheduler.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class WorldBorderFillListener implements Listener
{
    @EventHandler
    public void onWorldBorder(final WorldBorderFillFinishedEvent e) {
        if (e.getWorld().equals(GameManager.getGameManager().getUHCWorld())) {
            GameManager.getGameManager().setWasGenerated(true);
            GameManager.getGameManager().setMapGenerating(false);
        }
        if (!GameManager.getGameManager().wasRestarted()) {
            GameManager.getGameManager().setMapGenerating(false);
            Bukkit.getServer().broadcastMessage(GameManager.getGameManager().getMainColor() + "Successfully loaded all chunks in the UHC world!");
        }
        if (!GameManager.getGameManager().wasRestarted() && VenixUHC.getInstance().getConfig().getBoolean("settings.restart-on-chunk-load-finish")) {
            new BukkitRunnable() {
                public void run() {
                    GameManager.getGameManager().setRestarted(true);
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), GameManager.getGameManager().getRestartCommand().replace("/", ""));
                }
            }.runTaskLater((Plugin)VenixUHC.getInstance(), 100L);
        }
    }
}
