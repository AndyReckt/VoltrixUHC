package me.shir.uhcp.cmds;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class WhitelistCMD implements CommandExecutor
{
    private GameManager gameManager;
    
    public WhitelistCMD() {
        this.gameManager = GameManager.getGameManager();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("whitelist")) {
            if (!sender.hasPermission("uhc.host") && !this.gameManager.getModerators().contains(sender)) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            final ChatColor color1 = this.gameManager.getMainColor();
            final ChatColor color2 = this.gameManager.getSecondaryColor();
            if (args.length == 0) {
                sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getErrorPrefix())) + "§c/whitelist <add/remove/on/off/clear/all> <player>");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    this.gameManager.setWhitelist(true);
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + "§bYou have enabled the whitelist.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("off")) {
                    this.gameManager.setWhitelist(false);
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + "§bYou have disabled the whitelist.");
                    if (TweetCMD.tweeted = true) {}
                    final String tweetwl = "tweet The Whitelist has turned to Off!||» Mode: " + GameManager.getGameManager().gameType() + "|» Scenarios: " + ScenarioManager.getInstance().getActiveScenarios().toString().replace("[", "").replace(",", "").replace("]", "") + "|» IP: na.ploveruhc.com||You Have 5 Minutes to Join into the Game!";
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), tweetwl);
                    return true;
                }
                if (args[0].equalsIgnoreCase("clear")) {
                    this.gameManager.getWhitelist().clear();
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + "§bYou have cleared the whitelist.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("all")) {
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                        final Player players = onlinePlayers[i];
                        this.gameManager.addWhitelist((OfflinePlayer)players);
                    }
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + "§bYou have added everyone to the whitelist.");
                    return true;
                }
                sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getErrorPrefix())) + "§c/whitelist <add/remove/on/off/clear/all> <player>");
                return true;
            }
            else if (args.length > 1) {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1].toLowerCase());
                if (args[0].equalsIgnoreCase("add")) {
                    if (offlinePlayer == null) {
                        sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getErrorPrefix())) + "§c" + args[1] + " doesn't exist!");
                        return true;
                    }
                    this.gameManager.addWhitelist(offlinePlayer);
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + color1 + offlinePlayer.getName() + " §fhas been added to the whitelist!");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("remove")) {
                    if (offlinePlayer == null) {
                        sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getErrorPrefix())) + "§c" + args[1] + " doesn't exist!");
                        return true;
                    }
                    this.gameManager.removeWhitelist(offlinePlayer);
                    sender.sendMessage(String.valueOf(String.valueOf(this.gameManager.getPrefix())) + color1 + offlinePlayer.getName() + " §fhas been removed from the whitelist!");
                    return true;
                }
            }
        }
        return false;
    }
}
