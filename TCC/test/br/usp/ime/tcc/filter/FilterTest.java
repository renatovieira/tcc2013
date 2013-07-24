package br.usp.ime.tcc.filter;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FilterTest {
	// Tests
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void aFilterShouldReturnNullIfBitmapIsNull() {
		Filter filter = new Filter(-1f);

		assertNull(filter.applyTo(null));
	}
	
	@Test
	public void anyFilterShouldReturnNullIfBitmapIsNull() {
		Filter filter = new Filter(1f);

		assertNull(filter.applyTo(null));
	}
}
