package model.decorations;

import com.googlecode.lanterna.TextColor;
import model.Decoration;
import model.Enemy;
import model.Entity;
import model.Tile;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Torch Wall Decoration
 */

public class Torch extends Decoration {
    // EFFECTS: Creates a torch wall decoration
    public Torch(int posX, int posY) {
        super(
                posX, posY, SpriteID.DECORATION_TORCH, "Torch"
        );
    }
}
