package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;

public class StaffChatCMD implements CommandExecutor
{
    public static ArrayList<UUID> instaffchat;
    
    static {
        StaffChatCMD.instaffchat = new ArrayList<UUID>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("staffchat")) {
            final Player p = (Player)sender;
            if (!sender.hasPermission("uhc.host")) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (args.length < 1) {
                if (StaffChatCMD.instaffchat.contains(p.getUniqueId())) {
                    StaffChatCMD.instaffchat.remove(p.getUniqueId());
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§bStaff Chat is now §cuntoggled!");
                }
                else {
                    StaffChatCMD.instaffchat.add(p.getUniqueId());
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§bStaff Chat is now §atoggled!");
                }
            }
            else {
                final StringBuilder scMsg = new StringBuilder();
                for (final String s : args) {
                    scMsg.append(s).append(" ");
                }
                Player[] onlinePlayers;
                for (int length2 = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player staff = onlinePlayers[j];
                    if (staff.hasPermission("uhc.host")) {
                        staff.sendMessage("§b(Staff) " + sender.getName() + ": §f" + (Object)scMsg);
                    }
                }
            }
        }
        return false;
    }
}
