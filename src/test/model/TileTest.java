package model;

import com.googlecode.lanterna.TextColor;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {
    Tile tile;

    @BeforeEach
    public void setup() {
        tile = new Wall(0, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tile.getPosX());
        assertEquals(0, tile.getPosY());
        assertEquals('#', tile.getTextSprite());
        assertEquals(TextColor.ANSI.BLACK, tile.getTextColor());
        assertEquals(TextColor.ANSI.WHITE, tile.getBackgroundColor());
        assertEquals("Wall", tile.getName());
        assertEquals(null, tile.getDescription());
        assertEquals(true, tile.isSolid());
    }
}
