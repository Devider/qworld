package org.dron.world;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by PhantomX on 19.04.14.
 */
class FrameImagePanel extends Panel
{
    BufferedImage image;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 400;


    public FrameImagePanel(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
//        System.out.println("WIDTH = " + WIDTH);
        g.drawImage(image, 0, 0, this);
    }

    public void start() {
        final FrameImagePanel frame = this;
        Runnable runnable = new Runnable() {
            public void run() {
                final JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                f.add(new JScrollPane(frame));
                f.setSize(WIDTH, HEIGHT);
                f.setLocation(200,200);
                f.setVisible(true);
            }

        };
        new Thread(runnable).start();
    }

    public void updateImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }
}