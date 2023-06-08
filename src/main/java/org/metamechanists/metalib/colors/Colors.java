package org.metamechanists.metalib.colors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@SuppressWarnings("unused")
public enum Colors {

    BLACK(NamedTextColor.BLACK),
    DARK_BLUE(NamedTextColor.DARK_BLUE),
    GREEN(NamedTextColor.DARK_GREEN),
    DARK_AQUA(NamedTextColor.DARK_AQUA),
    DARK_RED(NamedTextColor.DARK_RED),
    DARK_PURPLE(NamedTextColor.DARK_PURPLE),
    GOLD(NamedTextColor.GOLD),
    GRAY(NamedTextColor.GRAY),
    DARK_GRAY(NamedTextColor.DARK_GRAY),
    BLUE(NamedTextColor.BLUE),
    LIGHT_GREEN(NamedTextColor.GREEN),
    AQUA(NamedTextColor.AQUA),
    RED(NamedTextColor.RED),
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE),
    YELLOW(NamedTextColor.YELLOW),
    WHITE(NamedTextColor.WHITE),

    MM_ORANGE(0xFFDE75),
    MM_YELLOW(0xD4A94C),
    MM_LIGHT_BLUE(0x9DC3E6),
    MM_GRAY(0xADB9CA),
    MM_BROWN(0x996666),

    COPPER_GREEN(0x69DA92),
    CRYSTAL_PURPLE (0xAA85CE),
    EMERALD_GREEN(0x56BF79),
    CARBON_GRAY(0x4C4C4C),
    GOLD_YELLOW(0xDDD305),
    DYE_WHITE(0xA9E7FF),
    DYE_DARK(0x53C6EF),
    PIG_PINK(0xFFAAEB),
    NETWORKS_QUANTUM_CYAN(0x3295a8),
    SOUL_LIGHT_BLUE(0xCDEEEE),
    AURELIUM_SKILL_ITEM_BLUE(0x6798E6),
    SLIME_TINKER_BLUE(0x21588f),
    LAVA_ORANGE(0xED6A13),
    DRIPSTONE_BROWN(0xFCD2A4),
    BLAZE_ORANGE(0xFFB805),
    TINKERS_ARROWS_LIME(0xC2FC03),

    DEEP_BLUE(0x03404F),
    LIGHT_BLUE(0x7692C2),
    BROWN(0x865D49);

    public final TextColor color;

    private static final LegacyComponentSerializer ampersandSerializer = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    private static final LegacyComponentSerializer sectionSerializer = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    Colors(int hex) {
         color = TextColor.color(hex);
    }

    Colors(NamedTextColor namedTextColor) {
        color = TextColor.color(namedTextColor);
    }

    public String tag() {
        return "<" + color.asHexString() + ">";
    }

    public String legacySection() {
        return sectionSerializer.serialize(Component.text("", color));
    }

    public String legacyAmpersand() {
        return ampersandSerializer.serialize(Component.text("", color)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
    }
}
