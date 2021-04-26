package me.shir.uhcp.scoreboard;

import java.util.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.util.*;

public class ScoreManager
{
    private static ArrayList<UUID> badlion;
    private static ArrayList<UUID> plover;
    private static ArrayList<UUID> ultra;
    
    static {
        ScoreManager.badlion = new ArrayList<UUID>();
        ScoreManager.plover = new ArrayList<UUID>();
        ScoreManager.ultra = new ArrayList<UUID>();
    }
    
    public static void setLang(final Player p, final String score) {
        if (!score.equalsIgnoreCase("Badlion") || !score.equalsIgnoreCase("Plover") || !score.equalsIgnoreCase("Ultra")) {
            return;
        }
        if (score.equalsIgnoreCase("Badlion")) {
            ScoreManager.ultra.remove(p.getUniqueId());
            ScoreManager.plover.remove(p.getUniqueId());
            ScoreManager.badlion.add(p.getUniqueId());
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + ("§cYour Scoreboard is now Badlion"));
        }
        if (score.equalsIgnoreCase("Voltrix")) {
            ScoreManager.badlion.remove(p.getUniqueId());
            ScoreManager.ultra.remove(p.getUniqueId());
            ScoreManager.plover.add(p.getUniqueId());
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + ("§aYour Scoreboard is now Voltrix"));
        }
        if (score.equalsIgnoreCase("Ultra")) {
            ScoreManager.badlion.remove(p.getUniqueId());
            ScoreManager.plover.remove(p.getUniqueId());
            ScoreManager.ultra.add(p.getUniqueId());
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + ("§aYour Scoreboard is now Ultra"));
        }
    }
    
    public static boolean isbadlion(final Player p) {
        return ScoreManager.badlion.contains(p.getUniqueId());
    }
    
    public static boolean isplover(final Player p) {
        return ScoreManager.plover.contains(p.getUniqueId());
    }
    
    public static boolean isultra(final Player p) {
        return ScoreManager.ultra.contains(p.getUniqueId());
    }
    
    public static boolean removeplover(final Player p) {
        return ScoreManager.plover.remove(p.getUniqueId());
    }
    
    public static boolean removeultra(final Player p) {
        return ScoreManager.ultra.remove(p.getUniqueId());
    }
    
    public static boolean removebadlion(final Player p) {
        return ScoreManager.badlion.remove(p.getUniqueId());
    }
    
    public static boolean addplover(final Player p) {
        return ScoreManager.plover.add(p.getUniqueId());
    }
    
    public static boolean addultra(final Player p) {
        return ScoreManager.ultra.add(p.getUniqueId());
    }
    
    public static boolean addbadlion(final Player p) {
        return ScoreManager.badlion.add(p.getUniqueId());
    }
}
