package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    // Game Constants
    public static final int TPS = 20;
    public static final String WELCOME_MESSAGE = "Welcome to Yet Unnamed Dungeon Crawler!";
    private static final int SPAWN_X = 2;
    private static final int SPAWN_Y = 2;

    // Information on level
    private final int sizeX;
    private final int sizeY;
    // Random Number Generator
    private final Random random;
    // Game Messages
    private final List<String> gameMessages;
    private Player player;
    private Level gameLevel;

    // REQUIRES: sizeX > 32 and sizeY > 24
    // EFFECTS: Creates a game object with levels and player
    public Game(int sizeX, int sizeY) {
        // Assert requires
        assert sizeX >= 32;
        assert sizeY >= 24;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize Random
        random = new Random();

        // Initialize Messages
        gameMessages = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Starts up game by generating map, spawning enemies, and initializing player
    public void initGame() {
        // Initialize First Level
        gameLevel = new Level(1, this, sizeX, sizeY, SPAWN_X, SPAWN_Y);
        gameLevel.initLevel();

        // Initialize Player
        player = new Player(this);
        player.initPlayer();

        // Spawn Player
        gameLevel.spawnPlayer(player);

        // Send Welcome Message
        sendMessage(WELCOME_MESSAGE);
    }

    // MODIFIES: this
    // Creates a completely empty map
    public void initEmptyGame() {
        gameLevel = new Level(1, this, sizeX, sizeY, SPAWN_X, SPAWN_Y);
    }

    // REQUIRES: Next tick is 1 greater than previous tick
    // MODIFIES: this
    // EFFECTS: For each tick:
    //          - Handles next tick for player and enemies
    //          - Removes dead enemies from map
    //          - Occasionally spawns new items and enemies
    public void handleNextTick(int tick) {
        // Next tick for all enemies
        for (Enemy e : getLevel().getEnemies()) {
            e.handleNextTick(tick);
        }

        // Next tick for player
        getPlayer().handleNextTick(tick);

        // Handle and Remove Dead Enemies
        for (Enemy e : getLevel().getEnemies()) {
            if (e.isDead()) {
                e.onDeath();
            }
        }
        getLevel().getEnemies().removeIf(Entity::isDead);
    }

    // EFFECTS: Prints the end game screen
    public void quit() {
        // TODO: STUB
    }

    // REQUIRES: n > 0
    // EFFECTS: Returns the last n messages from game messages
    public List<String> getLastMessages(int n) {
        assert n > 0;

        // Create new array list of last messages
        ArrayList<String> lastMessages = new ArrayList<>();

        // Iterate backwards through list of messages, and add them to a new list
        for (int i = gameMessages.size() - 1; i >= Math.max(gameMessages.size() - n, 0); i--) {
            lastMessages.add(0, gameMessages.get(i));
        }

        return lastMessages;
    }

    //
    // Getters and Setters
    //

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
}
