package me.stefan923.ultimateafk.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

public abstract class Database {

    public abstract void put(String playerKey, long join_time, long quit_time, long afk_time, String version);

    public abstract boolean has(String key);

    public abstract Set<String> getKeys();

    public abstract ResultSet get(String key);

    public abstract ResultSet get(String playerKey, String key);

    public abstract void delete(String playerKey);

    public abstract PreparedStatement prepareStatement(String query);

    public abstract void clear();

}
