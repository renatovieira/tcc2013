package br.usp.ime.tcc.activities;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.filter.ContinousFilter;
import br.usp.ime.tcc.filter.simulation.SimulationContinousFilter;
import br.usp.ime.tcc.filter.visocor.VisocorContinousFilter;
import br.usp.ime.tcc.utils.Constants;

public class ContinousFilterActivity extends Activity implements
		CvCameraViewListener2 {
	private CameraBridgeViewBase mOpenCvCameraView;
	private SeekBar seekBar;
	private int lastIntensity;
	private ContinousFilter filter;
	private int filterType;

	public ContinousFilterActivity() {
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.continous_filter);

		filterType = 0; //TODO Get right filter type

		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.continous_filter_camera);
		mOpenCvCameraView.setCvCameraViewListener(this);

		ComponentUtils cu = new ComponentUtils(this);

		if (filterType == Constants.VISOCOR_FILTER)
			seekBar = cu.loadSeekBar(R.id.continous_filter_intensity_bar,
					Constants.MAX_INTENSITY, Constants.PROGRESS);
		else
			cu.hideSeekBar(R.id.continous_filter_intensity_bar);
		lastIntensity = Constants.PROGRESS;
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
		if (filterType == Constants.VISOCOR_FILTER)
			filter = new VisocorContinousFilter(lastIntensity);
		else
			filter = new SimulationContinousFilter(filterType);
	}

	public void onCameraViewStopped() {
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		if (filterType == Constants.VISOCOR_FILTER
				&& lastIntensity != seekBar.getProgress()) {
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
