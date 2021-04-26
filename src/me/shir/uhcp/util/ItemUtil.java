package me.shir.uhcp.util;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class ItemUtil
{
    public static ItemStack createItem(final Material mat, final String s) {
        final ItemStack ist = new ItemStack(mat, 1);
        final ItemMeta istM = ist.getItemMeta();
        istM.setDisplayName(s);
        ist.setItemMeta(istM);
        return ist;
    }
    
    public static ItemStack createItemLore(final Material mat, final String s, final String l) {
        final ItemStack ist = new ItemStack(mat, 1);
        final ItemMeta istM = ist.getItemMeta();
        final List<String> lore = new ArrayList<String>();
        lore.add(l);
        istM.setDisplayName(s);
        istM.setLore((List)lore);
        ist.setItemMeta(istM);
        return ist;
    }
    
    public static ItemStack createItemData(final Material mat, final String s, final int data) {
        final ItemStack ist = new ItemStack(mat, 1, (short)data);
        final ItemMeta istM = ist.getItemMeta();
        istM.setDisplayName(s);
        ist.setItemMeta(istM);
        return ist;
    }
}