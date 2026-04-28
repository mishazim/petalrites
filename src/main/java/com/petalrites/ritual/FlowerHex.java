package com.petalrites.ritual;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maps vanilla flowers to hexadecimal digits 0-F.
 *
 * Flower → Hex reference table:
 *   0 = Dandelion        8 = Pink Tulip
 *   1 = Poppy            9 = Oxeye Daisy
 *   2 = Blue Orchid      A = Cornflower
 *   3 = Allium           B = Lily of the Valley
 *   4 = Azure Bluet      C = Wither Rose
 *   5 = Red Tulip        D = Sunflower
 *   6 = Orange Tulip     E = Lilac
 *   7 = White Tulip      F = Peony
 */
public final class FlowerHex {

    private static final Map<Block, Character> FLOWER_TO_HEX = new LinkedHashMap<>();
    private static final Map<Character, String> HEX_TO_NAME = new LinkedHashMap<>();

    static {
        put(Blocks.DANDELION,          '0', "Dandelion");
        put(Blocks.POPPY,              '1', "Poppy");
        put(Blocks.BLUE_ORCHID,        '2', "Blue Orchid");
        put(Blocks.ALLIUM,             '3', "Allium");
        put(Blocks.AZURE_BLUET,        '4', "Azure Bluet");
        put(Blocks.RED_TULIP,          '5', "Red Tulip");
        put(Blocks.ORANGE_TULIP,       '6', "Orange Tulip");
        put(Blocks.WHITE_TULIP,        '7', "White Tulip");
        put(Blocks.PINK_TULIP,         '8', "Pink Tulip");
        put(Blocks.OXEYE_DAISY,        '9', "Oxeye Daisy");
        put(Blocks.CORNFLOWER,         'A', "Cornflower");
        put(Blocks.LILY_OF_THE_VALLEY, 'B', "Lily of the Valley");
        put(Blocks.WITHER_ROSE,        'C', "Wither Rose");
        put(Blocks.SUNFLOWER,          'D', "Sunflower");
        put(Blocks.LILAC,              'E', "Lilac");
        put(Blocks.PEONY,              'F', "Peony");
    }

    private static void put(Block block, char hex, String name) {
        FLOWER_TO_HEX.put(block, hex);
        HEX_TO_NAME.put(hex, name);
    }

    public static Character toHex(Block block) {
        return FLOWER_TO_HEX.get(block);
    }

    public static String flowerName(char hex) {
        return HEX_TO_NAME.getOrDefault(hex, "?");
    }

    public static Map<Block, Character> getMap() {
        return Collections.unmodifiableMap(FLOWER_TO_HEX);
    }

    private FlowerHex() {}
}
