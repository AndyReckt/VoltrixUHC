package me.shir.uhcp.scenarios;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;
import me.shir.uhcp.teams.*;

public class BackPackCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("backpack")) {
            if (!TeamManager.getInstance().isTeamsEnabled()) {
                sender.sendMessage("\u00c2§cTeams are not currently enabled!");
                return true;
            }
            if (!ScenarioManager.getInstance().getScenarioExact("BackPacks").isEnabled()) {
                sender.sendMessage("\u00c2§cBackPacks scenario is not currently enabled!");
                return true;
            }
            if (!GameManager.getGameManager().isGameRunning()) {
                sender.sendMessage("\u00c2§cA UHC is not currently running!");
                return true;
            }
            final Player pl = (Player)sender;
            if (PlayerManager.getPlayerManager().getUHCPlayer(pl.getUniqueId()).isSpectating()) {
                pl.sendMessage("\u00c2§cYou cannot use this command while spectating!");
                return true;
            }
            if (TeamManager.getInstance().getTeam((OfflinePlayer)pl) != null) {
                final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)pl.getPlayer());
                pl.openInventory(team.getBackPack());
            }
        }
        return false;
    }
}
