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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private boolean checked;
	private static int defaultCheckBoxId = 47;
	private static int defaultLinearLayout = R.id.filter_type_spinner_ll;
	private static int defaultButtonId = R.id.visocor_button;
	private static int defaultImageviewId = R.id.filtered_image;
	private static int defaultSeekbarId = R.id.intensity_bar;
	private static int defaultTextViewId = R.id.textView1;
	private static int defaultSpinnerId = R.id.filter_type_spinner;
	private static int defaultEditTextId = R.id.blue_tolerance;
	private static int defaultImageButtonId = R.id.default_color_picker_button;

	private boolean buttonIsResponsive(Button button) {
		return button.performClick();
	}

	private OnClickListener createDefaultOnClickListener() {
		activated = false;

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
				Constants.MAX_INTENSITY, Constants.INTENSITY);

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

	private ImageButton getDefaultImageButton(OnClickListener listener) {
		ImageButton ib = componentUtils.loadImageButton(defaultImageButtonId,
				listener);
		return ib;
	}
	
	private LinearLayout getDefaultLinearLayout() {
		LinearLayout ll = componentUtils.loadLinearLayout(defaultLinearLayout);
		
		return ll;
	}
	
	private CheckBox getDefaultCheckBox() {
		CheckBox checkBox = componentUtils.loadCheckBox(defaultCheckBoxId, true);
		return checkBox;
	}

	private ImageButton createImageButtonWithDefaultListener() {
		OnClickListener listener = createDefaultOnClickListener();

		ImageButton ib = getDefaultImageButton(listener);
		return ib;
	}

	private boolean imageButtonIsResponsive(ImageButton ib) {
		return ib.performClick();

	}

	private void loadDefaultSpinner() {
		Spinner defaultSpinner = new Spinner(activity);
		when(activity.findViewById(defaultSpinnerId))
				.thenReturn(defaultSpinner);
	}

	private void loadDefaultTextView() {
		TextView defaultTextView = new TextView(activity);
		when(activity.findViewById(defaultTextViewId)).thenReturn(
				defaultTextView);
	}

	private void loadDefaultSeekBar() {
		SeekBar defaultSeekBar = new SeekBar(activity);
		when(activity.findViewById(defaultSeekbarId))
				.thenReturn(defaultSeekBar);
	}

	private void loadDefaultImageView() {
		ImageView defaultImageView = new ImageView(activity);
		defaultImageView.setImageBitmap(null);
		when(activity.findViewById(defaultImageviewId)).thenReturn(
				defaultImageView);
	}

	private void loadDefaultButton() {
		Button defaultButton = new Button(activity);
		when(activity.findViewById(defaultButtonId)).thenReturn(defaultButton);
	}

	private void loadDefaultEditText() {
		EditText et = new EditText(activity);
		when(activity.findViewById(defaultEditTextId)).thenReturn(et);
	}

	private void loadDefaultImageButton() {
		ImageButton ib = new ImageButton(activity);
		when(activity.findViewById(defaultImageButtonId)).thenReturn(ib);
	}
	
	private void loadDefaultLinearLayout() {
		LinearLayout ll = new LinearLayout(activity);
		ll.setVisibility(View.GONE);
		when(activity.findViewById(defaultLinearLayout)).thenReturn(ll);
	}
	
	private void loadDefaultCheckBox() {
		CheckBox checkBox = new CheckBox(activity);
		checkBox.setChecked(checked);
		when(activity.findViewById(defaultCheckBoxId )).thenReturn(checkBox);
	}

	private Activity buildActivityWithMockObjects() {
		activity = spy(new Activity());

		loadDefaultButton();
		loadDefaultImageView();
		loadDefaultSeekBar();
		loadDefaultTextView();
		loadDefaultSpinner();
		loadDefaultEditText();
		loadDefaultImageButton();
		loadDefaultLinearLayout();
		loadDefaultCheckBox();

		return activity;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		checked = true;
		
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

	@Test
	public void buttonShouldNotBeResponsiveWithoutListener() {
		Button b = getDefaultButton(null);

		assertEquals(false, buttonIsResponsive(b));
	}

	@Test
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

		Bitmap bitmap = componentUtils.getBitmap(imageView);
		assertNull(bitmap);
	}

	@Test
	public void shouldReturnRightBitmapFromFilledInImageView() {
		ImageView imageView = getDefaultImageView();
		Bitmap bitmapTest = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.ic_launcher);

		componentUtils.fillIn(imageView, bitmapTest);

		Bitmap returnedBitmap = componentUtils
				.getBitmap(imageView);

		assertEquals(bitmapTest, returnedBitmap);
	}

	@Test(expected = NullPointerException.class)
	public void seekbarShouldThrowExceptionIfInvalidId() {
		componentUtils.loadSeekBar(INVALID_ID, Constants.MAX_INTENSITY,
				Constants.INTENSITY);
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

		assertEquals(Constants.INTENSITY, bar.getProgress());
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
	public void defaultSpinnerShouldBeVisible() {
		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		assertEquals(View.VISIBLE, sp.getVisibility());
	}
	
	@Test
	public void defaultLinearLayoutShouldBeVisible() {
		LinearLayout ll = getDefaultLinearLayout();

		assertNotNull(ll);
		assertEquals(View.GONE, ll.getVisibility());
	}

	@Test
	public void linearLayoutShouldBeMadeVisible() {
		LinearLayout ll = getDefaultLinearLayout();

		assertNotNull(ll);
		assertEquals(View.GONE, ll.getVisibility());

		componentUtils.showLinearLayout(defaultLinearLayout );
		assertEquals(View.VISIBLE, ll.getVisibility());

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

	@Test
	public void setSelectedItemPositionShouldSetRightPosition() {
		int rightPosition = 1;

		Spinner sp = getDefaultSpinner();

		assertNotNull(sp);
		componentUtils.setSpinnerPosition(defaultSpinnerId, rightPosition);
		assertEquals(rightPosition, sp.getSelectedItemPosition());
	}

	@Test
	public void editTextShouldHaveRightText() {
		EditText et = componentUtils.loadEditText(defaultEditTextId);

		assertEquals("", et.getText().toString());
	}

	@Test
	public void anotherEditTextShouldHaveRightText() {
		String someText = "someText";
		EditText et = componentUtils.loadEditText(defaultEditTextId, someText);

		assertEquals(someText, et.getText().toString());
	}

	@Test(expected = NullPointerException.class)
	public void imageButtonShouldThrowExceptionIfInvalidId() {
		componentUtils.loadImageButton(INVALID_ID, null);
	}

	@Test
	public void imageButtonShouldCallRightListener() {
		ImageButton ib = createImageButtonWithDefaultListener();

		ib.performClick();

		assertTrue(activated);
	}

	@Test
	public void imageButtonShouldNotBeResponsiveWithoutListener() {
		ImageButton ib = getDefaultImageButton(null);

		assertEquals(false, imageButtonIsResponsive(ib));
	}

	@Test
	public void shouldReturnRightColorFromImageButton() {
		Bitmap bitmapTest = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.ic_launcher);
		ImageButton ib = componentUtils.loadImageButtonWithBitmap(defaultImageButtonId, bitmapTest, null);

		Bitmap returnedBitmap = componentUtils.getBitmap(ib);

		assertEquals(bitmapTest, returnedBitmap);
	}
	
	@Test(expected = NullPointerException.class)
	public void checkBoxShouldThrowExceptionIfInvalidId() {
		CheckBox checkBox = componentUtils.loadCheckBox(INVALID_ID, true);
		assertNull(checkBox);
	}

	@Test
	public void checkBoxShouldBeLoadedCorrectly() {
		CheckBox checkBox = getDefaultCheckBox();
		assertNotNull(checkBox);
		assertEquals(checked, checkBox.isChecked());
	}
}
