package me.shir.uhcp.listeners;

import org.bukkit.event.*;
import me.shir.uhcp.teams.*;
import java.util.*;

public class GameWinTeamListener extends Event
{
    private static HandlerList handlers;
    private Team team;
    
    static {
        GameWinTeamListener.handlers = new HandlerList();
    }
    
    public HandlerList getHandlers() {
        return GameWinTeamListener.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GameWinTeamListener.handlers;
    }
    
    public GameWinTeamListener(final Team team) {
        this.team = team;
    }
    
    public Team getWinTeam() {
        return this.team;
    }
    
    public Set<UUID> getUUIDs() {
        return this.team.getPlayers();
    }
}
