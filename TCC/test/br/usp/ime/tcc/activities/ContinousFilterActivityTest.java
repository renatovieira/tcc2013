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

import android.content.Intent;
import android.widget.SeekBar;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ContinousFilterActivityTest {
	private ContinousFilterActivity activity;

	private ContinousFilterActivity startWithExtras() {
		activity = new ContinousFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE, Constants.VISOCOR_FILTER);
		
		activity.setIntent(intent);
		activity.onCreate(null);

		return activity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		activity = startWithExtras();
	}

	@Test
	public void backButtonShouldFinish() {
		activity.onBackPressed();

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void seekBarShouldBeLoadedWithDefaultProgress() {
		SeekBar bar = (SeekBar) activity
				.findViewById(R.id.continous_filter_intensity_bar);

		assertNotNull(bar);
		assertEquals(Constants.PROGRESS, bar.getProgress());
	}

	@Test
	public void pauseAndResumeShouldKeepActivityRunning() {
		activity.onPause();
		activity.onResume();
		activity.onDestroy();
		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isDestroyed());
	}
}
