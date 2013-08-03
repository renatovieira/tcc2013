package br.usp.ime.tcc.activities.filter;

import android.content.Intent;
import android.os.Bundle;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class ColorHighlightFilterActivity extends FilterActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadSpecificComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.hideButton(R.id.liveModeButton);
		
		componentUtils.loadTextViewWithText(R.id.filter_title,
				getString(R.string.color_highlight_filter));
	}
	
	@Override
	protected void putFilterTypeExtra(Intent intent) {
		intent.putExtra(Constants.FILTER_TYPE, Constants.COLOR_HIGHLIGHT_FILTER);
	}
}
