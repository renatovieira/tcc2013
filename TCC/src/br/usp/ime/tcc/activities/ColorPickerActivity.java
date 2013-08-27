package br.usp.ime.tcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.settings.SettingsManager;
import br.usp.ime.tcc.utils.Constants;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ColorPickerActivity extends SherlockActivity {
	protected static final int RED = 0;
	protected static final int GREEN = 1;
	protected static final int BLUE = 2;
	
	private ComponentUtils cu;
	
	protected int[] rgb;
	private ImageView colorSample;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);
		
		loadInitialValues();
		
		loadComponents();
		cu.updateWithColor(colorSample, rgb[RED], rgb[GREEN], rgb[BLUE]);
	}

	private void loadInitialValues() {
		SettingsManager settingsManager = new SettingsManager(this);
		
		int redValue = settingsManager.loadDefaultRed();
		int greenValue = settingsManager.loadDefaultGreen();
		int blueValue = settingsManager.loadDefaultBlue();
		
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			redValue = (Integer) extras.get(Constants.RED);
			greenValue = (Integer) extras.get(Constants.GREEN);
			blueValue = (Integer) extras.get(Constants.BLUE);
		}
		
		rgb = new int[]{redValue, greenValue, blueValue};
	}

	private void loadComponents() {
		cu = new ComponentUtils(this);
		
		colorSample = cu.loadImageView(R.id.selected_color);

		cu.loadSeekBar(R.id.red_seekbar, Constants.MAX_COLOR_VALUE, rgb[RED],
				createSeekbarListener(RED));
		cu.loadSeekBar(R.id.green_seekbar, Constants.MAX_COLOR_VALUE,
				rgb[GREEN], createSeekbarListener(GREEN));
		cu.loadSeekBar(R.id.blue_seekbar, Constants.MAX_COLOR_VALUE, rgb[BLUE],
				createSeekbarListener(BLUE));
	}

	private OnSeekBarChangeListener createSeekbarListener(final int color) {
		return new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				rgb[color] = seekBar.getProgress();
				cu.updateWithColor(colorSample, rgb[RED], rgb[GREEN], rgb[BLUE]);
			}
		};
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, Constants.SAVE, Menu.NONE, getString(R.string.save))
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

    	menu.add(Menu.NONE, Constants.DISCARD, Menu.NONE, getString(R.string.discard))
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent returnIntent = new Intent();

    	switch(item.getItemId()) {
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