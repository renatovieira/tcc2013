package br.usp.ime.tcc.activities.filter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.view.View;
import android.widget.Button;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;

@RunWith(RobolectricTestRunner.class)
public class SimulationFilterActivityTest extends FilterActivityTest {
	private int getVisibilyFromButton(int buttonId) {
		Button button = (Button) filterActivity.findViewById(buttonId);
		return button.getVisibility();
	}
	
	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric.buildActivity(SimulationFilterActivity.class).create().get();

		bat = new ButtonActionsTest(filterActivity);
		
		title = filterActivity.getString(R.string.simulation_filter);
	}
	
	@Test
	public void liveModeButtonShouldBeGone() {
		assertEquals(View.GONE, getVisibilyFromButton(R.id.liveModeButton));
	}

	@Test
	public void spinnerShouldBeVisible() {
		assertEquals(View.VISIBLE, getVisibiltyFromSpinner());
	}
}
