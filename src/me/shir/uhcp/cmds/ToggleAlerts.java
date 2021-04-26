package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;

public class ToggleAlerts implements CommandExecutor
{
    public static ArrayList<UUID> xalerts;
    public static ArrayList<UUID> pvpalerts;
    public static ArrayList<UUID> abusealerts;
    
    static {
        ToggleAlerts.xalerts = new ArrayList<UUID>();
        ToggleAlerts.pvpalerts = new ArrayList<UUID>();
        ToggleAlerts.abusealerts = new ArrayList<UUID>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("xalerts")) {
            if (!GameManager.getGameManager().getModerators().contains(player)) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (ToggleAlerts.xalerts.contains(player.getUniqueId())) {
                ToggleAlerts.xalerts.remove(player.getUniqueId());
                sender.sendMessage("§c§lMining §8» §fYou've §aenabled §fyour xray alerts!");
                return true;
            }
            ToggleAlerts.xalerts.add(player.getUniqueId());
            sender.sendMessage("§c§lMining §8» §fYou've §cdisabled §fyour xray alerts!");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("pvpalerts")) {
            if (!GameManager.getGameManager().getModerators().contains(player)) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (ToggleAlerts.pvpalerts.contains(player.getUniqueId())) {
                ToggleAlerts.pvpalerts.remove(player.getUniqueId());
                sender.sendMessage("§6§lAbuse §8» §fYou've §aenabled §fyour pvp alerts!");
                return true;
            }
            ToggleAlerts.pvpalerts.add(player.getUniqueId());
            sender.sendMessage("§6§lAbuse §8» §fYou've §cdisabled §fyour pvp alerts!");
            return true;
        }
        else {
            if (!cmd.getName().equalsIgnoreCase("abusealerts")) {
                return false;
            }
            if (!sender.hasPermission("abuse.alert.command")) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (ToggleAlerts.abusealerts.contains(player.getUniqueId())) {
                ToggleAlerts.abusealerts.remove(player.getUniqueId());
                sender.sendMessage("§6b§lAbuse §8» §fYou've §aenabled §fyour abuse alerts!");
                return true;
            }
            ToggleAlerts.abusealerts.add(player.getUniqueId());
            sender.sendMessage("§6§lAbuse §8» §fYou've §cdisabled §fyour abuse alerts!");
            return true;
        }
    }
}
