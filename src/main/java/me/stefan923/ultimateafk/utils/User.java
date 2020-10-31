package me.stefan923.ultimateafk.utils;

import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class User {

    private final Player player;

    private final long join;
    private long afk;

    private final String version;

    public User(Player player) {
        this.player = player;
        join = System.currentTimeMillis();
        afk = 0;

        String[] ver = ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(player.getUniqueId())).getName().split("\\.");
        version = ver[0] + "." + ver[1];
    }

    public Player getPlayer() {
        return player;
    }

    public long getJoin() {
        return join;
    }

    public long getAfk() {
        return afk;
    }

    public void addAfk(long afk) {
        this.afk += afk;
    }

    public String getVersion() {
        return version;
    }
}
