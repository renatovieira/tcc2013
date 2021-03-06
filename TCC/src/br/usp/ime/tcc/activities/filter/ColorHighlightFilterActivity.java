package br.usp.ime.tcc.activities.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.filter.bitmap.ColorHighlightBitmapFilterActivity;

public class ColorHighlightFilterActivity extends FilterActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void loadSpecificComponents() {
		if (landscapeMode) {
			componentUtils.showLinearLayout(R.id.leftDummyLL);
			componentUtils.showLinearLayout(R.id.rightDummyLL);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Intent showImageIntent = new Intent(this,
					ColorHighlightBitmapFilterActivity.class);
			putContentOnNextActivityExtras(data, showImageIntent);
			startActivity(showImageIntent);
		}
	}
}
