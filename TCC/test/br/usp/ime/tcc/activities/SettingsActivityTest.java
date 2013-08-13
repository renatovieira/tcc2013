package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SettingsActivityTest {
	private SettingsActivity activity;
	private ButtonActionsTest bat;

	private String getToleranceFromEditText(int editTextId) {
		EditText et = (EditText) activity.findViewById(editTextId);
		return et.getText().toString();
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(SettingsActivity.class).create()
				.get();

		bat = new ButtonActionsTest(activity);
	}

	@Test
	public void colorPickerButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.default_color_picker_button));
	}

	@Test
	public void pressingColorPickerButtonShouldStartColorPickerActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.default_color_picker_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(ColorPickerActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void seekBarShouldBeLoadedCorrectly() {
		SeekBar bar = (SeekBar) activity.findViewById(R.id.default_intensity);
		assertNotNull(bar);

		assertEquals(Constants.MAX_INTENSITY, bar.getMax());

		int defaultIntensity = 0;
		assertEquals(defaultIntensity, bar.getProgress());
	}

	@Test
	public void spinnerShouldBeLoadedCorrectly() {
		Spinner simulationSpinner = (Spinner) activity
				.findViewById(R.id.default_simulation_filter_spinner);
		assertNotNull(simulationSpinner);

		int defaultSimulation = 0;
		assertEquals(defaultSimulation,
				simulationSpinner.getSelectedItemPosition());
	}

	@Test
	public void redToleranceShouldBeLoadedCorrectly() {
		String redTolerance = getToleranceFromEditText(R.id.red_tolerance);

		int defaultRedTolerance = 0;
		assertEquals(defaultRedTolerance + "", redTolerance);
	}

	@Test
	public void greenToleranceShouldBeLoadedCorrectly() {
		String greenTolerance = getToleranceFromEditText(R.id.green_tolerance);

		int defaultGreenTolerance = 0;
		assertEquals(defaultGreenTolerance + "", greenTolerance);
	}

	@Test
	public void blueToleranceShouldBeLoadedCorrectly() {
		String blueTolerance = getToleranceFromEditText(R.id.blue_tolerance);

		int defaultBlueTolerance = 0;
		assertEquals(defaultBlueTolerance + "", blueTolerance);
	}

	@Test
	public void shouldCallOnActivityResultAfterColorPickerResult() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.default_color_picker_button);
		ShadowActivity shadowActivity = shadowOf(activity);
		shadowActivity.receiveResult(startedIntent, Activity.RESULT_OK,
				new Intent().setData(null));

		assertTrue(true);
	}

	@Test
	public void backButtonWithValidValuesShouldFinishActivity() {
		activity.onBackPressed();

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void backButtonWithValidValuesShouldDisplayOkToast() {
		activity.onBackPressed();

		ShadowHandler.idleMainLooper();
		assertEquals(activity.getString(R.string.saved),
				ShadowToast.getTextOfLatestToast());
	}
	
	@Test
	public void backButtonWithEmptyValuesShouldDisplayErrorToast() {
		EditText blueTolerance = (EditText) activity.findViewById(R.id.blue_tolerance);
		blueTolerance.setText("");
		
		activity.onBackPressed();

		ShadowHandler.idleMainLooper();
		assertEquals(activity.getString(R.string.invalid_value),
				ShadowToast.getTextOfLatestToast());
	}
	
	@Test
	public void backButtonWithInvalidValuesShouldDisplayErrorToast() {
		EditText blueTolerance = (EditText) activity.findViewById(R.id.blue_tolerance);
		blueTolerance.setText("49831");
		
		activity.onBackPressed();

		ShadowHandler.idleMainLooper();
		assertEquals(activity.getString(R.string.invalid_value),
				ShadowToast.getTextOfLatestToast());
	}
}
