package hu.szaray.huskyadventures;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity {

	private MainActivity mainActivity;
	private static Preferences preferences = new Preferences();

	public void setMainActivity(MainActivity main) {
		mainActivity = main;
	}

	public static Preferences getInstance() {
		return preferences;
	}

	public void openPreferences() {
		mainActivity.startPreferences();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(0);
		addPreferencesFromResource(R.xml.preferences);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}

	public int getColor() {
		SharedPreferences Sp = PreferenceManager.getDefaultSharedPreferences(mainActivity.getBaseContext());
		String color = Sp.getString("collar_color", "1");
		return Integer.parseInt(color);
	}
}
