package com.petalrites.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class RitualDetector {

    public record DetectionResult(PatternType pattern, String hexCode) {
        public String registryKey() {
            return pattern.name() + ":" + hexCode;
        }
    }

    public static DetectionResult detect(Level level, BlockPos pedestalPos) {
        for (PatternType pattern : PatternType.values()) {
            String hex = tryMatchPattern(level, pedestalPos, pattern);
            if (hex != null) {
                return new DetectionResult(pattern, hex);
            }
        }
        return null;
    }

    private static String tryMatchPattern(Level level, BlockPos center, PatternType pattern) {
        StringBuilder hex = new StringBuilder();
        for (BlockOffset offset : pattern.getPositions()) {
            BlockPos checkPos = center.offset(offset.dx(), 0, offset.dz());
            Block block = level.getBlockState(checkPos).getBlock();
            Character digit = FlowerHex.toHex(block);
            if (digit == null) {
                return null;
            }
            hex.append(digit);
        }
        return hex.toString();
    }

    private RitualDetector() {}
}
