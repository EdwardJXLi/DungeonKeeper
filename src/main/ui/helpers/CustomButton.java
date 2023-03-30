package ui.helpers;

/*
 * Class that represents a custom button to be rendered in the game.
 */

import ui.renderers.MenuRenderer;

import java.awt.*;
import java.awt.event.ActionListener;

public class CustomButton {
    // Button Constants
    private static final Color BUTTON_COLOR = Color.WHITE;
    private static final int BUTTON_PADDING = 6;
    // Button Variables
    private final MenuRenderer menuRenderer;
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;
    private final String text;
    private final int padding;
    private final Font buttonFont;

    // Button onPress Action
    private final ActionListener actionListener;

    // EFFECTS: Creates a custom button with the given parameters
    public CustomButton(
            MenuRenderer menuRenderer,
            int posX,
            int posY,
            String text,
            int fontSize,
            ActionListener actionListener
    ) {
        this.menuRenderer = menuRenderer;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.padding = menuRenderer.getTextureManager().getScaledSize(BUTTON_PADDING);
        this.buttonFont = menuRenderer.getTextureManager().getFont(fontSize);
        this.actionListener = actionListener;
        this.width = buttonFont.getSize() / 2 * text.length() + padding * 2;
        this.height = buttonFont.getSize() / 2 + padding * 2;
    }

    // MODIFIES: g
    // EFFECTS: Renders the button
    public void render(Graphics g, boolean isHovered) {
        g.setColor(BUTTON_COLOR);
        g.setFont(buttonFont);
        if (isHovered) {
            g.drawRect(posX, posY, width, height);
        }
        g.drawString(text, posX + padding, posY + buttonFont.getSize() / 2 + padding);
    }

    // EFFECTS: Returns true if the given coordinates are within the button
    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }

    // MODIFIES: this
    // EFFECTS: Performs the button's action
    public void onClick() {
        actionListener.actionPerformed(null);
    }
}
