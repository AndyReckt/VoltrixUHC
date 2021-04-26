package me.shir.uhcp.listeners;

import org.bukkit.event.*;
import me.shir.uhcp.player.*;

public class GameWinListener extends Event
{
    private static HandlerList handlers;
    private String winner;
    private UHCPlayer uhcPlayer;
    
    static {
        GameWinListener.handlers = new HandlerList();
    }
    
    public HandlerList getHandlers() {
        return GameWinListener.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GameWinListener.handlers;
    }
    
    public GameWinListener(final String winner, final UHCPlayer uhcPlayer) {
        this.winner = winner;
        this.uhcPlayer = uhcPlayer;
    }
    
    public String getWinner() {
        return this.winner;
    }
    
    public UHCPlayer getUHCPlayer() {
        return this.uhcPlayer;
    }
}
