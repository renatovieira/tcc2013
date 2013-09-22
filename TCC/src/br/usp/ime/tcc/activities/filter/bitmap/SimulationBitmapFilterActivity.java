package br.usp.ime.tcc.activities.filter.bitmap;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import br.usp.ime.tcc.activities.R;
import br.usp.ime.tcc.activities.components.ComponentUtils;
import br.usp.ime.tcc.filter.simulation.SimulationBitmapFilter;
import br.usp.ime.tcc.utils.Constants;

public class SimulationBitmapFilterActivity extends BitmapFilterActivity {

	@Override
	protected void loadActivityLabel() {
		setTitle(R.string.simulation_filter);
	}

	@Override
	protected void loadFilter() {
		int filterType =  getIntent().getExtras().getInt(Constants.FILTER_TYPE);

		filter = new SimulationBitmapFilter(filterType);
	}
	
	@Override
	protected void loadComponents() {
		componentUtils = new ComponentUtils(this);

		filteredImage = (ImageViewTouch) findViewById(R.id.filtered_image);
	}
}