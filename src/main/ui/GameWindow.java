package ui;

import model.Game;
import ui.renderers.GameRenderer;
import ui.renderers.HudRenderer;
import ui.renderers.TestRenderer;

import javax.swing.*;

public class GameWindow extends JFrame {
    public static final int BASE_SPRITE_SIZE = 16;
    private final int sizeX;
    private final int sizeY;
    private final double scale;

    private GraphicalGame graphicalGame;

    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;
    private HudRenderer hudRenderer;

    private TextureManager textureManager;

    // EFFECTS: Creates and Initializes Game of size X and Y
    public GameWindow(int sizeX, int sizeY, GraphicalGame graphicalGame) {
        // Create UI
        super("Untitled Roguelike");

        // Set up window variables
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.graphicalGame = graphicalGame;
        this.scale = graphicalGame.getScale();

        // Initialize Graphics and Sprites
        int spriteSize = (int) (BASE_SPRITE_SIZE * scale);
        textureManager = new TextureManager("assets/texturepack.json", scale, spriteSize);

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

    public TextureManager getSpriteManager() {
        return textureManager;
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

    public double getScale() {
        return scale;
    }

    public int getFPS() {
        // TODO
        return -1;
    }
}
