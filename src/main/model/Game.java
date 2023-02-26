package model;

import model.enemies.Guard;
import model.enemies.Juggernaut;

import java.util.Random;

public class Game {
    public static final int TPS = 32;

    // Information on level
    private final int sizeX;
    private final int sizeY;
    private final Player player;
    private final Level gameLevel;

    // Random Number Generator
    protected Random random;

    // EFFECTS: Creates a game object with levels and player
    public Game(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize Random
        random = new Random();

        // Initialize First Level
        gameLevel = new Level(1, this, sizeX, sizeY, 2, 2);

        // Initialize Player
        player = new Player(this);

        // Spawn Player
        gameLevel.spawnPlayer(player);

        // Initialize Enemies in level
        gameLevel.initLevel();
    }

    // EFFECTS: Prints the end game screen
    public void quit() {
        // TODO: STUB
    }

    //
    // Getters
    //

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

    public Random getRandom() {
        return random;
    }
}
