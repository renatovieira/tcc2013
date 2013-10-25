package br.usp.ime.tcc.activities.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.ContinousFilterActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.filter.bitmap.SimulationBitmapFilterActivity;
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

		componentUtils.setSpinnerPosition(R.id.filter_type_spinner,
				settingsManager.loadDefaultColorSimulationId());

		componentUtils.showLinearLayout(R.id.filter_type_spinner_ll);

		componentUtils.loadImageButton(R.id.liveModeButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent continousModeIntent = new Intent(
						SimulationFilterActivity.this,
						ContinousFilterActivity.class);
				startActivity(continousModeIntent);
			}
		});

		componentUtils.showLinearLayout(R.id.liveModeLL);
	}

	protected void putFilterTypeExtra(Intent intent) {
		int filterType;
		ComponentUtils cu = new ComponentUtils(this);
		filterType = cu.getSpinnerPosition(R.id.filter_type_spinner);
		intent.putExtra(Constants.FILTER_TYPE, filterType);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Intent showImageIntent = new Intent(this,
					SimulationBitmapFilterActivity.class);
			putContentOnNextActivityExtras(data, showImageIntent);
			putFilterTypeExtra(showImageIntent);
			startActivity(showImageIntent);
		}
	}
}
