package me.shir.uhcp.scoreboard;

import me.shir.uhcp.util.*;

public class ScoreFile
{
    public static void loadConfigs() {
        load("scoreboard.yml");
    }
    
    private static void load(final String file) {
        final FileUtil config = new FileUtil(file);
        config.createFile();
        config.loadFile();
    }
}
