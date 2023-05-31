package org.metamechanists.metalib.yaml;

import org.bukkit.scheduler.BukkitRunnable;

public class YamlSaveRunnable extends BukkitRunnable {
    final private YamlStorage yamlFile;
    public YamlSaveRunnable(YamlStorage yamlFile) {
        this.yamlFile = yamlFile;
    }

    @Override
    public void run() {
        yamlFile.save();
    }
}