package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class StrengthPotion extends Potion {
    private static final int ATTACK_AMOUNT = 10;

    public StrengthPotion() {
        super(
                "Strength Potion",
                Arrays.asList("Permanently Increases", "Attack by 10"),
                TextColor.ANSI.MAGENTA);
    }

    @Override
    // EFFECTS: Adds additional attack to player
    public void useItem(Player p) {
        p.addAttack(ATTACK_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
