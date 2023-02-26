package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class HealingPotion extends Potion {
    private static final int HEAL_AMOUNT = 20;

    public HealingPotion() {
        super("Healing Potion", Arrays.asList("Heals 20 Hearts"), TextColor.ANSI.RED);
    }

    @Override
    // EFFECTS: heals player
    public void useItem(Player p) {
        p.heal(HEAL_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
