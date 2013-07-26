package br.usp.ime.tcc.filter.visocor;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.usp.ime.tcc.utils.Constants;

@Ignore
public class VisocorContinousFilterTest {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void aFiltershouldReturnNullIfNullFrame() {
		VisocorContinousFilter filter = new VisocorContinousFilter(0);

		assertNull(filter.applyFilterTo(null));
	}

	@Test
	public void anyFiltershouldReturnNullIfNullFrame() {
		VisocorContinousFilter filter = new VisocorContinousFilter(Constants.MAX_INTENSITY);

		assertNull(filter.applyFilterTo(null));
	}
}
