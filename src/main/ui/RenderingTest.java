package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RenderingTest extends JFrame {

    private BufferedImage image;

    public RenderingTest() {
        super("Image Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        try {
            // Load the image from a file
            image = ImageIO.read(new File("assets/Dungeon_Tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        // Draw the image on screen
        g.drawImage(image, 0, 0, null);
    }
}