package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Tile;

public class Wall extends Tile {
    public Wall(int posX, int posY) {
        super(posX, posY, '#', TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void onStep() {}

    @Override
    public void onInteract() {}
}
