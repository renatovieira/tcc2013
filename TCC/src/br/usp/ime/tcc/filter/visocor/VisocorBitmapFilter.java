package br.usp.ime.tcc.filter.visocor;

import br.usp.ime.tcc.filter.BitmapFilter;

public class VisocorBitmapFilter extends BitmapFilter {
	private VisocorParametersCalculator calc;

	public VisocorBitmapFilter(float intensity) {
		initFilterVector();
		calc = new VisocorParametersCalculator(intensity);
		updateFilter(calc.getAlpha(), calc.getBeta(), calc.getGama());
	}

	public VisocorBitmapFilter(int progress) {
		initFilterVector();
		update(progress);
	}
	
	@Override
	public void update (int progress) {
		calc = new VisocorParametersCalculator(progress);
		updateFilter(calc.getAlpha(), calc.getBeta(), calc.getGama());
	}
	
	private void initFilterVector() {
		filterVector = new float[] { 
			1f, 0f, 0f, 0f, 0f, // Red
			0f, 1f, 0f, 0f, 0f, // Green
			0f, 0f, 1f, 0f, 0f, // Blue
			0f, 0f, 0f, 1f, 0f, // Alpha
		};
	}

	private void updateFilter(float alpha, float beta, float gama) {
		filterVector[10] = alpha;
		filterVector[11] = beta;
		filterVector[12] = gama;
		loadMatrixFromVector();
	}
}
