package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import java.text.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;

public class HealthCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("health")) {
            final DecimalFormat df = new DecimalFormat("#.#");
            if (args.length < 1) {
                final Player playerS = (Player)sender;
                final double health = playerS.getHealthScale() / 2.0;
                sender.sendMessage(GameManager.getGameManager().getMainColor() + "Your Health: \u00c2§a" + df.format(health) + "\u00c2§4 \u00ef¿½\u00c2¤");
                return true;
            }
            if (args.length > 0) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find player!");
                    return true;
                }
                final double health = target.getHealthScale() / 2.0;
                sender.sendMessage(GameManager.getGameManager().getMainColor() + target.getName() + "'s Health: \u00c2§a" + df.format(health) + "\u00c2§4 \u00ef¿½\u00c2¤");
                return true;
            }
        }
        return false;
    }
}
