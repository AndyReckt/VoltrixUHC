package me.shir.uhcp.util;

import me.shir.uhcp.*;
import org.bukkit.configuration.file.*;
import java.io.*;
import org.bukkit.configuration.*;
import java.util.*;
import org.bukkit.*;

public class FileUtil
{
    private File file;
    private FileConfiguration config;
    private String folder;
    private String fileName;
    private boolean isFolder;
    private VenixUHC Core;
    
    public FileUtil(final String folder, final String fileName) {
        this.file = null;
        this.folder = folder;
        this.fileName = fileName;
        this.isFolder = true;
        if (this.file == null) {
            final StringBuilder sb = new StringBuilder();
            final VenixUHC core = this.Core;
            this.file = new File(sb.append(VenixUHC.getInstance().getDataFolder()).append("/").append(folder).toString(), fileName);
        }
        if (!this.file.exists()) {
            final VenixUHC core2 = this.Core;
            VenixUHC.getInstance().saveResource(String.valueOf(folder) + "/" + fileName, false);
            final StringBuilder sb2 = new StringBuilder();
            final VenixUHC core3 = this.Core;
            this.file = new File(sb2.append(VenixUHC.getInstance().getDataFolder()).append("/").append(folder).toString(), fileName);
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
    
    public FileUtil(final String fileName) {
        this.file = null;
        this.isFolder = false;
        this.fileName = fileName;
        if (this.file == null) {
            final VenixUHC core = this.Core;
            this.file = new File(VenixUHC.getInstance().getDataFolder(), fileName);
        }
        if (!this.file.exists()) {
            final VenixUHC core2 = this.Core;
            VenixUHC.getInstance().saveResource(fileName, false);
            final VenixUHC core3 = this.Core;
            this.file = new File(VenixUHC.getInstance().getDataFolder(), fileName);
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
    
    public boolean loadFile() {
        try {
            this.config.load(this.file);
            return true;
        }
        catch (FileNotFoundException e) {
            this.createFile();
            return true;
        }
        catch (IOException e2) {
            this.createFile();
            return false;
        }
        catch (InvalidConfigurationException e3) {
            this.createFile();
            return false;
        }
    }
    
    public boolean createFile() {
        if (this.isFolder) {
            if (this.file == null) {
                final StringBuilder sb = new StringBuilder();
                final VenixUHC core = this.Core;
                this.file = new File(sb.append(VenixUHC.getInstance().getDataFolder()).append("/").append(this.folder).toString(), this.fileName);
            }
            if (!this.file.exists()) {
                final VenixUHC core2 = this.Core;
                VenixUHC.getInstance().saveResource(String.valueOf(this.folder) + "/" + this.fileName, false);
                final StringBuilder sb2 = new StringBuilder();
                final VenixUHC core3 = this.Core;
                this.file = new File(sb2.append(VenixUHC.getInstance().getDataFolder()).append("/").append(this.folder).toString(), this.fileName);
            }
        }
        else {
            if (this.file == null) {
                final VenixUHC core4 = this.Core;
                this.file = new File(VenixUHC.getInstance().getDataFolder(), this.fileName);
            }
            if (!this.file.exists()) {
                final VenixUHC core5 = this.Core;
                VenixUHC.getInstance().saveResource(this.fileName, false);
                final VenixUHC core6 = this.Core;
                this.file = new File(VenixUHC.getInstance().getDataFolder(), this.fileName);
            }
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
        return true;
    }
    
    public boolean exist() {
        return this.file.exists();
    }
    
    public boolean saveFile() {
        try {
            this.config.save(this.file);
            return true;
        }
        catch (IOException e) {
            System.out.println("Something wrong went saving this file: " + this.file.getName());
            return false;
        }
    }
    
    public void deleteFile() {
        this.file.delete();
    }
    
    public void set(final String path, final Object value) {
        this.config.set(path, value);
    }
    
    public int getInt(final String path) {
        return this.config.getInt(path);
    }
    
    public String getString(final String path) {
        return this.config.getString(path);
    }
    
    public long getLong(final String path) {
        return this.config.getLong(path);
    }
    
    public boolean contains(final String path) {
        return this.config.contains(path);
    }
    
    public boolean getBoolean(final String path) {
        return this.config.getBoolean(path);
    }
    
    public boolean isSet(final String path) {
        return this.config.isSet(path);
    }
    
    public ConfigurationSection getConfigurationSection(final String path) {
        return this.config.getConfigurationSection(path);
    }
    
    public List<String> getStringList(final String path) {
        return (List<String>)this.config.getStringList(path);
    }
    
    public List<Integer> getIntList(final String path) {
        return (List<Integer>)this.config.getIntegerList(path);
    }
    
    public float getFloat(final String path) {
        return (float)this.getDouble(path);
    }
    
    public List<?> getList(final String path) {
        return (List<?>)this.config.getList(path);
    }
    
    public double getDouble(final String path) {
        return this.config.getDouble(path);
    }
    
    public short getShort(final String path) {
        return (short)this.config.getInt(path);
    }
    
    public String getStringColor(final String path) {
        return ChatColor.translateAlternateColorCodes('&', this.getString(path));
    }
    
    public FileConfiguration getConfig() {
        return (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
}
