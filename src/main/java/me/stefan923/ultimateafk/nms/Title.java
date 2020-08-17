package me.stefan923.ultimateafk.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class Title extends Reflection {

    public void send(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            Object chatSubTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Constructor<?> subTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object subPacket = subTitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatSubTitle,
                    fadeInTime, showTime, fadeOutTime);

            Constructor<?> timingTitleConstructor = getNMSClass("PacketPlayOutTitle")
                    .getConstructor(int.class, int.class, int.class);
            Object timingPacket = timingTitleConstructor.newInstance(fadeInTime, showTime, fadeOutTime);

            sendPacket(player, timingPacket);
            sendPacket(player, packet);
            sendPacket(player, subPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
