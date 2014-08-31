package org.nn.world.ui;

import org.dron.world.RowsData;

public class MovementExample {

	private double[] sensors;

	public double[] getSensors() {
		return sensors;
	}

	public double[] getUserDesision() {
		return userDesision;
	}

	private double[] userDesision;

	protected MovementExample() {
	}

	public static MovementExample fromRowsData(RowsData data) {
		MovementExample result = new MovementExample();
		result.sensors = new double[RowsData.SENSORS_COLUMNS];
		for (int i = 0; i < RowsData.SENSORS_COLUMNS; result.sensors[i] = data
				.getAt(i++))
			;
		result.userDesision = new double[3];
		result.userDesision[0] = data.getAt(RowsData.SENSORS_COLUMNS + 0);
		result.userDesision[1] = data.getAt(RowsData.SENSORS_COLUMNS + 1);
		result.userDesision[2] = data.getAt(RowsData.SENSORS_COLUMNS + 2);
		return result;
	}

}
