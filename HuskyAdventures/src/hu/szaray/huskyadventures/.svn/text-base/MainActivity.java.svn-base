package hu.szaray.huskyadventures;

import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.GooglePlus;
import hu.szaray.huskyadventures.googleservices.Multiplayer;
import hu.szaray.huskyadventures.scene.SceneManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GBaseGameActivity;

public class MainActivity extends GBaseGameActivity implements
		RealTimeMessageReceivedListener, RoomStatusUpdateListener,
		RoomUpdateListener {

	final static int RC_SELECT_PLAYERS = 10000;
	final static int RC_INVITATION_INBOX = 10001;
	final static int RC_WAITING_ROOM = 10002;
	final static int REQUEST_LEADERBOARD = 10007;
	final static int REQUEST_ACHIEVEMENTS = 10008;

	// Room ID where the currently active game is taking place; null if we're
	// not playing.
	String mRoomId = null;

	// My participant ID in the currently active game
	String mMyId = null;

	// The participants in the currently active game
	ArrayList<Participant> mParticipants = null;

	// are we already playing?
	boolean mPlaying = false;

	// at least 2 players required for our game
	final static int MIN_PLAYERS = 2;

	protected Scene mMainScene;

	private ConnectivityManager cm;

	private BoundCamera camera;
	
	private boolean[] asnycwaiter = {true, false, false};
	
	/*doInBackground: Code performing long running operation goes in this method.  When onClick method is executed on click of button, it calls execute method which accepts parameters and automatically calls doInBackground method with the parameters passed.
	onPostExecute: This method is called after doInBackground method completes processing. Result from doInBackground is passed to this method.
	onPreExecute: This method is called before doInBackground method is called.
	onProgressUpdate: This method is invoked by calling publishProgress anytime from doInBackground call this method.*/
	
	
	
	
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new BoundCamera(0, 0, Globals.CAMERA_WIDTH,
				Globals.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						Globals.CAMERA_WIDTH, Globals.CAMERA_HEIGHT),
				this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		if (!MultiTouch.isSupported(this)) {
			Toast.makeText(this, "Your device does not support multitouch!",
					Toast.LENGTH_LONG).show();
		}
		
		return engineOptions;
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws IOException {
		SceneManager.getInstance().setMenuScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws IOException {
		ResourceManager.initialize(this.getEngine(), this, camera, this.getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		GooglePlus.getInstance().setMainActivity(this);
		Multiplayer.getInstance().setMainActivity(this);
		Game.getInstance().setMainActivity(this);
		Preferences.getInstance().setMainActivity(this);
		this.runAsyncTask();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrent().onBackPressed();
			return true;
		}
		return false;
	}

	// GOOGLE SERVICES

	@Override
	public void onSignInFailed() {
		// Sign in has failed. So show the user the sign-in button.
		GooglePlus.getInstance().setSignedOut();
		if (!GooglePlus.getInstance().IsMenuLoaded())
			return;
		
		SceneManager.getInstance().showSignInButton();
	}

	@Override
	public void onSignInSucceeded() {
		// show sign-out button, hide the sign-in button
		GooglePlus.getInstance().setSignedOn();
		if (!GooglePlus.getInstance().IsMenuLoaded())
			return;
		SceneManager.getInstance().showSignOutButton();
	}

	public void googlePlussignIn() {
		beginUserInitiatedSignIn();
	}

	public void googlePlussignOut() {
		signOut();
		GooglePlus.getInstance().setSignedOut();
		SceneManager.getInstance().showSignInButton();
	}

	public void startQuickGame(int mask, boolean type) {
		if (type) {
			// auto-match criteria to invite one random automatch opponent.
			// You can also specify more opponents (up to 3).
			Bundle am = RoomConfig.createAutoMatchCriteria(1, 1, 0);

			// build the room config:
			RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
			roomConfigBuilder.setMessageReceivedListener(this);
			roomConfigBuilder.setRoomStatusUpdateListener(this);
			roomConfigBuilder.setAutoMatchCriteria(am);
			roomConfigBuilder.setVariant(mask+1);
			RoomConfig roomConfig = roomConfigBuilder.build();

			reset();

			// create room:
			try {
				Games.RealTimeMultiplayer.create(getApiClient(), roomConfig);
			} catch (Exception e) {
				GameError("default");
				Log.d(Globals.TAG, "GOOGLE API HIBA");
				e.printStackTrace();
			}

		} else {
			SceneManager.getInstance().setSplashScreen();
		}
	}

	private void reset() {
		mPlaying = false;
	}

	@Override
	public void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		super.onActivityResult(requestCode, responseCode, intent);

		switch (requestCode) {
		/*
		 * case RC_SELECT_PLAYERS: // we got the result from the
		 * "select players" UI -- ready to create the room
		 * handleSelectPlayersResult(responseCode, intent); break; case
		 * RC_INVITATION_INBOX: // we got the result from the
		 * "select invitation" UI (invitation inbox). We're // ready to accept
		 * the selected invitation: handleInvitationInboxResult(responseCode,
		 * intent); break;
		 */
		case RC_WAITING_ROOM:
			// we got the result from the "waiting room" UI.
			if (responseCode == Activity.RESULT_OK) {
				Log.d(Globals.TAG,"RESULT_OK");
				// ready to start playing
				Log.d(Globals.TAG, "Starting game (waiting room returned OK).");
				mPlaying = true;
			} else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
				Log.d(Globals.TAG,"RESULT_LEFT_ROOM");
				//Games.RealTimeMultiplayer.leave(getApiClient(), this, mRoomId);
				//Multiplayer.getInstance().connectionLost();
				// player indicated that they want to leave the room
				leaveRoom();
			} else if (responseCode == Activity.RESULT_CANCELED) {
				Log.d(Globals.TAG,"RESULT_CANCELED");
				// Dialog was cancelled (user pressed back key, for
				// instance). In our game,
				// this means leaving the room too.
				//finishActivity(RC_WAITING_ROOM);
				leaveRoom();
			}
			break;
		}

	}
	
	@Override
	public void onLeftRoom(int statusCode, String roomId) {
		// we have left the room; return to main screen.
		Log.d(Globals.TAG, "onLeftRoom, code " + statusCode);
		Multiplayer.getInstance().connectionLost();
	}

	void updateRoom(Room room) {
		if (room != null) {
			mParticipants = room.getParticipants();
		}
	}

	@Override
	public void onStop() {

		Log.d(Globals.TAG, "onStop");

		leaveRoom();
		super.onStop();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	// Show the waiting room UI to track the progress of other players as they
	// enter the
	// room and get connected.
	private void showWaitingRoom(Room room) {
		// minimum number of players required for our game
		// For simplicity, we require everyone to join the game before we start
		// it
		// (this is signaled by Integer.MAX_VALUE).
		final int MIN_PLAYERS = Integer.MAX_VALUE;
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(getApiClient(), room, MIN_PLAYERS);
		// show waiting room UI
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	public void leaveRoom() {
		Log.d(Globals.TAG, "Leaving room.");
		if (mRoomId != null) {
			Games.RealTimeMultiplayer.leave(getApiClient(), this, mRoomId);
			mRoomId = null;
			Multiplayer.getInstance().connectionLost();
		}
	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		Log.d(Globals.TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			Log.e(Globals.TAG, "*** Error: onRoomConnected, status "
					+ statusCode);

			// show error message, return to main screen.
			GameError("default");

			return;
		}
		// show the waiting room UI
		showWaitingRoom(room);
	}

	// vissza a menübe és error kiir
	public void GameError(String string) {		
		Multiplayer.getInstance().connectionLost();

		if (string.equals("default")) {
			Toast.makeText(this, "Ooops! Something went wrong in the Google Play services. Please try again!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	public void onRoomConnected(int statusCode, Room room) {
		Log.d(Globals.TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			Log.e(Globals.TAG, "*** Error: onRoomConnected, status "
					+ statusCode);

			// show error message, return to main screen.
			if (statusCode != 7007) {
				GameError("default");
			}

			return;
		}
		updateRoom(room);
	}

	@Override
	public void onRoomCreated(int statusCode, Room room) {
		Log.d(Globals.TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// let screen go to sleep
			Log.e(Globals.TAG, "*** Error: onRoomCreated, status " + statusCode);

			// show error message, return to main screen.
			if (statusCode != 7007) {
				GameError("default");
			}
			return;

		}
		showWaitingRoom(room);

	}

	// returns whether there are enough players to start the game
	boolean shouldStartGame(Room room) {
		int connectedPlayers = 0;
		for (Participant p : room.getParticipants()) {
			if (p.isConnectedToRoom())
				Log.d("Husky adv", "shouldstart game counter:"
						+ connectedPlayers);
			++connectedPlayers;
		}
		return connectedPlayers >= MIN_PLAYERS;
	}

	boolean shouldCancelGame(Room room) {
		//Your game-specific cancellation logic here. For example, you
		// might decide to
		// cancel the game if enough people have declined the invitation or left
		// the room.
		// You can check a participant's status with Participant.getStatus().
		// (Also, your UI should have a Cancel button that cancels the game too)
		return false;
	}

	@Override
	public void onConnectedToRoom(Room room) {
		Log.d(Globals.TAG, "onConnectedToRoom.");

		// get room ID, participants and my ID:
		mRoomId = room.getRoomId();
		mParticipants = room.getParticipants();
		mMyId = room.getParticipantId(Games.Players
				.getCurrentPlayerId(getApiClient()));

		// print out the list of participants (for debug purposes)
		Log.d(Globals.TAG, "Room ID: " + mRoomId);
		Log.d(Globals.TAG, "My ID " + mMyId);
		Log.d(Globals.TAG, "<< CONNECTED TO ROOM>>");

	}

	@Override
	public void onDisconnectedFromRoom(Room room) {

		mRoomId = null;
		// show error message and return to main screen
		//GameError("Disconnected from the room!");
	}

	// ---------------------------------------------------------------
	@Override
	public void onP2PConnected(String arg0) {
	}

	@Override
	public void onP2PDisconnected(String arg0) {
	}

	@Override
	public void onPeerDeclined(Room room, List<String> peers) {
		updateRoom(room);
		// peer declined invitation -- see if game should be canceled
		if (!mPlaying && shouldCancelGame(room)) {
			leaveRoom();
		}
	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> arg1) {
		updateRoom(room);
	}

	@Override
	public void onPeerJoined(Room room, List<String> arg1) {	
		updateRoom(room);
	}

	@Override
	public void onPeerLeft(Room room, List<String> arg1) {
		updateRoom(room);

	}

	@Override
	public void onPeersConnected(Room room, List<String> peers) {
		updateRoom(room);
		if (mPlaying) {
			// add new player to an ongoing game
		} else if (shouldStartGame(room)) {
			// start game!

			String master;
			if ((room.getParticipants().get(0).getParticipantId()
					.compareToIgnoreCase(room.getParticipants().get(1)
							.getParticipantId())) < 0) {
				master = room.getParticipants().get(0).getParticipantId();
			} else {
				master = room.getParticipants().get(1).getParticipantId();
			}

			if (master.equals(this.mMyId)) {
				Multiplayer.getInstance().setMaster(true);
			} else {
				Multiplayer.getInstance().setMaster(false);
			}
			Log.d(Globals.TAG, "Multiplayer.getInstance().isMaster(): "
					+ Multiplayer.getInstance().isMaster());
			SceneManager.getInstance().setSplashScreen();
		}
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> peers) {
		SceneManager.getInstance().setGameAlone();
		/*if (mPlaying) {
			// do game-specific handling of this -- remove player's avatar
			// from the screen, etc. If not enough players are left for
			// the game to go on, end the game and leave the room.
		} else if (shouldCancelGame(room)) {
			// cancel the game
			leaveRoom();
		}
		leaveRoom();
		SceneManager.getInstance().showErrorMessages("Kilépett a másik játékos!");
		//GameError();*/
	}

	// -----------------------------------------------------------------
	@Override
	public void onRoomAutoMatching(Room room) {
		updateRoom(room);
	}

	@Override
	public void onRoomConnecting(Room room) {
		updateRoom(room);
	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {

		byte[] buf = rtm.getMessageData();
		Multiplayer.getInstance().readMessage(buf);
		// Log.d(Globals.TAG, "Message received: " + (char) buf[0] + "/" + (int)
		// buf[1]);
	}

	public void sendRealTimeMessageRealiable(byte[] m) {
		if (this.mRoomId == null)
			return;
		for (Participant p : mParticipants) {
			if (p.getParticipantId().equals(mMyId))
				continue;
			if (p.getStatus() != Participant.STATUS_JOINED)
				continue;
			Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), null,
					m, mRoomId, p.getParticipantId());
		}
	}

	public void sendRealTimeMessageUnRealiable(byte[] m) {
		if (this.mRoomId == null)
			return;
		for (Participant p : mParticipants) {
			if (p.getParticipantId().equals(mMyId))
				continue;
			if (p.getStatus() != Participant.STATUS_JOINED)
				continue;
			Games.RealTimeMultiplayer.sendUnreliableMessage(getApiClient(), m,
					mRoomId, p.getParticipantId());
		}
	}

	public void startPreferences() {
		Intent intent = new Intent(MainActivity.this, Preferences.class);
		startActivity(intent);
	}

	public void displayLeaderboard() {
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
				getApiClient(),
				getResources().getString(R.string.leaderboard_id)),
				REQUEST_LEADERBOARD);
	}

	public void updateLeaderboard(int num) {
		Games.Leaderboards.submitScore(getApiClient(), getResources()
				.getString(R.string.leaderboard_id), num);
	}

	public void displayAchievements() {
		startActivityForResult(
				Games.Achievements.getAchievementsIntent(getApiClient()),
				REQUEST_ACHIEVEMENTS);
	}

	public void unlockAchievement(String a) {
		Games.Achievements.unlock(getApiClient(), a);
	}

	public void incrementAchievement(String achievement, int howmany) {
		Games.Achievements.increment(getApiClient(), achievement, howmany);
	}

	public boolean isInternetConnected() {
		NetworkInfo ni=null;
		try {
			cm = (ConnectivityManager) MainActivity.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			ni = cm.getActiveNetworkInfo();
		} catch (Exception e) {
			Log.d(Globals.TAG, "INTERNET CONN LEKÉRÉS SZAR");
			e.printStackTrace();
		}
		if (ni == null) {
			return false;
		}
		return true;
	}
/*
	public boolean downloadMapList() {
		if (!this.isInternetConnected())
			return false;
		try {
			URL url = new URL("https://carpoon.hu/fs/huskyadventures/list.txt");
			URLConnection conn = url.openConnection();
			conn.connect();
			int lenghtOfFile = conn.getContentLength();
			InputStream is = url.openStream();

			FileOutputStream fOut = openFileOutput("list.txt",
					MODE_WORLD_READABLE);
			byte data[] = new byte[1024];
			long total = 0;
			int progress = 0;
			int count = 0;
			while ((count = is.read(data)) != -1) {
				total += count;
				int progress_temp = (int) total * 100 / lenghtOfFile;
				/*
				 * publishProgress("" + progress_temp); //only for asynctask if
				 * (progress_temp % 10 == 0 && progress != progress_temp) {
				 * progress = progress_temp; }
				 *//*
				fOut.write(data, 0, count);
			}
			is.close();
			fOut.close();
		} catch (Exception e) {
			Log.e("ERROR DOWNLOADING", "Unable to download" + e.getMessage());
			return false;
		}
		return true;
	}*/

	public ArrayList <String> readFromInternalStorage(String filename) {
		ArrayList <String> temp;
		try {
			FileInputStream fis = openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			temp = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				temp.add(line);
				Log.d(Globals.TAG,""+line);
			}
			fis.close();
			isr.close();
			bufferedReader.close();
		} catch (Exception e) {
			Log.e("Husky adv", "Can't read from internal" + e.getMessage());
			GameError("Something went wrong while trying to read from the internal storage!");
			return null;

		}
		return temp;
	}
	
	public void saveToInterStorage (ArrayList<String> list, String filename) {

		//File myfile = getFileStreamPath(filename);
		/*File myfile = new File(this.getFilesDir(), filename);
		if(myfile.exists()) {
			myfile.delete();
			
		}*//*
		if (this.deleteFile(filename)) {
			Log.d(Globals.TAG,"FAJL TOROLVE: "+filename);
		}*/
				

		FileOutputStream outputStream;

		try {
		  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		  for (int i = 0; i< list.size();i++) {
			  outputStream.write((list.get(i)+"\n").getBytes());
		  }
		  
		  outputStream.close();
		} catch (Exception e) {
		GameError("Something went wrong while trying to write to the internal storage!");
		  e.printStackTrace();
		}
	}
	
	private class AsyncTaskRunner extends AsyncTask<Void, Void, Boolean> {



		@Override
		protected Boolean doInBackground(Void... params) {
			String version="-1";
			try {
				URL url = new URL("https://carpoon.hu/fs/huskyadventures/list.txt");

				String str;

				LineNumberReader in = new LineNumberReader(new InputStreamReader(
						url.openStream()));
				
				while ((str = in.readLine()) != null) {

					String[] split = str.split("\t");
					if (in.getLineNumber() == 1) {
						try {
							version = split[0];
							Log.d(Globals.TAG, "doinBackground_VERZIO"+version);
							Game.getInstance().setMapVersion(Integer.parseInt(split[0]));
						} catch (Exception e) {
							GameError("Something went wrong while reading map list!");
							e.printStackTrace();
							return false;
						}
					} else if (split[0].equals("mp")) {
						Game.getInstance().addMpMapList("https://carpoon.hu/fs/huskyadventures/mp/"	+ split[1]);
					} else if (split[0].equals("sp")) {
						Game.getInstance().addSpMapList("https://carpoon.hu/fs/huskyadventures/sp/"	+ split[1]);
						/*Log.d(Globals.TAG,
								"https://carpoon.hu/fs/huskyadventures/sp/+split[1]: "
										+ "https://carpoon.hu/fs/huskyadventures/sp/"
										+ split[1]);*/
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;				
			}
			
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(version);
			temp.addAll(Game.getInstance().getSpMapList());
			temp.addAll(Game.getInstance().getMpMapList());
			Game.getInstance().saveToInternalStorage(temp, "list.txt");
			return true;
		}
		
		@Override
	    protected void onPostExecute(Boolean result) {
			
			if (GooglePlus.getInstance().IsMenuLoaded()) {
				 SceneManager.getInstance().setBackgroundUrlFinishedWithGoodResult(result);
		         SceneManager.getInstance().setBackgroundUrlFinished(true);
		         Log.d(Globals.TAG,"onPostExecute");
			} else {
				asnycwaiter[0]=result;
				asnycwaiter[1]=true;
				asnycwaiter[2]=true;
			}
	    }

	}
	public void runAsyncTask () {
		new AsyncTaskRunner().execute(); 
	}
	
	public boolean[] getAsyncWaiter() {
		return asnycwaiter;
	}
}
