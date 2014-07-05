package org.nn.world.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import org.dron.world.RowsData;

public class MovesPanel extends JPanel implements MovesDataProvider{
	
	private List<RowsData> data = new ArrayList<RowsData>();
	
	private static final long serialVersionUID = 9037068493345085487L;
	
	public MovesPanel(){
		setBorder(new LineBorder(Color.gray));
		add(new JTable(new MovesTableModel(this.data)));
	}
	
	public void addSensorsData(RowsData data){
		this.data.add(data);
		doLayout();
	}

	@Override
	public List<MovementExample> getData() {
		List<MovementExample> result = new ArrayList<MovementExample>();
		for (RowsData row : data){
			result.add(MovementExample.fromRowsData(row));
		}
		return result;
	}
}
