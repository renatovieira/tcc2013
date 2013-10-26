package br.usp.ime.tcc.filter.simulation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.graphics.Color;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SimulationFilterValuesCalculatorTest {
	private SimulationFilterValuesCalculator calculator;

	// Tests
	@Test
	public void shouldCalculateRightValuesForProtanopia() {
		calculator = new SimulationFilterValuesCalculator(
				Constants.PROTANOPIA_SIMULATION_FILTER);

		assertEquals(-15461356, calculator.getValueForPixel(Color.BLACK));
		assertEquals(-10526948, calculator.getValueForPixel(Color.RED));
		assertEquals(-66028, calculator.getValueForPixel(Color.YELLOW));
	}

	@Test
	public void shouldCalculateRightValuesForDeuteranopia() {
		calculator = new SimulationFilterValuesCalculator(
				Constants.DEUTERANOPIA_SIMULATION_FILTER);

		assertEquals(-13882324, calculator.getValueForPixel(Color.BLACK));
		assertEquals(-7105792, calculator.getValueForPixel(Color.RED));
		assertEquals(-197588, calculator.getValueForPixel(Color.YELLOW));
	}
}
