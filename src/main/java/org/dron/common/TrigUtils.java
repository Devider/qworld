package org.dron.common;

import org.dron.world.Point;


public class TrigUtils {
	public static int concat(int angle1, int angle2){
        return (int)(angle1 + angle2) % 360;
	}

	public static int distance (Point p1, Point p2){
		int deltaX = p1.getXAsInt() - p2.getXAsInt();
		int deltaY = p1.getYAsInt() - p2.getYAsInt();
		return (int)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}

}
