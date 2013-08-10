package br.usp.ime.tcc.filter;

import android.graphics.Bitmap;

public abstract class BitmapFilter {
	protected abstract Bitmap filterBitmap(Bitmap bmp);

	public Bitmap applyTo(Bitmap bmp) {
		if (bmp == null)
			return null;

		Bitmap filtered = filterBitmap(bmp);

		return filtered;
	}
	
	public void update(int value) {
	}
}
