package br.usp.ime.tcc.filter.simulation;

import br.usp.ime.tcc.filter.ContinousFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationContinousFilter extends ContinousFilter {
	public SimulationContinousFilter(int simulationType) {
		super();
		createFilterMatrix(simulationType);
	}
	
	private void createFilterMatrix(int simulationType) {
		switch (simulationType) {
		case Constants.DEUTERANOPIA_SIMULATION_FILTER:
			loadFilterDeut();
			break;
		case Constants.PROTANOPIA_SIMULATION_FILTER:
			loadFilterProt();
			break;
		default:
			break;
		}
	}

	private void loadFilterDeut() {
		mFilterKernel.put(0, 0, 0.29275, 0.70725, 0f, 0f);
		mFilterKernel.put(1, 0, 0.29275, 0.70725, 0f, 0f);
		mFilterKernel.put(2, 0, -0.02234, 0.02234, 1f, 0f);
		mFilterKernel.put(3, 0, 0f, 0f, 0f, 1f);		
	}
	
	private void loadFilterProt() {
		mFilterKernel.put(0, 0, 0.11238, 0.88761, 0f, 0f);
		mFilterKernel.put(1, 0, 0.11238, 0.88761, 0f, 0f);
		mFilterKernel.put(2, 0, 0.004, -0.004, 1f, 0f);
		mFilterKernel.put(3, 0, 0f, 0f, 0f, 1f);	
	}
}
