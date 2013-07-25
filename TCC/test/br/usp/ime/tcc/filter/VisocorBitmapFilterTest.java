package br.usp.ime.tcc.filter;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class VisocorBitmapFilterTest {
	// Tests
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void aFilterShouldReturnNullIfBitmapIsNull() {
		VisocorBitmapFilter filter = new VisocorBitmapFilter(Constants.MAX_INTENSITY);

		assertNull(filter.applyTo(null));
	}
	
	@Test
	public void anyFilterShouldReturnNullIfBitmapIsNull() {
		VisocorBitmapFilter filter = new VisocorBitmapFilter(0f);

		assertNull(filter.applyTo(null));
	}
}
