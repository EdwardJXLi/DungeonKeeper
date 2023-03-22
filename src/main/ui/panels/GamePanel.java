package ui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    private BufferedImage image;

    public GamePanel() {
        super();

//        try {
//            // Load the image from a file
//            BufferedImage rawImage = ImageIO.read(new File("assets/tileset.png"));
//            image = resize(rawImage, 400, 400);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_FAST);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

//    @Override
//    public void paint(Graphics g) {
//        // Draw the image on screen
//        g.drawImage(image, 0, 0, null);
//    }

}
