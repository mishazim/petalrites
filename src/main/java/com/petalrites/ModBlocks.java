package com.petalrites;

import com.petalrites.block.RitualPedestalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PetalRites.MOD_ID);

    public static final DeferredBlock<RitualPedestalBlock> RITUAL_PEDESTAL = BLOCKS.registerBlock(
        "ritual_pedestal",
        RitualPedestalBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
    );
}
