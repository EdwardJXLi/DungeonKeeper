package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;
import model.items.MageRobe;

/*
 * A Mage Enemy. Large defense, but low attack.
 * Drops Mage Robe when killed.
 */

public class Mage extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = MageRobe.MAGE_DEFENSE;
    public static final int INITIAL_ATTACK = 20;
    public static final int TICKS_UNTIL_MOVEMENT = 15;
    public static final int MOVES_UNTIL_FIRE_SPAWN = 3;

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
    // EFFECTS: Handle movement for the mage
    private void handleMageMovement(int tick) {
        // Basic Mage AI
        // If player is within 10 tiles, move towards them
        // Otherwise, move randomly
        int playerX = getGame().getPlayer().getPosX();
        int playerY = getGame().getPlayer().getPosY();
        if (Math.abs(playerX - getPosX()) <= 10 && Math.abs(playerY - getPosY()) <= 10) {
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

            // If the enemy has moved twice, spawn a fire
            if (tick % (TICKS_UNTIL_MOVEMENT * MOVES_UNTIL_FIRE_SPAWN) == 0) {
                getGame().getLevel().spawnEnemy(new Fire(getGame()), getPosX(), getPosY());
            }

            // Handle Mage Movement
            handleMageMovement(tick);
        }
        // Handle super
        super.handleNextTick(tick);
    }
}
