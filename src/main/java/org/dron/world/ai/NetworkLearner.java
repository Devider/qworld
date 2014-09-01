package org.dron.world.ai;

import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import nn.networks.nonlinear.backpropogation.BNetwork;

import org.dron.common.MathUtils;
import org.dron.world.Ship;
import org.nn.world.ui.MovementExample;

public class NetworkLearner extends Thread{

	private int persents;
	private BNetwork student;
	private List<MovementExample> list;
	private int deep;
	private int count;

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;


	public int getPersents() {
		return persents;
	}

	public NetworkLearner(BNetwork student, List<MovementExample> list, int deep, int count){
		this.count = count;
		this.student = student;
		this.list = list;
		this.deep = deep;
	}

	private double[] concat(int number, int deep, List<MovementExample> data){
		int sensorsCount = Ship.getSonarCount();
		double[] result = new double[sensorsCount * deep];
		if (number < deep)
			throw new IllegalArgumentException("number < deep");
		for (int i = 0; i < deep; i++ ) {
			System.arraycopy(data.get(number - i).getSensors(), 0,
					result, sensorsCount * i, sensorsCount);
		}
		return result;
	}

	@Override
	public void run() {
		Random randomizer = new Random();
		if (list.size() < deep){
			JOptionPane.showMessageDialog(null, "Недостаточно данных для обучения", "Полетайте немного", JOptionPane.OK_OPTION);
			return;
		}

		for (int i = 0; i < count; i++){
			int rand = randomizer.nextInt(list.size() - deep) + deep;
			double[] teachData = concat(rand, deep, list);
			student.teach(
					MathUtils.normalizeSonar(teachData),
					//teachData,
					MathUtils.normalizeUserInput(list.get(rand).getUserDesision()));
			if (i % 100 == 0){
				persents = (int)(i * 1.0F / count * 100);
			}
		}
		persents = 100;
	}
}
