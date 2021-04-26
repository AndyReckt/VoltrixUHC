package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.scenarios.*;
import me.shir.uhcp.player.*;
import me.shir.uhcp.teams.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;
import me.shir.uhcp.listeners.*;
import org.bukkit.event.*;
import me.shir.uhcp.world.*;
import me.shir.uhcp.world.WorldCreator;

import org.bukkit.entity.*;

public class StopGameCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("stopuhc")) {
            if (!sender.hasPermission("uhc.host") && !GameManager.getGameManager().getHostName().equalsIgnoreCase(sender.getName())) {
                sender.sendMessage("\u00c2§cNo Permission!");
                return true;
            }
            if (!GameManager.getGameManager().isScattering() && !GameManager.getGameManager().isGameRunning()) {
                sender.sendMessage("\u00c2§cA UHC is not currently running!");
                return true;
            }
            sender.sendMessage("\u00c2§cStopping UHC...");
            GameManager.getGameManager().setGameRunning(false);
            new GameManager();
            new ScenarioManager();
            PlayerManager.getPlayerManager().getUHCPlayers().clear();
            new PlayerManager();
            TeamManager.getInstance().clearTeams();
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player player = onlinePlayers[i];
                player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
            }
            Bukkit.getServer().getPluginManager().callEvent((Event)new GameStopEvent("Force stopped with command."));
            GameManager.getGameManager().setCanBorderShrink(false);
            new WorldCreator(true, true);
            sender.sendMessage("\u00c2§cStopped UHC!");
            if (GameManager.getGameManager().restartOnEnd()) {
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), GameManager.getGameManager().getRestartCommand().replace("/", ""));
            }
        }
        return false;
    }
}
