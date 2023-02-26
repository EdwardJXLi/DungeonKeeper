package model;

import com.googlecode.lanterna.TextColor;

public abstract class Enemy extends Entity {
    protected boolean stunned;

    // EFFECTS: Creates a generic enemy
    public Enemy(
            Game game,
            char textSprite, TextColor textColor, TextColor backgroundColor,
            String name, int health, int defence, int attack) {
        super(
                game, 0, 0, textSprite, textColor, backgroundColor, name, health, defence, attack
        );

        this.stunned = false;
    }

    // MODIFIES: this
    // EFFECTS: Stuns enemy - next turn is skipped
    public void stun() {
        stunned = true;
    }
}
