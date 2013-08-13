package br.usp.ime.tcc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.filter.ColorHighlightFilterActivity;
import br.usp.ime.tcc.activities.filter.SimulationFilterActivity;
import br.usp.ime.tcc.activities.filter.VisocorFilterActivity;

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
				callActivity(VisocorFilterActivity.class);
			}
		});
		
		componentUtils.loadButton(R.id.simulation_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(SimulationFilterActivity.class);
			}
		});
		
		componentUtils.loadButton(R.id.highlight_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(ColorHighlightFilterActivity.class);
			}
		});
		
		componentUtils.loadButton(R.id.settings_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(SettingsActivity.class);
			}
		});
	}
	
	private void callActivity(Class<?> activityClass) {
		Intent activityIntent = new Intent(this, activityClass);
		startActivity(activityIntent);
	}
}