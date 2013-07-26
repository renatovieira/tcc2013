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

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	private MainActivity activity;
	private ButtonActionsTest bat;
	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(MainActivity.class)
				.create().get();
		
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
	public void pressingVisocorButtonShouldStartFilterActivity() {
		Intent startedIntent = bat.getButtonAndGetStartedIntentAfterClick(R.id.visocor_button);
		
		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(shadowIntent.getComponent().getClassName(), FilterActivity.class.getName());
	}

	@Test
	public void pressingSimulationButtonShouldStartFilterActivity() {
		Intent startedIntent = bat.getButtonAndGetStartedIntentAfterClick(R.id.visocor_button);
		
		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(shadowIntent.getComponent().getClassName(), FilterActivity.class.getName());
	}}
