package org.nn.world;

public class RightHandMovementStrategy implements IMovementStrategy{

	
	@Override
	public Movement doManeuver(DecisionData data) {
		
		if (data.getStraightSensor() < 20)
			return Movement.RIGHT;
		return Movement.STRAIGHT;
			 
	}
	
};
