package me.shir.uhcp.lang;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.util.*;
import org.bukkit.inventory.*;

public class LangCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("cambiarlenguajexdxdxd")) {
            this.LangGUI((Player)sender);
        }
        return false;
    }
    
    private void LangGUI(final Player p) {
        final Inventory lang = Bukkit.createInventory((InventoryHolder)null, 27, ("§c§lLang GUI"));
        lang.setItem(12, ItemUtil.createItemData(Material.STAINED_CLAY, ("§c§lEspanol"), 5));
        lang.setItem(14, ItemUtil.createItemData(Material.STAINED_CLAY, ("§c§lEnglish"), 14));
        p.openInventory(lang);
    }
}
