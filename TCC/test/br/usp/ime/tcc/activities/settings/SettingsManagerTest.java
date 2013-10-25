package br.usp.ime.tcc.activities.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
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
	
	@After
	public void tearDown() {
		settingsManager.settingsClear();
	}

	@Test
	public void shouldLoadAndSaveDefaultIntensityCorrectly() {
		assertEquals(Constants.INTENSITY,
				settingsManager.loadDefaultIntensity());
		
		assertTrue(settingsManager.saveDefaultIntensity(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultIntensity());
	}

	@Test
	public void shouldLoadAndSaveDefaultColorSimulationIdCorrectly() {
		assertEquals(0, settingsManager.loadDefaultColorSimulationId());
		
		assertTrue(settingsManager.saveDefaultColorSimulationId(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE,
				settingsManager.loadDefaultColorSimulationId());
	}

	@Test
	public void shouldLoadAndSaveDefaultRedToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultRedTolerance());
		
		assertTrue(settingsManager.saveDefaultRedTolerance(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultRedTolerance());
	}

	@Test
	public void shouldLoadAndSaveDefaultGreenToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultGreenTolerance());
		
		assertTrue(settingsManager.saveDefaultGreenTolerance(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultGreenTolerance());
	}

	@Test
	public void shouldLoadAndSaveDefaultBlueToleranceCorrectly() {
		assertEquals(Constants.DEFAULT_TOLERANCE,
				settingsManager.loadDefaultBlueTolerance());
		
		assertTrue(settingsManager.saveDefaultBlueTolerance(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultBlueTolerance());
	}

	@Test
	public void shouldLoadAndSaveDefaultRedCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultRed());
		
		assertTrue(settingsManager.saveDefaultRed(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultRed());
	}

	@Test
	public void shouldLoadAndSaveDefaultGreenCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultGreen());
		
		assertTrue(settingsManager.saveDefaultGreen(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultGreen());
	}

	@Test
	public void shouldLoadAndSaveDefaultBlueCorrectly() {
		assertEquals(Constants.MAX_COLOR_VALUE,
				settingsManager.loadDefaultBlue());
		
		assertTrue(settingsManager.saveDefaultBlue(DEFAULT_VALUE));

		assertEquals(DEFAULT_VALUE, settingsManager.loadDefaultBlue());
	}
	
	@Test
	public void shouldLoadAndSaveSimpleModeCorrectly() {
		assertEquals(Constants.SIMPLE_MODE,
				settingsManager.loadSimpleMode());
		
		assertTrue(settingsManager.saveSimpleMode(!Constants.SIMPLE_MODE));

		assertEquals(!Constants.SIMPLE_MODE, settingsManager.loadSimpleMode());
	}
}
