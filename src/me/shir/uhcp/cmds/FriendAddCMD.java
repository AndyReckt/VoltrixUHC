package me.shir.uhcp.cmds;

import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class FriendAddCMD implements CommandExecutor
{
    private final Map<Player, String> getWhitelisted;
    private final GameManager gameManager;
    
    public FriendAddCMD() {
        this.getWhitelisted = new HashMap<Player, String>();
        this.gameManager = GameManager.getGameManager();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("game")) {
            if (!sender.hasPermission("uhc.vip")) {
                sender.sendMessage(ChatColor.DARK_RED + "No Permission!");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(this.gameManager.getSecondaryColor() + "---------------");
                sender.sendMessage(this.gameManager.getMainColor() + "§nWhiteList commands: ");
                sender.sendMessage(" ");
                sender.sendMessage("§71) §cUse - /game add <player> To add a player to the whitelist.");
                sender.sendMessage("§72) §cUse - /game remove <player> To remove a player from the whitelist.");
                sender.sendMessage(this.gameManager.getSecondaryColor() + "---------------");
                return true;
            }
            if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") && !args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(this.gameManager.getSecondaryColor() + "---------------");
                sender.sendMessage(this.gameManager.getMainColor() + "§nWhiteList commands: ");
                sender.sendMessage(" ");
                sender.sendMessage("§71) §cUse - /game add <player> To add a player to the whitelist.");
                sender.sendMessage("§72) §cUse - /game remove <player> To remove a player from the whitelist.");
                sender.sendMessage(this.gameManager.getSecondaryColor() + "---------------");
                return true;
            }
            final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            final Player pl = (Player)sender;
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "/game add <Player>");
                    return true;
                }
                if (this.getWhitelisted.get(pl) != null) {
                    sender.sendMessage("§cYou can only whitelist 1 player per game!");
                    return true;
                }
                if (target == null) {
                    sender.sendMessage("§cPlayer was not found!");
                    return true;
                }
                if (target.isWhitelisted()) {
                    sender.sendMessage("§cThis player is already whitelisted!");
                    return true;
                }
                if (this.gameManager.isGameRunning() || this.gameManager.isScattering()) {
                    sender.sendMessage("§cYou cannot whitelist after the game has already started!");
                    return true;
                }
                target.setWhitelisted(true);
                this.getWhitelisted.put(pl, target.getUniqueId().toString());
                sender.sendMessage(this.gameManager.getMainColor() + "Whitelisted: " + this.gameManager.getSecondaryColor() + target.getName());
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    sender.sendMessage("§c/game remove <Player>");
                    return true;
                }
                if (target == null) {
                    sender.sendMessage("§cPlayer was not found!");
                    return true;
                }
                if (this.gameManager.isGameRunning() || this.gameManager.isScattering()) {
                    sender.sendMessage("§cYou cannot edit the whitelist after the game has started!");
                    return true;
                }
                if (this.getWhitelisted.get(pl) == null) {
                    sender.sendMessage("§cYou haven't whitelisted anyone!");
                    return true;
                }
                if (!this.getWhitelisted.get(pl).equalsIgnoreCase(target.getUniqueId().toString())) {
                    sender.sendMessage("§cYou haven't whitelisted this player!");
                    return true;
                }
                if (this.getWhitelisted.get(pl).equalsIgnoreCase(target.getUniqueId().toString())) {
                    target.setWhitelisted(false);
                    this.getWhitelisted.remove(pl);
                    if (target.isOnline()) {
                        Bukkit.getPlayer(target.getName()).kickPlayer("You were unwhitelisted by " + pl.getName());
                    }
                    sender.sendMessage(this.gameManager.getMainColor() + "Unwhitelisted: " + this.gameManager.getSecondaryColor() + target.getName());
                    return true;
                }
            }
        }
        return false;
    }
}
