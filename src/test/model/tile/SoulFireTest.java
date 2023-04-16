package model.tile;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Player;
import model.enemies.Dummy;
import model.graphics.SpriteID;
import model.tiles.SoulFire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SoulFireTest {
    SoulFire tile;
    Game game;
    Player player;
    Enemy enemy;

    @BeforeEach
    public void setup() {
        tile = new SoulFire(0, 0);
        game = new Game(32, 24);
        player = new Player(game);
        enemy = new Dummy(game);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tile.getPosX());
        assertEquals(0, tile.getPosY());
        assertEquals('#', tile.getTextSprite());
        assertEquals(TextColor.ANSI.CYAN, tile.getTextColor());
        assertEquals(TextColor.ANSI.BLUE, tile.getBackgroundColor());
        assertEquals(SpriteID.SOULFIRE, tile.getSpriteID());
        assertEquals("Soul Fire", tile.getName());
        assertEquals("Deals 35 damage to", tile.getDescription().get(0));
        assertEquals("ONLY enemies", tile.getDescription().get(1));
        assertFalse(tile.isSolid());
    }

    @Test
    public void testStepOnSoulFire() {
        tile.onStep(player);
        assertEquals(Player.INITIAL_HEALTH, player.getHealth());
    }

    @Test
    public void testStepOnTrapWithEnemy() {
        tile.onStep(enemy);
        assertEquals(Dummy.INITIAL_HEALTH - SoulFire.SOULFIRE_DAMAGE, enemy.getHealth());
    }
}
