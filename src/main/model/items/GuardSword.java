package model.items;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;

import java.util.Arrays;
import java.util.Collections;

/*
 * Basic weapon dropped by guards when killed
 */

public class GuardSword extends Weapon {
    // EFFECTS: Creates a simple sword dropped by guards
    public GuardSword(int swordDamage) {
        super(
                "Guard's Sword", Collections.singletonList("Sharp Looking Sword"),
                '!', TextColor.ANSI.RED, TextColor.ANSI.YELLOW, SpriteID.ITEM_GUARD_SWORD,
                swordDamage
        );
    }
}
