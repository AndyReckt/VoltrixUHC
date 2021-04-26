package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

public class ClearCenterCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearcenter")) {
            if (!sender.hasPermission("uhc.host")) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (Bukkit.getWorld("uhcworld") != null) {
                this.prepareSpawn(GameManager.getGameManager().getUHCWorld());
            }
            else {
                Bukkit.getConsoleSender().sendMessage("[Error] World not found");
            }
        }
        return false;
    }
    
    private void prepareSpawn(final World world) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§e§lCLEANING 0.0, DONT MOVE!");
        }
        new BukkitRunnable() {
            int yInicial = 40;
            int progress = 0;
            int YChange = this.yInicial;
            
            public void run() {
                final int xM = 0;
                final int zM = 0;
                for (int radius = 160, x = 0 - radius; x <= 0 + radius; ++x) {
                    for (int z = 0 - radius; z <= 0 + radius; ++z) {
                        final Block block = world.getBlockAt(x, this.YChange, z);
                        if (block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2 || block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
                            block.setType(Material.AIR);
                        }
                        else if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                            block.setType(Material.GRASS);
                        }
                    }
                }
                ++this.YChange;
                ++this.progress;
                if (this.progress >= 50) {
                    this.cancel();
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player p2 = onlinePlayers[i];
                        p2.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§e§lThe Center has been Correctly Cleaned!");
                    }
                }
            }
        }.runTaskTimer((Plugin)VenixUHC.getInstance(), 1L, 1L);
    }
}
