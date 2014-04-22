package org.dron.world;

import org.dron.Ship;
import org.dron.Sonar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by PhantomX on 19.04.14.
 */
public class ShipImpl implements Ship {
    private Point point;
    private Integer directionAngle;
//    public static final int SIZE = 50;
    public static final int RADIUS = 20;
    private Integer radius;


    public ShipImpl(Point point, Integer directionAngle) {
        this.point = point;
        this.directionAngle = directionAngle;
        radius = RADIUS;
    }

    @Override
    public List<Sonar> getSonars() {
        return null;
    }

    public void doPitch(int pitch) {
        double x = pitch * Math.cos(directionAngle * Math.PI / 180);
        double y = pitch * Math.sin(directionAngle * Math.PI / 180);

        point.setX(point.getX() + (int) x);
        point.setY(point.getY() + (int) y);
    }

    public void doRoll(int roll) {
        double x = roll * Math.cos((directionAngle + 90) * Math.PI / 180);
        double y = roll * Math.sin((directionAngle + 90) * Math.PI / 180);

        point.setX(point.getX() + (int) x);
        point.setY(point.getY() + (int) y);
    }

    public void doYaw(int yaw) {
        directionAngle = (directionAngle + yaw) % 360;
    }

    public Point getPoint() {
        return point;
    }

    /**
     * рисуем корпус корабля
     *
     * @param image
     */
    private void drawHullShip(BufferedImage image) {
        Point startPiont = new Point(point.getX() - RADIUS, point.getY() - RADIUS);
//        Point endPoint = new Point(point.getX() + SIZE/2, point.getY() + SIZE/2);
//        Rectangle r = new Rectangle();
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillOval(startPiont.getX(), startPiont.getY(), RADIUS*2, RADIUS*2);
    }

    /**
     * рисуем нос корабля
     *
     * @param image
     */
    private void drawProwShip(BufferedImage image) {
        double x = point.getX() + RADIUS * Math.cos(directionAngle * Math.PI / 180);
        double y = point.getY() + RADIUS * Math.sin(directionAngle * Math.PI / 180);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.ORANGE);
        graphics.drawLine(point.getX(), point.getY(), (int)x, (int)y);
    }

    public void draw(BufferedImage image) {
        drawHullShip(image);
        drawProwShip(image);
    }

    public Integer getRadius() {
        return radius;
    }
}
