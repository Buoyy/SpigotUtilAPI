package com.github.buoyy.api.file;

import com.github.buoyy.api.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * A class representing a Spigot handled YAML file.
 * Not to be confused with the default config from JavaPlugin class.
 * Remember to generate a default config before this YAML to
 * ensure that the data folder is created correctly.
 */
@SuppressWarnings("unused")
public class YAML {
    private File file;
    private FileConfiguration config;
    private final Messenger messenger;

    /**
     * The constructor for this class.
     * @param pluginName - The plugin's name in whose data folder this
     *                   file will live.
     * @param fileName - The file's name, excluding the .yml extension.
     * @param messenger - The messenger object to use for logging. @see Messenger
     */
    public YAML(String pluginName, String fileName, Messenger messenger) {
        this.messenger = messenger;
        setup(pluginName, fileName);
    }

    /**
     * Returns the configuration related to this YAML.
     * Functions similarly to JavaPlugin#getConfig() .
     * @return - The configuration
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Saves the YAML file at its location.
     */
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            messenger.consoleBad(e.getMessage());
        }
    }

    /**
     * Reloads the YAML from disk. Useful when user
     * edits the file at server runtime.
     */
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


