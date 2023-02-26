package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class DefensePotion extends Potion {
    private static final int DEFENSE_AMOUNT = 5;

    public DefensePotion() {
        super(
                "Defense Potion",
                Arrays.asList("Permanently Increases", "Defense by 5"),
                TextColor.ANSI.BLUE);
    }

    @Override
    // EFFECTS: Adds additional defense to player
    public void useItem(Player p) {
        p.addDefense(DEFENSE_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
