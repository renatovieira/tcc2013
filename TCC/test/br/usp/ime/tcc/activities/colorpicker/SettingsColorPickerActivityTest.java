package br.usp.ime.tcc.activities.colorpicker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SettingsColorPickerActivityTest extends ColorPickerActivityTest {
	private boolean colorSeekbarIsCorrectlyLoaded(int seekbarId) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);

		return (bar != null && Constants.MAX_COLOR_VALUE == bar.getMax());
	}

	private void setColor(int seekbarId, int colorValue) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);
		bar.setProgress(colorValue);
	}

	private void setSeekBarsToDesiredValues(int rgb[]) {
		setColor(R.id.red_seekbar, rgb[SettingsColorPickerActivity.RED]);
		setColor(R.id.green_seekbar, rgb[SettingsColorPickerActivity.GREEN]);
		setColor(R.id.blue_seekbar, rgb[SettingsColorPickerActivity.BLUE]);
	}

	private boolean sameRgb(int[] oneColor, int[] otherColor) {
		return oneColor[SettingsColorPickerActivity.RED] == otherColor[SettingsColorPickerActivity.RED] &&
			   oneColor[SettingsColorPickerActivity.GREEN] == otherColor[SettingsColorPickerActivity.GREEN] &&
			   oneColor[SettingsColorPickerActivity.BLUE] == otherColor[SettingsColorPickerActivity.BLUE];
	}

	private ColorPickerActivity startWithExtras() {
		activity = new SettingsColorPickerActivity();
		Intent intent = new Intent();
		
		int [] randomColor = generateRandomColor();
		
		intent.putExtra(Constants.RED_STR, randomColor[SettingsColorPickerActivity.RED]);
		intent.putExtra(Constants.GREEN_STR, randomColor[SettingsColorPickerActivity.GREEN]);
		intent.putExtra(Constants.BLUE_STR, randomColor[SettingsColorPickerActivity.BLUE]);

		activity.setIntent(intent);
		activity.onCreate(null);

		return activity;
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = startWithExtras();
		activity.onCreateOptionsMenu(new TestMenu());
	}

	@Test
	public void discardOptionShouldReturnToSettingsActivity() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_CANCELED, sa.getResultCode());
	}

	@Test
	public void saveOptionShouldReturnToSettingsActivityWithOkResult() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_OK, sa.getResultCode());
	}

	@Test
	public void redSeekbarShouldBeCorrectlyLoaded() {
		assertEquals(true, colorSeekbarIsCorrectlyLoaded(R.id.red_seekbar));
	}

	@Test
	public void greenSeekbarShouldBeCorrectlyLoaded() {
		assertEquals(true, colorSeekbarIsCorrectlyLoaded(R.id.green_seekbar));
	}

	@Test
	public void blueSeekbarShouldBeCorrectlyLoaded() {
		assertEquals(true, colorSeekbarIsCorrectlyLoaded(R.id.blue_seekbar));
	}

	@Test
	public void colorSampleShouldChangeAfterSeekbarChanges() {
		ImageView colorSample = (ImageView) activity
				.findViewById(R.id.selected_color);
		assertNotNull(colorSample);

		int[] randomColor = generateRandomColor();

		assertEquals(false, sameRgb(activity.rgb, randomColor));
		setSeekBarsToDesiredValues(randomColor);
		assertEquals(true, sameRgb(activity.rgb, randomColor));
	}
}