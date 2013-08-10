package br.usp.ime.tcc.filter.visocor;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class VisocorContinousFilter  {
	private VisocorParametersCalculator calc;
	private Mat mFilterKernel;

	public VisocorContinousFilter(float intensity) {
		initializeMatrixToIdentity();
		calc = new VisocorParametersCalculator(intensity);
		updateFilterMatrix();
	}

	public VisocorContinousFilter(int progress) {
		initializeMatrixToIdentity();
		update(progress);
	}

	private void updateFilterMatrix() {
		mFilterKernel.put(2, 0, calc.getAlpha(), calc.getBeta(),
				calc.getGama(), 0f);
	}

	public void update(int progress) {
		calc = new VisocorParametersCalculator(progress);
		updateFilterMatrix();
	}

	private void initializeMatrixToIdentity() {
		mFilterKernel = Mat.eye(4, 4, CvType.CV_32F);
	}

	public Mat applyFilterTo(CvCameraViewFrame inputFrame) {
		Mat mRgba = null;
		if (inputFrame != null) {
			mRgba = inputFrame.rgba();
			Core.transform(mRgba, mRgba, mFilterKernel);
		}
		return mRgba;
	}
}
