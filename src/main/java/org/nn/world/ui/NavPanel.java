package org.nn.world.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.dron.world.Movement;
import org.nn.world.ui.event.ControlActionEvent;
import org.nn.world.ui.event.ControlActionListener;
import org.nn.world.ui.event.MovementActionEvent;
import org.nn.world.ui.event.MovementActionListener;

public class NavPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5961030522679460490L;

	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;

	private MovementActionListener movementListener = null;
	private ControlActionListener controlListener = null;

	private JButton forward = new JButton("^");
	private JButton rollLeft = new JButton("<-");
	private JButton rollRight = new JButton("->");
	private JButton strafeLeft = new JButton("<<");
	private JButton strafeRight = new JButton(">>");
	private JButton undo = new JButton("Undo");
	private JButton reset = new JButton("Reset");

	public NavPanel(MainFrame mainPanel){
		setSize(WIDTH, HEIGHT);
		setLayout(new  BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new LineBorder(Color.gray));

		addAll(forward);
		addAll(rollLeft, rollRight);
		addAll(strafeLeft, strafeRight);
		addAll(undo, reset);
		forward.addActionListener(this);
		rollLeft.addActionListener(this);
		rollRight.addActionListener(this);
		strafeLeft.addActionListener(this);
		strafeRight.addActionListener(this);
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
		if ("^".equals(e.getActionCommand())) {
			movement = new Movement(Movement.STEP, 0, 0);
		} else if ("<-".equals(e.getActionCommand())) {
			movement = new Movement(0, 0, -90);
		} else if ("<<".equals(e.getActionCommand())) {
			movement = new Movement(0, Movement.STEP * -1, 0);
		} else if ("->".equals(e.getActionCommand())) {
			movement = new Movement(0, 0, 90);
		} else if (">>".equals(e.getActionCommand())) {
			movement = new Movement(0, Movement.STEP, 0);
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
		forward.setEnabled(false);
		rollLeft.setEnabled(false);
		rollRight.setEnabled(false);
		strafeLeft.setEnabled(false);
		strafeRight.setEnabled(false);
	}

	public void unhold() {
		forward.setEnabled(true);
		rollLeft.setEnabled(true);
		rollRight.setEnabled(true);
		strafeLeft.setEnabled(true);
		strafeRight.setEnabled(true);
	}


}