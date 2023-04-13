package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

/*
 * Web Decoration
 */

public class Web extends Decoration {
    public enum WebType {
        TYPE1_LEFT(0),
        TYPE1_RIGHT(1),
        TYPE2_LEFT(2),
        TYPE2_RIGHT(3);

        private final int value;
        WebType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // EFFECTS: Creates a web decoration
    public Web(int posX, int posY, WebType type) {
        super(
                posX, posY, SpriteID.DECORATION_WEB, "Web", type.getValue()
        );
    }
}
