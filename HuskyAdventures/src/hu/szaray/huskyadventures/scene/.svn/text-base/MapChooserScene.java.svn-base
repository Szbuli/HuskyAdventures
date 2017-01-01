package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

public class MapChooserScene extends BaseScene implements IOnMenuItemClickListener {
	private MenuScene menu;
	private boolean type;
	
	@Override
	public void createScene() {
		ResourceManager.getInstance().loadMapResources();
		this.setBackground(new SpriteBackground(new Sprite(400, 240, 800, 480, ResourceManager.getInstance().backgroundRegion, this.vbom)));
		this.menu = new MenuScene(camera);
		menu.setPosition(0, 0);
		this.camera.setCenter(400, -240);
		int size = 80, x0 = 63 + size / 2, y0 = 40 + size / 2, padx = 45, pady = 60;
		int target;
		int mapNum;
		if (this.type) {
			mapNum = Game.getInstance().getMpMapSize();
		} else {
			mapNum = Game.getInstance().getSpMapSize();
		}
		Log.d(Globals.TAG, "mapNum: " + mapNum);
		for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 6; x++) {
            	target = x + y * 6;
            	if ( target >= mapNum ) break;
            	IMenuItem item;
            	if (this.type) {
            		item = new ScaleMenuItemDecorator(new SpriteMenuItem(target, ResourceManager.getInstance().mapboxRegion, vbom), 1.08f, 1);
            	} else {
            		item = new ScaleMenuItemDecorator(new SpriteMenuItem(target, ResourceManager.getInstance().mapboxmpRegion, vbom), 1.08f, 1);
            	}
            	this.menu.addMenuItem(item);
            	item.setPosition(x0 + x * (padx + size), Globals.CAMERA_HEIGHT - y0 - y * (pady + size));
            	Text scoreText = new Text(size / 2, size / 2, ResourceManager.getInstance().mapFont, "" + (target + 1), this.vbom);
            	item.attachChild(scoreText);
            }
		}
		this.menu.setBackgroundEnabled(false);
		this.menu.setOnMenuItemClickListener(this);
		this.setChildScene(this.menu);
	}

	@Override
	public void onBackPressed() {
		SceneManager.getInstance().setMenuScene(null);
	}
	
	@Override
	public void disposeScene() {
		this.clearTouchAreas();
		this.clearChildScene();
		this.clearUpdateHandlers();
		this.clearEntityModifiers();
		this.detachSelf();
		this.dispose();
	}
	
	public void setType(boolean type) {
		this.type = type;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		int target = pMenuItem.getID();
		Log.d(Globals.TAG, "target: " + target);
		Game.getInstance().startQuickGame(target, this.type);
		return true;
	}

}
