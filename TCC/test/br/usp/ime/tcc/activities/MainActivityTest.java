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
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.activities.filter.ColorHighlightFilterActivity;
import br.usp.ime.tcc.activities.filter.SimulationFilterActivity;
import br.usp.ime.tcc.activities.filter.VisocorFilterActivity;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	private MainActivity activity;
	private ButtonActionsTest bat;

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(MainActivity.class).create().get();

		bat = new ButtonActionsTest(activity);
	}

	@Test
	public void visocorButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.visocor_button));
	}

	@Test
	public void simulationButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.simulation_button));
	}

	@Test
	public void highlightButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.highlight_button));
	}

	@Test
	public void pressingVisocorButtonShouldStartVisocorFilterActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.visocor_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(VisocorFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void pressingSimulationButtonShouldSimulationStartFilterActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.simulation_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(SimulationFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void pressingHighlightButtonShouldStartFilterActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.highlight_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(ColorHighlightFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}
}
