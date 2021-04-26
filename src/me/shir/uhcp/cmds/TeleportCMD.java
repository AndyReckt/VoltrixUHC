package me.shir.uhcp.cmds;

import org.bukkit.command.*;
import me.shir.uhcp.game.*;
import org.bukkit.entity.*;
import me.shir.uhcp.player.*;
import org.bukkit.*;

public class TeleportCMD implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleport")) {
            final UHCPlayer uhcPlayer = PlayerManager.getPlayerManager().getUHCPlayer(((Player)sender).getUniqueId());
            if (!uhcPlayer.isSpectating() && !GameManager.getGameManager().getModerators().contains(((Player)sender).getPlayer())) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/tele <player>");
                if (GameManager.getGameManager().getModerators().contains(((Player)sender).getPlayer())) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/tele <player>");
                    return true;
                }
                return true;
            }
            else {
                final Player playerS = (Player)sender;
                final ChatColor color = GameManager.getGameManager().getMainColor();
                final ChatColor color2 = GameManager.getGameManager().getSecondaryColor();
                if (args.length == 1) {
                    final Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
                        return true;
                    }
                    playerS.teleport((Entity)target);
                    playerS.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + color2 + "You have been teleported to " + color + target.getName());
                    return true;
                }
                else if (args.length == 2) {
                    final Player secondTarget = Bukkit.getServer().getPlayer(args[1]);
                    final Player firstTarget = Bukkit.getServer().getPlayer(args[0]);
                    if (!args[0].equalsIgnoreCase(playerS.getName()) && !GameManager.getGameManager().getModerators().contains(sender)) {
                        sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                        return true;
                    }
                    if (firstTarget == null) {
                        sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
                        return true;
                    }
                    if (secondTarget == null) {
                        sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[1] + " must be online!");
                        return true;
                    }
                    firstTarget.teleport((Entity)secondTarget);
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + color + firstTarget.getName() + color2 + " has been teleported to " + color + secondTarget.getName());
                    firstTarget.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + color2 + "You have been teleported to " + color + secondTarget.getName());
                    return true;
                }
                else if (args.length == 3) {
                    if (!this.isNum(args[0]) && !this.isNum(args[1]) && !this.isNum(args[2])) {
                        sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cLocation must be a number");
                        return true;
                    }
                    final double x = Double.parseDouble(args[0]);
                    final double y = Double.parseDouble(args[1]);
                    final double z = Double.parseDouble(args[2]);
                    playerS.teleport(new Location(playerS.getWorld(), x, y, z));
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + color2 + "You have been teleported to " + color + x + ", " + y + ", " + z);
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isNum(final String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
