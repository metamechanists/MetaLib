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
    public static final String MM_YELLOW = ColorUtils.addHexColor("MM_YELLOW", "D4A94C");
    public static final String MM_LIGHT_BLUE = ColorUtils.addHexColor("MM_LIGHT_BLUE", "9DC3E6");
    public static final String MM_GRAY = ColorUtils.addHexColor("MM_GRAY", "ADB9CA");
    public static final String MM_BROWN = ColorUtils.addHexColor("MM_BROWN", "996666");

    public static final String COPPER_GREEN = ColorUtils.addHexColor("COPPER_GREEN", "69DA92");
    public static final String CRYSTAL_PURPLE =  ColorUtils.addHexColor("CRYSTAL_PURPLE", "AA85CE");
    public static final String EMERALD_GREEN = ColorUtils.addHexColor("EMERALD_GREEN", "56BF79");
    public static final String CARBON_GRAY = ColorUtils.addHexColor("CARBON_GRAY", "4C4C4C");
    public static final String GOLD_YELLOW = ColorUtils.addHexColor("GOLD_YELLOW", "DDD305");
    public static final String DYE_WHITE = ColorUtils.addHexColor("DYE_WHITE", "A9E7FF");
    public static final String DYE_DARK = ColorUtils.addHexColor("DYE_DARK", "53C6EF");
    public static final String PIG_PINK = ColorUtils.addHexColor("PIG_PINK", "FFAAEB");
    public static final String ZINC_BLUE = ColorUtils.addHexColor("ZINC_BLUE", "477A87");
    public static final String NETWORKS_QUANTUM_CYAN = ColorUtils.addHexColor("NETWORKS_QUANTUM_CYAN", "3295a8");
    public static final String SOUL_LIGHT_BLUE = ColorUtils.addHexColor("SOUL_LIGHT_BLUE", "CDEEEE");
    public static final String AURELIUM_SKILL_ITEM_BLUE = ColorUtils.addHexColor("AURELIUM_SKILL_ITEM_BLUE", "6798E6");
    public static final String SLIME_TINKER_BLUE = ColorUtils.addHexColor("SLIME_TINKER_BLUE", "21588f");
    public static final String LAVA_ORANGE = ColorUtils.addHexColor("LAVA_ORANGE", "ED6A13");
    public static final String DRIPSTONE_BROWN = ColorUtils.addHexColor("DRIPSTONE_BROWN", "FCD2A4");
    public static final String BLAZE_ORANGE = ColorUtils.addHexColor("BLAZE_ORANGE", "FFB805");
    public static final String TINKERS_ARROWS_LIME = ColorUtils.addHexColor("TINKERS_ARROWS_LIME", "C2FC03");

    public static final String DEEP_BLUE = ColorUtils.addHexColor("DEEP_BLUE", "03404F");
    public static final String LIGHT_BLUE = ColorUtils.addHexColor("LIGHT_BLUE", "7692C2");
    public static final String BROWN = ColorUtils.addHexColor("BROWN", "865D49");

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
