package org.dron.world;

import java.text.MessageFormat;

/**
 * Created by PhantomX on 19.04.14.
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getYAsInt() {
        return (int)(y + .5);
    }

    public int getXAsInt() {
        return (int)(x + .5);
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return MessageFormat.format("[{0}, {1}]", x, y);
    }
}
