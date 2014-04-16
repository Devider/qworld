package org.nn.world;

import java.awt.Color;
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
		ship = new Ship(100,100);
	}

	public void updateField(int x, int y, Field field) {
		if (x > this.width)
			throw new IndexOutOfBoundsException(x + " > " + this.width);
		if (y > this.height)
			throw new IndexOutOfBoundsException(y + " > " + this.height);
		fields[x][y] = field;
	}

	public boolean isComplete() {
		for (int x = 0; x < this.width; x++)
			for (int y = 0; y < this.height; y++)
				if (fields[x][y] == null)
					return false;
		return true;
	}

	public void save(String filename){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < this.width; x++){
			for (int y = 0; y < this.height; y++){
				Field f = fields[x][y];
				image.setRGB(x, y, getColor(f).getRGB());
			}
		}
		
		image.setRGB(ship.getCoords().getX(), ship.getCoords().getY(), Color.MAGENTA.getRGB());
		
		File f = new File(filename);
		try {
			String ext = filename.substring(filename.length()-3);
			ImageIO.write(image, ext, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private Color getColor(Field field) {
		if (field.isVisited())
			return Color.RED;
		switch (field.getType()) {
		case WALL:
			return Color.BLACK;
		case SPACE:
			return Color.WHITE;
		default:
			return Color.ORANGE;
		}
	}

	public void start() {
		
		for (int i = 0; i < 1000; i++){
//		while (ship.isAlive()){
			ship.navidate(new EnvironementData(
					getNorthSersorData(ship.getCoords()),
					getEstSersorData(ship.getCoords()),
					getSouthSersorData(ship.getCoords()),
					getWestSersorData(ship.getCoords())));
			moveShip();
			checkShip();
		}
	}

	private void checkShip() {
		if ( fields[ship.getCoords().getX()][ship.getCoords().getY()].getType() == FieldType.WALL )
			ship.crash();
	}

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

	
	
	private int getNorthSersorData(Coordinates coords){
		int i = 0;
		while (fields[coords.getX()][coords.getY() - i].getType() == FieldType.SPACE){i++;}
		return i;
	}

	private int getEstSersorData(Coordinates coords){
		int i = 0;
		while (fields[coords.getX() + i][coords.getY()].getType() == FieldType.SPACE){i++;}
		return i;
	}
	
	private int getSouthSersorData(Coordinates coords){
		int i = 0;
		while (fields[coords.getX() + i][coords.getY()].getType() == FieldType.SPACE){i++;}
		return i;
	}
	
	private int getWestSersorData(Coordinates coords){
		int i = 0;
		while (fields[coords.getX() - i][coords.getY()].getType() == FieldType.SPACE){i++;}
		return i;
	}

	
}
