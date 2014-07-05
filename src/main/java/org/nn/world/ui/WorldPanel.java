package org.nn.world.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class WorldPanel extends JPanel {

	private static final long serialVersionUID = 3915856109557815793L;

	private BufferedImage image = null;

	public WorldPanel() {
		setBorder(new LineBorder(Color.gray));
		setDoubleBuffered(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getImage() == null)
			return;
		g.drawImage(getImage(), 0, 0, null);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		setMinimumSize(new Dimension(image.getWidth(), image.getHeight()));
		this.image = image;
		repaint();
	}

}
