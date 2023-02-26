package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;
import model.ScreenElement;
import model.Tile;

public class GameFrame extends Frame {
    Game game;

    public GameFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Game Level", screen);
        this.game = game;
    }

    public void drawGame() {
        // Draw all tiles, entities, and items on the map
        renderScreenElements();

        // Draw Player
        Player player = game.getPlayer();
        drawSprite(player.getPosX(), player.getPosY(), player.getTextSprite(), player.getTextColor(), player.getBackgroundColor());
    }

    private void renderScreenElements() {
        // Render Tiles
        for (ScreenElement e: game.getLevel().getTiles()) {
            drawSprite(e.getPosX(), e.getPosY(), e.getTextSprite(), e.getTextColor(), e.getBackgroundColor());
        }
    }
}
