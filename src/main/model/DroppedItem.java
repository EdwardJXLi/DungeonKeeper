package model;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;
import org.json.JSONObject;
import persistence.Writable;

/*
 * Screen element that represents a dropped item on ground.
 */

public class DroppedItem implements ScreenElement, Writable {
    // Basic information about dropped item
    private final int posX;
    private final int posY;
    // Reference to item that is dropped, so player knows what to pick up
    private final Item item;

    // EFFECTS: Creates a generic dropped item icon on screen
    public DroppedItem(int posX, int posY, Item item) {
        this.posX = posX;
        this.posY = posY;
        this.item = item;
    }

    // EFFECTS: Returns JSON Representation of a Dropped Item
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("posX", posX);
        json.put("posY", posY);
        json.put("item", item.toJson());
        return json;
    }

    // Getters

    // EFFECTS: Returns the item represented by the dropped item tile
    public Item getItem() {
        return item;
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
        return '★';
    }

    @Override
    public TextColor getTextColor() {
        return TextColor.ANSI.WHITE;
    }

    @Override
    public TextColor getBackgroundColor() {
        return TextColor.ANSI.YELLOW;
    }

    @Override
    public SpriteID getSpriteID() {
        return item.getSpriteID();
    }
}
