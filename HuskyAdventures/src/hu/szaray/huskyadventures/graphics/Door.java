package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.scene.GameScene;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Door extends StableTile {
	private boolean opening = false;
	private boolean opened = false;
	private GameScene gamescene;

	public Door(float x, float y, ITextureRegion texture, VertexBufferObjectManager vbom, PhysicsWorld physics, int X, int Y) {
		super(x, y, texture, vbom, physics, X, Y);
	}

	public boolean isOpening() {
		return opening;
	}

	public void open(GameScene gamescene) {
		this.gamescene = gamescene;
		this.opening = true;
		//TODO
		this.opened = true;
		this.physics.unregisterPhysicsConnector(this.connector);
		this.gamescene.addToRemoveTile(this);
	}

	public boolean isOpened() {
		return opened;
	}
}
