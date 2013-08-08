package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import android.app.Activity;
import android.widget.EditText;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ColorPickerActivityTest {
	private static final String INVALID_COLOR_VALUE = "256";
	
	private ColorPickerActivity activity;
	private ButtonActionsTest bat;

	private boolean colorSeekbarIsCorrectlyLoaded(int seekbarId) {
		SeekBar bar = (SeekBar) activity.findViewById(seekbarId);

		return (bar != null && Constants.MAX_COLOR_VALUE == bar.getMax());
	}

	private boolean changeToColorSeekBarProgressAffectRelatedEditText(
			int seekbarId, int editTextId) {
		SeekBar seekbar = (SeekBar) activity.findViewById(seekbarId);
		EditText et = (EditText) activity.findViewById(editTextId);

		int value = 120;

		seekbar.setProgress(value);

		return et.getText().toString().equals(120 + "");
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(ColorPickerActivity.class)
				.create().get();
		bat = new ButtonActionsTest(activity);
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
	public void okButtonShouldMakeAToastIfInvalidColor() {
		EditText et = (EditText) activity.findViewById(R.id.red_value);
		et.setText(INVALID_COLOR_VALUE);
		
		bat.getButtonAndClickOnIt(R.id.ok_button);
		
		ShadowHandler.idleMainLooper();
		assertEquals(activity.getString(R.string.invalid_color),
				ShadowToast.getTextOfLatestToast());
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
	public void redSeekbarProgressChangeShouldAffectRedEditText() {
		assertEquals(
				true,
				changeToColorSeekBarProgressAffectRelatedEditText(
						R.id.red_seekbar, R.id.red_value));
	}

	@Test
	public void greenSeekbarProgressChangeShouldAffectRedEditText() {
		assertEquals(
				true,
				changeToColorSeekBarProgressAffectRelatedEditText(
						R.id.green_seekbar, R.id.green_value));
	}

	@Test
	public void blueSeekbarProgressChangeShouldAffectRedEditText() {
		assertEquals(
				true,
				changeToColorSeekBarProgressAffectRelatedEditText(
						R.id.blue_seekbar, R.id.blue_value));
	}
}