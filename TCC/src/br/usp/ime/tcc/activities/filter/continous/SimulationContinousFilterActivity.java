package br.usp.ime.tcc.activities.filter.continous;

import android.os.Bundle;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.filter.simulation.SimulationContinousFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationContinousFilterActivity extends ContinousFilterActivity {
	private int filterType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadFilterType();
	}

	private void loadFilterType() {
		SettingsManager settingsManager = new SettingsManager(this);
		
		filterType = settingsManager.loadDefaultColorSimulationId();
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			filterType = extras.getInt(Constants.FILTER_TYPE);
		}
	}
	
	@Override
	public void onCameraViewStarted(int width, int height) {
		filter = new SimulationContinousFilter(filterType);
	}
}
