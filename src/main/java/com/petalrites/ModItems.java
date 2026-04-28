package com.petalrites;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PetalRites.MOD_ID);

    public static final DeferredItem<BlockItem> RITUAL_PEDESTAL =
        ITEMS.registerSimpleBlockItem(ModBlocks.RITUAL_PEDESTAL);

    public static final DeferredItem<Item> RITUAL_FOCUS =
        ITEMS.registerSimpleItem("ritual_focus", new Item.Properties().stacksTo(16));
}
