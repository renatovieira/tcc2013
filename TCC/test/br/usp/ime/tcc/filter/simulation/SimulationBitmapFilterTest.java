package br.usp.ime.tcc.filter.simulation;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SimulationBitmapFilterTest {
	private SimulationBitmapFilter filter;
	
	private Bitmap createYellowMockBitmap() {
		Bitmap bmp = mock(Bitmap.class);

		when(bmp.getWidth()).thenReturn(1);
		when(bmp.getHeight()).thenReturn(1);

		when(bmp.copy(any(Config.class), anyBoolean())).thenReturn(bmp);

		when(bmp.getPixel(anyInt(), anyInt())).thenReturn(Color.YELLOW);
		
		return bmp;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		filter = new SimulationBitmapFilter(
				Constants.DEUTERANOPIA_SIMULATION_FILTER);
	}

	@Test
	public void deutFilterShouldReturnNullIfBitmapIsNull() {
		assertNull(filter.applyTo(null));
	}

	@Test
	public void protFilterShouldReturnNullIfBitmapIsNull() {
		filter = new SimulationBitmapFilter(
				Constants.PROTANOPIA_SIMULATION_FILTER);

		assertNull(filter.applyTo(null));
	}

	@Test
	public void protFilterShouldFilterPixelCorrectly() {
		filter = new SimulationBitmapFilter(
				Constants.PROTANOPIA_SIMULATION_FILTER);
		
		Bitmap bmp = createYellowMockBitmap();
		filter.applyTo(bmp);
		verify(bmp, times(1)).setPixel(0, 0, -66028);
	}



	@Test
	public void deutFilterShouldFilterPixelCorrectly() {
		Bitmap bmp = createYellowMockBitmap();
		filter.applyTo(bmp);
		verify(bmp, times(1)).setPixel(0, 0, -197588);
	}
}
