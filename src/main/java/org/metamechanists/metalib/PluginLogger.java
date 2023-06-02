package org.metamechanists.metalib;

import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.language.LanguageStorage;
import org.metamechanists.metalib.language.PlaceholderPair;

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
    public final void infoLanguageEntry(String path, PlaceholderPair<Object>... args) {
        info(languageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void warningLanguageEntry(String path, PlaceholderPair<Object>... args) {
        warning(languageStorage.getLanguageEntry(path, args));
    }

    @SafeVarargs
    public final void severeLanguageEntry(String path, PlaceholderPair<Object>... args) {
        severe(languageStorage.getLanguageEntry(path, args));
    }
}
