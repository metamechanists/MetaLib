package org.metamechanists.metalib.yaml;

import org.bukkit.scheduler.BukkitRunnable;

public class YamlSaveRunnable extends BukkitRunnable {
    final private WriteableYaml yamlFile;
    public YamlSaveRunnable(WriteableYaml yamlFile) {
        this.yamlFile = yamlFile;
    }

    @Override
    public void run() {
        yamlFile.save();
    }
}