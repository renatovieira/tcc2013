package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class ColorPickerActivity extends Activity {
	private static final int initialColorValue = 0;
	
	private EditText redET;
	private EditText greenET;
	private EditText blueET;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);

		loadComponents();
	}

	private void loadComponents() {
		ComponentUtils cu = new ComponentUtils(this);

		cu.loadButton(R.id.ok_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				int red = getColor(redET);
				int green = getColor(greenET);
				int blue = getColor(blueET);

				if (colorsWithinLimit(red, green, blue)) {
					Intent returnIntent = new Intent();
					returnIntent.putExtra(Constants.RED, red);
					returnIntent.putExtra(Constants.GREEN, green);
					returnIntent.putExtra(Constants.BLUE, blue);
					setResult(RESULT_OK, returnIntent);
					finish();
				}
				else {
					Context ctx = getBaseContext();
					Toast.makeText(ctx, ctx.getString(R.string.invalid_color), Toast.LENGTH_SHORT).show();
				}
			}

			private boolean colorsWithinLimit(int red, int green, int blue) {
				return isWithinLimits(red) && isWithinLimits(green)
						&& isWithinLimits(blue);
			}

			private int getColor(EditText et) {
				return Integer.parseInt(et.getText().toString());
			}
			
			private boolean isWithinLimits (int color) {
				return color >= Constants.MIN_COLOR_VALUE && color <= Constants.MAX_COLOR_VALUE;
			}
			
		});

		cu.loadButton(R.id.cancel_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);
				finish();
			}
		});

		redET = cu.loadEditText(R.id.red_value, initialColorValue + "");
		greenET = cu.loadEditText(R.id.green_value, initialColorValue + "");
		blueET = cu.loadEditText(R.id.blue_value, initialColorValue + "");

		cu.loadSeekBar(R.id.red_seekbar, Constants.MAX_COLOR_VALUE, 0,
				createSeekbarListener(redET));
		cu.loadSeekBar(R.id.green_seekbar, Constants.MAX_COLOR_VALUE, 0,
				createSeekbarListener(greenET));
		cu.loadSeekBar(R.id.blue_seekbar, Constants.MAX_COLOR_VALUE, 0,
				createSeekbarListener(blueET));
	}

	private OnSeekBarChangeListener createSeekbarListener(final EditText et) {
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
				et.setText(progress + "");
			}
		};
	}
}