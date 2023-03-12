package model;

import model.items.Armor;
import model.items.Weapon;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements Writable {
    private List<Item> inventoryItems;
    private Armor equippedArmor;
    private Weapon equippedWeapon;

    // EFFECTS: Creates an empty inventory for a player to use
    public Inventory() {
        inventoryItems = new ArrayList<>();
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a pre-filled inventory
    public Inventory(List<Item> inventoryItems, Armor equippedArmor, Weapon equippedWeapon) {
        this.inventoryItems = inventoryItems;
        this.equippedArmor = equippedArmor;
        this. equippedWeapon = equippedWeapon;
    }

    // MODIFIES: this
    // EFFECTS: Adds item to inventory
    public void addItem(Item item) {
        inventoryItems.add(item);
    }

    // REQUIRES: Number of items in inventory must be greater than i
    // EFFECTS: Returns item at location i in inventory
    public Item getItemAtIndex(int index) {
        return inventoryItems.get(index);
    }

    // MODIFIES: this
    // EFFECTS: Removes item from inventory
    public void removeItem(Item item) {
        inventoryItems.removeIf(i -> i == item);
    }

    // EFFECTS: Returns true if there are no items in inventory
    public boolean isEmpty() {
        return inventoryItems.isEmpty();
    }

    // EFFECTS: Returns the number of items in inventory
    public int numItems() {
        return inventoryItems.size();
    }

    // EFFECTS: Returns JSON Representation of a player Inventory object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inventory", inventoryItemsToJson());
        if (equippedArmor != null) {
            json.put("equippedArmor", equippedArmor.toJson());
        }
        if (equippedArmor != null) {
            json.put("equippedWeapon", equippedWeapon.toJson());
        }
        return json;
    }

    // EFFECTS: Gets list of inventory items as serializable object
    public JSONArray inventoryItemsToJson() {
        JSONArray array = new JSONArray();
        for (Item i : inventoryItems) {
            array.put(i.toJson());
        }
        return array;
    }

    // MODIFIES: this
    // EFFECTS: Clears the inventory
    public void clearInventory() {
        inventoryItems.clear();
    }

    public List<Item> getInventoryItems() {
        return inventoryItems;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedArmor(Armor equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }
}
