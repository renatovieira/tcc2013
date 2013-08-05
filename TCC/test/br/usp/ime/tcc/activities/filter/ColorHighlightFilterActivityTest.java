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
public class ColorHighlightFilterActivityTest extends FilterActivityTest {
	private int getVisibilyFromButton(int liveModeButton) {
		Button button = (Button) filterActivity.findViewById(liveModeButton);
		return button.getVisibility();
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric.buildActivity(ColorHighlightFilterActivity.class).create().get();

		bat = new ButtonActionsTest(filterActivity);
		
		title = filterActivity.getString(R.string.color_highlight_filter);
	}
	
	@Test
	public void spinnerShouldBeGone() {
		assertEquals(View.GONE, getVisibiltyFromSpinner());
	}
	
	@Test
	public void liveModeButtonShouldBeGone() {
		assertEquals(View.GONE, getVisibilyFromButton(R.id.liveModeButton));
	}
}
