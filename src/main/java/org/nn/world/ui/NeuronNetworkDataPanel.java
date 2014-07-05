package org.nn.world.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import nn.common.ActivationFunctions;
import nn.networks.nonlinear.backpropogation.BNetwork;
import nn.networks.nonlinear.backpropogation.BNeuronFactory;

import org.dron.world.RowsData;
import org.dron.world.ai.NetworkLearner;
import org.nn.world.ui.event.TestStudentActionEvent;
import org.nn.world.ui.event.TestStudentActionListener;

public class NeuronNetworkDataPanel extends JPanel {

	private static final long serialVersionUID = -874242159670473877L;
	private JButton teachButton = new JButton("Teach");
	private JButton saveButton = new JButton("Save");
	private JButton loadButton = new JButton("Load");
	private JComboBox<Integer> teachingDeep = new JComboBox<Integer>();
	private JTextField teachingCount = new JTextField("100000", 8);
	private JProgressBar progress = new JProgressBar(0, 100);
	private JButton testButton = new JButton("Test");
	private MovesDataProvider provider = null;
	private BNetwork student = null;
	private Timer activityMonitor = null;
	private NetworkLearner learner = null;
	private TestStudentActionListener listener = null;

	public void addMovesDataProvider(MovesDataProvider mdp) {
		provider = mdp;
	}

	public NeuronNetworkDataPanel() {
		setBorder(new LineBorder(Color.gray));
		setMinimumSize(new Dimension(100, 500));
		
		initControls();
		
		initNN();
	}

	private void initNN() {
		int deep = teachingDeep.getItemAt(teachingDeep.getSelectedIndex());
		student = new BNetwork(new BNeuronFactory(ActivationFunctions.SIGMOIDAL), 
				RowsData.SENSORS_COLUMNS * deep, 15, 15, 5);
	}

	private void updateProgressBar(float percents) {
		System.out.println(percents);
		progress.setValue((int) percents);
		progress.doLayout();
		progress.invalidate();
		progress.repaint();
	}
	
	public void setTestStudentListener(TestStudentActionListener listener) {
		this.listener = listener;
	}

	private void initControls() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel0 = new JPanel();
		JLabel label0 = new JLabel("Глубина обучения: ");
		panel0.add(label0);
		teachingDeep.setSize(100, 10);
		teachingDeep.addItem(1);
		teachingDeep.addItem(2);
		teachingDeep.addItem(3);
		teachingDeep.setSelectedIndex(1);
		panel0.add(teachingDeep);
		add(panel0);
		
		//add(new HiddenLayersPanel());
		
		JPanel panel3 = new JPanel();
		JLabel label3 = new JLabel("Циклов обучения: ");
		panel3.add(label3);
		panel3.add(teachingCount);
		add(panel3);
		
		JPanel panel4 = new JPanel();
		panel4.add(teachButton);
		panel4.add(saveButton);
		panel4.add(loadButton);
		add(panel4);
		JPanel panel5 = new JPanel();
		panel5.add(progress);
		add(panel5);
		JPanel panel6 = new JPanel();
		panel6.add(testButton);
		add(panel6);
		
		teachButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<MovementExample> list = provider.getData();
				int deep = teachingDeep.getSelectedIndex() + 1;
				int count = Integer.valueOf(teachingCount.getText());
				learner = new NetworkLearner(student, list, deep, count);
				teachButton.setEnabled(false);
				learner.start();
				activityMonitor = new Timer(500, new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						updateProgressBar(learner.getPersents());
						if (learner.getPersents() == 100 || !learner.isAlive()){
							teachButton.setEnabled(true);
							activityMonitor.stop();
						}
					}
				});
				activityMonitor.start();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int result = chooser.showSaveDialog(NeuronNetworkDataPanel.this);
				if (result == JFileChooser.APPROVE_OPTION){
					try {
						student.save(chooser.getSelectedFile().getPath());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(NeuronNetworkDataPanel.this, "Ошибка при сохранении файла", "Ошибка", JOptionPane.OK_OPTION);
						e1.printStackTrace();
					}
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int result = chooser.showOpenDialog(NeuronNetworkDataPanel.this);
				if (result == JFileChooser.APPROVE_OPTION){
					try {
						student = BNetwork.load(chooser.getSelectedFile().getPath());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(NeuronNetworkDataPanel.this, "Ошибка при загрузке файла", "Ошибка", JOptionPane.OK_OPTION);
						e1.printStackTrace();
					}
				}
			}
		});
		
		testButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listener != null)
					listener.actionPerformed(new TestStudentActionEvent(student));
			}
		});
		
		teachingDeep.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				initNN();
				System.out.println();
			}
		});
		
	}
}
