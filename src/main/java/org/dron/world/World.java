package org.dron.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {

	private Field[][] fields;

	private int width;

	private int height;

	private Ship ship;

	public World(int width, int height) {
		fields = new Field[width][height];
		this.width = width;
		this.height = height;
		ship = new Ship(new Point(30, 120), -90, this);
	}

	public static World load(String file){
		BufferedImage image;
		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new World(image);
	}

    public World (BufferedImage image) {
        this(image.getWidth(), image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Point pointField = new Point(x, y);
                Field field;
                if (isBlack(image.getRGB(x, y))) {
                    field = new Field(FieldType.WALL, pointField);
                } else {
                    field = new Field(FieldType.SPACE, pointField);
                }
                setField(field);
            }
        }
        if (isComplete()) {
            System.out.println("World is filled");
        }
    }

    private static boolean isBlack(int rgb) {
        Color color = new Color(rgb);
        if ((color.getRed() + color.getGreen() + color.getBlue()) > 10 * 3)
            return false;
        return true;
    }

    public FieldType getField(int x,int y){
    	return fields[x][y].getType();
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
        ship.draw(image);
    }

	public BufferedImage toImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        draw(image);
        if (!checkShip()){
        	Graphics g = image.getGraphics();
        	g.setColor(Color.RED);
        	g.setFont(new Font("Arial",Font.BOLD, 140));
        	g.drawString("CRASH!", 50, 200);
        	g.dispose();
        }
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

    public boolean checkShip()  {
        int radius = Ship.RADIUS;
        Point point = ship.getLocation();
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


    public Ship getShip() {
        return ship;
    }

    public void moveShip(Movement data) throws CrashException{
    	ship.doPitch(data.getForward());
    	ship.doRoll(data.getRoll());
    	ship.doYaw(data.getYaw());
    	if (! checkShip())
    		throw new CrashException();
    }
}
