package com.petalrites;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PetalRites.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PETAL_RITES_TAB =
        CREATIVE_TABS.register("petal_rites_tab", () ->
            CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.petalrites"))
                .icon(() -> new ItemStack(ModItems.RITUAL_FOCUS.get()))
                .displayItems((params, output) -> {
                    output.accept(ModItems.RITUAL_PEDESTAL.get());
                    output.accept(ModItems.RITUAL_FOCUS.get());
                })
                .build()
        );
}
