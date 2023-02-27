package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.enemies.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    Game game;
    Enemy enemy;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        enemy = new Dummy(game);
    }

    @Test
    public void testConstructor() {
        assertEquals("Dummy", enemy.getName());
        assertEquals(';', enemy.getTextSprite());
        assertEquals(TextColor.ANSI.YELLOW, enemy.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, enemy.getBackgroundColor());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
        assertEquals(Dummy.INITIAL_ATTACK, enemy.getAttack());
        assertEquals(Dummy.INITIAL_DEFENSE, enemy.getDefense());
        assertEquals(game, enemy.getGame());
        assertFalse(enemy.isStunned());
    }

    @Test
    public void testEntityName() {
        assertEquals("Dummy", enemy.getName());
        enemy.setName("TestName");
        assertEquals("TestName", enemy.getName());
    }

    @Test
    public void testDamageAndNotDie() {
        enemy.damage(10);
        assertFalse(enemy.isDead());
        assertEquals(Dummy.INITIAL_HEALTH - 10, enemy.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
    }

    @Test
    public void testMultipleDamageAndNotDie() {
        enemy.damage(5);
        enemy.damage(7);
        assertFalse(enemy.isDead());
        assertEquals(Dummy.INITIAL_HEALTH - 5 - 7, enemy.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
    }

    @Test
    public void testDamageNearDeath() {
        enemy.damage(Dummy.INITIAL_HEALTH - 1);
        assertFalse(enemy.isDead());
        assertEquals(1, enemy.getHealth());
    }

    @Test
    public void testDeath() {
        enemy.damage(Dummy.INITIAL_HEALTH);
        assertTrue(enemy.isDead());
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void TestOverdamageDeath() {
        enemy.damage(Dummy.INITIAL_HEALTH + 1000);
        assertTrue(enemy.isDead());
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void testDamageAfterDeath() {
        enemy.damage(Dummy.INITIAL_HEALTH);
        assertTrue(enemy.isDead());
        assertEquals(0, enemy.getHealth());
        enemy.damage(24);
        assertTrue(enemy.isDead());
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void testHeal() {
        enemy.damage(50);
        assertEquals(Dummy.INITIAL_HEALTH - 50, enemy.getHealth());
        enemy.heal(10);
        assertEquals(Dummy.INITIAL_HEALTH - 40, enemy.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
    }

    @Test
    public void testMultipleHeal() {
        enemy.damage(50);
        assertEquals(Dummy.INITIAL_HEALTH - 50, enemy.getHealth());
        enemy.heal(3);
        enemy.heal(5);
        assertEquals(Dummy.INITIAL_HEALTH - 50 + 8, enemy.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
    }

    @Test
    public void testHealWhenFull() {
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
        enemy.heal(35);
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
    }

    @Test
    public void testHealUntilFull() {
        enemy.damage(10);
        assertEquals(Dummy.INITIAL_HEALTH - 10, enemy.getHealth());
        enemy.heal(50);
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
    }

    @Test
    public void testDefense() {
        enemy.addDefense(10);
        enemy.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH - 10, enemy.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getMaxHealth());
    }

    @Test
    public void testDefenseNegateFullDamage() {
        enemy.addDefense(20);
        enemy.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
    }

    @Test
    public void testDefenseMoreThanDamage() {
        enemy.addDefense(100);
        enemy.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH, enemy.getHealth());
    }

    @Test
    public void testSetAttack() {
        enemy.addAttack(12);
        assertEquals(12, enemy.getAttack());
    }

    @Test
    public void testStun() {
        enemy.stun();
        assertTrue(enemy.isStunned());
    }

    @Test
    public void testMultipleStun() {
        enemy.stun();
        enemy.stun();
        enemy.stun();
        assertTrue(enemy.isStunned());
    }

    @Test
    public void testResetStun() {
        enemy.stun();
        enemy.resetStunned();
        assertFalse(enemy.isStunned());
    }

    @Test
    public void testResetStunWhenNotStunned() {
        enemy.resetStunned();
        assertFalse(enemy.isStunned());
    }
}
