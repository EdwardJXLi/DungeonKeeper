package ui.renderers;

import ui.GameWindow;
import ui.helpers.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/*
 * Game Over Screen. Displays when the player dies. Allows the player to create a new game.
 */

public class GameOverRenderer extends MenuRenderer {
    public GameOverRenderer(GameWindow gameWindow) {
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
        setBackground(generateBlurBackground(gameWindow.getGameRenderer()));
    }

    // MODIFIES: this
    // EFFECTS: Sets up the buttons for the pause menu
    private void setupButtons() {
        // Add New Game Button
        addButton(new CustomButton(this, 16, 120, "NEW GAME", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.newGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // Load from last save
        addButton(new CustomButton(this, 16, 160, "LOAD LAST SAVE", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.loadGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // Add Quit without Saving Button
        addButton(new CustomButton(this, 16, 200, "QUIT", 24, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameWindow.quitGame();
            }
        }));
    }

    // EFFECTS: Overrides Keyboard Input to prevent leaving game over screen
    @Override
    public void onKeyPress(KeyEvent e) {
        // Do nothing
    }

    // MODIFIES: g
    // EFFECTS: Renders game over screen
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        renderBackground(g);

        // Draw pause menu in the center of the screen
        g.setColor(Color.RED);
        g.setFont(textureManager.getFont(96));
        g.drawString("GAME OVER", 16, 96);

        // Draw Buttons
        renderButtons(g);

        // Draw Debug Info
        renderDebugInfo(g);
    }
}
