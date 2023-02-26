package model;

import com.googlecode.lanterna.TextColor;

public abstract class Enemy extends Entity {

    // EFFECTS: Creates a generic enemy
    public Enemy(
            Game game,
            char textSprite, TextColor textColor, TextColor backgroundColor,
            String name, int health, int defence, int attack) {
        super(
                game, 0, 0, textSprite, textColor, backgroundColor, name, health, defence, attack
        );
    }
}
