package ui.renderers;

import model.graphics.SpriteID;
import ui.GameWindow;
import ui.GraphicalGame;
import ui.helpers.CustomButton;
import ui.helpers.TextureManager;
import ui.sprites.Sprite;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Renderer for the main menu.
 * Allows the player to start a new game, load a game, or quit the game.
 */

public class MainMenuRenderer extends MenuRenderer {
    // Main Menu Helpers
    private static final int BACKGROUND_SIZE = 200;
    private static final int BACKGROUND_SPEED = 1;
    private static final int PLAYER_MODEL_SCALE = 12;
    private static final int TITLE_SIZE = 96;
    private static final double TITLE_BOBBLE_SPEED = 0.385 / 2;
    private static final double TITLE_BOBBLE_HEIGHT = 8;

    // Internal Variables for Main Menu Rendering
    int internalTick = 0;
    int internalBackgroundOffset = 0;
    BufferedImage background;

    public MainMenuRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Setup Buttons
        setupButtons();

        // Initialize Background
        initializeBackground();
    }

    // MODIFIES: this
    // EFFECTS: Generates and Initializes the Background
    @Override
    public void initRenderer() {
        super.initRenderer();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the buttons for the main menu
    private void setupButtons() {
        // Calculate button positions
        int posY = (gameWindow.getSizeY() / 3) * 2;
        int posX = (gameWindow.getSizeX() / 8);
        int bs = (gameWindow.getSizeX() / 8) * 3;

        // Check if save file exists
        boolean saveFileExists = new File(GraphicalGame.SAVE_LOCATION).exists();

        // Add New Game Button
        addButton(new CustomButton(this, posX, posY, "NEW GAME", 32, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.createGame();
                gameWindow.initGameRenderers();
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
            }
        }));

        // If a save file exists, render option to use file
        if (saveFileExists) {
            // Add Load Game Button
            addButton(new CustomButton(this, posX + bs, posY, "CONTINUE GAME", 32, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameWindow.loadGame();
                    gameWindow.initGameRenderers();
                    gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                }
            }));
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
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                // Quick hack for a varied floor
                floorSprite = floorSprites.get(rng.nextInt(floorSprites.size()));

                drawMapSprite(g, floorSprite, x, y);
            }
        }

        g.dispose();
    }

    // MODIFIES: this
    // EFFECTS: Handles Keyboard Input.
    @Override
    public void onKeyPress(KeyEvent e) {
        // DO not call super.onKeyPress(e)! We want to override all functionality of keypress

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                gameWindow.quitGame();
                break;
            case KeyEvent.VK_ENTER:
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                break;
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the background
    @Override
    public void renderBackground(Graphics g) {
        // Draw background
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                g.drawImage(
                        background,
                        x * BACKGROUND_SIZE - internalBackgroundOffset,
                        y * BACKGROUND_SIZE - internalBackgroundOffset,
                        null
                );
            }
        }

        // Update background offset
        internalBackgroundOffset += BACKGROUND_SPEED;
        if (internalBackgroundOffset >= BACKGROUND_SIZE) {
            internalBackgroundOffset = 0;
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders Large Version of Player Model
    private void renderPlayerModel(Graphics g) {
        // Get the base player image
        BufferedImage rawPlayerSprite = textureManager.getSprite(SpriteID.PLAYER).getRawImage(internalTick);

        // Calculate new sprite size, and location on screen it should be drawn
        int spriteSize = textureManager.getSpriteSize() * PLAYER_MODEL_SCALE;
        int spriteXOffset = (gameWindow.getWidth() / 4) - (spriteSize / 2);
        int spriteYOffset = (gameWindow.getHeight() / 3) - (spriteSize / 2);

        // Scale up player sprite and draw it
        g.drawImage(
                TextureManager.resize(
                        rawPlayerSprite,
                        spriteSize,
                        spriteSize,
                        Image.SCALE_FAST
                ),
                spriteXOffset, spriteYOffset, null
        );
    }

    // MODIFIES: g
    // EFFECTS: Renders the title
    public void renderTitle(Graphics g) {
        // Calculate title location
        int boobleOffset = (int) (Math.sin(internalTick * TITLE_BOBBLE_SPEED) * TITLE_BOBBLE_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(TITLE_SIZE));
        g.drawString(
                "Dungeon",
                gameWindow.getSizeX() / 3, boobleOffset + gameWindow.getSizeY() / 4
        );
        g.drawString(
                "Keeper",
                gameWindow.getSizeX() / 3, boobleOffset + gameWindow.getSizeY() / 4 + TITLE_SIZE
        );
        g.setFont(textureManager.getFont(TITLE_SIZE / 4));
        g.drawString(
                "A CPSC 210 Project",
                gameWindow.getSizeX() / 3, boobleOffset + gameWindow.getSizeY() / 4 + TITLE_SIZE + TITLE_SIZE / 2
        );
    }

    private void renderExtraInfo(Graphics g) {
        int fontSize = TITLE_SIZE / 6;
        List<String> extraInfo = new ArrayList<>();
        extraInfo.add("--[ Dungeon Keeper - A CPSC 210 Project - 2022W ]--");
        extraInfo.add("By: Edward Li [github.com/edwardjxli]");
        extraInfo.add("] Textures (borrowed) from: ");
        extraInfo.add("]     2D Pixel Dungeon Asset Pack, Minecraft, and Terraria");

        // Draw Text
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(fontSize));

        // Loop through text backwards, so that the last line is drawn first
        for (int i = 0; i < extraInfo.size(); i++) {
            g.drawString(
                    extraInfo.get(extraInfo.size() - i - 1),
                    fontSize, gameWindow.getSizeY() - (fontSize + 4) * (i + 1)
            );
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders main menu
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        renderBackground(g);

        // Draw Player Model
        renderPlayerModel(g);

        // Draw Title
        renderTitle(g);

        // Draw Extra Info
        renderExtraInfo(g);

        // Draw buttons
        renderButtons(g);

        internalTick++;
    }
}
