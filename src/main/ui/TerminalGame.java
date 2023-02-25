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
    private Game game;
    private Screen screen;
    private Terminal terminal;
    private int tick;

    public TerminalGame() {
        // Setup and initialize game object
        game = new Game(32, 32);

        // Start the Terminal UI Rendering
        try {
            // Setup and create terminal
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setInitialTerminalSize(new TerminalSize(40, 40));
            terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

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
            // Draw all tiles, entities, and items on the map
            renderGame();
            Thread.sleep(1000L / Game.TPS);
        }
    }

    private void renderGame() throws IOException {
        // Initialize the screen
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();

        // Render all elements
        renderTiles();

        drawSprite(1, 1, Integer.toString(tick).charAt(0), TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

        screen.refresh();
    }

    private void renderTiles() {
        for (Tile t: game.getLevel().getTiles()) {
            drawSprite(t.getPosX(), t.getPosY(), t.getTextSprite(), t.getTextColor(), TextColor.ANSI.DEFAULT);
        }
    }

    private void drawSprite(int posX, int posY, char c, TextColor foregroundColor, TextColor backgroundColor) {
        // Initialize the sprite
        TextGraphics sprite = screen.newTextGraphics();
        sprite.setForegroundColor(foregroundColor);
        sprite.setBackgroundColor(backgroundColor);
        sprite.putString(posX, posY, String.valueOf(c));
    }
}
