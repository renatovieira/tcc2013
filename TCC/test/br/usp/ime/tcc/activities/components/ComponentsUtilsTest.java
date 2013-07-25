package br.usp.ime.tcc.activities.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.usp.ime.tcc.activities.FilteredImageActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ComponentsUtilsTest {
	private static final int INVALID_ID = -1;
	private Activity activity;
	private ComponentUtils componentUtils;
	private boolean activated;
	private static int defaultButtonId = R.id.save_button;
	private static int defaultImageviewId = R.id.filtered_image;
	private static int defaultSeekbarId = R.id.intensity_bar;

	private boolean buttonIsResponsive(Button button) {
		return button.performClick();
	}

	private OnClickListener createDefaultOnClickListener() {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activated = true;
			}
		};

		return listener;
	}

	private Button loadDefaultButton(OnClickListener listener) {
		Button b = componentUtils.loadButton(defaultButtonId, listener);
		return b;
	}

	private Button createButtonWithDefaultListener() {
		OnClickListener listener = createDefaultOnClickListener();

		Button b = loadDefaultButton(listener);
		return b;
	}

	private SeekBar loadDefaultSeekbar() {
		SeekBar bar = componentUtils.loadSeekBar(defaultSeekbarId,
				Constants.MAX_INTENSITY, Constants.PROGRESS);

		return bar;
	}
	
	private ImageView loadDefaultImageView() {
		ImageView imageView = componentUtils.loadImageView(defaultImageviewId);
		return imageView;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(FilteredImageActivity.class)
				.create().get();

		componentUtils = new ComponentUtils(activity);

		activated = false;
	}

	@Test(expected = NullPointerException.class)
	public void buttonShouldThrowExceptionIfInvalidId() {
		componentUtils.loadButton(INVALID_ID, null);
	}

	public void buttonShouldNotBeResponsiveWithoutListener() {
		Button b = loadDefaultButton(null);

		assertEquals(false, buttonIsResponsive(b));
	}

	@Test
	public void buttonShouldCallRightListener() {
		Button b = createButtonWithDefaultListener();

		b.performClick();

		assertTrue(activated);
	}

	public void imageViewShouldBeNullIfInvalidId() {
		ImageView imageView = componentUtils.loadImageView(INVALID_ID);
		assertNull(imageView);
	}

	@Test
	public void imageViewShouldNotBeNull() {
		ImageView imageView = loadDefaultImageView();

		assertNotNull(imageView);
	}

	@Test
	public void shouldReturnNullBitmapFromDefaultImageView() {
		ImageView imageView = loadDefaultImageView();

		Bitmap bitmap = componentUtils.getBitmapFromImageView(imageView);
		assertNull(bitmap);
	}

	@Test
	public void shouldReturnRightBitmapFromFilledInImageView() {
		ImageView imageView = loadDefaultImageView();
		Bitmap bitmapTest = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.ic_launcher);

		componentUtils.fillIn(imageView, bitmapTest);

		Bitmap returnedBitmap = componentUtils
				.getBitmapFromImageView(imageView);

		assertEquals(bitmapTest, returnedBitmap);
	}

	@Test(expected = NullPointerException.class)
	public void seekbarShouldThrowExceptionIfInvalidId() {
		componentUtils.loadSeekBar(INVALID_ID, Constants.MAX_INTENSITY,
				Constants.PROGRESS);
	}

	@Test
	public void seekbarShouldNotBeNull() {
		SeekBar bar = loadDefaultSeekbar();

		assertNotNull(bar);
	}
	
	@Test
	public void seekBarShouldBeInvisibleAfterHidden() {
		componentUtils.hideSeekBar(defaultSeekbarId);
		SeekBar bar = loadDefaultSeekbar();
		assertFalse(bar.isShown());
	}

	@Test
	public void seekbarMaximumShouldBeMaxIntensity() {
		SeekBar bar = loadDefaultSeekbar();

		assertEquals(Constants.MAX_INTENSITY, bar.getMax());
	}

	@Test
	public void seekbarProgressShouldBeMaxProgress() {
		SeekBar bar = loadDefaultSeekbar();

		assertEquals(Constants.PROGRESS, bar.getProgress());
	}
}
