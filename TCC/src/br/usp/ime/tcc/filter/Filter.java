package br.usp.ime.tcc.filter;

import br.usp.ime.tcc.utils.Constants;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Filter {
	private ColorMatrixColorFilter colorMatrixColorFilter;
	
	public Filter(float intensity) {
		generateMatrixFromIntensity(intensity);
	}
	
	public Filter(int progress) {
		float intensity = calculateIntensity(progress);
		generateMatrixFromIntensity(intensity);
	}
	
	private void generateMatrixFromIntensity(float intensity) {
		float alpha = calculaAlpha(intensity);
		float beta = calculaBeta(intensity);
		float gama = 1 - (alpha + beta);
		
		float[] filterMatrix = {
			    1, 0, 0, 0, 0, //Red
			    0, 1, 0, 0, 0, //Green
			    alpha, beta, gama, 0, 0, //Blue
			    0, 0, 0, 1, 0, //Alpha
		};
		
		colorMatrixColorFilter = new ColorMatrixColorFilter(filterMatrix);
	}
	
	private float calculateIntensity(int progress) {
		return ((2.0f * progress / Constants.MAX_INTENSITY)) - 1.0f;
	}

	private float calculaAlpha(float intensity) {
		return intensity >= 0 ? 0 : -intensity;
	}

	private float calculaBeta(float intensity) {
		return calculaAlpha(-intensity);
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
}
