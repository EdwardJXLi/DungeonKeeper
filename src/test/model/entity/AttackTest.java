package model.entity;

import model.Enemy;
import model.Game;
import model.Player;
import model.enemies.Guard;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttackTest {
    Game game;
    Player player;
    Enemy enemy;

    @BeforeEach
    public void setup() {
        game = new Game(32, 48);
        player = new Player(game);
        enemy = new Guard(game);
        enemy.addDefense(-Guard.INITIAL_DEFENSE);
    }

    @Test
    public void testOneAttack() {
        player.attackEnemy(enemy);
        assertEquals(Player.INITIAL_HEALTH - Guard.INITIAL_ATTACK, player.getHealth());
        assertEquals(Guard.INITIAL_HEALTH - Player.INITIAL_ATTACK, enemy.getHealth());
        assertFalse(enemy.isDead());
        assertEquals(0, player.getKills());
    }

    @Test
    public void testMultipleAttack() {
        player.attackEnemy(enemy);
        player.attackEnemy(enemy);
        assertEquals(Player.INITIAL_HEALTH - Guard.INITIAL_ATTACK * 2, player.getHealth());
        assertEquals(Guard.INITIAL_HEALTH - Player.INITIAL_ATTACK * 2, enemy.getHealth());
        assertFalse(enemy.isDead());
        assertEquals(0, player.getKills());
    }

    @Test
    public void testAttackUntilKill() {
        player.attackEnemy(enemy);
        player.attackEnemy(enemy);
        player.attackEnemy(enemy);
        assertTrue(enemy.isDead());
        assertEquals(Player.INITIAL_HEALTH - Guard.INITIAL_ATTACK * 3, player.getHealth());
        assertEquals(0, enemy.getHealth());
        assertEquals(1, player.getKills());
    }

    @Test
    public void testAttackMultipleEnemies() {
        // Create multiple enemies
        Enemy enemy1 = new Guard(game);
        Enemy enemy2 = new Guard(game);
        Enemy enemy3 = new Guard(game);
        // Attack but don't kill
        player.attackEnemy(enemy1);
        player.attackEnemy(enemy2);
        player.attackEnemy(enemy2);
    }

    @Test
    public void testAttackUntilPlayerDeath() {
        enemy.addDefense(9999);
        // Attack until player is dead
        while (!player.isDead()) {
            player.attackEnemy(enemy);
        }
        // Check that player is dead
        assertTrue(player.isDead());
        // Check that the game is over
        assertFalse(game.isGameRunning());
    }

    @Test
    public void testLastEnemyFoughtTest() {
        assertNull(player.getLastEnemyFought());
        player.attackEnemy(enemy);
        assertEquals(enemy, player.getLastEnemyFought());
    }

    @Test
    public void testInitiateAttackFromTop() {
        // Initiate map and place player and enemies
        assertNull(player.getLastEnemyFought());
        game.initEmptyGame();
        game.getLevel().spawnPlayer(player);
        game.getLevel().spawnEnemy(enemy, Game.SPAWN_X, Game.SPAWN_Y + 1);
        player.moveDown();
        assertEquals(enemy, player.getLastEnemyFought());
    }

    @Test
    public void testInitiateAttackFromBottom() {
        // Initiate map and place player and enemies
        assertNull(player.getLastEnemyFought());
        game.initEmptyGame();
        game.getLevel().spawnPlayer(player);
        game.getLevel().spawnEnemy(enemy, Game.SPAWN_X, Game.SPAWN_Y - 1);
        player.moveUp();
        assertEquals(enemy, player.getLastEnemyFought());
    }

    @Test
    public void testInitiateAttackFromLeft() {
        // Initiate map and place player and enemies
        assertNull(player.getLastEnemyFought());
        game.initEmptyGame();
        game.getLevel().spawnPlayer(player);
        game.getLevel().spawnEnemy(enemy, Game.SPAWN_X + 1, Game.SPAWN_Y);
        player.moveRight();
        assertEquals(enemy, player.getLastEnemyFought());
    }

    @Test
    public void testInitiateAttackFromRight() {
        // Initiate map and place player and enemies
        assertNull(player.getLastEnemyFought());
        game.initEmptyGame();
        game.getLevel().spawnPlayer(player);
        game.getLevel().spawnEnemy(enemy, Game.SPAWN_X - 1, Game.SPAWN_Y);
        player.moveLeft();
        assertEquals(enemy, player.getLastEnemyFought());
    }

    @Test
    public void testEnemyInitiatesAttackFirst() {
        // Initiate map and place player and enemies
        assertNull(player.getLastEnemyFought());
        game.initEmptyGame();
        game.getLevel().spawnPlayer(player);
        game.getLevel().spawnEnemy(enemy, Game.SPAWN_X, Game.SPAWN_Y + 1);
        // Create walls around enemy so that it can only attack up
        game.getLevel().addTile(new Wall(Game.SPAWN_X, Game.SPAWN_Y + 2));
        game.getLevel().addTile(new Wall(Game.SPAWN_X - 1, Game.SPAWN_Y + 1));
        game.getLevel().addTile(new Wall(Game.SPAWN_X + 1, Game.SPAWN_Y + 1));
        game.getLevel().addTile(new Wall(Game.SPAWN_X - 1, Game.SPAWN_Y));
        game.getLevel().addTile(new Wall(Game.SPAWN_X + 1, Game.SPAWN_Y));
        // Try to move the enemy about a thousand times.
        // Eventually, one of those would be one that initiates an attack on a player
        for (int i = 0; i < 1000; i++) {
            enemy.handleNextTick(0);
        }
        // Check that the player has been attacked
        assertNotEquals(Player.INITIAL_HEALTH, player.getHealth());
        assertEquals(enemy, player.getLastEnemyFought());
    }
}
