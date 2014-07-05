package org.nn.world.ui.event;

import org.dron.world.Movement;


public class MovementActionEvent {
	
	private Movement movement;
	
	public MovementActionEvent(Movement m){
		this.movement = m;
	}

	public Movement getMovement() {
		return movement;
	}

}
