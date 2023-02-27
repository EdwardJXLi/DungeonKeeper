package model.items;

import com.googlecode.lanterna.TextColor;

import java.util.Arrays;

/*
 * Basic armor dropped by guards when killed
 */

public class GuardArmor extends Armor {
    // EFFECTS: Creates a simple armor dropped by guards
    public GuardArmor(int armorDefense) {
        super("Guard's Armor", Arrays.asList("Sturdy Looking Armor"), TextColor.ANSI.YELLOW, armorDefense);
    }
}
