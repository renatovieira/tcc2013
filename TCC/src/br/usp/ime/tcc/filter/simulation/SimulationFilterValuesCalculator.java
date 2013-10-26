package br.usp.ime.tcc.filter.simulation;

import android.graphics.Color;
import br.usp.ime.tcc.utils.Constants;

public class SimulationFilterValuesCalculator {
	private double alpha, beta;
	private double a, b, c;

	public SimulationFilterValuesCalculator(int simulationType) {
		setParametersValues(simulationType);
	}

	private void setParametersValues(int simulationType) {
		if (simulationType == Constants.DEUTERANOPIA_SIMULATION_FILTER) {
			alpha = 0.957237;
			beta = 0.0213814;
			a = 0.29275;
			b = 0.70725;
			c = -0.02234;
		} else if (simulationType == Constants.PROTANOPIA_SIMULATION_FILTER) {
			alpha = 0.992052;
			beta = 0.003974;
			a = 0.11238;
			b = 0.88761;
			c = 0.004;
		}
	}

	public int getValueForPixel(int pixel) {
		double R = getRelativeScaledColor(Color.red(pixel));
		double G = getRelativeScaledColor(Color.green(pixel));
		double B = getRelativeScaledColor(Color.blue(pixel));

		B = c * (R - G) + B;
		R = a * R + b * G;
		//G = R; 

		int newPixelRG = getNormalizedValue(R);
		int newPixelB = getNormalizedValue(B);

		return Color.rgb(newPixelRG, newPixelRG, newPixelB);
	}

	private double getRelativeScaledColor(int color) {
		return (alpha * Math.pow((1.0 * color / Constants.MAX_COLOR_VALUE),
				Constants.REL_POWER)) + beta;
	}

	private int getNormalizedValue(double relativeScaledColor) {
		return (int) (Math.pow(relativeScaledColor, Constants.REL_POWER_INV) * Constants.MAX_COLOR_VALUE);
	}
}
