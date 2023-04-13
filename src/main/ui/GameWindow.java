package ui;

import model.Game;
import model.Level;
import model.Tile;
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
    private TestRenderer testRenderer;
    private GameRenderer gameRenderer;
    private MainMenuRenderer mainMenuRenderer;
    private PauseRenderer pauseRenderer;
    private InventoryRenderer inventoryRenderer;
    private Renderer currentRenderer;

    // Texture Manager
    private TextureManager textureManager;

    // TODO: Feature unfinished, but later be able to hotswap textures
    private List<TextureManager> textureManagers;

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

        // Initialize Main Menu
        initMainMenu();

        // Init mainMenuRenderer and add Input Handling
        currentRenderer = mainMenuRenderer;
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

        // Setup Window Close Handler
        addWindowListener(getWindowCloseAdapter());
    }

    // MODIFIES: this
    // EFFECTS: Initializes Main Menu
    public void initMainMenu() {
        // Initialize Main Menu Renderer
        mainMenuRenderer = new MainMenuRenderer(this);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Game
    public void initGame() {
        // Initialize Renderers and create all panels
        testRenderer = new TestRenderer(this);
        gameRenderer = new GameRenderer(this);
        pauseRenderer = new PauseRenderer(this);
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

    public MainMenuRenderer getMainMenuRenderer() {
        return mainMenuRenderer;
    }

    public PauseRenderer getPauseRenderer() {
        return pauseRenderer;
    }

    public InventoryRenderer getInventoryRenderer() {
        return inventoryRenderer;
    }

    public Renderer getCurrentRenderer() {
        return currentRenderer;
    }
}
