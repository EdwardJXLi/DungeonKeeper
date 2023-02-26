package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon extends Item {
    private final int additionalAttack;

    public Weapon(String name, List<String> description, TextColor weaponColor, int additionalAttack) {
        super(name, description, '!', TextColor.ANSI.RED, weaponColor);
        this.additionalAttack = additionalAttack;
    }

    @Override
    public List<String> getDescription() {
        // Create a new description with the attack amount added
        List<String> newDescription = new ArrayList<String>();
        for (String s: super.getDescription()) {
            newDescription.add(s);
        }
        newDescription.add(String.format("+%d Attack", getAdditionalAttack()));
        return newDescription;
    }

    public int getAdditionalAttack() {
        return additionalAttack;
    }

    @Override
    public void useItem(Player p) {
        p.equipWeapon(this);
    }
}
