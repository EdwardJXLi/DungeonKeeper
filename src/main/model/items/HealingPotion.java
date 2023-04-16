package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;
import model.graphics.SpriteID;

import java.util.Arrays;
import java.util.Collections;

/*
 * Basic defense potion that increases the player health (up to max)
 */

public class HealingPotion extends Potion {
    // Health Potion Constants
    public static final int HEAL_AMOUNT = 20;

    // EFFECTS: Creates a simple potion that increases health
    public HealingPotion() {
        super(
                "Healing Potion", Collections.singletonList("Heals 20 Hearts"),
                TextColor.ANSI.RED, SpriteID.ITEM_HEALING_POTION
        );
    }

    // EFFECTS: heals player once used
    @Override
    public void useItem(Player p) {
        p.getGame().sendMessage("You used " + getName());
        p.heal(HEAL_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
