package me.shir.uhcp.barrier;

import me.shir.uhcp.game.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.*;
import com.google.common.base.*;
import me.shir.uhcp.cuboid.*;
import org.bukkit.util.*;
import java.util.*;

public class WallBorderListener implements Listener
{
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getName().equals(GameManager.getGameManager().getUHCWorld().getName())) {
            final Location to = event.getTo();
            final int toX = to.getBlockX();
            final int toY = to.getBlockY();
            final int toZ = to.getBlockZ();
            final Location from = event.getFrom();
            if (from.getBlockX() != toX || from.getBlockY() != toY || from.getBlockZ() != toZ) {
                this.handlePositionChanged(event.getPlayer(), to.getWorld(), toX, toY, toZ);
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        this.onPlayerMove((PlayerMoveEvent)event);
    }
    
    private void handlePositionChanged(final Player player, final World toWorld, final int toX, final int toY, final int toZ) {
        final VisualType visualType = VisualType.UHC_BORDER;
        VenixUHC.getInstance().getVisualiseHandler().clearVisualBlocks(player, visualType, (Predicate<VisualBlock>)new Predicate<VisualBlock>() {
            public boolean apply(final VisualBlock visualBlock) {
                final Location other = visualBlock.getLocation();
                return other.getWorld().equals(toWorld) && (Math.abs(toX - other.getBlockX()) > 5 || Math.abs(toY - other.getBlockY()) > 4 || Math.abs(toZ - other.getBlockZ()) > 5);
            }
        });
        final int minHeight = toY - 3;
        final int maxHeight = toY + 4;
        final double x = GameManager.getGameManager().getCurrentBorder();
        final double z = GameManager.getGameManager().getCurrentBorder();
        final Location loc1 = new Location(toWorld, x, 0.0, -z);
        final Location loc2 = new Location(toWorld, -x, 0.0, z);
        final Cuboid cb = new Cuboid(loc1, loc2);
        final Collection<Vector> edges = cb.edges();
        for (final Vector edge : edges) {
            if (Math.abs(edge.getBlockX() - toX) <= 5 && Math.abs(edge.getBlockZ() - toZ) <= 5) {
                final Location location = edge.toLocation(toWorld);
                if (location == null) {
                    continue;
                }
                final Location first = location.clone();
                first.setY((double)minHeight);
                final Location second = location.clone();
                second.setY((double)maxHeight);
                VenixUHC.getInstance().getVisualiseHandler().generate(player, new Cuboid(first, second), visualType, false).size();
            }
        }
    }
}