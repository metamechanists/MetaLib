package org.metamechanists.metalib;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class PluginLogger {
    private Plugin plugin;

    public void Init(Plugin plugin_) {
        plugin = plugin_;
    }

    public void info(String message) {
        plugin.getLogger().info(message);
    }

    public void warning(String message) {
        plugin.getLogger().warning(message);
    }

    public void severe(String message) {
        plugin.getLogger().severe(message);
    }

    @SafeVarargs
    public final void infoLanguageEntry(String path, ImmutablePair<String, Object>... args) {
        info(LanguageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void warningLanguageEntry(String path, ImmutablePair<String, Object>... args) {
        warning(LanguageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void severeLanguageEntry(String path, ImmutablePair<String, Object>... args) {
        severe(LanguageStorage.getLanguageEntry(path, args));
    }
}
