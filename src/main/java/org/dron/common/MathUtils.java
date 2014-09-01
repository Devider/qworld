package org.dron.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.dron.world.Ship.Sonar;
import org.nn.world.ui.AdaptiveNavPanel;

public class MathUtils {

	public static double[] normalize(double[] data){
		double[] result = new double[data.length];
		double sum = 0;
		for (int i = 0; i < data.length; i++){
			sum += Math.pow(data[i], 2.0);
		}
		double lenght = Math.sqrt(sum);
		for (int i = 0; i < result.length; i++){
			result[i] = data[i] / lenght;
		}
		return result;
	}

	public static double[] normalizeSonar(double[] data){
		return normalizeBase(data, Sonar.MAX_LENGHT);
	}

	public static double[] normalizeUserInput(double[] data){
		return normalizeBase(data, AdaptiveNavPanel.MAX - AdaptiveNavPanel.MID);
	}

	private static double[] normalizeBase(double[] data, int base){
		double[] result = new double[data.length];
		for (int i = 0; i < data.length; i++){
			result[i] = data[i] == 0 ? 0 : data[i] / base;
		}
		return result;
	}



	public static 	BigDecimal[] round(double[] values) {
		BigDecimal[] result = new BigDecimal[values.length];
		for (int i = 0; i < values.length; i++){
			result[i] = new BigDecimal(values[i], new MathContext(2,RoundingMode.HALF_UP));
		}
		return result;
	}
}
