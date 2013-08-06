package br.usp.ime.tcc.activities.components;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ComponentUtils {
	private Activity activity;

	public ComponentUtils(Activity act) {
		activity = act;
	}

	public Button loadButton(int buttonId,
			android.view.View.OnClickListener onClickListener) {
		Button button = (Button) activity.findViewById(buttonId);
		
		button.setVisibility(View.VISIBLE);

		button.setOnClickListener(onClickListener);

		return button;
	}
	
	public void hideButton(int buttonId) {
		Button button = (Button) activity.findViewById(buttonId);

		button.setVisibility(View.GONE);
	}
	
	public SeekBar loadSeekBar(int intensityBarId, int maxIntensity,
			int progress) {
		SeekBar seekBar = (SeekBar) activity.findViewById(intensityBarId);

		seekBar.setMax(maxIntensity);
		seekBar.setProgress(progress);
		seekBar.setVisibility(View.VISIBLE);

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
	
	public TextView loadTextView (int textViewId) {
		return (TextView) activity.findViewById(textViewId);
	}
	
	public TextView loadTextViewWithText(int textViewId, String text) {
		TextView tv =loadTextView(textViewId);
		tv.setText(text);
		return tv;
	}
	
	public Spinner loadSpinner(int spinnerId) {
		Spinner sp = (Spinner) activity.findViewById(spinnerId);
		return sp;
	}
	
	public int getSpinnerPosition(int spinnerId) {
		Spinner sp = loadSpinner(spinnerId);
		return sp.getSelectedItemPosition();
	}
	
	public void showSpinner(int spinnerId) {
		Spinner sp = loadSpinner(spinnerId);
		sp.setVisibility(View.VISIBLE);
	}
}
