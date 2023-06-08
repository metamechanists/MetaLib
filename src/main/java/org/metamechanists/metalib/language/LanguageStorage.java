package org.metamechanists.metalib.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.colors.Colors;
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
            } else if (rawValue instanceof Component value) {
                message = message.replace("{" + i + "}", LegacyComponentSerializer.legacySection().serialize(value));
            } else {
                plugin.getLogger().severe("Could not substitute placeholder of type " + rawValue.getClass());
            }

            i++;
        }

        return message;
    }

    private String fillColors(String message) {
        for (Colors colors : Colors.values()) {
            message = message.replace("{" + colors.name() + "}", colors.tag());
        }
        return message;
    }

    public final Component getLanguageEntry(String path, Object... placeholders) {
        String message = languageTraverser.get(path);
        if (message == null) {
            ComponentLogger.logger().error("Language file entry missing: " + path);
            return Component.text("Language file entry missing. Contact a server admin and show them this message!", NamedTextColor.RED);
        }

        message = fillPlaceholders(message, placeholders);
        message = fillColors(message);
        return MiniMessage.miniMessage().deserialize(message);
    }
}
