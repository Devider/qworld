package org.nn.world;

public class Ship {
	private MovementDirection direction = MovementDirection.NORTH;
	private Coordinates coords = new Coordinates();
	private boolean isAlive = true;
	private IMovementStrategy strategy = new RightHandMovementStrategy();
	

	public boolean isAlive() {
		return isAlive;
	}

	public void crash() {
		this.isAlive = false;
	}

	public MovementDirection getDirection() {
		return direction;
	}

	public Ship(int x, int y){
		coords.setX(x);
		coords.setY(y);
	}
	
	public void navidate(EnvironementData data){
		if (!isAlive)
			throw new RuntimeException("I'm dead!");
		DecisionData dd = DirectionTransformer.transformData(data, direction);
		Movement movement = strategy.doManeuver(dd);
		direction = DirectionTransformer.transformMovement(movement, direction);
	}

	
	public Coordinates getCoords() {
		return coords;
	}

}
