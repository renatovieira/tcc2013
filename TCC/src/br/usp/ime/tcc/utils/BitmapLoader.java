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

		try {
			ExifInterface exif = new ExifInterface(imagePath);
			orientation = exif
					.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private boolean photoIsBiggerThanRequired(int reqWidth, int reqHeight,
			final int height, final int width) {
		return height > reqHeight || width > reqWidth;
	}

	private int calculateInSampleSize(Options options) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (photoIsBiggerThanRequired(Constants.REQUIRED_DIMENSION,
				Constants.REQUIRED_DIMENSION, height, width)) {
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

		options.inSampleSize = calculateInSampleSize(options);
		options.inJustDecodeBounds = false;

		Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
		Matrix rotationMatrix = getRotationMatrix();

		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
				rotationMatrix, true);
	}
}
