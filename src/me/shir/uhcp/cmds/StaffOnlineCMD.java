package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class StaffOnlineCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (!cmd.getName().equalsIgnoreCase("staffonlinegui")) {
            return false;
        }
        if (!sender.hasPermission("uhc.host")) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
            return true;
        }
        this.NetherGUI(player);
        return true;
    }
    
    private void NetherGUI(final Player player) {
        final Inventory nether = Bukkit.createInventory((InventoryHolder)null, 54, "§aStaff Online:");
        final ItemStack staffhead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta staffheadM = staffhead.getItemMeta();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player players = onlinePlayers[i];
            if (players.hasPermission("uhc.host") && players != null) {
                staffheadM.setDisplayName("§c§l" + players.getName());
                final List<String> lore = new ArrayList<String>();
                if (GameManager.getGameManager().getModerators().contains(players)) {
                    lore.clear();
                    lore.add(" ");
                    lore.add("§7» §3Modding");
                    staffheadM.setLore((List)lore);
                }
                else {
                    lore.clear();
                    lore.add(" ");
                    lore.add("§7» §ePlaying");
                    staffheadM.setLore((List)lore);
                }
                staffhead.setItemMeta(staffheadM);
                nether.addItem(new ItemStack[] { staffhead });
            }
        }
        player.openInventory(nether);
    }
}