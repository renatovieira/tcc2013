package br.usp.ime.tcc.activities.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ComponentUtilsTest {
	private static final int INVALID_ID = -1;
	private Activity activity;
	private ComponentUtils componentUtils;
	private boolean activated;
	private static int defaultButtonId = R.id.save_button;
	private static int defaultImageviewId = R.id.filtered_image;
	private static int defaultSeekbarId = R.id.intensity_bar;
	private static int defaultTextViewId = R.id.filter_title;
	private static int defaultSpinnerId = R.id.filter_type_spinner;

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

	private Button getDefaultButton(OnClickListener listener) {
		Button b = componentUtils.loadButton(defaultButtonId, listener);
		return b;
	}

	private Button getInvisibleDefaultButton(OnClickListener listener) {
		Button b = componentUtils.loadButton(defaultButtonId, listener);
		componentUtils.hideButton(defaultButtonId);
		return b;
	}

	private Button createButtonWithDefaultListener() {
		OnClickListener listener = createDefaultOnClickListener();

		Button b = getDefaultButton(listener);
		return b;
	}

	private SeekBar getDefaultSeekbar() {
		SeekBar bar = componentUtils.loadSeekBar(defaultSeekbarId,
				Constants.MAX_INTENSITY, Constants.PROGRESS);

		return bar;
	}

	private ImageView getDefaultImageView() {
		ImageView imageView = componentUtils.loadImageView(defaultImageviewId);
		return imageView;
	}

	private TextView getDefaultTextView() {
		TextView tv = componentUtils.loadTextView(defaultTextViewId);
		return tv;
	}

	private Spinner getDefaultSpinner() {
		Spinner sp = componentUtils.loadSpinner(defaultSpinnerId);
		sp.setSelection(0);
		return sp;
	}

	private void loadDefaultSpinner(Activity activity) {
		Spinner defaultSpinner = new Spinner(activity);
		defaultSpinner.setVisibility(View.GONE);
		when(activity.findViewById(defaultSpinnerId))
				.thenReturn(defaultSpinner);
	}

	private void loadDefaultTextView(Activity activity) {
		TextView defaultTextView = new TextView(activity);
		when(activity.findViewById(defaultTextViewId)).thenReturn(
				defaultTextView);
	}

	private void loadDefaultSeekBar(Activity activity) {
		SeekBar defaultSeekBar = new SeekBar(activity);
		when(activity.findViewById(defaultSeekbarId))
				.thenReturn(defaultSeekBar);
	}

	private void loadDefaultImageView(Activity activity) {
		ImageView defaultImageView = new ImageView(activity);
		defaultImageView.setImageBitmap(null);
		when(activity.findViewById(defaultImageviewId)).thenReturn(
				defaultImageView);
	}

	private void loadDefaultButton(Activity activity) {
		Button defaultButton = new Button(activity);
		when(activity.findViewById(defaultButtonId)).thenReturn(defaultButton);
	}

	private Activity buildActivityWithMockObjects() {
		Activity activity = spy(new Activity());

		loadDefaultButton(activity);
		loadDefaultImageView(activity);
		loadDefaultSeekBar(activity);
		loadDefaultTextView(activity);
		loadDefaultSpinner(activity);

		return activity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		activity = buildActivityWithMockObjects();

		componentUtils = new ComponentUtils(activity);

		activated = false;
	}

	@Test(expected = NullPointerException.class)
	public void buttonShouldThrowExceptionIfInvalidId() {
		componentUtils.loadButton(INVALID_ID, null);
	}

	@Test
	public void buttonShouldBeInvisible() {
		Button b = getInvisibleDefaultButton(null);

		assertEquals(View.GONE, b.getVisibility());
	}

	@Test
	public void buttonShouldCallRightListener() {
		Button b = createButtonWithDefaultListener();

		b.performClick();

		assertTrue(activated);
	}

	public void buttonShouldNotBeResponsiveWithoutListener() {
		Button b = getDefaultButton(null);

		assertEquals(false, buttonIsResponsive(b));
	}

	public void imageViewShouldBeNullIfInvalidId() {
		ImageView imageView = componentUtils.loadImageView(INVALID_ID);
		assertNull(imageView);
	}

	@Test
	public void imageViewShouldNotBeNull() {
		ImageView imageView = getDefaultImageView();

		assertNotNull(imageView);
	}

	@Test
	public void shouldReturnNullBitmapFromDefaultImageView() {
		ImageView imageView = getDefaultImageView();

		Bitmap bitmap = componentUtils.getBitmapFromImageView(imageView);
		assertNull(bitmap);
	}

	@Test
	public void shouldReturnRightBitmapFromFilledInImageView() {
		ImageView imageView = getDefaultImageView();
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
		SeekBar bar = getDefaultSeekbar();

		assertNotNull(bar);
	}

	@Test
	public void seekBarShouldBeVisibleAfterLoaded() {
		SeekBar bar = getDefaultSeekbar();
		assertEquals(View.VISIBLE, bar.getVisibility());
	}

	@Test
	public void seekbarMaximumShouldBeMaxIntensity() {
		SeekBar bar = getDefaultSeekbar();

		assertEquals(Constants.MAX_INTENSITY, bar.getMax());
	}

	@Test
	public void seekbarProgressShouldBeMaxProgress() {
		SeekBar bar = getDefaultSeekbar();

		assertEquals(Constants.PROGRESS, bar.getProgress());
	}

	@Test
	public void defaultTextViewTextShouldBeEmpty() {
		TextView tv = getDefaultTextView();
		assertNotNull(tv);
		assertEquals("", tv.getText());
	}

	@Test
	public void textViewTextShouldLoad() {
		String dummy = "dummy";
		TextView tv = componentUtils.loadTextViewWithText(defaultTextViewId,
				dummy);

		assertNotNull(tv);
		assertEquals(dummy, tv.getText());
	}

	@Test
	public void defaultSpinnerPositionShouldBeZero() {
		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		assertEquals(0, sp.getSelectedItemPosition());
	}

	@Test
	public void defaultSpinnerShouldBeHidden() {
		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		assertEquals(View.GONE, sp.getVisibility());
	}

	@Test
	public void spinnerShouldBeMadeVisible() {
		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		assertEquals(View.GONE, sp.getVisibility());

		componentUtils.showSpinner(defaultSpinnerId);
		assertEquals(View.VISIBLE, sp.getVisibility());

	}

	@Test
	public void getSelectedItemPositionShouldReturnRightPosition() {
		int rightPosition = 1;

		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		sp.setSelection(rightPosition);
		assertEquals(rightPosition,
				componentUtils.getSpinnerPosition(defaultSpinnerId));
	}
}
