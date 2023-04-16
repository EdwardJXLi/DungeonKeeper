package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.enemies.Whisper;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WhisperTest {
    Game game;
    Whisper whisper;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        whisper = new Whisper(game);
    }

    @Test
    public void testConstructor() {
        assertEquals("Whisper", whisper.getName());
        assertEquals('*', whisper.getTextSprite());
        assertEquals(TextColor.ANSI.CYAN, whisper.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, whisper.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_WHISPER, whisper.getSpriteID());
        assertEquals(Whisper.INITIAL_HEALTH, whisper.getMaxHealth());
        assertEquals(Whisper.INITIAL_HEALTH, whisper.getHealth());
        assertEquals(Whisper.INITIAL_ATTACK, whisper.getAttack());
        assertEquals(Whisper.INITIAL_DEFENSE, whisper.getDefense());
        assertEquals(game, whisper.getGame());
        assertFalse(whisper.isStunned());
    }

    @Test
    public void testTickMovement() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(whisper, 10, 10);
            whisper.handleNextTick(0);
            // Moved Away
            assertNull(game.getLevel().getEnemyAtLocation(10, 10));
            // Check if its within bounds
            assertTrue(
                    game.getLevel().getEnemyAtLocation(9, 10) != null ||
                    game.getLevel().getEnemyAtLocation(11, 10) != null ||
                    game.getLevel().getEnemyAtLocation(10, 9) != null ||
                    game.getLevel().getEnemyAtLocation(10, 11) != null
            );
        }
    }

    @Test
    public void testNotMovementTick() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(whisper, 10, 10);
            whisper.handleNextTick(Whisper.TICKS_UNTIL_MOVEMENT - 1);
            // Should not move
            assertNotNull(game.getLevel().getEnemyAtLocation(10, 10));
            assertFalse(
                    game.getLevel().getEnemyAtLocation(9, 10) != null ||
                            game.getLevel().getEnemyAtLocation(11, 10) != null ||
                            game.getLevel().getEnemyAtLocation(10, 9) != null ||
                            game.getLevel().getEnemyAtLocation(10, 11) != null
            );
        }
    }

    @Test
    public void testOnDeath() {
        // Nothing should happen on death
        game.getLevel().spawnEnemy(whisper, 10, 10);
        whisper.onDeath();
        assertNull(game.getLevel().getDroppedItemAtLocation(10, 10));
    }
}
