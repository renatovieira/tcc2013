package br.usp.ime.tcc.activities.filter;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;

public abstract class FilterActivity extends SherlockActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);

		Utils.loadStripedActionBar(this);

		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();

		if (display.getWidth() > display.getHeight())
			setContentView(R.layout.filter_activity);
		else
			setContentView(R.layout.filter_activity);
		loadComponents();
	}

	private void loadComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		componentUtils.loadImageButton(R.id.galleryModeButton,
				new OnClickListener() {

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

		componentUtils.loadImageButton(R.id.cameraModeButton, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(takePictureIntent,
						Constants.TAKE_PICTURE);
			}
		});

		loadSpecificComponents();
	}

	protected abstract void loadSpecificComponents();

	public void putContentOnNextActivityExtras(Intent data,
			Intent showImageIntent) {
		String imagePath = Utils.getSelectedPicturePath(data.getData(),
				this.getContentResolver());
		showImageIntent.putExtra(Constants.IMAGE_PATH, imagePath);
	}
}
