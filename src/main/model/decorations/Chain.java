package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Chain Wall Decoration
 */

public class Chain extends Decoration {
    // EFFECTS: Creates a chain wall decoration
    public Chain(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_CHAIN, "Chain"
        );
    }
}
