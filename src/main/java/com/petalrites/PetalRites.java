package com.petalrites;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(PetalRites.MOD_ID)
public class PetalRites {
    public static final String MOD_ID = "petalrites";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PetalRites(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTab.CREATIVE_TABS.register(modEventBus);
    }
}
