package br.usp.ime.tcc.filter;


public class VisocorContinousFilter extends ContinousFilter {
	private VisocorParametersCalculator calc;
	
	public VisocorContinousFilter(float intensity) {
		calc = new VisocorParametersCalculator(intensity);
		updateFilterMatrix();
	}
	
	public VisocorContinousFilter(int progress) {
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
