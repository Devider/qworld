package org.nn.world;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class App {

	public static void main(String[] args) throws IOException {
		File file = new File("./world.jpg");
		BufferedImage image = ImageIO.read(file);
		World world = new World(image.getWidth(), image.getHeight());
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Field f;
				if (isBlack(image.getRGB(x, y))) {
					f = new Field(FieldType.WALL);
				} else {
					f = new Field(FieldType.SPACE);
				}
				world.updateField(x, y, f);
			}
		}
		System.out.println(world.isComplete());
		world.start();
		world.save("snapshot.png");
	}

	private static boolean isBlack(int rgb) {
		Color color = new Color(rgb);
		if ((color.getRed() + color.getGreen() + color.getBlue()) > 10 * 3)
			return false;
		return true;
	}
}