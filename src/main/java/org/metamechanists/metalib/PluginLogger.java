package org.metamechanists.metalib;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class PluginLogger {
    private final Plugin plugin;
    private final LanguageStorage languageStorage;

    public PluginLogger(Plugin plugin, LanguageStorage languageStorage) {
        this.plugin = plugin;
        this.languageStorage = languageStorage;
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
    public final void infoLanguageEntry(String path, Pair<String, Object>... args) {
        info(languageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void warningLanguageEntry(String path, Pair<String, Object>... args) {
        warning(languageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void severeLanguageEntry(String path, Pair<String, Object>... args) {
        severe(languageStorage.getLanguageEntry(path, args));
    }
}
