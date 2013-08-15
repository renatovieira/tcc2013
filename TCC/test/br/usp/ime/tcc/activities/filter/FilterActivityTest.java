package br.usp.ime.tcc.activities.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.tcc.activities.BitmapFilterActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTestHelper;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class FilterActivityTest {
	protected FilterActivity filterActivity;
	protected ButtonActionsTestHelper bat;
	protected String title;

	protected String getFilterTitleFromTextView() {
		TextView tv = (TextView) filterActivity.findViewById(R.id.filter_title);
		return tv.getText().toString();
	}
	
	protected int getVisibiltyFromSpinner() {
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
		assertTrue(bat.getButtonAndClickOnIt(R.id.cameraModeButton));
	}

	@Test
	public void galleryModeButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.galleryModeButton));
	}

	@Test
	public void shouldHaveRightTitle() {
		String filterTitle = getFilterTitleFromTextView();

		assertEquals(title, filterTitle);
	}
	
	@Test
	public void pressingCameraModeButtonShouldStartCameraActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.cameraModeButton);

		assertNotNull(startedIntent);
		assertEquals(MediaStore.ACTION_IMAGE_CAPTURE, startedIntent.getAction());
	}

	@Test
	public void pressingGalleryModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat
				.getStartedIntentAfterClickOnButton(R.id.galleryModeButton);

		assertNotNull(startedIntent);
		assertEquals(Intent.ACTION_PICK, startedIntent.getAction());
	}

	@Test
	public void shouldCallBitmapFilterActivityAfterGalleryResult() {
		ShadowIntent shadowIntent = bat
				.getIntentAfterResultOfButtonClick(R.id.galleryModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				BitmapFilterActivity.class.getName());
	}

	@Test
	public void shouldCallBitmapFilterActivityAfterCameraResult() {
		ShadowIntent shadowIntent = bat
				.getIntentAfterResultOfButtonClick(R.id.cameraModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				BitmapFilterActivity.class.getName());
	}
}
