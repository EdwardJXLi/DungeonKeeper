package ui.renderers;

import model.DroppedItem;
import model.Player;
import model.ScreenElement;
import model.graphics.SpriteID;
import ui.GameWindow;
import ui.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameRenderer extends Renderer {
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseInFrame = false;
    private int lastKeyPress = 0;

    private TooltipHelper tooltipHelper;
    private BufferedImage background;

    public GameRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Initialize Helpers
        tooltipHelper = new TooltipHelper(this, textureManager);

        // Initialize Background
        initializeBackground();
    }

    // EFFECTS: Handles mouse movement. Updates mouse position.
    // MODIFIES: this
    @Override
    public void onMouseMove(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // EFFECTS: Handles mouse entering frame. Updates mouse position.
    // MODIFIES: this
    @Override
    public void onMouseEnter(MouseEvent e) {
        mouseInFrame = true;
    }

    // EFFECTS: Handles mouse leaving frame. Updates mouse position.
    // MODIFIES: this
    @Override
    public void onMouseLeave(MouseEvent e) {
        mouseInFrame = false;
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
                // TODO: Open Inventory
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
                // TODO: Pause Game
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

                drawSprite(g, floorSprite, x, y);
            }
        }
        g.dispose();
    }

    // EFFECTS: Draws all tiles and enemies to screen
    private void renderScreenElements(Graphics g) {
        // Render Tiles
        for (ScreenElement e : game.getLevel().getTiles()) {
            drawSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Enemies
        for (ScreenElement e : game.getLevel().getEnemies()) {
            drawSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Dropped Items
        for (ScreenElement e : game.getLevel().getDroppedItems()) {
            drawSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }
    }

    // EFFECTS: Renders Tooltips
    private void renderTooltips(Graphics g) {
        if (mouseInFrame) {
            BufferedImage tooltip = tooltipHelper.generateTileTooltip(game.getLevel().getTileAtLocation(0, 0));
            g.drawImage(tooltip, mouseX, mouseY, null);
        }
    }

    // EFFECTS: Renders HUD Elements
    private void renderHudElements(Graphics g) {
        // TODO
    }

    // EFFECTS: Draws debug info to screen
    private void renderDebugInfo(Graphics g) {
        // Setup Fonts
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 18));

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

    @Override
    public void paint(Graphics g) {
        // Draw Background
        g.drawImage(background, 0, 0, null);

        // Draw Screen Renderables
        renderScreenElements(g);

        // Draw Player
        Player player = game.getPlayer();
        drawSprite(
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
}
