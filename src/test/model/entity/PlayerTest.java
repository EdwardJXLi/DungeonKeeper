package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.enemies.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
    }

    @Test
    public void constructorTest() {
        assertEquals(game, player.getGame());
        assertEquals('@', player.getTextSprite());
        assertEquals(TextColor.ANSI.YELLOW, player.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, player.getBackgroundColor());
        assertEquals("Player", player.getName());
        assertEquals(Player.INITIAL_HEALTH, player.getHealth());
        assertEquals(Player.INITIAL_HEALTH, player.getMaxHealth());
        assertEquals(Player.INITIAL_DEFENSE, player.getDefense());
        assertEquals(Player.INITIAL_ATTACK, player.getAttack());
    }

    @Test
    public void testInitPlayer() {
        player.initPlayer();
        // Check Inventory
        assertEquals(5, player.getInventory().size());
        assertEquals("Healing Potion", player.getInventory().get(0).getName());
        assertEquals("Healing Potion", player.getInventory().get(1).getName());
        assertEquals("Healing Potion", player.getInventory().get(2).getName());
        assertEquals("Defense Potion", player.getInventory().get(3).getName());
        assertEquals("Strength Potion", player.getInventory().get(4).getName());
    }

    @Test
    public void testPlayerNearDeath() {
        player.damage(player.getHealth() - 1);
        assertFalse(player.isDead());
        assertTrue(player.getGame().isGameRunning());
    }

    @Test
    public void testPlayerDeath() {
        player.damage(player.getHealth());
        assertTrue(player.isDead());
        assertFalse(player.getGame().isGameRunning());
    }

    @Test
    public void testPlayerOverkillDeath() {
        player.damage(999999);
        assertTrue(player.isDead());
        assertFalse(player.getGame().isGameRunning());
    }
}
