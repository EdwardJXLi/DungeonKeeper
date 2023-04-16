package model.entity;

import model.Game;
import model.Player;
import model.tiles.Trap;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerMovementTest {
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
    }

    @Test
    public void testTeleport() {
        player.setCoordinate(10, 20);
        assertEquals(10, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testMoveUp() {
        player.setCoordinate(10, 20);
        assertTrue(player.moveUp());
        assertEquals(10, player.getPosX());
        assertEquals(19, player.getPosY());
    }

    @Test
    public void testMoveDown() {
        player.setCoordinate(10, 20);
        assertTrue(player.moveDown());
        assertEquals(10, player.getPosX());
        assertEquals(21, player.getPosY());
    }

    @Test
    public void testMoveLeft() {
        player.setCoordinate(10, 20);
        assertTrue(player.moveLeft());
        assertEquals(9, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testMoveRight() {
        player.setCoordinate(10, 20);
        assertTrue(player.moveRight());
        assertEquals(11, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testApproachTopWall() {
        player.setCoordinate(5, 1);
        assertTrue(player.moveUp());
        assertEquals(5, player.getPosX());
        assertEquals(0, player.getPosY());
    }

    @Test
    public void testCollideTopWall() {
        player.setCoordinate(5, 0);
        assertFalse(player.moveUp());
        assertEquals(5, player.getPosX());
        assertEquals(0, player.getPosY());
    }

    @Test
    public void testApproachBottomWall() {
        player.setCoordinate(5, 22);
        assertTrue(player.moveDown());
        assertEquals(5, player.getPosX());
        assertEquals(23, player.getPosY());
    }

    @Test
    public void testCollideBottomWall() {
        player.setCoordinate(5, 23);
        assertFalse(player.moveDown());
        assertEquals(5, player.getPosX());
        assertEquals(23, player.getPosY());
    }

    @Test
    public void testApproachLeftWall() {
        player.setCoordinate(1, 5);
        assertTrue(player.moveLeft());
        assertEquals(0, player.getPosX());
        assertEquals(5, player.getPosY());
    }

    @Test
    public void testCollideLeftWall() {
        player.setCoordinate(0, 5);
        assertFalse(player.moveLeft());
        assertEquals(0, player.getPosX());
        assertEquals(5, player.getPosY());
    }

    @Test
    public void testApproachRightWall() {
        player.setCoordinate(30, 5);
        assertTrue(player.moveRight());
        assertEquals(31, player.getPosX());
        assertEquals(5, player.getPosY());
    }

    @Test
    public void testCollideRightWall() {
        player.setCoordinate(31, 5);
        assertFalse(player.moveRight());
        assertEquals(31, player.getPosX());
        assertEquals(5, player.getPosY());
    }

    @Test
    public void testHitSolidTileFromUnder() {
        game.getLevel().addTile(new Wall(10, 20));
        player.setCoordinate(10, 21);
        assertFalse(player.moveUp());
        assertEquals(10, player.getPosX());
        assertEquals(21, player.getPosY());
    }

    @Test
    public void testHitSolidTileFromAbove() {
        game.getLevel().addTile(new Wall(10, 20));
        player.setCoordinate(10, 19);
        assertFalse(player.moveDown());
        assertEquals(10, player.getPosX());
        assertEquals(19, player.getPosY());
    }

    @Test
    public void testHitSolidTileFromRight() {
        game.getLevel().addTile(new Wall(10, 20));
        player.setCoordinate(11, 20);
        assertFalse(player.moveLeft());
        assertEquals(11, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testHitSolidTileFromLeft() {
        game.getLevel().addTile(new Wall(10, 20));
        player.setCoordinate(9, 20);
        assertFalse(player.moveRight());
        assertEquals(9, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromUnder() {
        game.getLevel().addTile(new Trap(10, 20));
        player.setCoordinate(10, 21);
        assertTrue(player.moveUp());
        assertEquals(10, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromAbove() {
        game.getLevel().addTile(new Trap(10, 20));
        player.setCoordinate(10, 19);
        assertTrue(player.moveDown());
        assertEquals(10, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromRight() {
        game.getLevel().addTile(new Trap(10, 20));
        player.setCoordinate(11, 20);
        assertTrue(player.moveLeft());
        assertEquals(10, player.getPosX());
        assertEquals(20, player.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromLeft() {
        game.getLevel().addTile(new Trap(10, 20));
        player.setCoordinate(9, 20);
        assertTrue(player.moveRight());
        assertEquals(10, player.getPosX());
        assertEquals(20, player.getPosY());
    }
}
