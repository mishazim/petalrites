package com.petalrites.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Registry of all predefined spells, keyed by "PATTERN:HEXCODE".
 *
 * ═══════════════════════════════════════════════════════════════════════
 * FLOWER → HEX REFERENCE
 * ═══════════════════════════════════════════════════════════════════════
 *   0 Dandelion        4 Azure Bluet       8 Pink Tulip        C Wither Rose
 *   1 Poppy            5 Red Tulip         9 Oxeye Daisy       D Sunflower
 *   2 Blue Orchid      6 Orange Tulip      A Cornflower        E Lilac
 *   3 Allium           7 White Tulip       B Lily of Valley    F Peony
 *
 * ═══════════════════════════════════════════════════════════════════════
 * PATTERN READING ORDER (flowers read clockwise from North)
 * ═══════════════════════════════════════════════════════════════════════
 *   CROSS  (4 flowers): N → E → S → W
 *   RING   (8 flowers): N → NE → E → SE → S → SW → W → NW
 *   DIAMOND(4 flowers): NW → NE → SE → SW
 *   STAR   (8 flowers): N_outer → E_outer → S_outer → W_outer
 *                       → NE_inner → SE_inner → SW_inner → NW_inner
 *   HEX    (6 flowers): N → NE → SE → S → SW → NW
 *   SPIRAL (8 flowers): clockwise spiral outward from center
 *
 * ═══════════════════════════════════════════════════════════════════════
 * KNOWN SPELLS
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ─── CROSS spells ─── (code = 4 hex digits)
 *   2222  Azure Rain         — rain 2 days
 *   0000  Golden Calm        — clear 7 days
 *   3333  Tempest            — thunder 2 days
 *   CCCC  Voidstorm          — thunder + Darkness to nearby players
 *   BBBB  Harvest Bloom      — bonemeal crops in radius 8
 *   A000  Dawn's Kiss        — clear sky + set time to dawn
 *   0A00  Solar Zenith       — clear sky + set time to noon
 *   000A  Midnight Call      — set time to midnight
 *   00A0  Dusk Veil          — set time to dusk
 *   4444  Frost Veil         — rain + Slowness II to players (2 min)
 *   1111  Ember Strike       — clear + lightning at the 4 cross positions
 *   5555  Storm Power        — thunder + Strength I to players (1 min)
 *   9999  Equilibrium        — clear sky + noon
 *   ABAB  Starfall           — clear + night + Haste I to players
 *   2323  Monsoon            — thunder + set time to evening
 *   BCBC  Soul Rain          — thunder + Regeneration I to players
 *   CDCD  Withering Storm    — thunder + Wither II to hostile mobs radius 15
 *   1234  Chaos Bloom        — crops grow + random weather
 *
 * ─── RING spells ─── (code = 8 hex digits)
 *   22222222  Endless Rain        — rain 3 days
 *   00000000  Eternal Sun         — clear 7 days + noon
 *   33333333  Grand Tempest       — thunder 3 days
 *   CCCCCCCC  Apocalypse Ring     — thunder 3 days + Darkness 3 min to players
 *   BBBBBBBB  Grand Harvest       — bonemeal crops in radius 16
 *   20202020  Rhythmic Drizzle    — rain 12 hours
 *   A0A0A0A0  Time Weave          — clear + morning
 *   CCBBCCBB  Death & Life        — thunder + crop growth + wither mobs
 *   01234567  Prism Ring          — spawn rainbow particles + gentle rain
 *
 * ─── DIAMOND spells ─── (code = 4 hex digits)
 *   2222  Mist Circle        — rain + dense fog particles
 *   3333  Thunder Cross      — lightning at the 4 diagonal corners
 *   0000  Sunbeam Circle     — clear + Speed I to players (1 min)
 *   CCCC  Shadow Mark        — thunder + Wither I to hostile mobs radius 10
 *   1111  Pyre Diamond       — clear + lightning at corners
 *   BBBB  Growth Ring        — bonemeal crops in radius 10
 *
 * ─── STAR spells ─── (code = 8 hex digits)
 *   22221111  Storm Fire          — thunder + lightning at all 8 star positions
 *   00002222  Dawn's Promise      — clear + morning + END_ROD particles
 *   CCCCBBBB  Death Bloom         — thunder + crop growth + wither hostile mobs
 *   33335555  Tempest Power       — thunder + Strength II to players (2 min)
 *   AAAABBBB  Star of Abundance   — clear + noon + bonemeal radius 12
 *
 * ─── HEX spells ─── (code = 6 hex digits)
 *   222222  Perfect Rain       — rain 2 days + Saturation I to players
 *   000000  Perfect Sun        — clear 7 days + Haste I to players
 *   333333  Hex Tempest        — thunder + lightning at hex positions
 *   CCCCCC  Hex Curse          — thunder + Wither II to all mobs radius 15
 *   BBBBBB  Hex Bloom          — bonemeal crops radius 12
 *   AAAAAA  Hex of Hours       — clear + set time to morning
 *
 * ─── SPIRAL spells ─── (code = 8 hex digits)
 *   20203030  Spiral Storm       — thunder + rain
 *   02020303  Unwinding          — clear sky
 *   BCBCBCBC  Soul Spiral        — thunder + Regeneration II to players
 *   ABABABAB  Time Spiral        — clear + morning + Haste I
 */
