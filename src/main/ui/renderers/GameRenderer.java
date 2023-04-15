package ui.renderers;

import model.*;
import model.decorations.*;
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
    private static final int ATTACK_PER_ATTACK_ICON = 10;
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
            // If dynamic walls are supported, add border walls to the level.
            initializeBorderWall(level);
        }

        // Next, check if decorations are supported
        if (textureManager.getFlags().contains("DECORATIONS")) {
            // If decorations are supported, add decorations to the level.
            initializeTorches(level);
            initializeWebs(level);
            initializeBones(level);
            initializeBanners(level);
            initializeChains(level);
            initializeRocks(level);
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Border Walls
    //          This makes it so that border walls are drawn as a single wall, instead of a wall and a floor.
    private void initializeBorderWall(Level level) {
        // Loop through edges of the map the top and bottom walls
        for (int x = 1; x < level.getSizeX() - 1; x++) {
            // Add decoration to top wall
            if (level.getTileAtLocation(x, 0) instanceof Wall) {
                level.addDecoration(new FancyWall(x, 0, FancyWall.FancyWallType.TOP));
            }

            // Add decoration to bottom wall
            if (level.getTileAtLocation(x, level.getSizeY() - 1) instanceof Wall) {
                level.addDecoration(new FancyWall(x, level.getSizeY() - 1, FancyWall.FancyWallType.BOTTOM));
            }
        }

        // Set the left and right walls.
        // Starting from 1 to sizeY-1 to avoid duplication
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            // Add decoration to left wall
            if (level.getTileAtLocation(0, y) instanceof Wall) {
                level.addDecoration(new FancyWall(0, y, FancyWall.FancyWallType.LEFT));
            }

            // Add decoration to right wall
            if (level.getTileAtLocation(level.getSizeX() - 1, y) instanceof Wall) {
                level.addDecoration(new FancyWall(level.getSizeX() - 1, y, FancyWall.FancyWallType.RIGHT));
            }
        }

        // Add borders
        level.addDecoration(new FancyWall(0, 0, FancyWall.FancyWallType.TOP_LEFT));
        level.addDecoration(new FancyWall(level.getSizeX() - 1, 0, FancyWall.FancyWallType.TOP_RIGHT));
        level.addDecoration(new FancyWall(0, level.getSizeY() - 1, FancyWall.FancyWallType.BOTTOM_LEFT));
        level.addDecoration(
                new FancyWall(level.getSizeX() - 1, level.getSizeY() - 1, FancyWall.FancyWallType.BOTTOM_RIGHT)
        );
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Torches
    private void initializeTorches(Level level) {
        Random random = getGame().getRandom();

        // Add Torches Randomly. The percent change depends on the type of wall torch is on.
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            for (int x = 1; x < level.getSizeX() - 1; x++) {
                // Left facing torches
                if (level.getTileAtLocation(x, y) == null
                        && level.getTileAtLocation(x - 1, y) instanceof Wall) {
                    if (random.nextInt(100) < 10) {
                        level.addDecoration(new Torch(x, y, Torch.TorchType.LEFT));
                    }
                // Right facing torches
                } else if (level.getTileAtLocation(x, y) == null
                        && level.getTileAtLocation(x + 1, y) instanceof Wall) {
                    if (random.nextInt(100) < 10) {
                        level.addDecoration(new Torch(x, y, Torch.TorchType.RIGHT));
                    }
                // Center facing torches
                } else if (level.getTileAtLocation(x, y) instanceof Wall) {
                    if (random.nextInt(100) < 5) {
                        level.addDecoration(new Torch(x, y, Torch.TorchType.CENTER));
                    }
                }
            }
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Webs
    private void initializeWebs(Level level) {
        Random random = getGame().getRandom();

        // Add Webs Randomly. Type 1 webs spawn next to walls
        // Skipping type 2 webs for now...
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            for (int x = 1; x < level.getSizeX() - 1; x++) {
                // Type 1 webs
                if (level.getTileAtLocation(x, y) == null
                        && level.getTileAtLocation(x - 1, y) instanceof Wall) {
                    if (random.nextInt(100) < 10) {
                        level.addDecoration(new Web(x, y, Web.WebType.TYPE1_LEFT));
                    }
                } else if (level.getTileAtLocation(x, y) == null
                        && level.getTileAtLocation(x + 1, y) instanceof Wall) {
                    if (random.nextInt(100) < 10) {
                        level.addDecoration(new Web(x, y, Web.WebType.TYPE1_RIGHT));
                    }
                }
            }
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Bones
    private void initializeBones(Level level) {
        // For each empty floor tile, have a chance of spawning bones
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            for (int x = 1; x < level.getSizeX() - 1; x++) {
                if (level.getTileAtLocation(x, y) == null) {
                    // Each empty tile has a chance of spawning bones
                    if (getGame().getRandom().nextInt(100) < 1) {
                        // Randomly choose a bone type. Normal bones are more likely
                        if (getGame().getRandom().nextInt(100) < 80) {
                            level.addDecoration(new Bone(x, y, Bone.BoneType.NORMAL));
                        } else {
                            level.addDecoration(new Bone(x, y, Bone.BoneType.SKULL));
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Banners
    private void initializeBanners(Level level) {
        // Banners spawn in an interval only on the top walls
        for (int x = 1; x < level.getSizeX() - 1; x++) {
            if ((x + 1) % 5 == 0) {
                level.addDecoration(new Banner(x, 0));
            }
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Chains
    private void initializeChains(Level level) {
        // Each wall has a small percent chance of spawning a chain
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            for (int x = 1; x < level.getSizeX() - 1; x++) {
                if (level.getTileAtLocation(x, y) instanceof Wall) {
                    if (getGame().getRandom().nextInt(100) < 10) {
                        // 50 / 50 chance of spawning both types of chains
                        if (getGame().getRandom().nextInt(100) < 50) {
                            level.addDecoration(new Chain(x, y, Chain.ChainType.TYPE1));
                        } else {
                            level.addDecoration(new Chain(x, y, Chain.ChainType.TYPE2));
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this, level
    // EFFECTS: Initializes Rocks
    private void initializeRocks(Level level) {
        Random random = getGame().getRandom();

        // For each empty floor tile, have a chance of spawning bones
        for (int y = 1; y < level.getSizeY() - 1; y++) {
            for (int x = 1; x < level.getSizeX() - 1; x++) {
                if (level.getTileAtLocation(x, y) == null) {
                    // Each empty tile has a chance of spawning bones
                    if (random.nextInt(100) < 1) {
                        // Spawn a small rock
                        level.addDecoration(new Rock(x, y, Rock.RockType.SMALL));
                        // Skipping large rocks as they look too much like walls.
                    }
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
        for (Tile e : game.getLevel().getTiles()) {
            // Draw Walls Specially
            if (e instanceof Wall) {
                drawMapSprite(
                        g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY()
                );
            } else {
                drawMapSprite(
                        g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick()
                );
            }
        }

        // Render Decorations
        for (Decoration e : game.getLevel().getDecorations()) {
            drawMapSprite(
                    g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), e.getSpriteOffset()
            );
        }

        // Render Enemies
        for (Enemy e : game.getLevel().getEnemies()) {
            drawMapSprite(g, textureManager.getSprite(e.getSpriteID()), e.getPosX(), e.getPosY(), gameWindow.getTick());
        }

        // Render Dropped Items
        for (DroppedItem e : game.getLevel().getDroppedItems()) {
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
