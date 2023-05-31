package org.metamechanists.metalib;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class MetaLib extends JavaPlugin {

    private static MetaLib instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static MetaLib getInstance() {
        return instance;
    }
}