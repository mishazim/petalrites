# 🌸 Petal Rites

A Minecraft mod for **NeoForge 1.21.1** that turns vanilla flowers into a ritual magic system.

Arrange flowers in geometric patterns around a **Ritual Pedestal**, activate with a **Ritual Focus**, and the shape and flowers you choose encode a hexadecimal spell — summoning weather, bending time, striking lightning, and more.

---

## Features

- **6 geometric ritual patterns** — Cross, Ring, Diamond, Star, Hex, Spiral
- **16 vanilla flowers** each mapped to a hex digit (0–F)
- **39 predefined spells** covering weather, time of day, player buffs, lightning strikes, and crop growth
- **Open-ended discovery** — unknown flower combinations reveal their hex code in chat, letting you experiment
- No new biomes, dimensions, or world generation — pure vanilla-compatible

---

## Installation

1. Install [NeoForge 21.1.172](https://neoforged.net/) for Minecraft 1.21.1
2. Drop `petalrites-1.0.0.jar` into your `.minecraft/mods/` folder
3. Launch the game

---

## Quick Start

| Step | Action |
|------|--------|
| 1 | Craft a **Ritual Pedestal** (8× Chiseled Stone Bricks in a ring) |
| 2 | Craft a **Ritual Focus** (4× Eye of Ender around 1× Blaze Rod → 4 focuses) |
| 3 | Place the Pedestal on the ground |
| 4 | Arrange flowers in a pattern on the same Y-level around it |
| 5 | Right-click the Pedestal with a Ritual Focus |

A helix of particles and a beacon chime confirm success. The chat shows the spell name and its hex code.

---

## Flower → Hex Reference

| Hex | Flower | Hex | Flower |
|-----|--------|-----|--------|
| `0` | Dandelion | `8` | Pink Tulip |
| `1` | Poppy | `9` | Oxeye Daisy |
| `2` | Blue Orchid | `A` | Cornflower |
| `3` | Allium | `B` | Lily of the Valley |
| `4` | Azure Bluet | `C` | Wither Rose |
| `5` | Red Tulip | `D` | Sunflower |
| `6` | Orange Tulip | `E` | Lilac |
| `7` | White Tulip | `F` | Peony |

---

## Pattern Shapes

All flowers must be placed on the **same Y-level** as the pedestal (`P`). Reading order (clockwise from North) determines the hex code.

```
CROSS (4 flowers)       RING (8 flowers)        DIAMOND (4 flowers)
  . . N . .               . NW N NE .              NW . . . NE
  . . . . .               . W  .  E .              .  . . .  .
  W . P . E               . SW S SE .              .  . P .  .
  . . . . .                                         .  . . .  .
  . . S . .                                        SW . . . SE

STAR (8 flowers)        HEX (6 flowers)         SPIRAL (8 flowers)
  . . N . . . .           . . N . .               . . 3 4 .
  . . . NE. .             . NW. NE.               . . 2 . 5
  W . . . . . E           . . P . .               . 1 P . 6
  . . . SW. .             . SW. SE.               . . 8 . 7
  . . S . . . .           . . S . .
  (outer d=3, inner d=2)
```

---

## Selected Spells

See [PLAYER_GUIDE.md](PLAYER_GUIDE.md) for the full spell list with all 39 known rituals.

| Pattern | Code | Spell | Effect |
|---------|------|-------|--------|
| CROSS | `2222` | Azure Rain | Rain for 2 days |
| CROSS | `0000` | Golden Calm | Clear sky for 7 days |
| CROSS | `3333` | Tempest | Thunderstorm for 2 days |
| CROSS | `CCCC` | Voidstorm | Thunder + Darkness to nearby players |
| CROSS | `1111` | Ember Strike | Clear + lightning at the 4 pattern positions |
| CROSS | `BBBB` | Harvest Bloom | Bonemeal crops within 8 blocks |
| CROSS | `A000` | Dawn's Kiss | Clear sky + time set to dawn |
| RING | `00000000` | Eternal Sun | Clear sky for 7 days + noon |
| RING | `CCCCCCCC` | Apocalypse Ring | Thunder + 3 min Darkness to all nearby |
| DIAMOND | `3333` | Thunder Cross | Lightning strikes at all 4 diagonal corners |
| STAR | `22221111` | Storm Fire | Thunder + lightning at all 8 star points |
| HEX | `333333` | Hex Tempest | Thunder + lightning at all 6 hex points |
| SPIRAL | `BCBCBCBC` | Soul Spiral | Thunder + Regeneration II to nearby players |

---

## Building from Source

Requirements: **Java 21 JDK** (Temurin recommended), **Gradle 8.10+**

```bash
git clone https://github.com/mishazim/petalrites.git
cd petalrites
gradle build
# Output: build/libs/petalrites-1.0.0.jar
```

First build downloads NeoForge dependencies (~300 MB). Subsequent builds are fast.

---

## License

MIT — do whatever you like with it.
