package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ColorPickerActivityTest {

	private ColorPickerActivity activity;

	private boolean colorSeekbarIsCorrectlyLoaded(int seekbarId) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);

		return (bar != null && Constants.MAX_COLOR_VALUE == bar.getMax());
	}

	private void setColor(int seekbarId, int colorValue) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);
		bar.setProgress(colorValue);
	}

	private void setSeekBarsToDesiredValues(int rgb[]) {
		setColor(R.id.red_seekbar, rgb[ColorPickerActivity.RED]);
		setColor(R.id.green_seekbar, rgb[ColorPickerActivity.GREEN]);
		setColor(R.id.blue_seekbar, rgb[ColorPickerActivity.BLUE]);
	}

	private int[] generateRandomColor() {
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);

		return new int[] { red, green, blue };
	}

	private boolean sameRgb(int[] oneColor, int[] otherColor) {
		return oneColor[ColorPickerActivity.RED] == otherColor[ColorPickerActivity.RED] &&
			   oneColor[ColorPickerActivity.GREEN] == otherColor[ColorPickerActivity.GREEN] &&
			   oneColor[ColorPickerActivity.BLUE] == otherColor[ColorPickerActivity.BLUE];
	}

	private ColorPickerActivity startWithExtras() {
		activity = new ColorPickerActivity();
		Intent intent = new Intent();
		
		int [] randomColor = generateRandomColor();
		
		intent.putExtra(Constants.RED, randomColor[ColorPickerActivity.RED]);
		intent.putExtra(Constants.GREEN, randomColor[ColorPickerActivity.GREEN]);
		intent.putExtra(Constants.BLUE, randomColor[ColorPickerActivity.BLUE]);

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
	public void saveOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		
		assertTrue(activity.onOptionsItemSelected(item));
	}

	@Test
	public void discardOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		
		assertTrue(activity.onOptionsItemSelected(item));	
	}
	
	@Test
	public void invalidOptionShouldReturnFalse() {
		MenuItem item = new TestMenuItem(7);
		
		assertFalse(activity.onOptionsItemSelected(item));	
	}

	@Test
	public void discardOptionShouldReturnBitmapFilterActivity() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_CANCELED, sa.getResultCode());
	}

	@Test
	public void saveOptionShouldReturnBitmapFilterActivityWithOkResult() {
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