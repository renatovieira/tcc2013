package br.usp.ime.tcc.activities.filter;

import android.content.Intent;
import android.os.Bundle;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.utils.Constants;

public class SimulationFilterActivity extends FilterActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void loadSpecificComponents() {
		SettingsManager settingsManager = new SettingsManager(this);

		ComponentUtils componentUtils = new ComponentUtils(this);
		componentUtils.hideButton(R.id.liveModeButton);
		componentUtils.loadTextViewWithText(R.id.filter_title,
				getString(R.string.simulation_filter));
		componentUtils.showSpinner(R.id.filter_type_spinner);

		componentUtils.setSpinnerPosition(R.id.filter_type_spinner,
				settingsManager.loadDefaultColorSimulationId());
	}

	@Override
	protected void putFilterTypeExtra(Intent intent) {
		int filterType;
		ComponentUtils cu = new ComponentUtils(this);
		filterType = cu.getSpinnerPosition(R.id.filter_type_spinner) + 2;
		intent.putExtra(Constants.FILTER_TYPE, filterType);
	}
}
