package me.stefan923.ultimateafk.settings;

import me.stefan923.ultimateafk.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private static SettingsManager instance = new SettingsManager();
    private FileConfiguration config;
    private File cfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("UltimateAfk by Stefan923\n");
        config.addDefault("Enabled Commands.Afk", true);
        config.addDefault("Enabled Commands.Exit", true);
        config.addDefault("Afk Settings.Afk Command Cooldown", 120);
        config.addDefault("Afk Settings.Announce Afk Players", true);
        config.addDefault("Afk Settings.Idle Time Until Afk", 300);
        config.addDefault("Afk Settings.Teleport Afk Players", true);
        config.addDefault("Afk Settings.Use Titles", true);
        config.addDefault("Afk Settings.Disable On Bed Enter", true);
        config.addDefault("Afk Settings.Disable On Bucket Empty", true);
        config.addDefault("Afk Settings.Disable On Bucket Fill", true);
        config.addDefault("Afk Settings.Disable On Changed World", true);
        config.addDefault("Afk Settings.Disable On Chat", true);
        config.addDefault("Afk Settings.Disable On Damage", true);
        config.addDefault("Afk Settings.Disable On Drop Item", true);
        config.addDefault("Afk Settings.Disable On Edit Book", true);
        config.addDefault("Afk Settings.Disable On Exp Change", true);
        config.addDefault("Afk Settings.Disable On Interact Entity", true);
        config.addDefault("Afk Settings.Disable On Interact", true);
        config.addDefault("Afk Settings.Disable On Inventory Open", true);
        config.addDefault("Afk Settings.Disable On Item Break", true);
        config.addDefault("Afk Settings.Disable On Move", true);
        config.addDefault("Afk Settings.Disable On Pickup Item", true);
        config.addDefault("Afk Settings.Disable On Shear Entity", true);
        config.addDefault("Afk Settings.Disable On Toggle Flight", true);
        config.addDefault("Afk Settings.Disable On Toggle Sneak", true);
        config.addDefault("Afk Settings.Disable On Toggle Sprint", true);
        config.addDefault("Afk Settings.Disable On Unleash Entity", true);
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Enabled Commands.Afk", true);
        config.set("Enabled Commands.Exit", true);
        config.set("Afk Settings.Idle Time Until Afk", 300);
        config.set("Afk Settings.Teleport Afk Players", true);
        config.set("Afk Settings.Afk Command Cooldown", 120);
        config.set("Afk Settings.Disable On Bed Enter", true);
        config.set("Afk Settings.Disable On Bucket Empty", true);
        config.set("Afk Settings.Disable On Bucket Fill", true);
        config.set("Afk Settings.Disable On Changed World", true);
        config.set("Afk Settings.Disable On Chat", true);
        config.set("Afk Settings.Disable On Damage", true);
        config.set("Afk Settings.Disable On Drop Item", true);
        config.set("Afk Settings.Disable On Edit Book", true);
        config.set("Afk Settings.Disable On Exp Change", true);
        config.set("Afk Settings.Disable On Interact Entity", true);
        config.set("Afk Settings.Disable On Interact", true);
        config.set("Afk Settings.Disable On Inventory Open", true);
        config.set("Afk Settings.Disable On Item Break", true);
        config.set("Afk Settings.Disable On Move", true);
        config.set("Afk Settings.Disable On Pickup Item", true);
        config.set("Afk Settings.Disable On Shear Entity", true);
        config.set("Afk Settings.Disable On Toggle Flight", true);
        config.set("Afk Settings.Disable On Toggle Sneak", true);
        config.set("Afk Settings.Disable On Toggle Sprint", true);
        config.set("Afk Settings.Disable On Unleash Entity", true);
        save();
    }

    public void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'settings.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
