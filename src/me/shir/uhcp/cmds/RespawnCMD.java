package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public class RespawnCMD implements CommandExecutor
{
    public static ArrayList<UUID> respawned;
    
    static {
        RespawnCMD.respawned = new ArrayList<UUID>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("respawn")) {
            return false;
        }
        if (!sender.hasPermission("uhc.host") && !GameManager.getGameManager().getModerators().contains(sender)) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/respawn <player>");
            return true;
        }
        if (!GameManager.getGameManager().isGameRunning()) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cA UHC is not currently running!");
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
            return true;
        }
        final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(target.getUniqueId());
        if (uhcPlayer == null) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cThis player hasn't played this UHC!");
            return true;
        }
        if (uhcPlayer.isPlayerAlive()) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cThis player is still alive!");
            return true;
        }
        if (uhcPlayer.getRespawnLocation() == null) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cCould not find a respawn Location!");
            return true;
        }
        if (uhcPlayer.isSpectating()) {
            PlayerManager.getPlayerManager().setSpectating(false, target);
        }
        RespawnCMD.respawned.add(target.getUniqueId());
        Bukkit.getScheduler().runTaskLater((Plugin)VenixUHC.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                target.setHealth(20.0);
                target.setFoodLevel(20);
                target.teleport(uhcPlayer.getRespawnLocation());
                target.getInventory().setArmorContents(uhcPlayer.lastArmour());
                target.getInventory().setContents(uhcPlayer.lastInventory());
                uhcPlayer.setPlayerAlive(true);
                uhcPlayer.setDied(false);
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§a" + target.getName() + " §fhas been respawned!");
            }
        }, 65L);
        new BukkitRunnable() {
            public void run() {
                RespawnCMD.respawned.remove(target.getUniqueId());
            }
        }.runTaskLater((Plugin)VenixUHC.getInstance(), 85L);
        return true;
    }
}