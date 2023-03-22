package ui;

import ui.panels.GamePanel;
import ui.sprites.SpriteManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicalGame extends JFrame {
    public static final int SPRITE_SIZE = 16;
    public static final int SCALE = 2;

    private Canvas canvas;

    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;

    private GamePanel gamePanel;

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

        // Set up graphical UI
        setSize(windowSizeX, windowSizeY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create and add all panels
        gamePanel = new GamePanel();
        add(gamePanel);

        // Initialize Graphics and Sprites
        spriteManager = new SpriteManager("assets/texturepack.json");

        // Initialize UI and Game
//        setVisible(true);
    }
}
