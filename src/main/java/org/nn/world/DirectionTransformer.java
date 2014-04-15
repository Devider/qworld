package org.nn.world;

public class DirectionTransformer{
	public static DecisionData transformData(EnvironementData data, MovementDirection current){
		DecisionData desision = null;
		switch (current) {
		case EST:
			desision = new DecisionData(data.getSensorNorth(), data.getSensorEst(), data.getSensorSouth());
			break;
		case WEST:
			desision = new DecisionData(data.getSensorSouth(), data.getSensorWest(), data.getSensorNorth());
			break;
		case NORTH:
			desision = new DecisionData(data.getSensorWest(), data.getSensorNorth(), data.getSensorEst());
			break;
		case SOUTH:
			desision = new DecisionData(data.getSensorEst(), data.getSensorSouth(), data.getSensorWest());
			break;
		}			
		return desision;
	}
	
	public static MovementDirection transformMovement(Movement movement, MovementDirection current){
		MovementDirection result = null;
		switch (movement) {
		case STRAIGHT:
			result = current;
			break;
		case RIGHT:
			result =  current.right();
			break;
		case LEFT:
			result =  current.left();
			break;
		}
		return result;
	}
}