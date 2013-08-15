package br.usp.ime.tcc.activities.filter;

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
import android.view.View;
import br.usp.ime.tcc.activities.ContinousFilterActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;

@RunWith(RobolectricTestRunner.class)
public class VisocorFilterActivityTest extends FilterActivityTest {
	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric.buildActivity(VisocorFilterActivity.class).create().get();

		bat = new ButtonActionsTestHelper(filterActivity);
		
		title = filterActivity.getString(R.string.visocor_filter);
	}
	
	@Test
	public void liveModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.liveModeButton));
	}
	
	@Test
	public void liveModeButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.liveModeButton));
	}

	@Test
	public void spinnerShouldBeHidden() {
		assertEquals(View.GONE, getVisibiltyFromSpinner());
	}
	
	@Test
	public void pressingLiveModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.liveModeButton);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(shadowIntent.getComponent().getClassName(),
				ContinousFilterActivity.class.getName());
	}
}
