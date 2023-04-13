package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.graphics.SpriteID;

/*
 * A Dummy enemy that has no attack and no defense.
 * Used to test out the fighting and movement system.
 */

public class Vampire extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 0;

    // EFFECTS: Creates a basic dummy to fight
    public Vampire(Game game) {
        super(
                game,
                ';', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_DUMMY,
                "Dummy", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a dummy from pre-existing values.
    public Vampire(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, ';', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_DUMMY,
                "Dummy", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // EFFECTS: Helper function to run movement tests
    public void setCoordinate(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }
}
