package br.usp.ime.tcc.activities.components;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ComponentUtils {
	private Activity activity;

	public ComponentUtils(Activity act) {
		activity = act;
	}

	public Button loadButton(int buttonId, OnClickListener onClickListener) {
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

	public Bitmap getBitmap(ImageView imageView) {
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		return drawable.getBitmap();
	}

	public void fillIn(ImageView imageView, Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
	}

	public void updateWithColor(ImageView imageView, int red, int green,
			int blue) {
		Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(bitmap);
		c.drawRGB(red, green, blue);
		imageView.setImageBitmap(bitmap);
	}

	public TextView loadTextView(int textViewId) {
		return (TextView) activity.findViewById(textViewId);
	}

	public TextView loadTextViewWithText(int textViewId, String text) {
		TextView tv = loadTextView(textViewId);
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

	public void setSpinnerPosition(int spinnerId, int position) {
		Spinner sp = loadSpinner(spinnerId);
		sp.setSelection(position);
	}
	
	public void showLinearLayout(int linearLayoutId) {
		LinearLayout ll = loadLinearLayout(linearLayoutId);
		ll.setVisibility(View.VISIBLE);
	}

	public LinearLayout loadLinearLayout(int linearLayoutId) {
		LinearLayout ll = (LinearLayout) activity.findViewById(linearLayoutId);
		return ll;
	}

	public EditText loadEditText(int editTextId) {
		return loadEditText(editTextId, "");
	}

	public EditText loadEditText(int editTextId, String text) {
		EditText et = (EditText) activity.findViewById(editTextId);
		et.setText(text);
		return et;
	}

	public ImageButton loadImageButton(int imageButtonId,
			OnClickListener listener) {
		ImageButton imageButton = (ImageButton) activity
				.findViewById(imageButtonId);
		imageButton.setOnClickListener(listener);
		return imageButton;
	}

	public ImageButton loadImageButtonWithBitmap(int imageButtonId, Bitmap bmp,
			OnClickListener listener) {
		ImageButton imageButton = loadImageButton(imageButtonId, listener);
		
		fillIn(imageButton, bmp);

		return imageButton;
	}

	public CheckBox loadCheckBox(int checkBoxId, boolean checked) {
		CheckBox checkBox = (CheckBox) activity.findViewById(checkBoxId);
		
		checkBox.setChecked(checked);
		return checkBox;
	}
}
