package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;

public class Juggernaut extends Enemy {
    private static final int INITIAL_HEALTH = 5000;
    private static final int INITIAL_DEFENSE = 25;
    private static final int INITIAL_ATTACK = 50;
    private static final int TICKS_UNTIL_MOVEMENT = 50;

    // EFFECTS: Creates a basic guard enemy
    public Juggernaut(Game game) {
        super(
                game,
                'X', TextColor.ANSI.YELLOW, TextColor.ANSI.BLUE,
                "Juggernaut", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        if (tick % TICKS_UNTIL_MOVEMENT == 0) {
            if (stunned) {
                stunned = false;
                return;
            }

            switch (getGame().getRandom().nextInt(4)) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveDown();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveRight();
                    break;
            }
        }
    }
}
