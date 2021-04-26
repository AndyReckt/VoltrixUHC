package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.*;
import me.shir.uhcp.game.*;

public class MapGeneratedCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("ismapgenerated")) {
            return false;
        }
        if (!sender.hasPermission("uhc.host")) {
            sender.sendMessage("§cNo permission!");
            return true;
        }
        if (!VenixUHC.getInstance().getConfig().getBoolean("settings.using-world-border-plugin")) {
            sender.sendMessage("§cYou aren't using WorldBorder plugin.");
            return true;
        }
        if (GameManager.getGameManager().isGameRunning()) {
            sender.sendMessage("§cYou can't use it after the game started.");
            return true;
        }
        sender.sendMessage("§7§m----------------");
        sender.sendMessage(" ");
        sender.sendMessage("§6MAP Generating!");
        sender.sendMessage("§6Generated:§c" + GameManager.getGameManager().wasGenerated());
        sender.sendMessage(" ");
        sender.sendMessage("§7§m----------------");
        return true;
    }
}
