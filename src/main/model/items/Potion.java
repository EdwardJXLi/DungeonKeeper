package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;

import java.util.List;

public abstract class Potion extends Item {
    // EFFECTS: Creates a generic abstract class of a potion
    public Potion(String name, List<String> description, TextColor potionColor) {
        super(name, description, '▲', TextColor.ANSI.WHITE, potionColor);
    }
}
