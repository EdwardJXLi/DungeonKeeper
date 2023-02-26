package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import org.w3c.dom.Text;

import java.util.List;

public abstract class Potion implements Item {
    private String modifier;
    private String name;
    private List<String> description;
    private TextColor potionColor;

    public Potion(String modifier, String name, List<String> description, TextColor potionColor) {
        this.modifier = modifier;
        this.name = name;
        this.description = description;
        this.potionColor = potionColor;
    }

    // EFFECTS: Handles Drinking Potion
    public abstract void drink(Player p);

    @Override
    public char getTextSprite() {
        return '▲';
    }

    @Override
    public TextColor getTextColor() {
        return TextColor.ANSI.WHITE;
    }

    @Override
    public TextColor getBackgroundColor() {
        return potionColor;
    }

    @Override
    // EFFECTS: Gets name of potion
    public String getName() {
        if (modifier != null) {
            return modifier + " " + name;
        } else {
            return name;
        }
    }

    @Override
    // EFFECTS: Gets description of potion
    public List<String> getDescription() {
        return description;
    }
}
