package model.items;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Basic weapon dropped by guards when killed
 */

public class VampireFangs extends Weapon {
    // EFFECTS: Creates a simple sword dropped by guards
    public VampireFangs(int swordDamage) {
        super(
                "Guard's Sword", Arrays.asList("Sharp Looking Sword"),
                TextColor.ANSI.YELLOW, SpriteID.ITEM_GUARD_SWORD,
                swordDamage
        );
    }
}
