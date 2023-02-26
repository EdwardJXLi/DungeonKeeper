package model;

import com.googlecode.lanterna.TextColor;

public class Player extends Entity {
    private static final int INITIAL_HEALTH = 200;
    private static final int INITIAL_DEFENSE = 10;
    private static final int INITIAL_ATTACK = 10;

    // Player Information
    private int kills;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // EFFECTS: Do nothing on new tick
    public void handleNextTick(int tick) {}

    //
    // Getters and Setters
    //

    public int getKills() {
        return kills;
    }
}
