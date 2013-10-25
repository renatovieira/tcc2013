package br.usp.ime.tcc.activities.filter.continous;

import br.usp.ime.tcc.filter.simulation.SimulationContinousFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationContinousFilterActivity extends ContinousFilterActivity {
	public SimulationContinousFilterActivity() {
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		filter = new SimulationContinousFilter(Constants.DEUTERANOPIA_SIMULATION_FILTER);
	}
}
