package model;

import com.googlecode.lanterna.TextColor;

import java.util.List;

public abstract class Tile implements ScreenElement {
    private final int posX;
    private final int posY;
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;
    private final String name;
    private final List<String> description;

    // EFFECTS: Creates a generic map tile
    public Tile(
            int posX, int posY, char textSprite,
            TextColor textColor, TextColor backgroundColor,
            String name, List<String> description
    ) {
        this.posX = posX;
        this.posY = posY;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.name = name;
        this.description = description;
    }

    // EFFECTS: Returns if the object could be stood on
    public abstract boolean isSolid();

    // MODIFIES: this, e
    // EFFECTS: Handles what happens when an entity steps on tile
    public abstract void onStep(Entity e);

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

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }
}