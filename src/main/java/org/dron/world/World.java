package org.dron.world;

import org.dron.Movement;
import org.dron.Ship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class World {

	private Field[][] fields;

	private int width;

	private int height;

	private ShipImpl ship;

	public World(int width, int height) {
		fields = new Field[width][height];
		this.width = width;
		this.height = height;
		ship = new ShipImpl(new Point(100, 100), 0);
	}

    public static World load(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        World world = new World(image.getWidth(), image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Point pointField = new Point(x, y);
                Field field;
                if (isBlack(image.getRGB(x, y))) {
                    field = new Field(FieldType.WALL, pointField);
                } else {
                    field = new Field(FieldType.SPACE, pointField);
                }
                world.setField(field);
            }
        }
        if (world.isComplete()) {
            System.out.println("World is filled");
        }
        return world;
    }

    private static boolean isBlack(int rgb) {
        Color color = new Color(rgb);
        if ((color.getRed() + color.getGreen() + color.getBlue()) > 10 * 3)
            return false;
        return true;
    }


	public void setField(Field field) {
        Point point = field.getPoint();
        if (point.getX() > this.width)
			throw new IndexOutOfBoundsException(point.getX() + " > " + this.width);
		if (point.getY() > this.height)
			throw new IndexOutOfBoundsException(point.getY() + " > " + this.height);
		fields[point.getX()][point.getY()] = field;
	}

	public boolean isComplete() {
		for (int x = 0; x < this.width; x++)
			for (int y = 0; y < this.height; y++)
				if (fields[x][y] == null)
					return false;
		return true;
	}

    private void draw(BufferedImage image) {
        for (int x = 0; x < this.width; x++){
            for (int y = 0; y < this.height; y++){
                Field f = fields[x][y];
                image.setRGB(x, y, getColor(f).getRGB());
            }
        }
    }

	public BufferedImage draw(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        draw(image);
        ship.draw(image);
        return image;
	}

	private Color getColor(Field field) {
		switch (field.getType()) {
		case WALL:
			return Color.BLACK;
		case SPACE:
			return Color.WHITE;
		default:
			return Color.ORANGE;
		}
	}

    private LinkedList<Field> findFieldsRound(Point point, Integer radius) {
        LinkedList<Field> result = new LinkedList<Field>();
        long rad2 = radius * radius;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                long deltaX = point.getX() - x;
                long deltaY = point.getY() - y;
                if (rad2 >= deltaX*deltaX + deltaY*deltaY) {
                    result.add(fields[x][y]);
                }
            }
        }

        return result;
    }

    public boolean checkShip()  {
        int radius = ship.getRadius();
        Point point = ship.getPoint();
        long rad2 = radius * radius;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                long deltaX = point.getX() - x;
                long deltaY = point.getY() - y;
                if (rad2 >= deltaX*deltaX + deltaY*deltaY && fields[x][y].getType() == FieldType.WALL) {
                    return false;
                }
            }
        }
        return true;
	}
/*

	private void moveShip(){
		switch (ship.getDirection()) {
		case NORTH:
			ship.getCoords().setY(ship.getCoords().getY() - 1);
			break;
		case EST:
			ship.getCoords().setX(ship.getCoords().getX() + 1);
			break;
		case SOUTH:
			ship.getCoords().setY(ship.getCoords().getY() + 1);
			break;
		case WEST:
			ship.getCoords().setX(ship.getCoords().getX() - 1);
			break;
		}
		fields[ship.getCoords().getX()][ship.getCoords().getY()].setVisited(true);
		System.out.println(ship.getCoords().getX() + " " + ship.getCoords().getY());
	}
*/

    public Ship getShip() {
        return ship;
    }

    public void moveShip(Movement movement) {
        ship.doPitch(movement.pitch());
        ship.doRoll(movement.roll());
        ship.doYaw(movement.yaw());
    }
}
