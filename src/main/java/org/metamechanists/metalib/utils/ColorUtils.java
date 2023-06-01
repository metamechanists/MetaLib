package org.metamechanists.metalib.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ColorUtils {

    @Getter
    private static final Map<String, String> colorMap = new HashMap<>();

    public static final String MM_ORANGE = ColorUtils.addHexColor("MM_ORANGE", "FFDE75");
    public static final String MM_LIGHT_BLUE = ColorUtils.addHexColor("MM_LIGHT_BLUE", "9DC3E6");
    public static final String MM_GRAY = ColorUtils.addHexColor("MM_GRAY", "ADB9CA");
    public static final String MM_BROWN = ColorUtils.addHexColor("MM_BROWN", "996666");

    private static String addHexColor(String name, String rawHex) {
        // "#ffffff" -> "&x&f&f&f&f&f&f
        // "#123456" -> "&x&1&2&3&4&5&6
        final StringBuilder colorStringBuilder = new StringBuilder("&x");
        for (char character : rawHex.toCharArray()) {
            colorStringBuilder.append("&").append(character);
        }
        final String colorString = formatColors(colorStringBuilder.toString());
        colorMap.put(name, colorString);
        return colorString;
    }

    public static String getHexColor(String name) {
        final String color = colorMap.get(name);
        if (color == null) {
            return ChatColor.RED + "Color \"" + name + "\" not found; contact a server admin and show them this message!";
        }
        return color;
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
