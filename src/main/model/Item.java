package model;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/*
 * Abstract class for all items.
 * Items have a name and a description.
 * Items can also be stored in a player inventory
 * Examples: Potions, Armors, Weapons
 */


public abstract class Item implements Renderable, Writable {
    // Item Constants
    private final String name;
    private final List<String> description;
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;
    private final SpriteID spriteID;

    // Creates a generic item with a sprite, name, description, and methods for when it is used
    public Item(
            String name, List<String> description, char textSprite,
            TextColor textColor, TextColor backgroundColor, SpriteID spriteID
    ) {
        this.name = name;
        this.description = description;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.spriteID = spriteID;
    }

    // EFFECTS: Abstract function for what happens when an item is used
    public abstract void useItem(Player p);

    // EFFECTS: Returns JSON Representation of an Item object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("__type__", this.getClass().getName());
        return json;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
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

    @Override
    public SpriteID getSpriteID() {
        return spriteID;
    }
}
