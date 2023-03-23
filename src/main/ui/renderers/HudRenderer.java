package ui.renderers;

import model.graphics.SpriteID;
import ui.GameWindow;
import ui.sprites.Sprite;

import java.awt.*;
import java.util.List;

public class HudRenderer extends Renderer {
    public HudRenderer(GameWindow gameWindow) {
        super(gameWindow);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Test", Font.PLAIN, 24));
        g.drawString(
                "TEST TEST TEST" + gameWindow.getTick(),
                5, 25
        );
    }
}
