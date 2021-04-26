package me.shir.uhcp.scenarios;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;

public class ScenarioCMD implements CommandExecutor
{
    private final Inventory scenarioInv;
    
    public ScenarioCMD() {
        this.scenarioInv = Bukkit.createInventory((InventoryHolder)null, 36, "§6Scenarios Manager:");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("scenario")) {
            final Player playerS = (Player)sender;
            if (args.length <= 0) {
                int x = 0;
                for (final Scenario scenario : ScenarioManager.getInstance().getScenarios().values()) {
                    if (scenario.isEnabled()) {
                        ++x;
                    }
                }
                if (x == 0) {
                    playerS.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cAll of the scenarios are currently disabled!");
                    return true;
                }
                playerS.openInventory(ScenarioManager.getInstance().getScenariosInventory());
                return true;
            }
            else if (sender.hasPermission("uhc.host") || GameManager.getGameManager().getHostName().equalsIgnoreCase(playerS.getName())) {
                if (args[0].equalsIgnoreCase("manage")) {
                    playerS.openInventory(this.getAllScenariosInv());
                    return true;
                }
                if (args.length == 1 && !args[0].equalsIgnoreCase("manage")) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/scenario <scenario> <enable/disable>");
                    return true;
                }
                if (!ScenarioManager.getInstance().doesScenarioExists(args[0])) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + ChatColor.RED + "This scenario is not set!");
                    return true;
                }
                if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("true")) {
                    final Scenario scenario2 = ScenarioManager.getInstance().getScenarioIgnoreCase(args[0]);
                    scenario2.setEnabled(true);
                    return true;
                }
                if (args[1].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("false")) {
                    final Scenario scenario2 = ScenarioManager.getInstance().getScenarioIgnoreCase(args[0]);
                    scenario2.setEnabled(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    private Inventory getAllScenariosInv() {
        this.scenarioInv.clear();
        for (final Scenario scenario : ScenarioManager.getInstance().getScenarios().values()) {
            this.scenarioInv.addItem(new ItemStack[] { scenario.getScenarioIST() });
        }
        return this.scenarioInv;
    }
}
