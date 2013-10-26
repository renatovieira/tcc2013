package br.usp.ime.tcc.activities.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

	private boolean save(String key, int value) {
		return preferences.edit().putInt(key, value).commit();
	}
	
	public boolean settingsClear() {
		return preferences.edit().clear().commit();
	}

	public int loadDefaultIntensity() {
		return preferences.getInt(DEFAULT_INTENSITY, Constants.INTENSITY);
	}

	public int loadDefaultColorSimulationId() {
		return preferences.getInt(DEFAULT_COLOR_SIMULATION_ID, Constants.DEUTERANOPIA_SIMULATION_FILTER);
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
	
	public boolean saveDefaultIntensity(int value) {
		return save(DEFAULT_INTENSITY, value);
	}

	public boolean saveDefaultColorSimulationId(int value) {
		return save(DEFAULT_COLOR_SIMULATION_ID, value);
	}

	public boolean saveDefaultRedTolerance(int value) {
		return save(DEFAULT_RED_TOLERANCE, value);
	}

	public boolean saveDefaultGreenTolerance(int value) {
		return save(DEFAULT_GREEN_TOLERANCE, value);
	}

	public boolean saveDefaultBlueTolerance(int value) {
		return save(DEFAULT_BLUE_TOLERANCE, value);
	}

	public boolean saveDefaultRed(int value) {
		return save(DEFAULT_RED, value);
	}

	public boolean saveDefaultGreen(int value) {
		return save(DEFAULT_GREEN, value);
	}

	public boolean saveDefaultBlue(int value) {
		return save(DEFAULT_BLUE, value);
	}
}
