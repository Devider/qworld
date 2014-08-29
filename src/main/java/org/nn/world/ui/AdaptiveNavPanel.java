package org.nn.world.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;

import org.dron.world.Movement;
import org.nn.world.ui.event.ControlActionEvent;
import org.nn.world.ui.event.ControlActionListener;
import org.nn.world.ui.event.MovementActionEvent;
import org.nn.world.ui.event.MovementActionListener;

public class AdaptiveNavPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5961030522679460490L;

	public static final int WIDTH = 100;
	public static final int HEIGHT = 350;

	public static int MAX = 20;
	public static int MIN = 0;
	public static int MID = 10;

	private MovementActionListener movementListener = null;
	private ControlActionListener controlListener = null;

	private JSlider pitch = new JSlider(JSlider.VERTICAL, MIN, MAX, 10);
	private JSlider yaw = new JSlider(JSlider.HORIZONTAL, MIN, MAX, 10);
	private JSlider roll = new JSlider(JSlider.HORIZONTAL, MIN, MAX, 10);

	private JButton go = new JButton("Go!");
	private JButton undo = new JButton("Undo");
	private JButton reset = new JButton("Reset");

	public AdaptiveNavPanel(MainFrame mainPanel){
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new  BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new LineBorder(Color.gray));


		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put( new Integer( MIN ), new JLabel("Back") );
		labelTable.put( new Integer( MAX ), new JLabel("Forward") );
		labelTable.put( new Integer( (MAX - MIN) / 2 ), new JLabel("Stop") );
		pitch.setLabelTable(labelTable);
		pitch.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTableYaw = new Hashtable<>();
		labelTableYaw.put( new Integer( MIN ), new JLabel("Left") );
		labelTableYaw.put( new Integer( MAX ), new JLabel("Right") );
		labelTableYaw.put( new Integer( (MAX - MIN) / 2 ), new JLabel("0") );
		yaw.setLabelTable(labelTableYaw);
		yaw.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTableRoll = new Hashtable<>();
		labelTableRoll.put( new Integer( MIN ), new JLabel("<<") );
		labelTableRoll.put( new Integer( MAX ), new JLabel(">>") );
		labelTableRoll.put( new Integer( (MAX - MIN) / 2 ), new JLabel("0") );
		roll.setLabelTable(labelTableRoll);
		roll.setPaintLabels(true);

		addAll(pitch);
		addAll(yaw);
		addAll(roll);

		addAll(go, undo, reset);
		go.addActionListener(this);
		undo.addActionListener(this);
		reset.addActionListener(this);
	}

	private void addAll(JComponent...components){
		JPanel p = new JPanel();
		for( JComponent c : components){
			p.add(c);
		}
		add(p);
	}

	public boolean getDesision() {
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Movement movement = null;
		if ("Go!".equals(e.getActionCommand())) {
			movement = new Movement(
					pitch.getValue() - MID,
					roll.getValue() - MID,
					yaw.getValue() - MID
					);
		} else if ("Undo".equals(e.getActionCommand())) {
			movement = Movement.back();
		} else if ("Reset".equals(e.getActionCommand())) {
			if (controlListener != null){
				controlListener.actionPerformed(new ControlActionEvent(ControlActionEvent.ActionType.RESET));
			}
			return;
		}
		if (movementListener != null){
			movementListener.actionPerformed(new MovementActionEvent(movement));
		}
	}

	public void setMovementActionListener(MovementActionListener listener) {
		this.movementListener = listener;
	}

	public void setControlActionListener(ControlActionListener listener) {
		this.controlListener = listener;
	}

	public void hold() {
		go.setEnabled(false);
	}

	public void unhold() {
		go.setEnabled(true);
	}


}