package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import model.graphics.SpriteID;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Abstract class for all equip-able armors.
 * Example: Guard's Armor
 */

public abstract class Armor extends Item {
    private final int additionalDefense;

    // EFFECTS: Creates an abstract armor class
    public Armor(
            String name, List<String> description,
            TextColor armorColor, SpriteID spriteID,
            int additionalDefense
    ) {
        super(name, description, '$', TextColor.ANSI.WHITE, armorColor, spriteID);
        this.additionalDefense = additionalDefense;
    }

    // EFFECTS: Overrides previous get description and adds defence value to the end
    @Override
    public List<String> getDescription() {
        // Create a new description with the defense amount added
        // Copy all of previous description into new description
        List<String> newDescription = new ArrayList<>(super.getDescription());
        newDescription.add(String.format("+%d Defense", getAdditionalDefense()));
        return newDescription;
    }

    // EFFECTS: Returns the amount of additional defense added by armor
    public int getAdditionalDefense() {
        return additionalDefense;
    }

    // EFFECTS: When used, player equips armor
    @Override
    public void useItem(Player p) {
        if (p.getArmor() == this) {
            p.getGame().sendMessage("You Unequipped " + getName());
            p.unequipArmor();
        } else {
            p.getGame().sendMessage("You Equipped " + getName());
            p.equipArmor(this);
        }
    }

    // EFFECTS: Returns JSON Representation of an Armor
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("additionalDefense", additionalDefense);
        return json;
    }
}
