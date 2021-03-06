package br.usp.ime.tcc.filter.colorhighlight;

import android.graphics.Bitmap;
import android.graphics.Color;
import br.usp.ime.tcc.filter.BitmapFilter;

public class ColorHighlightBitmapFilter extends BitmapFilter {
	private static final double LUMA_RED_COEFFICENT = 0.2126;
	private static final double LUMA_GREEN_COEFFICENT = 0.7152;
	private static final double LUMA_BLUE_COEFFICENT = 0.0722;

	private ColorHighlightParametersCalculator colorHighlightParametersCalculator;

	public ColorHighlightBitmapFilter(int red, int green, int blue,
			int redTolerance, int greenTolerance, int blueTolerance) {
		colorHighlightParametersCalculator = new ColorHighlightParametersCalculator(
				red, green, blue, redTolerance, greenTolerance, blueTolerance);
	}

	@Override
	protected Bitmap filterBitmap(Bitmap bmp) {
		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);
		
		int width = filtered.getWidth();
		int height = filtered.getHeight();

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				filterPixel(filtered, w, h);
			}
		}

		return filtered;
	}

	private void filterPixel(Bitmap filtered, int w, int h) {
		int pixel = filtered.getPixel(w, h);
		int pixelR = Color.red(pixel);
		int pixelG = Color.green(pixel);
		int pixelB = Color.blue(pixel);

		if (!colorHighlightParametersCalculator
				.pixelShouldBeHighlighted(pixelR, pixelG, pixelB)) {
			changePixelToGreyScale(filtered, w, h, pixelR, pixelG, pixelB);
		}
	}

	private void changePixelToGreyScale(Bitmap filtered, int w, int h,
			int pixelR, int pixelG, int pixelB) {
		int grayValue = calculateGrayValue(pixelR, pixelG, pixelB);
		filtered.setPixel(w, h,
				Color.rgb(grayValue, grayValue, grayValue));
	}

	private int calculateGrayValue(int red, int green, int blue) {
		// Calculate using the Luma formula
		return (int) (LUMA_RED_COEFFICENT * red + 
					  LUMA_GREEN_COEFFICENT * green +
				  	  LUMA_BLUE_COEFFICENT * blue);
	}
}
