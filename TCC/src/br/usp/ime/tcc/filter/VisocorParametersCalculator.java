package br.usp.ime.tcc.filter;

import br.usp.ime.tcc.utils.Constants;

public class VisocorParametersCalculator {
	private float alpha;
	private float beta;
	private float gama;
	
	public VisocorParametersCalculator (float intensity) {
		setParametersFromIntensity(intensity);
	}
	
	public VisocorParametersCalculator (int progress) {
		setParametersFromIntensity(calculateIntensity(progress));
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
	
	private void setParametersFromIntensity (float intensity) {
		alpha = calculateAlpha(intensity);
		beta = calculateBeta(intensity);
		gama = calculateGama(alpha, beta);
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public float getBeta() {
		return beta;
	}
	
	public float getGama() {
		return gama;
	}
}