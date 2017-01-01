package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.Messages;
import hu.szaray.huskyadventures.googleservices.Multiplayer;
import hu.szaray.huskyadventures.scene.GameScene;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.util.Log;

public class Player extends CAnimatedSprite {
	protected final long[] PLAYER_ANIMATE = new long[] { 180, 180, 180, 0, 0, 0 };
	protected final long[] PLAYER_ANIMATE_UP = new long[] { 0, 0, 0, 300, 0, 0 };
	protected final long[] PLAYER_ANIMATE_FLOAT = new long[] { 0, 0, 0, 0, 300, 0 };
	protected final long[] PLAYER_ANIMATE_DOWN = new long[] { 0, 0, 0, 0, 0, 300 };
	protected final long[] PLAYER_ANIMATE_STOP = new long[] { 300, 0, 0, 0, 0, 0 };
	
	private boolean died = false;
	private float immunityOver = 0;
	private boolean master;
	
	private enum ANIMATION_STATUS {
		UP,
		FLOAT,
		DOWN,
		GROUND,
		STOP,
	}
	
	private ANIMATION_STATUS status;
	private boolean jumped = false;
	
	public Player(float x, float y, GameScene gameScene, PhysicsWorld physics, int Id, ITiledTextureRegion region, boolean master) {
		super(x, y, gameScene, physics, Id, region);
		this.master = master;
		this.body.setUserData(this);
		this.status = ANIMATION_STATUS.STOP;
	}
	
	protected void setOrientation(float vx) {
		if (vx < -0.1) {
			this.sprite.setScale(-1, 1);
		} else if (vx > 0.1) {
			this.sprite.setScale(1, 1);
		}
	}
	
