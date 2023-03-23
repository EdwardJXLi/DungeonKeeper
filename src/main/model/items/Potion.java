package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.graphics.SpriteID;

import java.util.List;

/*
 * Abstract class for all drinkable potions
 * Example: Healing Potion, Defense Potion, Strength Potion
 */

public abstract class Potion extends Item {
    // EFFECTS: Creates a generic abstract class of a potion
    public Potion(
            String name, List<String> description,
            TextColor potionColor, SpriteID spriteID
    ) {
        super(
                name, description,
                '▲', TextColor.ANSI.WHITE, potionColor, spriteID
        );
    }
}
