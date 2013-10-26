package br.usp.ime.tcc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapLoader {
	private String imagePath;

	public BitmapLoader(String imagePath) {
		this.imagePath = imagePath;
	}

	private boolean photoIsBiggerThanRequired(final int height, final int width) {
		return height > Constants.REQUIRED_DIMENSION || width > Constants.REQUIRED_DIMENSION;
	}

	private int calculateInSampleSize(Options options) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (photoIsBiggerThanRequired(height, width)) {
			final int heightRatio = Math.round((float) height
					/ (float) Constants.REQUIRED_DIMENSION);
			final int widthRatio = Math.round((float) width
					/ (float) Constants.REQUIRED_DIMENSION);

			inSampleSize = (heightRatio < widthRatio) ? heightRatio
					: widthRatio;
		}
		return inSampleSize;
	}

	public Bitmap getBitmap() {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		int inSampleSize = calculateInSampleSize(options);
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;

		Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
		
		double width = bmp.getWidth();
		double height = bmp.getHeight();
		
		if (photoIsBiggerThanRequired((int) height, (int) width)) {
			if (width > height) {
				height = (height / width) * Constants.REQUIRED_DIMENSION;
				width = Constants.REQUIRED_DIMENSION;
			} else {
				width = (width / height) * Constants.REQUIRED_DIMENSION;
				height = Constants.REQUIRED_DIMENSION;
			}
			
			bmp = Bitmap.createScaledBitmap(bmp, (int) width, (int) height, true);
		}
		
		return bmp;
	}
}
