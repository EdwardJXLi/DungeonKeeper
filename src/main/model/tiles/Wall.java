package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Tile;

public class Wall implements Tile {
    private int posX;
    private int posY;

    public Wall(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public char getTextSprite() {
        return '#';
    }

    @Override
    public TextColor getTextColor() {
        return TextColor.ANSI.MAGENTA;
    }

    @Override
    public TextColor getBackgroundColor() {
        return TextColor.ANSI.DEFAULT;
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
