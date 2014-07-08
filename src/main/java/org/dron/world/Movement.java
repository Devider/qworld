package org.dron.world;

public class Movement {

	public static final int STEP = 20;

	private int forward;
	private int roll;
	private int yaw;

	private MovementType type = MovementType.MOVE;

	public int getForward() {
		return forward;
	}

	public int getRoll() {
		return roll;
	}

	public int getYaw() {
		return yaw;
	}

	public static Movement back() {
		return new Movement(MovementType.UNDO);
	}

	public Movement(int forward, int roll, int yaw){
		this.forward = forward;
		this.roll = roll;
		this.yaw = yaw;
	}

	protected Movement(MovementType type){
		this.type = type;
	}

	public enum MovementType{
		MOVE, UNDO;
	}

	public MovementType getType() {
		return type;
	}

}
