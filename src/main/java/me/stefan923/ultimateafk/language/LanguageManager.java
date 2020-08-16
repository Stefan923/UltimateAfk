package me.stefan923.ultimateafk.language;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LanguageManager {
    private static LanguageManager instance = new LanguageManager();
    private FileConfiguration config;
    private File cfile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(UltimateAfk instance) {
        cfile = new File(instance.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("UltimateAfk by Stefan923\n");
        config.addDefault("Command.Exit.Success", "&8(&3!&8) &fYou have typed &3/exit&f. You are &bNOT afk &fanymore!");
        config.addDefault("Command.Exit.Not Afk", "&8(&3!&8) &fYou are &bnot &fafk!");
        config.addDefault("Event.Move Event", "&8(&3!&8) &fYou have moved. You are &bNOT afk &fanymore!");
        config.addDefault("Event.Chat Event", "&8(&3!&8) &fYou have sent a message. You are &bNOT afk &fanymore!");
        config.addDefault("General.Afk Location Not Set", "&8(&3!&8) &cThe AFK location for &4UltimateAfk &cis not set!");
        config.addDefault("General.You Are Afk", "&8(&3!&8) &fYou are &bafk &fnow! Use &3/exit &f or &3move &fto return to spawn.");
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Command.Exit.Success", "&8(&3!&8) &fYou have typed &3/exit&f. You are &bNOT afk &fanymore!");
        config.set("Command.Exit.Not Afk", "&8(&3!&8) &fYou are &bnot &fafk!");
        config.set("Event.You Are Afk", "&8(&3!&8) &fYou are &bafk &fnow! Use &3/exit &f or &3move &fto return to spawn.");
        config.set("Event.Move Event", "&8(&3!&8) &fYou have moved. You are &bNOT afk &fanymore!");
        config.set("Event.Chat Event", "&8(&3!&8) &fYou have sent a message. You are &bNOT afk &fanymore!");
        config.set("General.Afk Location Not Set", "&8(&3!&8) &cThe AFK location for &4UltimateAfk &cis not set!");
        save();
    }

    private void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'language.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
