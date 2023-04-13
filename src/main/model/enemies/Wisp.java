package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.graphics.SpriteID;
import model.items.*;

/*
 * A Wisp enemy that has no attack and no defense.
 * But spawns whispers that do attack the player.
 */

public class Wisp extends Enemy {
    public static final int INITIAL_HEALTH = 140;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 0;
    public static final int TICKS_UNTIL_MOVEMENT = 10;
    public static final int MOVES_UNTIL_WHISPER_SPAWN = 3;

    // EFFECTS: Creates a wisp to fight
    public Wisp(Game game) {
        super(
                game,
                '*', TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_WISP,
                "Wisp", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a wisp from pre-existing values.
    public Wisp(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, '*', TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_WISP,
                "Wisp", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Drops Ectoplasm on death
    @Override
    public void onDeath() {
        // Drop Ectoplasm
        getGame().getLevel().dropItem(getPosX(), getPosY(), new Ectoplasm());

        // Handle super
        super.onDeath();
    }

    // EFFECTS: Returns the number of whispers in the level
    private int getWhispersInLevel() {
        int count = 0;
        for (Enemy e : getGame().getLevel().getEnemies()) {
            if (e instanceof Whisper) {
                count++;
            }
        }
        return count;
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

            // If the enemy has moved 5 times, spawn a whisper
            if (tick % (TICKS_UNTIL_MOVEMENT * MOVES_UNTIL_WHISPER_SPAWN) == 0) {
                // Check if there's enough space to spawn a whisper
                if (getWhispersInLevel() < 10) {
                    getGame().getLevel().spawnEnemy(new Whisper(getGame()), getPosX(), getPosY());
                }
            }
        }

        // Handle super
        super.handleNextTick(tick);
    }
}
