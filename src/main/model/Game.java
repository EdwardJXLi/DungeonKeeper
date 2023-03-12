package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Main class for all game components
 */

public class Game implements Writable {
    // Game Constants
    public static final int TPS = 20;
    public static final String WELCOME_MESSAGE = "Welcome to Yet Unnamed Dungeon Crawler!";
    public static final int SPAWN_X = 2;
    public static final int SPAWN_Y = 2;

    // Information on level
    private final int sizeX;
    private final int sizeY;
    // Random Number Generator
    private final Random random;
    // Game Messages
    private final List<String> gameMessages;
    private Player player;
    private Level gameLevel;
    // Game Status
    private boolean gameRunning;

    // REQUIRES: sizeX > 32 and sizeY > 24
    // EFFECTS: Creates a game object with levels and player
    public Game(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Initialize Random
        random = new Random();

        // Initialize Messages
        gameMessages = new ArrayList<>();

        // Start Game
        gameRunning = true;
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

    // EFFECTS: Ends game
    public void gameOver() {
        this.gameRunning = false;
    }

    // REQUIRES: n > 0
    // EFFECTS: Returns the last n messages from game messages
    public List<String> getLastMessages(int n) {
        // Create new array list of last messages
        ArrayList<String> lastMessages = new ArrayList<>();

        // Iterate backwards through list of messages, and add them to a new list
        for (int i = gameMessages.size() - 1; i >= Math.max(gameMessages.size() - n, 0); i--) {
            lastMessages.add(0, gameMessages.get(i));
        }

        return lastMessages;
    }

    // EFFECTS: Returns JSON Representation of a Game object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sizeX", sizeX);
        json.put("sizeY", sizeY);
        json.put("gameMessages", gameMessagesToJson());
        json.put("player", player.toJson());
        json.put("level", gameLevel.toJson());
        return json;
    }

    // EFFECTS: Returns JSON Array Representation of Game Objects
    public JSONArray gameMessagesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String m : gameMessages) {
            jsonArray.put(m);
        }
        return jsonArray;
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

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }
}
