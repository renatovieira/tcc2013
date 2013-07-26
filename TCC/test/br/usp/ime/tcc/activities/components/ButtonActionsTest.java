package br.usp.ime.tcc.activities.components;

import static org.robolectric.Robolectric.shadowOf;

import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class ButtonActionsTest {
	private Activity activity;
	
	private Intent getStartedIntentAfterClickOnButton() {
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
	
	//Public
	
	public ButtonActionsTest(Activity activity) {
		this.activity = activity;
	}
	
	public boolean getButtonAndClickOnIt(int buttonId) {
		Button button = getButtonById(buttonId);
		return button.performClick();
	}
	
	public Intent getButtonAndGetStartedIntentAfterClick(int buttonId) {
		getButtonAndClickOnIt(buttonId);

		Intent startedIntent = getStartedIntentAfterClickOnButton();
		return startedIntent;
	}
	
	public ShadowIntent getButtonClickAndGetIntentAfterResult(int buttonId) {
		Intent startedIntent = getButtonAndGetStartedIntentAfterClick(buttonId);
		
		ShadowIntent shadowIntent = getShadowIntentAfterResult(startedIntent);
		return shadowIntent;
	}
}
