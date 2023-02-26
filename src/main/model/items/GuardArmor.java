package model.items;

import com.googlecode.lanterna.TextColor;

import java.util.List;

public class GuardArmor extends Armor {
    // EFFECTS: Creates a simple armor dropped by guards
    public GuardArmor(int armorDefense) {
        super("Guard's Armor", List.of("Sturdy Looking Armor"), TextColor.ANSI.YELLOW, armorDefense);
    }
}
