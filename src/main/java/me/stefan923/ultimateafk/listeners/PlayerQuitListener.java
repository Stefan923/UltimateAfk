package me.stefan923.ultimateafk.listeners;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;
import me.stefan923.ultimateafk.utils.User;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        String playerName = player.getName();
        instance.getAfkPlayers().remove(playerName);
        instance.getPlayers().remove(playerName);
        instance.getCooldowns().remove(playerName);

        User user = instance.getUser(player);
        instance.getDatabase("ultimateafk_logs").put(playerName, user.getJoin(), System.currentTimeMillis(), user.getAfk(), user.getVersion());

        instance.removeUser(player);
    }

}
