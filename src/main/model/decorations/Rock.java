package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Rock Decoration
 */

public class Rock extends Decoration {
    // EFFECTS: Creates a rock decoration
    public Rock(int posX, int posY, RockType type) {
        super(
                posX, posY, SpriteID.DECORATION_ROCK, "Rock", type.getValue()
        );
    }

    public enum RockType {
        BIG(0),
        SMALL(1);

        private final int value;

        RockType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
