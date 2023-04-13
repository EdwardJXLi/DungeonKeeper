package model;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;
import org.json.JSONObject;
import org.w3c.dom.Text;
import persistence.Writable;

import java.util.List;

/*
 * Abstract class for all Decorations.
 * All decorations are non-solid, and non-intractable.
 * They also do not get saved, as they are procedurally generated.
 * Finally, they do not have ascii sprites.
 * Examples: Wall, Spike
 */

public abstract class Decoration implements ScreenElement {
    // Generic Tile Elements
    private final int posX;
    private final int posY;
    private final SpriteID spriteID;
    private final String name;

    // EFFECTS: Creates a generic map tile
    public Decoration(
            int posX, int posY, SpriteID spriteID, String name
    ) {
        this.posX = posX;
        this.posY = posY;
        this.spriteID = spriteID;
        this.name = name;
    }

    //
    // Getters and Setters
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
        return ' ';
    }

    @Override
    public TextColor getTextColor() {
        return TextColor.ANSI.DEFAULT;
    }

    @Override
    public TextColor getBackgroundColor() {
        return TextColor.ANSI.DEFAULT;
    }

    @Override
    public SpriteID getSpriteID() {
        return spriteID;
    }

    public String getName() {
        return name;
    }
}
