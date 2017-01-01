package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.R;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.GooglePlus;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

public class SceneManager {
	private MapChooserScene mapchooser;
	private SplashScene splash;
	private MainMenuScene menu;
	private GameScene game;
	
	private BaseScene current;
	
	private boolean isBackgroundUrlFinished=false;
	private boolean isBackgroundUrlFinishedWithGoodResult=false;
	
	private Engine engine = ResourceManager.getInstance().engine;
	
	
	private static final SceneManager instance = new SceneManager();
	
	public enum SceneType {
		MENU,
		SPLASH,
		GAME,
		MAPCHOOSER,
	}
	
	public void setBackgroundUrlFinished (boolean a) {
		isBackgroundUrlFinished = true;
	}
	
	public void setMenuScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourceManager.getInstance().loadMenuResources();
		this.menu = new MainMenuScene();
		this.setScene(this.menu);
		this.current.createScene();
		if (pOnCreateSceneCallback!=null) {
			pOnCreateSceneCallback.onCreateSceneFinished(this.menu);
		}
	}
	
	public void makeGameScene() {
		ResourceManager.getInstance().loadGameResources();
		this.game = new GameScene();
	}
	
	public void setGameScene() {
		setScene(this.game);
		this.current.createScene();
	}
	
	public void setScene(SceneType type) {
		switch(type) {
			case MENU:
				this.setScene(this.menu);
				break;
			case SPLASH:
				this.setScene(this.splash);
				break;
			case GAME:
				this.setScene(this.game);
				break;
			case MAPCHOOSER:
				this.setScene(this.mapchooser);
				break;
		}
	}
	
	private void setScene(BaseScene scene) {
		if (this.current != null) {
			this.current.disposeScene();
		}
		this.engine.setScene(scene);
		this.current = scene;
		if (scene.isMenu()) {
			GooglePlus.getInstance().setMenuLoaded();
		}
			
		
	}
	
	public static SceneManager getInstance() {
		return instance;
	}
	
	public BaseScene getCurrent() {
		return this.current;
	}

	public void setMapChooserScene(boolean type) {
		if (!isBackgroundUrlFinished ) {
			MainActivity activity = ResourceManager.getInstance().activity;
			Game.getInstance().showToast(activity.getString(R.string.ERROR_download_not_finished_yet));
			return;
		}
		if (!this.isBackgroundUrlFinishedWithGoodResult) {
			MainActivity activity = ResourceManager.getInstance().activity;
			Game.getInstance().showToast(activity.getString(R.string.ERROR_while_downloading));
			return;
		}

		
		this.mapchooser = new MapChooserScene();
		this.mapchooser.setType(type);
		setScene(this.mapchooser);
		this.current.createScene();
	}

	public void setSplashScreen() {
		this.splash = new SplashScene();
		setScene(this.splash);
		this.current.createScene();
	}
	
	public void showSignOutButton ()  {
		if (menu == null)
			return;
		this.menu.showSignOutButton();
	}
	public void showSignInButton ()  {
		if (menu == null)
			return;
		this.menu.showSignInButton();
	}
	public void setOtherPlayerReady() {
		splash.setOtherPlayerReady();
	}
	
	public void setGameAlone () {
		game.setGameAlone();
	}

	public void setBackgroundUrlFinishedWithGoodResult(boolean b) {
		this.isBackgroundUrlFinishedWithGoodResult = b;
		
	}
}
