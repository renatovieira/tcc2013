package br.usp.ime.tcc.filter.colorhighlight;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ColorHighlightParametersCalculatorTest {
	private static final int FILTER_RED = 63;
	private static final int FILTER_GREEN = 127;
	private static final int FILTER_BLUE = 255;
	private static final int DEFAULT_TOLERANCE = 13;

	private ColorHighlightParametersCalculator colorHighlightParametersCalculator;

	// Tests
	@Before
	public void setUp() throws Exception {
		colorHighlightParametersCalculator = new ColorHighlightParametersCalculator(
				FILTER_RED, FILTER_GREEN, FILTER_BLUE, DEFAULT_TOLERANCE,
				DEFAULT_TOLERANCE, DEFAULT_TOLERANCE);
	}

	@Test
	public void shouldHighlightSamePixel() {
		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED, FILTER_GREEN, FILTER_BLUE));
	}

	@Test
	public void shouldHighlightCloseColorPixel() {
		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED + 1, FILTER_GREEN + 1, FILTER_BLUE + 1));

		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED - 1, FILTER_GREEN - 1, FILTER_BLUE - 1));
	}

	@Test
	public void shouldHighlightBorderlineColorPixel() {
		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED + DEFAULT_TOLERANCE, FILTER_GREEN,
						FILTER_BLUE));

		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED, FILTER_GREEN - DEFAULT_TOLERANCE,
						FILTER_BLUE));

	}

	@Test
	public void shouldNotHighlightSlightlyMoreThanBorderlineColorPixel() {
		assertEquals(false,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED + DEFAULT_TOLERANCE, FILTER_GREEN
								+ DEFAULT_TOLERANCE + 1, FILTER_BLUE
								+ DEFAULT_TOLERANCE + 1));

		assertEquals(false,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED - DEFAULT_TOLERANCE, FILTER_GREEN
								- DEFAULT_TOLERANCE, FILTER_BLUE
								- DEFAULT_TOLERANCE - 1));
	}

	@Test
	public void shouldNotHighlightFarColorPixel() {
		assertEquals(false,
				colorHighlightParametersCalculator
						.pixelShouldBeHighlighted(FILTER_RED + 127,
								FILTER_GREEN + 127, FILTER_BLUE + 127));

		assertEquals(false,
				colorHighlightParametersCalculator
						.pixelShouldBeHighlighted(FILTER_RED - 127,
								FILTER_GREEN - 127, FILTER_BLUE - 127));
	}

	@Test
	public void zeroToleranceFilterShouldHighlightSamePixel() {
		colorHighlightParametersCalculator = new ColorHighlightParametersCalculator(
				FILTER_RED, FILTER_GREEN, FILTER_BLUE, 0, 0, 0);

		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED, FILTER_GREEN, FILTER_BLUE));
	}
	
	@Test
	public void zeroToleranceFilterShouldNotHighlightOneDifferencePixel() {
		colorHighlightParametersCalculator = new ColorHighlightParametersCalculator(
				FILTER_RED, FILTER_GREEN, FILTER_BLUE, 0, 0, 0);

		assertEquals(true,
				colorHighlightParametersCalculator.pixelShouldBeHighlighted(
						FILTER_RED, FILTER_GREEN, FILTER_BLUE + 1));
	}
}
