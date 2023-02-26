package model;

import com.googlecode.lanterna.TextColor;

import java.util.List;

public abstract class Item implements Renderable {
    private final String name;
    private final List<String> description;
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;

    public Item(
            String name, List<String> description, char textSprite,
                TextColor textColor, TextColor backgroundColor
    ) {
        this.name = name;
        this.description = description;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public abstract void useItem(Player p);

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
}
