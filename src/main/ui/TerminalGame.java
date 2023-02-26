package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import model.Game;

import java.io.IOException;

public class TerminalGame {
    private static final int INFO_BOX_WIDTH = 24;
    private static final int CHAT_BOX_HEIGHT = 5;
    private static final int PLAYER_INFO_BOX_HEIGHT = 3;
    private Game game;
    private Screen screen;
    private Terminal terminal;
    private int tick;
    private int gameSizeX;
    private int gameSizeY;
    private int windowSizeX;
    private int windowSizeY;

    private GameFrame gameFrame;
    private PlayerInfoFrame playerInfoFrame;
    private InfoFrame infoFrame;
    private MessageFrame messageFrame;

    public TerminalGame(int sizeX, int sizeY) {
        gameSizeX = sizeX;
        gameSizeY = sizeY;
        windowSizeX = gameSizeX + INFO_BOX_WIDTH + 4;
        windowSizeY = gameSizeY + CHAT_BOX_HEIGHT + 4;

        // Setup and initialize game object
        game = new Game(gameSizeX, gameSizeY);

        // Start the Terminal UI Rendering
        try {
            // Setup and create terminal
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setInitialTerminalSize(new TerminalSize(windowSizeX, windowSizeY));
            terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Set up UI Frames
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
                    screen
            );
            messageFrame = new MessageFrame(
                    0, gameSizeY + 2,
                    windowSizeX - 1, windowSizeY - 1,
                    screen
            );

            // Start The Game Loop
            startGameLoop();
        } catch (IOException e) {
            System.out.println("Game Unexpectedly Closed");
            // End the game and print out credits
            game.quit();
        } catch (InterruptedException e) {
            // End the game and print out credits
            game.quit();
        }

        System.exit(0);
    }

    private void startGameLoop() throws IOException, InterruptedException {
        tick = 0;
        while (true) {
            tick++;
            render();
            handlePlayerInput();
            Thread.sleep(1000L / Game.TPS);
        }
    }

    private void render() throws IOException {
        // Initialize the screen
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();

        // Render all elements
        gameFrame.drawFrame();
        gameFrame.drawGame();
        playerInfoFrame.drawFrame();
        playerInfoFrame.drawPlayerInfo();
        infoFrame.drawFrame();
        messageFrame.drawFrame();

        screen.refresh();
    }

    private void handlePlayerInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        // Check if keystroke is valid
        if (stroke != null) {
            if (stroke.getCharacter() != null) {
                switch (stroke.getCharacter()) {
                    case 'w':
                        game.getPlayer().moveUp();
                        break;
                    case 's':
                        game.getPlayer().moveDown();
                        break;
                    case 'a':
                        game.getPlayer().moveLeft();
                        break;
                    case 'd':
                        game.getPlayer().moveRight();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
