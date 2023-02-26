package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;

public class Guard extends Enemy {
    private static final int INITIAL_HEALTH = 50;
    private static final int INITIAL_DEFENSE = 5;
    private static final int INITIAL_ATTACK = 10;

    // EFFECTS: Creates a basic guard enemy
    public Guard(Game game) {
        super(
                game,
                '%', TextColor.ANSI.RED, TextColor.ANSI.DEFAULT,
                "Guard", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }
}
