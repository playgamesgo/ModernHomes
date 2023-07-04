package com.playgamesgo.modernhomes;

import com.playgamesgo.modernhomes.Commands.*;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;

public final class ModernHomes extends JavaPlugin {
    private PluginManager pm = getServer().getPluginManager();
    public static YamlDocument config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        new DatabaseManager(this);

        getCommand("sethome").setExecutor(new setHomeCommand());
        getCommand("home").setExecutor(new homeCommand(this));
        getCommand("delhome").setExecutor(new delHomeCommand());
        getCommand("homes").setExecutor(new homesCommand());
        getCommand("homeedit").setExecutor(new homeEditCommand());
        getCommand("hometp").setExecutor(new homeTpCommand(this));
        getCommand("homeinvite").setExecutor(new homeInviteCommand());
        getCommand("homeignore").setExecutor(new homeIgnoreCommand());
        getCommand("homeaccept").setExecutor(new homeAcceptCommand(this));
        getCommand("homedeny").setExecutor(new homeDenyCommand());
        getCommand("homeadmin").setExecutor(new homeAdminCommand());
        getCommand("modernhomes").setExecutor(new modernHomesCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DatabaseManager.closeConnection();
    }
}
