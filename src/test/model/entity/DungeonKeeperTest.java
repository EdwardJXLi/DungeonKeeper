package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.enemies.DungeonKeeper;
import model.graphics.SpriteID;
import model.items.DungeonKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonKeeperTest {
    Game game;
    DungeonKeeper dungeonKeeper;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        dungeonKeeper = new DungeonKeeper(game);
    }

    @Test
    public void testConstructor() {
        assertEquals("Dungeon Keeper", dungeonKeeper.getName());
        assertEquals('%', dungeonKeeper.getTextSprite());
        assertEquals(TextColor.ANSI.YELLOW, dungeonKeeper.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, dungeonKeeper.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_DUNGEON_KEEPER, dungeonKeeper.getSpriteID());
        assertEquals(DungeonKeeper.INITIAL_HEALTH, dungeonKeeper.getMaxHealth());
        assertEquals(DungeonKeeper.INITIAL_HEALTH, dungeonKeeper.getHealth());
        assertEquals(DungeonKeeper.INITIAL_ATTACK, dungeonKeeper.getAttack());
        assertEquals(DungeonKeeper.INITIAL_DEFENSE, dungeonKeeper.getDefense());
        assertEquals(game, dungeonKeeper.getGame());
        assertFalse(dungeonKeeper.isStunned());
    }

    @Test
    public void testTickMovement() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried oyt
        for (int i = 0; i < 1000; i++) {
            game.getLevel().spawnEnemy(dungeonKeeper, 10, 10);
            dungeonKeeper.handleNextTick(0);
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
            game.getLevel().spawnEnemy(dungeonKeeper, 10, 10);
            dungeonKeeper.handleNextTick(DungeonKeeper.TICKS_UNTIL_MOVEMENT - 1);
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
        dungeonKeeper.stun();
        dungeonKeeper.handleNextTick(DungeonKeeper.TICKS_UNTIL_MOVEMENT - 1);
        // Should not reset, not movement
        assertTrue(dungeonKeeper.isStunned());
        // Movement Tick
        dungeonKeeper.handleNextTick(DungeonKeeper.TICKS_UNTIL_MOVEMENT);
        // Should reset
        assertFalse(dungeonKeeper.isStunned());
        // Should not move, because it was stunned
    }

    @Test
    public void testOnDeath() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried out
        game.getLevel().spawnEnemy(dungeonKeeper, 10, 10);
        dungeonKeeper.onDeath();
        // Check if dungeon key was dropped
        assertNotNull(game.getLevel().getDroppedItemAtLocation(10, 10));
        assertTrue(game.getLevel().getDroppedItemAtLocation(10, 10).getItem() instanceof DungeonKey);
    }
}
