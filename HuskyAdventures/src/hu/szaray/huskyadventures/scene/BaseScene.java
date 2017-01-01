package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class BaseScene extends Scene {
	protected Engine engine;
	protected VertexBufferObjectManager vbom;
	protected MainActivity activity;
	protected BoundCamera camera;
	
	public BaseScene() {
		this.engine = ResourceManager.getInstance().engine;
		this.vbom = ResourceManager.getInstance().vbom;
		this.activity = ResourceManager.getInstance().activity;
		this.camera = ResourceManager.getInstance().camera;
	}
	
	public Sprite createSprite(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom) {
		Sprite sprite = new Sprite(x, y, region, vbom) {
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		return sprite;
	}
	
	public AnimatedSprite createAnimatedSprite(float x, float y, ITiledTextureRegion region, VertexBufferObjectManager vbom) {
		AnimatedSprite sprite = new AnimatedSprite(x, y, region, vbom) {
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		return sprite;
	}
	
	public VertexBufferObjectManager getVbom() {
		return this.vbom;
	}
	public abstract void createScene();
	public abstract void onBackPressed();
	public abstract void disposeScene();
	
	public boolean isMenu () {
		return false;
	}
}
