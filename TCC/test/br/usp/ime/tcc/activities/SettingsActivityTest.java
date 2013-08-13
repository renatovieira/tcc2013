package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;

@RunWith(RobolectricTestRunner.class)
public class SettingsActivityTest {
	private SettingsActivity activity;
	private ButtonActionsTest bat;

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(SettingsActivity.class).create().get();

		bat = new ButtonActionsTest(activity);
	}
}
