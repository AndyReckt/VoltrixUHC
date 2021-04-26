package me.shir.uhcp.barrier;

import org.bukkit.entity.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import me.shir.uhcp.cuboid.*;
import org.bukkit.*;
import org.bukkit.block.*;
import javax.annotation.*;
import java.util.*;

public class VisualiseHandler{
    private final Table<UUID, Location, VisualBlock> storedVisualises;
    
    public VisualiseHandler() {
        this.storedVisualises = (Table<UUID, Location, VisualBlock>)HashBasedTable.create();
    }
    
    public Table<UUID, Location, VisualBlock> getStoredVisualises() {
        return this.storedVisualises;
    }
    
    @Deprecated
    public VisualBlock getVisualBlockAt(final Player player, final int x, final int y, final int z) throws NullPointerException {
        return this.getVisualBlockAt(player, new Location(player.getWorld(), (double)x, (double)y, (double)z));
    }
    
    public VisualBlock getVisualBlockAt(final Player player, final Location location) throws NullPointerException {
        Preconditions.checkNotNull((Object)player, (Object)"Player cannot be null");
        Preconditions.checkNotNull((Object)location, (Object)"Location cannot be null");
        synchronized (this.storedVisualises) {
            // monitorexit(this.storedVisualises)
            return (VisualBlock)this.storedVisualises.get((Object)player.getUniqueId(), (Object)location);
        }
    }
    
    public Map<Location, VisualBlock> getVisualBlocks(final Player player, final VisualType visualType) {
        return (Map<Location, VisualBlock>)Maps.filterValues((Map)this.getVisualBlocks(player, visualType), (Predicate)new Predicate() {
            public boolean apply(final VisualBlock visualBlock) {
                return visualType == visualBlock.getVisualType();
            }
            
            public boolean apply(final Object arg0) {
                return false;
            }
        });
    }
    
    public LinkedHashMap<Location, VisualBlockData> generate(final Player player, final Cuboid cuboid, final VisualType visualType, final boolean canOverwrite) {
        final Collection<Location> locations = new HashSet<Location>(cuboid.getSizeX() * cuboid.getSizeY() * cuboid.getSizeZ());
        final Iterator<Location> iterator = cuboid.locationIterator();
        while (iterator.hasNext()) {
            locations.add(iterator.next());
        }
        return this.generate(player, locations, visualType, canOverwrite);
    }
    
    public LinkedHashMap<Location, VisualBlockData> generateAsync(final Player player, final Cuboid cuboid, final VisualType visualType, final boolean canOverwrite) {
        final Collection<Location> locations = new HashSet<Location>(cuboid.getSizeX() * cuboid.getSizeY() * cuboid.getSizeZ());
        final Iterator<Location> iterator = cuboid.locationIterator();
        while (iterator.hasNext()) {
            locations.add(iterator.next());
        }
        return this.generateAsync(player, locations, visualType, canOverwrite);
    }
    
    public LinkedHashMap<Location, VisualBlockData> generate(final Player player, final Iterable<Location> locations, final VisualType visualType, final boolean canOverwrite) {
        synchronized (this.storedVisualises) {
            final LinkedHashMap<Location, VisualBlockData> results = new LinkedHashMap<Location, VisualBlockData>();
            final ArrayList<VisualBlockData> filled = visualType.blockFiller().bulkGenerate(player, locations);
            if (filled != null) {
                int count = 0;
                for (final Location location : locations) {
                    if (canOverwrite || !this.storedVisualises.contains((Object)player.getUniqueId(), (Object)location)) {
                        final Material previousType = location.getBlock().getType();
                        if (previousType.isSolid() || previousType != Material.AIR) {
                            continue;
                        }
                        final VisualBlockData visualBlockData = filled.get(count++);
                        results.put(location, visualBlockData);
                        player.sendBlockChange(location, visualBlockData.getBlockType(), visualBlockData.getData());
                        this.storedVisualises.put((Object)player.getUniqueId(), (Object)location, (Object)new VisualBlock(visualType, visualBlockData, location));
                    }
                }
            }
            // monitorexit(this.storedVisualises)
            return results;
        }
    }
    
    public LinkedHashMap<Location, VisualBlockData> generateAsync(final Player player, final Iterable<Location> locations, final VisualType visualType, final boolean canOverwrite) {
        synchronized (this.storedVisualises) {
            final LinkedHashMap<Location, VisualBlockData> results = new LinkedHashMap<Location, VisualBlockData>();
            final ArrayList<VisualBlockData> filled = visualType.blockFiller().bulkGenerate(player, locations);
            if (filled != null) {
                for (final Location location : locations) {
                    if (canOverwrite || !this.storedVisualises.contains((Object)player.getUniqueId(), (Object)location)) {
                        location.getWorld().getChunkAt(location);
                    }
                }
            }
            // monitorexit(this.storedVisualises)
            return results;
        }
    }
    
    public boolean clearVisualBlock(final Player player, final Location location) {
        return this.clearVisualBlock(player, location, true);
    }
    
    public boolean clearVisualBlock(final Player player, final Location location, final boolean sendRemovalPacket) {
        synchronized (this.storedVisualises) {
            final VisualBlock visualBlock = (VisualBlock)this.storedVisualises.remove((Object)player.getUniqueId(), (Object)location);
            if (sendRemovalPacket && visualBlock != null) {
                final Block block = location.getBlock();
                final VisualBlockData visualBlockData = visualBlock.getBlockData();
                if (visualBlockData.getBlockType() != block.getType() || visualBlockData.getData() != block.getData()) {
                    player.sendBlockChange(location, block.getType(), block.getData());
                }
                // monitorexit(this.storedVisualises)
                return true;
            }
        }
        // monitorexit(this.storedVisualises)
        return false;
    }
    
    public Map<Location, VisualBlock> clearVisualBlocks(final Player player) {
        return this.clearVisualBlocks(player, null, null);
    }
    
    public Map<Location, VisualBlock> clearVisualBlocks(final Player player, @Nullable final VisualType visualType, @Nullable final Predicate<VisualBlock> predicate) {
        return this.clearVisualBlocks(player, visualType, predicate, true);
    }
    
    @Deprecated
    public Map<Location, VisualBlock> clearVisualBlocks(final Player player, @Nullable final VisualType visualType, @Nullable final Predicate<VisualBlock> predicate, final boolean sendRemovalPackets) {
        synchronized (this.storedVisualises) {
            if (!this.storedVisualises.containsRow((Object)player.getUniqueId())) {
                // monitorexit(this.storedVisualises)
                return Collections.emptyMap();
            }
            final Map<Location, VisualBlock> results = new HashMap<Location, VisualBlock>(this.storedVisualises.row((Object)player.getUniqueId()));
            final Map<Location, VisualBlock> removed = new HashMap<Location, VisualBlock>();
            for (final Map.Entry<Location, VisualBlock> entry : results.entrySet()) {
                final VisualBlock visualBlock = entry.getValue();
                if ((predicate == null || predicate.apply((Object)visualBlock)) && (visualType == null || visualBlock.getVisualType() == visualType)) {
                    final Location location = entry.getKey();
                    if (removed.put(location, visualBlock) != null) {
                        continue;
                    }
                    this.clearVisualBlock(player, location, sendRemovalPackets);
                }
            }
            // monitorexit(this.storedVisualises)
            return removed;
        }
    }
}
