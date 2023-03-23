package ui.renderers;

import model.Game;
import ui.GameWindow;
import ui.sprites.Sprite;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Renderer extends JPanel {
    protected GameWindow gameWindow;
    protected Game game;
    protected SpriteManager spriteManager;

    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public Renderer(GameWindow gameWindow) {
        super();

        // Make layers transparent
        setOpaque(false);

        // Set up game variables
        this.gameWindow = gameWindow;
        this.game = gameWindow.getGame();
        this.spriteManager = gameWindow.getSpriteManager();

        // Initialize Key Handlers
        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();

        setPreferredSize(new Dimension(gameWindow.getSizeX(), gameWindow.getSizeY()));
    }

    // EFFECTS: Sets up mouse and key listeners
    // MODIFIES: this
    public void initUserInputHandlers() {
        gameWindow.addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    // EFFECTS: Removes mouse and key listeners
    // MODIFIES: this
    public void removeUserInputHandlers() {
        gameWindow.removeKeyListener(keyHandler);
        removeMouseListener(mouseHandler);
        removeMouseMotionListener(mouseHandler);
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            onKeyPress(e);
        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            onMouseMove(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            onMouseDrag(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            onMouseEnter(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            onMouseLeave(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            onMouseClick(e);
        }
    }

    // EFFECTS: Handler for key presses
    public void onKeyPress(KeyEvent e) {
        // Do Nothing
    }

    // EFFECTS: Handler for mouse movement
    public void onMouseMove(MouseEvent e) {
        // Do Nothing
    }

    // EFFECTS: Handler for mouse clicks
    public void onMouseClick(MouseEvent e) {
        // Do Nothing
    }

    // EFFECTS: Handler for mouse dragging
    public void onMouseDrag(MouseEvent e) {
        // Do Nothing
    }

    // EFFECTS: Handler for mouse entering
    public void onMouseEnter(MouseEvent e) {
        // Do Nothing
    }

    // EFFECTS: Handler for mouse exiting
    public void onMouseLeave(MouseEvent e) {
        // Do Nothing
    }

    // TODO: Hacky and Temporary Draw Function!
    public void drawSprite(Graphics g, Sprite sprite, int offsetX, int offsetY, int tick) {
        g.drawImage(
                sprite.getImage(tick),
                offsetX * spriteManager.getSpriteSize(),
                offsetY * spriteManager.getSpriteSize(),
                null
        );
    }

    public void drawSprite(Graphics g, Sprite sprite, int offsetX, int offsetY) {
        drawSprite(g, sprite, offsetX, offsetY, 0);
    }
}
