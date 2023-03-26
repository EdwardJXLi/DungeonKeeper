package ui;

import model.Game;
import ui.renderers.GameRenderer;
import ui.renderers.PauseRenderer;
import ui.renderers.Renderer;
import ui.renderers.TestRenderer;

import javax.swing.*;

public class GameWindow extends JFrame {
    public static final int BASE_SPRITE_SIZE = 16;
    private final int sizeX;
    private final int sizeY;
    private final double scale;
    private boolean paused;

    private GraphicalGame graphicalGame;

    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;
    private PauseRenderer pauseRenderer;
    private Renderer currentRenderer;

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
        testRenderer = new TestRenderer(this);
        gameRenderer = new GameRenderer(this);
        pauseRenderer = new PauseRenderer(this);

        // Init gameRenderer and add Input Handling
        currentRenderer = gameRenderer;
        add(gameRenderer, 0);
        gameRenderer.initUserInputHandlers();

        // Set up graphical UI
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize UI and Rendering
        setVisible(true);
    }

    // EFFECTS: Helper method to switch scenes
    // MODIFIES: this, other
    public void switchRenderer(Renderer other, boolean pause) {
        remove(currentRenderer);
        add(other, 0);
        currentRenderer.switchInputHandlers(other);
        setPaused(pause);
        pack();
        repaint();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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

    public TestRenderer getTestRenderer() {
        return testRenderer;
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    public PauseRenderer getPauseRenderer() {
        return pauseRenderer;
    }
}
