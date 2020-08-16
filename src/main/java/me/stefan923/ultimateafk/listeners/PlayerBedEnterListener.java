package me.stefan923.ultimateafk.listeners;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnterListener implements Listener, MessageUtils {

    private final UltimateAfk instance;

    public PlayerBedEnterListener(UltimateAfk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (instance.isAfk(player.getName())) {
            player.sendMessage(formatAll(instance.getLanguageManager().getConfig().getString("Event.Bed Enter Event")));
        }
        instance.getAfkPlayers().replace(player.getName(), System.currentTimeMillis());
    }

}
