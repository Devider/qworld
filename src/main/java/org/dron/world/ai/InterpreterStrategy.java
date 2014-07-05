package org.dron.world.ai;

import org.dron.world.Movement;

public abstract class InterpreterStrategy {
	public abstract Movement interpret(double[] data);
	
	protected int getMaxIndex(double[] data){
		int maxIndex = -1;
		double max = -1;
		for (int i = 0; i < data.length; i++){
			if (Math.abs(data[i]) > max){
				maxIndex = i;
				max = Math.abs(data[i]);	
			}
		}
		return maxIndex;
	}
}
