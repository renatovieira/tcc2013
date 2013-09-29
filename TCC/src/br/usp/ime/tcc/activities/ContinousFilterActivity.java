package br.usp.ime.tcc.activities;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.visocor.VisocorContinousFilter;
import br.usp.ime.tcc.utils.Constants;

import com.actionbarsherlock.app.SherlockActivity;

public class ContinousFilterActivity extends SherlockActivity implements
		CvCameraViewListener2 {
	private CameraBridgeViewBase mOpenCvCameraView;
	private SeekBar seekBar;
	private int lastIntensity;
	private VisocorContinousFilter filter;

	public ContinousFilterActivity() {
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_NoActionBar);
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.continous_filter);

		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.continous_filter_camera);
		mOpenCvCameraView.setCvCameraViewListener(this);

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
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				mOpenCvCameraView.enableView();
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	public void onCameraViewStarted(int width, int height) {
		filter = new VisocorContinousFilter(lastIntensity);
	}

	public void onCameraViewStopped() {
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		if (lastIntensity != seekBar.getProgress()) {
			int currentIntensity = seekBar.getProgress();
			filter.update(seekBar.getProgress());
			lastIntensity = currentIntensity;
		}
		return filter.applyFilterTo(inputFrame);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
