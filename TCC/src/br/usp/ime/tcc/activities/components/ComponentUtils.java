package br.usp.ime.tcc.activities.components;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ComponentUtils {
	private Activity activity;

	public ComponentUtils(Activity act) {
		activity = act;
	}

	public Button loadButton(int buttonId,
			android.view.View.OnClickListener onClickListener) {
		Button button = (Button) activity.findViewById(buttonId);

		button.setOnClickListener(onClickListener);

		return button;
	}
	
	public void hideSeekBar(int intensityBarId) {
		SeekBar seekBar = (SeekBar) activity.findViewById(intensityBarId);
		seekBar.setVisibility(View.INVISIBLE);
	}

	public SeekBar loadSeekBar(int intensityBarId, int maxIntensity,
			int progress) {
		SeekBar seekBar = (SeekBar) activity.findViewById(intensityBarId);

		seekBar.setMax(maxIntensity);
		seekBar.setProgress(progress);

		return seekBar;
	}
	
	public SeekBar loadSeekBar(int intensityBarId, int maxIntensity,
			int progress, OnSeekBarChangeListener listener) {
		SeekBar seekBar = loadSeekBar(intensityBarId, maxIntensity, progress);
		seekBar.setOnSeekBarChangeListener(listener);

		return seekBar;
	}
	
	public ImageView loadImageView(int imageViewId) {
		ImageView imageView = (ImageView) activity.findViewById(imageViewId);
		return imageView;
	}

	public Bitmap getBitmapFromImageView(ImageView imageView) {
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		return drawable.getBitmap();
	}

	public void fillIn(ImageView imageView, Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
	}
}
