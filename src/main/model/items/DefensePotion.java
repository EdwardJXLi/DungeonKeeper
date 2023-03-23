package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Basic defense potion that permanently increases defense
 */

public class DefensePotion extends Potion {
    // Defense Potion Constants
    public static final int DEFENSE_AMOUNT = 5;

    // EFFECTS: Creates a simple potion that permanently increases defense
    public DefensePotion() {
        super(
                "Defense Potion",
                Arrays.asList("Permanently Increases", "Defense by 5"),
                TextColor.ANSI.BLUE, SpriteID.ITEM_DEFENSE_POTION);
    }

    // EFFECTS: Adds additional defense to player when used
    @Override
    public void useItem(Player p) {
        p.getGame().sendMessage("You used " + getName());
        p.addDefense(DEFENSE_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
