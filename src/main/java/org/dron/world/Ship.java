package org.dron.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

import org.dron.common.TrigUtils;

/**
 * Created by PhantomX on 19.04.14.
 */
public class Ship {
    private Point location;
    private Integer directionAngle;
    private World environement;
    private Sonar[] sonars;
    public static final int RADIUS = 10;
    private static int[] sonarsAngles =
    	{-30, -60, -90, -120, 0, 30, 60, 90, 120};

    public Ship(Point point, Integer directionAngle, World environement) {
        this.location = point;
        this.directionAngle = directionAngle;
        this.environement = environement;
        initSonars();
    }

    private void initSonars() {
		sonars = new Sonar[sonarsAngles.length];
		for (int i = 0; i < sonarsAngles.length; i++) {
			sonars[i] = new Sonar(sonarsAngles[i]);
		}
	}

	public void doPitch(int pitch) {
        double x = pitch * Math.cos(directionAngle * Math.PI / 180);
        double y = pitch * Math.sin(directionAngle * Math.PI / 180);

        location.setX((int)(location.getX() + x + .5));
        location.setY((int)(location.getY() + y + .5));
    }

    public void doRoll(int roll) {
        double x = roll * Math.cos((directionAngle + 90) * Math.PI / 180);
        double y = roll * Math.sin((directionAngle + 90) * Math.PI / 180);

        location.setX((int)(location.getX() + x + .5));
        location.setY((int)(location.getY() + y + .5));
    }

    public void doYaw(int yaw) {
    	directionAngle = TrigUtils.concat(directionAngle, yaw);
    }

    public Point getLocation() {
        return location;
    }

    /**
     * рисуем корабль
     *
     * @param image
     */
    public void draw(BufferedImage image) {
        Point startPiont = new Point(location.getX() - RADIUS, location.getY() - RADIUS);
        for (int i = 0; i < sonars.length; i++) {
			sonars[i].draw(image);
		}
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillOval(startPiont.getX(), startPiont.getY(), RADIUS*2, RADIUS*2);
        // нос
        double x = getLocation().getX() + RADIUS * Math.cos(directionAngle * Math.PI / 180) * 2;
        double y = getLocation().getY() + RADIUS * Math.sin(directionAngle * Math.PI / 180) * 2;
        graphics.setColor(Color.CYAN);
        graphics.drawLine(getLocation().getX(), getLocation().getY(), (int)x, (int)y);
        graphics.dispose();

    }

    public Sonar[] getSonars() {
		return sonars;
	}

    public static int getSonarCount(){
    	return sonarsAngles.length;
    }

    public class Sonar {

    	public static final int MAX_LENGHT = 60;
    	public static final int SENSOR_HALF_RAY_ANDLE = 15;
    	
    	
    	private int angle;

    	public Sonar(int angle){
    		this.angle = angle;
    	}

    	public Point getReflectionPoint(){
			int angles[] = {
					directionAngle - SENSOR_HALF_RAY_ANDLE,
					directionAngle,
					directionAngle + SENSOR_HALF_RAY_ANDLE
			};
			Map<Integer, Point> points = new TreeMap<>(); 
			for (int k = 0; k < angles.length; k++){
				int direction = TrigUtils.concat(angles[k], angle);
				for (int i = 0; i < MAX_LENGHT; i++){
					double x = location.getX() + i * Math.cos(direction * Math.PI / 180);
			        double y = location.getY() + i * Math.sin(direction * Math.PI / 180);
			        if (environement.getField((int)(x), (int)(y)) == FieldType.WALL){
			        	Point p = new Point((int)x, (int)y);
			        	points.put(TrigUtils.distance(getLocation(), p), p);
			        	break;
			        }
				}
			}
			if (points.size() == 0){
				return null;
			} else {
				Integer firstKey = points.keySet().iterator().next();
				return points.get(firstKey);
			}

    	}

		private Point getSensorPoint(int angle){
    		int direction = TrigUtils.concat(directionAngle, angle);
			double x = location.getX() + MAX_LENGHT * Math.cos(direction * Math.PI / 180);
	        double y = location.getY() + MAX_LENGHT * Math.sin(direction * Math.PI / 180);
        	return new Point((int)x, (int)y);
    	}

		public int getDistance() {
			Point reflectionPoint = getReflectionPoint();
			if (reflectionPoint == null)
				return MAX_LENGHT;
			return TrigUtils.distance(location, reflectionPoint);
		}

		public int getAngle() {
			return angle;
		}

		public void draw(BufferedImage image){
			Point reflectionPoint = getReflectionPoint();
			Graphics g = image.getGraphics();
			if (reflectionPoint == null){
				Point sensorPoint = getSensorPoint(angle);
				g.setColor(Color.PINK);
				g.drawLine(location.getX(), location.getY(), sensorPoint.getX(), sensorPoint.getY());
			} else {
				reflectionPoint = getReflectionPoint();
				g.setColor(Color.BLACK);
				g.drawLine(location.getX(), location.getY(), reflectionPoint.getX(), reflectionPoint.getY());
				g.setColor(Color.YELLOW);
				g.fillOval(reflectionPoint.getX()-1, reflectionPoint.getY()-1, 3, 3);
			}
			g.dispose();
		}


    }
}
