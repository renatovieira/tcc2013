package br.usp.ime.tcc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore;

public final class Utils {
	private static boolean photoIsBiggerThanRequired(int reqWidth,
			int reqHeight, final int height, final int width) {
		return height > reqHeight || width > reqWidth;
	}

	private static int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (photoIsBiggerThanRequired(reqWidth, reqHeight, height, width)) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = (heightRatio < widthRatio) ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	private static Cursor queryFor(Uri photoUri, ContentResolver cr, String[] value) {
		Cursor cursor = cr.query(photoUri, value, null, null,
				null);
		cursor.moveToFirst();
		
		return cursor;
	}

	private static String queryForPicturePath(Uri photoUri,
			ContentResolver cr) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		
		Cursor cursor = queryFor(photoUri, cr, filePathColumn);

		String picturePath = cursor.getString(0);
		cursor.close();
		return picturePath;
	}
	
	// Public Methods

	public static String getSelectedPicturePath(Uri selectedImageUri,
			ContentResolver cr) {
		String picturePath = null;

		if (selectedImageUri != null) 
			picturePath = queryForPicturePath(selectedImageUri, cr);
		return picturePath;
	}

	public static String getTimeStamp() {
		String timeStamp = new SimpleDateFormat(Constants.DATE_FORMAT,
				Locale.getDefault()).format(new Date());
		return timeStamp;
	}

	public static Bitmap getScaledBitmapFromImagePath(String imagePath,
			int imageOrientation) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		options.inSampleSize = calculateInSampleSize(options,
				Constants.REQDIMENSION, Constants.REQDIMENSION);
		options.inJustDecodeBounds = false;

		Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

		return bmp;
	}
}
