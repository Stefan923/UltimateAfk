package me.stefan923.ultimateafk.commands.type;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.commands.AbstractCommand;
import me.stefan923.ultimateafk.nms.Title;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandExit extends AbstractCommand implements MessageUtils {

    private Title title;
    public CommandExit() {
        super(null, true, "exit");
        title = new Title();
    }

    @Override
    protected ReturnType runCommand(UltimateAfk instance, CommandSender sender, String... args) {
        Player player = (Player) sender;
        String playerName = player.getName();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        if (instance.getAfkPlayers().containsKey(playerName)) {
            if (settings.getBoolean("Afk Settings.Use Titles") && instance.getAfkPlayers().containsKey(player.getName())) {
                title.send(player, "", "", 0, 1, 0);
            }
            if (settings.getBoolean("Afk Settings.Teleport Afk Players")) {
                player.teleport(instance.getAfkPlayers().get(playerName));
            }

            instance.getAfkPlayers().remove(playerName);
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
