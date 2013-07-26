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
public class BitmapFilterActivityTest {
	private BitmapFilterActivity bitmapFilterActivity;

	private Bitmap getBitmapFromImageView(ImageView image) {
		return ((BitmapDrawable)image.getDrawable()).getBitmap();
	}

	private void callTheSeekBarListener(SeekBar bar) {
		bar.setProgress(0);
	}
	
	private BitmapFilterActivity startWithExtras() {
		bitmapFilterActivity = new BitmapFilterActivity();
		Intent intent = new Intent();
		intent.putExtra(Constants.FILTER_TYPE, Constants.VISOCOR_FILTER);
		intent.putExtra(Constants.IMAGE_PATH, "dummy");
		intent.putExtra(Constants.IMAGE_ORIENTATION, 0);
		
		bitmapFilterActivity.setIntent(intent);
		bitmapFilterActivity.onCreate(null);

		return bitmapFilterActivity;
	}
	
	// Tests
	@Before
	public void setUp() throws Exception {
		bitmapFilterActivity = startWithExtras();
	}
	
	@Test
	public void backButtonShouldFinishActivity() {
		bitmapFilterActivity.onBackPressed();
		
		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
	    assertTrue(sa.isFinishing());
	}

	@Test
	public void extrasShouldNotBeNull () {
		assertNotNull(bitmapFilterActivity.getIntent().getExtras());
	}
	
	@Test
	public void discardButtonShouldReturnToFilterActivity() {
		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);
		bat.getButtonAndClickOnIt(R.id.discard_button);
		
		ShadowActivity sa = Robolectric.shadowOf(bitmapFilterActivity);
	    assertTrue(sa.isFinishing());
	}
	
	@Test
	public void saveButtonShouldReturnErrorToast() {
		ButtonActionsTest bat = new ButtonActionsTest(bitmapFilterActivity);
		bat.getButtonAndClickOnIt(R.id.save_button);
		
		ShadowHandler.idleMainLooper(); 
		assertEquals(bitmapFilterActivity.getString(R.string.try_again), ShadowToast.getTextOfLatestToast()); 
	}
	
	@Test
	public void shouldCallSeekBarListenerWhenProgressChanged() {
		ImageView image = (ImageView) bitmapFilterActivity.findViewById(R.id.filtered_image);

		SeekBar bar = (SeekBar) bitmapFilterActivity.findViewById(R.id.intensity_bar);
		assertNotNull(bar);
		
		Bitmap bmp =  getBitmapFromImageView(image);
		
		callTheSeekBarListener(bar);
		
		Bitmap newBmp = getBitmapFromImageView(image);
		
		assertEquals(false, bmp.equals(newBmp));
	}
}
