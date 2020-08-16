package me.stefan923.ultimateafk.listeners;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener, MessageUtils {

    private final UltimateAfk instance;

    public PlayerQuitListener(UltimateAfk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        instance.getAfkPlayers().remove(playerName);
        instance.getPlayers().remove(playerName);
    }

}
