package model.game;

import model.Inventory;
import model.Item;
import model.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    public void setup() {
        inventory = new Inventory();
    }

    @Test
    public void testNoItemsInInventory() {
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.numItems());
        assertEquals(0, inventory.getInventoryItems().size());
    }

    @Test
    public void testSingleItemInInventory() {
        Item testItem = new HealingPotion();
        inventory.addItem(testItem);
        assertFalse(inventory.isEmpty());
        assertEquals(1, inventory.numItems());
        assertEquals(testItem, inventory.getInventoryItems().get(0));
        assertEquals(testItem, inventory.getItemAtIndex(0));
    }

    @Test
    public void testMultipleItemsInInventory() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        assertFalse(inventory.isEmpty());
        assertEquals(3, inventory.numItems());
        assertEquals(testItem2, inventory.getInventoryItems().get(1));
        assertEquals(testItem1, inventory.getItemAtIndex(0));
        assertEquals(testItem2, inventory.getItemAtIndex(1));
        assertEquals(testItem3, inventory.getItemAtIndex(2));
    }

    @Test
    public void testRemoveFirstItem() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem1);
        assertEquals(testItem2, inventory.getItemAtIndex(0));
        assertEquals(testItem3, inventory.getItemAtIndex(1));
    }

    @Test
    public void testRemoveMiddleItem() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem2);
        assertEquals(testItem1, inventory.getItemAtIndex(0));
        assertEquals(testItem3, inventory.getItemAtIndex(1));
    }

    @Test
    public void testRemoveLastItem() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem3);
        assertEquals(testItem1, inventory.getItemAtIndex(0));
        assertEquals(testItem2, inventory.getItemAtIndex(1));
    }

    @Test
    public void testRemoveMultipleItems() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem1);
        inventory.removeItem(testItem3);
        assertEquals(1, inventory.numItems());
        assertEquals(testItem2, inventory.getItemAtIndex(0));
    }

    @Test
    public void testRemoveAllItems() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem1);
        inventory.removeItem(testItem2);
        inventory.removeItem(testItem3);
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.numItems());
    }

    @Test
    public void testClearItems() {
        Item testItem1 = new HealingPotion();
        Item testItem2 = new DefensePotion();
        Item testItem3 = new StrengthPotion();
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        inventory.clearInventory();
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.numItems());
    }

    @Test
    public void testEquipArmor() {
        Armor armor = new GuardArmor(5);
        inventory.setEquippedArmor(armor);
        assertEquals(armor, inventory.getEquippedArmor());
    }

    @Test
    public void testEquipWeapon() {
        Weapon weapon = new GuardSword(8);
        inventory.setEquippedWeapon(weapon);
        assertEquals(weapon, inventory.getEquippedWeapon());
    }
}
