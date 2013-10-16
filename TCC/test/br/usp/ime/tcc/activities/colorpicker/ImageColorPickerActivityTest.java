package br.usp.ime.tcc.activities.colorpicker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.utils.Constants;

@RunWith(RobolectricTestRunner.class)
public class ImageColorPickerActivityTest extends ColorPickerActivityTest{

	private ColorPickerActivity startWithExtras() {
		activity = new ImageColorPickerActivity();
		Intent intent = new Intent();
		
		int [] randomColor = generateRandomColor();
		
		intent.putExtra(Constants.RED_STR, randomColor[Constants.RED]);
		intent.putExtra(Constants.GREEN_STR, randomColor[Constants.GREEN]);
		intent.putExtra(Constants.BLUE_STR, randomColor[Constants.BLUE]);
		intent.putExtra(Constants.IMAGE_PATH, "dummy");

		activity.setIntent(intent);
		activity.onCreate(null);

		return activity;
	}

	// Tests

	@Before
	public void setUp() throws Exception {
		activity = startWithExtras();
		activity.onCreateOptionsMenu(new TestMenu());
	}

	@Test
	public void discardOptionShouldReturnBitmapFilterActivity() {
		MenuItem item = new TestMenuItem(Constants.DISCARD);
		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_CANCELED, sa.getResultCode());
	}

	@Test
	public void saveOptionShouldReturnBitmapFilterActivityWithOkResult() {
		MenuItem item = new TestMenuItem(Constants.SAVE);
		activity.onOptionsItemSelected(item);

		ShadowActivity sa = Robolectric.shadowOf(activity);
		assertTrue(sa.isFinishing());
		assertEquals(Activity.RESULT_OK, sa.getResultCode());
	}
	
	@Test
	public void helpOptionShouldDisplayHelpDialog() {
		MenuItem item = new TestMenuItem(Constants.HELP);

		activity.onOptionsItemSelected(item);

		ShadowHandler.idleMainLooper();
		AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
		ShadowAlertDialog sAlert = shadowOf(alertDialog);

		assertEquals(activity.getString(R.string.image_color_picker_help), sAlert.getMessage().toString());
		assertTrue(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick());
		assertFalse(alertDialog.isShowing());
	}
}