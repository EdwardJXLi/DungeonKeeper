package model.game;

import model.Game;
import model.Item;
import model.Player;
import model.items.HealingPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        game.getLevel().spawnPlayer(player);
    }

    @Test
    public void emptyInventoryTest() {
        assertTrue(player.getInventory().isEmpty());
        assertEquals(0, player.getInventory().size());
    }

    @Test
    public void testAddItemToInventory() {
        Item item = new HealingPotion();
        assertTrue(player.getInventory().isEmpty());
        player.addItem(item);
        assertFalse(player.getInventory().isEmpty());
        assertEquals(1, player.getInventory().size());
        assertEquals(item, player.getInventory().get(0));
    }

    @Test
    public void testAddMultipleItemsToInventory() {
        Item item1 = new HealingPotion();
        Item item2 = new HealingPotion();
        Item item3 = new HealingPotion();
        assertTrue(player.getInventory().isEmpty());
        player.addItem(item1);
        player.addItem(item2);
        player.addItem(item3);
        assertFalse(player.getInventory().isEmpty());
        assertEquals(3, player.getInventory().size());
        assertEquals(item1, player.getInventory().get(0));
        assertEquals(item2, player.getInventory().get(1));
        assertEquals(item3, player.getInventory().get(2));
    }

    @Test
    public void testRemoveItemFromInventory() {
        Item item = new HealingPotion();
        player.addItem(item);
        assertFalse(player.getInventory().isEmpty());
        player.removeItem(item);
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    public void testRemoveMultipleItemsFromInventory() {
        Item item1 = new HealingPotion();
        Item item2 = new HealingPotion();
        Item item3 = new HealingPotion();
        player.addItem(item1);
        player.addItem(item2);
        player.addItem(item3);
        assertEquals(3, player.getInventory().size());
        player.removeItem(item1);
        player.removeItem(item3);
        assertEquals(1, player.getInventory().size());
        assertEquals(item2, player.getInventory().get(0));
    }
}
