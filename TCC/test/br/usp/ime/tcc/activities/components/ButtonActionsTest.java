package br.usp.ime.tcc.activities.components;

import static org.robolectric.Robolectric.shadowOf;

import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class ButtonActionsTest {
	private Activity activity;
	
	public ButtonActionsTest(Activity activity) {
		this.activity = activity;
	}
	
	private Button getButtonById(int buttonId) {
		return (Button) activity.findViewById(buttonId);
	}
	
	public void createButtonAndClickOnIt(int buttonId) {
		Button button = getButtonById(buttonId);
		button.performClick();
	}
	
	public Intent createButtonAndGetStartedIntentAfterClick(int buttonId) {
		createButtonAndClickOnIt(buttonId);

		Intent startedIntent = getStartedIntentAfterClickOnButton();
		return startedIntent;
	}
	
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
	
	public ShadowIntent createButtonClickAndGetIntentAfterResult(int buttonId) {
		Intent startedIntent = createButtonAndGetStartedIntentAfterClick(buttonId);
		
		ShadowIntent shadowIntent = getShadowIntentAfterResult(startedIntent);
		return shadowIntent;
	}
}
