package br.usp.ime.tcc.filter;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ContinousFilter {
	protected Mat mFilterKernel;

	public ContinousFilter() {
		initializeMatrixToIdentity();
	}

	private void initializeMatrixToIdentity() {
		mFilterKernel = new Mat(4, 4, CvType.CV_32F);
		mFilterKernel.put(0, 0, 1f, 0f, 0f, 0f);
		mFilterKernel.put(1, 0, 0f, 1f, 0f, 0f);
		mFilterKernel.put(2, 0, 0f, 0f, 1f, 0f);
		mFilterKernel.put(3, 0, 0f, 0f, 0f, 1f);
	}
	
	public Mat applyFilterTo(CvCameraViewFrame inputFrame) {
		Mat mRgba = null;
		if (inputFrame != null) {
			mRgba = inputFrame.rgba();
			Core.transform(mRgba, mRgba, mFilterKernel);
		}
		return mRgba;
	}

	public void update(int value) {
	}
}
