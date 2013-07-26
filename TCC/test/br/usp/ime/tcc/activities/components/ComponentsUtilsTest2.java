package br.usp.ime.tcc.activities.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import br.usp.ime.tcc.activities.FilterActivity;
import br.usp.ime.tcc.activities.R;

@RunWith(RobolectricTestRunner.class)
public class ComponentsUtilsTest2 {
	private Activity activity;
	private ComponentUtils componentUtils;
	private static int defaultTextViewId = R.id.filter_title;
	private static int defaultSpinnerId = R.id.filter_type_spinner;

	private TextView loadDefaultTextView() {
		TextView tv = componentUtils.loadTextView(defaultTextViewId);
		return tv;
	}
	
	private Spinner loadDefaultSpinner() {
		Spinner sp = componentUtils.loadSpinner(defaultSpinnerId);
		return sp;
	}

	// Tests
	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(FilterActivity.class).create()
				.get();

		componentUtils = new ComponentUtils(activity);
	}

	@Test
	public void defaultTextViewTextShouldBeVisocor() {
		TextView tv = loadDefaultTextView();
		assertNotNull(tv);
		assertEquals(activity.getString(R.string.visocor_filter), tv.getText());
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
		Spinner sp = loadDefaultSpinner();
		
		assertNotNull(sp);
		assertEquals(0, sp.getSelectedItemPosition());
	}
	
	@Test
	public void shouldReturnRightDefaultSpinnerText() {
		Spinner sp = loadDefaultSpinner();
		
		assertNotNull(sp);
		assertEquals(activity.getString(R.string.deuteranopia), (String) sp.getSelectedItem());
	}
	
	@Test
	public void defaultSpinnerShouldBeHidden() {
		Spinner sp = loadDefaultSpinner();
		
		assertNotNull(sp);
		assertEquals(View.INVISIBLE, sp.getVisibility());
	}
	
	@Test
	public void spinnerShouldBeMadeVisible() {
		Spinner sp = loadDefaultSpinner();
		
		assertNotNull(sp);
		assertEquals(View.INVISIBLE, sp.getVisibility());
		
		componentUtils.showSpinner(defaultSpinnerId);
		assertEquals(View.VISIBLE, sp.getVisibility());

	}
	
	@Test
	public void getSelectedItemPositionShouldReturnRightPosition() {
		int rightPosition = 1;
		
		Spinner sp = loadDefaultSpinner();
		
		assertNotNull(sp);
		sp.setSelection(rightPosition);
		assertEquals(rightPosition, componentUtils.getSpinnerPosition(defaultSpinnerId));
	}
}
