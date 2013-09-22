package br.usp.ime.tcc.activities.colorpicker;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class SettingsColorPickerActivity extends ColorPickerActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		setContentView(R.layout.settings_color_picker);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadComponents() {
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
}