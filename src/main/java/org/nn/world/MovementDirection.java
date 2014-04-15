package org.nn.world;

public enum MovementDirection{
	NORTH, EST, SOUTH, WEST;
	
	public MovementDirection right(){
		int current = this.ordinal() + 1;
		if (current > MovementDirection.values().length - 1)
			current = 0;
		return MovementDirection.values()[current];
	}

	public MovementDirection left(){
		int current = this.ordinal() - 1;
		if (current < 0)
			current = MovementDirection.values().length - 1;
		return MovementDirection.values()[current];
	}
}