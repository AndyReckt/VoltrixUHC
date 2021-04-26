package me.shir.uhcp.cmds;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.command.*;
import me.shir.uhcp.lang.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.teams.*;
import com.nametagedit.plugin.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import me.shir.uhcp.*;
import org.bukkit.plugin.*;

public class FreezeCMD implements CommandExecutor
{
    public static ArrayList<UUID> freeze;
    public static HashMap<Player, ItemStack> helmet;
    
    static {
        FreezeCMD.freeze = new ArrayList<UUID>();
        FreezeCMD.helmet = new HashMap<Player, ItemStack>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("freeze")) {
            if (!sender.hasPermission("uhc.host")) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§cYou don't have permissions to execute this command!");
                }
                return true;
            }
            if (args.length < 1) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c/freeze <player>");
                }
                return true;
            }
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getErrorPrefix()) + "§c" + args[0] + " must be online!");
                }
                return true;
            }
            if (FreezeCMD.freeze.contains(target.getUniqueId())) {
                FreezeCMD.freeze.remove(target.getUniqueId());
                if (LangManager.isEnglish(p)) {
                    sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§b" + target.getName() + " has been unfrozen!");
                }
                if (LangManager.isEnglish(target)) {
                    target.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§bYou have been unfrozen!");
                }
                target.getInventory().setHelmet((ItemStack)FreezeCMD.helmet.get(target));
                if (TeamManager.getInstance().isTeamsEnabled()) {
                    NametagEdit.getApi().setNametag(target, TeamManager.getInstance().getTeam((OfflinePlayer)target).getTeamColor(), "");
                }
                else {
                    NametagEdit.getApi().setNametag(target, "§f", "");
                }
                return true;
            }
            FreezeCMD.freeze.add(target.getUniqueId());
            FreezeCMD.helmet.put(target, target.getInventory().getHelmet());
            target.getInventory().setHelmet(new ItemStack(Material.PACKED_ICE, 1, (short)14));
            if (LangManager.isEnglish(p)) {
                sender.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§b" + target.getName() + " has been frozen!");
            }
            if (LangManager.isEnglish(target)) {
                target.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§bYou are now frozen!");
            }
            this.message(target);
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player staff = onlinePlayers[i];
                if (staff.hasPermission("uhc.host") && LangManager.isEnglish(staff)) {
                    staff.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + "§b" + sender.getName() + " §ehas frozen §c" + target.getName());
                }
            }
            final String dm = "dm VoltrixPvP [VoltrixFreeze] [ALERT] " + sender.getName() + " has frozen " + target.getName();
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), dm);
            target.playSound(target.getLocation(), Sound.IRONGOLEM_HIT, 10.0f, 0.0f);
            new BukkitRunnable() {
                public void run() {
                    if (FreezeCMD.freeze.contains(target.getUniqueId())) {
                        FreezeCMD.this.message(target);
                        target.playSound(target.getLocation(), Sound.IRONGOLEM_HIT, 10.0f, 0.0f);
                    }
                    else {
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)VenixUHC.getInstance(), 600L, 20L);
        }
        return false;
    }
    
    public void message(final Player player) {
        if (LangManager.isEnglish(player)) {
            player.sendMessage("&f&c&6&0&6&c&f §4§lYou have been frozen".replace('&', '§'));
            player.sendMessage("&f&c&6&0&6&c&f §c§lDON'T LOG OUT!".replace('&', '§'));
            player.sendMessage("&f&c&6&0&6&c&f §e§lPlease join to teamspeak server!".replace('&', '§'));
        }
        player.sendMessage(String.valueOf("&f&c&6&c&f §b".replace('&', '§')) + GameManager.getGameManager().getTS().replace('&', '§'));
        player.sendMessage("&c&6&0&6&c".replace('&', '§'));
        player.sendMessage("&c ".replace('&', '§'));
        player.sendMessage(" ");
    }
}