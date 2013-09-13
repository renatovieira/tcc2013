package br.usp.ime.tcc.activities;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.BitmapFilter;
import br.usp.ime.tcc.filter.colorhighlight.ColorHighlightBitmapFilter;
import br.usp.ime.tcc.filter.simulation.SimulationBitmapFilter;
import br.usp.ime.tcc.filter.visocor.VisocorBitmapFilter;
import br.usp.ime.tcc.utils.BitmapLoader;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.FileSaver;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class BitmapFilterActivity extends SherlockActivity {
	private ImageView filteredImage;
	private Bitmap originalBitmap;
	private Bitmap filteredBitmap;
	private BitmapFilter filter;
	private int filterType;
	private SettingsManager settingsManager;
	private int red, green, blue, redTolerance, greenTolerance, blueTolerance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmap_filter);

		getExtras();

		loadSettings();

		loadComponents();

		// TODO Fix this when separating activities, only uses for Color
		// Highlight
		if (getLastNonConfigurationInstance() != null)
			filter = (BitmapFilter) getLastNonConfigurationInstance();
		else
			loadFilter(filterType);
		applyFilter();
	}

	private void loadSettings() {
		settingsManager = new SettingsManager(this);
		red = settingsManager.loadDefaultRed();
		green = settingsManager.loadDefaultGreen();
		blue = settingsManager.loadDefaultBlue();
		redTolerance = settingsManager.loadDefaultRedTolerance();
		greenTolerance = settingsManager.loadDefaultGreenTolerance();
		blueTolerance = settingsManager.loadDefaultBlueTolerance();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String imagePath = (String) extras.get(Constants.IMAGE_PATH);
			BitmapLoader bitmapLoader = new BitmapLoader(imagePath);
			originalBitmap = bitmapLoader.getBitmap();

			filterType = (Integer) extras.get(Constants.FILTER_TYPE);
		}
	}

	private void applyFilter() {
		filteredBitmap = filter.applyTo(originalBitmap);
		new ComponentUtils(this).fillIn(filteredImage, filteredBitmap);
	}

	private void loadFilter(int filterType) {
		switch (filterType) {
		case Constants.VISOCOR_FILTER:
			filter = new VisocorBitmapFilter(
					settingsManager.loadDefaultIntensity());
			break;
		case Constants.COLOR_HIGHLIGHT_FILTER:
			filter = new ColorHighlightBitmapFilter(red, green, blue,
					redTolerance, greenTolerance, blueTolerance);
			break;
		default:
			filter = new SimulationBitmapFilter(filterType);
			break;
		}
	}

	private void loadComponents() {
		final ComponentUtils componentUtils = new ComponentUtils(this);

		filteredImage = componentUtils.loadImageView(R.id.filtered_image);

		if (filterType == Constants.VISOCOR_FILTER) {
			componentUtils.loadSeekBar(R.id.intensity_bar,
					Constants.MAX_INTENSITY, Constants.INTENSITY,
					new OnSeekBarChangeListener() {
						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							filter.update(progress);
							filteredBitmap = filter.applyTo(originalBitmap);

							componentUtils
									.fillIn(filteredImage, filteredBitmap);
						}
					});
		}

		else if (filterType == Constants.COLOR_HIGHLIGHT_FILTER) {
			componentUtils.loadButton(R.id.color_picker_button,
					new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Intent i = new Intent(getBaseContext(),
									ColorPickerActivity.class);
							i.putExtra(Constants.RED, red);
							i.putExtra(Constants.GREEN, green);
							i.putExtra(Constants.BLUE, blue);
							startActivityForResult(i,
									Constants.COLOR_PICKER_REQUEST_CODE);
						}
					});
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == Constants.COLOR_PICKER_REQUEST_CODE) {
				red = data.getIntExtra(Constants.RED, red);
				green = data.getIntExtra(Constants.GREEN, green);
				blue = data.getIntExtra(Constants.BLUE, blue);

				filter = new ColorHighlightBitmapFilter(red, green, blue,
						redTolerance, greenTolerance, blueTolerance);
				applyFilter();
			}
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return filter;
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
					getString(R.string.file_saved), Toast.LENGTH_SHORT)
					.show();
			finish();
		} catch (IOException e) {
			Toast.makeText(BitmapFilterActivity.this,
					getString(R.string.try_again), Toast.LENGTH_LONG)
					.show();
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE, getString(R.string.discard))
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int option = item.getItemId();
		
		if (option == Constants.SAVE) {
			saveFilteredImage();
			return true;
		}
		else if (option == Constants.DISCARD) {
			finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}