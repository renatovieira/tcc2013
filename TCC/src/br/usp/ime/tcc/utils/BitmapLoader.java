package br.usp.ime.tcc.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapLoader {
	private String imagePath;

	public BitmapLoader(String imagePath) {
		this.imagePath = imagePath;
	}

	private int getImageOrientation() {
		int orientation = 0;

		if (imagePath != null) {
			try {
				ExifInterface exif = new ExifInterface(imagePath);
				orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orientation;
	}

	private Matrix getRotationMatrix() {
		int orientation = getImageOrientation();

		Matrix rotationMatrix = new Matrix();

		switch (orientation) {
		case 3:
			rotationMatrix.postRotate(180);
			break;
		case 6:
			rotationMatrix.postRotate(90);
			break;
		case 8:
			rotationMatrix.preRotate(90);
			break;
		}

		return rotationMatrix;

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
		
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				getRotationMatrix(), true);
	}
}
