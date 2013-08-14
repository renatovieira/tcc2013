package br.usp.ime.tcc.activities.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import br.usp.ime.tcc.activities.ColorPickerActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class SettingsActivity extends Activity {
	private int defaultIntensity;
	private int defaultColorSimulation;
	private int defaultRedTolerance;
	private int defaultGreenTolerance;
	private int defaultBlueTolerance;
	private int defaultRed;
	private int defaultGreen;
	private int defaultBlue;
	private EditText redToleranceET;
	private EditText greenToleranceET;
	private EditText blueToleranceET;
	private SettingsManager settingsManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		settingsManager = new SettingsManager(this);

		loadDefaultValues();

		loadComponents();
	}

	private void loadDefaultValues() {
		defaultIntensity = settingsManager.loadDefaultIntensity();
		defaultColorSimulation = settingsManager.loadDefaultColorSimulationId();
		defaultRedTolerance = settingsManager.loadDefaultRedTolerance();
		defaultGreenTolerance = settingsManager.loadDefaultGreenTolerance();
		defaultBlueTolerance = settingsManager.loadDefaultBlueTolerance();
		defaultRed = settingsManager.loadDefaultRed();
		defaultGreen = settingsManager.loadDefaultGreen();
		defaultBlue = settingsManager.loadDefaultBlue();
	}

	private void loadComponents() {
		ComponentUtils cu = new ComponentUtils(this);

		cu.loadButton(R.id.default_color_picker_button, new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent colorPickerIntent = new Intent(getBaseContext(),
						ColorPickerActivity.class);
				startActivityForResult(colorPickerIntent,
						Constants.COLOR_PICKER_REQUEST_CODE);
			}
		});

		cu.loadSeekBar(R.id.default_intensity, Constants.MAX_INTENSITY,
				defaultIntensity);

		cu.setSpinnerPosition(R.id.default_simulation_filter_spinner,
				defaultColorSimulation);

		redToleranceET = cu.loadEditText(R.id.red_tolerance,
				defaultRedTolerance + "");
		greenToleranceET = cu.loadEditText(R.id.green_tolerance,
				defaultGreenTolerance + "");
		blueToleranceET = cu.loadEditText(R.id.blue_tolerance,
				defaultBlueTolerance + "");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == Constants.COLOR_PICKER_REQUEST_CODE) {
				defaultRed = data.getIntExtra(Constants.RED, defaultRed);
				defaultGreen = data.getIntExtra(Constants.GREEN, defaultGreen);
				defaultBlue = data.getIntExtra(Constants.BLUE, defaultBlue);
			}
		}
	}

	@Override
	public void onBackPressed() {
		tryToSaveValues();
	}

	private void tryToSaveValues() {
		if (validTolerances()) {
			saveValues();
			Toast.makeText(this, this.getString(R.string.saved),
					Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(this, this.getString(R.string.invalid_value),
					Toast.LENGTH_LONG).show();
		}
	}

	private int getTolerance(EditText et) {
		try {
			return Integer.parseInt(et.getText().toString());
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	private boolean validTolerances() {
		defaultRedTolerance = getTolerance(redToleranceET);
		defaultGreenTolerance = getTolerance(greenToleranceET);
		defaultBlueTolerance = getTolerance(blueToleranceET);

		return validTolerance(defaultRedTolerance)
				&& validTolerance(defaultGreenTolerance)
				&& validTolerance(defaultBlueTolerance);
	}

	private boolean validTolerance(int color) {
		return color >= Constants.MIN_COLOR_VALUE
				&& color <= Constants.MAX_COLOR_VALUE;
	}

	private void saveValues() {
		settingsManager.saveDefaultIntensity(defaultIntensity);
		settingsManager.saveDefaultColorSimulationId(defaultColorSimulation);
		settingsManager.saveDefaultRedTolerance(defaultRedTolerance);
		settingsManager.saveDefaultGreenTolerance(defaultGreenTolerance);
		settingsManager.saveDefaultBlueTolerance(defaultBlueTolerance);
		settingsManager.saveDefaultRed(defaultRed);
		settingsManager.saveDefaultGreen(defaultGreen);
		settingsManager.saveDefaultBlue(defaultBlue);
	}
}
