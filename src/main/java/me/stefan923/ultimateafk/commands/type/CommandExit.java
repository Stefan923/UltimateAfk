package me.stefan923.ultimateafk.commands.type;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.commands.AbstractCommand;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandExit extends AbstractCommand implements MessageUtils {

    public CommandExit() {
        super(null, true, "exit");
    }

    @Override
    protected ReturnType runCommand(UltimateAfk instance, CommandSender sender, String... args) {
        if (instance.isAfk(sender.getName())) {
            instance.getAfkPlayers().remove(sender.getName());
            sender.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.Exit.Success")));
            return ReturnType.SUCCESS;
        }

        sender.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.Exit.Not Afk")));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(UltimateAfk instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/exit";
    }

    @Override
    public String getDescription() {
        return "Exits afk status.";
    }

}
