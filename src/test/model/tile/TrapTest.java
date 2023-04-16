package model.tile;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Player;
import model.Tile;
import model.enemies.Dummy;
import model.graphics.SpriteID;
import model.tiles.Trap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TrapTest {
    Tile tile;
    Game game;
    Player player;
    Enemy enemy;

    @BeforeEach
    public void setup() {
        tile = new Trap(19, 20);
        game = new Game(32, 24);
        player = new Player(game);
        enemy = new Dummy(game);
    }

    @Test
    public void testConstructor() {
        assertEquals(19, tile.getPosX());
        assertEquals(20, tile.getPosY());
        assertEquals('"', tile.getTextSprite());
        assertEquals(TextColor.ANSI.RED, tile.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, tile.getBackgroundColor());
        assertEquals(SpriteID.TILE_TRAP, tile.getSpriteID());
        assertEquals("Spike Trap", tile.getName());
        assertEquals("Ouch! That Hurts!", tile.getDescription().get(0));
        assertEquals("Standing on Spike Traps", tile.getDescription().get(1));
        assertEquals("deals 5 damage!", tile.getDescription().get(2));
        assertFalse(tile.isSolid());
    }

    @Test
    public void testStepOnTrap() {
        tile.onStep(player);
        assertEquals(Player.INITIAL_HEALTH - Trap.TRAP_DAMAGE, player.getHealth());
    }

    @Test
    public void testStepOnTrapWithSomeDefense() {
        player.addDefense(3);
        tile.onStep(player);
        assertEquals(Player.INITIAL_HEALTH - Trap.TRAP_DAMAGE + 3, player.getHealth());
    }

    @Test
    public void testStepOnTrapWithFullDefense() {
        player.addDefense(Trap.TRAP_DAMAGE + 1);
        tile.onStep(player);
        assertEquals(Player.INITIAL_HEALTH, player.getHealth());
    }

    @Test
    public void testStepOnTrapWithEnemy() {
        tile.onStep(enemy);
            assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
    }
}
