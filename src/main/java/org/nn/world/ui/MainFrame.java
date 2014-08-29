package org.nn.world.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import nn.networks.nonlinear.backpropogation.BNetwork;

import org.dron.world.CrashException;
import org.dron.world.Movement;
import org.dron.world.Movement.MovementType;
import org.dron.world.RowsData;
import org.dron.world.Ship.Sonar;
import org.dron.world.World;
import org.dron.world.ai.NetworkTester;
import org.nn.world.ui.event.ControlActionEvent;
import org.nn.world.ui.event.ControlActionListener;
import org.nn.world.ui.event.MovementActionEvent;
import org.nn.world.ui.event.MovementActionListener;
import org.nn.world.ui.event.TestStudentActionEvent;
import org.nn.world.ui.event.TestStudentActionListener;

public class MainFrame extends JFrame implements MovementActionListener,TestStudentActionListener, ControlActionListener{

	private static final long serialVersionUID = 8306859277454164557L;

	private final static String WORLD_FILE = "world.jpg";

	private WorldPanel worldPanel;
	private World world;
	private NeuronNetworkDataPanel nnPanel;
	private MovesPanel movesPanel;
	private AdaptiveNavPanel navigationPanel;
	private NetworkTester tester;
	private Timer timer;


	public MainFrame() {
		setTitle("MainPanel");
		init();
	}

	private void init() {
		worldPanel = new WorldPanel();
		world = World.load(WORLD_FILE);
		nnPanel = new NeuronNetworkDataPanel();
		movesPanel = new MovesPanel();
		navigationPanel = new AdaptiveNavPanel(this);

		navigationPanel.setMovementActionListener(this);
		navigationPanel.setControlActionListener(this);

		nnPanel.addMovesDataProvider(movesPanel);
		nnPanel.setTestStudentListener(this);

		worldPanel.setImage(world.toImage());

		JSplitPane innerPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				worldPanel, movesPanel);

		JSplitPane innerPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				nnPanel, navigationPanel);

		JSplitPane outerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				innerPane1, innerPane2);

		setSize(1000, 800);

		add(outerPane);
	}

	public boolean getDesision() {
		return navigationPanel.getDesision();
	}

	public static void main(String[] args) throws IOException {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(MovementActionEvent e) {
		Movement movement;
		if (MovementType.UNDO.equals(e.getMovement().getType())) {
			RowsData data = movesPanel.pollLast();
			if (data == null)
				return;

			navigationPanel.unhold();

			movement = new Movement(
					data.getAt(RowsData.SENSORS_COLUMNS) * -1 ,
					data.getAt(RowsData.SENSORS_COLUMNS + 1) * -1 ,
					data.getAt(RowsData.SENSORS_COLUMNS + 2) * -1);
		} else {
			Sonar[] sonars = world.getShip().getSonars();
			int[] data = new int[RowsData.COLUMNS_COUNT];
			for (int i = 0; i < sonars.length; i++){
				data[i] = sonars[i].getDistance();
			}

			data[RowsData.SENSORS_COLUMNS] = e.getMovement().getForward();
			data[RowsData.SENSORS_COLUMNS + 1] = e.getMovement().getRoll();
			data[RowsData.SENSORS_COLUMNS + 2] = e.getMovement().getYaw();

			movesPanel.addSensorsData(new RowsData(data));
			movement = e.getMovement();
		}
		try{
			world.moveShip(movement);
		} catch (CrashException ex) {
			navigationPanel.hold();
			worldPanel.setImage(world.toImage());
			return;
		}
		worldPanel.setImage(world.toImage());
	}

	@Override
	public void actionPerformed(TestStudentActionEvent e) {
		BNetwork pilot = e.getStudent();
		tester = new NetworkTester(world, pilot, WORLD_FILE);
		timer = new Timer(300, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Movement mov = tester.doIteration();
				try {
					world.moveShip(mov);
				} catch (CrashException e1) {
					timer.stop();
				}
				worldPanel.setImage(world.toImage());
			}
		});
		timer.start();
	}

	@Override
	public void actionPerformed(ControlActionEvent e) {
		if (timer != null)
			timer.stop();
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.this.setVisible(false);
		MainFrame.this.dispose();
		frame.setVisible(true);
	}
}
