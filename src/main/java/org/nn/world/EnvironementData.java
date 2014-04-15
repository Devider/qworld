package org.nn.world;

public class EnvironementData {
	private int sensorNorth;
	private int sensorEst;
	private  int sensorSouth;
	private int sensorWest;
	
	public EnvironementData(int sensorNorth, int sensorEst, int sensorSouth, int sensorWest){
		this.sensorEst=sensorEst;
		this.sensorNorth=sensorNorth;
		this.sensorSouth = sensorSouth;
		this.sensorWest = sensorWest;
	}
	
	public int getSensorNorth() {
		return sensorNorth;
	}

	public int getSensorEst() {
		return sensorEst;
	}

	public int getSensorSouth() {
		return sensorSouth;
	}

	public int getSensorWest() {
		return sensorWest;
	}

}
