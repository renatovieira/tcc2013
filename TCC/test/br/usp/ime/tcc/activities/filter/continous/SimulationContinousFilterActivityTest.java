package br.usp.ime.tcc.activities.filter.continous;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import android.content.Intent;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SimulationContinousFilterActivityTest {
	private SimulationContinousFilterActivity activity;

	private void startActivityWithExtras() {
		activity = new SimulationContinousFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE,
				Constants.DEUTERANOPIA_SIMULATION_FILTER);

		activity.setIntent(intent);
		activity.onCreate(null);
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		startActivityWithExtras();
	}

	@Test
	public void backButtonShouldFinish() {
		activity.onBackPressed();

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
	}

	@Test
	public void interruptionsShouldntCrashActivity() {
		activity.onPause();
		activity.onResume();
		activity.onDestroy();
		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isDestroyed());
	}
}
