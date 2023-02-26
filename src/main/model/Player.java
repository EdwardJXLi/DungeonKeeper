package model;

import com.googlecode.lanterna.TextColor;

public class Player extends Entity {
    private static final int INITIAL_HEALTH = 200;
    private static final int INITIAL_DEFENSE = 10;
    private static final int INITIAL_ATTACK = 10;

    private final Game game;

    // EFFECTS: Creates a player with initial X Y coords
    public Player(int initialX, int initialY, Game game) {
        super(initialX, initialY, '@', TextColor.ANSI.YELLOW, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK);
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player up.
    //          If allowed (within bounds and no walls):
    //               Moves the player up and returns true
    //          Else:
    //               Returns False
    public boolean moveUp() {
        if (!canBeAtLocation(game.getLevel(), getPosX(), getPosY() - 1)) {
            return false;
        }

        // Move to new location
        setPosY(getPosY() - 1);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player down.
    //          If allowed (within bounds and no walls):
    //               Moves the player up and returns true
    //          Else:
    //               Returns False
    public boolean moveDown() {
        if (!canBeAtLocation(game.getLevel(), getPosX(), getPosY() + 1)) {
            return false;
        }

        // Move to new location
        setPosY(getPosY() + 1);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player left.
    //          If allowed (within bounds and no walls):
    //               Moves the player up and returns true
    //          Else:
    //               Returns False
    public boolean moveLeft() {
        if (!canBeAtLocation(game.getLevel(), getPosX() - 1, getPosY())) {
            return false;
        }

        // Move to new location
        setPosX(getPosX() - 1);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player right.
    //          If allowed (within bounds and no walls):
    //               Moves the player up and returns true
    //          Else:
    //               Returns False
    public boolean moveRight() {
        if (!canBeAtLocation(game.getLevel(), getPosX() + 1, getPosY())) {
            return false;
        }

        // Move to new location
        setPosX(getPosX() + 1);
        return true;
    }
}
