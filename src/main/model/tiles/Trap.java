package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Tile;

public class Trap extends Tile {
    // EFFECTS: Creates a generic wall object
    public Trap(int posX, int posY) {
        super(posX, posY, '"', TextColor.ANSI.BLACK, TextColor.ANSI.RED);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void onStep() {
    }
}
