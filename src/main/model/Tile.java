package model;

import com.googlecode.lanterna.TextColor;
import org.w3c.dom.Text;

public abstract class Tile implements ScreenElement{
    private int posX;
    private int posY;
    private char textSprite;
    private TextColor textColor;
    private TextColor backgroundColor;

    public Tile(int posX, int posY, char textSprite, TextColor textColor, TextColor backgroundColor) {
        this.posX = posX;
        this.posY = posY;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
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

    public abstract boolean isSolid();

    public abstract void onStep();

    public abstract void onInteract();
}
