package model.entity;

import model.Game;
import model.enemies.Dummy;
import model.tiles.Trap;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyMovementTest {
    Game game;
    Dummy entity;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        entity = new Dummy(game);
    }

    @Test
    public void testTeleport() {
        entity.setCoordinate(10, 20);
        assertEquals(10, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testMoveUp() {
        entity.setCoordinate(10, 20);
        assertTrue(entity.moveUp());
        assertEquals(10, entity.getPosX());
        assertEquals(19, entity.getPosY());
    }

    @Test
    public void testMoveDown() {
        entity.setCoordinate(10, 20);
        assertTrue(entity.moveDown());
        assertEquals(10, entity.getPosX());
        assertEquals(21, entity.getPosY());
    }

    @Test
    public void testMoveLeft() {
        entity.setCoordinate(10, 20);
        assertTrue(entity.moveLeft());
        assertEquals(9, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testMoveRight() {
        entity.setCoordinate(10, 20);
        assertTrue(entity.moveRight());
        assertEquals(11, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testApproachTopWall() {
        entity.setCoordinate(5, 1);
        assertTrue(entity.moveUp());
        assertEquals(5, entity.getPosX());
        assertEquals(0, entity.getPosY());
    }

    @Test
    public void testCollideTopWall() {
        entity.setCoordinate(5, 0);
        assertFalse(entity.moveUp());
        assertEquals(5, entity.getPosX());
        assertEquals(0, entity.getPosY());
    }

    @Test
    public void testApproachBottomWall() {
        entity.setCoordinate(5, 22);
        assertTrue(entity.moveDown());
        assertEquals(5, entity.getPosX());
        assertEquals(23, entity.getPosY());
    }

    @Test
    public void testCollideBottomWall() {
        entity.setCoordinate(5, 23);
        assertFalse(entity.moveDown());
        assertEquals(5, entity.getPosX());
        assertEquals(23, entity.getPosY());
    }

    @Test
    public void testApproachLeftWall() {
        entity.setCoordinate(1, 5);
        assertTrue(entity.moveLeft());
        assertEquals(0, entity.getPosX());
        assertEquals(5, entity.getPosY());
    }

    @Test
    public void testCollideLeftWall() {
        entity.setCoordinate(0, 5);
        assertFalse(entity.moveLeft());
        assertEquals(0, entity.getPosX());
        assertEquals(5, entity.getPosY());
    }

    @Test
    public void testApproachRightWall() {
        entity.setCoordinate(30, 5);
        assertTrue(entity.moveRight());
        assertEquals(31, entity.getPosX());
        assertEquals(5, entity.getPosY());
    }

    @Test
    public void testCollideRightWall() {
        entity.setCoordinate(31, 5);
        assertFalse(entity.moveRight());
        assertEquals(31, entity.getPosX());
        assertEquals(5, entity.getPosY());
    }

    @Test
    public void testHitSolidTileFromUnder() {
        game.getLevel().getTiles().add(new Wall(10, 20));
        entity.setCoordinate(10, 21);
        assertFalse(entity.moveUp());
        assertEquals(10, entity.getPosX());
        assertEquals(21, entity.getPosY());
    }

    @Test
    public void testHitSolidTileFromAbove() {
        game.getLevel().getTiles().add(new Wall(10, 20));
        entity.setCoordinate(10, 19);
        assertFalse(entity.moveDown());
        assertEquals(10, entity.getPosX());
        assertEquals(19, entity.getPosY());
    }

    @Test
    public void testHitSolidTileFromRight() {
        game.getLevel().getTiles().add(new Wall(10, 20));
        entity.setCoordinate(11, 20);
        assertFalse(entity.moveLeft());
        assertEquals(11, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testHitSolidTileFromLeft() {
        game.getLevel().getTiles().add(new Wall(10, 20));
        entity.setCoordinate(9, 20);
        assertFalse(entity.moveRight());
        assertEquals(9, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromUnder() {
        game.getLevel().getTiles().add(new Trap(10, 20));
        entity.setCoordinate(10, 21);
        assertTrue(entity.moveUp());
        assertEquals(10, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromAbove() {
        game.getLevel().getTiles().add(new Trap(10, 20));
        entity.setCoordinate(10, 19);
        assertTrue(entity.moveDown());
        assertEquals(10, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromRight() {
        game.getLevel().getTiles().add(new Trap(10, 20));
        entity.setCoordinate(11, 20);
        assertTrue(entity.moveLeft());
        assertEquals(10, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }

    @Test
    public void testHitNonSolidTileFromLeft() {
        game.getLevel().getTiles().add(new Trap(10, 20));
        entity.setCoordinate(9, 20);
        assertTrue(entity.moveRight());
        assertEquals(10, entity.getPosX());
        assertEquals(20, entity.getPosY());
    }
}
