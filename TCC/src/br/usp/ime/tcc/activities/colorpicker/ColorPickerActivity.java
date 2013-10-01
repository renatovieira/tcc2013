package br.usp.ime.tcc.activities.colorpicker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class ColorPickerActivity extends SherlockActivity {
	protected ComponentUtils cu;

	protected int[] rgb;
	protected ImageView colorSample;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadInitialValues();

		loadComponents();
		cu.updateWithColor(colorSample, rgb[Constants.RED], rgb[Constants.GREEN], rgb[Constants.BLUE]);
	}

	private void loadInitialValues() {
		Bundle extras = getIntent().getExtras();

		int redValue = (Integer) extras.get(Constants.RED_STR);
		int greenValue = (Integer) extras.get(Constants.GREEN_STR);
		int blueValue = (Integer) extras.get(Constants.BLUE_STR);

		rgb = new int[] { redValue, greenValue, blueValue };
	}

	protected abstract void loadComponents();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
				.setIcon(R.drawable.ic_action_accept)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE,
				getString(R.string.discard))
				.setIcon(R.drawable.ic_action_cancel)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent returnIntent = new Intent();

		switch (item.getItemId()) {
		case Constants.SAVE:
			putExtras(returnIntent);
			setResult(RESULT_OK, returnIntent);
			finish();
			return true;
		case Constants.DISCARD:
			setResult(RESULT_CANCELED, returnIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void putExtras(Intent returnIntent) {
		returnIntent.putExtra(Constants.RED_STR, rgb[Constants.RED]);
		returnIntent.putExtra(Constants.GREEN_STR, rgb[Constants.GREEN]);
		returnIntent.putExtra(Constants.BLUE_STR, rgb[Constants.BLUE]);
	}
}