package br.usp.ime.tcc.activities.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.Spinner;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class FilterActivityTest {
	protected FilterActivity filterActivity;
	protected ButtonActionsTestHelper bat;
	protected String title;
	
	protected int getVisibilityFromLinearLayout(int llId) {
		LinearLayout ll = (LinearLayout) filterActivity.findViewById(llId);
		return ll.getVisibility();
	}

	protected int getVisibilityFromSpinner() {
		Spinner sp = (Spinner) filterActivity.findViewById(R.id.filter_type_spinner);
		return sp.getVisibility();
	}
	
	// Tests

	@Test
	public void cameraModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.liveModeButton));
	}
	
	@Test
	public void galleryModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.galleryModeButton));
	}

	@Test
	public void cameraModeButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getImageButtonAndClickOnIt(R.id.cameraModeButton));
	}

	@Test
	public void galleryModeButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getImageButtonAndClickOnIt(R.id.galleryModeButton));
	}

	@Test
	public void pressingCameraModeButtonShouldStartCameraActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnImageButton(R.id.cameraModeButton);

		assertNotNull(startedIntent);
		assertEquals(MediaStore.ACTION_IMAGE_CAPTURE, startedIntent.getAction());
	}

	@Test
	public void pressingGalleryModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnImageButton(R.id.galleryModeButton);

		assertNotNull(startedIntent);
		assertEquals(Intent.ACTION_PICK, startedIntent.getAction());
	}
}
