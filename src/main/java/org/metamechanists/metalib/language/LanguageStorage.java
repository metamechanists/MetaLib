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

    public LanguageStorage(Plugin plugin) {
        plugin.saveResource("language.yml", true);
        this.plugin = plugin;
        this.languageTraverser = new YamlTraverser(plugin, "language.yml");
    }

    private String fillPlaceholders(String message, Object... placeholders) {
        int i = 1;
        for (Object rawValue : placeholders) {
            if (rawValue instanceof Player value) {
                message = message.replace("{" + i + "}", value.getName());
            } else if (rawValue instanceof String value) {
                message = message.replace("{" + i + "}", value);
            } else if (rawValue instanceof Integer value) {
                message = message.replace("{" + i + "}", value.toString());
            } else if (rawValue instanceof Double value) {
                message = message.replace("{" + i + "}", value.toString());
            } else {
                plugin.getLogger().severe("Could not substitute placeholder of type " + rawValue.getClass());
            }

            i++;
        }

        return message;
    }

    private String fillColors(String message) {
        for (var colorPair : ColorUtils.getColorMap().entrySet()) {
            message = message.replace("{" + colorPair.getKey() + "}", colorPair.getValue());
        }
        return message;
    }

    public final String getLanguageEntry(String path, Object... placeholders) {
        String message = languageTraverser.get(path);
        if (message == null) {
            plugin.getLogger().severe("Language file entry missing: " + path);
            return ChatColor.RED + "Language file entry missing. Contact a server admin and show them this message!";
        }

        message = fillPlaceholders(message, placeholders);
        message = ColorUtils.formatColors(message);
        message = fillColors(message);
        return message;
    }
}
