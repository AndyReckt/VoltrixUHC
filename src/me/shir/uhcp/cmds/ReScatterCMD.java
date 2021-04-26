package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;

public class ReScatterCMD implements CommandExecutor
{
    private final Set<CommandSender> used;
    
    public ReScatterCMD() {
        this.used = new HashSet<CommandSender>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rescatter")) {
            return false;
        }
        if (!sender.hasPermission("uhc.vip")) {
            sender.sendMessage("§cNo Permission!");
            return true;
        }
        if (!GameManager.getGameManager().isGameRunning()) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cThe game is not running!");
            return true;
        }
        if (GameManager.getGameManager().canUseRescatter()) {
            sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou can no longer use this command!");
            return true;
        }
        TasksManager.getInstance().scatterPlayer((Player)sender);
        this.used.add(sender);
        sender.sendMessage(GameManager.getGameManager().getMainColor() + "Successfully re-scattered!");
        return true;
    }
}
