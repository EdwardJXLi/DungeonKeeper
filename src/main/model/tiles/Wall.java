package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Tile;

public class Wall extends Tile {
    // EFFECTS: Creates a generic wall object
    public Wall(int posX, int posY) {
        super(posX, posY, '#', TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, "Wall", null);
    }

    // EFFECTS: Since you cannot walk through a wall, it is solid.
    @Override
    public boolean isSolid() {
        return true;
    }

    // EFFECTS: Nothing happens
    @Override
    public void onStep(Entity e) {}
}
