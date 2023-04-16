package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.enemies.Fire;
import model.enemies.Vampire;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VampireTest {
    Game game;
    Player player;
    Vampire vampire;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        game.getLevel().spawnPlayer(player);
        vampire = new Vampire(game);
    }

    @Test
    public void constructorTest() {
        assertEquals("Vampire", vampire.getName());
        assertEquals('V', vampire.getTextSprite());
        assertEquals(TextColor.ANSI.BLACK, vampire.getTextColor());
        assertEquals(TextColor.ANSI.RED, vampire.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_VAMPIRE, vampire.getSpriteID());
        assertEquals(Vampire.INITIAL_HEALTH, vampire.getMaxHealth());
        assertEquals(Vampire.INITIAL_HEALTH, vampire.getHealth());
        assertEquals(Vampire.INITIAL_ATTACK, vampire.getAttack());
        assertEquals(Vampire.INITIAL_DEFENSE, vampire.getDefense());
        assertEquals(game, vampire.getGame());
        assertFalse(vampire.isStunned());
    }

    @Test
    public void testTickMovement() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(vampire, 10, 10);
            vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
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
    public void testTickMovementWithPlayerFar() {
        //  Move player to a far away location
        game.getPlayer().setCoordinate(50, 50);
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(vampire, 10, 10);
            vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
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
            game.getLevel().spawnEnemy(vampire, 10, 10);
            vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT - 1);
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
        vampire.stun();
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT - 1);
        // Should not reset, not movement
        assertTrue(vampire.isStunned());
        // Movement Tick
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        // Should reset
        assertFalse(vampire.isStunned());
        // Should not move, because it was stunned
    }

    @Test
    public void testOnDeath() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried out
        game.getLevel().spawnEnemy(vampire, 10, 10);
        for (int i = 0; i < 1000; i++) {
            vampire.onDeath();
            // Check if item dropped
            assertNotNull(game.getLevel().getDroppedItemAtLocation(10, 10));
        }
    }

    @Test
    public void testDontMoveOntopPlayer() {
        game.getLevel().spawnEnemy(vampire, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 10);
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        assertNotNull(game.getLevel().getEnemyAtLocation(10, 10));
    }

    @Test
    public void testMoveLeftTowardsPlayer() {
        game.getLevel().spawnEnemy(vampire, 10, 10);
        // Test Move Left
        player.setCoordinate(8, 10);
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(vampire, game.getLevel().getEnemyAtLocation(9, 10));
    }

    @Test
    public void testMoveRightTowardsPlayer() {
        game.getLevel().spawnEnemy(vampire, 10, 10);
        // Test Move Right
        player.setCoordinate(12, 10);
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(vampire, game.getLevel().getEnemyAtLocation(11, 10));
    }

    @Test
    public void testMoveUpTowardsPlayer() {
        game.getLevel().spawnEnemy(vampire, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 8);
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(vampire, game.getLevel().getEnemyAtLocation(10, 9));
    }

    @Test
    public void testMoveDownTowardsPlayer() {
        game.getLevel().spawnEnemy(vampire, 10, 10);
        // Test Move Down
        player.setCoordinate(10, 12);
        vampire.handleNextTick(Vampire.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(vampire, game.getLevel().getEnemyAtLocation(10, 11));
    }
}
