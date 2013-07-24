package br.usp.ime.tcc.activities;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.filter.Filter;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.FileSaver;
import br.usp.ime.tcc.utils.Utils;

public class FilteredImageActivity extends Activity {
	private ImageView filteredImage;
	private Bitmap originalBitmap;
	private Bitmap filteredBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtered_image);

		getExtras();
		loadComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		goBackToFilterActivity();
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String imagePath = (String) extras.get(Constants.IMAGE_PATH);
			int imageOrientation = (Integer) extras.get(Constants.IMAGE_ORIENTATION);
			originalBitmap = Utils.getScaledBitmapFromImagePath(imagePath, imageOrientation);
			Filter filter = new Filter(Constants.PROGRESS);
			filteredBitmap = filter.applyTo(originalBitmap);
		}
	}

	private void loadComponents() {
		final ComponentUtils componentUtils = new ComponentUtils(this);

		filteredImage = componentUtils.loadImageView(R.id.filtered_image);

		componentUtils.loadButton(R.id.save_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				FileSaver fileSaver = new FileSaver();

				try {
					File file = fileSaver.saveToFile(filteredBitmap);
					addToGallery(file);
					Toast.makeText(FilteredImageActivity.this,
							getString(R.string.file_saved), Toast.LENGTH_SHORT)
							.show();
					goBackToFilterActivity();
				} catch (IOException e) {
					Toast.makeText(FilteredImageActivity.this,
							getString(R.string.try_again), Toast.LENGTH_LONG)
							.show();
				}

			}

			private void addToGallery(File file) {
				Intent mediaScanIntent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri contentUri = Uri.fromFile(file);
				mediaScanIntent.setData(contentUri);
				FilteredImageActivity.this.sendBroadcast(mediaScanIntent);
			}
		});

		componentUtils.loadButton(R.id.discard_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBackToFilterActivity();
			}
		});

		componentUtils.fillIn(filteredImage, filteredBitmap);

		componentUtils.loadSeekBar(R.id.intensity_bar,
				Constants.MAX_INTENSITY, Constants.PROGRESS, new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						Filter filter = new Filter(progress);
						Bitmap filteredBitmap = filter.applyTo(originalBitmap);

						componentUtils.fillIn(filteredImage, filteredBitmap);				
					}
				});
	}

	private void goBackToFilterActivity() {
		finish();
	}
}
