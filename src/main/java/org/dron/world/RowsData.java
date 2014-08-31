package org.dron.world;

public class RowsData{

	public static final int SENSORS_COLUMNS = Ship.getSonarCount();

	public static final int MOVES_COLUMNS = 3;

	public static final int COLUMNS_COUNT = SENSORS_COLUMNS + MOVES_COLUMNS;

	private int[] sensors = new int[COLUMNS_COUNT];

	public RowsData(int...vals){
		if (vals.length != COLUMNS_COUNT)
			throw new IllegalArgumentException("Wrong arguments count! Expected: " + COLUMNS_COUNT);
		for (int i = 0; i < COLUMNS_COUNT; sensors[i] = vals[i++]);
	}

	public int getAt(int index){
		if (index >= COLUMNS_COUNT)
			throw new IllegalArgumentException("Wrong argument!");
		return sensors[index];
	}
}