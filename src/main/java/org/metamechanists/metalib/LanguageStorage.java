package org.metamechanists.metalib;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.utils.ColorUtils;
import org.metamechanists.metalib.yaml.WriteableYaml;
import org.metamechanists.metalib.yaml.YamlTraverser;

@SuppressWarnings("unused")
public class LanguageStorage {

    private final Plugin plugin;
    private final YamlTraverser languageTraverser;
    private final String prefix;

    public LanguageStorage(Plugin plugin_) {
        plugin = plugin_;
        languageTraverser = new YamlTraverser(plugin, "language.yml");
        prefix = getLanguageEntry("general.prefix");
    }

    @SafeVarargs
    private String fillPlaceholders(String message, Pair<String, Object>... args) {
        String filledMessage = message;

        for (Pair<String, Object> placeholderInfo : args) {
            String key = placeholderInfo.getFirstValue();
            Object rawValue = placeholderInfo.getSecondValue();

            if (rawValue instanceof Player value) {
                filledMessage = filledMessage.replace("{" + key + "}", value.getName());
            } else if (rawValue instanceof String value) {
                filledMessage = filledMessage.replace("{" + key + "}", value);
            } else if (rawValue instanceof Integer value) {
                filledMessage = filledMessage.replace("{" + key + "}", value.toString());
            } else if (rawValue instanceof Double value) {
                filledMessage = filledMessage.replace("{" + key + "}", value.toString());
            } else {
                plugin.getLogger().severe("Could not substitute placeholder of type " + rawValue.getClass());
            }
        }

        return filledMessage;
    }

    private String fillColors(String message) {
        while (message.contains("{#")) {
            final String rawHex = message.substring(message.indexOf("{#")+3, message.indexOf("{#")+9);
            plugin.getLogger().severe("FUCK YOU JAVA " + rawHex);
            message = message.replace("{#" + rawHex + "}", ColorUtils.getHexFromString(rawHex));
        }
        return message;
    }

    @SafeVarargs
    public final String getLanguageEntry(String path, boolean usePrefix, Pair<String, Object>... args) {
        String message = usePrefix ? prefix : "";
        message += languageTraverser.get(path);
        if (message == null) {
            return ChatColor.RED + "Language file entry missing. Contact a server admin and show them this message!";
        }
        message = fillPlaceholders(message, args);
        message = ColorUtils.formatColors(message);
        message = fillColors(message);
        return message;
    }

    @SafeVarargs
    public final String getLanguageEntry(String path, Pair<String, Object>... args) {
        return getLanguageEntry(path, false, args);
    }
}
