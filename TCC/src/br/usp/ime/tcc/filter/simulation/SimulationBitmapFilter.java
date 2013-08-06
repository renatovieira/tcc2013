package br.usp.ime.tcc.filter.simulation;

import br.usp.ime.tcc.filter.BitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationBitmapFilter extends BitmapFilter {
	
	public SimulationBitmapFilter(int simulationType) {
		initFilterVector(simulationType);
		loadMatrixFromVector();
	}

	private void initFilterVector(int simulationType) {
		switch (simulationType) {
		case Constants.DEUTERANOPIA_SIMULATION_FILTER:
			loadFilterDeut();
			break;
		case Constants.PROTANOPIA_SIMULATION_FILTER:
			loadFilterProt();
			break;
		case Constants.TRITANOPIA_SIMULATION_FILTER:
			loadFilterTrit();
		default:
			break;
		}
	}

	private void loadFilterDeut() {
		filterVector = new float[] { 
				0.29275f, 0.70725f, 0f, 0f, 0f, // Red
				0.29275f, 0.70725f, 0f, 0f, 0f, // Green
				-0.02234f, 0.02234f, 1f, 0, 0, // Blue
				0f, 0f, 0f, 1f, 0f, // Alpha
		};
	}

	private void loadFilterProt() {
		filterVector = new float[] { 
				0.11238f, 0.88761f, 0f, 0f, 0f, // Red
				0.11238f, 0.88761f, 0f, 0f, 0f, // Green
				0.004f, -0.004f, 1f, 0, 0, // Blue
				0f, 0f, 0f, 1f, 0f, // Alpha
		};
	}
	
	private void loadFilterTrit() {
		filterVector = new float[] { 
				0.49326f, 0.50675f, 0f, 0f, 0f, // Red
				0.49326f, 0.50675f, 0f, 0f, 0f, // Green
				-3.0109f, -3.0109f, 1f, 0, 0, // Blue
				0f, 0f, 0f, 1f, 0f, // Alpha
		};
	}
}
