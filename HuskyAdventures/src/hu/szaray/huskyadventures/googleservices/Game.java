package hu.szaray.huskyadventures.googleservices;

import hu.szaray.huskyadventures.MainActivity;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

public class Game {

	private boolean gameType;
	private int mapNumber;

	private MainActivity mainActivity;
	private static Game game = new Game();
	private List<String> spMapList = null;
	private List<String> mpMapList = null;
	private int mapVersion;
	
	public Game () {
		this.mpMapList = new ArrayList<String>();
		this.spMapList = new ArrayList<String>();
	}

	public void setMainActivity(MainActivity main) {
		mainActivity = main;
	}

	public static Game getInstance() {
		return game;
	}

	public void startQuickGame(int mask, boolean type) {
		this.gameType = type;
		//Log.d(Globals.TAG, "GAMETYPE: "+type);
		this.mapNumber = mask;
		mainActivity.startQuickGame(mask, type);		
	}

	public void displayLeaderboard() {
		mainActivity.displayLeaderboard();
	}

	public void updateLeaderboard(int num) {
		mainActivity.updateLeaderboard(num);
	}

	public void displayAchievements() {
		mainActivity.displayAchievements();
	}

	public void unlockAchievement(String achievement) {
		mainActivity.unlockAchievement(achievement);
	}

	public void incrementAchievement(String achievement, int howmany) {
		mainActivity.incrementAchievement(achievement, howmany);
	}

	/*public boolean getMapList() {
		if (!this.mainActivity.isInternetConnected()) {
			Toast.makeText(this.mainActivity,
					"Error! No Internet connection available!",
					Toast.LENGTH_LONG).show();
			return false;
		}
		try {
			URL url = new URL("https://carpoon.hu/fs/huskyadventures/list.txt");

			String str;

			LineNumberReader in = new LineNumberReader(new InputStreamReader(
					url.openStream()));
			this.mpMapList = new ArrayList<String>();
			this.spMapList = new ArrayList<String>();
			while ((str = in.readLine()) != null) {

				String[] split = str.split("\t");
				if (in.getLineNumber() == 1) {
					try {
						mapVersion = Integer.parseInt(split[0]);
					} catch (Exception e) {
						// ERROR, HIBAS FAJL
						e.printStackTrace();
						return false;
					}
				} else if (split[0].equals("mp")) {
					mpMapList.add("https://carpoon.hu/fs/huskyadventures/mp/"
							+ split[1]);
				} else if (split[0].equals("sp")) {
					spMapList.add("https://carpoon.hu/fs/huskyadventures/sp/"
							+ split[1]);
					Log.d(Globals.TAG,
							"https://carpoon.hu/fs/huskyadventures/sp/+split[1]: "
									+ "https://carpoon.hu/fs/huskyadventures/sp/"
									+ split[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/

	public int getSpMapSize() {
		return spMapList.size();
	}

	public int getMpMapSize() {
		return mpMapList.size();
	}

	public int getMapNumber() {
		return this.mapNumber;
	}

	public boolean getGameType() {
		return this.gameType;
	}
	
	public void addSpMapList(String s) {
		this.spMapList.add(s);
	}
	
	public void addMpMapList(String s) {
		this.mpMapList.add(s);
	}
	
	public ArrayList<String> getSpMapList() {
		return (ArrayList<String>) this.spMapList;
	}

	public ArrayList<String> getMpMapList() {
		return (ArrayList<String>) this.mpMapList;
	}

	public void saveToInternalStorage(ArrayList<String> list, String filename) {
		this.mainActivity.saveToInterStorage(list, filename);
	}
	
	public ArrayList<String> loadFromInternalStorage (String filename) {
		return this.mainActivity.readFromInternalStorage(filename);
	}

	public int getMapVersion() {
		return mapVersion;
	}
	
	public void setMapVersion (int i) {
		this.mapVersion=i;
	}
	
	public ArrayList <String> readFromInternalStorage (String filename) {
		return this.mainActivity.readFromInternalStorage(filename);
	}

	public void runAsyncTask () {
		this.mainActivity.runAsyncTask();
	}

	public void showToast (final String error) {
		this.mainActivity.runOnUiThread(new Runnable() {
		    public void run() {
		        Toast.makeText(mainActivity, error, Toast.LENGTH_SHORT).show();
		    }
		});
	}
}
