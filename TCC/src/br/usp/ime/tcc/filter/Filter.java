package br.usp.ime.tcc.filter;

import br.usp.ime.tcc.utils.Constants;

public class Filter {
	protected float calculateIntensity(int progress) {
		return ((2.0f * progress / Constants.MAX_INTENSITY)) - 1.0f;
	}

	protected float calculateAlpha(float intensity) {
		return intensity >= 0 ? 0 : -intensity;
	}

	protected float calculateBeta(float intensity) {
		return calculateAlpha(-intensity);
	}
	
	protected float calculateGama(float alpha, float beta) {
		return 1 - (alpha + beta);
	}
}
