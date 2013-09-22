package br.usp.ime.tcc.activities.filter.bitmap;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.colorpicker.ImageColorPickerActivity;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.BitmapFilter;
import br.usp.ime.tcc.filter.colorhighlight.ColorHighlightBitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class ColorHighlightBitmapFilterActivity extends BitmapFilterActivity {
	private int red, green, blue, redTolerance, greenTolerance, blueTolerance;

	@Override
	protected void loadFilter() {
		filter = (BitmapFilter) getLastNonConfigurationInstance();

		if (filter == null) {
			loadSettings();

			filter = new ColorHighlightBitmapFilter(red, green, blue,
					redTolerance, greenTolerance, blueTolerance);
		}
	}

	@Override
	protected void loadActivityLabel() {
		setTitle(R.string.color_highlight_filter);
	}

	private void loadSettings() {
		SettingsManager settingsManager = new SettingsManager(this);
		red = settingsManager.loadDefaultRed();
		green = settingsManager.loadDefaultGreen();
		blue = settingsManager.loadDefaultBlue();
		redTolerance = settingsManager.loadDefaultRedTolerance();
		greenTolerance = settingsManager.loadDefaultGreenTolerance();
		blueTolerance = settingsManager.loadDefaultBlueTolerance();
	}

	@Override
	protected void loadComponents() {
		componentUtils = new ComponentUtils(this);

		filteredImage = (ImageViewTouch) findViewById(R.id.filtered_image);

		componentUtils.loadButton(R.id.color_picker_button,
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent i = new Intent(getBaseContext(),
								ImageColorPickerActivity.class);
						i.putExtra(Constants.RED, red);
						i.putExtra(Constants.GREEN, green);
						i.putExtra(Constants.BLUE, blue);
						i.putExtra(Constants.IMAGE_PATH, imagePath);
						startActivityForResult(i,
								Constants.COLOR_PICKER_REQUEST_CODE);
					}
				});
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
}