package br.usp.ime.tcc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import br.usp.ime.tcc.activities.R;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public final class Utils {
	private static Cursor queryFor(Uri photoUri, ContentResolver cr,
			String[] value) {
		Cursor cursor = cr.query(photoUri, value, null, null, null);
		cursor.moveToFirst();

		return cursor;
	}

	private static String queryForPicturePath(Uri photoUri, ContentResolver cr) {
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
	
	public static void loadStripedActionBar(SherlockActivity act) {
		ActionBar supportActionBar = act.getSupportActionBar();
		
		if (supportActionBar != null) {
			BitmapDrawable bg = (BitmapDrawable)act.getResources().getDrawable(R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			supportActionBar.setBackgroundDrawable(bg);
		}
	}
}
