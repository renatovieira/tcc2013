package br.usp.ime.tcc.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BitmapFilter {
	private ColorMatrixColorFilter colorMatrixColorFilter;
	
	private Bitmap filterBitmap(Bitmap bmp) {
		Bitmap filtered = bmp.copy(Bitmap.Config.ARGB_8888, true);

		Canvas c = new Canvas(filtered);
		Paint paint = new Paint();
		paint.setColorFilter(colorMatrixColorFilter);
		c.drawBitmap(filtered, 0, 0, paint);
		return filtered;
	}
	
	public Bitmap applyTo(Bitmap bmp) {
		if (bmp == null)
			return null;

		Bitmap filtered = filterBitmap(bmp);

		return filtered;
	}
	
	protected void loadMatrixFromVector(float [] filterVector) {
		colorMatrixColorFilter = new ColorMatrixColorFilter(filterVector);
	}
	
	public void update(int value) {
	}
}
