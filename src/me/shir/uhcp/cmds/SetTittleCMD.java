package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;
import me.shir.uhcp.game.*;

public class SetTittleCMD implements CommandExecutor
{
    private ScoreboardHandler scoreboardHandler;
    
    public SetTittleCMD() {
        this.scoreboardHandler = new ScoreboardHandler();
    }
    
    public boolean onCommand(final CommandSender paramCommandSender, final Command paramCommand, final String paramString, final String[] paramArrayOfString) {
        if (!paramCommand.getName().equalsIgnoreCase("settitle")) {
            return true;
        }
        if (!(paramCommandSender instanceof Player)) {
            return true;
        }
        final Player localPlayer = (Player)paramCommandSender;
        if (!localPlayer.hasPermission("uhc.host")) {
            localPlayer.sendMessage("§cInsufficient permissions.");
            return true;
        }
        if (paramArrayOfString.length == 0) {
            localPlayer.sendMessage("§e/§fsettitle §a<title>");
            return true;
        }
        final String str1 = paramArrayOfString[0];
        if (str1.length() > 16) {
            str1.substring(0, 16);
        }
        final String str2 = str1.replaceAll("&", "§");
        final Player[] onlinePlayers;
        final int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length;
        final int i = 0;
        if (i < length) {
            final Player player = onlinePlayers[i];
            final ScoreboardHelper board = this.scoreboardHandler.getScoreboard(player);
            board.getScoreBoard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(str2);
            return true;
        }
        return true;
    }
}