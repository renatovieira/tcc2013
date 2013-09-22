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
	protected static final int RED = 0;
	protected static final int GREEN = 1;
	protected static final int BLUE = 2;

	protected ComponentUtils cu;

	protected int[] rgb;
	protected ImageView colorSample;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadInitialValues();

		loadComponents();
		cu.updateWithColor(colorSample, rgb[RED], rgb[GREEN], rgb[BLUE]);
	}
	
	private void loadInitialValues() {
		Bundle extras = getIntent().getExtras();

		int redValue = (Integer) extras.get(Constants.RED);
		int greenValue = (Integer) extras.get(Constants.GREEN);
		int blueValue = (Integer) extras.get(Constants.BLUE);

		rgb = new int[] { redValue, greenValue, blueValue };
	}

	protected abstract void loadComponents();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE,
				getString(R.string.discard)).setShowAsAction(
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
		returnIntent.putExtra(Constants.RED, rgb[RED]);
		returnIntent.putExtra(Constants.GREEN, rgb[GREEN]);
		returnIntent.putExtra(Constants.BLUE, rgb[BLUE]);
	}
}