package br.usp.ime.tcc.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.colorpicker.SettingsColorPickerActivity;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SettingsActivity extends SherlockActivity {
	private int defaultIntensity;
	private int defaultColorSimulation;
	private int defaultRedTolerance;
	private int defaultGreenTolerance;
	private int defaultBlueTolerance;
	private int defaultRed;
	private int defaultGreen;
	private int defaultBlue;
	
	private SettingsManager settingsManager;
	
	private EditText redToleranceET;
	private EditText greenToleranceET;
	private EditText blueToleranceET;
	private SeekBar intensityBar;
	private Spinner simulationTypeSpinner;
	private ComponentUtils cu;
	private ImageButton colorSampleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setTheme(R.style.Theme_Sherlock);
		Utils.loadStripedActionBar(this);
		
		setContentView(R.layout.settings);
		settingsManager = new SettingsManager(this);

		loadDefaultValues();

		loadComponents();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
        	.setIcon(R.drawable.ic_action_save)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE, getString(R.string.discard))
        	.setIcon(R.drawable.ic_action_discard)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
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
		cu = new ComponentUtils(this);
		
		colorSampleButton = cu.loadImageButton(R.id.default_color_picker_button, new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent colorPickerIntent = new Intent(getBaseContext(),
						SettingsColorPickerActivity.class);
				colorPickerIntent.putExtra(Constants.RED, defaultRed);
				colorPickerIntent.putExtra(Constants.GREEN, defaultGreen);
				colorPickerIntent.putExtra(Constants.BLUE, defaultBlue);
				startActivityForResult(colorPickerIntent,
						Constants.COLOR_PICKER_REQUEST_CODE);
			}
		});
		
		cu.updateWithColor(colorSampleButton, defaultRed, defaultGreen, defaultBlue);

		intensityBar = cu.loadSeekBar(R.id.default_intensity, Constants.MAX_INTENSITY,
				defaultIntensity);
		
		simulationTypeSpinner = cu.loadSpinner(R.id.default_simulation_filter_spinner);

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
				cu.updateWithColor(colorSampleButton, defaultRed, defaultGreen, defaultBlue);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int option = item.getItemId();
		
		if (option == Constants.SAVE) {
			tryToSaveValues();
			return true;
		}
		else if (option == Constants.DISCARD) {
			finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void tryToSaveValues() {
		if (validTolerances()) {
			defaultIntensity = intensityBar.getProgress();
			defaultColorSimulation = simulationTypeSpinner.getSelectedItemPosition();
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
