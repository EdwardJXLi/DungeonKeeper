package model.tile;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.enemies.Dummy;
import model.graphics.SpriteID;
import model.items.DungeonKey;
import model.tiles.DungeonExit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonExitTest {
    DungeonExit tile;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        tile = new DungeonExit(0, 0);
        game = new Game(32, 24);
        player = new Player(game);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tile.getPosX());
        assertEquals(0, tile.getPosY());
        assertEquals('O', tile.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, tile.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, tile.getBackgroundColor());
        assertEquals(SpriteID.TILE_EXIT, tile.getSpriteID());
        assertEquals("Dungeon Exit", tile.getName());
        assertEquals("Get a dungeon key", tile.getDescription().get(0));
        assertEquals("unlock this door", tile.getDescription().get(1));
        assertEquals("to win the game.", tile.getDescription().get(2));
        assertFalse(tile.isSolid());
    }

    @Test
    public void testStepOnDungeonExitWithNoKey() {
        tile.onStep(player);
        assertEquals("You need a dungeon key to unlock this door.", game.getLastMessages(2).get(0));
        assertEquals("Kill the dungeon keeper to get one.", game.getLastMessages(2).get(1));
    }

    @Test
    public void testStepOnDungeonExitWithKey() {
        player.addItem(new DungeonKey());
        tile.onStep(player);
        assertTrue(game.isGameWon());
    }

    @Test
    public void testEnemyStepOnDungeonExit() {
        tile.onStep(new Dummy(game));
        assertEquals(0, game.getLastMessages(1).size());
        assertFalse(game.isGameWon());
    }
}
