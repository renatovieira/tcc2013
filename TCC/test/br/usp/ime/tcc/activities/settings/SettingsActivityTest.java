package br.usp.ime.tcc.activities.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.colorpicker.SettingsColorPickerActivity;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SettingsActivityTest {
	private SettingsActivity activity;
	private ButtonActionsTestHelper bat;

	private String getToleranceFromEditText(int editTextId) {
		EditText et = (EditText) activity.findViewById(editTextId);
		return et.getText().toString();
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(SettingsActivity.class).create()
				.get();

		bat = new ButtonActionsTestHelper(activity);

		activity.onCreateOptionsMenu(new TestMenu());
	}

	@Test
	public void colorPickerButtonShouldBeLoadedAndWorking() {
		assertTrue(bat
				.getImageButtonAndClickOnIt(R.id.default_color_picker_button));
	}

	@Test
	public void pressingColorPickerButtonShouldStartColorPickerActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnImageButton(R.id.default_color_picker_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(SettingsColorPickerActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void seekBarShouldBeLoadedCorrectly() {
		SeekBar bar = (SeekBar) activity.findViewById(R.id.default_intensity);
		assertNotNull(bar);

		assertEquals(Constants.MAX_INTENSITY, bar.getMax());

		assertEquals(Constants.INTENSITY, bar.getProgress());
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

		int defaultRedTolerance = Constants.DEFAULT_TOLERANCE;
		assertEquals(defaultRedTolerance + "", redTolerance);
	}

	@Test
	public void greenToleranceShouldBeLoadedCorrectly() {
		String greenTolerance = getToleranceFromEditText(R.id.green_tolerance);

		int defaultGreenTolerance = Constants.DEFAULT_TOLERANCE;
		assertEquals(defaultGreenTolerance + "", greenTolerance);
	}

	@Test
	public void blueToleranceShouldBeLoadedCorrectly() {
		String blueTolerance = getToleranceFromEditText(R.id.blue_tolerance);

		int defaultBlueTolerance = Constants.DEFAULT_TOLERANCE;
		assertEquals(defaultBlueTolerance + "", blueTolerance);
	}
	
	@Test
	public void simpleModeCheckBoxShouldBeLoadedCorrectly() {
		CheckBox simpleModeCheckBox = (CheckBox) activity.findViewById(R.id.simple_simulation);
		assertNotNull(simpleModeCheckBox);
		
		boolean checked = simpleModeCheckBox.isChecked();

		assertEquals(Constants.SIMPLE_MODE, checked);
	}

	@Test
	public void shouldCallOnActivityResultAfterColorPickerResult() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnImageButton(R.id.default_color_picker_button);
		ShadowActivity shadowActivity = shadowOf(activity);
		shadowActivity.receiveResult(startedIntent, Activity.RESULT_OK,
				new Intent().setData(null));

		assertTrue(true);
	}

	@Test
	public void saveOptionWithValidValuesShouldFinishActivity() {
		MenuItem item = new TestMenuItem(Constants.SAVE);

		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void discardOptionShouldFinishActivity() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);

		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void saveOptionWithValidValuesShouldDisplayOkToast() {
		MenuItem item = new TestMenuItem(Constants.SAVE);

		activity.onOptionsItemSelected(item);

		ShadowHandler.idleMainLooper();
		assertEquals(activity.getString(R.string.saved),
				ShadowToast.getTextOfLatestToast());
	}

	@Test
	public void saveOptionWithEmptyValuesShouldDisplayErrorDialog() {
		EditText blueTolerance = (EditText) activity
				.findViewById(R.id.blue_tolerance);
		blueTolerance.setText("");

		MenuItem item = new TestMenuItem(Constants.SAVE);

		activity.onOptionsItemSelected(item);

		ShadowHandler.idleMainLooper();
		AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
		ShadowAlertDialog sAlert = shadowOf(alertDialog);

		assertEquals(activity.getString(R.string.invalid_value), sAlert.getMessage().toString());
		assertTrue(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick());
		assertFalse(alertDialog.isShowing());	}

	@Test
	public void saveOptionWithInvalidValuesShouldDisplayErrorDialog() {
		EditText blueTolerance = (EditText) activity
				.findViewById(R.id.blue_tolerance);
		blueTolerance.setText("49831");

		MenuItem item = new TestMenuItem(Constants.SAVE);

		activity.onOptionsItemSelected(item);

		ShadowHandler.idleMainLooper();
		AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
		ShadowAlertDialog sAlert = shadowOf(alertDialog);

		assertEquals(activity.getString(R.string.invalid_value), sAlert.getMessage().toString());
		assertTrue(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick());
		assertFalse(alertDialog.isShowing());
	}
	
	@Test
	public void invalidOptionShouldReturnFalse() {
		MenuItem item = new TestMenuItem(42);

		assertFalse(activity.onOptionsItemSelected(item));
	}

	@Test
	public void saveOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.SAVE);

		assertTrue(activity.onOptionsItemSelected(item));
	}

	@Test
	public void discardOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);

		assertTrue(activity.onOptionsItemSelected(item));
	}
}
