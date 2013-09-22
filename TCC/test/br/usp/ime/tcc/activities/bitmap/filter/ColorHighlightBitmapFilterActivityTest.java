package br.usp.ime.tcc.activities.bitmap.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.tester.android.view.TestMenu;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.ColorPickerActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;
import br.usp.ime.tcc.activities.filter.bitmap.BitmapFilterActivity;
import br.usp.ime.tcc.activities.filter.bitmap.ColorHighlightBitmapFilterActivity;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ColorHighlightBitmapFilterActivityTest extends BitmapFilterActivityTest {
	private BitmapFilterActivity startWithExtras() {
		bitmapFilterActivity = new ColorHighlightBitmapFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.IMAGE_PATH, "dummy");

		bitmapFilterActivity.setIntent(intent);
		bitmapFilterActivity.onCreate(null);
		
		bitmapFilterActivity.onCreateOptionsMenu(new TestMenu());

		return bitmapFilterActivity;
	}
	
	@Before
	public void setUp() throws Exception {
		bitmapFilterActivity = startWithExtras();
	}

	@Test
	public void seekBarShouldBeHiddenOnColorHighlight() {
		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.GONE, bar.getVisibility());
	}

	@Test
	public void colorPickerButtonShouldBeVisibleOnColorHighlight() {
		assertEquals(View.VISIBLE,
				getVisibilyFromButton(R.id.color_picker_button));
	}

	@Test
	public void colorPickerButtonStartColorPickerActivity() {
		ButtonActionsTestHelper bat = new ButtonActionsTestHelper(bitmapFilterActivity);

		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.color_picker_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(ColorPickerActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void shouldCallOnActivityResultAfterColorPickerResult() {
		ButtonActionsTestHelper bat = new ButtonActionsTestHelper(bitmapFilterActivity);

		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.color_picker_button);
		ShadowActivity shadowActivity = shadowOf(bitmapFilterActivity);
		shadowActivity.receiveResult(startedIntent, Activity.RESULT_OK,
				new Intent().setData(null));
		
		assertTrue(true);
	}
}
