package ui;

import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Main class for the graphical game.
 * Initializes the game, timer, and the game window.
 * Handles game loop and save/loading.
 */

public class GraphicalGame {
    // Game Constants
    public static final double DEFAULT_SCALE = 1.5;

    // Game Variables
    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;
    private final double scale;

    // Game Objects
    private GameWindow gameWindow;
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

        // Initialize Game
        tick = 0;
        game = new Game(sizeX, sizeY);
        game.initGame();

        // Initialize UI
        gameWindow = new GameWindow(windowSizeX, windowSizeY, this);

        // Setup tick timer
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
                if (game.isGameRunning() && !gameWindow.isPaused()) {
                    tick++;
                    gameWindow.repaint();
                    game.handleNextTick(tick);
                // If game is paused, just draw the game, but do not tick
                } else if (game.isGameRunning() && gameWindow.isPaused()) {
                    // Just draw but do not tick!
                    gameWindow.repaint();
                // If dead, render death screen and quit game.
                } else {
                    // TODO: DEATH
                    System.out.println("TODO: Game over!");
                    timer.stop();
                }
            }
        });
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
}
