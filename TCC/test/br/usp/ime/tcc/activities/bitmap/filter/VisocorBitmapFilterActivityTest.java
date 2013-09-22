package br.usp.ime.tcc.activities.bitmap.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.filter.bitmap.BitmapFilterActivity;
import br.usp.ime.tcc.activities.filter.bitmap.VisocorBitmapFilterActivity;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class VisocorBitmapFilterActivityTest extends BitmapFilterActivityTest {
	private void callTheSeekBarListener(SeekBar bar) {
		bar.setProgress((bar.getProgress() + 1) % bar.getMax());
	}

	private BitmapFilterActivity startWithExtras() {
		bitmapFilterActivity = new VisocorBitmapFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.IMAGE_PATH, "dummy");

		bitmapFilterActivity.setIntent(intent);
		bitmapFilterActivity.onCreate(null);
		
		bitmapFilterActivity.onCreateOptionsMenu(new TestMenu());

		return bitmapFilterActivity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		bitmapFilterActivity = startWithExtras();
	}

	@Test
	public void backButtonShouldFinishActivity() {
		bitmapFilterActivity.onBackPressed();

		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void extrasShouldNotBeNull() {
		assertNotNull(bitmapFilterActivity.getIntent().getExtras());
	}

	@Test
	public void discardButtonShouldReturnToFilterActivity() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		bitmapFilterActivity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void saveButtonShouldReturnErrorToast() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		bitmapFilterActivity.onOptionsItemSelected(item);

		ShadowHandler.idleMainLooper();
		assertEquals(bitmapFilterActivity.getString(R.string.file_saved),
				ShadowToast.getTextOfLatestToast());
	}

	@Test
	public void shouldCallSeekBarListenerWhenProgressChanged() {
		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);
		callTheSeekBarListener(bar);
	}

	public void seekBarShouldBeVisibleOnVisocor() {
		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.VISIBLE, bar.getVisibility());
	}
	
	@Test
	public void seekBarShouldBeLoadedCorrectly() {
		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);
		
		assertEquals(Constants.MAX_INTENSITY, bar.getMax());
	}

	@Test
	public void colorPickerButtonShouldBeGoneOnVisocor() {
		assertEquals(View.GONE, getVisibilyFromButton(R.id.color_picker_button));
	}
}
