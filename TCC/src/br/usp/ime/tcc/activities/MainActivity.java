package br.usp.ime.tcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.activities.filter.ColorHighlightFilterActivity;
import br.usp.ime.tcc.activities.filter.SimulationFilterActivity;
import br.usp.ime.tcc.activities.filter.VisocorFilterActivity;
import br.usp.ime.tcc.activities.settings.SettingsActivity;
import br.usp.ime.tcc.utils.Constants;
import br.usp.ime.tcc.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(R.style.Theme_Sherlock);
		Utils.loadStripedActionBar(this);
		
		Display display = getWindowManager().getDefaultDisplay();
		
		if (display.getWidth() > display.getHeight())
			setContentView(R.layout.main_landscape);
		else
			setContentView(R.layout.main_portrait);

		loadComponents();
	}

	private void loadComponents() {
		ComponentUtils componentUtils = new ComponentUtils(this);

		componentUtils.loadImageButton(R.id.visocor_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(VisocorFilterActivity.class);
			}
		});
		
		componentUtils.loadImageButton(R.id.simulation_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(SimulationFilterActivity.class);
			}
		});
		
		componentUtils.loadImageButton(R.id.highlight_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivity(ColorHighlightFilterActivity.class);
			}
		});
	}
	
	private void callActivity(Class<?> activityClass) {
		Intent activityIntent = new Intent(this, activityClass);
		startActivity(activityIntent);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Constants.SETTINGS, Menu.NONE, getString(R.string.settings))
	        .setIcon(R.drawable.ic_action_settings)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int option = item.getItemId();
		
		if (option == Constants.SETTINGS) {
			callActivity(SettingsActivity.class);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}