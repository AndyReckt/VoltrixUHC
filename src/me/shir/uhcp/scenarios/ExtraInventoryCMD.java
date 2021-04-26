package me.shir.uhcp.scenarios;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;

public class ExtraInventoryCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("extrainventory")) {
            if (!ScenarioManager.getInstance().getScenarioExact("ExtraInventory").isEnabled()) {
                sender.sendMessage("\u00c2§cExtraInventory scenario is not currently enabled!");
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
            pl.openInventory(pl.getEnderChest());
            pl.sendMessage("\u00c2§aOpened extra inventory!");
        }
        return false;
    }
}
