package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Banner Wall Decoration
 */

public class Banner extends Decoration {
    // EFFECTS: Creates a banner wall decoration
    public Banner(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_BANNER, "Banner"
        );
    }
}
