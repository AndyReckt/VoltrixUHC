package me.shir.uhcp.cmds;

import org.bukkit.*;
import me.shir.uhcp.game.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.shir.uhcp.lang.*;
import me.shir.uhcp.util.*;
import me.shir.uhcp.player.*;
import java.util.*;

public class BestKillersCMD implements CommandExecutor
{
    private final ChatColor color1;
    private final ChatColor color2;
    
    public BestKillersCMD() {
        this.color1 = GameManager.getGameManager().getMainColor();
        this.color2 = GameManager.getGameManager().getSecondaryColor();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("bestkillers")) {
            if (!GameManager.getGameManager().isGameRunning()) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§6§lKT §7:|: §3The Game has not yet Started.");
                }
                return true;
            }
            sender.sendMessage("§7§m*---------§4§lKT§7§m---------*");
            this.getBestKillers(10, sender);
            sender.sendMessage("§7§m*---------§4§lKT§7§m---------*");
        }
        return false;
    }
    
    private void getBestKillers(final int amount, final CommandSender sender) {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        final ValueComparator bvc = new ValueComparator(map);
        final TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        for (final UHCPlayer uhcPlayers : PlayerManager.getPlayerManager().getUHCPlayers().values()) {
            if (uhcPlayers.getKills() > 0) {
                if (uhcPlayers.isPlayerAlive()) {
                    map.put(ChatColor.GOLD + uhcPlayers.getName(), uhcPlayers.getKills());
                }
                else {
                    map.put(ChatColor.RED + uhcPlayers.getName(), uhcPlayers.getKills());
                }
            }
        }
        sorted_map.putAll(map);
        int i = 1;
        for (final String s : sorted_map.keySet()) {
            if (i > amount) {
                break;
            }
            sender.sendMessage("§e" + i + ") " + s + " §7- " + map.get(s) + " §7Kills");
            ++i;
        }
    }
}
