package org.dron.world;

public class Movement {

	public static final int STEP = 20;

	private int forward;
	private int roll;
	private int yaw;

	public static final Movement ZERO = new Movement(0, 0, 0);

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + forward;
		result = prime * result + roll;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + yaw;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (forward != other.forward)
			return false;
		if (roll != other.roll)
			return false;
		if (type != other.type)
			return false;
		if (yaw != other.yaw)
			return false;
		return true;
	}



}
