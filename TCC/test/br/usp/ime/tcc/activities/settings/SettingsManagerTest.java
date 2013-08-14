package br.usp.ime.tcc.activities.settings;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class SettingsManagerTest {
	private static final int DEFAULT_VALUE = 47;
	private SettingsManager settingsManager;

	@Before
	public void setUp() throws Exception {
		settingsManager = new SettingsManager(new Activity());
	}

	@Test
	public void shouldLoadDefaultIntensityCorrectly() {
		assertEquals(Constants.INTENSITY,
				settingsManager.loadDefaultIntensity());
	}

	@Test
	public void shouldLoadDefaultColorSimulationIdCorrectly() {
		assertEquals(0, settingsManager.loadDefaultColorSimulationId());
	}

	@Test
	public void shouldLoadDefaultRedToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultRedTolerance());
	}

	@Test
	public void shouldLoadDefaultGreenToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultGreenTolerance());
	}

	@Test
	public void shouldLoadDefaultBlueToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultBlueTolerance());
	}

	@Test
	public void shouldLoadDefaultRedCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultRed());
	}

	@Test
	public void shouldLoadDefaultGreenCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultGreen());
	}

	@Test
	public void shouldLoadDefaultBlueCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultBlue());
	}

	// Can't test because Android can't save the values

	@Ignore
	@Test
	public void shouldSaveDefaultIntensityCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultIntensity());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultColorSimulationIdCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE,
				settingsManager.loadDefaultColorSimulationId());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultRedToleranceCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultRedTolerance());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultGreenToleranceCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultGreenTolerance());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultBlueToleranceCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultBlueTolerance());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultRedCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultRed());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultGreenCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultGreen());
	}

	@Ignore
	@Test
	public void shouldSaveDefaultBlueCorrectly() {
		settingsManager.saveDefaultIntensity(DEFAULT_VALUE);

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultBlue());
	}
}
