package model.items;

import com.googlecode.lanterna.TextColor;

import java.util.Arrays;

public class GuardSword extends Weapon {
    public GuardSword(int armorDefense) {
        super("Guard's Sword", Arrays.asList("Sharp Looking Sword"), TextColor.ANSI.YELLOW, armorDefense);
    }
}
