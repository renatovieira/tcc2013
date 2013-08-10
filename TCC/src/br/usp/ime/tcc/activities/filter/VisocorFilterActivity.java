package br.usp.ime.tcc.activities.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.ContinousFilterActivity;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class VisocorFilterActivity extends FilterActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadSpecificComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.loadButton(R.id.liveModeButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent continousModeIntent = new Intent(
						VisocorFilterActivity.this,
						ContinousFilterActivity.class);
				putFilterTypeExtra(continousModeIntent);
				startActivity(continousModeIntent);
			}
		});

		componentUtils.loadTextViewWithText(R.id.filter_title,
				getString(R.string.visocor_filter));
	}
	
	@Override
	protected void putFilterTypeExtra(Intent intent) {
		intent.putExtra(Constants.FILTER_TYPE, Constants.VISOCOR_FILTER);
	}
}
