package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;

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

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates an enemy from pre-existing values.
    //          Used for loading games from JSON file
    public Enemy(
            Game game, int posX, int posY,
            char textSprite, TextColor textColor, TextColor backgroundColor,
            String name, boolean stunned, int maxHealth, int maxDefense, int maxAttack,
            int health, int defense, int attack
    ) {
        super(game, posX, posY, textSprite, textColor, backgroundColor,
                name, maxHealth, maxDefense, maxAttack);
        setHealth(health);
        setDefense(defense);
        setAttack(attack);
        if (stunned) {
            stun();
        }
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

    // EFFECTS: Returns JSON Representation of an Enemy object
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("__type__", this.getClass().getName());
        json.put("stunned", stunned);
        return json;
    }
}
