package me.shir.uhcp.util;

import me.shir.uhcp.game.*;
import org.bukkit.configuration.file.*;
import me.shir.uhcp.*;
import java.sql.*;

public class SQL
{
    private Connection connection;
    private final GameManager gameManager;
    private final FileConfiguration config;
    public String table;
    
    public SQL() {
        this.gameManager = GameManager.getGameManager();
        this.config = VenixUHC.getInstance().getConfig();
        this.table = this.config.getString("stats.SQL.table");
    }
    
    public Connection getConnection() {
        if (this.connection == null) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.config.getString("stats.SQL.ip") + ":3306/" + this.config.getString("stats.SQL.database") + "?autoReconnect=true", this.config.getString("stats.SQL.user"), this.config.getString("stats.SQL.password"));
                System.out.println("[" + this.gameManager.getName() + "][SQL] Successfully re-connected to database!");
            }
            catch (SQLException e) {
                System.out.println("[" + this.gameManager.getName() + "][SQL] Re-connection to database failed!");
            }
        }
        return this.connection;
    }
    
    public synchronized void openConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.config.getString("stats.SQL.ip") + ":3306/" + this.config.getString("stats.SQL.database"), this.config.getString("stats.SQL.user"), this.config.getString("stats.SQL.password"));
            System.out.println("[" + this.gameManager.getName() + "][SQL] Successfully connected to database!");
        }
        catch (SQLException e) {
            System.out.println("[" + this.gameManager.getName() + "][SQL] Connection to database failed!");
            System.out.println("MAKE SURE THAT ALL DETAILS IN THE CONFIG.YML ARE CORRECT");
            this.gameManager.setStatsEnabled(false);
        }
    }
    
    public void closeConnection() {
        try {
            this.connection.close();
            System.out.println("[" + this.gameManager.getName() + "][SQL] Successfully disconnected from database!");
        }
        catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public void createTables() {
        try {
            final Statement statement1 = this.getConnection().createStatement();
            try {
                statement1.executeUpdate("CREATE TABLE IF NOT EXISTS `" + this.table + "`(`uuid` VARCHAR(60), `wins` INT NOT NULL DEFAULT '0', `kills` INT NOT NULL DEFAULT '0',`deaths` INT NOT NULL DEFAULT '0',`kd` DECIMAL(11,2) NOT NULL DEFAULT '0.0', " + "`higheststreak` INT NOT NULL DEFAULT '0', `arrowsshot` INT NOT NULL DEFAULT '0', `arrowshit` INT NOT NULL DEFAULT '0'," + "`goldenappleseaten` INT NOT NULL DEFAULT '0', `goldenheadseaten` INT NOT NULL DEFAULT '0', `heartshealed` INT NOT NULL DEFAULT '0'," + "`zombieskilled` INT NOT NULL DEFAULT '0', `creeperskilled` INT NOT NULL DEFAULT '0', `skeletonskilled` INT NOT NULL DEFAULT '0', `cavespiderskilled` INT NOT NULL DEFAULT '0'," + "`spiderskilled` INT NOT NULL DEFAULT '0', `blazeskilled` INT NOT NULL DEFAULT '0', `ghastskilled` INT NOT NULL DEFAULT '0', `cowskilled` INT NOT NULL DEFAULT '0'," + "`pigskilled` INT NOT NULL DEFAULT '0', `chickenskilled` INT NOT NULL DEFAULT '0', `horseskilled` INT NOT NULL DEFAULT '0', `witcheskilled` INT NOT NULL DEFAULT '0', `netherentrances` INT NOT NULL DEFAULT '0'," + "`horsestamed` INT NOT NULL DEFAULT '0', `xplevelsearned` INT NOT NULL DEFAULT '0', `diamondsmined` INT NOT NULL DEFAULT '0', `goldmined` INT NOT NULL DEFAULT '0', `redstonemined` INT NOT NULL DEFAULT '0'," + "`lapismined` INT NOT NULL DEFAULT '0', `ironmined` INT NOT NULL DEFAULT '0', `coalmined` INT NOT NULL DEFAULT '0', `quartzmined` INT NOT NULL DEFAULT '0', `spawnersmined` INT NOT NULL DEFAULT '0')");
            }
            catch (SQLException e) {
                System.out.print(e.getMessage());
            }
            finally {
                try {
                    if (statement1 != null) {
                        statement1.close();
                    }
                }
                catch (SQLException ex) {}
            }
            try {
                if (statement1 != null) {
                    statement1.close();
                }
            }
            catch (SQLException ex2) {}
        }
        catch (SQLException e2) {
            System.out.print(e2.getMessage());
        }
    }
}
