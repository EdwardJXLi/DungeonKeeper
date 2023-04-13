package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;
import model.items.MageRobe;
import model.items.VampireFangs;

/*
 * A Mage Enemy. Large defense, but low attack.
 * Drops Mage Robe when killed.
 */

public class Mage extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = MageRobe.MAGE_DEFENSE;
    public static final int INITIAL_ATTACK = 20;
    public static final int TICKS_UNTIL_MOVEMENT = 20;

    // EFFECTS: Creates a mage to fight
    public Mage(Game game) {
        super(
                game,
                'M', TextColor.ANSI.BLACK, TextColor.ANSI.GREEN, SpriteID.ENTITY_MAGE,
                "Mage", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a mage from pre-existing values.
    public Mage(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, 'M', TextColor.ANSI.BLACK, TextColor.ANSI.GREEN, SpriteID.ENTITY_MAGE,
                "Mage", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Drops mage robe on death
    @Override
    public void onDeath() {
        // Drop mage robe
        getGame().getLevel().dropItem(getPosX(), getPosY(), new MageRobe());

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
