package model.items;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;

import java.util.Arrays;
import java.util.Collections;

/*
 * Basic armor dropped by guards when killed
 */

public class GuardArmor extends Armor {
    // EFFECTS: Creates a simple armor dropped by guards
    public GuardArmor(int armorDefense) {
        super(
                "Guard's Armor", Collections.singletonList("Sturdy Looking Armor"),
                '$', TextColor.ANSI.WHITE, TextColor.ANSI.YELLOW, SpriteID.ITEM_GUARD_ARMOR,
                armorDefense
        );
    }
}
