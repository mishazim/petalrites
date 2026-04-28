# Petal Rites — Player Guide

Everything you need to perform rituals, discover spells, and decode the flower-hex system.

---

## Table of Contents

1. [Getting Started](#getting-started)
2. [Crafting](#crafting)
3. [How Rituals Work](#how-rituals-work)
4. [Flower Hex Reference](#flower-hex-reference)
5. [Pattern Shapes](#pattern-shapes)
6. [Full Spell List](#full-spell-list)
7. [Discovering New Spells](#discovering-new-spells)
8. [Tips & Tricks](#tips--tricks)

---

## Getting Started

Petal Rites uses vanilla Minecraft flowers as magical ingredients. Different flowers represent different hex digits (0–F), and the geometric shape of the arrangement defines the spell category. Combine them and right-click the Ritual Pedestal with a Ritual Focus to cast.

**You only need two crafted items to start:**

- **Ritual Pedestal** — the anchor block for every ritual
- **Ritual Focus** — the consumable activator (one per cast unless you're in Creative)

---

## Crafting

### Ritual Pedestal
Place **8 Chiseled Stone Bricks** in a crafting grid leaving the center empty:

```
[C][C][C]
[C][ ][C]
[C][C][C]
```
`C` = Chiseled Stone Bricks

Yields **1 Ritual Pedestal**.

---

### Ritual Focus
```
[ ][E][ ]
[E][B][E]
[ ][E][ ]
```
`E` = Eye of Ender · `B` = Blaze Rod

Yields **4 Ritual Focuses**.

---

## How Rituals Work

### Step 1 — Place the Pedestal
Set the Ritual Pedestal on any solid surface. The ground around it needs to be clear on the **same Y-level** so you can place flowers.

### Step 2 — Lay the flowers
Arrange vanilla flowers in one of the six geometric patterns (see [Pattern Shapes](#pattern-shapes)) around the pedestal. Every position in the pattern must contain a flower — if even one slot is missing or has the wrong kind of flower-like block, the ritual fails.

> All flowers must be on the **same Y-level as the pedestal block itself**.  
> Flowers placed one block lower (on dirt) are still at the same Y as the pedestal if the pedestal is sitting on that same ground level.

### Step 3 — Activate
Hold a **Ritual Focus** and right-click the Ritual Pedestal.

**On success:**
- A rising helix of End Rod particles spirals up from the pedestal
- A burst of Totem particles fires from the center
- A beacon activation chime plays
- A chat message shows the spell name, pattern type, and hex code

**On failure (no matching pattern):**
- A smoke particle effect
- A chime at lower pitch
- Chat shows the detected pattern name and hex code (useful for discovering new spells)

The Ritual Focus is consumed on activation (1 use per cast). Creative mode players keep theirs.

---

## Flower Hex Reference

Each vanilla flower maps to one hexadecimal digit. This is the core of the spell system — the flowers you place, read in the pattern's canonical order, spell out the hex code of your ritual.

| Digit | Flower | Notes |
|-------|--------|-------|
| `0` | **Dandelion** | Common, yellow — sun and clearing |
| `1` | **Poppy** | Red — fire, strikes, noon |
| `2` | **Blue Orchid** | Blue — rain and water |
| `3` | **Allium** | Purple — wind, thunder, storms |
| `4` | **Azure Bluet** | White-blue — frost and cold |
| `5` | **Red Tulip** | Red — power and strength |
| `6` | **Orange Tulip** | Orange — warmth |
| `7` | **White Tulip** | White — purity |
| `8` | **Pink Tulip** | Pink — gentleness |
| `9` | **Oxeye Daisy** | White-yellow — balance |
| `A` | **Cornflower** | Deep blue — time and cosmos |
| `B` | **Lily of the Valley** | White — life and growth |
| `C` | **Wither Rose** | Black — decay and darkness |
| `D` | **Sunflower** | Tall, yellow — solar power |
| `E` | **Lilac** | Tall, purple — amplification |
| `F` | **Peony** | Tall, pink — grand effects |

> **Tall flowers** (Sunflower, Lilac, Peony) occupy two block heights. Place the bottom half as usual — the mod detects the flower type regardless of half.

---

## Pattern Shapes

The shape you choose determines:
- How many flowers you need
- The reading order used to generate the hex code
- Which spell list is checked

All diagrams use `P` for the Ritual Pedestal and numbers for reading order.

---

### CROSS — 4 flowers
Flowers at the four cardinal directions, distance 2 from the pedestal.

```
. . 1 . .
. . . . .
4 . P . 2
. . . . .
. . 3 . .
```

Reading order: **N → E → S → W**  
Hex code length: **4 digits**  
Example: Blue Orchid at all 4 positions = code `2222`

---

### RING — 8 flowers
Flowers at all 8 compass points forming an octagon, distance 2 from the pedestal.

```
. 8 1 2 .
. 7 . 3 .
. 6 5 4 .
```

Reading order: **N → NE → E → SE → S → SW → W → NW**  
Hex code length: **8 digits**  
Example: Wither Rose at all 8 positions = code `CCCCCCCC`

---

### DIAMOND — 4 flowers
Flowers at the four diagonal corners, distance 2 from the pedestal.

```
1 . . . 2
. . . . .
. . P . .
. . . . .
4 . . . 3
```

Reading order: **NW → NE → SE → SW**  
Hex code length: **4 digits**  
Example: Allium at all 4 corners = code `3333`

---

### STAR — 8 flowers
Four outer flowers at cardinal directions (distance 3) plus four inner flowers at diagonals (distance 2).

```
. . 1 . .
. 8 . 2 .
4 . P . 3
. 7 . 5 .
. . 6 . .
```

Reading order: **N_outer → E_outer → S_outer → W_outer → NE_inner → SE_inner → SW_inner → NW_inner**  
Hex code length: **8 digits**  
Example: Blue Orchid outer + Poppy inner = code `22221111`

---

### HEX — 6 flowers
Six flowers in a flat-top hexagonal arrangement, distance 2 (cardinal) and ~2.2 (diagonal) from the pedestal.

```
. . 1 . .
. 6 . 2 .
. . P . .
. 5 . 3 .
. . 4 . .
```

Reading order: **N → NE → SE → S → SW → NW**  
Hex code length: **6 digits**  
Example: Allium at all 6 positions = code `333333`

---

### SPIRAL — 8 flowers
Eight flowers in a clockwise Fibonacci-inspired spiral radiating outward from the pedestal.

```
. . 3 4 .
. . 2 . 5
. 1 P . 6
. . 8 . 7
```

Reading order: spiral from closest to farthest, clockwise  
Hex code length: **8 digits**  
Example: Lily of the Valley and Cornflower alternating = code `ABABABAB`

---

## Full Spell List

### CROSS Spells (4-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `2222` | **Azure Rain** | Rain for 2 in-game days |
| `0000` | **Golden Calm** | Clear sky for 7 in-game days |
| `3333` | **Tempest** | Thunderstorm for 2 in-game days |
| `CCCC` | **Voidstorm** | Thunder 2 days + Darkness effect on all players within 24 blocks |
| `BBBB` | **Harvest Bloom** | Bonemeal pulse on all crops within 8 blocks |
| `A000` | **Dawn's Kiss** | Clear sky + time set to dawn (1000 ticks) |
| `0A00` | **Solar Zenith** | Clear sky + time set to noon (6000 ticks) |
| `000A` | **Midnight Call** | Time set to midnight (18000 ticks) |
| `00A0` | **Dusk Veil** | Time set to dusk (13000 ticks) |
| `4444` | **Frost Veil** | Rain 2 days + Slowness II to players within 16 blocks for 2 min |
| `1111` | **Ember Strike** | Clear sky + lightning bolt at each of the 4 cross positions |
| `5555` | **Storm Power** | Thunder 2 days + Strength I to players within 20 blocks for 1 min |
| `9999` | **Equilibrium** | Clear sky + noon — perfect balance |
| `ABAB` | **Starfall** | Clear sky + midnight + Haste I to players for 1 min |
| `2323` | **Monsoon** | Thunder 3 days + time set to evening |
| `BCBC` | **Soul Rain** | Thunder 2 days + Regeneration I to players within 20 blocks for 1 min |
| `CDCD` | **Withering Storm** | Thunder 2 days + Wither II on all hostile mobs within 15 blocks |
| `1234` | **Chaos Bloom** | Crops grow in radius 8 + random weather outcome |

---

### RING Spells (8-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `22222222` | **Endless Rain** | Rain for 3 in-game days |
| `00000000` | **Eternal Sun** | Clear sky 7 days + noon |
| `33333333` | **Grand Tempest** | Thunderstorm for 3 in-game days |
| `CCCCCCCC` | **Apocalypse Ring** | Thunder 3 days + Darkness on all players within 30 blocks for 3 min |
| `BBBBBBBB` | **Grand Harvest** | Bonemeal pulse on all crops within 16 blocks |
| `20202020` | **Rhythmic Drizzle** | Light rain for 12 in-game hours |
| `A0A0A0A0` | **Time Weave** | Clear sky + dawn |
| `CCBBCCBB` | **Death & Life** | Thunder 2 days + crops grow radius 12 + Wither on hostile mobs |
| `01234567` | **Prism Ring** | Gentle rain + a rainbow particle ring around the pedestal |

---

### DIAMOND Spells (4-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `2222` | **Mist Circle** | Rain 2 days + cloud particle burst |
| `3333` | **Thunder Cross** | Thunder 1 day + lightning bolt at each of the 4 diagonal corners |
| `0000` | **Sunbeam Circle** | Clear sky + Speed I to players within 16 blocks for 1 min |
| `CCCC` | **Shadow Mark** | Thunder 1 day + Wither I on hostile mobs within 10 blocks |
| `1111` | **Pyre Diamond** | Clear sky + lightning bolt at each of the 4 diagonal corners |
| `BBBB` | **Growth Ring** | Bonemeal pulse on all crops within 10 blocks |

---

### STAR Spells (8-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `22221111` | **Storm Fire** | Thunder 2 days + lightning bolt at all 8 star positions |
| `00002222` | **Dawn's Promise** | Clear sky + dawn + starburst particle display |
| `CCCCBBBB` | **Death Bloom** | Thunder 2 days + crops grow radius 10 + Wither II on hostile mobs |
| `33335555` | **Tempest Power** | Thunder 2 days + Strength II to players within 24 blocks for 2 min |
| `AAAABBBB` | **Star of Abundance** | Clear sky + noon + crops grow radius 12 |

---

### HEX Spells (6-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `222222` | **Perfect Rain** | Rain 2 days + Saturation I to players within 16 blocks |
| `000000` | **Perfect Sun** | Clear sky 7 days + Haste I to players within 16 blocks |
| `333333` | **Hex Tempest** | Thunder 2 days + lightning at all 6 hex positions |
| `CCCCCC` | **Hex Curse** | Thunder 2 days + Wither II on all creatures within 15 blocks |
| `BBBBBB` | **Hex Bloom** | Bonemeal pulse on all crops within 12 blocks |
| `AAAAAA` | **Hex of Hours** | Clear sky + dawn |

---

### SPIRAL Spells (8-digit hex)

| Code | Spell Name | Effect |
|------|-----------|--------|
| `20203030` | **Spiral Storm** | Thunderstorm 2 days |
| `02020303` | **Unwinding** | Clear sky 7 days |
| `BCBCBCBC` | **Soul Spiral** | Thunder 2 days + Regeneration II to players within 20 blocks for 1 min |
| `ABABABAB` | **Time Spiral** | Clear sky + dawn + Haste I to players for 1 min |

---

## Discovering New Spells

The 39 spells above are only the known combinations. The system supports thousands more.

When you activate a pattern the mod doesn't recognise, instead of failing silently it tells you:

```
[Petal Rites] Unknown ritual discovered! (CROSS pattern, code 0x5A3B)
```

Keep a notebook of discovered codes — future mod updates may assign effects to them, and sharing codes with other players is the primary way the community discovers new rituals.

**Good starting experiments:**
- Try all-same-flower patterns in each shape
- Mix two complementary flowers (e.g., `2` and `0` for water/sun interplay)
- Use tall flowers (`D`, `E`, `F`) as modifiers — they tend to produce amplified effects

---

## Tips & Tricks

**Flower farming:** Blue Orchid grows naturally in swamps. Wither Rose drops from mobs killed by the Wither. Cornflower and Lily of the Valley are found in flower forests.

**Ritual Focus cost:** Each cast uses one Focus. Bring several when experimenting. The recipe yields 4 at a time, so stockpiling is easy.

**Range matters:** Player buff effects (Strength, Speed, Regeneration, etc.) apply to players within ~16–24 blocks of the pedestal. Stand close when casting.

**Weather vs. game rules:** If your server has `doWeatherCycle` disabled, rituals still change weather directly and will work normally.

**Mob effects:** Wither effects from spells like Withering Storm and Shadow Mark only target hostile mobs (zombies, skeletons, creepers, etc.) — not players or passive animals.

**Crop growth:** Bonemeal spells grow any `BonemealableBlock` — crops, saplings, grass, seagrass, kelp, and more.

**The pedestal doesn't need to stay:** Once a ritual is cast, the pedestal is just a block. You can pick it up and reuse it. The flowers are not consumed — only the Ritual Focus is.
