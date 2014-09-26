package org.nn.world.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import org.dron.common.MathUtils;
import org.dron.world.RowsData;

public class MovesPanel extends JPanel implements MovesDataProvider{

	private List<RowsData> data = new LinkedList<RowsData>();

	private MovesTableModel tm;

	private static final long serialVersionUID = 9037068493345085487L;

	public MovesPanel(){
		setBorder(new LineBorder(Color.gray));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		tm = new MovesTableModel(this.data);
		JTable table = new JTable(tm);
		JScrollPane spTable = new JScrollPane(table);
		JPanel panel0 = new JPanel();
		add(spTable);
		add(panel0);
		JPanel panel1 = new JPanel();
		JButton export = new JButton();
		export.setText("Export");
		export.addActionListener(new ActionListener() {

			private String format(double[] arr) {
				StringBuilder result = new StringBuilder("{").append(arr[0]);
				for (int i = 1; i < arr.length; i++) {
					result.append(", ").append(arr[i]);
				}
				result.append("}");
				return result.toString();
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					double[][] sd = new double[data.size()][RowsData.SENSORS_COLUMNS];
					double[][] ud = new double[data.size()][RowsData.MOVES_COLUMNS];
					for (int k = 0; k < data.size(); k++) {
						RowsData row = data.get(k);
						for (int i = 0; i < RowsData.SENSORS_COLUMNS; i++) {
							sd[k][i] = row.getAt(i);
						}
						for (int i = 0; i < RowsData.MOVES_COLUMNS; i++) {
							ud[k][i] = row.getAt(RowsData.SENSORS_COLUMNS + i);
						}
					}
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("."));
					int result = chooser.showSaveDialog(MovesPanel.this);
					if (result != JFileChooser.APPROVE_OPTION){
						return;
					}
					File file=new File(chooser.getSelectedFile().getPath());

				    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				    writer.write ( "#define INPUT_NEURONS\t" + RowsData.SENSORS_COLUMNS + "\n");
				    writer.write ( "#define OUTPUT_NEURONS\t" + RowsData.MOVES_COLUMNS + "\n");
				    writer.write ( "#define MAX_SAMPLES\t" +  data.size() + "\n");

				    writer.write ( "double samples[MAX_SAMPLES][INPUT_NEURONS]={\n");

				    for (int i = 0; i < sd.length; i++) {
				    	writer.write ("\t" + format(MathUtils.normalizeSonar(sd[i])) + ",\n");
				    }
				    writer.write ("};\n");

				    writer.write ( "double expected[MAX_SAMPLES][OUTPUT_NEURONS] = {\n");

				    for (int i = 0; i < ud.length- 1; i++) {
				    	writer.write ("\t" + format( MathUtils.normalizeUserInput(ud[i])) + ",\n");
				    }
				    writer.write ("};\n");


				    //Close writer
				    writer.close();
				} catch(Exception e)
				{
				    e.printStackTrace();
				}
			}
		});
		panel1.add(export);

		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {

			public void save(String filename) throws IOException{
			    FileOutputStream fos = new FileOutputStream(filename);
			    ObjectOutputStream oos = new ObjectOutputStream(fos);
			    RowsData[] rows = new RowsData[data.size()];
			    data.toArray(rows);
			    oos.writeObject(rows);
			    oos.flush();
			    oos.close();
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int result = chooser.showSaveDialog(MovesPanel.this);
				if (result != JFileChooser.APPROVE_OPTION){
					return;
				}
				try {
					save(chooser.getSelectedFile().getPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(MovesPanel.this, "Ошибка при сохранении!\n" + e.getMessage());
					e.printStackTrace();
				}
			}
		});

		panel1.add(save);

		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {


			public RowsData[] load(String filename) throws Exception{
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream oin = new ObjectInputStream(fis);
				RowsData[] obj = (RowsData[]) oin.readObject();
				oin.close();
				return obj;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int result = chooser.showSaveDialog(MovesPanel.this);
				if (result != JFileChooser.APPROVE_OPTION){
					return;
				}
				try {
					RowsData[] rows = load(chooser.getSelectedFile().getPath());
					data = new ArrayList<RowsData>(Arrays.asList(rows));
					tm.updateData(data);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		panel1.add(load);


		add(panel1);
	}

	public void addSensorsData(RowsData data){
		this.data.add(data);
		tm.fireTableDataChanged();
	}

	@Override
	public List<MovementExample> getData() {
		List<MovementExample> result = new ArrayList<MovementExample>();
		for (RowsData row : data){
			result.add(MovementExample.fromRowsData(row));
		}
		return result;
	}

	public RowsData pollLast() {
		if (data.size() == 0)
			return null;
		RowsData row = ((LinkedList<RowsData>)data).pollLast();
		return row;

	}
}
