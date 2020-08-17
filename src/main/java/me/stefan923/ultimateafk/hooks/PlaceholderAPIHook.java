package me.stefan923.ultimateafk.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.stefan923.ultimateafk.UltimateAfk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private UltimateAfk instance;

    public PlaceholderAPIHook(UltimateAfk instance) {
        this.instance = instance;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ultimateafk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Stefan923";
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        if (identifier.equalsIgnoreCase("isafk")) {
            return instance.getAfkPlayers().containsKey(player.getName()) ? language.getString("PlaceholderAPI Hook.Yes") : language.getString("PlaceholderAPI Hook.No");
        }
        return null;
    }
}
