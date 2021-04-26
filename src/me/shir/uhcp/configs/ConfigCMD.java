package me.shir.uhcp.configs;

import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import me.shir.uhcp.scenarios.*;

public class ConfigCMD implements CommandExecutor
{
    private final GameManager gameManager;
    
    public ConfigCMD() {
        this.gameManager = GameManager.getGameManager();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("config")) {
            if (args.length <= 0) {
                sender.sendMessage("§7§m--------------------------");
                sender.sendMessage("§b§lMatch Configuration §7:|:§c " + this.gameManager.getHostName());
                sender.sendMessage("");
                sender.sendMessage("§a§lTime Of PvP:§f " + ConfigIntegers.PVP_TIME.get() + "m");
                sender.sendMessage("§a§lTime Of FinalHeal:§f " + ConfigIntegers.HEAL_TIME.get() + "m");
                sender.sendMessage("§a§lTime Of Border:§f " + ConfigIntegers.BORDER_SHRINK_TIME.get() + "m");
                sender.sendMessage("");
                sender.sendMessage("§a§lSlots Of the Match:§f " + ConfigIntegers.MAX_PLAYERS.get());
                sender.sendMessage("§a§lNether:§f " + ConfigBooleans.NETHER.isEnabled());
                sender.sendMessage("");
                sender.sendMessage("§a§lScenarios: " + ScenarioManager.getInstance().getActiveScenarios().toString().replace("[", "").replace(",", "").replace("]", ""));
                sender.sendMessage("§7§m--------------------------");
                return true;
            }
            if (sender.hasPermission("uhc.host") || this.gameManager.getHostName().equalsIgnoreCase(sender.getName())) {
                if (args.length == 1 && !args[0].equalsIgnoreCase("types")) {
                    sender.sendMessage("§3Config §7:|: §c/Use Config <configType> <True/False/Number>");
                    return true;
                }
                if (ConfigIntegers.fromString(args[0]) == null && ConfigBooleans.fromString(args[0]) == null && !args[0].equalsIgnoreCase("applerates") && !args[0].equalsIgnoreCase("applesrate")) {
                    sender.sendMessage("§3Config §7:|: §cConfig type could not be found!");
                    return true;
                }
                final ConfigIntegers configValue = ConfigIntegers.fromString(args[0]);
                if (configValue != null) {
                    if (!this.isNumeric(args[1])) {
                        sender.sendMessage("§3Config §7:|: §cThis config type must be a number!");
                        return true;
                    }
                    if (configValue.equals(ConfigIntegers.BORDER_SHRINK_TIME) && Integer.parseInt(args[1]) < 10) {
                        sender.sendMessage("§3Config §7:|: §cBorder shrink time cannot be smaller than 10 minutes!");
                        return true;
                    }
                    if (configValue.equals(ConfigIntegers.BORDER_SHRINK_TIME) && GameManager.getGameManager().isGameRunning()) {
                        sender.sendMessage("§3Config §7:|: §cYou can't change the border shrink time after the game has already started!");
                        return true;
                    }
                    configValue.set(Integer.parseInt(args[1]), sender);
                    return true;
                }
                else if (ConfigBooleans.fromString(args[0]) != null) {
                    if (!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
                        sender.sendMessage("§3Config §7:|: §cThis config type must be a boolean! (True/False)");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("true")) {
                        ConfigBooleans.fromString(args[0]).enable(sender);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("false")) {
                        ConfigBooleans.fromString(args[0]).disable(sender);
                        return true;
                    }
                    return true;
                }
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
