package me.shir.uhcp.listeners;

import me.shir.uhcp.game.*;
import me.shir.uhcp.player.*;
import org.bukkit.event.*;
import me.shir.uhcp.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import me.shir.uhcp.configs.*;

public class BlockPlaceListener implements Listener
{
    private final GameManager gameManager;
    
    public BlockPlaceListener() {
        this.gameManager = GameManager.getGameManager();
    }
    
    @EventHandler
    public void onBlockPlaceEvent(final BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && !player.hasPermission("uhc.host")) {
            e.setCancelled(true);
            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou cannot place blocks here!");
        }
        if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerBucketEmptyEvent(final PlayerBucketEmptyEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld().equals(this.gameManager.getSpawnLocation().getWorld()) && !player.hasPermission("uhc.spawnprotection.bypass")) {
            e.setCancelled(true);
            player.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + "§cYou cannot place blocks here!");
        }
        if (PlayerManager.getPlayerManager().getUHCPlayer(player.getUniqueId()).isSpectating()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayeriPvPEvent(final PlayerBucketEmptyEvent e) {
        final Player p = e.getPlayer();
        final Location loc = e.getBlockClicked().getLocation();
        if (this.gameManager.isPvpEnabled()) {
            return;
        }
        if (e.getBucket() == Material.WATER_BUCKET) {
            return;
        }
        Entity[] entities;
        for (int length = (entities = loc.getChunk().getEntities()).length, i = 0; i < length; ++i) {
            final Entity entity = entities[i];
            if (entity instanceof Player) {
                final Player target = (Player)entity;
                if (target.getLocation().distance(loc) <= 4.9 && !target.getName().equalsIgnoreCase(p.getName())) {
                    e.setCancelled(true);
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + ("§cYou can't do this before PVP"));
                    return;
                }
            }
        }
    }
    
    @EventHandler
    public void onInteractiPvPEvent(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final ItemStack item = p.getItemInHand();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (item.getType() != Material.FLINT_AND_STEEL) {
            return;
        }
        if (this.gameManager.isPvpEnabled()) {
            return;
        }
        final Location loc = e.getClickedBlock().getLocation();
        Entity[] entities;
        for (int length = (entities = loc.getChunk().getEntities()).length, i = 0; i < length; ++i) {
            final Entity entity = entities[i];
            if (entity instanceof Player) {
                final Player target = (Player)entity;
                if (target.getLocation().distance(loc) <= 4.9 && !target.getName().equalsIgnoreCase(p.getName())) {
                    e.setCancelled(true);
                    p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + ("§cYou can't do this before PVP"));
                    return;
                }
            }
        }
    }
    
    @EventHandler
    public void onPlaceBediPvPEvent(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final ItemStack item = p.getItemInHand();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (item.getType() != Material.BED) {
            return;
        }
        if (!p.getWorld().equals(Bukkit.getWorld("uhcworld_nether"))) {
            return;
        }
        if (this.gameManager.isPvpEnabled()) {
            return;
        }
        e.setCancelled(true);
        p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + ("§cYou can't do this before PVP"));
    }
    
    @EventHandler
    public void onPlaceBedEvent(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final ItemStack item = p.getItemInHand();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (item.getType() != Material.BED) {
            return;
        }
        if (!p.getWorld().equals(Bukkit.getWorld("uhcworld_nether"))) {
            return;
        }
        if (!this.gameManager.isPvpEnabled()) {
            return;
        }
        if (ConfigBooleans.BEDBOMBS.isEnabled()) {
            return;
        }
        e.setCancelled(true);
        p.sendMessage(String.valueOf(this.gameManager.getErrorPrefix()) + ("§cBedbombs are currently disabled"));
    }
}
