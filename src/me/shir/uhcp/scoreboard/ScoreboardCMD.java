package me.shir.uhcp.scoreboard;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.util.*;
import org.bukkit.inventory.*;

public class ScoreboardCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("board")) {
            this.ScoreboardGUI((Player)sender);
        }
        return false;
    }
    
    private void ScoreboardGUI(final Player p) {
        final Inventory score = Bukkit.createInventory((InventoryHolder)null, 27, ("§cScoreboards"));
        score.setItem(12, ItemUtil.createItemData(Material.STAINED_CLAY, ("§aBadlion"), 14));
        score.setItem(14, ItemUtil.createItemData(Material.STAINED_CLAY, ("§cPlover"), 14));
        score.setItem(22, ItemUtil.createItemData(Material.STAINED_CLAY, ("§6Ultra"), 14));
        p.openInventory(score);
    }
}
