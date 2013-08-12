package br.usp.ime.tcc.filter.simulation;

import android.graphics.Bitmap;
import br.usp.ime.tcc.filter.BitmapFilter;

public class SimulationBitmapFilter extends BitmapFilter {
	private SimulationFilterValuesCalculator calculator;

	public SimulationBitmapFilter(int simulationType) {
		calculator = new SimulationFilterValuesCalculator(simulationType);
	}

	@Override
	protected Bitmap filterBitmap(Bitmap bmp) {
		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);

		int width = filtered.getWidth();
		int height = filtered.getHeight();

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++)
				filtered.setPixel(w, h,
						calculator.getValueForPixel(filtered.getPixel(w, h)));
		return filtered;
	}
}
