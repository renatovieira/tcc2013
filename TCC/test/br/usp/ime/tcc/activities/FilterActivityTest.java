package br.usp.ime.tcc.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class FilterActivityTest {
	private FilterActivity filterActivity;
	private ButtonActionsTest bat;

	private FilterActivity startWithFilterTypeOnExtras(int filterType) {
		filterActivity = new FilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE, filterType);
		
		filterActivity.setIntent(intent);
		filterActivity.onCreate(null);

		return filterActivity;
	}
	
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
		filterActivity = startWithFilterTypeOnExtras(Constants.VISOCOR_FILTER);

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
	public void defaultFilterTitleShouldBeVisocors() {
		String filterTitle = getFilterTitleFromTextView();

		assertEquals(filterActivity.getString(R.string.visocor_filter), filterTitle);
	}
	
	@Test
	public void spinnerShoudBeHiddenInVisocor() {
		assertEquals(View.INVISIBLE, getVisibiltyFromSpinner());
	}

	@Test
	public void simulationFilterTitleShouldBeSimulation() {
		filterActivity = startWithFilterTypeOnExtras(Constants.SIMULATION_FILTER);
		
		String filterTitle = getFilterTitleFromTextView();

		assertEquals(filterActivity.getString(R.string.simulation_filter), filterTitle);
	}
	
	@Test
	public void spinnerShoudBeVisibleInSimulation() {
		filterActivity = startWithFilterTypeOnExtras(Constants.SIMULATION_FILTER);
		
		assertEquals(View.VISIBLE, getVisibiltyFromSpinner());
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
		filterActivity = startWithFilterTypeOnExtras(Constants.SIMULATION_FILTER);
		bat = new ButtonActionsTest(filterActivity);
		
		ShadowIntent shadowIntent = bat
				.getButtonClickAndGetIntentAfterResult(R.id.cameraModeButton);
		assertEquals(shadowIntent.getComponent().getClassName(),
				BitmapFilterActivity.class.getName());
	}
}
