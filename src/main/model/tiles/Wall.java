package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Tile;

public class Wall extends Tile {
    // EFFECTS: Creates a generic wall object
    public Wall(int posX, int posY) {
        super(posX, posY, '#', TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void onStep() {
    }
}
