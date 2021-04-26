package me.shir.uhcp.barrier;

import org.bukkit.entity.*;
import org.bukkit.*;

public enum VisualType{
    UHC_BORDER(0) {
        private final BlockFiller blockFiller;
        
        {
            this.blockFiller = new BlockFiller() {
                @Override
                VisualBlockData generate(final Player player, final Location location) {
                    return new VisualBlockData(Material.STAINED_GLASS, DyeColor.RED.getData());
                }
            };
        }
        
        @Override
        BlockFiller blockFiller() {
            return this.blockFiller;
        }
    }, 
    CLAIM_BORDER(1) {
        private final BlockFiller blockFiller;
        
        {
            this.blockFiller = new BlockFiller() {
                @Override
                VisualBlockData generate(final Player player, final Location location) {
                    return new VisualBlockData(Material.STAINED_GLASS, DyeColor.RED.getData());
                }
            };
        }
        
        @Override
        BlockFiller blockFiller() {
            return this.blockFiller;
        }
    };
    
    private VisualType(final String s, final int n) {
    }
    
    abstract BlockFiller blockFiller();
}