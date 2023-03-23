package ui;

import model.Game;
import ui.renderers.GameRenderer;
import ui.renderers.HudRenderer;
import ui.renderers.TestRenderer;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    public static final int BASE_SPRITE_SIZE = 16;
    private final int sizeX;
    private final int sizeY;

    private GraphicalGame graphicalGame;

    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;
    private HudRenderer hudRenderer;

    private SpriteManager spriteManager;

    // EFFECTS: Creates and Initializes Game of size X and Y
    public GameWindow(int sizeX, int sizeY, GraphicalGame graphicalGame) {
        // Create UI
        super("Untitled Roguelike");

        // Set up window variables
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.graphicalGame = graphicalGame;

        // Initialize Graphics and Sprites
        int spriteSize = (int) (BASE_SPRITE_SIZE * graphicalGame.getSpriteScale());
        spriteManager = new SpriteManager("assets/texturepack.json", spriteSize);

        // Create and add all panels
//        testRenderer = new TestRenderer(this);
//        getContentPane().add(testRenderer);
//        testRenderer.initUserInputHandlers();
        gameRenderer = new GameRenderer(this);
        add(gameRenderer, 0);
        hudRenderer = new HudRenderer(this);
//        add(hudRenderer, 1);
        gameRenderer.initUserInputHandlers();

        // Set up graphical UI
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize UI and Rendering
        setVisible(true);
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getTick() {
        return graphicalGame.getTick();
    }

    public Game getGame() {
        return graphicalGame.getGame();
    }

    public int getFPS() {
        // TODO
        return -1;
    }
}