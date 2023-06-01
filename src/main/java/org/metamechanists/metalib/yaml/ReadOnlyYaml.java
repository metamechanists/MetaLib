package org.metamechanists.metalib.yaml;

import org.bukkit.plugin.Plugin;

import java.io.File;

@SuppressWarnings("unused")
public class ReadOnlyYaml {

    private final YamlTraverser traverser;

    public ReadOnlyYaml(Plugin plugin, File file) {
        this.traverser = new YamlTraverser(plugin, file);
    }

    public ReadOnlyYaml(Plugin plugin, String path) {
        this(plugin, new File(plugin.getDataFolder(), path));
    }

    public YamlTraverser getTraverser() {
        return traverser;
    }
}
