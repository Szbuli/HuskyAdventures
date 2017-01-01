package hu.szaray.huskyadventures.googleservices;

import android.util.Log;
import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.scene.SceneManager;

public class GooglePlus {
	
	private boolean signedIn = false;
	private boolean loaded = false;
	private boolean setAtStart = false;
	private MainActivity mainActivity;	
	private static GooglePlus googlePlus = new GooglePlus();	
	
	public void setMainActivity (MainActivity main) {
		mainActivity=main;
	}
	
	public static GooglePlus getInstance () {
		return googlePlus;
	}
	
	public void signIn () {
		mainActivity.googlePlussignIn();
	}
	
	public void signOut () {
		mainActivity.googlePlussignOut();
	}
	
	public void setSignedOut () {
		this.signedIn=false;
	}
	
	public void setSignedOn () {
		this.signedIn=true;		
	}
	
	public boolean isSignedIn () {
		return this.signedIn;
	}

	public void setMenuLoaded() {
		loaded=true;
		Log.d(Globals.TAG,"setMenuLoaded");
		if (!setAtStart && this.mainActivity.getAsyncWaiter()[2]) {
			SceneManager.getInstance().setBackgroundUrlFinishedWithGoodResult(this.mainActivity.getAsyncWaiter()[0]);
	        SceneManager.getInstance().setBackgroundUrlFinished(this.mainActivity.getAsyncWaiter()[1]);
			setAtStart=true;
		}
	}
	public boolean IsMenuLoaded() {
		return loaded;
	}
	
}
