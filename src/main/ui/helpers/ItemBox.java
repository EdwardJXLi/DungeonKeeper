package ui.helpers;

import model.Item;

public class ItemBox {
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;
    Item item;

    public ItemBox(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public ItemBox(int posX, int posY, int width, int height, Item item) {
        this(posX, posY, width, height);
        this.item = item;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Item getItem() {
        return item;
    }
}
