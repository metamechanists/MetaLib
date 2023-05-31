package org.metamechanists.metalib.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class KeyUtils {

    private final Plugin plugin;

    public KeyUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    public NamespacedKey newKey(String id) {
        return new NamespacedKey(plugin, id);
    }

    public static String toId(String string) {
        final StringBuilder id = new StringBuilder();
        for (char character : ChatColor.stripColor(string).replace(" ", "_").toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                id.append(character);
            }
        }
        return id.toString();
    }
}
