package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class StrengthPotion extends Potion {
    // Strength Potion Constants
    public static final int ATTACK_AMOUNT = 10;

    // EFFECTS: Creates a simple potion that permanently increases strength
    public StrengthPotion() {
        super(
                "Strength Potion",
                Arrays.asList("Permanently Increases", "Attack by 10"),
                TextColor.ANSI.MAGENTA);
    }

    // EFFECTS: Adds additional attack to player once used
    @Override
    public void useItem(Player p) {
        p.addAttack(ATTACK_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
