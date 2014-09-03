package org.dron.world.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nn.networks.nonlinear.backpropogation.BNetwork;

import org.dron.common.MathUtils;
import org.dron.world.Movement;
import org.dron.world.RowsData;
import org.dron.world.Ship;
import org.dron.world.Ship.Sonar;
import org.dron.world.World;

public class NetworkTester {

	private final BNetwork pilot;
	private final World world;
	private int historyDeep;
	private List<SonarData> history = new ArrayList<>();


	public NetworkTester(World world, BNetwork pilot, String worldImage){
		this.pilot = pilot;
		historyDeep = pilot.getNeuronCountAt(0) / Ship.getSonarCount();
		this.world = world;

	}

	public Movement doIteration(){
		Sonar[] sonars = world.getShip().getSonars();
		int[] data = new int[RowsData.SENSORS_COLUMNS];
		for (int i = 0; i < sonars.length; i++){
			data[i] = sonars[i].getDistance();
		}
		history.add(new SonarData(data));
		double[] test = prepareData();

		double[] desision = pilot.test(
				MathUtils.normalizeSonar(test)
				);

		System.out.println(Arrays.toString(test));
		System.out.println(Arrays.toString(desision));

		return interpretOutputs(desision);
	}

	private Movement interpretOutputs(double[] desision) {
		return AdaptiveStrategy.interpret(desision);
	}

	private double[] prepareData() {
		int sensorsCount = Ship.getSonarCount();
		int[] result = new int[sensorsCount * historyDeep];
		int historySize = history.size();
		int availDeep = (historyDeep < historySize) ? historyDeep : historySize;
		for (int i = 0; i < availDeep; i++ ) {
			System.arraycopy(history.get(historySize - i - 1).getSonars(), 0,
					result, sensorsCount * i, sensorsCount);
		}
		return toDouble(result);
	}

	private double[] toDouble(int[] vals){
		double[] result = new double[vals.length];
		for (int i= 0; i < vals.length; i++)
			result[i] = vals[i];
		return result;
	}

	@SuppressWarnings("unused")
	private final InterpreterStrategy WTAStrategy = new InterpreterStrategy() {

		@Override
		public Movement interpret(double[] data) {
			int index = getMaxIndex(data);
			if (index == 0){
				return new Movement(10, 0, 0);
			} else if (index == 1){
				return new Movement(0, 0, 90 /* + error*/ );
			} else if (index == 2){
				return new Movement(0, 0, -90 /* + error*/ );
			} else if (index == 3){
				return new Movement(0, 10, 0);
			} else if (index == 4){
				return new Movement(0, -10, 0);
			}
			throw new RuntimeException("Somethimg wrong...");
		}
	};

	private final InterpreterStrategy AdaptiveStrategy = new InterpreterStrategy() {
		public Movement interpret(double[] data) {
			return new Movement((int)(data[0] * 10), (int)(data[1] * 10), (int)(data[2] * 10));
		};
	};
}
