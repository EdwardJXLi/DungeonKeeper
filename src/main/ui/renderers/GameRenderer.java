package ui.renderers;

import model.*;
import model.decorations.FancyWall;
import model.graphics.SpriteID;
import model.tiles.Wall;
import ui.GameWindow;
import ui.helpers.TooltipHelper;
import ui.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
 * Main Render Class for the entire game.
 * Handles rendering of the game state, with map tiles and UI elements.
 * Also handles key presses and mouse clicks.
 */

public class GameRenderer extends Renderer {
    // Game Renderer Constants
    private static final int HEARTS_PER_HEART_ICON = 20;
    private static final int ATTACK_PER_ATTACK_ICON = 5;
    private static final int DEFENSE_PER_DEFENSE_ICON = 10;

    // Game Renderer Helpers
    private final TooltipHelper tooltipHelper;
    private BufferedImage background;

    // EFFECTS: Create Game Renderer
    public GameRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Initialize Helpers
        tooltipHelper = new TooltipHelper(this, textureManager);

        // Initialize Background
        initializeBackground();

        // Initialize Level Decorations
        initializeLevelDecorations(getGame().getLevel());
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Level Decorations
    private void initializeLevelDecorations(Level level) {
        // Check if dynamic walls are supported
        if (textureManager.getFlags().contains("DYNAMIC_WALLS")) {
            initializeDynamicWalls(level);
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Dynamic Walls
    //          This makes it so that connecting walls are drawn as one wall
    private void initializeDynamicWalls(Level level) {
        // Loop through all tiles.
        // If they have a wall next to them on both sides, draw a connecting wall.
        // Else, if they have another wall at a 90 degree angle, draw a connecting wall, for all 4 angles.
        for (Tile tile : level.getTiles()) {
            if (tile instanceof Wall) {
                int tileX = tile.getPosX();
                int tileY = tile.getPosY();
                // Check if tile has walls on both sides and air on bottom
                if (level.getTileAtLocation(tileX - 1, tileY) instanceof Wall
                        && level.getTileAtLocation(tileX + 1, tileY) instanceof Wall
                        && level.getTileAtLocation(tileX, tileY + 1) == null) {
                    level.addDecoration(new FancyWall(tileX, tileY, FancyWall.FancyWallType.TOP));
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles movement key presses.
    private void handleMovementKeypress(KeyEvent e) {
        Player player = game.getPlayer();

        // Handles key presses and moves character
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.moveUp();
                break;
            case KeyEvent.VK_S:
                player.moveDown();
                break;
            case KeyEvent.VK_A:
                player.moveLeft();
                break;
            case KeyEvent.VK_D:
                player.moveRight();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles key presses. Updates game state.
    @Override
    public void onKeyPress(KeyEvent e) {
        super.onKeyPress(e);
        handleMovementKeypress(e);

        // Get Game Info
        Player player = game.getPlayer();
        DroppedItem di = game.getLevel().getDroppedItemAtLocation(player.getPosX(), player.getPosY());

        // Handles key presses and moves character
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E:
                gameWindow.switchRenderer(gameWindow.getInventoryRenderer(), true);
                break;
            case KeyEvent.VK_Q:
                if (di != null) {
                    player.pickupItem(di);
                }
                break;
            case KeyEvent.VK_X:
                if (di != null) {
                    game.getLevel().removeDroppedItem(di);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                gameWindow.switchRenderer(gameWindow.getPauseRenderer(), true);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Renders the initial background
    private void initializeBackground() {
        // Initialize Background
        background = new BufferedImage(
                gameWindow.getSizeX(),
                gameWindow.getSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(
                0, 0,
                gameWindow.getSizeX(),
                gameWindow.getSizeY()
        );

        // Get list of floor sprites
        List<Sprite> floorSprites = textureManager.getSpriteList(SpriteID.TILE_FLOOR);
        Sprite floorSprite;

        // Set up RNG to get random floor sprites
        Random rng = new Random(1337);

        // Draw floors
        for (int y = 0; y < game.getSizeY(); y++) {
            for (int x = 0; x < game.getSizeX(); x++) {
                // Quick hack for a varied floor
                floorSprite = floorSprites.get(rng.nextInt(floorSprites.size()));

                drawMapSprite(g, floorSprite, x, y);
            }
        }
        g.dispose();
    }

    // MODIFIES: g
    // EFFECTS: Renders the game state to the screen
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw Background
        g.drawImage(background, 0, 0, null);

        // Draw Screen Renderables
        renderScreenElements(g);

        // Draw Player
        Player player = game.getPlayer();
        drawMapSprite(
                g, textureManager.getSprite(player.getSpriteID()),
                player.getPosX(), player.getPosY(),
                gameWindow.getTick()
        );

        // Draw Tooltips
        renderTooltips(g);

        // Draw HUD Elements
        renderHudElements(g);

        // Draw Debug Info
        renderDebugInfo(g);
    }

    // MODIFIES: g
    // EFFECTS: Draws all tiles and enemies to screen
    private void renderScreenElements(Graphics g) {
        // Render Tiles
        for (ScreenElement e : game.getLevel().getTiles()) {
            drawMapSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Enemies
        for (ScreenElement e : game.getLevel().getEnemies()) {
            drawMapSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Dropped Items
        for (ScreenElement e : game.getLevel().getDroppedItems()) {
            drawMapSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders Tooltips
    private void renderTooltips(Graphics g) {
        // Only render if mouse is in frame
        if (mouseInFrame) {
            // Get whichever tooltip should show up at mouse location
            BufferedImage tooltip = getTooltipAtMouseLocation();

            // If a tooltip should be rendered, do it.
            if (tooltip != null) {
                // Get the currently highlighted tile
                int tileX = mouseX / textureManager.getSpriteSize();
                int tileY = mouseY / textureManager.getSpriteSize();

                // Draw the hover animation
                drawMapSprite(g, textureManager.getSprite(SpriteID.SELECT_GREEN), tileX, tileY, gameWindow.getTick());

                // Draw the tooltip
                final int tooltipOffset = 15;
                g.drawImage(tooltip, mouseX + tooltipOffset, mouseY + tooltipOffset, null);
            }
        }
    }

    // EFFECTS: Gets tooltip based on where the mouse is hovering
    private BufferedImage getTooltipAtMouseLocation() {
        // Get currently highlighted tile, and initiate tooltip
        int tileX = mouseX / textureManager.getSpriteSize();
        int tileY = mouseY / textureManager.getSpriteSize();

        // First, check if a dropped item is at the hovered location.
        // Else, if it is the player at the hovered location.
        // Else, if it is an entity at the hovered location
        // Else, if it is a tile with a valid description at the hovered location.
        if (game.getLevel().getDroppedItemAtLocation(tileX, tileY) != null) {
            return tooltipHelper.generateDroppedItemTooltip(
                    game.getLevel().getDroppedItemAtLocation(tileX, tileY)
            );
        } else if (tileX == game.getPlayer().getPosX() && tileY == game.getPlayer().getPosY()) {
            return tooltipHelper.generatePlayerTooltip(
                    game.getPlayer()
            );
        } else if (game.getLevel().getEnemyAtLocation(tileX, tileY) != null) {
            return tooltipHelper.generateEnemyTooltip(
                    game.getLevel().getEnemyAtLocation(tileX, tileY)
            );
        } else if (game.getLevel().getTileAtLocation(tileX, tileY) != null) {
            if (game.getLevel().getTileAtLocation(tileX, tileY).getDescription() != null) {
                return tooltipHelper.generateTileTooltip(
                        game.getLevel().getTileAtLocation(tileX, tileY)
                );
            }
        }

        // All conditions failed. No tooltip should be rendered.
        return null;
    }

    // MODIFIES: g
    // EFFECTS: Renders HUD Elements
    private void renderHudElements(Graphics g) {
        // Render Player HUD Elements
        renderPlayerHearts(g);
        renderPlayerDefense(g);
        renderPlayerAttack(g);

        // Renders Game Messages
        renderGameMessages(g);

        // Renders Game Instructions
        renderGameInstructions(g);
    }

    // MODIFIES: g
    // EFFECTS: Renders the Player Hearts
    private void renderPlayerHearts(Graphics g) {
        // Draw Heart Text
        g.setColor(Color.RED);
        g.setFont(textureManager.getFont(20));
        g.drawString("Health: ", 10, 8 + textureManager.getSpriteSize());

        // Draw Empty Hearts (Max Number of Hearts)
        for (int i = 0; i < game.getPlayer().getMaxHealth() / HEARTS_PER_HEART_ICON; i++) {
            drawSprite(
                    g, textureManager.getSprite(SpriteID.SPRITE_HEART_EMPTY),
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)), 10
            );
        }

        // Draw Full Hearts (Current Health)
        for (int i = 0; i < game.getPlayer().getHealth() / HEARTS_PER_HEART_ICON; i++) {
            drawSprite(
                    g, textureManager.getSprite(SpriteID.SPRITE_HEART_FULL),
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)), 10
            );
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the Player Defense
    private void renderPlayerDefense(Graphics g) {
        // Draw Defense Text
        g.setColor(Color.BLUE);
        g.setFont(textureManager.getFont(20));
        g.drawString("DEFENSE: ", 10, 4 + (4 + textureManager.getSpriteSize()) * 2);

        // Draw Defense Points
        for (int i = 0; i < game.getPlayer().getDefense() / DEFENSE_PER_DEFENSE_ICON; i++) {
            drawSprite(
                    g, textureManager.getSprite(SpriteID.SPRITE_ARMOR_FULL),
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)),
                    10 + textureManager.getSpriteSize() + 4
            );
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the Player Attack
    private void renderPlayerAttack(Graphics g) {
        // Draw Defense Text
        g.setColor(Color.ORANGE);
        g.setFont(textureManager.getFont(20));
        g.drawString("STRENGTH: ", 10, 4 + (4 + textureManager.getSpriteSize()) * 3);

        // Draw Attack Points
        for (int i = 0; i < game.getPlayer().getAttack() / ATTACK_PER_ATTACK_ICON; i++) {
            drawSprite(
                    g, textureManager.getSprite(SpriteID.SPRITE_STRENGTH),
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)),
                    10 + (textureManager.getSpriteSize() + 4) * 2
            );
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders Game Messages
    private void renderGameMessages(Graphics g) {
        // Setup Fonts
        g.setFont(textureManager.getFont(12));

        // Get Game Messages
        List<String> gameMessages = game.getLastMessages(5);

        // Renders game messages on bottom left corner of screen, backwards
        for (int i = 0; i < gameMessages.size(); i++) {
            // Render with slight gradient as messages go on
            g.setColor(new Color(
                    255, 200, 255,
                    250 - (int) (200 * ((float) i / (float) gameMessages.size()))
            ));
            g.drawString(
                    gameMessages.get(gameMessages.size() - i - 1),
                    10,
                    gameWindow.getHeight() - 50 - (i * textureManager.getFont(12).getSize())
            );
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders Game Instructions
    private void renderGameInstructions(Graphics g) {
        // Setup Fonts
        g.setFont(textureManager.getFont(16));

        // Renders Inventory Instructions
        g.setColor(new Color(255, 255, 255, 128));
        g.drawString(
                "Press [E] to open your inventory.",
                textureManager.getFont(16).getSize() * 18,
                gameWindow.getHeight() - textureManager.getFont(16).getSize() * 4
        );
        g.drawString(
                "Use [W] [A] [S] [D] to move.",
                textureManager.getFont(16).getSize() * 22,
                gameWindow.getHeight() - textureManager.getFont(16).getSize() * 3
        );
    }

    // MODIFIES: g
    // EFFECTS: Returns the rendered game screen as a BufferedImage
    public BufferedImage getRenderedFrame() {
        BufferedImage framebuffer = new BufferedImage(
                gameWindow.getWidth(),
                gameWindow.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics g = framebuffer.getGraphics();

        // Draw Background
        g.drawImage(background, 0, 0, null);

        // Draw Screen Renderables
        renderScreenElements(g);

        // Draw Player
        Player player = game.getPlayer();
        drawMapSprite(
                g, textureManager.getSprite(player.getSpriteID()),
                player.getPosX(), player.getPosY(),
                gameWindow.getTick()
        );

        // Draw HUD Elements
        renderHudElements(g);

        return framebuffer;
    }
}
