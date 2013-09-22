package br.usp.ime.tcc.activities.bitmap.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.android.view.TestMenu;

import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.filter.bitmap.BitmapFilterActivity;
import br.usp.ime.tcc.activities.filter.bitmap.SimulationBitmapFilterActivity;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SimulationBitmapFilterActivityTest extends BitmapFilterActivityTest{
	private BitmapFilterActivity startWithExtras(int filterType) {
		bitmapFilterActivity = new SimulationBitmapFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE, filterType);
		intent.putExtra(Constants.IMAGE_PATH, "dummy");

		bitmapFilterActivity.setIntent(intent);
		bitmapFilterActivity.onCreate(null);
		
		bitmapFilterActivity.onCreateOptionsMenu(new TestMenu());

		return bitmapFilterActivity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		bitmapFilterActivity = startWithExtras(0);
	}

	@Test
	public void seekBarShouldBeGoneOnSimulation() {
		SeekBar bar = (SeekBar) bitmapFilterActivity
				.findViewById(R.id.intensity_bar);
		assertNotNull(bar);

		assertEquals(View.GONE, bar.getVisibility());
	}

	@Test
	public void colorPickerButtonShouldBeGoneOnSimulation() {
		assertEquals(View.GONE, getVisibilyFromButton(R.id.color_picker_button));
	}
}
