package br.usp.ime.tcc.activities.filter;

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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.tcc.activities.BitmapFilterActivity;
import br.usp.ime.tcc.activities.ContinousFilterActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;

@RunWith(RobolectricTestRunner.class)
public class VisocorFilterActivityTest {
	private VisocorFilterActivity filterActivity;
	private ButtonActionsTest bat;

	private String getFilterTitleFromTextView() {
		TextView tv = (TextView) filterActivity.findViewById(R.id.filter_title);
		return tv.getText().toString();
	}
	
	private int getVisibiltyFromSpinner() {
		Spinner sp = (Spinner) filterActivity.findViewById(R.id.filter_type_spinner);
		return sp.getVisibility();
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		filterActivity = Robolectric.buildActivity(VisocorFilterActivity.class).create().get();

		bat = new ButtonActionsTest(filterActivity);
	}
	
	@Test
	public void liveModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.liveModeButton));
	}
	
	@Test
	public void cameraModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.liveModeButton));
	}
	
	@Test
	public void galleryModeButtonShouldNotBeNull() {
		assertNotNull(filterActivity.findViewById(R.id.galleryModeButton));
	}

	@Test
	public void liveModeButtonShouldBeLoadedAndWorking() {
		assertTrue(bat.getButtonAndClickOnIt(R.id.liveModeButton));
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

		assertEquals(filterActivity.getString(R.string.visocor_filter), filterTitle);
	}
	
	@Test
	public void spinnerShouldBeHidden() {
		assertEquals(View.GONE, getVisibiltyFromSpinner());
	}

	@Test
	public void pressingCameraModeButtonShouldStartCameraActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.cameraModeButton);

		assertNotNull(startedIntent);
		assertEquals(MediaStore.ACTION_IMAGE_CAPTURE, startedIntent.getAction());
	}

	@Test
	public void pressingGalleryModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.galleryModeButton);

		assertNotNull(startedIntent);
		assertEquals(Intent.ACTION_PICK, startedIntent.getAction());
	}

	@Test
	public void pressingLiveModeButtonShouldStartGalleryActivity() {
		Intent startedIntent = bat
				.getButtonAndGetStartedIntentAfterClick(R.id.liveModeButton);

		assertNotNull(startedIntent);
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertEquals(shadowIntent.getComponent().getClassName(),
				ContinousFilterActivity.class.getName());
	}

	@Test
	public void shouldCallFilteredImageActivityAfterGalleryResult() {
		ShadowIntent shadowIntent = bat
				.getButtonClickAndGetIntentAfterResult(R.id.galleryModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				BitmapFilterActivity.class.getName());
	}

	@Test
	public void shouldCallFilteredImageActivityAfterCameraResult() {
		ShadowIntent shadowIntent = bat
				.getButtonClickAndGetIntentAfterResult(R.id.cameraModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				BitmapFilterActivity.class.getName());
	}
}
