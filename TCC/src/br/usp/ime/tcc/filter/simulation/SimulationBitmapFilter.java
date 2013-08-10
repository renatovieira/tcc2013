package br.usp.ime.tcc.filter.simulation;

import android.graphics.Bitmap;
import android.graphics.Color;
import br.usp.ime.tcc.filter.BitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationBitmapFilter extends BitmapFilter {
	private double alpha, beta;
	private double a, b, c;

	public SimulationBitmapFilter(int simulationType) {
		if (simulationType == Constants.PROTANOPIA_SIMULATION_FILTER) {
			alpha = 0.992052;
			beta = 0.003974;
			a = 0.29275;
			b = 0.70725;
			c = -0.02234;
		} else if (simulationType == Constants.DEUTERANOPIA_SIMULATION_FILTER) {
			alpha = 0.957237;
			beta = 0.003974;
			a = 0.11238;
			b = 0.88761;
			c = 0.004;
		}
	}

	@Override
	protected Bitmap filterBitmap(Bitmap bmp) {
		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);

		int width = filtered.getWidth();
		int height = filtered.getHeight();

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				applyFilterToPixel(filtered, w, h);
			}
		}

		return filtered;
	}

	private void applyFilterToPixel(Bitmap filtered, int w, int h) {
		int pixel = filtered.getPixel(w, h);
		int pixelR = Color.red(pixel);
		int pixelG = Color.green(pixel);
		int pixelB = Color.blue(pixel);

		double R = getRelativeScaledColor(pixelR);
		double G = getRelativeScaledColor(pixelG);
		double B = getRelativeScaledColor(pixelB);

		B = c * (R - G) + B;
		R = a * R + b * G;
		G = R;

		pixelR = getNormalizedValue(R);
		pixelG = getNormalizedValue(G);
		pixelB = getNormalizedValue(B);

		filtered.setPixel(w, h, Color.rgb(pixelR, pixelG, pixelB));
	}

	private double getRelativeScaledColor(int color) {
		return (alpha * Math.pow((1.0 * color / Constants.MAX_COLOR_VALUE),
				Constants.REL_POWER)) + beta;
	}

	private int getNormalizedValue(double relativeScaledColor) {
		return (int) (Math.pow(relativeScaledColor, Constants.REL_POWER_INV) * Constants.MAX_COLOR_VALUE);
	}

}
