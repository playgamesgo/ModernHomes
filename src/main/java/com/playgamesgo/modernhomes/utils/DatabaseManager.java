package com.playgamesgo.modernhomes.utils;

import com.playgamesgo.modernhomes.ModernHomes;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {
    YamlDocument config = ModernHomes.config;
    Plugin plugin;
    public static Connection connection;

    public DatabaseManager(Plugin plugin) {
        this.plugin = plugin;
        initDB();
    }

    private void initDB() {
        if (config.getBoolean("use-mysql")) {
            connection = getMySQLConnection();
        } else {
            connection = getSQLiteConnection();
        }
        
        if (connection == null) {
            plugin.getLogger().log(Level.SEVERE, "Could not establish a connection to the database.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS homes (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(36), name VARCHAR(16), x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, pitch FLOAT, world VARCHAR(32), public BOOLEAN DEFAULT FALSE)");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not create the homes table.", e);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private Connection getSQLiteConnection() {
        File dataFile = new File(plugin.getDataFolder(), "homes.db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: homes.db");
            }
        }
        
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + dataFile);
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", e);
        }
        return null;
    }
    
    private Connection getMySQLConnection() {
        String host = config.getString("mysql.host");
        String port = config.getString("mysql.port");
        String database = config.getString("mysql.database");
        String username = config.getString("mysql.username");
        String password = config.getString("mysql.password");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "MySQL exception on initialize", e);
        }
        return null;
    }

    public static void closeConnection() {
        try {
            DatabaseManager.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
