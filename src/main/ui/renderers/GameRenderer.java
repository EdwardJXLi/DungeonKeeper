package ui.renderers;

import model.graphics.SpriteID;
import ui.GraphicalGame;
import ui.sprites.Sprite;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameRenderer extends Renderer {
    BufferedImage background;

    public GameRenderer(GraphicalGame graphicalGame) {
        super(graphicalGame);


        // Initialize Background
        initializeBackground();
    }

    private void initializeBackground() {
        // Initialize Background
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

        // Get list of floor sprites
        List<Sprite> floorSprites = spriteManager.getSpriteList(SpriteID.TILE_FLOOR);
        Sprite floorSprite;

        // Set up RNG to get random floor sprites
        Random rng = new Random(1337);

        // Draw floors
        for (int y = 0; y < game.getSizeY(); y++) {
            for (int x = 0; x < game.getSizeX(); x++) {
                // Quick hack for a varied floor
                floorSprite = floorSprites.get(rng.nextInt(floorSprites.size()));

                drawSprite(g, floorSprite, x, y);
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
