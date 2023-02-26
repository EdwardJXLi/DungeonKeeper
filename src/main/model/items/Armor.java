package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Armor extends Item {
    private final int additionalDefense;

    public Armor(String name, List<String> description, TextColor armorColor, int additionalDefense) {
        super(name, description, '$', TextColor.ANSI.WHITE, armorColor);
        this.additionalDefense = additionalDefense;
    }

    @Override
    public List<String> getDescription() {
        // Create a new description with the defense amount added
        List<String> newDescription = new ArrayList<String>();
        for (String s: super.getDescription()) {
            newDescription.add(s);
        }
        newDescription.add(String.format("+%d Defense", getAdditionalDefense()));
        return newDescription;
    }

    public int getAdditionalDefense() {
        return additionalDefense;
    }

    @Override
    public void useItem(Player p) {
        p.equipArmor(this);
    }
}
