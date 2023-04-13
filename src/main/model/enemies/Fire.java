package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;

/*
 * Fire entity - Chance of spawning next to mage.
 * Fire doesnt move, but it slowly "dies" and loses health over time.
 * Fire also does a lot of damage, to all enemies.
 */

public class Fire extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = 9999;
    public static final int INITIAL_ATTACK = 30;

    // EFFECTS: Creates a fire enemy
    public Fire(Game game) {
        super(
                game,
                '#', TextColor.ANSI.YELLOW, TextColor.ANSI.RED, SpriteID.ENTITY_FIRE,
                "Fire", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a fire from pre-existing values.
    public Fire(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, '#', TextColor.ANSI.YELLOW, TextColor.ANSI.RED, SpriteID.ENTITY_FIRE,
                "Fire", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        // Every tick, the fire "dies" by losing one health!
        // Using setHealth() to avoid defense check
        setHealth(getHealth() - 1);

        // Handle super
        super.handleNextTick(tick);
    }
}
