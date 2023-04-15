package ui.renderers;

import ui.GameWindow;
import ui.helpers.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

/*
 * Renderer for the pause menu.
 * Allows the player to resume the game, save the game, or quit to the main menu.
 */

public class PauseRenderer extends MenuRenderer {
    public PauseRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Setup Buttons
        setupButtons1();
        setupButtons2();
    }

    // MODIFIES: this
    // EFFECTS: Generates and Initializes the Background
    @Override
    public void initRenderer() {
        super.initRenderer();

        // Generates the background
        setBackground(generateBlurBackground(gameWindow.getGameRenderer()));
    }

    // MODIFIES: this
    // EFFECTS: Sets up the resume, save, and load buttons for the pause menu
    private void setupButtons1() {
        // Add Resume Button
        addButton(new CustomButton(this, 16, 60, "RESUME", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // Add Save Button
        addButton(new CustomButton(this, 16, 100, "SAVE GAME", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.saveGame();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // Add Load Button
        addButton(new CustomButton(this, 16, 140, "LOAD LAST SAVE", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.loadGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));
    }

    // MODIFIES: this
    // EFFECTS: Sets up the new game, quit, and force quit buttons for the pause menu
    private void setupButtons2() {
        // Add Save and Quit Button
        addButton(new CustomButton(this, 16, 220, "CREATE NEW GAME", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.newGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // Add Save and Quit Button
        addButton(new CustomButton(this, 16, 260, "SAVE AND QUIT", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.saveGame();
                gameWindow.quitGame();
            }
        }));

        // Add Quit without Saving Button
        addButton(new CustomButton(this, 16, 300, "QUIT WITHOUT SAVING", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.quitGame();
            }
        }));
    }

    // MODIFIES: g
    // EFFECTS: Renders pause menu
    // EFFECTS: Renders pause menu
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        renderBackground(g);

        // Draw pause menu in the center of the screen
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("GAME PAUSED", 16, 48);

        // Draw Buttons
        renderButtons(g);

        // Draw Debug Info
        renderDebugInfo(g);
    }
}
