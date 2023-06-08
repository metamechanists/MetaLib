package org.metamechanists.metalib.utils;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ColorUtils {

    @Getter
    private static final Map<String, TextColor> colorMap = new HashMap<>();

    public static final TextColor MM_ORANGE = ColorUtils.addHexColor("MM_ORANGE", 0xFFDE75);
    public static final TextColor MM_YELLOW = ColorUtils.addHexColor("MM_YELLOW", 0xD4A94C);
    public static final TextColor MM_LIGHT_BLUE = ColorUtils.addHexColor("MM_LIGHT_BLUE", 0x9DC3E6);
    public static final TextColor MM_GRAY = ColorUtils.addHexColor("MM_GRAY", 0xADB9CA);
    public static final TextColor MM_BROWN = ColorUtils.addHexColor("MM_BROWN", 0x996666);

    public static final TextColor COPPER_GREEN = ColorUtils.addHexColor("COPPER_GREEN", 0x69DA92);
    public static final TextColor CRYSTAL_PURPLE =  ColorUtils.addHexColor("CRYSTAL_PURPLE", 0xAA85CE);
    public static final TextColor EMERALD_GREEN = ColorUtils.addHexColor("EMERALD_GREEN", 0x56BF79);
    public static final TextColor CARBON_GRAY = ColorUtils.addHexColor("CARBON_GRAY", 0x4C4C4C);
    public static final TextColor GOLD_YELLOW = ColorUtils.addHexColor("GOLD_YELLOW", 0xDDD305);
    public static final TextColor DYE_WHITE = ColorUtils.addHexColor("DYE_WHITE", 0xA9E7FF);
    public static final TextColor DYE_DARK = ColorUtils.addHexColor("DYE_DARK", 0x53C6EF);
    public static final TextColor PIG_PINK = ColorUtils.addHexColor("PIG_PINK", 0xFFAAEB);
    public static final TextColor NETWORKS_QUANTUM_CYAN = ColorUtils.addHexColor("NETWORKS_QUANTUM_CYAN", 0x3295a8);
    public static final TextColor SOUL_LIGHT_BLUE = ColorUtils.addHexColor("SOUL_LIGHT_BLUE", 0xCDEEEE);
    public static final TextColor AURELIUM_SKILL_ITEM_BLUE = ColorUtils.addHexColor("AURELIUM_SKILL_ITEM_BLUE", 0x6798E6);
    public static final TextColor SLIME_TINKER_BLUE = ColorUtils.addHexColor("SLIME_TINKER_BLUE", 0x21588f);
    public static final TextColor LAVA_ORANGE = ColorUtils.addHexColor("LAVA_ORANGE", 0xED6A13);
    public static final TextColor DRIPSTONE_BROWN = ColorUtils.addHexColor("DRIPSTONE_BROWN", 0xFCD2A4);
    public static final TextColor BLAZE_ORANGE = ColorUtils.addHexColor("BLAZE_ORANGE", 0xFFB805);
    public static final TextColor TINKERS_ARROWS_LIME = ColorUtils.addHexColor("TINKERS_ARROWS_LIME", 0xC2FC03);

    public static final TextColor DEEP_BLUE = ColorUtils.addHexColor("DEEP_BLUE", 0x03404F);
    public static final TextColor LIGHT_BLUE = ColorUtils.addHexColor("LIGHT_BLUE", 0x7692C2);
    public static final TextColor BROWN = ColorUtils.addHexColor("BROWN", 0x865D49);

    private static TextColor addHexColor(String name, int hex) {
        final TextColor color = TextColor.color(hex);
        colorMap.put(name, color);
        return color;
    }

    public static TextColor getHexColor(String name) {
        final TextColor color = colorMap.get(name);
        if (color == null) {
            ComponentLogger.logger().error("Color \"" + name + "\" not found");
            return null;
        }
        return color;
    }

    public static String getLegacyHexColor(String name) {
        return LegacyComponentSerializer.legacySection().serialize(Component.text().color(getHexColor(name)).build());
    }
}