	@Override
	public void andengineOnUpdate(float lastTime, float secondsElapsed) {
		if (this.died) {
			
			this.died = false;
			this.setPosition(this.startX, this.startY);
		}
		if (this.gameScene.getPlayer()==this && this.sprite.getY() < this.gameScene.getBottomLine()) {
			this.gameScene.playerDied();
			return;
		}
		float time = this.gameScene.getTime() - this.immunityOver;
		if (time < 0) {
			if (Math.round(time*10) % 2 == 0) {
				this.sprite.setVisible(false);
			} else {
				this.sprite.setVisible(true);
			}
		} else {
			this.sprite.setVisible(true);
		}
		if (this.left) {
			this.getBody().setLinearVelocity(-4.5f, this.getBody().getLinearVelocity().y);
		} else if (this.right) {
			this.getBody().setLinearVelocity(4.5f, this.getBody().getLinearVelocity().y);
		}
		if (this.getBody().getLinearVelocity().y < -2.) {
			if (this.status != ANIMATION_STATUS.DOWN) this.sprite.animate(PLAYER_ANIMATE_DOWN);
			this.status = ANIMATION_STATUS.DOWN;
		} else if (this.getBody().getLinearVelocity().y > 2.) {
			if (this.status != ANIMATION_STATUS.UP) this.sprite.animate(PLAYER_ANIMATE_UP);
			this.status = ANIMATION_STATUS.UP;
		} else if (this.getBody().getLinearVelocity().y == 0.) {
			if (this.getBody().getLinearVelocity().x > 0.9 || this.getBody().getLinearVelocity().x < -0.9) {
				if (this.status != ANIMATION_STATUS.GROUND) this.sprite.animate(PLAYER_ANIMATE);
				this.status = ANIMATION_STATUS.GROUND;
			} else {
				if (this.status != ANIMATION_STATUS.STOP) this.sprite.animate(PLAYER_ANIMATE_STOP);
				this.status = ANIMATION_STATUS.STOP;
			}
		} else {
			//if (this.status != ANIMATION_STATUS.FLOAT) this.sprite.animate(PLAYER_ANIMATE_FLOAT);
			//this.status = ANIMATION_STATUS.FLOAT;
		}
		
		if (!this.left && !this.right && (this.status == ANIMATION_STATUS.GROUND || this.status == ANIMATION_STATUS.STOP)) {
			if (this.getBody().getLinearVelocity().x < -0.1) {
				this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x + .28f, this.getBody().getLinearVelocity().y);
			} else if (this.getBody().getLinearVelocity().x > 0.1) {
				this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x - .28f, this.getBody().getLinearVelocity().y);
			} else {
				this.getBody().setLinearVelocity(0f, this.getBody().getLinearVelocity().y);
			}
		}
		
		this.setOrientation(this.body.getLinearVelocity().x);
	}
	
	public boolean hitTest(Tile tile) {
		float dy = (float) (Math.abs(this.sprite.getY() - tile.getY()));
		float dx = (float) (Math.abs(this.sprite.getX() - tile.getX()));
		if (this.sprite.getY() > tile.getY() && dx < 30 && this.body.getLinearVelocity().y < 0.) {
			if (this.body.getLinearVelocity().y < -15.f) { 
				this.gameScene.decreseLife();
			}
			this.body.setLinearVelocity(this.body.getLinearVelocity().x, 0f);
			this.jumped = false;
		}
		if (dx > 28.9 && this.body.getLinearVelocity().x == 0.) {
			return false;
		}
		if (dy > 30.85f) {
			return false;
		}
		return true;
	}

	public boolean hitTest(Enemy enemy) {
		if(this.master && !enemy.isDead()){
			float dx = (float) (Math.abs(this.sprite.getX() - enemy.getSprite().getX()));
			if (this.sprite.getY() > enemy.getSprite().getY() + 14 && dx > 2 && this.body.getLinearVelocity().y < 0) {
				Multiplayer.getInstance().sendMessage(Messages.ENEMY_DIED, enemy.getId(), 0);
				enemy.die();
				this.gameScene.increaseScore(Globals.SCORE_FOR_ENEMY_KILL);
				this.gameScene.increaseEnemiesKilled();
				this.body.setLinearVelocity(0, 9.2f/1.4f);
			} else {
				this.body.setLinearVelocity(-this.body.getLinearVelocity().x, 9.2f/1.4f);
				if (this.immunityOver < this.gameScene.getTime()) {
					this.gameScene.decreseLife();
					this.immunityOver = this.gameScene.getTime() + Globals.immunity;
				}
			}
		}
		return false;
	}
	
	public boolean hitTest(Door tile) {
		if (tile.isOpened()) return false;
		float dx = (float) (Math.abs(this.sprite.getX() - tile.getX()));
		if (dx > 28.9 && this.body.getLinearVelocity().x == 0.) {
			if (this.master && !tile.isOpening() && !tile.isOpened() && this.gameScene.removeHUDKey()) {
				this.gameScene.openDoor(tile);
				Multiplayer.getInstance().sendMessage(Messages.DOOR_OPEN, tile.getMapX(), tile.getMapY());
			}
			return false;
		}
		return true;
	}
	
	public boolean getLeft() {
		return this.left;
	}
	
	public boolean getRight() {
		return this.right;
	}

	public void jump() {
		if (!this.jumped) {
			this.jumped = true;
			this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, 9.2f);
		}
	}

	public void hitTestForPickup(Tile tile) {
		float dy = Math.abs( this.sprite.getY() - tile.getY() );
		float dx = Math.abs( this.sprite.getX() - tile.getX() );
		if ( tile instanceof Coin ) {
			if ((dx * dx + dy * dy) < 1050) {
				this.gameScene.increaseScore(((Coin) tile).getValue());
				this.gameScene.removeTile(tile);
				Multiplayer.getInstance().sendMessage(Messages.REMOVE_COIN, tile.getMapX(), tile.getMapY());
				return;
			} 
		} if (tile instanceof Trap) {
			if (dx < 35 && dy < 20) {
				this.body.setLinearVelocity(-this.body.getLinearVelocity().x, 9.2f/1.4f);
				if (this.immunityOver < this.gameScene.getTime()) {
					this.gameScene.decreseLife();
					this.immunityOver = this.gameScene.getTime() + Globals.immunity;
				}
			}
		} else if (tile instanceof Key && dy < 30 && dx < 20) {
			this.gameScene.increaseScore(5);
			this.gameScene.pickupKey();
			this.gameScene.removeTile(tile);
			Multiplayer.getInstance().sendMessage(Messages.REMOVE_KEY, tile.getMapX(), tile.getMapY());
		} else if (tile instanceof EndDoor && (dx * dx + dy * dy) < 4500 ) {
			this.gameScene.playerWon();
		}
	}
	
	public void reset() {
		Log.d(Globals.TAG, "Player.reset");
		this.immunityOver = this.gameScene.getTime() + Globals.immunity;
		this.setPosition(this.startX, this.startY);
		this.died = true;
		this.getBody().setLinearVelocity(0, 0);
	}
}

