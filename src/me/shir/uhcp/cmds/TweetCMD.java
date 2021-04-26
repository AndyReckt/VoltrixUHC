package me.shir.uhcp.cmds;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.scenarios.*;
import org.bukkit.*;
import fr.galaxyoyo.spigot.twitterapi.*;
import twitter4j.*;

public class TweetCMD implements CommandExecutor
{
    private final GameManager gameManager;
    private final ChatColor color;
    public static boolean tweeted;
    
    static {
        TweetCMD.tweeted = false;
    }
    
    public TweetCMD() {
        this.gameManager = GameManager.getGameManager();
        this.color = this.gameManager.getMainColor();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("tweetgame")) {
            final Player p = (Player)sender;
            if (!sender.hasPermission("uhc.host")) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§cYou don't have permissions to execute this command!");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§c/tweetgame <time>");
                return true;
            }
            if (!args[0].contains(":")) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§cYou must be put ':'");
                return true;
            }
            if (args[0].length() < 5) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§cYou must be put 4 numbers and ':'");
                return true;
            }
            final String surevalid = args[0].replace(":", "");
            if (!this.isNumeric(surevalid)) {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getErrorPrefix())) + "§cYou must be put 4 numbers and ':'");
                return true;
            }
            final Player player = (Player)sender;
            TweetCMD.tweeted = true;
            final String time = String.valueOf(args[0]);
            final String tweetuhc = "tweet VoltrixPvP UHC Game [North America]||- Scenarios: " + ScenarioManager.getInstance().getActiveScenarios().toString().replace("[", "").replace(",", "").replace("]", "") + "|- Type: " + GameManager.getGameManager().gameType() + "|- WL OFF: " + time + " time.is/Mexico||- IP: voltrixpvp.win";
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), tweetuhc);
            try {
                sender.sendMessage(String.valueOf(String.valueOf(GameManager.getGameManager().getPrefix())) + "§e§lYour Tweet has been Posted! " + "§cID:§e " + TwitterAPI.instance().getTwitter().getId());
            }
            catch (IllegalStateException | TwitterException ex2) {
                final Exception ex = null;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean isNumeric(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
