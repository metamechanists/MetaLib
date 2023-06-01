package org.metamechanists.metalib.utils;

import io.github.bakedlibs.dough.common.CommonPatterns;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ChatUtils {
    public static @Nonnull String humanize(@Nonnull String string) {
        StringBuilder builder = new StringBuilder();
        String[] segments = CommonPatterns.UNDERSCORE.split(string.toLowerCase(Locale.ROOT));

        builder.append(Character.toUpperCase(segments[0].charAt(0))).append(segments[0].substring(1));

        for (int i = 1; i < segments.length; i++) {
            String segment = segments[i];
            builder.append(' ').append(Character.toUpperCase(segment.charAt(0))).append(segment.substring(1));
        }

        return builder.toString();
    }
}
