package br.usp.ime.tcc.filter;

import br.usp.ime.tcc.utils.Constants;

public abstract class Filter {
	protected float alpha;
	protected float beta;
	protected float gama;
	
	public Filter(int progress) {
		loadFilterMatrixFromProgress(progress);
	}
	
	public Filter(float intensity) {
		loadMatrixFromIntensity(intensity);
	}
	
	private float calculateIntensity(int progress) {
		return ((2.0f * progress / Constants.MAX_INTENSITY)) - 1.0f;
	}

	private float calculateAlpha(float intensity) {
		return intensity >= 0 ? 0 : -intensity;
	}

	private float calculateBeta(float intensity) {
		return calculateAlpha(-intensity);
	}
	
	private float calculateGama(float alpha, float beta) {
		return 1 - (alpha + beta);
	}
	
	private void calculateValuesFromIntensity (float intensity) {
		alpha = calculateAlpha(intensity);
		beta = calculateBeta(intensity);
		gama = calculateGama(alpha, beta);
	}

	private void loadFilterMatrixFromProgress (int progress) {
		float intensity = calculateIntensity(progress);
		loadMatrixFromIntensity(intensity);
	}

	private void loadMatrixFromIntensity (float intensity) {
		calculateValuesFromIntensity(intensity);
		loadMatrix();
	}
	
	public void reloadMatrix(int progress) {
		loadFilterMatrixFromProgress(progress);
	}
	
	protected abstract void loadMatrix();
}
