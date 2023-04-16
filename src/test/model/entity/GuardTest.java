package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.enemies.Guard;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuardTest {
    Game game;
    Guard guard;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        guard = new Guard(game);
    }

    @Test
    public void testConstructor() {
        assertEquals("Guard", guard.getName());
        assertEquals('%', guard.getTextSprite());
        assertEquals(TextColor.ANSI.MAGENTA, guard.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, guard.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_GUARD, guard.getSpriteID());
        assertEquals(Guard.INITIAL_HEALTH, guard.getMaxHealth());
        assertEquals(Guard.INITIAL_HEALTH, guard.getHealth());
        assertEquals(Guard.INITIAL_ATTACK, guard.getAttack());
        assertEquals(Guard.INITIAL_DEFENSE, guard.getDefense());
        assertEquals(game, guard.getGame());
        assertFalse(guard.isStunned());
    }

    @Test
    public void testTickMovement() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(guard, 10, 10);
            guard.handleNextTick(0);
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
            game.getLevel().spawnEnemy(guard, 10, 10);
            guard.handleNextTick(Guard.TICKS_UNTIL_MOVEMENT - 1);
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
    public void testResetStunOnMovementAttempt() {
        guard.stun();
        guard.handleNextTick(Guard.TICKS_UNTIL_MOVEMENT - 1);
        // Should not reset, not movement
        assertTrue(guard.isStunned());
        // Movement Tick
        guard.handleNextTick(Guard.TICKS_UNTIL_MOVEMENT);
        // Should reset
        assertFalse(guard.isStunned());
        // Should not move, because it was stunned
    }

    @Test
    public void testOnDeath() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried out
        game.getLevel().spawnEnemy(guard, 10, 10);
        for (int i = 0; i < 1000; i++) {
            guard.onDeath();
            // Check if item dropped
            assertNotNull(game.getLevel().getDroppedItemAtLocation(10, 10));
        }
    }
}
