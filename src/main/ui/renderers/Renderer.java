package ui.renderers;

import model.Game;
import ui.GraphicalGame;
import ui.sprites.Sprite;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;

public abstract class Renderer extends JPanel {
    protected GraphicalGame gameWindow;
    protected Game game;
    protected SpriteManager spriteManager;

    public Renderer(GraphicalGame gameWindow) {
        super();
        this.gameWindow = gameWindow;
        this.game = gameWindow.getGame();
        this.spriteManager = gameWindow.getSpriteManager();

        setPreferredSize(new Dimension(gameWindow.getWindowSizeX(), gameWindow.getWindowSizeY()));
    }

    // TODO: Hacky and Temporary Draw Function!
    public void drawSprite(Graphics g, Sprite sprite, int offsetX, int offsetY, int tick) {
        g.drawImage(
                sprite.getImage(tick),
                offsetX * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                offsetY * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                null
        );
    }

    public void drawSprite(Graphics g, Sprite sprite, int offsetX, int offsetY) {
        drawSprite(g, sprite, offsetX, offsetY, 0);
    }
}
