package br.usp.ime.tcc.activities.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import br.usp.ime.tcc.utils.Constants;

public class SettingsManager {
	private static final String DEFAULT_BLUE = "defaultBlue";
	private static final String DEFAULT_GREEN = "defaultGreen";
	private static final String DEFAULT_RED = "defaultRed";
	private static final String DEFAULT_BLUE_TOLERANCE = "defaultBlueTolerance";
	private static final String DEFAULT_GREEN_TOLERANCE = "defaultGreenTolerance";
	private static final String DEFAULT_RED_TOLERANCE = "defaultRedTolerance";
	private static final String DEFAULT_COLOR_SIMULATION_ID = "defaultColorSimulationId";
	private static final String DEFAULT_INTENSITY = "defaultIntensity";
	private static final String SETTINGS = "settings";
	private SharedPreferences preferences;

	public SettingsManager(Activity activity) {
		preferences = activity.getSharedPreferences(SETTINGS,
				Context.MODE_PRIVATE);
	}

	private void save(String key, int value) {
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int loadDefaultIntensity() {
		return preferences.getInt(DEFAULT_INTENSITY, Constants.INTENSITY);
	}

	public int loadDefaultColorSimulationId() {
		return preferences.getInt(DEFAULT_COLOR_SIMULATION_ID, 0);
	}

	public int loadDefaultRedTolerance() {
		return preferences.getInt(DEFAULT_RED_TOLERANCE,
				Constants.DEFAULT_TOLERANCE);
	}

	public int loadDefaultGreenTolerance() {
		return preferences.getInt(DEFAULT_GREEN_TOLERANCE,
				Constants.DEFAULT_TOLERANCE);
	}

	public int loadDefaultBlueTolerance() {
		return preferences.getInt(DEFAULT_BLUE_TOLERANCE,
				Constants.DEFAULT_TOLERANCE);
	}

	public int loadDefaultRed() {
		return preferences.getInt(DEFAULT_RED, Constants.MAX_COLOR_VALUE);
	}

	public int loadDefaultGreen() {
		return preferences.getInt(DEFAULT_GREEN, Constants.MAX_COLOR_VALUE);
	}

	public int loadDefaultBlue() {
		return preferences.getInt(DEFAULT_BLUE, Constants.MAX_COLOR_VALUE);
	}

	public void saveDefaultIntensity(int value) {
		save(DEFAULT_INTENSITY, value);
	}

	public void saveDefaultColorSimulationId(int value) {
		save(DEFAULT_COLOR_SIMULATION_ID, value);
	}

	public void saveDefaultRedTolerance(int value) {
		save(DEFAULT_RED_TOLERANCE, value);
	}

	public void saveDefaultGreenTolerance(int value) {
		save(DEFAULT_GREEN_TOLERANCE, value);
	}

	public void saveDefaultBlueTolerance(int value) {
		save(DEFAULT_BLUE_TOLERANCE, value);
	}

	public void saveDefaultRed(int value) {
		save(DEFAULT_RED, value);
	}

	public void saveDefaultGreen(int value) {
		save(DEFAULT_GREEN, value);
	}

	public void saveDefaultBlue(int value) {
		save(DEFAULT_BLUE, value);
	}
}
