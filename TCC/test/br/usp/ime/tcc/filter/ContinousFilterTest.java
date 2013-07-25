package br.usp.ime.tcc.filter;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.usp.ime.tcc.utils.Constants;

@Ignore
public class ContinousFilterTest {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void aFiltershouldReturnNullIfNullFrame() {
		ContinousFilter filter = new ContinousFilter(0);

		assertNull(filter.applyFilterTo(null));
	}

	@Test
	public void anyFiltershouldReturnNullIfNullFrame() {
		ContinousFilter filter = new ContinousFilter(Constants.MAX_INTENSITY);

		assertNull(filter.applyFilterTo(null));
	}
}
