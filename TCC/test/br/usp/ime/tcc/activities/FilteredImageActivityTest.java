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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.components.ButtonActionsTest;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class FilteredImageActivityTest {
	private FilteredImageActivity filteredImageActivity;

	private Bitmap getBitmapFromImageView(ImageView image) {
		return ((BitmapDrawable)image.getDrawable()).getBitmap();
	}

	private void callTheSeekBarListener(SeekBar bar) {
		bar.setProgress(0);
	}
	
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
	}
	
	@Test
	public void backButtonShouldFinishActivity() {
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
		ButtonActionsTest bat = new ButtonActionsTest(filteredImageActivity);
		bat.createButtonAndClickOnIt(R.id.discard_button);
		
		ShadowActivity sa = Robolectric.shadowOf(filteredImageActivity);
	    assertTrue(sa.isFinishing());
	}
	
	@Test
	public void saveButtonShouldReturnErrorToast() {
		ButtonActionsTest bat = new ButtonActionsTest(filteredImageActivity);
		bat.createButtonAndClickOnIt(R.id.save_button);
		
		ShadowHandler.idleMainLooper(); 
		assertEquals(filteredImageActivity.getString(R.string.try_again), ShadowToast.getTextOfLatestToast()); 
	}
	
	@Test
	public void shouldCallSeekBarListenerWhenProgressChanged() {
		ImageView image = (ImageView) filteredImageActivity.findViewById(R.id.filtered_image);

		SeekBar bar = (SeekBar) filteredImageActivity.findViewById(R.id.intensity_bar);
		assertNotNull(bar);
		
		Bitmap bmp =  getBitmapFromImageView(image);
		
		callTheSeekBarListener(bar);
		
		Bitmap newBmp = getBitmapFromImageView(image);
		
		assertEquals(false, bmp.equals(newBmp));
	}
}
