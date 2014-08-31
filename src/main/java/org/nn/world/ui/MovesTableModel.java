package org.nn.world.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.dron.world.RowsData;

public class MovesTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 9119865887897076458L;

	private List<RowsData> data;
	
	public MovesTableModel(List<RowsData> data){
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return RowsData.COLUMNS_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).getAt(columnIndex);
	}
}