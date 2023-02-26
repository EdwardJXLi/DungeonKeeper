package ui;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;
import model.ScreenElement;

public class GameFrame extends Frame {
    Game game;

    // EFFECTS: Initializes a player info frame that draws primary game map
    public GameFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Game Level", screen);
        this.game = game;
    }

    // EFFECTS: Draws entire game board
    public void drawGame() {
        // Draw all tiles, entities, and items on the map
        renderScreenElements();

        // Draw Player
        Player player = game.getPlayer();
        drawSprite(
                player.getPosX(), player.getPosY(),
                player.getTextSprite(), player.getTextColor(), player.getBackgroundColor()
        );
    }

    // EFFECTS: Draws all tiles and enemies to screen
    private void renderScreenElements() {
        // Render Tiles
        for (ScreenElement e : game.getLevel().getTiles()) {
            drawSprite(e.getPosX(), e.getPosY(), e.getTextSprite(), e.getTextColor(), e.getBackgroundColor());
        }
    }
}
