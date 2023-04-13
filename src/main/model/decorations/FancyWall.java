package model.decorations;

import model.Decoration;
import model.graphics.SpriteID;

public class FancyWall extends Decoration {
    public static enum FancyWallType {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP, BOTTOM, LEFT, RIGHT
    }

    // EFFECTS: Creates a fancy wall decoration
    public FancyWall(int posX, int posY) {
        super(
                posX, posY, SpriteID.TILE_WALL, "Wall (Connected)"
        );
    }
}
