package model.items;

import com.googlecode.lanterna.TextColor;
import model.Player;

import java.util.Arrays;

public class RandomPotion extends Potion {
    public RandomPotion() {
        super(
                null, "Random Potion",
                Arrays.asList("A wasd", "Looking Potion"),
                TextColor.ANSI.BLUE
        );
    }

    @Override
    // EFFECTS: does nothing
    public void useItem(Player p) {}
}
