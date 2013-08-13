package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class ColorPickerActivity extends Activity {
	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;

	private ComponentUtils cu;
	
	protected int[] rgb;
	private ImageView colorSample;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);
		
		loadInitialValues();
		
		loadComponents();
		updateImageViewWithColor();
	}

	private void loadInitialValues() {
		int redValue = Constants.MAX_COLOR_VALUE;
		int greenValue = Constants.MAX_COLOR_VALUE;
		int blueValue = Constants.MAX_COLOR_VALUE;
		
		rgb = new int[]{redValue, greenValue, blueValue};
	}

	private void loadComponents() {
		cu = new ComponentUtils(this);
		
		colorSample = cu.loadImageView(R.id.selected_color);

		cu.loadButton(R.id.ok_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(Constants.RED, rgb[RED]);
				returnIntent.putExtra(Constants.GREEN, rgb[GREEN]);
				returnIntent.putExtra(Constants.BLUE, rgb[BLUE]);
				setResult(RESULT_OK, returnIntent);
				finish();
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
				updateImageViewWithColor();
			}
		};
	}

	private void updateImageViewWithColor() {
		Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(bitmap);
		c.drawRGB(rgb[RED], rgb[GREEN], rgb[BLUE]);
		colorSample.setImageBitmap(bitmap);
	}
}