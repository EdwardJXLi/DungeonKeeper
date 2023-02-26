package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.List;

public class HealingPotion extends Potion {
    // Health Potion Constants
    public static final int HEAL_AMOUNT = 20;

    // EFFECTS: Creates a simple potion that increases health
    public HealingPotion() {
        super("Healing Potion", List.of("Heals 20 Hearts"), TextColor.ANSI.RED);
    }

    // EFFECTS: heals player once used
    @Override
    public void useItem(Player p) {
        p.heal(HEAL_AMOUNT);
        // Remove potion after use
        p.removeItem(this);
    }
}
