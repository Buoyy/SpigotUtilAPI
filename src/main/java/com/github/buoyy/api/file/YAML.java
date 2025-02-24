package com.github.buoyy.api.file;

import com.github.buoyy.api.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class YAML {
    private File file;
    private FileConfiguration config;
    private final Messenger messenger;
    public YAML(String pluginName, String fileName, Messenger messenger) {
        this.messenger = messenger;
        setup(pluginName, fileName);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            messenger.consoleBad(e.getMessage());
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void setup(String pluginName, String fileName) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(pluginName)).getDataFolder(), fileName + ".yml");
        if (!file.exists()) {
            messenger.consoleOK("Data file not found. Creating new file...");
            try {
                if (file.createNewFile())
                    messenger.consoleGood(fileName + " file was successfully created.");
            } catch (IOException e) {
                messenger.consoleBad(e.getMessage());
            }
        } else {
            messenger.consoleGood(fileName + " file found! Loading...");
        }
        config = YamlConfiguration.loadConfiguration(file);
        messenger.consoleGood("Loaded data file!");
    }
}


