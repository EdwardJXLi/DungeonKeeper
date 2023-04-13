package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.graphics.SpriteID;
import model.items.*;

/*
 * A Vampire enemy. Large attack but little defense.
 * Drops Vampire Fangs when killed.
 */

public class Vampire extends Enemy {
    public static final int INITIAL_HEALTH = 400;
    public static final int INITIAL_DEFENSE = 20;
    public static final int INITIAL_ATTACK = VampireFangs.FANG_DAMAGE;
    public static final int TICKS_UNTIL_MOVEMENT = 20;

    // EFFECTS: Creates a vampire to fight
    public Vampire(Game game) {
        super(
                game,
                'V', TextColor.ANSI.BLACK, TextColor.ANSI.RED, SpriteID.ENTITY_VAMPIRE,
                "Vampire", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a vampire from pre-existing values.
    public Vampire(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, 'V', TextColor.ANSI.BLACK, TextColor.ANSI.RED, SpriteID.ENTITY_VAMPIRE,
                "Vampire", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Drops vampire fangs on death
    @Override
    public void onDeath() {
        // Drop vampire fangs
        getGame().getLevel().dropItem(getPosX(), getPosY(), new VampireFangs());

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
