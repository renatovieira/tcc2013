package br.usp.ime.tcc.activities.components;

import static org.robolectric.Robolectric.shadowOf;

import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

public class ButtonActionsTestHelper {
	private Activity activity;
	
	private Intent getStartedIntentAfterClick() {
		ShadowActivity shadowActivity = shadowOf(activity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		return startedIntent;
	}
	
	private ShadowIntent getShadowIntentAfterResult(Intent startedIntent) {
		ShadowActivity shadowActivity = shadowOf(activity);
		shadowActivity.receiveResult(startedIntent, Activity.RESULT_OK,
				new Intent().setData(null));
		startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		return shadowIntent;
	}
	
	private Button getButtonById(int buttonId) {
		return (Button) activity.findViewById(buttonId);
	}
	
	private ImageButton getImageButtonById(int imageButtonId) {
		return (ImageButton) activity.findViewById(imageButtonId);
	}

	//Public
	
	public ButtonActionsTestHelper(Activity activity) {
		this.activity = activity;
	}
	
	public boolean getButtonAndClickOnIt(int buttonId) {
		Button button = getButtonById(buttonId);
		return button.performClick();
	}
	
	public Intent getStartedIntentAfterClickOnButton(int buttonId) {
		getButtonAndClickOnIt(buttonId);

		Intent startedIntent = getStartedIntentAfterClick();
		return startedIntent;
	}
	
	public ShadowIntent getIntentAfterResultOfButtonClick(int buttonId) {
		Intent startedIntent = getStartedIntentAfterClickOnButton(buttonId);
		
		ShadowIntent shadowIntent = getShadowIntentAfterResult(startedIntent);
		return shadowIntent;
	}
	
	public boolean getImageButtonAndClickOnIt(int imageButtonId) {
		ImageButton ib = getImageButtonById(imageButtonId);
		return ib.performClick();
	}
	
	public Intent getStartedIntentAfterClickOnImageButton(int imageButtonId) {
		getImageButtonAndClickOnIt(imageButtonId);

		Intent startedIntent = getStartedIntentAfterClick();
		return startedIntent;
	}
	
	public ShadowIntent getIntentAfterResultOfImageButtonClick(int imageButtonId) {
		Intent startedIntent = getStartedIntentAfterClickOnImageButton(imageButtonId);
		
		ShadowIntent shadowIntent = getShadowIntentAfterResult(startedIntent);
		return shadowIntent;
	}
}
