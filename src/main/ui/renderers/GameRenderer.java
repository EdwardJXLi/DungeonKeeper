package ui.renderers;

import model.graphics.SpriteID;
import ui.GraphicalGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameRenderer extends Renderer {
    BufferedImage background;

    public GameRenderer(GraphicalGame graphicalGame) {
        super(graphicalGame);


        // Initialize Background
        initializeBackground();
    }

    private void initializeBackground() {
        background = new BufferedImage(
                graphicalGame.getWindowSizeX(),
                graphicalGame.getWindowSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(
                0, 0,
                graphicalGame.getWindowSizeX(),
                graphicalGame.getWindowSizeY()
        );
        for (int y = 0; y < game.getSizeY(); y++) {
            for (int x = 0; x < game.getSizeX(); x++) {
                drawSprite(g, spriteManager.getSprite(SpriteID.TILE_FLOOR), x, y);
            }
        }
        g.dispose();
    }

    @Override
    public void paint(Graphics g) {
        // Draw Background
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawString("Tick: " + graphicalGame.getTick(), 8, 16);
        System.out.println(graphicalGame.getWindowSizeX() + " " + graphicalGame.getWindowSizeY());
    }
}
