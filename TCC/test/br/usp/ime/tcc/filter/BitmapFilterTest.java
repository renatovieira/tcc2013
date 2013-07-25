package br.usp.ime.tcc.filter;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class BitmapFilterTest {
	// Tests
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void aFilterShouldReturnNullIfBitmapIsNull() {
		BitmapFilter filter = new BitmapFilter(Constants.MAX_INTENSITY);

		assertNull(filter.applyTo(null));
	}
	
	@Test
	public void anyFilterShouldReturnNullIfBitmapIsNull() {
		BitmapFilter filter = new BitmapFilter(0);

		assertNull(filter.applyTo(null));
	}
}
