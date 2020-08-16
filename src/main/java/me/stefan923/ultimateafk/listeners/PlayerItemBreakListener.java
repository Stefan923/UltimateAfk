package me.stefan923.ultimateafk.listeners;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

public class PlayerItemBreakListener implements Listener, MessageUtils {

    private final UltimateAfk instance;

    public PlayerItemBreakListener(UltimateAfk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        if (instance.isAfk(player.getName())) {
            player.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Event.Item Break Event")));
        }
        instance.getAfkPlayers().replace(player.getName(), System.currentTimeMillis());
    }

}
