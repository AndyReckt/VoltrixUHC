package me.shir.uhcp.teams;

import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.inventory.*;
import me.shir.uhcp.player.*;
import java.util.*;
import org.bukkit.*;
import java.text.*;

public class Team
{
    private final Set<UUID> players;
    private Player owner;
    private int id;
    private int kills;
    private Inventory backPack;
    private Location scatterLocation;
    
    protected Team(final Player owner, final int id) {
        this.players = new HashSet<UUID>();
        this.kills = 0;
        this.scatterLocation = GameManager.getGameManager().getScatterLocation();
        this.owner = owner;
        this.id = id;
        this.backPack = Bukkit.createInventory((InventoryHolder)null, 27, "§aTeam #" + id + " Backpack");
    }
    
    public Inventory getBackPack() {
        return this.backPack;
    }
    
    public Location getScatterLocation() {
        return this.scatterLocation;
    }
    
    public void setScatterLocation(final Location scatterLocation) {
        this.scatterLocation = scatterLocation;
    }
    
    public Set<UUID> getPlayers() {
        return this.players;
    }
    
    public Player getOwner() {
        return this.owner;
    }
    
    public String getTeamColor() {
        return TeamManager.teamcolor.get(TeamManager.getInstance().getTeam((OfflinePlayer)this.owner).getId());
    }
    
    boolean isAlive() {
        for (final UHCPlayer uhcPlayer : PlayerManager.getPlayerManager().uhcPlayersSet(this.players)) {
            if (uhcPlayer != null && uhcPlayer.isPlayerAlive()) {
                return true;
            }
        }
        return false;
    }
    
    public int getSize() {
        return this.getPlayers().size();
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getKills() {
        return this.kills;
    }
    
    public void addKill() {
        ++this.kills;
    }
    
    @Override
    public String toString() {
        String out = "§a[Team " + String.valueOf(this.id) + "§a]";
        for (final UUID uuid : this.players) {
            final OfflinePlayer offp = Bukkit.getOfflinePlayer(uuid);
            if (offp.isOnline() && offp.getPlayer().getGameMode().equals((Object)GameMode.SURVIVAL) && offp.getPlayer().getWorld().equals(GameManager.getGameManager().getUHCWorld())) {
                final double h = Bukkit.getPlayer(uuid).getHealthScale() / 2.0;
                final DecimalFormat df = new DecimalFormat("#.#");
                out = String.valueOf(out) + "§a " + Bukkit.getPlayer(uuid).getName() + "§e(§a" + df.format(h) + "§4 \u2665§e)§e(§a" + PlayerManager.getPlayerManager().getUHCPlayer(uuid).getKills() + " kills§e)§6,";
            }
            else {
                out = String.valueOf(out) + "§c " + Bukkit.getOfflinePlayer(uuid).getName() + "§6,§a";
            }
        }
        return out;
    }
    
    void removePlayer(final OfflinePlayer player) {
        this.players.remove(player.getUniqueId());
        if (player.isOnline()) {
            player.getPlayer().sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + "§aYou are no longer part of your team!");
        }
        for (final UUID uuid : this.getPlayers()) {
            final Player player2 = Bukkit.getPlayer(uuid);
            if (player2 != null) {
                player2.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + "§a" + player.getName() + " §fhas left your team.");
            }
        }
    }
    
    void addPlayer(final Player player) {
        for (final UUID uuid : this.getPlayers()) {
            final Player player2 = Bukkit.getPlayer(uuid);
            if (player2 != null) {
                player2.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + "§a" + player.getName() + " §fhas joined your team.");
            }
        }
        this.players.add(player.getUniqueId());
        player.sendMessage(String.valueOf(TeamManager.getInstance().getTeamsPrefix()) + GameManager.getGameManager().getMainColor() + "Joined team: §e" + "[#" + this.id + "]");
    }
    
    public void sendMessage(final String message) {
        for (final UUID uuid : this.getPlayers()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(message);
            }
        }
    }
    
    public void setTeamColor(final Player owner, final String color) {
        TeamManager.teamcolor.put(TeamManager.getInstance().getTeam((OfflinePlayer)owner).getId(), color);
    }
}
