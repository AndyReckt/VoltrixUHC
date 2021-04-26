package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import me.shir.uhcp.player.*;

public class StatsCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("stats")) {
            if (!GameManager.getGameManager().isStatsEnabled()) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cStats are currently disabled!");
                return true;
            }
            final Player playerS = (Player)sender;
            if (args.length < 1) {
                final Inventory inv = PlayerManager.getPlayerManager().getUHCPlayer(playerS.getUniqueId()).getStatsInventory();
                playerS.openInventory(inv);
                return true;
            }
            if (args.length > 0) {
                final OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
                UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(target.getUniqueId());
                if (uhcPlayer == null) {
                    PlayerManager.getPlayerManager().createUHCPlayer(target.getUniqueId());
                    uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(target.getUniqueId());
                }
                if (!uhcPlayer.hasData() && !target.isOnline()) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cThis player never played on this server!");
                    return true;
                }
                playerS.openInventory(uhcPlayer.getStatsInventory());
            }
        }
        return false;
    }
}
