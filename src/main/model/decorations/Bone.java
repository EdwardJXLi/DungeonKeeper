package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Bone Wall Decoration
 */

public class Bone extends Decoration {
    // EFFECTS: Creates a bone wall decoration
    public Bone(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_BONE, "Bone"
        );
    }
}
