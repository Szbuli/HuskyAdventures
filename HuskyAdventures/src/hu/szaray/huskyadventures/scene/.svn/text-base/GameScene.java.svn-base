package hu.szaray.huskyadventures.scene;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.Messages;
import hu.szaray.huskyadventures.R;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.Multiplayer;
import hu.szaray.huskyadventures.graphics.CAnimatedSprite;
import hu.szaray.huskyadventures.graphics.Door;
import hu.szaray.huskyadventures.graphics.EndDoor;
import hu.szaray.huskyadventures.graphics.Enemy;
import hu.szaray.huskyadventures.graphics.Key;
import hu.szaray.huskyadventures.graphics.Map;
import hu.szaray.huskyadventures.graphics.Player;
import hu.szaray.huskyadventures.graphics.Tile;
import hu.szaray.huskyadventures.graphics.TimeAnimatedTile;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameScene extends BaseScene {
	private HUD gameHUD;
	private PhysicsWorld physics;
	private ContactListener contactListener;
	private Player player;
	private Player friend;
	private Map map;
	private Rectangle bg;
	private Text scoreText;
	
	private float lastTime = .0f;
	
	private int enemiesKilled = 0;
	private int score = 0;
	private int life = 5;
	private Tile[] lifes = new Tile[5];
	private ArrayList<Tile> keys = new ArrayList<Tile>();
	
	private ArrayList<Tile> toremove =  new ArrayList<Tile>();
	private ArrayList<CAnimatedSprite> toremoveCAnimatedSprite =  new ArrayList<CAnimatedSprite>();
	
	private Rectangle touchLeft;
	private Rectangle touchRight;
	private Rectangle touchUp;
	private Rectangle touchFire;
	private boolean yesnoOn = false;
	private Sprite yesbtn;
	private Sprite nobtn;
	private Sprite yesnobg;
	
	private boolean isAlone = false;
	private boolean friendWon = false;
	private boolean won = false;
	
	
	
	private ArrayList<TimeAnimatedTile> tempItems = new ArrayList<TimeAnimatedTile>();
		
	@Override
	public void createScene() {
		this.setBackground(new Background(new Color(0.313f,0.541f,937f)));
		this.createPhysics();
		this.createHUD();
		this.map = new Map(this, this.vbom, this.physics);
		if (Game.getInstance().getGameType()) {
			if (Multiplayer.getInstance().isMaster()) {
				this.player = this.map.getPlayer0();
				this.friend = this.map.getPlayer1();
			} else {
				this.player = this.map.getPlayer1();
				this.friend = this.map.getPlayer0();
			}
		} else {
			this.player = this.map.getPlayer0();
		}
		this.camera.setChaseEntity(this.player.getSprite());
		this.createContectListener();
		this.physics.setContactListener(this.contactListener);
		this.registerUpdateHandler(new IUpdateHandler(){
			@Override
			public void onUpdate(float pSecondsElapsed) {
				GameScene.this.andengineOnUpdate(pSecondsElapsed);
			}
			@Override
			public void reset() {
				
			}
		});
		Multiplayer.getInstance().setGameScene(this);
	}
	
	public void increaseEnemiesKilled () {
		this.enemiesKilled++;
	}
	
	public void gameEnded(boolean naturealend) {
		if (naturealend) {
			int mapnum = Game.getInstance().getMapNumber()+1;
			if (Game.getInstance().getGameType()) {
				switch (mapnum) {
				case 1:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_multiplayer_level_1));
					break;
				case 2:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_multiplayer_level_2));
					break;
				case 3:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_multiplayer_level_3));
					break;
				case 4:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_multiplayer_level_4));
					break;					
				}
			} else {
				switch (mapnum) {
				case 1:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_singleplayer_level_1));
					break;
				case 2:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_singleplayer_level_2));
					break;
				case 3:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_singleplayer_level_3));
					break;
				case 4:
					Game.getInstance().unlockAchievement(this.activity.getString(R.string.achievement_singleplayer_level_4));
					break;	
				}
			}
			
			Game.getInstance().incrementAchievement(this.activity.getString(R.string.achievement_hunter), enemiesKilled);
			Game.getInstance().incrementAchievement(this.activity.getString(R.string.achievement_beginner), 1);
			Game.getInstance().incrementAchievement(this.activity.getString(R.string.achievement_expert), 1);
			Game.getInstance().incrementAchievement(this.activity.getString(R.string.achievement_hardcore), 1);
			Game.getInstance().incrementAchievement(this.activity.getString(R.string.achievement_medium), 1);
			Game.getInstance().updateLeaderboard(this.score);
			
			MainActivity activity = ResourceManager.getInstance().activity;
			Game.getInstance().showToast(activity.getString(R.string.GAME_END_won));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Multiplayer.getInstance().setGameScene(null);
		this.activity.leaveRoom();
		SceneManager.getInstance().setMenuScene(null);
		
	}
	
	public float getTime() {
		return this.lastTime;
	}
	
	private void andengineOnUpdate(float secondsElapsed) {
		this.map.andengineOnUpdate(this.lastTime, secondsElapsed);
		this.lastTime += secondsElapsed;
		for (int i = this.toremove.size() - 1 ; i > -1  ; i--) {
			this.removeTile(this.toremove.remove(i));
		}
		for (int i = this.toremoveCAnimatedSprite.size() - 1 ; i > -1  ; i--) {
			this.toremoveCAnimatedSprite.remove(i).getSprite().detachSelf();
		}
		for (int i = 0 ; i < this.tempItems.size() ; i++) {
			this.tempItems.get(i).onAndengineUpdate(lastTime);
		}
		if (Game.getInstance().getGameType()) Multiplayer.getInstance().sendPosition(this.player.getSprite().getX(), this.player.getSprite().getY(), this.player.getBody().getLinearVelocity().x, this.player.getBody().getLinearVelocity().y, this.player.getId());
	}
	
	public void addToRemoveCAnimatedSprite (CAnimatedSprite toremove) {
		this.toremoveCAnimatedSprite.add(toremove);
	}
	
	public void addToRemoveTile (Tile toremove) {
		this.toremove.add(toremove);
	}

	private void createContectListener() {
		this.contactListener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				if ( contact.getFixtureA().getBody().getUserData() instanceof Tile && contact.getFixtureB().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureB().getBody().getUserData()).hitTest((Tile) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Tile && contact.getFixtureA().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureA().getBody().getUserData()).hitTest((Tile) contact.getFixtureB().getBody().getUserData()));
				}
			}

			@Override
			public void endContact(Contact contact) {
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				if ( contact.getFixtureA().getBody().getUserData() instanceof Door && contact.getFixtureB().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureB().getBody().getUserData()).hitTest((Door) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Door && contact.getFixtureA().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureA().getBody().getUserData()).hitTest((Door) contact.getFixtureB().getBody().getUserData()));
				/* *** ***  *** *** *** *** */
				} else if ( contact.getFixtureA().getBody().getUserData() instanceof Door && contact.getFixtureB().getBody().getUserData() instanceof Enemy ) {
					contact.setEnabled(((Enemy) contact.getFixtureB().getBody().getUserData()).hitTest((Door) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Door && contact.getFixtureA().getBody().getUserData() instanceof Enemy ) {
					contact.setEnabled(((Enemy) contact.getFixtureA().getBody().getUserData()).hitTest((Door) contact.getFixtureB().getBody().getUserData()));
				/* *** ***  *** *** *** *** */
				} else if ( contact.getFixtureA().getBody().getUserData() instanceof EndDoor && contact.getFixtureB().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureB().getBody().getUserData()).hitTest((EndDoor) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof EndDoor && contact.getFixtureA().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureA().getBody().getUserData()).hitTest((EndDoor) contact.getFixtureB().getBody().getUserData()));
				/* *** *** *** *** *** *** */
				} else if ( contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureB().getBody().getUserData()).hitTest((Enemy) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Enemy && contact.getFixtureA().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureA().getBody().getUserData()).hitTest((Enemy) contact.getFixtureB().getBody().getUserData()));
				/* *** *** *** *** *** *** */
				} else if ( contact.getFixtureA().getBody().getUserData() instanceof Tile && contact.getFixtureB().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureB().getBody().getUserData()).hitTest((Tile) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Tile && contact.getFixtureA().getBody().getUserData() instanceof Player ) {
					contact.setEnabled(((Player) contact.getFixtureA().getBody().getUserData()).hitTest((Tile) contact.getFixtureB().getBody().getUserData()));
				/* *** *** *** *** *** *** */
				} else if ( contact.getFixtureA().getBody().getUserData() instanceof Tile && contact.getFixtureB().getBody().getUserData() instanceof Enemy ) {
					contact.setEnabled(((Enemy) contact.getFixtureB().getBody().getUserData()).hitTest((Tile) contact.getFixtureA().getBody().getUserData()));
				} else if ( contact.getFixtureB().getBody().getUserData() instanceof Tile && contact.getFixtureA().getBody().getUserData() instanceof Enemy ) {
					contact.setEnabled(((Enemy) contact.getFixtureA().getBody().getUserData()).hitTest((Tile) contact.getFixtureB().getBody().getUserData()));
				}
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
			
		};
	}
	
	public void readMessage (int message, float value0, float value1) {
		switch (message) {
			case Messages.BARK:
				TimeAnimatedTile bark = new TimeAnimatedTile(value0, value1, vbom, this, lastTime);
				this.map.fireHitTest(value0, value1);
				this.attachChild(bark);
				this.tempItems.add(bark);
				return;
		}
	}
	
	public void removteTempItem(TimeAnimatedTile item) {
		this.tempItems.remove(item);
	}
	
	public void readMessage (int message, int value0, int value1) {
		switch (message) {
			case Messages.REMOVE_COIN:
				this.toremove.add(this.map.getTile(value0, value1));
				return;
			case Messages.REMOVE_KEY:
				this.toremove.add(this.map.getTile(value0, value1));
				return;
			case Messages.DOOR_OPEN:
				this.openDoor((Door) this.map.getTile(value0, value1));
				return;
			case Messages.FRIEND_WON:
				this.friendWon = true;
				return;
			case Messages.FRIEND_DIED:
				return;
			case Messages.ENEMY_DIED:
				((Enemy) this.map.getCAnimatedSprite(value0)).die();
				return;
		}
	}
	
	public void openDoor(Door tile) {
		if (!tile.isOpening() && !tile.isOpened()) {
			tile.open(this);
		}
	}

	public void readPosition (float x, float y, float vx, float vy, int charID) {
		CAnimatedSprite sprite = this.map.getCAnimatedSprite(charID);
		sprite.setPosition(x, y);
		sprite.getBody().setLinearVelocity(vx, vy);
	}
	

	@Override
	public void onBackPressed() {
		this.addYesNo();
	}

	@Override
	public void disposeScene() {
		Multiplayer.getInstance().setGameScene(null);
		ResourceManager.getInstance().unloadGameResources();
		this.camera.setHUD(null);
		this.dispose();
	}
	
	public void setCameraBounds(int xmin, int ymin, int xmax, int ymax) {
		this.camera.setBounds(xmin, ymin, xmax, ymax);
		this.camera.setBoundsEnabled(true);
	}

	private void moveLeft(boolean ended) {
		if (ended) {
			this.player.setLeft(false);
		} else {
			this.player.setRight(false);
			this.player.setLeft(true);
		}
	}

	private void moveRight(boolean ended) {
		if (ended) {
			this.player.setRight(false);
		} else {
			this.player.setLeft(false);
			this.player.setRight(true);
		}
	}

	private void moveJump() {
		this.player.jump();
	}

	private void createHUD() {
		gameHUD = new HUD();
		
		this.touchLeft = new Rectangle(100, 240, 200, 400, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					GameScene.this.moveLeft(true);
				} else if (touchEvent.isActionDown()) {
					GameScene.this.moveLeft(false);
				}
				return true;
			};
		};
		
		this.touchRight = new Rectangle(700, 240, 200, 400, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					GameScene.this.moveRight(true);
				} else if (touchEvent.isActionDown()) {
					GameScene.this.moveRight(false);
				}
				return true;
			};
		};
		
		this.touchUp = new Rectangle(400, 380-32, 400, 200, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionDown()) {
					GameScene.this.moveJump();
				}
				return true;
			};
		};
		
		this.touchFire = new Rectangle(400, 100, 400, 200, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionDown()) {
					GameScene.this.fire();
				}
				return true;
			};
		};
		
		touchLeft.setVisible(false);
		touchRight.setVisible(false);
		touchUp.setVisible(false);
		touchFire.setVisible(false);
		gameHUD.registerTouchArea(touchLeft);
		gameHUD.registerTouchArea(touchRight);
		gameHUD.registerTouchArea(touchUp);
		gameHUD.registerTouchArea(touchFire);
		gameHUD.attachChild(touchLeft);
		gameHUD.attachChild(touchRight);
		gameHUD.attachChild(touchUp);
		gameHUD.attachChild(touchFire);
		bg = new Rectangle(400, 480 - 16, 800, 32, vbom);
		bg.setColor(Color.BLACK);
		gameHUD.attachChild(bg);
		Text lifeText = new Text(30, 480 + 16 - 32, ResourceManager.getInstance().font, "Life:", this.vbom);
		gameHUD.attachChild(lifeText);
		for (int i = 0 ; i < 5 ; i++) {
			lifes[i] = new Tile(75 + i * 30, 480 + 16 - 32, ResourceManager.getInstance().heartRegion, vbom, i, i);
			gameHUD.attachChild(lifes[i]);
		}
		scoreText = new Text(30+300, 480 + 16 - 32, ResourceManager.getInstance().font, "Score: 000" + this.score, this.vbom);
		gameHUD.attachChild(scoreText);
		Text itemText = new Text(30+550, 480 + 16 - 32, ResourceManager.getInstance().font, "Items:", this.vbom);
		gameHUD.attachChild(itemText);
		
		this.yesnobg = new Sprite(400, 80, ResourceManager.getInstance().yesnobgRegion, this.vbom){
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		this.yesbtn = new Sprite(300, 80, ResourceManager.getInstance().yesRegion, this.vbom){
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					if (GameScene.this.won) {
						GameScene.this.gameEnded(true);
					} else {
						GameScene.this.gameEnded(false);
					}
					
				}
				return true;
			};
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		this.nobtn = new Sprite(500, 80, ResourceManager.getInstance().noRegion, this.vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					GameScene.this.removeYesNo();
				}
				return true;
			};
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		
		camera.setHUD(this.gameHUD);
	}
	
	private void addYesNo() {
		if (!this.yesnoOn) {
			if (this.player.getSprite().getY() < this.map.getBottomLine() + 200) {
				this.yesnobg.setPosition(400, 480-80 - 64);
				this.yesbtn.setPosition(300, 480-80 - 64);
				this.nobtn.setPosition(500, 480-80 - 64);
			} else {
				this.yesnobg.setPosition(400, 80);
				this.yesbtn.setPosition(300, 80);
				this.nobtn.setPosition(500, 80);
			}
			this.yesnoOn = true;
			this.gameHUD.unregisterTouchArea(this.touchLeft);
			this.gameHUD.unregisterTouchArea(this.touchRight);
			this.gameHUD.unregisterTouchArea(this.touchUp);
			this.gameHUD.unregisterTouchArea(this.touchFire);
			this.gameHUD.attachChild(this.yesnobg);
			this.gameHUD.attachChild(this.yesbtn);
			this.gameHUD.attachChild(this.nobtn);
			this.gameHUD.registerTouchArea(this.yesbtn);
			this.gameHUD.registerTouchArea(this.nobtn);
		}
	}
	
	protected void removeYesNo() {
		if (this.yesnoOn) {
			this.yesnoOn = false;
			this.gameHUD.registerTouchArea(this.touchLeft);
			this.gameHUD.registerTouchArea(this.touchRight);
			this.gameHUD.registerTouchArea(this.touchUp);
			this.gameHUD.registerTouchArea(this.touchFire);
			this.yesnobg.detachSelf();
			this.yesbtn.detachSelf();
			this.nobtn.detachSelf();
			this.gameHUD.unregisterTouchArea(this.yesbtn);
			this.gameHUD.unregisterTouchArea(this.nobtn);
		}
	}
	
	protected void fire() {
		float barkx = this.player.getSprite().getX();
		float barky = this.player.getSprite().getY();
		Multiplayer.getInstance().sendMessage(Messages.BARK, barkx, barky);
		TimeAnimatedTile bark = new TimeAnimatedTile(barkx, barky, vbom, this, lastTime);
		this.map.fireHitTest(barkx, barky);
		this.attachChild(bark);
		this.tempItems.add(bark);
	}
	
	private void createPhysics() {
		this.physics = new FixedStepPhysicsWorld(60, new Vector2(0, -19), false);
		this.registerUpdateHandler(this.physics);
	}
	
	public void increaseScore(int value) {
		this.score += value;
		if (this.score < 0) this.score = 0;
		this.updateHud();
	}
	
	private void updateHud() {
		String zeros = "";
		if (this.score<10) {
			zeros="000";
		} else if (this.score<100) {
			zeros = "00";
		} else if (this.score<1000) {
			zeros = "0";
		}		
			
		scoreText.setText("Score: " + zeros+this.score);
		Log.d(Globals.TAG, "score: " + this.score);
	}
	
	public void removeTile(Tile tile) {
		if (tile == null) return;
		tile.detachSelf();
		this.map.removeTile(tile);
	}
	
	public void decreseLife() {
		this.life--;
		if (this.life > 0) {
			this.lifes[this.life].detachSelf();
		} else {
			this.playerDied();
		}
	}
	
	public void pickupKey() {
		Key key = new Key(30 + 550 + 40 + this.keys.size() * 14, 480 + 16 - 32, ResourceManager.getInstance().keyRegion, vbom, 0, 0);
		this.gameHUD.attachChild(key);
		this.keys.add(key);
	}
	
	public boolean removeHUDKey() {
		if (this.keys.size() > 0) {
			this.keys.remove(this.keys.size() - 1).detachSelf();
			return true;
		}
		return false;
	}
	
	public int getBottomLine() {
		return this.map.getBottomLine();
	}
	
	public void playerDied() {
		this.increaseScore(-10);
		this.player.reset();
		for (int i = life ; i < 5 ; i++) {
			lifes[i] = new Tile(75 + i * 30, 480 + 16 - 32, ResourceManager.getInstance().heartRegion, vbom, i, i);
			gameHUD.attachChild(lifes[i]);
		}
		this.life = 5;
		//Multiplayer.getInstance().sendMessage(Messages.FRIEND_DIED, 0, 0);
	}

	public void playerWon() {
		this.gameHUD.unregisterTouchArea(this.touchLeft);
		this.gameHUD.unregisterTouchArea(this.touchRight);
		this.gameHUD.unregisterTouchArea(this.touchUp);
		this.gameHUD.unregisterTouchArea(this.touchFire);
		Multiplayer.getInstance().sendMessage(Messages.FRIEND_WON, 0, 0);
		if (Game.getInstance().getGameType()) {
			if (this.friendWon || this.isAlone) {
				this.gameEnded(true);
			} else if (!this.won) {
				MainActivity activity = ResourceManager.getInstance().activity;
				Game.getInstance().showToast(activity.getString(R.string.GAME_END_won_can_exit));
				this.won = true;
				this.camera.setChaseEntity(this.friend.getSprite());
			}
		} else {
			this.gameEnded(true);
		}
	}
	
	public void setGameAlone () {
		this.isAlone = true;
	}

	public void frinedLeft() {
		if (this.won) {
			this.gameEnded(true);
		} else {
			MainActivity activity = ResourceManager.getInstance().activity;
			Game.getInstance().showToast(activity.getString(R.string.GAME_other_left));
		}
	}
	public Player getPlayer() {
		return this.player;
	}
}
