package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class SuspiciousPotion extends Potion {
    public SuspiciousPotion() {
        super("Suspicious Potion", Arrays.asList("A Suspicious", "Looking Potion"), TextColor.ANSI.MAGENTA);
    }

    @Override
    // EFFECTS: does nothing
    public void useItem(Player p) {
    }
}
