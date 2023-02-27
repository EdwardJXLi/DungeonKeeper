package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;

public class Dummy extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 0;

    // EFFECTS: Creates a basic dummy to fight
    public Dummy(Game game) {
        super(
                game,
                ';', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Dummy", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // EFFECTS: Helper function to run movement tests
    public void setCoordinate(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }
}
