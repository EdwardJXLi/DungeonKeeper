package model.item;

import com.googlecode.lanterna.TextColor;
import model.*;
import model.enemies.Dummy;
import model.items.HealingPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedItemTest {
    Game game;
    Player player;
    Item testItem1;
    Item testItem2;
    Item testItem3;
    DroppedItem di;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        testItem1 = new HealingPotion();
        testItem2 = new HealingPotion();
        testItem3 = new HealingPotion();
        di = new DroppedItem(1, 2, testItem1);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, di.getPosX());
        assertEquals(2, di.getPosY());
        assertEquals('★', di.getTextSprite());
        assertEquals(testItem1.getSpriteID(), di.getSpriteID());
        assertEquals(TextColor.ANSI.WHITE, di.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, di.getBackgroundColor());
    }

    @Test
    public void testNoDroppedItems() {
        assertNull(game.getLevel().getDroppedItemAtLocation(0, 0));
        assertNull(game.getLevel().getDroppedItemAtLocation(10, 20));
        assertNull(game.getLevel().getDroppedItemAtLocation(8, 2));
    }

    @Test
    public void testDroppedItem() {
        DroppedItem di = game.getLevel().dropItem(5, 5, testItem1);
        assertEquals(di, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertNull(game.getLevel().getDroppedItemAtLocation(4, 5));
        assertNull(game.getLevel().getDroppedItemAtLocation(5, 4));
        assertEquals(testItem1, game.getLevel().getDroppedItemAtLocation(5, 5).getItem());
        assertEquals(testItem1, di.getItem());
    }

    @Test
    public void testRemoveDroppedItem() {
        DroppedItem di = game.getLevel().dropItem(5, 5, testItem1);
        game.getLevel().removeDroppedItem(di);
        assertNull(game.getLevel().getDroppedItemAtLocation(5, 5));
    }

    @Test
    public void testMultipleDroppedItem() {
        // Drop Multiple Items
        DroppedItem di1 = game.getLevel().dropItem(5, 5, testItem1);
        DroppedItem di2 = game.getLevel().dropItem(2, 8, testItem2);
        DroppedItem di3 = game.getLevel().dropItem(4, 22, testItem3);
        // Check if the item matches the location
        assertEquals(di1, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertEquals(testItem1, game.getLevel().getDroppedItemAtLocation(5, 5).getItem());
        assertEquals(di2, game.getLevel().getDroppedItemAtLocation(2, 8));
        assertEquals(testItem2, game.getLevel().getDroppedItemAtLocation(2, 8).getItem());
        assertEquals(di3, game.getLevel().getDroppedItemAtLocation(4, 22));
        assertEquals(testItem3, game.getLevel().getDroppedItemAtLocation(4, 22).getItem());
        assertNull(game.getLevel().getDroppedItemAtLocation(0, 0));
        // Pick up item and check if correct item is picked up
        game.getLevel().removeDroppedItem(di2);
        assertEquals(di1, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertNull(game.getLevel().getDroppedItemAtLocation(2, 8));
        assertEquals(di3, game.getLevel().getDroppedItemAtLocation(4, 22));
    }

    @Test
    public void testMultipleDroppedItemsSameLocaiton() {
        DroppedItem di1 = game.getLevel().dropItem(5, 5, testItem1);
        DroppedItem di2 = game.getLevel().dropItem(5, 5, testItem2);
        DroppedItem di3 = game.getLevel().dropItem(5, 5, testItem3);
        // Check if item at location is the last one dropped
        assertEquals(di3, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertEquals(testItem3, game.getLevel().getDroppedItemAtLocation(5, 5).getItem());
        // Pick up item and check if next item is there
        game.getLevel().removeDroppedItem(di3);
        assertEquals(di2, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertEquals(testItem2, game.getLevel().getDroppedItemAtLocation(5, 5).getItem());
        // If the bottom one is somehow removes, next item on ground should still be second
        game.getLevel().removeDroppedItem(di3);
        assertEquals(di2, game.getLevel().getDroppedItemAtLocation(5, 5));
        assertEquals(testItem2, game.getLevel().getDroppedItemAtLocation(5, 5).getItem());
    }

    @Test
    public void testDropItemFromInventory() {
        // Setup player
        game.getLevel().spawnPlayer(player);
        assertTrue(player.getInventory().isEmpty());
        // Add item to inventory and drop it
        player.addItem(testItem1);
        player.dropItem(testItem1);
        assertTrue(player.getInventory().isEmpty());
        // Check if dropped item is same one that was in inventory
        DroppedItem di1 = game.getLevel().getDroppedItemAtLocation(Game.SPAWN_X, Game.SPAWN_Y);
        assertEquals(testItem1, di1.getItem());
    }

    @Test
    public void testPickupItemToInventory() {
        // Setup player
        game.getLevel().spawnPlayer(player);
        assertTrue(player.getInventory().isEmpty());
        // Drop an item at spawn
        DroppedItem di1 = game.getLevel().dropItem(Game.SPAWN_X, Game.SPAWN_Y, testItem1);
        // Pickup Item
        player.pickupItem(di1);
        assertFalse(player.getInventory().isEmpty());
        // Check is item in inventory is the same as one one dropped
        assertEquals(testItem1, player.getInventory().getItemAtIndex(0));
    }
}
