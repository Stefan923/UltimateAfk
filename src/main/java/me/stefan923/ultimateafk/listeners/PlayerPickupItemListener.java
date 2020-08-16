package me.stefan923.ultimateafk.listeners;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener, MessageUtils {

    private final UltimateAfk instance;

    public PlayerPickupItemListener(UltimateAfk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (instance.getAfkPlayers().containsKey(player.getName())) {
            instance.setNotAfk(player);
        }
        instance.getPlayers().replace(player.getName(), System.currentTimeMillis());
    }

}
