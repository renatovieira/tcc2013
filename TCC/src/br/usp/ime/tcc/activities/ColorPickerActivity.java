package br.usp.ime.tcc.activities;

import br.usp.ime.tcc.utils.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ColorPickerActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra(Constants.RED, 0);
		returnIntent.putExtra(Constants.GREEN, 0);
		returnIntent.putExtra(Constants.BLUE, 0);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}