package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Chain Decoration
 */

public class Chain extends Decoration {
    public enum ChainType {
        TYPE1(0),
        TYPE2(1);

        private final int value;
        ChainType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // EFFECTS: Creates a chain decoration
    public Chain(int posX, int posY, ChainType type) {
        super(
                posX, posY, SpriteID.DECORATION_CHAIN, "Chain", type.getValue()
        );
    }

    // EFFECTS: Creates a chain decoration with a normal chain
    public Chain(int posX, int posY) {
        this(posX, posY, ChainType.TYPE1);
    }
}
