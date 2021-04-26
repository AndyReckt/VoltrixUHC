package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class XalertGUICMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("xalertsgui")) {
            if (!GameManager.getGameManager().getModerators().contains(player)) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            this.XalertsGUI(player);
        }
        return false;
    }
    
    public void XalertsGUI(final Player player) {
        final Inventory xalers = Bukkit.createInventory((InventoryHolder)null, 27, "§aAlerts:");
        final ItemStack mining = new ItemStack(Material.DIAMOND_PICKAXE);
        final ItemMeta miningM = mining.getItemMeta();
        if (ToggleAlerts.xalerts.contains(player.getUniqueId())) {
            miningM.setDisplayName(GameManager.getGameManager().getMainColor() + "Mining Alerts: §8(§cOFF§8)");
        }
        else {
            miningM.setDisplayName(GameManager.getGameManager().getMainColor() + "Mining Alerts: §8(§aON§8)");
        }
        mining.setItemMeta(miningM);
        final ItemStack pvp = new ItemStack(Material.DIAMOND_ORE);
        final ItemMeta pvpM = pvp.getItemMeta();
        if (ToggleAlerts.pvpalerts.contains(player.getUniqueId())) {
            pvpM.setDisplayName(GameManager.getGameManager().getMainColor() + "PvP Alerts: §8(§cOFF§8)");
        }
        else {
            pvpM.setDisplayName(GameManager.getGameManager().getMainColor() + "PvP Alerts: §8(§aON§8)");
        }
        pvp.setItemMeta(pvpM);
        final ItemStack abuse = new ItemStack(Material.PACKED_ICE, 1, (short)3);
        final ItemMeta abuseM = abuse.getItemMeta();
        if (ToggleAlerts.abusealerts.contains(player.getUniqueId())) {
            abuseM.setDisplayName(GameManager.getGameManager().getMainColor() + "Abuse Alerts: §8(§cOFF§8)");
        }
        else {
            abuseM.setDisplayName(GameManager.getGameManager().getMainColor() + "Abuse Alerts: §8(§aON§8)");
        }
        abuse.setItemMeta(abuseM);
        xalers.setItem(10, mining);
        xalers.setItem(13, pvp);
        xalers.setItem(16, abuse);
        player.openInventory(xalers);
    }
}
