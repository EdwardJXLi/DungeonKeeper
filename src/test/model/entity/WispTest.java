package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.enemies.Wisp;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WispTest {
    Game game;
    Player player;
    Wisp wisp;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        game.getLevel().spawnPlayer(player);
        wisp = new Wisp(game);
    }

    @Test
    public void constructorTest() {
        assertEquals("Wisp", wisp.getName());
        assertEquals('*', wisp.getTextSprite());
        assertEquals(TextColor.ANSI.BLUE, wisp.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, wisp.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_WISP, wisp.getSpriteID());
        assertEquals(Wisp.INITIAL_HEALTH, wisp.getMaxHealth());
        assertEquals(Wisp.INITIAL_HEALTH, wisp.getHealth());
        assertEquals(Wisp.INITIAL_ATTACK, wisp.getAttack());
        assertEquals(Wisp.INITIAL_DEFENSE, wisp.getDefense());
        assertEquals(game, wisp.getGame());
        assertFalse(wisp.isStunned());
    }

    @Test
    public void testResetStunOnMovementAttempt() {
        wisp.stun();
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT - 1);
        // Should not reset, not movement
        assertTrue(wisp.isStunned());
        // Movement Tick
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        // Should reset
        assertFalse(wisp.isStunned());
        // Should not move, because it was stunned
    }

    @Test
    public void testOnDeath() {
        // Since movement is random, we try this 1000 ties so that
        // Every possibility gets tried out
        game.getLevel().spawnEnemy(wisp, 10, 10);
        wisp.onDeath();
        // Check if item dropped
        assertNotNull(game.getLevel().getDroppedItemAtLocation(10, 10));
        // Check if four whispers spawned in a cross formation
        assertNotNull(game.getLevel().getEnemyAtLocation(9, 10));
        assertNotNull(game.getLevel().getEnemyAtLocation(11, 10));
        assertNotNull(game.getLevel().getEnemyAtLocation(10, 9));
        assertNotNull(game.getLevel().getEnemyAtLocation(10, 11));

    }

    @Test
    public void testDontMoveOntopPlayer() {
        game.getLevel().spawnEnemy(wisp, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 10);
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        assertNotNull(game.getLevel().getEnemyAtLocation(10, 10));
    }

    @Test
    public void testMoveLeftTowardsPlayer() {
        game.getLevel().spawnEnemy(wisp, 10, 10);
        // Test Move Left
        player.setCoordinate(8, 10);
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(wisp, game.getLevel().getEnemyAtLocation(9, 10));
    }

    @Test
    public void testMoveRightTowardsPlayer() {
        game.getLevel().spawnEnemy(wisp, 10, 10);
        // Test Move Right
        player.setCoordinate(12, 10);
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(wisp, game.getLevel().getEnemyAtLocation(11, 10));
    }

    @Test
    public void testMoveUpTowardsPlayer() {
        game.getLevel().spawnEnemy(wisp, 10, 10);
        // Test Move Up
        player.setCoordinate(10, 8);
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(wisp, game.getLevel().getEnemyAtLocation(10, 9));
    }

    @Test
    public void testMoveDownTowardsPlayer() {
        game.getLevel().spawnEnemy(wisp, 10, 10);
        // Test Move Down
        player.setCoordinate(10, 12);
        wisp.handleNextTick(Wisp.TICKS_UNTIL_MOVEMENT);
        assertNull(game.getLevel().getEnemyAtLocation(10, 10));
        assertEquals(wisp, game.getLevel().getEnemyAtLocation(10, 11));
    }
}
