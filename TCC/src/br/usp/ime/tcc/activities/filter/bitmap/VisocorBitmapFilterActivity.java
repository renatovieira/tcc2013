package br.usp.ime.tcc.activities.filter.bitmap;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import android.graphics.Matrix;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.visocor.VisocorBitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class VisocorBitmapFilterActivity extends BitmapFilterActivity {
	@Override
	protected void loadActivityLabel() {
		setTitle(R.string.visocor_filter);
	}

	@Override
	protected void loadFilter() {
		SettingsManager settingsManager = new SettingsManager(this);
		filter = new VisocorBitmapFilter(settingsManager.loadDefaultIntensity());
	}

	@Override
	protected void loadComponents() {
		filteredImage = (ImageViewTouch) findViewById(R.id.filtered_image);

		componentUtils = new ComponentUtils(this);

		componentUtils.loadSeekBar(R.id.intensity_bar, Constants.MAX_INTENSITY,
				Constants.INTENSITY, new OnSeekBarChangeListener() {
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
						reapplyFilter();
					}
				});
	}

	protected void reapplyFilter() {
		Matrix imageMatrix = filteredImage.getDisplayMatrix();
		
		filteredBitmap = filter.applyTo(originalBitmap);
		
		filteredImage.setImageBitmap(filteredBitmap, imageMatrix.isIdentity() ? null
				: imageMatrix, ImageViewTouchBase.ZOOM_INVALID,
				ImageViewTouchBase.ZOOM_INVALID);
	}
}