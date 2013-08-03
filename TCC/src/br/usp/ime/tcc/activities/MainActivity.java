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
				callFilterActivity(VisocorFilterActivity.class);
			}
		});
		
		componentUtils.loadButton(R.id.simulation_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callFilterActivity(SimulationFilterActivity.class);
			}
		});
		
		componentUtils.loadButton(R.id.highlight_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callFilterActivity(ColorHighlightFilterActivity.class);
			}
		});
	}
	
	private void callFilterActivity(Class<?> filterClass) {
		Intent filterActivityIntent = new Intent(this, filterClass);
		startActivity(filterActivityIntent);
	}
}