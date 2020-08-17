package me.stefan923.ultimateafk.language;

import me.stefan923.ultimateafk.UltimateAfk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
        cfile = new File(instance.getDataFolder(), "language.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("UltimateAfk by Stefan923\n");
        config.addDefault("Command.Exit.Not Afk", "&8(&3!&8) &fYou are &bnot &fafk!");
        config.addDefault("Command.Exit.Success", "&8(&3!&8) &fYou have typed &3/exit&f. You are &bNOT afk &fanymore!");
        config.addDefault("Command.SetLocation.Success", "&8(&3!&8) &fYou have set a &bnew location &fwhere to teleport afk players.");
        config.addDefault("Command.SetLocation.Not Enabled", "&8(&3!&8) &fTeleporting afk players is &bnot enabled &fin &3settings.yml&f.");
        config.addDefault("General.Afk Location Not Set", "&8(&3!&8) &cThe AFK location for &4UltimateAfk &cis not set!");
        config.addDefault("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.addDefault("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.addDefault("General.You Are Afk", "&8(&3!&8) &fYou are &bafk &fnow! Use &3/exit &f or &3move &fto return to spawn.");
        config.addDefault("General.You Are Not Afk", "&8(&3!&8) &fYou did an action, so you are &bno longer afk&f!");
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Command.Exit.Not Afk", "&8(&3!&8) &fYou are &bnot &fafk!");
        config.set("Command.Exit.Success", "&8(&3!&8) &fYou have typed &3/exit&f. You are &bNOT afk &fanymore!");
        config.set("Command.SetLocation.Success", "&8(&3!&8) &fYou have set a &bnew location &fwhere to teleport afk players.");
        config.set("Command.SetLocation.Not Enabled", "&8(&3!&8) &fTeleporting afk players is &bnot enabled &fin &3settings.yml&f.");
        config.set("General.Afk Location Not Set", "&8(&3!&8) &cThe AFK location for &4UltimateAfk &cis not set!");
        config.set("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.set("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.set("General.You Are Afk", "&8(&3!&8) &fYou are &bafk &fnow! Use &3/exit &f or &3move &fto return to spawn.");
        config.set("General.You Are Not Afk", "&8(&3!&8) &fYou did an action, so you are &bno longer afk&f!");
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
