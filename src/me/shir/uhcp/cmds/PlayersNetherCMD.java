package me.shir.uhcp.cmds;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class PlayersNetherCMD implements CommandExecutor
{
    private final GameManager gameManager;
    private final ChatColor color;
    
    public PlayersNetherCMD() {
        this.gameManager = GameManager.getGameManager();
        this.color = this.gameManager.getMainColor();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("playersinnether")) {
            if (!PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            this.NetherGUI(player);
        }
        return false;
    }
    
    private void NetherGUI(final Player player) {
        final Inventory nether = Bukkit.createInventory((InventoryHolder)null, 54, "§aPlayers in Nether:");
        final ItemStack uhc = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta uhcM = uhc.getItemMeta();
        nether.clear();
        for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayers.isPlayerAlive()) {
                final Player target = Bukkit.getServer().getPlayer(uhcPlayers.getName());
                if (target == null) {
                    continue;
                }
                if (target.getWorld() != Bukkit.getWorld("uhcworld_nether")) {
                    continue;
                }
                uhcM.setDisplayName("§a" + target.getName());
                uhc.setItemMeta(uhcM);
                nether.addItem(new ItemStack[] { uhc });
            }
        }
        player.openInventory(nether);
    }
}