package me.shir.uhcp.teams.request;

import me.shir.uhcp.teams.*;
import org.bukkit.entity.*;
import me.shir.uhcp.game.*;

public class Request
{
    private Team team;
    private Player recipient;
    
    public Request(final Team team, final Player recipient) {
        this.team = team;
        this.recipient = recipient;
    }
    
    void decline() {
        this.recipient.sendMessage(GameManager.getGameManager().getMainColor() + "You have denied the team invite!");
        this.team.getOwner().sendMessage("\u00c2§c" + this.recipient.getName() + " denied the team invite!");
    }
    
    public Team getTeam() {
        return this.team;
    }
}
