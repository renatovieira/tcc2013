package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import android.content.Intent;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class FilteredImageActivityTest {
	private FilteredImageActivity filteredImageActivity;
	private ButtonActionsTest bat;

	private FilteredImageActivity startWithImagePathOnExtras() {
		filteredImageActivity = Robolectric
				.buildActivity(FilteredImageActivity.class).create().get();

		Intent intent = filteredImageActivity.getIntent();
		intent.putExtra(Constants.IMAGE_PATH, "dummy");
		intent.putExtra(Constants.IMAGE_ORIENTATION, 0);
		
		return Robolectric.buildActivity(FilteredImageActivity.class).withIntent(intent).create().get();
	}
	
	// Tests
	@Before
	public void setUp() throws Exception {
		filteredImageActivity = startWithImagePathOnExtras();
		bat = new ButtonActionsTest(filteredImageActivity);
	}
	
	@Test
	public void backButtonShouldGoBackToFilterActivity() {
		filteredImageActivity.onBackPressed();
		
		ShadowActivity sa = Robolectric.shadowOf(filteredImageActivity);
	    assertTrue(sa.isFinishing());
	}

	@Test
	public void extrasShouldNotBeNull () {
		assertNotNull(filteredImageActivity.getIntent().getExtras());
	}
	
	@Test
	public void discardButtonShouldReturnToFilterActivity() {
		bat.createButtonAndClickOnIt(R.id.discard_button);
		
		ShadowActivity sa = Robolectric.shadowOf(filteredImageActivity);
	    assertTrue(sa.isFinishing());
	}
	
	@Test
	public void saveButtonShouldReturnErrorToast() {
		bat.createButtonAndClickOnIt(R.id.save_button);
		
		ShadowHandler.idleMainLooper(); 
		assertEquals(filteredImageActivity.getString(R.string.try_again), ShadowToast.getTextOfLatestToast()); 
	}
}
