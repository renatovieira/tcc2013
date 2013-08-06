package br.usp.ime.tcc.filter.colorhighlight;

import android.graphics.Bitmap;
import android.graphics.Color;
import br.usp.ime.tcc.filter.BitmapFilter;

public class ColorHighlightBitmapFilter extends BitmapFilter {
	private ColorHighlightParametersCalculator colorHighlightParametersCalculator;

	public ColorHighlightBitmapFilter(int red, int green, int blue,
			int redTolerance, int greenTolerance, int blueTolerance) {
		colorHighlightParametersCalculator = new ColorHighlightParametersCalculator(
				red, green, blue, redTolerance, greenTolerance, blueTolerance);
	}

	@Override
	protected Bitmap filterBitmap(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				int pixel = filtered.getPixel(w, h);
				int pixelR = getRedValueFromPixel(pixel);
				int pixelG = getGreenValueFromPixel(pixel);
				int pixelB = getBlueValueFromPixel(pixel);

				if (!colorHighlightParametersCalculator
						.pixelShouldBeHighlighted(pixelR, pixelG, pixelB)) {
					int grayValue = (pixelR + pixelG + pixelB) / 3;
					filtered.setPixel(w, h,
							Color.rgb(grayValue, grayValue, grayValue));
				}
			}
		}

		return filtered;
	}

	protected int getBlueValueFromPixel(int pixel) {
		return Color.blue(pixel);
	}

	protected int getGreenValueFromPixel(int pixel) {
		return Color.green(pixel);
	}

	protected int getRedValueFromPixel(int pixel) {
		return Color.red(pixel);
	}
}
