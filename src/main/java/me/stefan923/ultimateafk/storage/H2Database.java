package me.stefan923.ultimateafk.storage;

import me.stefan923.ultimateafk.UltimateAfk;
import me.stefan923.ultimateafk.utils.MessageUtils;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class H2Database extends Database implements MessageUtils {

    private final String tablename;
    private final Connection connection;

    public H2Database(UltimateAfk ultimateAfk, String tablename) throws SQLException, ClassNotFoundException {
        this.tablename = tablename;

        Class.forName("org.h2.Driver");

        String url = "jdbc:h2:" + ultimateAfk.getDataFolder().getAbsolutePath() + File.separator + "database;mode=MySQL";
        connection = DriverManager.getConnection(url);
        if (connection == null)
            return;
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS %table (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `player_key` VARCHAR(36), `join_time` BIGINT(18), `quit_time` BIGINT(18), `afk_time` BIGINT(18), `version` VARCHAR(10));".replace("%table", tablename));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public ResultSet get(String playerKey) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM %table WHERE `player_key` = ?;".replace("%table", tablename));
            preparedStatement.setString(1, playerKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet get(String playerKey, String key) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `%key` FROM %table WHERE `player_key` = ?;".replace("%table", tablename).replace("%key", key));
            preparedStatement.setString(1, playerKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String playerKey) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM %table WHERE `player_key` = ?".replace("%table", tablename));
                preparedStatement.setString(1, playerKey);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void put(String playerKey, long join_time, long quit_time, long afk_time, String version) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO %table (`player_key`, `join_time`, `quit_time`, `afk_time`, `version`) VALUES (?, ?, ?, ?, ?)".replace("%table", tablename));
                preparedStatement.setString(1, playerKey);
                preparedStatement.setLong(2, join_time);
                preparedStatement.setLong(3, quit_time);
                preparedStatement.setLong(4, afk_time);
                preparedStatement.setString(5, version);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean has(String key) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `player_key` FROM %table WHERE `player_key` = ?;".replace("%table", tablename));
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear() {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM %table;".replace("%table", tablename));
                preparedStatement.executeQuery();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public PreparedStatement prepareStatement(String query) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    public Set<String> getKeys() {
        Set<String> tempSet = new HashSet<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `player_key` FROM %table;".replace("%table", tablename));
            ResultSet result = preparedStatement.executeQuery();
            while (result.next())
                tempSet.add(result.getString("player_key"));
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempSet;
    }


}
