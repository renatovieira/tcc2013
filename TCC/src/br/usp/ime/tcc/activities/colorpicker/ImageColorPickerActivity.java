package br.usp.ime.tcc.activities.colorpicker;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.os.Bundle;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.BitmapLoader;
import br.usp.ime.tcc.utils.Constants;

public class ImageColorPickerActivity extends ColorPickerActivity {
	private ImageViewTouch imageViewTouch;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		setContentView(R.layout.image_color_picker);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadComponents() {
		cu = new ComponentUtils(this);

		colorSample = cu.loadImageView(R.id.image_selected_color);
		
		imageViewTouch = (ImageViewTouch) findViewById(R.id.image_for_selection);
		
		String imagePath = getIntent().getExtras().getString(Constants.IMAGE_PATH);
		
		BitmapLoader bitmapLoader = new BitmapLoader(imagePath);
		cu.fillIn(imageViewTouch, bitmapLoader.getBitmap());
	}
}