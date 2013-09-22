package br.usp.ime.tcc.activities.filter.bitmap;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.filter.BitmapFilter;
import br.usp.ime.tcc.utils.BitmapLoader;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.FileSaver;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class BitmapFilterActivity extends SherlockActivity {
	protected ImageViewTouch filteredImage;
	protected Bitmap originalBitmap;
	protected Bitmap filteredBitmap;
	protected BitmapFilter filter;
	protected ComponentUtils componentUtils;
	protected String imagePath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmap_filter);

		getExtras();
		
		loadFilter();

		loadActivityLabel();

		loadComponents();

		applyFilter();
	}

	protected abstract void loadFilter();

	protected abstract void loadComponents();
	
	protected abstract void loadActivityLabel();

	@Override
	public void onBackPressed() {
		finish();
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			imagePath = (String) extras.get(Constants.IMAGE_PATH);
			BitmapLoader bitmapLoader = new BitmapLoader(imagePath);
			originalBitmap = bitmapLoader.getBitmap();
		}
	}

	protected void applyFilter() {
		filteredBitmap = filter.applyTo(originalBitmap);
		componentUtils.fillIn(filteredImage, filteredBitmap);
	}

	private void addToGallery(File file) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void saveFilteredImage() {
		FileSaver fileSaver = new FileSaver();

		try {
			File file = fileSaver.saveToFile(filteredBitmap);
			addToGallery(file);
			Toast.makeText(BitmapFilterActivity.this,
					getString(R.string.file_saved), Toast.LENGTH_SHORT).show();
			finish();
		} catch (IOException e) {
			Toast.makeText(BitmapFilterActivity.this,
					getString(R.string.try_again), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE,
				getString(R.string.discard)).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int option = item.getItemId();

		if (option == Constants.SAVE) {
			saveFilteredImage();
			return true;
		} else if (option == Constants.DISCARD) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}