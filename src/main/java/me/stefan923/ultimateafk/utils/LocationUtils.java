package me.stefan923.ultimateafk.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public interface LocationUtils {

    default String serializeLocation(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    default Location deserializeLocation(String string) {
        if (string.equals("") || string.trim().equals("")) {
            return null;
        }

        final String[] parts = string.split(":");
        if (parts.length == 6) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }

        return null;
    }

}
