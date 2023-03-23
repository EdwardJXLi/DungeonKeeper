package ui;

import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalGame {
    public static final double DEFAULT_SCALE = 1.5;

    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;
    private final double scale;

    private GameWindow gameWindow;
    private Game game;

    private int tick;
    private Timer timer;

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

    public GraphicalGame(int sizeX, int sizeY) {
        this(sizeX, sizeY, DEFAULT_SCALE);
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(1000 / Game.TPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isGameRunning()) {
                    tick++;
                    gameWindow.repaint();
                    game.handleNextTick(tick);
                } else {
                    // TODO: DEATH
                    System.out.println("Game over!");
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
}
