package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

public class FilterActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		loadComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}
	
	private void loadComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.loadButton(R.id.liveModeButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Chamar modo continuo
			}
		});

		componentUtils.loadButton(R.id.galleryModeButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				startGalleryChooser();
			}

			private void startGalleryChooser() {
				Intent photoPickerIntent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPickerIntent.setType("image/*");

				startActivityForResult(photoPickerIntent,
						Constants.SELECT_PICTURE);
			}
		});

		componentUtils.loadButton(R.id.cameraModeButton, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(takePictureIntent,
						Constants.TAKE_PICTURE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			Intent showImageIntent = new Intent(this,
					FilteredImageActivity.class);
			putPicturePathAndOrientationOnNextActivityExtras(data, showImageIntent);
			startActivity(showImageIntent);
		}

	}

	private void putPicturePathAndOrientationOnNextActivityExtras(Intent data,
			Intent showImageIntent) {
		String imagePath = Utils.getSelectedPicturePath(data.getData(), this.getContentResolver());
		int imageOrientation = Utils.getOrientation(data.getData(), this.getContentResolver());
		showImageIntent.putExtra(Constants.IMAGE_PATH, imagePath);
		showImageIntent.putExtra(Constants.IMAGE_ORIENTATION, imageOrientation);
	}
}
