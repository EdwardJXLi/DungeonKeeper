package model.items;

import com.googlecode.lanterna.TextColor;

import java.util.List;

public class GuardSword extends Weapon {
    // EFFECTS: Creates a simple sword dropped by guards
    public GuardSword(int swordDamage) {
        super("Guard's Sword", List.of("Sharp Looking Sword"), TextColor.ANSI.YELLOW, swordDamage);
    }
}
