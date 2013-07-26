package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.utils.Constants;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadComponents();
	}

	private void loadComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.loadButton(R.id.visocor_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callFilterActivity(Constants.VISOCOR_FILTER);
			}
		});
		
		componentUtils.loadButton(R.id.simulation_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callFilterActivity(Constants.SIMULATION_FILTER);
			}
		});
	}
	
	private void callFilterActivity(int filterType) {
		Intent filterActivityIntent = new Intent(this, FilterActivity.class);
		filterActivityIntent.putExtra(Constants.FILTER_TYPE, filterType);
		startActivity(filterActivityIntent);
	}
}