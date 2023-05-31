package org.metamechanists.metalib;

import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.metamechanists.metalib.yaml.YamlTraverser;

@SuppressWarnings("unused")
public class LanguageStorage {

    private Plugin plugin;
    private YamlTraverser languageTraverser;
    private String prefix;

    private LanguageStorage() {}

    public void Init(Plugin plugin_) {
        plugin = plugin_;
        languageTraverser = new YamlTraverser(plugin, "language.yml");
        prefix  = getLanguageEntry("general.prefix");
    }

    @SafeVarargs
    private String fillPlaceholders(String message, ImmutablePair<String, Object>... args) {
        String filledMessage = message;

        for (ImmutablePair<String, Object> placeholderInfo : args) {
            String key = placeholderInfo.getKey();
            Object rawValue = placeholderInfo.getValue();

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

    @SafeVarargs
    public final String getLanguageEntry(String path, ImmutablePair<String, Object>... args) {
        String entry = languageTraverser.get(path);
        String message = fillPlaceholders(prefix + entry, args);
        return ChatColors.color(message);
    }
}
