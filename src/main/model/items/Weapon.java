package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import model.graphics.SpriteID;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Abstract class for all equip-able weapons.
 * Example: Guard's Sword
 */

public abstract class Weapon extends Item {
    private final int additionalAttack;

    public Weapon(
            String name, List<String> description, char textSprite,
            TextColor textColor, TextColor backgroundColor, SpriteID spriteID,
            int additionalAttack
    ) {
        super(
                name, description, textSprite,
                textColor, backgroundColor, spriteID
        );
        this.additionalAttack = additionalAttack;
    }

    // EFFECTS: Overrides previous get description and adds attack value to the end
    @Override
    public List<String> getDescription() {
        // Create a new description with the attack amount added
        // Copy all of previous description into new description
        List<String> newDescription = new ArrayList<>(super.getDescription());
        newDescription.add(String.format("+%d Attack", getAdditionalAttack()));
        return newDescription;
    }

    // EFFECTS: Returns the amount of additional attack added by weapon
    public int getAdditionalAttack() {
        return additionalAttack;
    }

    // EFFECTS: When used, player equips weapon
    @Override
    public void useItem(Player p) {
        if (p.getWeapon() == this) {
            p.getGame().sendMessage("You Unequipped " + getName());
            p.unequipWeapon();
        } else {
            p.getGame().sendMessage("You Equipped " + getName());
            p.equipWeapon(this);
        }
    }

    // EFFECTS: Returns JSON Representation of a Weapon
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("additionalAttack", additionalAttack);
        return json;
    }
}
