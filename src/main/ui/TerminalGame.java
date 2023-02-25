package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import model.Tile;

import java.io.IOException;

public class TerminalGame {
    private Game game;
    private Screen screen;

    public TerminalGame() {
        // Setup and initialize game object
        game = new Game(64, 64);

        // Start the Terminal UI Rendering
        try {
            // Setup and create screen
            screen = new DefaultTerminalFactory().createScreen();
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
        while (true) {
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
