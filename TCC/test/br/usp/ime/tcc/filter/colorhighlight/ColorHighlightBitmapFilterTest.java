package br.usp.ime.tcc.filter.colorhighlight;

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
public class ColorHighlightBitmapFilterTest {
	private ColorHighlightBitmapFilter filter;

	private Bitmap createMockBitmap() {
		Bitmap bmp = mock(Bitmap.class);

		when(bmp.getWidth()).thenReturn(2);
		when(bmp.getHeight()).thenReturn(2);

		when(bmp.copy(any(Config.class), anyBoolean())).thenReturn(bmp);

		when(bmp.getPixel(anyInt(), anyInt())).thenReturn(Color.BLACK)
				.thenReturn(Color.RED).thenReturn(Color.GREEN)
				.thenReturn(Color.BLUE);
		return bmp;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		filter = new ColorHighlightBitmapFilter(0, 0, 0,
				Constants.DEFAULT_TOLERANCE, Constants.DEFAULT_TOLERANCE,
				Constants.DEFAULT_TOLERANCE);
	}

	@Test
	public void aFilterShouldReturnNullIfBitmapIsNull() {
		assertNull(filter.applyTo(null));
	}

	@Test
	public void shouldFilterBitmap() {
		Bitmap bmp = createMockBitmap();

		filter.applyTo(bmp);

		verify(bmp, times(3)).setPixel(anyInt(), anyInt(), anyInt());
	}
}
