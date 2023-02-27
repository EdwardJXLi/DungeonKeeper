package model.game;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
    }

    @Test
    public void testConstructor() {
        assertEquals(32, game.getSizeX());
        assertEquals(24, game.getSizeY());
        assertTrue(game.isGameRunning());
    }

    @Test
    public void testInit() {
        game.initGame();
        // Intellij suggested this
        assertNotNull(game.getLevel());
        assertEquals(32, game.getLevel().getSizeX());
        assertEquals(24, game.getLevel().getSizeY());
        assertNotNull(game.getPlayer());
    }

    @Test
    public void testNextTick() {
        game.initGame();
        // Kill one enemy, and check if next tick removes enemy
        assertEquals(10, game.getLevel().getEnemies().size());
        game.getLevel().getEnemies().get(0).damage(9999);
        game.handleNextTick(0);
        assertEquals(9, game.getLevel().getEnemies().size());
    }

    @Test
    public void gameOverTest() {
        game.gameOver();
        assertFalse(game.isGameRunning());
    }
}
