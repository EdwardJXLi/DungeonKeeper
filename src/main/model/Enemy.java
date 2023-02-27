package model;

import com.googlecode.lanterna.TextColor;

/*
 * Abstract class for all Enemies.
 * Enemies can be stunned when attacked.
 * Examples: Dummy, Guard
 */

public abstract class Enemy extends Entity {
    // When enemy is stunned, it will not make the next move
    private boolean stunned;

    // EFFECTS: Creates a generic enemy
    public Enemy(
            Game game,
            char textSprite, TextColor textColor, TextColor backgroundColor,
            String name, int health, int baseDefence, int baseAttack
    ) {
        super(
                game, 0, 0, textSprite, textColor, backgroundColor,
                name, health, baseDefence, baseAttack
        );

        // Enemy is initially not stunned
        this.stunned = false;
    }

    // MODIFIES: this
    // EFFECTS: Stuns enemy - next turn is skipped
    public void stun() {
        stunned = true;
    }

    // EFFECTS: Returns whether the enemy is stunned
    public boolean isStunned() {
        return stunned;
    }

    // EFFECTS: Resets the stunned state of the enemy
    public void resetStunned() {
        stunned = false;
    }
}
