package me.stefan923.ultimateafk.storage;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MySQLDatabase extends Database {

    private String tablename;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public MySQLDatabase(String host, Integer port, String dbname, String tablename, String username, String password) throws SQLException {
        this.tablename = tablename;
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        connection = DriverManager.getConnection(url, username, password);
        initTable();
    }

    @Override
    public ResultSet get(String playerKey) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM %table WHERE `player_key` = ?;".replace("%table", tablename));
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
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT `%key` FROM %table WHERE `player_key` = ?;".replace("%table", tablename).replace("%key", key));
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
                PreparedStatement statement = getConnection().prepareStatement("DELETE FROM %table WHERE `player_key` = ?".replace("%table", tablename));
                statement.setString(1, playerKey);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void put(String playerKey, long join_time, long quit_time, long afk_time, String version) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO %table (`player_key`, `join_time`, `quit_time`, `afk_time`, `version`) VALUES (?, ?, ?, ?, ?)".replace("%table", tablename));
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
    public boolean has(String playerKey) {
        boolean result = false;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT `player_key` FROM %table WHERE `player_key` = ?".replace("%table", tablename));
            statement.setString(1, playerKey);
            ResultSet rs = statement.executeQuery();
            result = rs.next();
            rs.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear() {
        try {
            PreparedStatement statement = getConnection().prepareStatement("TRUNCATE TABLE %table".replace("%table", tablename));
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public Set<String> getKeys() {
        Set<String> tempset = new HashSet<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT `player_key` FROM %table".replace("%table", tablename));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tempset.add(rs.getString("id"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempset;
    }

    private void initTable() throws SQLException {
        String tablequery = "CREATE TABLE IF NOT EXISTS %table (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `player_key` VARCHAR(36), `join_time` BIGINT(18), `quit_time` BIGINT(18), `afk_time` BIGINT(18), `version` VARCHAR(10));".replace("%table", tablename);
        PreparedStatement preparedStatement = getConnection().prepareStatement(tablequery);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || !this.connection.isValid(5)) {
            this.connect();
        }
        return this.connection;
    }

}
