package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.Messages;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.Multiplayer;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

public class SplashScene extends BaseScene {
	private MenuScene menu;
	
	private boolean otherPlayerReady=false;
	private boolean myPlayerReady=false;
	@Override
	public void createScene() {
		Log.d(Globals.TAG, "SplashScene.createScene");
		this.setBackground(new SpriteBackground(new Sprite(400, 240, 800, 480, ResourceManager.getInstance().backgroundRegion, this.vbom)));
		this.menu = new MenuScene(camera);
		Text itemText = new Text(400, 240, ResourceManager.getInstance().mapFont, "Loading...", this.vbom);
		menu.attachChild(itemText);
		this.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				if (Game.getInstance().getGameType()) {
					
					Log.d(Globals.TAG, "SplashScene.onTimePassed");
					if (!myPlayerReady) {
						SceneManager.getInstance().makeGameScene();
						Multiplayer.getInstance().sendMessage(Messages.CAN_START_GAME, 0, 0);
						myPlayerReady=true;
					}
					if (otherPlayerReady && myPlayerReady) {
						SplashScene.this.unregisterUpdateHandler(pTimerHandler);
						SceneManager.getInstance().setGameScene();
					}
					
					
					pTimerHandler.reset();
				} else {
					SplashScene.this.unregisterUpdateHandler(pTimerHandler);
					SceneManager.getInstance().makeGameScene();
					SceneManager.getInstance().setGameScene();
				}
			}
		}));
		this.menu.setBackgroundEnabled(false);
		this.setChildScene(this.menu);
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public void onAttached() {
	}

	@Override
	public void disposeScene() {

	}
	
	public void setOtherPlayerReady() {
		this.otherPlayerReady=true;
	}

}
