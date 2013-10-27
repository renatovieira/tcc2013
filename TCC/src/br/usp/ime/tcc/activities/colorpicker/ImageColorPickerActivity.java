package br.usp.ime.tcc.activities.colorpicker;

import it.sephiroth.android.library.imagezoom.ImageColorPicker;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.BitmapLoader;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ImageColorPickerActivity extends ColorPickerActivity {
	private ImageColorPicker imageColorPicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		Utils.loadStripedActionBar(this);
		setContentView(R.layout.image_color_picker);

		rgb = (int[]) getLastNonConfigurationInstance();

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void loadComponents() {
		cu = new ComponentUtils(this);

		colorSample = cu.loadImageView(R.id.image_selected_color);

		imageColorPicker = (ImageColorPicker) findViewById(R.id.image_for_selection);

		imageColorPicker.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				rgb = imageColorPicker.getTouchedPixel();

				cu.updateWithColor(colorSample, rgb[Constants.RED],
						rgb[Constants.GREEN], rgb[Constants.BLUE]);

				return true;
			}
		});

		String imagePath = getIntent().getExtras().getString(
				Constants.IMAGE_PATH);

		BitmapLoader bitmapLoader = new BitmapLoader(imagePath);
		cu.fillIn(imageColorPicker, bitmapLoader.getBitmap());
	}

	@Override
	@Deprecated
	public Object onRetainNonConfigurationInstance() {
		return rgb;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Constants.HELP, Menu.NONE, getString(R.string.help))
				.setIcon(R.drawable.ic_action_about)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == Constants.HELP) {
			Builder alertDialogBuilder = new Builder(this);

			alertDialogBuilder.setTitle(R.string.help);

			alertDialogBuilder
					.setMessage(R.string.image_color_picker_help)
					.setCancelable(false)
					.setPositiveButton(getString(R.string.OK),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			alertDialogBuilder.create().show();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}