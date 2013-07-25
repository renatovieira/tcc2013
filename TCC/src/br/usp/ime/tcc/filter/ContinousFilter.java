package br.usp.ime.tcc.filter;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ContinousFilter extends Filter {
	private Mat mFilterKernel = new Mat(4, 4, CvType.CV_32F);

	private void setFilterMatrix(float intensity) {
		float alpha = calculateAlpha(intensity);
		float beta = calculateBeta(intensity);
		float gama = calculateGama(alpha, beta);

		loadKernel(alpha, beta, gama);
	}
	
	private void loadKernel(float alpha, float beta, float gama) {
		mFilterKernel.put(0, 0, /* R */1f, 0f, 0f, 0f);
		mFilterKernel.put(1, 0, /* G */0f, 1f, 0f, 0f);
		mFilterKernel.put(2, 0, /* B */alpha, beta, gama, 0f);
		mFilterKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
	}
	
	public void reloadMatrix(int progress) {
		setFilterMatrix(calculateIntensity(progress));
	}

	public ContinousFilter(float intensity) {
		setFilterMatrix(intensity);
	}

	public ContinousFilter(int progress) {
		float intensity = calculateIntensity(progress);
		setFilterMatrix(intensity);
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
