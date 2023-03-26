package ui.renderers;

import ui.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public abstract class MenuRenderer extends Renderer {
    public MenuRenderer(GameWindow gameWindow) {
        super(gameWindow);
    }

    // EFFECTS: Renders menu background
    public BufferedImage renderBackground(GameRenderer gameRenderer) {
        BufferedImage background = new BufferedImage(
                gameWindow.getSizeX(),
                gameWindow.getSizeY(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = background.createGraphics();

        // Get a frame buffer of the currently rendered game
        BufferedImage gameFrame = gameRenderer.getRenderedFrame();

        // Draw blurred background
        g.drawImage(blur(gameFrame), 0, 0, null);

        return background;
    }

    // EFFECTS: Helper function to blur buffered image
    public BufferedImage blur(BufferedImage image) {
        BufferedImage blurredImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = blurredImage.createGraphics();

        // Copy over image to blurredImage.
        g.drawImage(image, 0, 0, null);

        // Blur image using gaussian blur.
        // NOTE: I did not write this following code.
        // Source: https://stackoverflow.com/questions/29295929/java-blur-image
        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(blurredImage, null);
    }
}
