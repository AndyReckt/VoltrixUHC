package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import me.shir.uhcp.listeners.*;
import org.bukkit.event.*;
import me.shir.uhcp.*;
import me.shir.uhcp.world.*;
import me.shir.uhcp.world.WorldCreator;

public class CreateCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("createuhc")) {
            return false;
        }
        if (!sender.hasPermission("uhc.host") && !GameManager.getGameManager().getHostName().equalsIgnoreCase(sender.getName())) {
            sender.sendMessage("§cYou Dont have Permission!");
            return true;
        }
        if (GameManager.getGameManager().isGameRunning()) {
            Bukkit.getServer().getPluginManager().callEvent((Event)new GameStopEvent("World re-creation (command)"));
        }
        if (VenixUHC.getInstance().getConfig().getBoolean("settings.using-world-border-plugin")) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "worldborder:wb fill cancel");
        }
        new WorldCreator(true, true);
        sender.sendMessage(GameManager.getGameManager().getMainColor() + "Successfully created a new game!");
        return true;
    }
}
