package ui;

import model.Game;
import model.logging.Event;
import model.logging.EventLog;
import ui.helpers.TextureManager;
import ui.renderers.*;
import ui.renderers.Renderer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/*
 * Main class for GUI game rendering and control handling.
 */

public class GameWindow extends JFrame {
    // Renderer Constants
    public static final int BASE_SPRITE_SIZE = 16;

    // Renderer Variables
    private final int sizeX;
    private final int sizeY;
    private final double scale;
    private final GraphicalGame graphicalGame;
    private boolean paused;
    private boolean debug;

    // Renderers
    private LoadingRenderer loadingRenderer;
    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;
    private MainMenuRenderer mainMenuRenderer;
    private PauseRenderer pauseRenderer;
    private GameOverRenderer gameOverRenderer;
    private GameWinRenderer gameWinRenderer;
    private InventoryRenderer inventoryRenderer;
    private Renderer currentRenderer;

    // Texture Manager
    private TextureManager textureManager;

    // TODO: Feature unfinished, but later be able to hotswap textures
    private List<TextureManager> textureManagers;

    // EFFECTS: Creates and Initializes Game of size X and Y
    public GameWindow(int sizeX, int sizeY, GraphicalGame graphicalGame) {
        // Create UI
        super("Dungeon Keeper - V1.0.0");

        // Set up window variables
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.graphicalGame = graphicalGame;
        this.scale = graphicalGame.getScale();

        // Initialize Loading Screen
        loadingRenderer = new LoadingRenderer(this);

        // Init loadingRenderer and add Input Handling
        currentRenderer = loadingRenderer;
        add(currentRenderer, 0);
        currentRenderer.initRenderer();
        currentRenderer.initUserInputHandlers();

        // Set up graphical UI
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setDebug(false);
        setPaused(false);

        // Initialize UI and Rendering
        setVisible(true);

        // Initialize Graphics and Sprites
        int spriteSize = (int) (BASE_SPRITE_SIZE * scale);
        textureManager = new TextureManager("texturepack.json", scale, spriteSize,
                loadingRenderer, graphicalGame.isCheerpJ());

        // Initialize Main Menu
        mainMenuRenderer = new MainMenuRenderer(this);

        // Switch Renderer
        switchRenderer(mainMenuRenderer, false);

        // Setup Window Close Handler
        addWindowListener(getWindowCloseAdapter());
    }

    // MODIFIES: this
    // EFFECTS: Initializes Game Renderers
    public void initGameRenderers() {
        // Initialize Renderers and create all panels
        testRenderer = new TestRenderer(this);
        gameRenderer = new GameRenderer(this);
        pauseRenderer = new PauseRenderer(this);
        gameOverRenderer = new GameOverRenderer(this);
        gameWinRenderer = new GameWinRenderer(this);
        inventoryRenderer = new InventoryRenderer(this);
    }

    // MODIFIES: this, other
    // EFFECTS: Helper method to switch scenes
    public void switchRenderer(Renderer other, boolean pause) {
        // Remove current renderer from JFrame and add the other
        remove(currentRenderer);
        add(other, 0);

        // Initialize the other renderer and switch input handling
        other.initRenderer();
        currentRenderer.switchInputHandlers(other);

        // Redraw graphics and update tick handler
        setPaused(pause);
        pack();
        repaint();
        currentRenderer = other;
    }

    // MODIFIES: this
    // EFFECTS: Creates Game
    public void createGame() {
        graphicalGame.newGame();
    }

    // MODIFIES: this
    // EFFECTS: Saves Game To File
    public void saveGame() {
        try {
            graphicalGame.saveGame();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save game to file: " + e);
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads Game From File
    public void loadGame() {
        try {
            graphicalGame.loadGame();
        } catch (IOException e) {
            System.out.println("Unable to load game from file: " + e);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a brand new game
    public void newGame() {
        graphicalGame.newGame();
    }

    // MODIFIES: this
    // EFFECTS: Quits Game
    public void quitGame() {
        graphicalGame.quitGame();
    }

    // EFFECTS: Returns WindowAdapter that handles on window close.
    private static WindowAdapter getWindowCloseAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // Print out all logs
                EventLog logger = EventLog.getInstance();

                System.out.println("Game Logs:");
                for (Event event : logger) {
                    System.out.println(event);
                }
            }
        };
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
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

    public GraphicalGame getGraphicalGame() {
        return graphicalGame;
    }

    public double getScale() {
        return scale;
    }

    public int getFPS() {
        // TODO
        return -1;
    }

    public LoadingRenderer getLoadingRenderer() {
        return loadingRenderer;
    }

    public TestRenderer getTestRenderer() {
        return testRenderer;
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    public MainMenuRenderer getMainMenuRenderer() {
        return mainMenuRenderer;
    }

    public PauseRenderer getPauseRenderer() {
        return pauseRenderer;
    }

    public GameOverRenderer getGameOverRenderer() {
        return gameOverRenderer;
    }

    public GameWinRenderer getGameWinRenderer() {
        return gameWinRenderer;
    }

    public InventoryRenderer getInventoryRenderer() {
        return inventoryRenderer;
    }

    public Renderer getCurrentRenderer() {
        return currentRenderer;
    }
}
