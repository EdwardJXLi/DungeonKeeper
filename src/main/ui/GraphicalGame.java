package ui;

import model.Game;
import model.logging.Event;
import model.logging.EventLog;
import persistence.GameReader;
import persistence.GameWriter;
import persistence.SaveGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Main class for the graphical game.
 * Initializes the game, timer, and the game window.
 * Handles game loop and save/loading.
 */

public class GraphicalGame {
    // Game Constants
    public static final double DEFAULT_SCALE = 1.5;
    public static final String SAVE_LOCATION = "./data/saveGame.json";
    // This is supposed to be in the root folder
    public static final String CHEERPJ_SAVE_LOCATION = "/files/saveGame.json";

    // Game Variables
    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;
    private final double scale;
    private final boolean isCheerpJ;

    // Game Objects
    private final GameWindow gameWindow;
    private Game game;

    // Game Loop
    private int tick;
    private Timer timer;

    // EFFECTS: initializes a new game with the given size and scale
    public GraphicalGame(int sizeX, int sizeY, double scale) {
        // Set up game variables
        this.gameSizeX = sizeX;
        this.gameSizeY = sizeY;
        this.scale = scale;
        this.windowSizeX = (int) (sizeX * GameWindow.BASE_SPRITE_SIZE * scale);
        this.windowSizeY = (int) (sizeY * GameWindow.BASE_SPRITE_SIZE * scale);
        this.isCheerpJ = new File("/app/index.html").exists();

        // Initialize UI
        gameWindow = new GameWindow(windowSizeX, windowSizeY, this);

        // Setup tick timer
        tick = 0;
        addTimer();
        timer.start();
    }

    // EFFECTS: initializes a new game with the given size and default scale
    public GraphicalGame(int sizeX, int sizeY) {
        this(sizeX, sizeY, DEFAULT_SCALE);
    }

    // Set up timer
    // MODIFIES: this
    // EFFECTS:  initializes a timer that updates game each tick with time
    //           determined by the game TPS
    private void addTimer() {
        timer = new Timer(1000 / Game.TPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Only tick if game is running and not paused
                if (game != null && game.isGameRunning() && !gameWindow.isPaused()) {
                    tick++;
                    gameWindow.repaint();
                    game.handleNextTick(tick);
                    // Else, just draw the game, but do not tick
                } else {
                    // Just draw but do not tick!
                    gameWindow.repaint();
                }

                // Check if player wins game. If so, end game.
                if (game != null && game.isGameWon()) {
                    gameWindow.switchRenderer(gameWindow.getGameWinRenderer(), true);
                }

                // Check if player is dead. If so, end game
                if (game != null && game.getPlayer().isDead()) {
                    gameWindow.switchRenderer(gameWindow.getGameOverRenderer(), true);
                }
            }
        });
    }

    // EFFECTS: Saves the game to file
    public void saveGame() throws FileNotFoundException {
        // Determine if it should save to cheerpJ's IndexedDB-based filesystem
        GameWriter gameWriter;
        if (!isCheerpJ) {
            gameWriter = new GameWriter(SAVE_LOCATION);
        } else {
            gameWriter = new GameWriter(CHEERPJ_SAVE_LOCATION);
        }

        // Save game to file
        SaveGame saveGame = gameWriter.createSaveGame(game, tick);
        gameWriter.open();
        gameWriter.write(saveGame);
        gameWriter.close();
    }

    // REQUIRES: Save File Exists
    // MODIFIES: this
    // EFFECTS: Loads The Game from File
    public void loadGame() throws IOException {
        // Determine if it should load from cheerpJ's IndexedDB-based filesystem
        GameReader gameReader;
        if (!isCheerpJ) {
            gameReader = new GameReader(SAVE_LOCATION);
        } else {
            gameReader = new GameReader(CHEERPJ_SAVE_LOCATION);
        }

        // Load game from file
        SaveGame saveGame = gameReader.read();
        game = saveGame.getGame();
        tick = saveGame.getTick();
    }

    // MODIFIES: this
    // EFFECTS: Initializes New Game
    public void newGame() {
        game = new Game(gameSizeX, gameSizeY);
        game.initGame();
        tick = 0;
    }

    // MODIFIES: this
    // EFFECTS: Quits Game
    public void quitGame() {
        // Print out all logs
        EventLog logger = EventLog.getInstance();

        System.out.println("Game Logs:");
        for (Event event : logger) {
            System.out.println(event);
        }

        System.exit(0);
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public Game getGame() {
        return game;
    }

    public int getTick() {
        return tick;
    }

    public Timer getTimer() {
        return timer;
    }

    public double getScale() {
        return scale;
    }

    public int getGameSizeX() {
        return gameSizeX;
    }

    public int getGameSizeY() {
        return gameSizeY;
    }

    public int getWindowSizeX() {
        return windowSizeX;
    }

    public int getWindowSizeY() {
        return windowSizeY;
    }

    public boolean isCheerpJ() {
        return isCheerpJ;
    }
}
