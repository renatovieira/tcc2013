package br.usp.ime.tcc.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BitmapFilter extends Filter {
	private ColorMatrixColorFilter colorMatrixColorFilter;

	public BitmapFilter(float intensity) {
		super(intensity);
	}

	public BitmapFilter(int progress) {
		super(progress);
	}

	public Bitmap applyTo(Bitmap bmp) {
		if (bmp == null)
			return null;

		Bitmap filtered = filterBitmap(bmp);

		return filtered;
	}

	private Bitmap filterBitmap(Bitmap bmp) {
		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);

		Canvas c = new Canvas(filtered);
		Paint paint = new Paint();
		paint.setColorFilter(colorMatrixColorFilter);
		c.drawBitmap(filtered, 0, 0, paint);
		return filtered;
	}

	@Override
	protected void loadMatrix() {
		float[] filterMatrix = { 1, 0, 0, 0, 0, // Red
				0, 1, 0, 0, 0, // Green
				alpha, beta, gama, 0, 0, // Blue
				0, 0, 0, 1, 0, // Alpha
		};

		colorMatrixColorFilter = new ColorMatrixColorFilter(filterMatrix);
	}
}
