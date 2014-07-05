package org.dron.world;

public class Movement {
	private int forward;
	private int roll;
	private int yaw;
	
	public int getForward() {
		return forward;
	}

	public int getRoll() {
		return roll;
	}

	public int getYaw() {
		return yaw;
	}

	public Movement(int forward, int roll, int yaw){
		this.forward = forward;
		this.roll = roll;
		this.yaw = yaw;
	}
}
