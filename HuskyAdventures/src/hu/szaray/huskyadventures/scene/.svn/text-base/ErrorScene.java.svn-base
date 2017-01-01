package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.ResourceManager;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;
import android.view.MotionEvent;

public class ErrorScene extends BaseScene {
	private Text errorText;
	@Override
	public void createScene() {
		Log.d(Globals.TAG, "ErrorScene.createScene");
		this.setBackground(new SpriteBackground(new Sprite(400, 240, 800, 480, ResourceManager.getInstance().backgroundRegion, this.vbom)));
		errorText = new Text(400, -240, ResourceManager.getInstance().mapFont, "", this.vbom);
		this.attachChild(errorText);
		this.registerTouchArea(this);
	}
	
	public void setErrorText(String errortext) {
		this.errorText.setText(errortext);
	}
	
	@Override
	public void onBackPressed() {
		
	}

	@Override
	public void disposeScene() {
		
	}
	public boolean onTouchEvent(MotionEvent event) {
		return true;
		
	}
}
