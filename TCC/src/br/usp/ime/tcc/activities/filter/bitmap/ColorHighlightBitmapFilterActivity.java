package br.usp.ime.tcc.activities.filter.bitmap;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.colorpicker.ImageColorPickerActivity;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.colorhighlight.ColorHighlightBitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class ColorHighlightBitmapFilterActivity extends BitmapFilterActivity {
	private int redTolerance, greenTolerance, blueTolerance;
	private int rgb[];

	@Override
	protected void loadFilter() {
		loadSettings();

		filter = new ColorHighlightBitmapFilter(rgb[Constants.RED],
				rgb[Constants.GREEN], rgb[Constants.BLUE], redTolerance,
				greenTolerance, blueTolerance);
	}

	@Override
	protected void loadActivityLabel() {
		setTitle(R.string.color_highlight_filter);
	}

	private void loadSettings() {
		SettingsManager settingsManager = new SettingsManager(this);

		rgb = (int[]) getLastNonConfigurationInstance();

		if (rgb == null)
			rgb = new int[] { settingsManager.loadDefaultRed(),
					settingsManager.loadDefaultGreen(),
					settingsManager.loadDefaultBlue() };

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
						i.putExtra(Constants.RED_STR, rgb[Constants.RED]);
						i.putExtra(Constants.GREEN_STR, rgb[Constants.GREEN]);
						i.putExtra(Constants.BLUE_STR, rgb[Constants.BLUE]);
						i.putExtra(Constants.IMAGE_PATH, imagePath);
						startActivityForResult(i,
								Constants.COLOR_PICKER_REQUEST_CODE);
					}
				});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == Constants.COLOR_PICKER_REQUEST_CODE) {
				rgb[Constants.RED] = data.getIntExtra(Constants.RED_STR,
						rgb[Constants.RED]);
				rgb[Constants.GREEN] = data.getIntExtra(Constants.GREEN_STR,
						rgb[Constants.GREEN]);
				rgb[Constants.BLUE] = data.getIntExtra(Constants.BLUE_STR,
						rgb[Constants.BLUE]);

				filter = new ColorHighlightBitmapFilter(rgb[Constants.RED],
						rgb[Constants.GREEN], rgb[Constants.BLUE],
						redTolerance, greenTolerance, blueTolerance);
				applyFilter();
			}
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return rgb;
	}
}