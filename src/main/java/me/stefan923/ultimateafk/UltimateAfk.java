package me.stefan923.ultimateafk;

import me.stefan923.ultimateafk.commands.CommandManager;
import me.stefan923.ultimateafk.hooks.PlaceholderAPIHook;
import me.stefan923.ultimateafk.language.LanguageManager;
import me.stefan923.ultimateafk.listeners.*;
import me.stefan923.ultimateafk.nms.Title;
import me.stefan923.ultimateafk.settings.SettingsManager;
import me.stefan923.ultimateafk.storage.Database;
import me.stefan923.ultimateafk.storage.H2Database;
import me.stefan923.ultimateafk.storage.MySQLDatabase;
import me.stefan923.ultimateafk.utils.LocationUtils;
import me.stefan923.ultimateafk.utils.MessageUtils;
import me.stefan923.ultimateafk.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public class UltimateAfk extends JavaPlugin implements MessageUtils, LocationUtils {
    private static UltimateAfk instance;

    private SettingsManager settingsManager;
    private LanguageManager languageManager;

    private FileConfiguration settings;
    private Database database;

    private HashMap<String, Long> players;
    private HashMap<String, Location> afkPlayers;
    private HashMap<String, Long> cooldowns;

    private HashMap<Player, User> users;

    private Title title;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = SettingsManager.getInstance();
        settingsManager.setup(this);
        settings = settingsManager.getConfig();

        languageManager = LanguageManager.getInstance();
        languageManager.setup(this);

        players = new HashMap<>();
        afkPlayers = new HashMap<>();
        cooldowns = new HashMap<>();

        users = new HashMap<>();

        title = new Title();

        sendLogger("&8&l> &7&m------- &8&l( &3&lUltimateAfk &b&lby Stefan923 &8&l) &7&m------- &8&l<");
        sendLogger("&b   Plugin has been initialized.");
        sendLogger("&b   Version: &3v" + getDescription().getVersion());
        sendLogger("&b   Enabled listeners: &3" + enableListeners());
        sendLogger("&b   Enabled commands: &3" + enableCommands());
        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIHook(instance).register();
            sendLogger("&b   Placeholders: &aEnabled");
        } else {
            sendLogger("&b   Placeholders: &aDisabled");
        }
        sendLogger("&8&l> &7&m------- &8&l( &3&lUltimateAfk &b&lby Stefan923 &8&l) &7&m------- &8&l<");

        getDatabase("ultimateafk_logs");
        afkCheck();
    }

    private Integer enableListeners() {
        Integer i = 3;
        FileConfiguration settings = settingsManager.getConfig();
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerKickListener(this), this);
        if (settings.getBoolean("Afk Settings.Disable On Bed Enter")) {
            pluginManager.registerEvents(new PlayerBedEnterListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Bucket Empty")) {
            pluginManager.registerEvents(new PlayerBucketEmptyListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Bucket Fill")) {
            pluginManager.registerEvents(new PlayerBucketFillListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Changed World")) {
            pluginManager.registerEvents(new PlayerChangedWorldListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Chat")) {
            pluginManager.registerEvents(new PlayerChatListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Damage")) {
            pluginManager.registerEvents(new PlayerDamageListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Drop Item")) {
            pluginManager.registerEvents(new PlayerDropItemListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Edit Book")) {
            pluginManager.registerEvents(new PlayerEditBookListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Exp Change")) {
            pluginManager.registerEvents(new PlayerExpChangeListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Interact Entity")) {
            pluginManager.registerEvents(new PlayerInteractEntityListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Interact")) {
            pluginManager.registerEvents(new PlayerInteractListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Inventory Open")) {
            pluginManager.registerEvents(new PlayerInventoryOpenListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Item Break")) {
            pluginManager.registerEvents(new PlayerItemBreakListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Move")) {
            pluginManager.registerEvents(new PlayerMoveListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Pickup Item")) {
            pluginManager.registerEvents(new PlayerPickupItemListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Shear Entity")) {
            pluginManager.registerEvents(new PlayerShearEntityListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Toggle Flight")) {
            pluginManager.registerEvents(new PlayerToggleFlightListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Toggle Sneak")) {
            pluginManager.registerEvents(new PlayerToggleSneakListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Toggle Sprint")) {
            pluginManager.registerEvents(new PlayerToggleSprintListener(this), this);
            ++i;
        }
        if (settings.getBoolean("Afk Settings.Disable On Unleash Entity")) {
            pluginManager.registerEvents(new PlayerUnleashEntityListener(this), this);
            ++i;
        }
        return i;
    }

    private Integer enableCommands() {
        CommandManager commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    private void afkCheck() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(instance, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (settings.getBoolean("Afk Settings.Use Titles") && afkPlayers.containsKey(player.getName())) {
                    title.send(player, formatAll(languageManager.getConfig().getString("Titles.Afk.Title")), formatAll(languageManager.getConfig().getString("Titles.Afk.Subtitle")), 5, 600, 10);
                }
                if (isAfk(player.getName()) && !afkPlayers.containsKey(player.getName())) {
                    setAfk(player);
                }
            }
        }, 600, 600);
    }

    public void setAfk(Player player) {
        afkPlayers.put(player.getName(), player.getLocation());

        if (settings.getBoolean("Afk Settings.Teleport Afk Players")) {
            if (settings.isSet("Afk Settings.Teleport Location")) {
                player.teleport(deserializeLocation(settings.getString("Afk Settings.Teleport Location")));
            } else {
                sendLogger(formatAll(languageManager.getConfig().getString("General.Afk Location Not Set")));
            }
        }
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                onlinePlayer.hidePlayer(player);
                if (settings.getBoolean("Afk Settings.Announce Afk Players")) {
                    onlinePlayer.sendMessage(formatAll(languageManager.getConfig().getString("General.Is Afk").replace("%player_name%", player.getName())));
                }
            }
        });

        title.send(player, formatAll(languageManager.getConfig().getString("Titles.Afk.Title")), formatAll(languageManager.getConfig().getString("Titles.Afk.Subtitle")), 5, 600, 10);
        player.sendMessage(formatAll(languageManager.getConfig().getString("General.You Are Afk")));
    }

    public void setNotAfk(Player player) {
        String playerName = player.getName();

        getUser(player).addAfk(players.get(playerName));

        if (settings.getBoolean("Afk Settings.Teleport Afk Players")) {
            player.teleport(afkPlayers.get(playerName));
        }

        if (settings.getBoolean("Afk Settings.Use Titles") && afkPlayers.containsKey(playerName)) {
            title.send(player, "", "", 0, 1, 0);
        }

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                onlinePlayer.showPlayer(player);
                if (settings.getBoolean("Afk Settings.Announce Afk Players")) {
                    onlinePlayer.sendMessage(formatAll(languageManager.getConfig().getString("General.Is Not Afk").replace("%player_name%", playerName)));
                }
            }
        });
        player.sendMessage(formatAll(languageManager.getConfig().getString("General.You Are Not Afk")));
        afkPlayers.remove(playerName);
    }

    public static UltimateAfk getInstance() {
        return instance;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void reloadSettingManager() {
        settingsManager.reload();
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public void reloadLanguageManager() {
        languageManager.reload();
    }

    public User getUser(Player player) {
        if (users.containsKey(player)) {
            return users.get(player);
        }
        User user = new User(player);
        users.put(player, user);
        return user;
    }

    public void removeUser(Player player) {
        users.remove(player);
    }

    public Database getDatabase(String table) {
        if (database != null)
            return database;
        if (settingsManager.getConfig().getBoolean("Storage.MySQL.Enable"))
            return getMySQLDatabase(table);
        else
            return getFileDatabase(table);
    }

    protected Database getMySQLDatabase(String table) {
        ConfigurationSection section = settingsManager.getConfig().getConfigurationSection("Storage");
        String address = section.getString("MySQL.IP Adress", "localhost");
        Integer port = section.getInt("MySQL.Port");
        String password = section.getString("MySQL.Password");
        String name = section.getString("MySQL.Database Name");
        String user = section.getString("MySQL.User");
        Database lDatabase = null;
        try {
            lDatabase = new MySQLDatabase(address, port, name, table, user, password);
            sendLogger("&8(&3UltimateAfk&8) &rMySQL connection " + address + " was a success!");
            database = lDatabase;
            return database;
        } catch (SQLException exception) {
            sendLogger("&8(&3UltimateAfk&8) &cMySQL connection failed!");
            sendLogger("&8(&3UltimateAfk&8) &rAddress: " + address + " with user: " + user);
            sendLogger("&8(&3UltimateAfk&8) &rReason: " + exception.getMessage());
        } finally {
            if (database == null) {
                sendLogger("&8(&3UltimateAfk&8) &rAttempting to use H2 database instead...");
                database = getFileDatabase(table);
            }
        }
        return database;
    }

    private Database getFileDatabase(String table) {
        Database lDatabase = null;
        try {
            lDatabase = new H2Database(instance, table);
            sendLogger("&8(&3UltimateAfk&8) &rUsing H2 database for &b" + table + " &7data.");
            database = lDatabase;
        } catch (ClassNotFoundException | SQLException e) {
            sendLogger("&8(&3UltimateAfk&8) &cH2 failed...");
            e.printStackTrace();
        }
        return database;
    }

    public HashMap<String, Location> getAfkPlayers() {
        return afkPlayers;
    }

    public HashMap<String, Long> getPlayers() {
        return players;
    }

    public HashMap<String, Long> getCooldowns() {
        return cooldowns;
    }

    public boolean isAfk(String playerName) {
        return (System.currentTimeMillis() - players.get(playerName)) / 1000 > settings.getInt("Afk Settings.Idle Time Until Afk");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
