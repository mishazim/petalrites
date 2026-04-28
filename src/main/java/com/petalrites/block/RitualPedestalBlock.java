package com.petalrites.block;

import com.petalrites.ModItems;
import com.petalrites.ritual.RitualDetector;
import com.petalrites.ritual.SpellEffect;
import com.petalrites.ritual.SpellRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RitualPedestalBlock extends Block {

    public RitualPedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
        BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (!stack.is(ModItems.RITUAL_FOCUS.get())) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        ServerLevel serverLevel = (ServerLevel) level;
        RitualDetector.DetectionResult detected = RitualDetector.detect(level, pos);

        if (detected == null) {
            broadcastMessage(serverLevel, pos,
                "§c[Petal Rites] §7No recognisable flower pattern detected around the pedestal.");
            return ItemInteractionResult.FAIL;
        }

        SpellEffect spell = SpellRegistry.find(detected);
        if (spell == null) {
            // Unknown combination — show the discovered code so players can experiment
            broadcastMessage(serverLevel, pos,
                "§9[Petal Rites] §eUnknown ritual discovered! §7(" +
                detected.pattern().name() + " pattern, code §f0x" + detected.hexCode() + "§7)");
            spawnActivationParticles(serverLevel, pos, false);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 0.6f);
        } else {
            broadcastMessage(serverLevel, pos,
                "§9[Petal Rites] §a" + spell.name() +
                " §7[" + detected.pattern().name() + ":§f0x" + detected.hexCode() + "§7] — " +
                spell.description());
            spell.activate(serverLevel, pos);
            spawnActivationParticles(serverLevel, pos, true);
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.2f);
        }

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        return ItemInteractionResult.CONSUME;
    }

    private static void broadcastMessage(ServerLevel level, BlockPos pos, String text) {
        Component msg = Component.literal(text);
        Vec3 center = Vec3.atCenterOf(pos);
        double r2 = 64.0 * 64.0;
        for (Player p : level.players()) {
            if (p.distanceToSqr(center) <= r2) {
                p.sendSystemMessage(msg);
            }
        }
    }

    private static void spawnActivationParticles(ServerLevel level, BlockPos pos, boolean success) {
        // Clockwise rising helix around the pedestal
        for (int i = 0; i < 80; i++) {
            double angle = (i / 80.0) * Math.PI * 4;
            double radius = 2.0 + (i / 80.0) * 0.5;
            double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
            double y = pos.getY() + 0.5 + (i / 80.0) * 2.5;
            level.sendParticles(success ? ParticleTypes.END_ROD : ParticleTypes.SMOKE,
                x, y, z, 1, 0, 0, 0, 0.01);
        }
        // Centre burst
        if (success) {
            level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                30, 0.4, 0.4, 0.4, 0.5);
        }
    }
}
