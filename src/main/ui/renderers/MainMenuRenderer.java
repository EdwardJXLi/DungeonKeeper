package ui.renderers;

import ui.GameWindow;
import ui.GraphicalGame;
import ui.helpers.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/*
 * Renderer for the main menu.
 * Allows the player to start a new game, load a game, or quit the game.
 */

public class MainMenuRenderer extends MenuRenderer {
    public MainMenuRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Setup Buttons
        setupButtons();
    }

    // MODIFIES: this
    // EFFECTS: Generates and Initializes the Background
    @Override
    public void initRenderer() {
        super.initRenderer();

        // Generates the background
        setBackground(generateBackground());
    }

    // MODIFIES: this
    // EFFECTS: Sets up the buttons for the main menu
    private void setupButtons() {
        // Check if save file exists
        boolean saveFileExists = new File(GraphicalGame.SAVE_LOCATION).exists();

        // Add New Game Button
        addButton(new CustomButton(this, 16, 60, "NEW GAME", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.createGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // If a save file exists, render option to use file
        if (saveFileExists) {
            // Add Load Game Button
            addButton(new CustomButton(this, 16, 100, "LOAD GAME", 24, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameWindow.loadGame();
                    gameWindow.initGameRenderers();
                    gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                }
            }));
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles Keyboard Input.
    @Override
    public void onKeyPress(KeyEvent e) {
        // DO not call super.onKeyPress(e)! We want to override all functionality of keypress

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                gameWindow.quitGame();
                break;
            case KeyEvent.VK_ENTER:
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Renders the title
    public void renderTitle(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("Yet Unnamed Dungeon Crawler", 16, 48);
    }

    // MODIFIES: g
    // EFFECTS: Renders Background
    public BufferedImage generateBackground() {
        // Draw Background as black box
        BufferedImage background = new BufferedImage(
                gameWindow.getSizeX(),
                gameWindow.getSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gameWindow.getSizeX(), gameWindow.getSizeY());

        return background;
    }

    // MODIFIES: g
    // EFFECTS: Renders main menu
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        renderBackground(g);

        // Draw Title
        renderTitle(g);

        // Draw buttons
        renderButtons(g);
    }
}
