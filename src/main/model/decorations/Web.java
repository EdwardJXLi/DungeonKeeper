package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Web Wall Decoration
 */

public class Web extends Decoration {
    // EFFECTS: Creates a web wall decoration
    public Web(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_WEB, "Web"
        );
    }
}
