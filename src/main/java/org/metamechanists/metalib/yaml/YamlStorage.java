package org.metamechanists.metalib.yaml;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class YamlStorage {
    private static final long savePeriod = 18000L;

    private final Plugin plugin;
    private final File file;
    private final YamlConfiguration config;

    public YamlStorage(Plugin plugin, File file, boolean savePeriodically) {
        this.plugin = plugin;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        if (savePeriodically) {
            new YamlSaveRunnable(this).runTaskTimer(plugin, 0, savePeriod);
        }
    }

    public YamlStorage(Plugin plugin, String path, boolean savePeriodically) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), path);
        this.config = YamlConfiguration.loadConfiguration(file);
        if (savePeriodically) {
            new YamlSaveRunnable(this).runTaskTimer(plugin, 0, savePeriod);
        }
    }

    public YamlTraverser getTraverser() {
        return new YamlTraverser(plugin, config);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
