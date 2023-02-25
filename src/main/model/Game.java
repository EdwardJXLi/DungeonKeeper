package model;

public class Game {
    public static final int TPS = 20;

    // Information on level
    private int sizeX;
    private int sizeY;
    private Level gameLevel;

    public Game(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize First Level
        gameLevel = new Level(1, sizeX, sizeY);
    }

    // EFFECTS: Prints the end game screen
    public void quit() {
        // TODO: STUB
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Level getLevel() {
        return gameLevel;
    }
}
