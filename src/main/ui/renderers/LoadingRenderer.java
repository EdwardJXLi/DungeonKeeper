package ui.renderers;

import model.Player;
import ui.GameWindow;

import java.awt.*;

import static java.lang.Thread.sleep;

public class LoadingRenderer extends Renderer {
    String currentLoadingInfo;

    public LoadingRenderer(GameWindow gameWindow) {
        super(gameWindow);
        currentLoadingInfo = "Initializing Game...";
    }

    // MODIFIES: this
    // EFFECTS: Sets the current loading information
    public void setCurrentLoadingInfo(String currentLoadingInfo) {
        this.currentLoadingInfo = currentLoadingInfo;
        gameWindow.repaint();
    }

    // MODIFIES: g
    // EFFECTS: Renders the current loading information on screen
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Clear Screen and paint screen black
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, gameWindow.getSizeX(), gameWindow.getSizeY());

        // Draw Current Loading Information
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Loading Dungeon Keeper...", 10, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(currentLoadingInfo, 10, 60);
    }
}
