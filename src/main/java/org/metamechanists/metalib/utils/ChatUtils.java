package org.metamechanists.metalib.utils;

import io.github.bakedlibs.dough.common.CommonPatterns;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ChatUtils {
    public static @Nonnull String humanize(@Nonnull String string) {
        // Converts eg ENDER_DRAGON to Ender Dragon (in effect ID to name)
        StringBuilder builder = new StringBuilder();
        String[] segments = CommonPatterns.UNDERSCORE.split(string.toLowerCase(Locale.ROOT));

        builder.append(Character.toUpperCase(segments[0].charAt(0))).append(segments[0].substring(1));

        for (int i = 1; i < segments.length; i++) {
            String segment = segments[i];
            builder.append(' ').append(Character.toUpperCase(segment.charAt(0))).append(segment.substring(1));
        }

        return builder.toString();
    }

    public static String asLegacySection(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static String asLegacyAmpersand(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }
}
