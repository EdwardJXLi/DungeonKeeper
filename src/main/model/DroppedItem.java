package model;

import com.googlecode.lanterna.TextColor;

public class DroppedItem implements ScreenElement {
    private int posX;
    private int posY;
    private Item item;

    // EFFECTS: Creates a generic dropped item icon on screen
    public DroppedItem(int posX, int posY, Item item) {
        this.posX = posX;
        this.posY = posY;
        this.item = item;
    }

    //
    // Getters and Setters
    //

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Item getItem() {
        return item;
    }

    public char getTextSprite() {
        return '★';
    }

    public TextColor getTextColor() {
        return TextColor.ANSI.WHITE;
    }

    public TextColor getBackgroundColor() {
        return TextColor.ANSI.YELLOW;
    }
}
