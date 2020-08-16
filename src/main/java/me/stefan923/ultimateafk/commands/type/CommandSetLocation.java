package me.stefan923.ultimateafk.commands.type;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.commands.AbstractCommand;
import me.stefan923.ultimateafk.utils.LocationUtils;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandSetLocation extends AbstractCommand implements MessageUtils, LocationUtils {

    public CommandSetLocation(AbstractCommand abstractCommand) {
        super(abstractCommand, true, "setlocation");
    }

    @Override
    protected ReturnType runCommand(UltimateAfk instance, CommandSender sender, String... args) {
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        if (settings.getBoolean("Afk Settings.Teleport Afk Players")) {
            settings.set("Afk Settings.Teleport Location", serializeLocation(((Player) sender).getLocation()));
            instance.getSettingsManager().save();

            sender.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.SetLocation.Success")));
            return ReturnType.SUCCESS;
        }

        sender.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.SetLocation.Not Enabled")));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(UltimateAfk instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "ultimateafk.admin";
    }

    @Override
    public String getSyntax() {
        return "/ultimateafk setlocation";
    }

    @Override
    public String getDescription() {
        return "Sets the location for AFK players.";
    }

}
