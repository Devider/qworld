package org.dron.common;

import org.dron.world.Point;


public class TrigUtils {
	public static int concat(int angle1, int angle2){
        return (int)(angle1 + angle2 + 0.5) % 360;
	}
	
	public static int distance (Point p1, Point p2){
		int deltaX = p1.getX() - p2.getX();
		int deltaY = p1.getY() - p2.getY();
		return (int)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}
	
}
