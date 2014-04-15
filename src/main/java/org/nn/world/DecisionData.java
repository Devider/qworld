package org.nn.world;

public class DecisionData {
	private int rightSensor;
	private int leftSensor;
	private int straightSensor;
	public DecisionData(int leftSensor, int straightSensor, int rightSensor) {
		this.leftSensor = leftSensor;
		this.rightSensor = rightSensor;
		this.straightSensor = straightSensor;
	}
	public int getRightSensor() {
		return rightSensor;
	}
	public int getLeftSensor() {
		return leftSensor;
	}
	public int getStraightSensor() {
		return straightSensor;
	}

}
