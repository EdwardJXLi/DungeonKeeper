package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Torch Wall Decoration
 */

public class Torch extends Decoration {
    // EFFECTS: Creates a torch wall decoration
    public Torch(int posX, int posY, TorchType type) {
        super(
                posX, posY, SpriteID.DECORATION_TORCH, "Torch", type.getValue()
        );
    }

    public enum TorchType {
        CENTER(0),
        LEFT(1),
        RIGHT(2);

        private final int value;

        TorchType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
