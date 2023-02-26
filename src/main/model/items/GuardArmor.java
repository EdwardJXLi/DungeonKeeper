package model.items;

import com.googlecode.lanterna.TextColor;

import java.util.Arrays;

public class GuardArmor extends Armor {
    public GuardArmor(int armorDefense) {
        super("Guard's Armor", Arrays.asList("Sturdy Looking Armor"), TextColor.ANSI.YELLOW, armorDefense);
    }
}
