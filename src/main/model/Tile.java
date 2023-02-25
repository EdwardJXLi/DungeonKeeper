package model;

import com.googlecode.lanterna.TextColor;

public interface Tile {
    int getPosX();

    int getPosY();

    char getTextSprite();

    TextColor getTextColor();

    TextColor getBackgroundColor();

    boolean isSolid();

    void onStep();

    void onInteract();
}
