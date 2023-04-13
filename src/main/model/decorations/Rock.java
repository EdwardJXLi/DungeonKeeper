package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Rock Decoration
 */

public class Rock extends Decoration {
    // EFFECTS: Creates a rock decoration
    public Rock(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_ROCK, "Rock", 0
        );
    }
}
