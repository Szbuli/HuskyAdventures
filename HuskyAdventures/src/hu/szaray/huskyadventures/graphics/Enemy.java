package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.scene.GameScene;

import org.andengine.extension.physics.box2d.PhysicsWorld;

public class Enemy extends CAnimatedSprite {
	private final long[] ENEMY_ANIMATE = new long[] {	200, 200, 200 };
	private int range;
	private boolean died = false;
	
	public Enemy(float x, float y, GameScene gameScene, PhysicsWorld physics, int Id, int range) {
		super(x, y, gameScene, physics, Id, ResourceManager.getInstance().ufoRegion);
		this.body.setUserData(this);
		this.sprite.animate(ENEMY_ANIMATE);
		this.right = true;
		this.range = range;
	}

	@Override
	public void andengineOnUpdate(float lastTime, float secondsElapsed) {
		if(this.died) return;
		if (this.sprite.getX() < this.startX - 32 * this.range ) {
			this.left = false;
			this.right = true;
		} else if (this.sprite.getX() > this.startX + 32 * this.range ) {
			this.left = true;
			this.right = false;
		}
		if (this.left) {
			this.getBody().setLinearVelocity(-1.5f, this.getBody().getLinearVelocity().y);
			this.sprite.setScale(-1, 1);
		} else if (this.right) {
			this.getBody().setLinearVelocity(1.5f, this.getBody().getLinearVelocity().y);
			this.sprite.setScale(1, 1);
		}
	}

	public boolean hitTest(Tile tile) {
		float dy = (float) (Math.abs(this.sprite.getY() - tile.getY()));
		float dx = (float) (Math.abs(this.sprite.getX() - tile.getX()));
		if (dy < 5) {
			if (this.sprite.getX() > tile.getX()) {
				this.left = false;
				this.right = true;
			} else {
				this.left = true;
				this.right = false;
			}
		}
		//if (dy > 30.85f) {
		if (dy > 30.85f && dx > 18f) {
			return false;
		}
		return true;
	}
	
	public boolean hitTest(Door tile) {
		if (tile.isOpened()) {
			return false;
		} else {
			return this.hitTest((Tile) tile);
		}
	}
	
	public boolean isDead() {
		return this.died;
	}

	public void die() {
		this.died = true;
		this.gameScene.addToRemoveCAnimatedSprite(this);
	}

	public void fireHitTest(float x, float y) {
		float dx = this.sprite.getX() - x;
		float dy = this.sprite.getY() - y;
		if ((dx * dx + dy * dy) < 25600) {
			if (this.left) {
				this.left = false;
				this.right = true;
			} else {
				this.right = false;
				this.left = true;
			}
		}
	}
}
