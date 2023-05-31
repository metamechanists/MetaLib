package org.metamechanists.metalib.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ColorUtils {

    public static String getHexFromString(String rawHex) {
        // "#ffffff" -> "&x&f&f&f&f&f&f
        // "#123456" -> "&x&1&2&3&4&5&6
        final StringBuilder colorString = new StringBuilder("&x");
        for (char character : rawHex.toCharArray()) {
            colorString.append("&").append(character);
        }
        return colorString.toString();
    }

    public static List<String> formatColors(List<String> toColor) {
        final List<String> toReturn = new ArrayList<>();
        for (String color : toColor) {
            toReturn.add(formatColors(color));
        }
        return toReturn;
    }

    public static String formatColors(String toColor) {
        return ChatColor.translateAlternateColorCodes('&', toColor);
    }

    public static String removeColors(String string) {
        return ChatColor.stripColor(string);
    }
}
