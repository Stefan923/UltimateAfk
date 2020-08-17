package me.stefan923.ultimateafk.commands.type;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.commands.AbstractCommand;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandUltimateAfk extends AbstractCommand implements MessageUtils {

    public CommandUltimateAfk() {
        super(null, false, "ultimateafk");
    }

    @Override
    protected ReturnType runCommand(UltimateAfk instance, CommandSender sender, String... args) {
        sender.sendMessage(formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sendCenteredMessage(sender, formatAll("&3&lUltimateAfk &f&lv" + instance.getDescription().getVersion()));
        sendCenteredMessage(sender, formatAll("&8&l» &fPlugin author: &bStefan923"));
        sendCenteredMessage(sender, formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&l» &fAdds the afk status with customisable settings."));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sender.sendMessage(formatAll(" "));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(UltimateAfk instance, CommandSender sender, String... args) {
        if (sender.hasPermission("ultimateafk.admin"))
            return Arrays.asList("reload", "setLocation");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/ultimateafk";
    }

    @Override
    public String getDescription() {
        return "Displays plugin info";
    }

}
