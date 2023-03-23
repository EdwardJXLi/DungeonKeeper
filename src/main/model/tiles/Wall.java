package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Tile;
import model.graphics.SpriteID;

/*
 * Basic map tile that restricts movement
 */

public class Wall extends Tile {
    // EFFECTS: Creates a generic wall object
    public Wall(int posX, int posY) {
        super(
                posX, posY, true, '#',
                TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, SpriteID.TILE_WALL,
                "Wall", null
        );
    }
}
