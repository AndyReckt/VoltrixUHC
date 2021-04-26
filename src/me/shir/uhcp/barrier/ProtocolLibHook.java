package me.shir.uhcp.barrier;

import me.shir.uhcp.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.block.Block;

import com.comphenix.protocol.reflect.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.events.PacketListener;

import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.server.v1_7_R4.Material;

import com.comphenix.protocol.*;
import org.bukkit.*;

public class ProtocolLibHook
{
    private static final Material RED_ROSE = null;
	private static final Material WORKBENCH = null;

	public static void hook(final VenixUHC hcf, final VenixUHC Main) {
        final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter(VenixUHC.getInstance(), ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.BLOCK_PLACE }) {
            public void onPacketReceiving(final PacketEvent event) {
                final PacketContainer packet = event.getPacket();
                final StructureModifier<Integer> modifier = (StructureModifier<Integer>)packet.getIntegers();
                final Player player = event.getPlayer();
                try {
                    if (modifier.size() < 4 || (int)modifier.read(3) == 255) {
                        return;
                    }
                    final int face2 = 0;
                    final Location location = new Location(player.getWorld(), (double)(int)modifier.read(0), (double)(int)modifier.read(1), (double)(int)modifier.read(2));
                    VisualBlock visualBlock = hcf.getVisualiseHandler().getVisualBlockAt(player, location);
                    if (visualBlock == null) {
                        return;
                    }
                    switch (face2) {
                        case 0: {
                            location.add(0.0, -1.0, 0.0);
                            break;
                        }
                        case 1: {
                            location.add(0.0, 1.0, 0.0);
                            break;
                        }
                        case 2: {
                            location.add(0.0, 0.0, -1.0);
                            break;
                        }
                        case 3: {
                            location.add(0.0, 0.0, 1.0);
                            break;
                        }
                        case 4: {
                            location.add(-1.0, 0.0, 0.0);
                            break;
                        }
                        case 5: {
                            location.add(1.0, 0.0, 0.0);
                            break;
                        }
                        default: {
                            return;
                        }
                    }
                    event.setCancelled(true);
                    final ItemStack stack = (ItemStack)packet.getItemModifier().read(0);
                    if (stack != null && (stack.getType().isBlock() || isLiquidSource(stack.getType()))) {
                        player.setItemInHand(player.getItemInHand());
                    }
                    visualBlock = hcf.getVisualiseHandler().getVisualBlockAt(player, location);
                    if (visualBlock != null) {
                        final VisualBlockData visualBlockData = visualBlock.getBlockData();
                        player.sendBlockChange(location, visualBlockData.getBlockType(), visualBlockData.getData());
                    }
                    else {
                        new BukkitRunnable() {
                            public void run() {
                                final Block block = location.getBlock();
                                player.sendBlockChange(location, block.getType(), block.getData());
                            }
                        }.runTask((Plugin)VenixUHC.getInstance());
                    }
                }
                catch (FieldAccessException ex) {}
            }
        });
        protocolManager.addPacketListener((PacketListener)new PacketAdapter(VenixUHC.getInstance(), ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.BLOCK_DIG }) {
            public void onPacketReceiving(final PacketEvent event) {
                final PacketContainer packet = event.getPacket();
                final StructureModifier<Integer> modifier = (StructureModifier<Integer>)packet.getIntegers();
                final Player player = event.getPlayer();
                try {
                    final int status = (int)modifier.read(4);
                    if (status == 0 || status == 2) {
                        final int x = (int)modifier.read(0);
                        final int y = (int)modifier.read(1);
                        final int z = (int)modifier.read(2);
                        final Location location = new Location(player.getWorld(), (double)x, (double)y, (double)z);
                        final VisualBlock visualBlock = hcf.getVisualiseHandler().getVisualBlockAt(player, location);
                        if (visualBlock == null) {
                            return;
                        }
                        event.setCancelled(true);
                        final VisualBlockData data = visualBlock.getBlockData();
                        if (status == 2) {
                            player.sendBlockChange(location, data.getBlockType(), data.getData());
                        }
                        else if (status == 0) {
                            final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
                            if (player.getGameMode() == GameMode.CREATIVE || net.minecraft.server.v1_7_R4.Block.getById(data.getItemTypeId()).getDamage((EntityHuman)entityPlayer, entityPlayer.world, x, y, z) > 1.0f) {
                                player.sendBlockChange(location, data.getBlockType(), data.getData());
                            }
                        }
                    }
                }
                catch (FieldAccessException ex) {}
            }
        });
    }
    
    protected static boolean isLiquidSource(org.bukkit.Material type) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isLiquidSource(final Material material) {
        switch (material) {
            case RED_ROSE:
            case WORKBENCH: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}

