package com.petalrites.ritual;

import java.util.Collections;
import java.util.List;

/**
 * Each pattern defines which block offsets (dx, dz) are checked, and in what order
 * they are read to form the hex spell code.
 *
 * Reading order is always clockwise from North so the hex code is deterministic.
 *
 * Pattern layout diagrams (P = pedestal, numbers = reading order):
 *
 * CROSS (distance 2, 4 flowers):        RING (distance 2, 8 flowers):
 *   . . 1 . .                             . 8 1 2 .
 *   . . . . .                             . 7 . 3 .
 *   4 . P . 2                             . 6 5 4 .
 *   . . . . .
 *   . . 3 . .
 *
 * DIAMOND (distance 2 corners, 4):      STAR (outer d=3 + inner d=2, 8 flowers):
 *   1 . . . 2                             . . 1 . .
 *   . . . . .                             . 8 . 2 .
 *   . . P . .                             4 . P . 3  (outer)
 *   . . . . .                             . 7 . 5 .
 *   4 . . . 3                             . . 6 . .  + inner diagonals 5-8
 *
 * HEX (hexagonal, 6 flowers):           SPIRAL (Fibonacci-inspired, 8 flowers):
 *   . . 1 . .                             . . 3 4 .
 *   . 6 . 2 .                             . . 2 . 5
 *   . . P . .                             . 1 P . 6
 *   . 5 . 3 .                             . . 8 . 7
 *   . . 4 . .
 */
public enum PatternType {

    CROSS(List.of(
        new BlockOffset(0, -2),   // 1 N
        new BlockOffset(2, 0),    // 2 E
        new BlockOffset(0, 2),    // 3 S
        new BlockOffset(-2, 0)    // 4 W
    )),

    RING(List.of(
        new BlockOffset(0, -2),   // 1 N
        new BlockOffset(2, -2),   // 2 NE
        new BlockOffset(2, 0),    // 3 E
        new BlockOffset(2, 2),    // 4 SE
        new BlockOffset(0, 2),    // 5 S
        new BlockOffset(-2, 2),   // 6 SW
        new BlockOffset(-2, 0),   // 7 W
        new BlockOffset(-2, -2)   // 8 NW
    )),

    DIAMOND(List.of(
        new BlockOffset(-2, -2),  // 1 NW
        new BlockOffset(2, -2),   // 2 NE
        new BlockOffset(2, 2),    // 3 SE
        new BlockOffset(-2, 2)    // 4 SW
    )),

    STAR(List.of(
        new BlockOffset(0, -3),   // 1 N outer
        new BlockOffset(3, 0),    // 2 E outer
        new BlockOffset(0, 3),    // 3 S outer
        new BlockOffset(-3, 0),   // 4 W outer
        new BlockOffset(2, -2),   // 5 NE inner
        new BlockOffset(2, 2),    // 6 SE inner
        new BlockOffset(-2, 2),   // 7 SW inner
        new BlockOffset(-2, -2)   // 8 NW inner
    )),

    HEX(List.of(
        new BlockOffset(0, -2),   // 1 N
        new BlockOffset(2, -1),   // 2 NE
        new BlockOffset(2, 1),    // 3 SE
        new BlockOffset(0, 2),    // 4 S
        new BlockOffset(-2, 1),   // 5 SW
        new BlockOffset(-2, -1)   // 6 NW
    )),

    SPIRAL(List.of(
        new BlockOffset(1, 0),    // 1
        new BlockOffset(1, -1),   // 2
        new BlockOffset(0, -2),   // 3
        new BlockOffset(-1, -2),  // 4
        new BlockOffset(-2, -1),  // 5
        new BlockOffset(-2, 1),   // 6
        new BlockOffset(0, 2),    // 7
        new BlockOffset(2, 1)     // 8
    ));

    private final List<BlockOffset> positions;

    PatternType(List<BlockOffset> positions) {
        this.positions = Collections.unmodifiableList(positions);
    }

    public List<BlockOffset> getPositions() {
        return positions;
    }

    public int size() {
        return positions.size();
    }
}
