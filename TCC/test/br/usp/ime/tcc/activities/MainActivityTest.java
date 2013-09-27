package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;

import android.content.Intent;
import android.view.MenuItem;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;
import br.usp.ime.tcc.activities.filter.ColorHighlightFilterActivity;
import br.usp.ime.tcc.activities.filter.SimulationFilterActivity;
import br.usp.ime.tcc.activities.filter.VisocorFilterActivity;
import br.usp.ime.tcc.activities.settings.SettingsActivity;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	private MainActivity activity;
	private ButtonActionsTestHelper bat;

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(MainActivity.class).create().get();
		
		activity.onCreateOptionsMenu(new TestMenu());

		bat = new ButtonActionsTestHelper(activity);
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
	public void settingsButtonShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.SETTINGS);
		
		assertTrue(activity.onOptionsItemSelected(item));	
	}
	
	@Test
	public void invalidButtonShouldReturnFalse() {
		MenuItem item = new TestMenuItem(7);
		
		assertFalse(activity.onOptionsItemSelected(item));	
	}

	@Test
	public void pressingVisocorButtonShouldStartVisocorFilterActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.visocor_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(VisocorFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void pressingSimulationButtonShouldSimulationStartFilterActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.simulation_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(SimulationFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}

	@Test
	public void pressingHighlightButtonShouldStartColorHighlightFilterActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.highlight_button);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(ColorHighlightFilterActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}
	
	@Test
	public void pressingSettingsButtonShouldStartSettingsActivity() {
		MenuItem item = new TestMenuItem(Constants.SETTINGS);
		
		activity.onOptionsItemSelected(item);	
		
		Intent startedIntent = shadowOf(activity).getNextStartedActivity();

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(SettingsActivity.class.getName(), shadowIntent
				.getComponent().getClassName());
	}
}
