package com.petalrites.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.function.BiConsumer;

public record SpellEffect(
    String name,
    String description,
    BiConsumer<ServerLevel, BlockPos> effect
) {
    public void activate(ServerLevel level, BlockPos pos) {
        effect.accept(level, pos);
    }
}
