package org.metamechanists.metalib.yaml;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class YamlTraverser {

    private final Plugin plugin;
    private final ConfigurationSection section;
    private final String rootName;

    public YamlTraverser(Plugin plugin, ConfigurationSection section, String rootName) {
        this.plugin = plugin;
        this.section = section;
        this.rootName = rootName;
    }

    public YamlTraverser(Plugin plugin, File file) {
        this.plugin = plugin;
        this.section = YamlConfiguration.loadConfiguration(file);
        this.rootName = file.getName();
    }

    public YamlTraverser(Plugin plugin, String path) {
        this.plugin = plugin;
        this.section = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path));
        this.rootName = path;
    }

    private void logMissingKey(String key) {
        plugin.getLogger().severe("Could not find " + rootName + "." + section.getCurrentPath() + "." + key);
    }

    public @Nullable YamlTraverser getSection(String key, boolean throwsError) {
        if (throwsError && section.getConfigurationSection(key) == null) {
            logMissingKey(key);
        }
        return new YamlTraverser(plugin, section.getConfigurationSection(key), rootName);
    }
    public @Nullable YamlTraverser getSection(String key) {
        return getSection(key, true);
    }

    public List<YamlTraverser> getSections() {
        List<YamlTraverser> list = new ArrayList<>();
        for (String key : section.getKeys(false)) {
             list.add(new YamlTraverser(plugin, section.getConfigurationSection(key), rootName));
        }
        return list;
    }

    public String name() {
        return section.getName();
    }
    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(String key, boolean throwsError) {
        if (throwsError && section.get(key) == null) {
            logMissingKey(key);
        }
        return (T) section.get(key);
    }
    public <T> @NonNull T get(String key, T defaultValue) {
        T value = get(key, false);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
    public <T> @Nullable T get(String key) {
        return get(key, true);
    }

    public <T> void set(String key, T value) {
        section.set(key, value);
    }
}
