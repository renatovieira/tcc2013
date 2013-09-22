package br.usp.ime.tcc.activities.bitmap.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.tester.android.view.TestMenuItem;

import android.view.MenuItem;
import android.widget.Button;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.filter.bitmap.BitmapFilterActivity;
import br.usp.ime.tcc.utils.Constants;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class BitmapFilterActivityTest {
	protected BitmapFilterActivity bitmapFilterActivity;

	protected int getVisibilyFromButton(int buttonId) {
		Button button = (Button) bitmapFilterActivity
				.findViewById(buttonId);
		return button.getVisibility();
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
		assertTrue(bitmapFilterActivity.onOptionsItemSelected(item));

		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void saveButtonShouldReturnErrorToast() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		assertTrue(bitmapFilterActivity.onOptionsItemSelected(item));

		ShadowHandler.idleMainLooper();
		assertEquals(bitmapFilterActivity.getString(R.string.file_saved),
				ShadowToast.getTextOfLatestToast());
	}
	
	@Test
	public void invalidButtonShouldReturnFalse() {
		MenuItem item = new TestMenuItem(7);
		assertFalse(bitmapFilterActivity.onOptionsItemSelected(item));
	}
}
