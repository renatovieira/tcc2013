package br.usp.ime.tcc.activities.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
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

	// Tests
	@Before
	public void setUp() throws Exception {
		activity = Robolectric.buildActivity(FilterActivity.class).create()
				.get();

		componentUtils = new ComponentUtils(activity);
	}

	public void defaultTextViewTextShouldBeEmpty() {
		TextView tv = loadDefaultTextView();
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
}
