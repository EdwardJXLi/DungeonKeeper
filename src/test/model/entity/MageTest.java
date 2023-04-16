package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.enemies.Fire;
import model.enemies.Guard;
import model.enemies.Mage;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MageTest {
    Game game;
    Player player;
    Mage mage;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        game.getLevel().spawnPlayer(player);
        mage = new Mage(game);
    }

    @Test
    public void constructorTest() {
        assertEquals("Mage", mage.getName());
        assertEquals('M', mage.getTextSprite());
        assertEquals(TextColor.ANSI.BLACK, mage.getTextColor());
        assertEquals(TextColor.ANSI.GREEN, mage.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_MAGE, mage.getSpriteID());
        assertEquals(Mage.INITIAL_HEALTH, mage.getMaxHealth());
        assertEquals(Mage.INITIAL_HEALTH, mage.getHealth());
        assertEquals(Mage.INITIAL_ATTACK, mage.getAttack());
        assertEquals(Mage.INITIAL_DEFENSE, mage.getDefense());
        assertEquals(game, mage.getGame());
        assertFalse(mage.isStunned());
    }

    @Test
    public void testTickMovement() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(mage, 10, 10);
            mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
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
    public void testTickMovementWithPlayer() {
        // Loop through all possible positions for the mage to be in.
        // Keep the player at the same position in the middle of the map
        player.setCoordinate(game.getLevel().getSizeX() / 2, game.getLevel().getSizeY() / 2);

        // Loop through all possible mage positions
        for (int x = 0; x < game.getLevel().getSizeX(); x++) {
            for (int y = 0; y < game.getLevel().getSizeY(); y++) {
                // Spawn mage
                game.getLevel().spawnEnemy(mage, x, y);
                // Move mage
                mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
                // Check if it moved towards the player
                assertTrue(
                        game.getLevel().getEnemyAtLocation(x - 1, y) != null ||
                                game.getLevel().getEnemyAtLocation(x + 1, y) != null ||
                                game.getLevel().getEnemyAtLocation(x, y - 1) != null ||
                                game.getLevel().getEnemyAtLocation(x, y + 1) != null ||
                                game.getLevel().getEnemyAtLocation(x, y) != null
                );
            }
        }
    }

    @Test
    public void testNotMovementTick() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(mage, 10, 10);
            mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT - 1);
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
    public void testSpawnFireTick() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT * Mage.MOVES_UNTIL_FIRE_SPAWN);
        // Move mage out of the way
        player.setCoordinate(10, 12);
        mage.handleNextTick(1);
        // Should spawn fire
        assertTrue(game.getLevel().getEnemyAtLocation(10, 10) instanceof Fire);
    }

    @Test
    public void testNotFireTick() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT * Mage.MOVES_UNTIL_FIRE_SPAWN - 1);
        // Move mage out of the way
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        // Should spawn fire
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
    }

    @Test
    public void testResetStunOnMovementAttempt() {
        mage.stun();
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT - 1);
        // Should not reset, not movement
        assertTrue(mage.isStunned());
        // Movement Tick
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        // Should reset
        assertFalse(mage.isStunned());
        // Should not move, because it was stunned
    }

    @Test
    public void testOnDeath() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried out
        game.getLevel().spawnEnemy(mage, 10, 10);
        for (int i = 0; i < 1000; i++) {
            mage.onDeath();
            // Check if item dropped
            assertNotNull(game.getLevel().getDroppedItemAtLocation(10, 10));
        }
    }

    @Test
    public void testDontMoveOntopPlayer() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 10);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        assertNotNull(game.getLevel().getEnemyAtLocation(10, 10));
    }

    @Test
    public void testMoveLeftTowardsPlayer() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        // Test Move Left
        player.setCoordinate(8, 10);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(mage, game.getLevel().getEnemyAtLocation(9, 10));
    }

    @Test
    public void testMoveRightTowardsPlayer() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        // Test Move Right
        player.setCoordinate(12, 10);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(mage, game.getLevel().getEnemyAtLocation(11, 10));
    }

    @Test
    public void testMoveUpTowardsPlayer() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 8);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(mage, game.getLevel().getEnemyAtLocation(10, 9));
    }

    @Test
    public void testMoveDownTowardsPlayer() {
        game.getLevel().spawnEnemy(mage, 10, 10);
        // Test Move Down
        player.setCoordinate(10, 12);
        mage.handleNextTick(Mage.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(mage, game.getLevel().getEnemyAtLocation(10, 11));
    }
}
