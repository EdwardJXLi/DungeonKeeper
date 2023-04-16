package ui.renderers;

import ui.GameWindow;
import ui.helpers.CustomButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Abstract class for all menu renderers.
 * Menu renderers are used to render the game menus.
 */

public abstract class MenuRenderer extends Renderer {
    private BufferedImage background;
    private final List<CustomButton> buttons;

    // EFFECTS: Creates a menu renderer for a game window.
    public MenuRenderer(GameWindow gameWindow) {
        super(gameWindow);

        // Setup Menu Items
        background = null;
        buttons = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a button to the menu
    public void addButton(CustomButton button) {
        buttons.add(button);
    }

    // MODIFIES: g
    // EFFECTS: Renders Menu Buttons
    public void renderButtons(Graphics g) {
        for (CustomButton button : buttons) {
            // Render Button
            button.render(g, button.isHovered(mouseX, mouseY));
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the background
    public void renderBackground(Graphics g) {
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }

    // EFFECTS: Generates menu background by blurring a frame of the game
    public BufferedImage generateBlurBackground(GameRenderer gameRenderer) {
        BufferedImage background = new BufferedImage(
                gameWindow.getSizeX(),
                gameWindow.getSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();

        // Get a frame buffer of the currently rendered game
        BufferedImage gameFrame = gameRenderer.getRenderedFrame();

        // Draw background
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fillRect(0, 0, gameWindow.getSizeX(), gameWindow.getSizeY());

        // Draw blurred background
        g.drawImage(blur(gameFrame), 0, 0, null);

        return background;
    }

    // EFFECTS: Helper function to blur buffered image
    public BufferedImage blur(BufferedImage image) {
        // Blur image using gaussian blur, with a hacky cyclic convolution "algorithm".
        // NOTE: I did not write the bulk of the gaussian blur code.
        // Source: https://stackoverflow.com/questions/29295929/java-blur-image
        // But I did write the hack that overdraws the frame with gray to help with blurring

        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        // Create a new image to draw on. This is larger on the edges to allow for "cyclic convolution".
        BufferedImage blurredImage = new BufferedImage(
                image.getWidth() + radius * 2,
                image.getHeight() + radius * 2,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = blurredImage.createGraphics();

        // Set the background to dark gray.
        g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.2f));
        g.fillRect(0, 0, blurredImage.getWidth(), blurredImage.getHeight());

        // Copy over image to blurredImage.
        g.drawImage(image, radius, radius, null);

        // Apply blur (this is the part I stole from stackoverflow)
        Arrays.fill(data, weight);
        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        blurredImage = op.filter(blurredImage, null);

        // Crop the image to the original size.
        return blurredImage.getSubimage(radius, radius, image.getWidth(), image.getHeight());
    }

    // MODIFIES: this
    // EFFECTS: Handles mouse clicks, and performs actions based on the buttons
    @Override
    public void onMouseClick(MouseEvent e) {
        super.onMouseClick(e);

        // Check if the location clicked is that of a button
        for (CustomButton button : buttons) {
            if (button.isHovered(e.getX(), e.getY())) {
                button.onClick();
                break; // Only one button can be clicked at a time
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the background
    public void setBackground(BufferedImage background) {
        this.background = background;
    }
}
