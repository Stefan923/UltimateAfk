package me.stefan923.ultimateafk.commands.type;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.commands.AbstractCommand;
import me.stefan923.ultimateafk.exceptions.MissingPermissionException;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAfk extends AbstractCommand implements MessageUtils {
    public CommandAfk() {
        super(true, false, "afk");
    }

    @Override
    protected ReturnType runCommand(UltimateAfk instance, CommandSender sender, String... args) throws MissingPermissionException {
        Player player = (Player) sender;
        String playerName = player.getName();

        if (instance.getAfkPlayers().containsKey(playerName)) {
            player.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.Afk.Already Afk")));
            return ReturnType.SUCCESS;
        }

        int cooldown = instance.getSettingsManager().getConfig().getInt("Afk Settings.Afk Command Cooldown");
        if (!instance.getCooldowns().containsKey(playerName) || (System.currentTimeMillis() - instance.getCooldowns().get(playerName)) / 1000 > cooldown) {
            instance.setAfk(player);
            return ReturnType.SUCCESS;
        }

        player.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Command.Afk.Cooldown").replace("%time%", String.valueOf(cooldown))));
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(UltimateAfk instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "ultimateafk.afk";
    }

    @Override
    public String getSyntax() {
        return "/afk";
    }

    @Override
    public String getDescription() {
        return "Changes own status to AFK.";
    }
}
