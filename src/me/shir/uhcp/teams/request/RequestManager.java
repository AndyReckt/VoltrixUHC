package me.shir.uhcp.teams.request;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.teams.*;
import org.bukkit.*;

public class RequestManager implements Listener
{
    private static RequestManager instance;
    public Map<Player, Request> requestMap;
    
    public RequestManager() {
        this.requestMap = new HashMap<Player, Request>();
        RequestManager.instance = this;
    }
    
    public void register(final JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    public static RequestManager getInstance() {
        return RequestManager.instance;
    }
    
    public void sendRequest(final Player sender, final Player player, final Team team) {
        final ChatColor color1 = GameManager.getGameManager().getMainColor();
        final ChatColor color2 = GameManager.getGameManager().getSecondaryColor();
        if (!this.requestMap.containsKey(player)) {
            if (TeamManager.getInstance().getTeam((OfflinePlayer)player) == null) {
                this.requestMap.put(player, new Request(team, player));
                player.sendMessage(color1 + "You have received a team request from: \u00c2§r" + team.toString());
                player.sendMessage(color1 + "Use \u00c2§a" + "/team accept " + color1 + "or " + "\u00c2§c/team deny" + color1 + " to respond.");
                team.sendMessage("\u00c2§a" + team.getOwner().getName() + color1 + " invited " + color2 + player.getName() + color1 + " to the team!");
                RequestTime.requestTimer(player, team);
            }
            else {
                sender.sendMessage("\u00c2§cPlayer is already in a team!");
            }
        }
        else {
            sender.sendMessage("\u00c2§cPlayer already has a team request!");
        }
    }
    
    public Request getRequest(final Player player) {
        return this.requestMap.get(player);
    }
    
    public void declined(final Player player) {
        this.requestMap.remove(player).decline();
    }
    
    public void timedOut(final Player player) {
        this.requestMap.remove(player);
    }
}
