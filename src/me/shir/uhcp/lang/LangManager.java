package me.shir.uhcp.lang;

import java.util.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;
import me.shir.uhcp.util.*;

public class LangManager
{
    private static ArrayList<UUID> english;
    private static ArrayList<UUID> espanol;
    
    static {
        LangManager.english = new ArrayList<UUID>();
        LangManager.espanol = new ArrayList<UUID>();
    }
    
    public static void setLang(final Player p, final String lang) {
        if (!lang.equalsIgnoreCase("english") || !lang.equalsIgnoreCase("espanol")) {
            return;
        }
        if (lang.equalsIgnoreCase("english")) {
            LangManager.espanol.remove(p.getUniqueId());
            LangManager.english.add(p.getUniqueId());
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + Color.translate("&aYour language is now english"));
        }
        if (lang.equalsIgnoreCase("espanol")) {
            LangManager.english.remove(p.getUniqueId());
            LangManager.espanol.add(p.getUniqueId());
            p.sendMessage(String.valueOf(GameManager.getGameManager().getPrefix()) + Color.translate("&aTu idioma es ahora espa\u00f1ol"));
        }
    }
    
    public static boolean isEnglish(final Player p) {
        return LangManager.english.contains(p.getUniqueId());
    }
    
    public static boolean esEspanol(final Player p) {
        return LangManager.espanol.contains(p.getUniqueId());
    }
    
    public static boolean removeEspanol(final Player p) {
        return LangManager.espanol.remove(p.getUniqueId());
    }
    
    public static boolean removeEnglish(final Player p) {
        return LangManager.english.remove(p.getUniqueId());
    }
    
    public static boolean addEspanol(final Player p) {
        return LangManager.espanol.add(p.getUniqueId());
    }
    
    public static boolean addEnglish(final Player p) {
        return LangManager.english.add(p.getUniqueId());
    }
}
