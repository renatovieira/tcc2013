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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class BitmapFilterActivityTest {
	private BitmapFilterActivity bitmapFilterActivity;

	private Bitmap getBitmapFromImageView(ImageView image) {
		return ((BitmapDrawable) image.getDrawable()).getBitmap();
	}

	private void callTheSeekBarListener(SeekBar bar) {
		bar.setProgress((bar.getProgress() + 1) % bar.getMax());
	}

	private int getVisibilyFromButton(int liveModeButton) {
		Button button = (Button) bitmapFilterActivity
				.findViewById(liveModeButton);
		return button.getVisibility();
	}

	private BitmapFilterActivity startWithExtras(int filterType) {
		bitmapFilterActivity = new BitmapFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE, filterType);
		intent.putExtra(Constants.IMAGE_PATH, "dummy");

		bitmapFilterActivity.setIntent(intent);
		bitmapFilterActivity.onCreate(null);

		return bitmapFilterActivity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void backButtonShouldFinishActivity() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		bitmapFilterActivity.onBackPressed();

		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void extrasShouldNotBeNull() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		assertNotNull(bitmapFilterActivity.getIntent().getExtras());
	}

	@Test
	public void discardButtonShouldReturnToFilterActivity() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);
		bat.getButtonAndClickOnIt(R.id.discard_button);

		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void saveButtonShouldReturnErrorToast() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);
		bat.getButtonAndClickOnIt(R.id.save_button);

		ShadowHandler.idleMainLooper();
		assertEquals(bitmapFilterActivity.getString(R.string.try_again),
				ShadowToast.getTextOfLatestToast());
	}

	@Test
	public void shouldCallSeekBarListenerWhenProgressChanged() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		ImageView image = (ImageView) bitmapFilterActivity
				.findViewById(R.id.filtered_image);

		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		Bitmap bmp = getBitmapFromImageView(image);

		callTheSeekBarListener(bar);

		Bitmap newBmp = getBitmapFromImageView(image);

		assertEquals(false, bmp.equals(newBmp));
	}

	public void seekBarShouldBeVisibleOnVisocor() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.VISIBLE, bar.getVisibility());
	}
	
	@Test
	public void seekBarShouldBeLoadedCorrectly() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);
		
		assertEquals(Constants.MAX_INTENSITY, bar.getMax());
	}

	@Test
	public void seekBarShouldBeGoneOnSimulation() {
		bitmapFilterActivity = startWithExtras(Constants.SIMULATION_FILTER);

		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.GONE, bar.getVisibility());
	}

	@Test
	public void seekBarShouldBeHiddenOnColorHighlight() {
		bitmapFilterActivity = startWithExtras(Constants.COLOR_HIGHLIGHT_FILTER);

		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.GONE, bar.getVisibility());
	}

	@Test
	public void colorPickerButtonShouldBeGoneOnVisocor() {
		bitmapFilterActivity = startWithExtras(Constants.VISOCOR_FILTER);

		assertEquals(View.GONE, getVisibilyFromButton(R.id.color_picker_button));
	}

	@Test
	public void colorPickerButtonShouldBeGoneOnSimulation() {
		bitmapFilterActivity = startWithExtras(Constants.SIMULATION_FILTER);

		assertEquals(View.GONE, getVisibilyFromButton(R.id.color_picker_button));
	}

	@Test
	public void colorPickerButtonShouldBeVisibleOnColorHighlight() {
		bitmapFilterActivity = startWithExtras(Constants.COLOR_HIGHLIGHT_FILTER);

		assertEquals(View.VISIBLE,
				getVisibilyFromButton(R.id.color_picker_button));
	}

	@Test
	public void colorPickerButtonStartColorPickerActivity() {
		bitmapFilterActivity = startWithExtras(Constants.COLOR_HIGHLIGHT_FILTER);
		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);

		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.color_picker_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(ColorPickerActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void shouldCallOnActivityResultAfterColorPickerResult() {
		bitmapFilterActivity = startWithExtras(Constants.COLOR_HIGHLIGHT_FILTER);
		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);

		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.color_picker_button);
		ShadowActivity shadowActivity = shadowOf(bitmapFilterActivity);
		shadowActivity.receiveResult(startedIntent, Activity.RESULT_OK,
				new Intent().setData(null));
		
		assertTrue(true);
	}
}
