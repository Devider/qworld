package org.dron.world;

public class RowsData{
			
	public static int SENSORS_COLUMNS = 7;
	
	public static int MOVES_COLUMNS = 3;
	
	public static final int COLUMNS_COUNT = SENSORS_COLUMNS + MOVES_COLUMNS;
	
	public static final String[] COLUMNS = {"IIX", "X", "XI", "XII", "I", "II", "IV", "MOV", "ROLL", "YAW"};
	
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