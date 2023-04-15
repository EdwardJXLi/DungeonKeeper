package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.graphics.SpriteID;
import model.items.Armor;
import model.items.Ectoplasm;
import model.items.GuardArmor;
import model.tiles.SoulFire;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EctoplasmTest {
    Ectoplasm ectoplasm;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        ectoplasm = new Ectoplasm();
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
    }

    @Test
    public void testEctoplasmConstructor() {
        assertEquals("Ectoplasm", ectoplasm.getName());
        assertEquals("Spawns Soul Fire", ectoplasm.getDescription().get(0));
        assertEquals("that Only Damages", ectoplasm.getDescription().get(1));
        assertEquals("enemies", ectoplasm.getDescription().get(2));
        assertEquals('*', ectoplasm.getTextSprite());
        assertEquals(TextColor.ANSI.CYAN, ectoplasm.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, ectoplasm.getBackgroundColor());
        assertEquals(SpriteID.ITEM_ECTOPLASM, ectoplasm.getSpriteID());
    }

    @Test
    public void testUseItem() {
        ectoplasm.useItem(player);

        // Check that soulfire was placed correctly
        assertNotNull(game.getLevel().getTileAtLocation(player.getPosX(), player.getPosY()));
        assertNotNull(game.getLevel().getTileAtLocation(player.getPosX() - 1, player.getPosY() - 1));
        assertNotNull(game.getLevel().getTileAtLocation(player.getPosX() - 1, player.getPosY() + 1));
        assertNotNull(game.getLevel().getTileAtLocation(player.getPosX() + 1, player.getPosY() - 1));
        assertNotNull(game.getLevel().getTileAtLocation(player.getPosX() + 1, player.getPosY() - 1));

        assertTrue(game.getLevel().getTileAtLocation(
                player.getPosX(), player.getPosY()) instanceof SoulFire
        );
        assertTrue(game.getLevel().getTileAtLocation(
                player.getPosX() - 1, player.getPosY() - 1) instanceof SoulFire
        );
        assertTrue(game.getLevel().getTileAtLocation(
                player.getPosX() - 1, player.getPosY() + 1) instanceof SoulFire
        );
        assertTrue(game.getLevel().getTileAtLocation(
                player.getPosX() + 1, player.getPosY() - 1) instanceof SoulFire
        );
        assertTrue(game.getLevel().getTileAtLocation(
                player.getPosX() + 1, player.getPosY() + 1) instanceof SoulFire
        );
    }

    @Test
    public void testUseItemWhenBlocked() {
        // Place tiles around user.
        // Soulfire should not spawn
        for (int x = player.getPosX() - 1; x <= player.getPosX() + 1; x++) {
            for (int y = player.getPosY() - 1; y <= player.getPosY() + 1; y++) {
                game.getLevel().addTile(new Wall(x, y));
            }
        }

        ectoplasm.useItem(player);

        // Check that soulfire was not placed
        assertFalse(game.getLevel().getTileAtLocation(
                player.getPosX(), player.getPosY()) instanceof SoulFire
        );
        assertFalse(game.getLevel().getTileAtLocation(
                player.getPosX() - 1, player.getPosY() - 1) instanceof SoulFire
        );
        assertFalse(game.getLevel().getTileAtLocation(
                player.getPosX() - 1, player.getPosY() + 1) instanceof SoulFire
        );
        assertFalse(game.getLevel().getTileAtLocation(
                player.getPosX() + 1, player.getPosY() - 1) instanceof SoulFire
        );
        assertFalse(game.getLevel().getTileAtLocation(
                player.getPosX() + 1, player.getPosY() + 1) instanceof SoulFire
        );
    }
}
