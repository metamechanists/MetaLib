package org.metamechanists.metalib.language;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.utils.ColorUtils;
import org.metamechanists.metalib.yaml.YamlTraverser;

@SuppressWarnings("unused")
public class LanguageStorage {

    private final Plugin plugin;
    private final YamlTraverser languageTraverser;
    private final String prefix;

    public LanguageStorage(Plugin plugin) {
        plugin.saveResource("language.yml", true);
        this.plugin = plugin;
        this.languageTraverser = new YamlTraverser(plugin, "language.yml");
        this.prefix = getLanguageEntry("general.prefix");
    }

    @SafeVarargs
    private String fillPlaceholders(String message, PlaceholderPair<Object>... args) {
        for (PlaceholderPair<Object> placeholderInfo : args) {
            final String key = placeholderInfo.key();
            final Object rawValue = placeholderInfo.value();

            if (rawValue instanceof Player value) {
                message = message.replace("{" + key + "}", value.getName());
            } else if (rawValue instanceof String value) {
                message = message.replace("{" + key + "}", value);
            } else if (rawValue instanceof Integer value) {
                message = message.replace("{" + key + "}", value.toString());
            } else if (rawValue instanceof Double value) {
                message = message.replace("{" + key + "}", value.toString());
            } else {
                plugin.getLogger().severe("Could not substitute placeholder of type " + rawValue.getClass());
            }
        }

        return message;
    }

    private String fillColors(String message) {
        for (var colorPair : ColorUtils.getColorMap().entrySet()) {
            message = message.replace("{" + colorPair.getKey() + "}", colorPair.getValue());
        }
        return message;
    }

    @SafeVarargs
    public final String getLanguageEntry(String path, boolean usePrefix, PlaceholderPair<Object>... args) {
        final String messagePrefix = usePrefix ? prefix : "";
        final String rawMessage = languageTraverser.get(path);
        if (rawMessage == null) {
            return ChatColor.RED + "Language file entry missing. Contact a server admin and show them this message!";
        }

        String message = messagePrefix + rawMessage;
        message = fillPlaceholders(message, args);
        message = ColorUtils.formatColors(message);
        message = fillColors(message);
        return message;
    }

    @SafeVarargs
    public final String getLanguageEntry(String path, PlaceholderPair<Object>... args) {
        return getLanguageEntry(path, false, args);
    }
}
