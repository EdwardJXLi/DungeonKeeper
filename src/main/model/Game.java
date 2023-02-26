package model;

import model.enemies.Guard;
import model.enemies.Juggernaut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static final int TPS = 32;

    // Information on level
    private final int sizeX;
    private final int sizeY;
    private final Player player;
    private final Level gameLevel;

    // Random Number Generator
    private Random random;

    // Game Messages
    private ArrayList<String> gameMessages;

    // EFFECTS: Creates a game object with levels and player
    public Game(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize Random
        random = new Random();

        // Initialize Messages
        gameMessages = new ArrayList<>();

        // Initialize First Level
        gameLevel = new Level(1, this, sizeX, sizeY, 2, 2);

        // Initialize Player
        player = new Player(this);

        // Spawn Player
        gameLevel.spawnPlayer(player);

        // Initialize Enemies in level
        gameLevel.initLevel();

        // Send Welcome Message
        sendMessage("Welcome to Yet Unnamed Dungeon Crawler!");
    }

    // MODIFIES: this
    // EFFECTS: For each tick:
    //          Handles next tick for player and enemies
    //          Chance of randomly spawning items
    public void handleNextTick(int tick) {
        // Next tick for all enemies
        for (Enemy e: getLevel().getEnemies()) {
            e.handleNextTick(tick);
        }

        // Next tick for player
        getPlayer().handleNextTick(tick);

        // Handle and Remove Dead Enemies
        for (Enemy e: getLevel().getEnemies()) {
            if (e.isDead()) {
                e.onDeath();
            }
        }
        getLevel().getEnemies().removeIf(e -> e.isDead());
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

    public void sendMessage(String message) {
        gameMessages.add(message);
    }

    public List<String> getMessages() {
        return gameMessages;
    }

    // EFFECTS: Returns the last n messages from game messages
    public List<String> getLastMessages(int n) {
        ArrayList<String> lastMessages = new ArrayList<>();

        // Iterate backwards through list of messages, and add them to a new list
        for (int i = gameMessages.size() - 1; i >= Math.max(gameMessages.size() - n, 0); i--) {
            lastMessages.add(0, gameMessages.get(i));
        }

        return lastMessages;
    }
}
