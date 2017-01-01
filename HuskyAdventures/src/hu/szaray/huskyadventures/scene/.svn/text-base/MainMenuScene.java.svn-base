package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.Preferences;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.GooglePlus;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {
	private MenuScene menu;
	
	private final int MENU_START_SP = 0;
	private final int MENU_START_MP = MENU_START_SP + 1;
	private final int MENU_LEADER = MENU_START_MP + 1;
	private final int MENU_ACHI = MENU_LEADER + 1;
	private final int MENU_SIGN_IN = MENU_ACHI + 1;
	private final int MENU_SIGN_OUT = MENU_SIGN_IN + 1;
	private final int MENU_OPTIONS = MENU_SIGN_OUT + 1;
	private final int MENU_EXIT = MENU_OPTIONS + 1;
	
	IMenuItem signInItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SIGN_IN, ResourceManager.getInstance().signInBtnRegion, vbom), 1.08f, 1);
	IMenuItem signOutItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SIGN_OUT, ResourceManager.getInstance().signOutBtnRegion, vbom), 1.08f, 1);
	

	@Override
	public void createScene() {
		this.setBackground(new SpriteBackground(new Sprite(400, 240, 800, 480, ResourceManager.getInstance().backgroundRegion, this.vbom)));
		this.createMenu();
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	public void disposeScene() {
		ResourceManager.getInstance().unloadMenuResources();
	}
	
	private void createMenu() {
		this.menu = new MenuScene(camera);
		menu.setPosition(0, 0);
		final IMenuItem startSpItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_START_SP, ResourceManager.getInstance().startBtnRegion, vbom), 1.08f, 1);
		final IMenuItem startMpItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_START_MP, ResourceManager.getInstance().startmpBtnRegion, vbom), 1.08f, 1);
		final IMenuItem leaderItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LEADER, ResourceManager.getInstance().leaderBtnRegion, vbom), 1.08f, 1);
		final IMenuItem achiItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_ACHI, ResourceManager.getInstance().achiBtnRegion, vbom), 1.08f, 1);		
		final IMenuItem optionsItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, ResourceManager.getInstance().optionsBtnRegion, vbom), 1.08f, 1);
		final IMenuItem exitItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXIT, ResourceManager.getInstance().exitBtnRegion, vbom), 1.08f, 1);
		
		this.menu.addMenuItem(startSpItem);
		this.menu.addMenuItem(startMpItem);
		this.menu.addMenuItem(leaderItem);
		this.menu.addMenuItem(achiItem);
		this.menu.addMenuItem(signInItem);
		this.menu.addMenuItem(signOutItem);
		if (GooglePlus.getInstance().isSignedIn()) {
			this.signInItem.setAlpha(0);
			this.signOutItem.setAlpha(1);
			//this.menu.addMenuItem(signOutItem);
		} else {
			this.signOutItem.setAlpha(0);
			this.signInItem.setAlpha(1);
			//this.menu.addMenuItem(signInItem);
		}
		this.menu.addMenuItem(optionsItem);
		this.menu.addMenuItem(exitItem);
		
		this.menu.buildAnimations();
		this.menu.setBackgroundEnabled(false);
		
		int y0 = 46;
		int dy = 78;
		startSpItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_START_SP * dy));
		startMpItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_START_MP * dy));
		leaderItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_LEADER * dy));
		achiItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_ACHI * dy));
		signOutItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_SIGN_IN * dy));
		signInItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_SIGN_IN * dy));
		optionsItem.setPosition(Globals.CAMERA_WIDTH / 2, Globals.CAMERA_HEIGHT - (y0 + MENU_SIGN_OUT * dy));
		exitItem.setPosition(Globals.CAMERA_WIDTH -30, Globals.CAMERA_HEIGHT-30);
		
		this.menu.setOnMenuItemClickListener(this);
		this.setChildScene(this.menu);
		//GooglePlus.getInstance().setMenuLoaded();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene scene, IMenuItem item, float localX, float localY) {
		Log.d(Globals.TAG, "MENU");
		switch (item.getID()) {
			case MENU_START_SP:
				Log.d(Globals.TAG, "MENU_START_SP");
				SceneManager.getInstance().setMapChooserScene(false);
				return true;
			case MENU_START_MP:
				Log.d(Globals.TAG, "MENU_START_MP");
				SceneManager.getInstance().setMapChooserScene(true);
				return true;
			case MENU_LEADER:
				Log.d(Globals.TAG, "MENU_START_MP");
				Game.getInstance().displayLeaderboard();
				return true;
			case MENU_ACHI:
				Log.d(Globals.TAG, "MENU_START_MP");
				Game.getInstance().displayAchievements();
				return true;
			case MENU_SIGN_IN:
				Log.d(Globals.TAG, "MENU_SIGN_IN");
				if (GooglePlus.getInstance().isSignedIn()) {
					GooglePlus.getInstance().signOut();
				} else {
					GooglePlus.getInstance().signIn();
				}				
				return true;
			case MENU_SIGN_OUT:
				Log.d(Globals.TAG, "MENU_SIGN_OUT");
				if (GooglePlus.getInstance().isSignedIn()) {
					GooglePlus.getInstance().signOut();
				} else {
					GooglePlus.getInstance().signIn();
				}	
				return true;
			case MENU_OPTIONS:
				Log.d(Globals.TAG, "MENU_OPTIONS");
				Preferences.getInstance().openPreferences();				
				return true;
			case MENU_EXIT:
				Log.d(Globals.TAG, "MENU_EXIT");
				System.exit(0);			
				return true;
		}
		return false;
	}
	
	public void showSignOutButton ()  {
		if (this.signInItem== null )
			return;
		this.signInItem.setAlpha(0);
		this.signOutItem.setAlpha(1);
		
	}
	public void showSignInButton ()  {
		if (this.signOutItem== null )
			return;
		this.signOutItem.setAlpha(0);
		this.signInItem.setAlpha(1);
		
	}
	
	public boolean isMenu () {
		return true;
	}
	
}
