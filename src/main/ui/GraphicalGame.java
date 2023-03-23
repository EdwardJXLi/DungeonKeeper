package ui;

import model.Game;
import ui.renderers.GameRenderer;
import ui.renderers.Renderer;
import ui.renderers.TestRenderer;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalGame extends JFrame {
    public static final int SPRITE_SIZE = 16;
    public static final int SCALE = 2;

    private Canvas canvas;

    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;

    private int tick;
    private Timer timer;
    private Game game;

    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;

    private SpriteManager spriteManager;

    // EFFECTS: Creates and Initializes Game of size X and Y
    public GraphicalGame(int sizeX, int sizeY) {
        // Create UI
        super("Untitled Roguelike");

        // Set up game variables
        gameSizeX = sizeX;
        gameSizeY = sizeY;
        windowSizeX = sizeX * SPRITE_SIZE * SCALE;
        windowSizeY = sizeY * SPRITE_SIZE * SCALE;

        // Initialize Graphics and Sprites
        spriteManager = new SpriteManager("assets/texturepack.json");

        // Initialize Game
        tick = 0;
        game = new Game(sizeX, sizeY);
        game.initGame();

        // Create and add all panels
        testRenderer = new TestRenderer(this);
        getContentPane().add(testRenderer);
//        gameRenderer = new GameRenderer(this);
//        add(gameRenderer);

        // Set up graphical UI
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize UI and Rendering
        setVisible(true);
        addTimer();
        timer.start();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(1000 / Game.TPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick++;
                repaint();
                game.handleNextTick(tick);
            }
        });
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
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

    public int getTick() {
        return tick;
    }

    public Game getGame() {
        return game;
    }
}