public final class SpellRegistry {

    private static final Map<String, SpellEffect> REGISTRY = new LinkedHashMap<>();

    static {
        // ── CROSS ──────────────────────────────────────────────────────────────
        cross("2222", "Azure Rain",
            "Summons rain for 2 in-game days.",
            (level, pos) -> level.setWeatherParameters(0, 48000, true, false));

        cross("0000", "Golden Calm",
            "Banishes all weather for 7 days.",
            (level, pos) -> level.setWeatherParameters(168000, 0, false, false));

        cross("3333", "Tempest",
            "Calls a thunderstorm for 2 days.",
            (level, pos) -> level.setWeatherParameters(0, 48000, true, true));

        cross("CCCC", "Voidstorm",
            "Thunder rages; Darkness envelops all nearby.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                applyToNearbyPlayers(level, pos, 24,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 400, 0)));
            });

        cross("BBBB", "Harvest Bloom",
            "Flowers and crops burst with growth within 8 blocks.",
            (level, pos) -> growCropsInRadius(level, pos, 8));

        cross("A000", "Dawn's Kiss",
            "Clears the sky and turns the world to dawn.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(1000);
            });

        cross("0A00", "Solar Zenith",
            "Clears the sky and raises the sun to noon.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(6000);
            });

        cross("000A", "Midnight Call",
            "Pulls the clock forward to the deep of night.",
            (level, pos) -> level.setDayTime(18000));

        cross("00A0", "Dusk Veil",
            "Draws the sun low on the horizon.",
            (level, pos) -> level.setDayTime(13000));

        cross("4444", "Frost Veil",
            "A cold rain falls; Slowness grips those caught in it.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, false);
                applyToNearbyPlayers(level, pos, 16,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2400, 1)));
            });

        cross("1111", "Ember Strike",
            "Lightning crashes at the four ritual corners.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                for (BlockOffset off : PatternType.CROSS.getPositions()) {
                    strikeAt(level, pos.offset(off.dx(), 0, off.dz()));
                }
            });

        cross("5555", "Storm Power",
            "Thunder rolls in; courage surges through nearby warriors.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                applyToNearbyPlayers(level, pos, 20,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0)));
            });

        cross("9999", "Equilibrium",
            "Balance is restored: clear skies at the height of day.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(6000);
            });

        cross("ABAB", "Starfall",
            "Night falls clear and bright; alertness sharpens.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(18000);
                applyToNearbyPlayers(level, pos, 20,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0)));
            });

        cross("2323", "Monsoon",
            "A roaring storm breaks as evening descends.",
            (level, pos) -> {
                level.setWeatherParameters(0, 72000, true, true);
                level.setDayTime(13000);
            });

        cross("BCBC", "Soul Rain",
            "Thunder rumbles above; life force replenishes the faithful.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                applyToNearbyPlayers(level, pos, 20,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 0)));
            });

        cross("CDCD", "Withering Storm",
            "A dark tempest descends; hostile creatures wither.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                witherHostilesInRadius(level, pos, 15, 200, 1);
            });

        cross("1234", "Chaos Bloom",
            "Crops erupt with growth amid unpredictable weather.",
            (level, pos) -> {
                growCropsInRadius(level, pos, 8);
                // Random weather outcome
                int roll = level.random.nextInt(3);
                if (roll == 0) level.setWeatherParameters(0, 24000, true, false);
                else if (roll == 1) level.setWeatherParameters(0, 24000, true, true);
                else level.setWeatherParameters(24000, 0, false, false);
            });

        // ── RING ───────────────────────────────────────────────────────────────
        ring("22222222", "Endless Rain",
            "Three days of unyielding rainfall.",
            (level, pos) -> level.setWeatherParameters(0, 72000, true, false));

        ring("00000000", "Eternal Sun",
            "Seven days of brilliant sunshine and high noon.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(6000);
            });

        ring("33333333", "Grand Tempest",
            "Three days of ceaseless thunder.",
            (level, pos) -> level.setWeatherParameters(0, 72000, true, true));

        ring("CCCCCCCC", "Apocalypse Ring",
            "Thunder for three days; Darkness descends on all nearby.",
            (level, pos) -> {
                level.setWeatherParameters(0, 72000, true, true);
                applyToNearbyPlayers(level, pos, 30,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 3600, 0)));
            });

        ring("BBBBBBBB", "Grand Harvest",
            "An enormous pulse of growth touches all crops within 16 blocks.",
            (level, pos) -> growCropsInRadius(level, pos, 16));

        ring("20202020", "Rhythmic Drizzle",
            "A short, gentle rain passes over.",
            (level, pos) -> level.setWeatherParameters(0, 12000, true, false));

        ring("A0A0A0A0", "Time Weave",
            "Skies clear and a new morning is woven into being.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(1000);
            });

        ring("CCBBCCBB", "Death & Life",
            "Thunder stirs the dead; the living flourish with renewed growth.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                growCropsInRadius(level, pos, 12);
                witherHostilesInRadius(level, pos, 12, 200, 0);
            });

        ring("01234567", "Prism Ring",
            "A chromatic cascade of particles dances; gentle rain follows.",
            (level, pos) -> {
                level.setWeatherParameters(0, 12000, true, false);
                spawnRainbowRing(level, pos);
            });

        // ── DIAMOND ────────────────────────────────────────────────────────────
        diamond("2222", "Mist Circle",
            "Rain rolls in wrapped in thick silver mist.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, false);
                spawnMistParticles(level, pos);
            });

        diamond("3333", "Thunder Cross",
            "Four bolts of lightning strike the corners of fate.",
            (level, pos) -> {
                level.setWeatherParameters(0, 24000, true, true);
                for (BlockOffset off : PatternType.DIAMOND.getPositions()) {
                    strikeAt(level, pos.offset(off.dx(), 0, off.dz()));
                }
            });

        diamond("0000", "Sunbeam Circle",
            "The sun breaks free; swiftness fills those nearby.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                applyToNearbyPlayers(level, pos, 16,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0)));
            });

        diamond("CCCC", "Shadow Mark",
            "Thunder and wither plague the hostile within 10 blocks.",
            (level, pos) -> {
                level.setWeatherParameters(0, 24000, true, true);
                witherHostilesInRadius(level, pos, 10, 200, 0);
            });

        diamond("1111", "Pyre Diamond",
            "A fiery clear sky ignites the corners with lightning.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                for (BlockOffset off : PatternType.DIAMOND.getPositions()) {
                    strikeAt(level, pos.offset(off.dx(), 0, off.dz()));
                }
            });

        diamond("BBBB", "Growth Ring",
            "Life surges through roots and stems within 10 blocks.",
            (level, pos) -> growCropsInRadius(level, pos, 10));

        // ── STAR ───────────────────────────────────────────────────────────────
        star("22221111", "Storm Fire",
            "Lightning erupts at every star point amid driving rain.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                for (BlockOffset off : PatternType.STAR.getPositions()) {
                    strikeAt(level, pos.offset(off.dx(), 0, off.dz()));
                }
            });

        star("00002222", "Dawn's Promise",
            "A tranquil morning rises, heralded by trails of light.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(1000);
                spawnStarburstParticles(level, pos);
            });

        star("CCCCBBBB", "Death Bloom",
            "Thunder, growth, and decay entwine in a fearsome rite.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                growCropsInRadius(level, pos, 10);
                witherHostilesInRadius(level, pos, 10, 300, 1);
            });

        star("33335555", "Tempest Power",
            "A roaring storm grants warriors terrible strength.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                applyToNearbyPlayers(level, pos, 24,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 1)));
            });

        star("AAAABBBB", "Star of Abundance",
            "Under clear noon skies, the land overflows with growth.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(6000);
                growCropsInRadius(level, pos, 12);
            });

        // ── HEX ────────────────────────────────────────────────────────────────
        hex("222222", "Perfect Rain",
            "Rain and saturation — the ideal harvest storm.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, false);
                applyToNearbyPlayers(level, pos, 16,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.SATURATION, 600, 0)));
            });

        hex("000000", "Perfect Sun",
            "Brilliant clear skies with an energising haste.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                applyToNearbyPlayers(level, pos, 16,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0)));
            });

        hex("333333", "Hex Tempest",
            "Thunder rings across all six points of the hexagram.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                for (BlockOffset off : PatternType.HEX.getPositions()) {
                    strikeAt(level, pos.offset(off.dx(), 0, off.dz()));
                }
            });

        hex("CCCCCC", "Hex Curse",
            "A cursed storm inflicts wither on all creatures within 15 blocks.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                witherHostilesInRadius(level, pos, 15, 200, 1);
            });

        hex("BBBBBB", "Hex Bloom",
            "Six points of life force burst through the soil.",
            (level, pos) -> growCropsInRadius(level, pos, 12));

        hex("AAAAAA", "Hex of Hours",
            "Time bends; clear skies and a fresh morning are conjured.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(1000);
            });

        // ── SPIRAL ─────────────────────────────────────────────────────────────
        spiral("20203030", "Spiral Storm",
            "Rain and thunder wind together in an inward spiral.",
            (level, pos) -> level.setWeatherParameters(0, 48000, true, true));

        spiral("02020303", "Unwinding",
            "The storm unwraps itself and the sky clears.",
            (level, pos) -> level.setWeatherParameters(168000, 0, false, false));

        spiral("BCBCBCBC", "Soul Spiral",
            "Thunder and regeneration spiral outward from the rite.",
            (level, pos) -> {
                level.setWeatherParameters(0, 48000, true, true);
                applyToNearbyPlayers(level, pos, 20,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1)));
            });

        spiral("ABABABAB", "Time Spiral",
            "Clear morning and haste — woven through the spiral.",
            (level, pos) -> {
                level.setWeatherParameters(168000, 0, false, false);
                level.setDayTime(1000);
                applyToNearbyPlayers(level, pos, 20,
                    p -> p.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0)));
            });
    }

    // ── Registration helpers ────────────────────────────────────────────────
    private static void register(PatternType pattern, String hex, String name, String desc,
        BiConsumer<ServerLevel, BlockPos> effect) {
        REGISTRY.put(pattern.name() + ":" + hex, new SpellEffect(name, desc, effect));
    }
    private static void cross(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e)   { register(PatternType.CROSS,   hex, name, desc, e); }
    private static void ring(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e)    { register(PatternType.RING,    hex, name, desc, e); }
    private static void diamond(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e) { register(PatternType.DIAMOND, hex, name, desc, e); }
    private static void star(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e)    { register(PatternType.STAR,    hex, name, desc, e); }
    private static void hex(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e)     { register(PatternType.HEX,     hex, name, desc, e); }
    private static void spiral(String hex, String name, String desc, BiConsumer<ServerLevel, BlockPos> e)  { register(PatternType.SPIRAL,  hex, name, desc, e); }

    public static SpellEffect find(RitualDetector.DetectionResult result) {
        return REGISTRY.get(result.registryKey());
    }

    public static Map<String, SpellEffect> all() {
        return Collections.unmodifiableMap(REGISTRY);
    }

    // ── Shared effect helpers ────────────────────────────────────────────────
    private static void applyToNearbyPlayers(ServerLevel level, BlockPos pos, int radius,
        java.util.function.Consumer<Player> action) {
        Vec3 center = Vec3.atCenterOf(pos);
        double r2 = (double) radius * radius;
        for (Player player : level.players()) {
            if (player.distanceToSqr(center) <= r2) {
                action.accept(player);
            }
        }
    }

    private static void witherHostilesInRadius(ServerLevel level, BlockPos pos, int radius,
        int duration, int amplifier) {
        AABB box = new AABB(pos).inflate(radius);
        level.getEntitiesOfClass(net.minecraft.world.entity.Mob.class, box,
            mob -> mob instanceof net.minecraft.world.entity.monster.Enemy)
            .forEach(mob -> mob.addEffect(new MobEffectInstance(MobEffects.WITHER, duration, amplifier)));
    }

    private static void growCropsInRadius(ServerLevel level, BlockPos center, int radius) {
        BlockPos.betweenClosedStream(
            center.offset(-radius, -1, -radius),
            center.offset(radius, 1, radius)
        ).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof BonemealableBlock bm
                && bm.isValidBonemealTarget(level, pos, state)) {
                bm.performBonemeal(level, level.random, pos, state);
            }
        });
    }

    private static void strikeAt(ServerLevel level, BlockPos pos) {
        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        bolt.moveTo(Vec3.atCenterOf(pos));
        level.addFreshEntity(bolt);
    }

    private static void spawnRainbowRing(ServerLevel level, BlockPos center) {
        for (int i = 0; i < 120; i++) {
            double angle = (i / 120.0) * Math.PI * 2;
            double x = center.getX() + 0.5 + Math.cos(angle) * 3.5;
            double z = center.getZ() + 0.5 + Math.sin(angle) * 3.5;
            double y = center.getY() + 0.5 + Math.sin(angle * 6) * 0.5;
            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0.02, 0, 0.01);
        }
    }

    private static void spawnMistParticles(ServerLevel level, BlockPos center) {
        for (int i = 0; i < 80; i++) {
            double rx = (level.random.nextDouble() - 0.5) * 10;
            double rz = (level.random.nextDouble() - 0.5) * 10;
            level.sendParticles(ParticleTypes.CLOUD,
                center.getX() + 0.5 + rx, center.getY() + 1.0, center.getZ() + 0.5 + rz,
                1, 0, 0.1, 0, 0.02);
        }
    }

    private static void spawnStarburstParticles(ServerLevel level, BlockPos center) {
        for (int i = 0; i < 60; i++) {
            double angle = (i / 60.0) * Math.PI * 2;
            double r = 1 + (i / 60.0) * 2;
            double x = center.getX() + 0.5 + Math.cos(angle) * r;
            double z = center.getZ() + 0.5 + Math.sin(angle) * r;
            double y = center.getY() + 0.5 + r * 0.4;
            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0.05, 0, 0.03);
        }
    }

    private SpellRegistry() {}
}
