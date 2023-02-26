package model;

import com.googlecode.lanterna.TextColor;

public abstract class Tile implements ScreenElement {
    private final int posX;
    private final int posY;
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;

    // EFFECTS: Creates a generic map tile
    public Tile(int posX, int posY, char textSprite, TextColor textColor, TextColor backgroundColor) {
        this.posX = posX;
        this.posY = posY;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    // EFFECTS: Returns if the object could be stood on
    public abstract boolean isSolid();

    // EFFECTS: Handles what happens when an entity steps on tile
    public abstract void onStep();

    //
    // GETTERS
    //

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
        return textSprite;
    }

    @Override
    public TextColor getTextColor() {
        return textColor;
    }

    @Override
    public TextColor getBackgroundColor() {
        return backgroundColor;
    }
}
