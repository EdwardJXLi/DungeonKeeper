package model.game;

import model.Enemy;
import model.Game;
import model.Level;
import model.Player;
import model.enemies.Guard;
import model.tiles.Trap;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {
    Game game;
    Level level;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        level = new Level(11, game, 32, 24, 1, 2);
    }

    @Test
    public void testConstructor() {
        assertEquals(11, level.getLevelNum());
        assertEquals(32, level.getSizeX());
        assertEquals(24, level.getSizeY());
        assertTrue(level.getTiles().isEmpty());
        assertTrue(level.getEnemies().isEmpty());
        assertTrue(level.getDroppedItems().isEmpty());
    }

    @Test
    public void checkSpawnPlayer() {
        Player player = new Player(game);
        level.spawnPlayer(player);
        assertEquals(1, player.getPosX());
        assertEquals(2, player.getPosY());
    }

    @Test
    public void testSpawnEnemy() {
        Enemy enemy = new Guard(game);
        level.spawnEnemy(enemy, 5, 6);
        assertEquals(1, level.getEnemies().size());
        assertEquals(enemy, level.getEnemies().get(0));
        assertEquals(enemy, level.getEnemyAtLocation(5, 6));
    }

    @Test
    public void testSpawnMultipleEnemies() {
        Enemy enemy1 = new Guard(game);
        Enemy enemy2 = new Guard(game);
        level.spawnEnemy(enemy1, 5, 6);
        level.spawnEnemy(enemy2, 7, 8);
        assertEquals(2, level.getEnemies().size());
        assertEquals(enemy2, level.getEnemyAtLocation(7, 8));
        assertEquals(enemy1, level.getEnemyAtLocation(5, 6));
    }

    @Test
    public void testNoTileAtLocation() {
        assertNull(level.getTileAtLocation(10, 20));
        assertFalse(level.isSolidTileAtLocation(10, 20));
    }

    @Test
    public void testSolidTileAtLocation() {
        level.addTile(new Wall(10, 20));
        assertTrue(level.isSolidTileAtLocation(10, 20));
    }

    @Test
    public void testNonSolidTileAtLocation() {
        level.addTile(new Trap(10, 20));
        assertFalse(level.isSolidTileAtLocation(10, 20));
    }

    @Test
    public void testOverlappingTiles() {
        // Check disabled, tile should add
        level.addTile(new Wall(10, 20), false);
        level.addTile(new Trap(10, 20), false);
        assertEquals(2, level.getTiles().size());

        // Check Enabled, tile should not add
        level.addTile(new Wall(10, 20), true);
        assertEquals(2, level.getTiles().size());
    }

    @Test
    public void testInitLevel() {
        level.initLevel();

        // Check is walls are generated correctly
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 32; x++) {
                if (x == 0 || x == 31 || y == 0 || y == 23) {
                    assertNotNull(level.getTileAtLocation(x, y));
                }
            }
        }

        // Check if enemies are generated correctly
        int numEnemiesSpawned = 0;
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                if (level.getEnemyAtLocation(x, y) != null) {
                    numEnemiesSpawned++;
                }
            }
        }
        assertTrue(numEnemiesSpawned >= 1);

        // Check if dungeon exit is generated correctly
        assertNotNull(level.getDungeonExit());
    }

    @Test
    public void testNextTickSpawnPotion() {
        game.initEmptyGame();
        int numPotionsBeforeTick = level.getDroppedItems().size();
        level.handleNextTick(Level.TICKS_UNTIL_HEALTH_SPAWN);

        // Check if potion spawns
        assertEquals(numPotionsBeforeTick + 1, level.getDroppedItems().size());
    }

    @Test
    public void testNextTickSpawnGuard() {
        game.initEmptyGame();
        int numGuardsBeforeTick = level.getEnemies().size();
        level.handleNextTick(Level.TICKS_UNTIL_GUARD_SPAWN);

        // Check if banner spawns
        assertEquals(numGuardsBeforeTick + 1, level.getEnemies().size());
    }

    @Test
    public void testNextTickSpawnWisp() {
        game.initEmptyGame();
        int numWispBeforeTick = level.getEnemies().size();
        level.handleNextTick(Level.TICKS_UNTIL_WISP_SPAWN);

        // Check if banner spawns
        assertEquals(numWispBeforeTick + 1, level.getEnemies().size());
    }
}
