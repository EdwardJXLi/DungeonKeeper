package ui.renderers;

import model.DroppedItem;
import model.Player;
import model.ScreenElement;
import model.graphics.SpriteID;
import ui.GameWindow;
import ui.helpers.TooltipHelper;
import ui.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameRenderer extends Renderer {
    private static final int HEARTS_PER_HEART_ICON = 20;
    private static final int ATTACK_PER_ATTACK_ICON = 5;
    private static final int DEFENSE_PER_DEFENSE_ICON = 5;
    
    private int lastKeyPress = 0;

    private final TooltipHelper tooltipHelper;
    private BufferedImage background;

    public GameRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Initialize Helpers
        tooltipHelper = new TooltipHelper(this, textureManager);

        // Initialize Background
        initializeBackground();
    }

    // EFFECTS: Handles key presses. Updates game state.
    // MODIFIES: this
    @Override
    public void onKeyPress(KeyEvent e) {
        lastKeyPress = e.getKeyCode();

        // Get Game Info
        Player player = game.getPlayer();
        DroppedItem di = game.getLevel().getDroppedItemAtLocation(player.getPosX(), player.getPosY());

        // Handles key presses and moves character
        switch (lastKeyPress) {
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
            case KeyEvent.VK_BACK_SLASH:
                gameWindow.switchRenderer(gameWindow.getTestRenderer(), false);
                break;
        }
    }

    // EFFECTS: Renders the initial background
    // MODIFIES: this
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

    @Override
    public void paint(Graphics g) {
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
//        drawSprite(
//                g, textureManager.getSprite(SpriteID.SELECT_BLUE),
//                player.getPosX(), player.getPosY(),
//                gameWindow.getTick() / 2  // half speed
//        );

        // Draw Tooltips
        renderTooltips(g);

        // Draw HUD Elements
        renderHudElements(g);

        // Draw Debug Info
//        renderDebugInfo(g);
    }

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

    // EFFECTS: Renders Tooltips
    private void renderTooltips(Graphics g) {
        // Only render if mouse is in frame
        if (mouseInFrame) {
            // Get currently highlighted tile, and initiate tooltip
            int tileX = mouseX / textureManager.getSpriteSize();
            int tileY = mouseY / textureManager.getSpriteSize();
            BufferedImage tooltip;

            // First, check if a dropped item is at the hovered location.
            // Else, if it is the player at the hovered location.
            // Else, if it is an entity at the hovered location
            // Else, if it is a tile with a valid description at the hovered location.
            if (game.getLevel().getDroppedItemAtLocation(tileX, tileY) != null) {
                tooltip = tooltipHelper.generateDroppedItemTooltip(
                        game.getLevel().getDroppedItemAtLocation(tileX, tileY)
                );
            } else if (tileX == game.getPlayer().getPosX() && tileY == game.getPlayer().getPosY()) {
                tooltip = tooltipHelper.generatePlayerTooltip(game.getPlayer());
            } else if (game.getLevel().getEnemyAtLocation(tileX, tileY) != null) {
                tooltip = tooltipHelper.generateEnemyTooltip(
                        game.getLevel().getEnemyAtLocation(tileX, tileY)
                );
            } else if (game.getLevel().getTileAtLocation(tileX, tileY) != null) {
                if (game.getLevel().getTileAtLocation(tileX, tileY).getDescription() != null) {
                    tooltip = tooltipHelper.generateTileTooltip(
                            game.getLevel().getTileAtLocation(tileX, tileY)
                    );
                } else {
                    // Do not render!
                    return;
                }
            } else {
                // Do not render tooltip and return!
                return;
            }

            // Draw the hover animation
            drawMapSprite(g, textureManager.getSprite(SpriteID.SELECT_GREEN), tileX, tileY, gameWindow.getTick());

            // Draw the tooltip
            final int tooltipOffset = 15;
            g.drawImage(tooltip, mouseX + tooltipOffset, mouseY + tooltipOffset, null);
        }
    }

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
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)), 10 + textureManager.getSpriteSize() + 4
            );
        }
    }

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
                    (textureManager.getSpriteSize() * 6) + (i * (textureManager.getSpriteSize() + 4)), 10 + (textureManager.getSpriteSize() + 4) * 2
            );
        }
    }

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
                    10, gameWindow.getHeight() - 50 - (i * textureManager.getFont(12).getSize())
            );
        }
    }

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

    // EFFECTS: Draws debug info to screen
    private void renderDebugInfo(Graphics g) {
        // Setup Fonts
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(12));

        // Add Debug Text
        List<String> debugInfo = new ArrayList<>();
        debugInfo.add("== ENGINE DEBUG ==");
        debugInfo.add("Game Tick: " + gameWindow.getTick());
        debugInfo.add("Player Position: " + game.getPlayer().getPosX() + ", " + game.getPlayer().getPosY());
        debugInfo.add("Mouse Position: " + mouseX + ", " + mouseY);
        debugInfo.add("Mouse In Frame: " + mouseInFrame);
        debugInfo.add("Last Key Press: " + lastKeyPress);
        debugInfo.add("");
        debugInfo.add("== RENDERING DEBUG ==");
        debugInfo.add("FPS: " + gameWindow.getFPS());
        debugInfo.add("Rendered Tiles: " + game.getLevel().getTiles().size());
        debugInfo.add("Rendered Enemies: " + game.getLevel().getEnemies().size());
        debugInfo.add("Rendered Dropped Items: " + game.getLevel().getDroppedItems().size());
        debugInfo.add("");
        debugInfo.add("== GAME DEBUG ==");
        debugInfo.add("Player Health: " + game.getPlayer().getHealth());
        debugInfo.add("Player Max Health: " + game.getPlayer().getMaxHealth());
        debugInfo.add("Player Attack: " + game.getPlayer().getAttack());
        debugInfo.add("Player Defense: " + game.getPlayer().getDefense());
        debugInfo.add("Player Kills: " + game.getPlayer().getKills());
        debugInfo.add("Player Inventory Size: " + game.getPlayer().getInventory().numItems());

        // Draw Debug Text
        for (int i = 0; i < debugInfo.size(); i++) {
            g.drawString(debugInfo.get(i), 0, 18 * (i + 1));
        }
    }

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
