package org.metamechanists.metalib.yaml;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

@SuppressWarnings("unused")
public class ReadOnlyYaml {

    private final Plugin plugin;
    private final YamlConfiguration config;

    public ReadOnlyYaml(Plugin plugin, File file) {
        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public ReadOnlyYaml(Plugin plugin, String path) {
        this(plugin, new File(plugin.getDataFolder(), path));
    }

    public YamlTraverser getTraverser() {
        return new YamlTraverser(plugin, config);
    }
}
