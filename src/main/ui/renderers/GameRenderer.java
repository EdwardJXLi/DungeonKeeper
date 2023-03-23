package ui.renderers;

import model.Player;
import model.ScreenElement;
import model.graphics.SpriteID;
import ui.GraphicalGame;
import ui.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameRenderer extends Renderer {
    BufferedImage background;

    public GameRenderer(GraphicalGame gameWindow) {
        super(gameWindow);


        // Initialize Background
        initializeBackground();
    }

    private void initializeBackground() {
        // Initialize Background
        background = new BufferedImage(
                gameWindow.getWindowSizeX(),
                gameWindow.getWindowSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(
                0, 0,
                gameWindow.getWindowSizeX(),
                gameWindow.getWindowSizeY()
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

        // Draw Screen Renderables
        renderScreenElements(g);

        // Draw Player
        Player player = game.getPlayer();
        drawSprite(
                g, spriteManager.getSprite(player.getSpriteID()),
                player.getPosX(), player.getPosY(),
                gameWindow.getTick()
        );

        // Draw Debug Info
        renderDebugInfo(g);
    }

    // EFFECTS: Draws all tiles and enemies to screen
    private void renderScreenElements(Graphics g) {
        // Render Tiles
        for (ScreenElement e : game.getLevel().getTiles()) {
            drawSprite(g, spriteManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Enemies
        for (ScreenElement e : game.getLevel().getEnemies()) {
            drawSprite(g, spriteManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Dropped Items
        for (ScreenElement e : game.getLevel().getDroppedItems()) {
            drawSprite(g, spriteManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }
    }

    // EFFECTS: Draws debug info to screen
    private void renderDebugInfo(Graphics g) {
        // Setup Fonts
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 18));

        // Add Debug Text
        List<String> debugInfo = new ArrayList<>();
        debugInfo.add("== GAME DEBUG ==");
        debugInfo.add("Game Tick: " + gameWindow.getTick());
        debugInfo.add("Player Position: " + game.getPlayer().getPosX() + ", " + game.getPlayer().getPosY());

        // Draw Debug Text
        for (int i = 0; i < debugInfo.size(); i++) {
            g.drawString(debugInfo.get(i), 0, 18 * (i + 1));
        }
    }
}
