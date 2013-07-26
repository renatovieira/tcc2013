package br.usp.ime.tcc.filter.visocor;

import br.usp.ime.tcc.filter.ContinousFilter;


public class VisocorContinousFilter extends ContinousFilter {
	private VisocorParametersCalculator calc;
	
	public VisocorContinousFilter(float intensity) {
		super();
		calc = new VisocorParametersCalculator(intensity);
		updateFilterMatrix();
	}
	
	public VisocorContinousFilter(int progress) {
		super();
		update(progress);
	}
	
	private void updateFilterMatrix() {
		mFilterKernel.put(2, 0, calc.getAlpha(), calc.getBeta(), calc.getGama(), 0f);
	}

	@Override
	public void update(int progress) {
		calc = new VisocorParametersCalculator(progress);
		updateFilterMatrix();
	}
}
