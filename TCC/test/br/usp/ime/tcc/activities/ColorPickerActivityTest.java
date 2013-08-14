package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ColorPickerActivityTest {

	private ColorPickerActivity activity;
	private ButtonActionsTestHelper bat;

	private boolean colorSeekbarIsCorrectlyLoaded(int seekbarId) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);

		return (bar != null && Constants.MAX_COLOR_VALUE == bar.getMax());
	}

	private void setColor(int seekbarId, int colorValue) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);
		bar.setProgress(colorValue);
	}

	private void setSeekBarsToDesiredValues(int rgb[]) {
		setColor(R.id.red_seekbar, rgb[0]);
		setColor(R.id.green_seekbar, rgb[1]);
		setColor(R.id.blue_seekbar, rgb[2]);
	}

	private int[] generateRandomColor() {
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);

		return new int[] { red, green, blue };
	}

	private boolean sameRgb(int[] oneColor, int[] otherColor) {
		return oneColor[0] == otherColor[0] && oneColor[1] == otherColor[1]
				&& oneColor[2] == otherColor[2];
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(ColorPickerActivity.class)
				.create().get();
		bat = new ButtonActionsTestHelper(activity);
	}

	@Test
	public void okButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.ok_button));
	}

	@Test
	public void cancelButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.cancel_button));
	}

	@Test
	public void cancelButtonShouldReturnBitmapFilterActivity() {
		bat.getButtonAndClickOnIt(R.id.cancel_button);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_CANCELED, sa.getResultCode());
	}

	@Test
	public void okButtonShouldReturnBitmapFilterActivityWithOkResultIfValidColor() {
		bat.getButtonAndClickOnIt(R.id.ok_button);

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