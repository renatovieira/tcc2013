package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import android.provider.MediaStore;
import br.usp.ime.tcc.activities.FilterActivity;
import br.usp.ime.tcc.activities.FilteredImageActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;

@RunWith(RobolectricTestRunner.class)
public class FilterActivityTest {
	private FilterActivity filterActivity;
	private ButtonActionsTest bat;

	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric.buildActivity(FilterActivity.class)
				.create().get();
		
		bat = new ButtonActionsTest(filterActivity);
	}

	@Test
	public void pressingCameraModeButtonShouldStartCameraActivity() {
		Intent startedIntent = bat.createButtonAndGetStartedIntentAfterClick(R.id.cameraModeButton);
		
		assertNotNull(startedIntent);
		assertEquals(MediaStore.ACTION_IMAGE_CAPTURE, startedIntent.getAction());
	}

	@Test
	public void pressingGalleryModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat.createButtonAndGetStartedIntentAfterClick(R.id.galleryModeButton);
		
		assertNotNull(startedIntent);
		assertEquals(Intent.ACTION_PICK, startedIntent.getAction());
	}

	@Test
	public void shouldCallFilteredImageActivityAfterGalleryResult() {
		ShadowIntent shadowIntent = bat.createButtonClickAndGetIntentAfterResult(R.id.galleryModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				FilteredImageActivity.class.getName());
	}

	@Test
	public void shouldCallFilteredImageActivityAfterCameraResult() {
		ShadowIntent shadowIntent = bat.createButtonClickAndGetIntentAfterResult(R.id.cameraModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				FilteredImageActivity.class.getName());
	}
}
