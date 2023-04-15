package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Bone Decoration
 */

public class Bone extends Decoration {
    public enum BoneType {
        NORMAL(0),
        SKULL(1);

        private final int value;
        BoneType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // EFFECTS: Creates a bone decoration
    public Bone(int posX, int posY, BoneType type) {
        super(
                posX, posY, SpriteID.DECORATION_BONE, "Bone", type.getValue()
        );
    }
}
