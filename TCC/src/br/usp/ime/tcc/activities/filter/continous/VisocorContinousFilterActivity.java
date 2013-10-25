package br.usp.ime.tcc.activities.filter.continous;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Mat;

import android.os.Bundle;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.visocor.VisocorContinousFilter;
import br.usp.ime.tcc.utils.Constants;

public class VisocorContinousFilterActivity extends ContinousFilterActivity {
	private SeekBar seekBar;
	private int lastIntensity;

	public VisocorContinousFilterActivity() {
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadComponents();
	}

	private void loadComponents() {
		SettingsManager settingsManager = new SettingsManager(this);
		
		ComponentUtils cu = new ComponentUtils(this);

		seekBar = cu.loadSeekBar(R.id.continous_filter_intensity_bar,
				Constants.MAX_INTENSITY, settingsManager.loadDefaultIntensity());
		lastIntensity = settingsManager.loadDefaultIntensity();
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		filter = new VisocorContinousFilter(lastIntensity);
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		if (lastIntensity != seekBar.getProgress()) {
			int currentIntensity = seekBar.getProgress();
			filter.update(seekBar.getProgress());
			lastIntensity = currentIntensity;
		}
		return super.onCameraFrame(inputFrame);
	}
}
