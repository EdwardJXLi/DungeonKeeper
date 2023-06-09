package model.item;

import model.Game;
import model.Player;
import model.items.Armor;
import model.items.GuardArmor;
import model.items.GuardSword;
import model.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {
    Game game;
    Player player;
    Armor testArmor1;
    Armor testArmor2;
    Weapon testWeapon1;
    Weapon testWeapon2;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        player = new Player(game);
        testArmor1 = new GuardArmor(1);
        testArmor2 = new GuardArmor(2);
        testWeapon1 = new GuardSword(10);
        testWeapon2 = new GuardSword(20);

    }

    @Test
    public void testInitialArmorWeaponState() {
        assertNull(player.getArmor());
        assertNull(player.getWeapon());
    }

    @Test
    public void testEquipArmor() {
        // Add armor to inventory and equip
        player.addItem(testArmor1);
        player.equipArmor(testArmor1);
        // Check if armor is equipped, inventory is empty, and armor in armor slot
        assertTrue(player.getInventory().isEmpty());
        assertEquals(testArmor1, player.getArmor());
        // Check if defense is increased
        assertEquals(Player.INITIAL_DEFENSE + 1, player.getDefense());
    }

    @Test
    public void testEquipArmorMultipleTimes() {
        // Add armors to inventory
        player.addItem(testArmor1);
        player.addItem(testArmor2);
        // Equip the first armor
        player.equipArmor(testArmor1);
        // Check if armor is equipped, armor in armor slot, and remaining armor in inventory
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testArmor2, player.getInventory().getItemAtIndex(0));
        assertEquals(testArmor1, player.getArmor());
        // Check if defense is increased by testArmor1's value
        assertEquals(Player.INITIAL_DEFENSE + 1, player.getDefense());
        // Now, equip second armor
        player.equipArmor(testArmor2);
        // Check if original armor is back in inventory, and new armor in armor slot
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testArmor1, player.getInventory().getItemAtIndex(0));
        assertEquals(testArmor2, player.getArmor());
        // Check if defense is increased by testArmor2's value
        assertEquals(Player.INITIAL_DEFENSE + 2, player.getDefense());
    }

    @Test
    public void testUnequipArmor() {
        // Add armor to inventory and equip
        player.addItem(testArmor1);
        player.equipArmor(testArmor1);
        // Check if armor is equipped, inventory is empty, and armor in armor slot
        assertTrue(player.getInventory().isEmpty());
        assertEquals(testArmor1, player.getArmor());
        // Unequip Armor
        player.unequipArmor();
        // Check if armor is unequipped, armor in inventory, and armor slot is empty
        assertFalse(player.getInventory().isEmpty());
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testArmor1, player.getInventory().getItemAtIndex(0));
        assertNull(player.getArmor());
        // Check if defense remains the same
        assertEquals(Player.INITIAL_DEFENSE, player.getDefense());
    }

    @Test
    public void testUnequipArmorWhenNoArmorEquipped() {
        // "Unequip" armor when no armor is equipped
        player.unequipArmor();
        // Check if inventory remains empty, armor slot remains empty, and defense remains the same
        assertTrue(player.getInventory().isEmpty());
        assertNull(player.getArmor());
        assertEquals(Player.INITIAL_DEFENSE, player.getDefense());
    }

    @Test
    public void testEquipWeapon() {
        // Add weapon to inventory and equip
        player.addItem(testWeapon1);
        player.equipWeapon(testWeapon1);
        // Check if weapon is equipped, inventory is empty, and weapon in weapon slot
        assertTrue(player.getInventory().isEmpty());
        assertEquals(testWeapon1, player.getWeapon());
        // Check if defense is increased
        assertEquals(Player.INITIAL_ATTACK + 10, player.getAttack());
    }

    @Test
    public void testEquipWeaponMultipleTimes() {
        // Add weapons to inventory
        player.addItem(testWeapon1);
        player.addItem(testWeapon2);
        // Equip the first weapon
        player.equipWeapon(testWeapon1);
        // Check if weapon is equipped, weapon in weapon slot, and remaining armor in inventory
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testWeapon2, player.getInventory().getItemAtIndex(0));
        assertEquals(testWeapon1, player.getWeapon());
        // Check if defense is increased by testWeapon1's value
        assertEquals(Player.INITIAL_ATTACK + 10, player.getAttack());
        // Now, equip second weapon
        player.equipWeapon(testWeapon2);
        // Check if original weapon is back in inventory, and new weapon in armor slot
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testWeapon1, player.getInventory().getItemAtIndex(0));
        assertEquals(testWeapon2, player.getWeapon());
        // Check if defense is increased by testWeapon2's value
        assertEquals(Player.INITIAL_ATTACK + 20, player.getAttack());
    }

    @Test
    public void testUnequipWeapon() {
        // Add weapon to inventory and equip
        player.addItem(testWeapon1);
        player.equipWeapon(testWeapon1);
        // Check if weapon is equipped, inventory is empty, and weapon in weapon slot
        assertTrue(player.getInventory().isEmpty());
        assertEquals(testWeapon1, player.getWeapon());
        // Unequip Weapon
        player.unequipWeapon();
        // Check if weapon is unequipped, weapon in inventory, and weapon slot is empty
        assertFalse(player.getInventory().isEmpty());
        assertEquals(1, player.getInventory().numItems());
        assertEquals(testWeapon1, player.getInventory().getItemAtIndex(0));
        assertNull(player.getWeapon());
        // Check if defense remains the same
        assertEquals(Player.INITIAL_ATTACK, player.getAttack());
    }

    @Test
    public void testUnequipWeaponWhenNoWeaponEquipped() {
        // "Unequip" weapon when no weapon is equipped
        player.unequipWeapon();
        // Check if inventory remains empty, weapon slot remains empty, and attack remains the same
        assertTrue(player.getInventory().isEmpty());
        assertNull(player.getWeapon());
        assertEquals(Player.INITIAL_ATTACK, player.getAttack());
    }
}
