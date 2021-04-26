package me.shir.uhcp.cuboid;

import org.bukkit.block.*;

public enum CuboidDirection
{
    NORTH("NORTH", 0), 
    EAST("EAST", 1), 
    SOUTH("SOUTH", 2), 
    WEST("WEST", 3), 
    UP("UP", 4), 
    DOWN("DOWN", 5), 
    HORIZONTAL("HORIZONTAL", 6), 
    VERTICAL("VERTICAL", 7), 
    BOTH("BOTH", 8), 
    UNKNOWN("UNKNOWN", 9);
    
    private CuboidDirection(final String s, final int n) {
    }
    
    public CuboidDirection opposite() {
        switch (this) {
            case BOTH: {
                return CuboidDirection.SOUTH;
            }
            case DOWN: {
                return CuboidDirection.WEST;
            }
            case EAST: {
                return CuboidDirection.NORTH;
            }
            case HORIZONTAL: {
                return CuboidDirection.EAST;
            }
            case UNKNOWN: {
                return CuboidDirection.VERTICAL;
            }
            case UP: {
                return CuboidDirection.HORIZONTAL;
            }
            case NORTH: {
                return CuboidDirection.DOWN;
            }
            case SOUTH: {
                return CuboidDirection.UP;
            }
            case VERTICAL: {
                return CuboidDirection.BOTH;
            }
            default: {
                return CuboidDirection.UNKNOWN;
            }
        }
    }
    
    public BlockFace toBukkitDirection() {
        switch (this) {
            case BOTH: {
                return BlockFace.NORTH;
            }
            case DOWN: {
                return BlockFace.EAST;
            }
            case EAST: {
                return BlockFace.SOUTH;
            }
            case HORIZONTAL: {
                return BlockFace.WEST;
            }
            case NORTH: {
                return BlockFace.UP;
            }
            case SOUTH: {
                return BlockFace.DOWN;
            }
            default: {
                return null;
            }
        }
    }
}
