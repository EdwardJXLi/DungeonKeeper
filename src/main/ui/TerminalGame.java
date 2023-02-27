package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import model.*;
import ui.frames.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TerminalGame {
    // Constants
    private static final int INFO_BOX_WIDTH = 24;
    private static final int CHAT_BOX_HEIGHT = 5;
    private static final int PLAYER_INFO_BOX_HEIGHT = 5;
    private static final int INV_PREVIEW_BOX_HEIGHT = 4;
    private static final int INV_INSTRUCTION_BOX_HEIGHT = 5;

    // Game and Screen Variables
    private final Game game;
    private Screen screen;
    private int tick;
    private final int gameSizeX;
    private final int gameSizeY;
    private final int windowSizeX;
    private final int windowSizeY;

    // Terminal UI Frames
    private GameFrame gameFrame;
    private PlayerInfoFrame playerInfoFrame;
    private InfoFrame infoFrame;
    private MessageFrame messageFrame;

    // Terminal Inventory Frames
    private InventoryFrame inventoryFrame;
    private InvInstructionsFrame inventoryInstructionsFrame;
    private InvPreviewFrame inventoryPreviewFrame;
    private EquipmentFrame equipmentFrame;

    // EFFECTS: Creates and Initializes Game of size X and Y
    public TerminalGame(int sizeX, int sizeY) {
        gameSizeX = sizeX;
        gameSizeY = sizeY;

        // Calculate max window size
        windowSizeX = gameSizeX + INFO_BOX_WIDTH + 4;
        windowSizeY = gameSizeY + CHAT_BOX_HEIGHT + 4;

        // Setup and initialize game object
        game = new Game(gameSizeX, gameSizeY);
        game.initGame();

        // Start the Terminal UI Rendering
        beginTerminalRendering();
    }

    // MODIFIES: this
    // EFFECTS: Begins Terminal Rendering for game
    private void beginTerminalRendering() {
        // Catch any terminal errors
        try {
            // Setup and create terminal
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setInitialTerminalSize(new TerminalSize(windowSizeX, windowSizeY));
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Set up main UI Frames
            initUIFrames();

            // Setup Inventory Frames
            initInventoryFrames();

            // Start The Game Loop
            startGameLoop();
        } catch (IOException e) {
            System.out.println("Game Unexpectedly Closed");
        } catch (InterruptedException e) {
            System.out.println("Game Interrupted");
        }

        // Print out goodbye message
        System.out.println("You Died!");
        System.out.println("Thank you for playing!");

        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Main UI Terminal Frames
    private void initUIFrames() {
        gameFrame = new GameFrame(
                0, 0,
                gameSizeX + 1, gameSizeY + 1,
                screen, game
        );
        playerInfoFrame = new PlayerInfoFrame(
                gameSizeX + 2, 0,
                windowSizeX - 1, PLAYER_INFO_BOX_HEIGHT + 1,
                screen, game
        );
        infoFrame = new InfoFrame(
                gameSizeX + 2, PLAYER_INFO_BOX_HEIGHT + 2,
                windowSizeX - 1, gameSizeY + 1,
                screen, game
        );
        messageFrame = new MessageFrame(
                0, gameSizeY + 2,
                windowSizeX - 1, windowSizeY - 1,
                screen, game
        );
    }

    // MODIFIES: this
    // EFFECTS: Initializes Inventory Terminal Frames
    private void initInventoryFrames() {
        inventoryFrame = new InventoryFrame(
                0, 0,
                gameSizeX + 1, gameSizeY - INV_INSTRUCTION_BOX_HEIGHT - 1,
                screen, game
        );
        inventoryInstructionsFrame = new InvInstructionsFrame(
                0, gameSizeY - INV_INSTRUCTION_BOX_HEIGHT,
                gameSizeX + 1, gameSizeY + 1,
                screen, game
        );
        inventoryPreviewFrame = new InvPreviewFrame(
                gameSizeX + 2, PLAYER_INFO_BOX_HEIGHT + 2,
                windowSizeX - 1, PLAYER_INFO_BOX_HEIGHT + INV_PREVIEW_BOX_HEIGHT + 3,
                screen, game
        );
        equipmentFrame = new EquipmentFrame(
                gameSizeX + 2, PLAYER_INFO_BOX_HEIGHT + INV_PREVIEW_BOX_HEIGHT + 4,
                windowSizeX - 1, gameSizeY + 1,
                screen, game
        );
    }

    // MODIFIES: this
    // EFFECTS: Renders Game and Handles Input for each tick
    private void startGameLoop() throws IOException, InterruptedException {
        tick = 0;
        while (game.isGameRunning()) {
            tick++;
            renderGame();
            handlePlayerInput();
            game.handleNextTick(tick);
            Thread.sleep(1000L / Game.TPS);
        }
    }

    // EFFECTS: Renders the game UI with game elements
    private void renderGame() throws IOException {
        // Initialize the screen
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();

        // Render all UI elements
        gameFrame.drawFrame();
        gameFrame.drawGame();
        playerInfoFrame.drawFrame();
        playerInfoFrame.drawPlayerInfo();
        infoFrame.drawFrame();
        infoFrame.renderInfo();
        messageFrame.drawFrame();
        messageFrame.renderMessages();

        // Refresh Screen
        screen.refresh();
    }

    // MODIFIES: this
    // EFFECTS: Renders and handles the inventory screen
    @SuppressWarnings("methodlength")
    private void handleInventory() throws IOException {
        // Get the inventory content
        List<Item> inventory = game.getPlayer().getInventory();

        // Get the range of the inventory
        int maxItemsPerPage = inventoryFrame.maxItemsInPage();
        int selected = 0;
        int from = 0;
        int to = Math.min(inventory.size(), maxItemsPerPage);

        // Wait for user input
        while (true) {
            // Render the current inventory
            renderInventory(inventory, from, to, selected);

            // Launch a thread blocking read
            KeyStroke stroke = screen.readInput();

            if (stroke != null) {
                if (stroke.getCharacter() != null) {
                    if (stroke.getCharacter().equals('e')) {
                        return;
                    } else if (stroke.getCharacter().equals('q')) {
                        if (inventory.size() > 0) {
                            game.getPlayer().dropItem(inventory.get(selected));
                            selected = 0;
                            from = 0;
                            to = Math.min(inventory.size(), maxItemsPerPage);
                        }
                    } else if (stroke.getCharacter().equals('x')) {
                        if (inventory.size() > 0) {
                            game.getPlayer().removeItem(inventory.get(selected));
                            selected = 0;
                            from = 0;
                            to = Math.min(inventory.size(), maxItemsPerPage);
                        }
                    }
                }
                if (stroke.getKeyType() != null) {
                    if (stroke.getKeyType() == KeyType.ArrowUp) {
                        if (selected > 0) {
                            selected--;
                            if (selected < from) {
                                from--;
                                to--;
                            }
                        }
                    } else if (stroke.getKeyType() == KeyType.ArrowDown) {
                        if (selected < inventory.size() - 1) {
                            selected++;
                            if (selected >= to) {
                                from++;
                                to++;
                            }
                        }
                    } else if (stroke.getKeyType() == KeyType.Enter) {
                        if (inventory.size() > 0) {
                            inventory.get(selected).useItem(game.getPlayer());
                            selected = 0;
                            from = 0;
                            to = Math.min(inventory.size(), maxItemsPerPage);
                        }
                    } else if (stroke.getKeyType() == KeyType.Escape) {
                        return;
                    }
                }
            }
        }
    }

    // EFFECTS: Renders the inventory UI with inventory elements
    private void renderInventory(
            List<Item> inventory, int from, int to, int selected
    ) throws IOException {
        // Initialize the screen
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();

        // Render all inventory elements
        inventoryFrame.drawFrame();
        inventoryFrame.renderInventory(inventory, from, to, selected);
        inventoryInstructionsFrame.drawFrame();
        inventoryInstructionsFrame.renderInstructions();
        playerInfoFrame.drawFrame();
        playerInfoFrame.drawPlayerInfo();
        inventoryPreviewFrame.drawFrame();
        inventoryPreviewFrame.renderPreview(inventory, selected);
        equipmentFrame.drawFrame();
        equipmentFrame.renderEquipment();
        messageFrame.drawFrame();
        messageFrame.renderMessages();

        // Refresh Screen
        screen.refresh();
    }

    // MODIFIES: this
    // EFFECTS: Takes user input and performs action
    private void handlePlayerInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        Player player = game.getPlayer();
        DroppedItem di = game.getLevel().getDroppedItemAtLocation(player.getPosX(), player.getPosY());

        // Check if keystroke is valid
        if (stroke != null) {
            if (stroke.getCharacter() != null) {
                Character character = stroke.getCharacter();
                if (character == 'w') {
                    player.moveUp();
                } else if (character == 's') {
                    player.moveDown();
                } else if (character == 'a') {
                    player.moveLeft();
                } else if (character == 'd') {
                    player.moveRight();
                } else if (character == 'e') {
                    handleInventory();
                } else if (character == 'q' && di != null) {
                    player.pickupItem(di);
                } else if (character == 'x' && di != null) {
                    game.getLevel().removeDroppedItem(di);
                }
            }
        }
    }
}
