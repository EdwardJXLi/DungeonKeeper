package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Fancy Wall Decoration
 * This supports connecting walls together
 */

public class FancyWall extends Decoration {
    // EFFECTS: Creates a fancy wall decoration
    public FancyWall(int posX, int posY, FancyWallType type) {
        super(
                posX, posY, SpriteID.TILE_WALL, "Wall (Connected)", type.getValue()
        );
    }

    public enum FancyWallType {
        TOP_LEFT(1),
        TOP_RIGHT(6),
        BOTTOM_LEFT(15),
        BOTTOM_RIGHT(10),
        TOP(2),
        BOTTOM(11),
        LEFT(16),
        RIGHT(7);

        private final int value;

        FancyWallType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
