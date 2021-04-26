package me.shir.uhcp.cmds;

import org.bukkit.entity.*;
import org.bukkit.command.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.game.*;
import java.text.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class InvseeCMD implements CommandExecutor
{
    public static HashMap<Player, Player> open;
    
    static {
        InvseeCMD.open = new HashMap<Player, Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("invsee")) {
            return false;
        }
        final Player player2 = (Player)sender;
        if (!PlayerManager.getPlayerManager().getUHCPlayer(player2.getUniqueId()).isSpectating() && (!sender.hasPermission("mencho.spec") || !sender.hasPermission("uhc.host"))) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/invsee <player>");
            return true;
        }
        final Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
            return true;
        }
        final Player player3 = (Player)sender;
        final DecimalFormat df = new DecimalFormat("#.#");
        final double health = target.getHealthScale() / 2.0;
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.GREEN + target.getName() + "'s Inventory");
        final ItemStack xp = new ItemStack(Material.EXP_BOTTLE, 1);
        final ItemMeta xpM = xp.getItemMeta();
        xpM.setDisplayName(ChatColor.GREEN + "XP: " + target.getLevel());
        xp.setItemMeta(xpM);
        final ItemStack h = new ItemStack(Material.GOLDEN_APPLE, 1);
        final ItemMeta hM = h.getItemMeta();
        hM.setDisplayName(ChatColor.GREEN + "Health: " + df.format(health) + "§4 \u2764");
        h.setItemMeta(hM);
        final ItemStack f = new ItemStack(Material.COOKED_BEEF, 1);
        final ItemMeta fM = f.getItemMeta();
        fM.setDisplayName(ChatColor.GREEN + "Food: " + target.getFoodLevel());
        f.setItemMeta(fM);
        final ItemStack p = new ItemStack(Material.POTION, 1);
        final ItemMeta pM = p.getItemMeta();
        pM.setDisplayName(ChatColor.GREEN + "Potions: ");
        final List<String> lore = new ArrayList<String>();
        for (final PotionEffect e : target.getActivePotionEffects()) {
            lore.add(ChatColor.GREEN + e.getType().getName().toString() + " " + e.getDuration() + " " + e.getAmplifier());
        }
        pM.setLore((List)lore);
        p.setItemMeta(pM);
        inv.setItem(0, target.getInventory().getHelmet());
        inv.setItem(1, target.getInventory().getChestplate());
        inv.setItem(2, target.getInventory().getLeggings());
        inv.setItem(3, target.getInventory().getBoots());
        inv.setItem(5, xp);
        inv.setItem(6, h);
        inv.setItem(7, f);
        inv.setItem(8, p);
        int a = 18;
        for (int i = 0; i < target.getInventory().getContents().length; ++i) {
            inv.setItem(a, target.getInventory().getItem(i));
            ++a;
        }
        InvseeCMD.open.put(player3, target);
        player3.openInventory(inv);
        return true;
    }
}
