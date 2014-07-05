package org.dron.world.ai;

import org.dron.world.Ship;

public class SonarData {
	
	private int[] sonars = new int[Ship.getSonarCount()];
	
	public SonarData(int...vals){
		if (vals.length != Ship.getSonarCount())
			throw new IllegalArgumentException("Wrong arguments count! Expected: " + Ship.getSonarCount());
		for (int i = 0; i < Ship.getSonarCount(); sonars[i] = vals[i++]);
	}
	
	public int getAt(int index){
		if (index >= Ship.getSonarCount())
			throw new IllegalArgumentException("Wrong argument!");
		return sonars[index];
	}

	public int[] getSonars() {
		return sonars;
	}
	
}
