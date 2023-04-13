package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Basic defense potion that permanently increases defense
 */

public class Ectoplasm extends Item {
    // EFFECTS: Creates soul fire that only damages enemies
    public Ectoplasm() {
        super(
                "Ectoplasm",
                Arrays.asList("Spawns Soul Fire", "That Only Damages", "Enemies"),
                '*', TextColor.ANSI.CYAN, TextColor.ANSI.DEFAULT, SpriteID.ITEM_ECTOPLASM);
    }

    // MODIFIES: p
    // EFFECTS: Spawns a soul fire that only damages enemies
    @Override
    public void useItem(Player p) {
        p.getGame().sendMessage("TODO UNFINISHED");
        // TODO: UNFINISHED
    }
}
