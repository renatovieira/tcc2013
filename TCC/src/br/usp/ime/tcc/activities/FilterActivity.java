package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

public class FilterActivity extends Activity {
	private int filterType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_activity);
		getExtras();
		loadComponents();
	}
	
	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			filterType = (Integer) extras.get(Constants.FILTER_TYPE);
		}
	}

	private void loadComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.loadButton(R.id.liveModeButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent continousModeIntent = new Intent(FilterActivity.this, ContinousFilterActivity.class);
				putFilterTypeExtra(continousModeIntent);
				startActivity(continousModeIntent);
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
		
		if (filterType == Constants.VISOCOR_FILTER) {
			componentUtils.loadTextViewWithText(R.id.filter_title, getString(R.string.visocor_filter));
		}
		else {
			componentUtils.loadTextViewWithText(R.id.filter_title, getString(R.string.simulation_filter));
			componentUtils.showSpinner(R.id.filter_type_spinner);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			Intent showImageIntent = new Intent(this,
					BitmapFilterActivity.class);
			putContentOnNextActivityExtras(data, showImageIntent);
			startActivity(showImageIntent);
		}

	}

	private void putContentOnNextActivityExtras(Intent data,
			Intent showImageIntent) {
		String imagePath = Utils.getSelectedPicturePath(data.getData(), this.getContentResolver());
		int imageOrientation = Utils.getOrientation(data.getData(), this.getContentResolver());
		showImageIntent.putExtra(Constants.IMAGE_PATH, imagePath);
		showImageIntent.putExtra(Constants.IMAGE_ORIENTATION, imageOrientation);
		putFilterTypeExtra(showImageIntent);
	}

	private void putFilterTypeExtra(Intent showImageIntent) {
		int filterType;
		
		if (this.filterType == Constants.VISOCOR_FILTER) {
			filterType = this.filterType;
		}
		else {
			ComponentUtils cu = new ComponentUtils(this);
			filterType = cu.getSpinnerPosition(R.id.filter_type_spinner) + 1;
		}
		showImageIntent.putExtra(Constants.FILTER_TYPE, filterType);
	}
}
