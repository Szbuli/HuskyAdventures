package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.scene.GameScene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TimeAnimatedTile extends Sprite {
	private GameScene gamescene;
	private float startTime;

	public TimeAnimatedTile(float x, float y, VertexBufferObjectManager vbom, GameScene gamescene, float lastTime) {
		super(x, y, ResourceManager.getInstance().barkRegion, vbom);
		this.gamescene = gamescene;
		this.startTime = lastTime;
		this.setAlpha(0f);
	}
	
	public void onAndengineUpdate(float time) {
		float dt = time - this.startTime;
		if (dt < .2f) {
			this.setAlpha(dt / .2f );
		} else if (dt < .3f) {
			this.setAlpha(1f);
		} else if (dt < .5f) {
			this.setAlpha(1f + (.3f - dt) / .2f );
		} else {
			this.gamescene.removteTempItem(this);
			this.detachSelf();
		}
	}
}
