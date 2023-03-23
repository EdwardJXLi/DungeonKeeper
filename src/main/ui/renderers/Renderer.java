package ui.renderers;

import model.Game;
import ui.GraphicalGame;
import ui.sprites.Sprite;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;

public abstract class Renderer extends JPanel {
    protected GraphicalGame graphicalGame;
    protected Game game;
    protected SpriteManager spriteManager;

    public Renderer(GraphicalGame graphicalGame) {
        super();
        this.graphicalGame = graphicalGame;
        this.game = graphicalGame.getGame();
        this.spriteManager = graphicalGame.getSpriteManager();
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
