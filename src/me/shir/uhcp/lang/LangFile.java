package me.shir.uhcp.lang;

import me.shir.uhcp.util.*;

public class LangFile
{
    public static void loadConfigs() {
        load("lang.yml");
    }
    
    private static void load(final String file) {
        final FileUtil config = new FileUtil(file);
        config.createFile();
        config.loadFile();
    }
}
