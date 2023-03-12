package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.items.*;

/*
 * A basic Guard enemy that has basic attack, defense, and movement.
 * First enemy that player sees when entering the game
 */

public class Guard extends Enemy {
    // Enemy Constants
    // Public so that tests can access them
    public static final int INITIAL_HEALTH = 50;
    public static final int INITIAL_DEFENSE = 5;
    public static final int INITIAL_ATTACK = 20;
    public static final int TICKS_UNTIL_MOVEMENT = 20;

    // EFFECTS: Creates a basic guard enemy
    public Guard(Game game) {
        super(
                game,
                '%', TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT,
                "Guard", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a guard, from pre-existing values.
    public Guard(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, '%', TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT,
                "Guard", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Drops random loop on death
    @Override
    public void onDeath() {
        // Chance of dropping either Armor, Weapon, or Potion
        Item droppedItem = new HealingPotion();
        int i = getGame().getRandom().nextInt(5);
        if (i == 0) {
            droppedItem = new GuardArmor(getGame().getRandom().nextInt(25));
        } else if (i == 1) {
            droppedItem = new GuardSword(getGame().getRandom().nextInt(25));
        } else if (i == 2) {
            droppedItem = new StrengthPotion();
        } else if (i == 3) {
            droppedItem = new DefensePotion();
        } else {
            droppedItem = new HealingPotion();
        }
        // Drop item
        getGame().getLevel().dropItem(getPosX(), getPosY(), droppedItem);

        // Handle super
        super.onDeath();
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        if (tick % TICKS_UNTIL_MOVEMENT == 0) {
            // If enemy is stunned, skip movement
            if (isStunned()) {
                resetStunned();
                return;
            }

            // Try to move in a random direction
            // If this fails, nothing will happen. This is intended behavior!
            int i = getGame().getRandom().nextInt(4);
            if (i == 0) {
                moveUp();
            } else if (i == 1) {
                moveDown();
            } else if (i == 2) {
                moveLeft();
            } else {
                moveRight();
            }
        }

        // Handle super
        super.handleNextTick(tick);
    }
}
