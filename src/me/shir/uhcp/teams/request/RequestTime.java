package me.shir.uhcp.teams.request;

import org.bukkit.entity.*;
import me.shir.uhcp.teams.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

class RequestTime
{
    static void requestTimer(final Player player, final Team team) {
        new BukkitRunnable() {
            public void run() {
                final Request request = RequestManager.getInstance().getRequest(player);
                if (request != null) {
                    RequestManager.getInstance().timedOut(player);
                    player.sendMessage("\u00c2§cYour request from " + team.getOwner().getName() + " timed out!");
                    team.getOwner().sendMessage("\u00c2§cTeam invite to " + player.getName() + " timed out!");
                }
            }
        }.runTaskLater((Plugin)VenixUHC.getInstance(), 350L);
    }
}
