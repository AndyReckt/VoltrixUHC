package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.teams.*;

public class KillCountCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("killcount")) {
            if (!GameManager.getGameManager().isGameRunning()) {
                sender.sendMessage("\u00c2§cA UHC is not currently running!");
                return true;
            }
            if (args.length == 0) {
                final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(((Player)sender).getUniqueId());
                sender.sendMessage(GameManager.getGameManager().getMainColor() + "You have " + GameManager.getGameManager().getSecondaryColor() + uhcPlayer.getKills() + GameManager.getGameManager().getMainColor() + " kills.");
                final Team team = TeamManager.getInstance().getTeam((OfflinePlayer)sender);
                if (TeamManager.getInstance().isTeamsEnabled() && team != null) {
                    sender.sendMessage(GameManager.getGameManager().getMainColor() + "You have " + GameManager.getGameManager().getSecondaryColor() + team.getKills() + GameManager.getGameManager().getMainColor() + " team kills.");
                }
                return true;
            }
            if (args.length > 0) {
                final OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                if (pl == null) {
                    sender.sendMessage("\u00c2§cCould not find player!");
                    return true;
                }
                final UHCPlayer uhcPlayer2 = PlayerManager.getPlayerManager().getUHCPlayer(pl.getUniqueId());
                if (uhcPlayer2 == null) {
                    sender.sendMessage("\u00c2§cThis player hasn't played this UHC!");
                    return true;
                }
                sender.sendMessage(GameManager.getGameManager().getMainColor() + pl.getName() + " has " + GameManager.getGameManager().getSecondaryColor() + uhcPlayer2.getKills() + GameManager.getGameManager().getMainColor() + " kills.");
                final Team team2 = TeamManager.getInstance().getTeam(pl);
                if (TeamManager.getInstance().isTeamsEnabled() && team2 != null) {
                    sender.sendMessage(GameManager.getGameManager().getMainColor() + pl.getName() + " has " + GameManager.getGameManager().getSecondaryColor() + team2.getKills() + GameManager.getGameManager().getMainColor() + " team kills.");
                }
                return true;
            }
        }
        return false;
    }
}
