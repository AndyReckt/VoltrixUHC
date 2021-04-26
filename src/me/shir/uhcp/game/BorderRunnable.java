package me.shir.uhcp.game;

import org.bukkit.scheduler.*;
import me.shir.uhcp.configs.*;
import org.bukkit.*;

public class BorderRunnable extends BukkitRunnable
{
    private int i;
    private final GameManager gameManager;
    
    public BorderRunnable() {
        this.i = ConfigIntegers.BORDER_SHRINK_EVERY.get() * 60;
        this.gameManager = GameManager.getGameManager();
    }
    
    public void run() {
        if (this.gameManager.getCurrentBorder() <= ConfigIntegers.BORDER_SHRINK_UNTIL.get()) {
            this.cancel();
            return;
        }
        if (!this.gameManager.canBorderShrink()) {
            this.cancel();
        }
        this.i -= 10;
        final ChatColor color1 = GameManager.getGameManager().getMainColor();
        final ChatColor color2 = GameManager.getGameManager().getSecondaryColor();
        if (this.i == 10) {
            Bukkit.getServer().broadcastMessage(String.valueOf(GameManager.getGameManager().getBorderPrefix()) + ChatColor.WHITE + "Border shrinking in " + ChatColor.GOLD + "10" + ChatColor.WHITE + " seconds!");
            TasksManager.getInstance().startSeconds();
            this.cancel();
        }
        else if (this.i > 10 && (this.i == 600 || this.i == 540 || this.i == 480 || this.i == 420 || this.i == 360 || this.i == 300 || this.i == 240 || this.i == 180 || this.i == 120 || this.i == 60)) {
            Bukkit.getServer().broadcastMessage(String.valueOf(GameManager.getGameManager().getBorderPrefix()) + ChatColor.WHITE + "Border shrinking in " + ChatColor.GOLD + this.i / 60 + ChatColor.WHITE + " minutes!");
        }
    }
}
