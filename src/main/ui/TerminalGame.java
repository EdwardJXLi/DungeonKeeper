package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import model.Game;
import model.Tile;

import java.io.IOException;

public class TerminalGame {
    private static final int INFO_BOX_WIDTH = 24;
    private static final int INFO_BOX_HEIGHT = 1;
    private Game game;
    private Screen screen;
    private Terminal terminal;
    private int tick;
    private int gameSizeX;
    private int gameSizeY;
    private int windowSizeX;
    private int windowSizeY;

    private GameFrame gameFrame;
    private InfoFrame infoFrame;
    private HudFrame hudFrame;

    public TerminalGame(int sizeX, int sizeY) {
        gameSizeX = sizeX;
        gameSizeY = sizeY;
        windowSizeX = gameSizeX + INFO_BOX_WIDTH + 4;
        windowSizeY = gameSizeY + INFO_BOX_HEIGHT + 4;

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
            infoFrame = new InfoFrame(
                    gameSizeX + 2, 0,
                    windowSizeX - 1, gameSizeY + 1,
                    screen
            );
            hudFrame = new HudFrame(
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
        infoFrame.drawFrame();
        hudFrame.drawFrame();

        screen.refresh();
    }
}
