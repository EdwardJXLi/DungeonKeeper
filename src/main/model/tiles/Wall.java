package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Tile;

public class Wall extends Tile {
    // EFFECTS: Creates a generic wall object
    public Wall(int posX, int posY) {
        super(posX, posY, '#', TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, "Wall", null);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    // EFFECTS: Nothing
    public void onStep(Entity e) {}
}