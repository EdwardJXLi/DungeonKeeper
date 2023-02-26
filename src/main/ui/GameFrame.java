package ui;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;
import model.ScreenElement;

public class GameFrame extends Frame {
    // EFFECTS: Initializes a player info frame that draws primary game map
    public GameFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Game Level", screen, game);
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
        for (ScreenElement e : game.getLevel().getTiles()) {
            drawSprite(e.getPosX(), e.getPosY(), e.getTextSprite(), e.getTextColor(), e.getBackgroundColor());
        }

        // Render Enemies
        for (ScreenElement e : game.getLevel().getEnemies()) {
            drawSprite(e.getPosX(), e.getPosY(), e.getTextSprite(), e.getTextColor(), e.getBackgroundColor());
        }

        // Render Dropped Items
        for (ScreenElement e : game.getLevel().getDroppedItems()) {
            drawSprite(e.getPosX(), e.getPosY(), e.getTextSprite(), e.getTextColor(), e.getBackgroundColor());
        }
    }
}
