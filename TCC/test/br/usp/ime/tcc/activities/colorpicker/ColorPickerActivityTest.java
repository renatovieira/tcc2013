package br.usp.ime.tcc.activities.colorpicker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.android.view.TestMenuItem;

import android.view.MenuItem;
import br.usp.ime.tcc.utils.Constants;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class ColorPickerActivityTest {
	protected ColorPickerActivity activity;

	protected int[] generateRandomColor() {
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);

		return new int[] { red, green, blue };
	}

	// Tests

	@Test
	public void saveOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		
		assertTrue(activity.onOptionsItemSelected(item));
	}

	@Test
	public void discardOptionShouldBeLoadedAndWorking() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		
		assertTrue(activity.onOptionsItemSelected(item));	
	}
	
	@Test
	public void invalidOptionShouldReturnFalse() {
		MenuItem item = new TestMenuItem(7);
		
		assertFalse(activity.onOptionsItemSelected(item));	
	}
}