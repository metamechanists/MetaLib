package org.metamechanists.metalib;

import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.language.LanguageStorage;

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

    public final void infoLanguageEntry(String path, Object... args) {
        info(languageStorage.getLanguageEntry(path, args));
    }

    public final void warningLanguageEntry(String path, Object... args) {
        warning(languageStorage.getLanguageEntry(path, args));
    }

    public final void severeLanguageEntry(String path, Object... args) {
        severe(languageStorage.getLanguageEntry(path, args));
    }
}
