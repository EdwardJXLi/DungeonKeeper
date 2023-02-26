package model;

public class Game {
    public static final int TPS = 32;

    // Information on level
    private int sizeX;
    private int sizeY;
    private Player player;
    private Level gameLevel;

    public Game(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize First Level
        gameLevel = new Level(1, sizeX, sizeY, 2, 2);

        // Initialize Player
        player = new Player(0, 0, this);

        // Spawn Player
        gameLevel.spawnPlayer(player);
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

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return gameLevel;
    }
}
