package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;
import model.items.Ectoplasm;

/*
 * A Wisp enemy that has no attack and no defense.
 * But spawns whispers that do attack the player.
 */

public class Wisp extends Enemy {
    public static final int INITIAL_HEALTH = 200;
    public static final int INITIAL_DEFENSE = 20;
    public static final int INITIAL_ATTACK = 20;
    public static final int TICKS_UNTIL_MOVEMENT = 10;

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

        // Spawn Whispers in all four directions
        getGame().getLevel().spawnEnemy(new Whisper(getGame()), getPosX() - 1, getPosY());
        getGame().getLevel().spawnEnemy(new Whisper(getGame()), getPosX() + 1, getPosY());
        getGame().getLevel().spawnEnemy(new Whisper(getGame()), getPosX(), getPosY() - 1);
        getGame().getLevel().spawnEnemy(new Whisper(getGame()), getPosX(), getPosY() + 1);

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

            // Very basic entity AI.
            // Always try to move in the direction of the player
            // Always try to move up and down first
            if (getGame().getPlayer().getPosY() < getPosY()) {
                moveUp();
            } else if (getGame().getPlayer().getPosY() > getPosY()) {
                moveDown();
            } else if (getGame().getPlayer().getPosX() < getPosX()) {
                moveLeft();
            } else if (getGame().getPlayer().getPosX() > getPosX()) {
                moveRight();
            }
        }

        // Handle super
        super.handleNextTick(tick);
    }
}
