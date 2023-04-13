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
    public static final int TICKS_UNTIL_MOVEMENT = 15;

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
    // EFFECTS: Handle vampire movement
    private void handleVampireMovement(int tick) {
        // Basic Vampire AI
        // If player is within 5 tiles, move towards them
        // Otherwise, move randomly
        int playerX = getGame().getPlayer().getPosX();
        int playerY = getGame().getPlayer().getPosY();
        if (Math.abs(playerX - getPosX()) <= 5 && Math.abs(playerY - getPosY()) <= 5) {
            // Move towards player
            if (playerX > getPosX()) {
                moveRight();
            } else if (playerX < getPosX()) {
                moveLeft();
            } else if (playerY > getPosY()) {
                moveDown();
            } else if (playerY < getPosY()) {
                moveUp();
            }
        } else {
            // Move randomly
            moveRandom();
        }
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

            // Handle Vampire Movement
            handleVampireMovement(tick);
        }

        // Handle super
        super.handleNextTick(tick);
    }
}
