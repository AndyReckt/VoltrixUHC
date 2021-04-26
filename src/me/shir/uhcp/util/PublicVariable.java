package me.shir.uhcp.util;

import org.bukkit.entity.*;
import java.util.*;

public class PublicVariable
{
    public static HashMap<Player, Integer> playerKills;
    public static List<Player> invincible;
    public static HashMap<Player, Integer> playerVoted;
    
    static {
        PublicVariable.playerKills = new HashMap<Player, Integer>();
        PublicVariable.invincible = new ArrayList<Player>();
        PublicVariable.playerVoted = new HashMap<Player, Integer>();
    }
}
