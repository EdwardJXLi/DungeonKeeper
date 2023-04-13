package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;

/*
 * Minion of the wisp.
 * Little defence, attack, and health, but spawns a lot at a time.
 */

public class Whisper extends Enemy {
    public static final int INITIAL_HEALTH = 10;
    public static final int INITIAL_DEFENSE = 5;
    public static final int INITIAL_ATTACK = 5;
    public static final int TICKS_UNTIL_MOVEMENT = 5;

    // EFFECTS: Creates a whisper enemy
    public Whisper(Game game) {
        super(
                game,
                '*', TextColor.ANSI.CYAN, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_WHISPER,
                "Whisper", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a whisper from pre-existing values.
    public Whisper(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, '*', TextColor.ANSI.CYAN, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_DUMMY,
                "Whisper", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // EFFECTS: Helper function to run movement tests
    public void setCoordinate(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        if (tick % TICKS_UNTIL_MOVEMENT == 0) {
            // Whisper cant get stunned!

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