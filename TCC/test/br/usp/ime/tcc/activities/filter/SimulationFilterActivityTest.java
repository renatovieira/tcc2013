package br.usp.ime.tcc.activities.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import android.view.View;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;
import br.usp.ime.tcc.activities.filter.bitmap.SimulationBitmapFilterActivity;
import br.usp.ime.tcc.activities.filter.continous.SimulationContinousFilterActivity;

@RunWith(RobolectricTestRunner.class)
public class SimulationFilterActivityTest extends FilterActivityTest {
	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric
				.buildActivity(SimulationFilterActivity.class).create().get();

		bat = new ButtonActionsTestHelper(filterActivity);

		title = filterActivity.getString(R.string.simulation_filter);
	}

	@Test
	public void liveModeLLShouldBeVisible() {
		assertEquals(View.VISIBLE, getVisibilityFromLinearLayout(R.id.liveModeLL));
	}

	@Test
	public void spinnerLLShouldBeVisible() {
		assertEquals(View.VISIBLE,
				getVisibilityFromLinearLayout(R.id.filter_type_spinner_ll));
	}

	@Test
	public void shouldCallBitmapFilterActivityAfterGalleryResult() {
		ShadowIntent shadowIntent = bat
				.getIntentAfterResultOfImageButtonClick(R.id.galleryModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				SimulationBitmapFilterActivity.class.getName());
	}

	@Test
	public void shouldCallBitmapFilterActivityAfterCameraResult() {
		ShadowIntent shadowIntent = bat
				.getIntentAfterResultOfImageButtonClick(R.id.cameraModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				SimulationBitmapFilterActivity.class.getName());
	}
	
	@Test
	public void pressingLiveModeButtonShouldStartSimulationContinousFilterActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnImageButton(R.id.liveModeButton);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(shadowIntent.getComponent().getClassName(),
				SimulationContinousFilterActivity.class.getName());
	}
}
