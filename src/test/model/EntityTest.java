package model;

import com.googlecode.lanterna.TextColor;
import model.enemies.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    Game game;
    Entity entity;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        entity = new Dummy(game);
    }

    @Test
    public void testConstructor() {
        assertEquals("Dummy", entity.getName());
        assertEquals(';', entity.getTextSprite());
        assertEquals(TextColor.ANSI.YELLOW, entity.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, entity.getBackgroundColor());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
        assertEquals(Dummy.INITIAL_ATTACK, entity.getAttack());
        assertEquals(Dummy.INITIAL_DEFENSE, entity.getDefense());
        assertEquals(game, entity.getGame());
    }

    @Test
    public void testEntityName() {
        assertEquals("Dummy", entity.getName());
        entity.setName("TestName");
        assertEquals("TestName", entity.getName());
    }

    @Test
    public void testDamageAndNotDie() {
        entity.damage(10);
        assertFalse(entity.isDead());
        assertEquals(Dummy.INITIAL_HEALTH - 10, entity.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
    }

    @Test
    public void testMultipleDamageAndNotDie() {
        entity.damage(5);
        entity.damage(7);
        assertFalse(entity.isDead());
        assertEquals(Dummy.INITIAL_HEALTH - 5 - 7, entity.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
    }

    @Test
    public void testDamageNearDeath() {
        entity.damage(Dummy.INITIAL_HEALTH - 1);
        assertFalse(entity.isDead());
        assertEquals(1, entity.getHealth());
    }

    @Test
    public void testDeath() {
        entity.damage(Dummy.INITIAL_HEALTH);
        assertTrue(entity.isDead());
        assertEquals(0, entity.getHealth());
    }

    @Test
    public void TestOverdamageDeath() {
        entity.damage(Dummy.INITIAL_HEALTH + 1000);
        assertTrue(entity.isDead());
        assertEquals(0, entity.getHealth());
    }

    @Test
    public void testDamageAfterDeath() {
        entity.damage(Dummy.INITIAL_HEALTH);
        assertTrue(entity.isDead());
        assertEquals(0, entity.getHealth());
        entity.damage(24);
        assertTrue(entity.isDead());
        assertEquals(0, entity.getHealth());
    }

    @Test
    public void testHeal() {
        entity.damage(50);
        assertEquals(Dummy.INITIAL_HEALTH - 50, entity.getHealth());
        entity.heal(10);
        assertEquals(Dummy.INITIAL_HEALTH - 40, entity.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
    }

    @Test
    public void testMultipleHeal() {
        entity.damage(50);
        assertEquals(Dummy.INITIAL_HEALTH - 50, entity.getHealth());
        entity.heal(3);
        entity.heal(5);
        assertEquals(Dummy.INITIAL_HEALTH - 50 + 8, entity.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
    }

    @Test
    public void testHealWhenFull() {
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
        entity.heal(35);
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
    }

    @Test
    public void testHealUntilFull() {
        entity.damage(10);
        assertEquals(Dummy.INITIAL_HEALTH - 10, entity.getHealth());
        entity.heal(50);
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
    }

    @Test
    public void testDefense() {
        entity.addDefense(10);
        entity.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH - 10, entity.getHealth());
        assertEquals(Dummy.INITIAL_HEALTH, entity.getMaxHealth());
    }

    @Test
    public void testDefenseNegateFullDamage() {
        entity.addDefense(20);
        entity.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
    }

    @Test
    public void testDefenseMoreThanDamage() {
        entity.addDefense(100);
        entity.damage(20);
        assertEquals(Dummy.INITIAL_HEALTH, entity.getHealth());
    }

    @Test
    public void testSetAttack() {
        entity.addAttack(12);
        assertEquals(12, entity.getAttack());
    }
}
