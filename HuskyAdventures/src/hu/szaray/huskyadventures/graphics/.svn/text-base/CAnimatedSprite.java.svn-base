package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.scene.GameScene;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

abstract public class CAnimatedSprite {
	protected Body body;
	protected AnimatedSprite sprite;
	protected int Id;
	protected boolean left = false, right = false;
	protected GameScene gameScene;
	protected float startX, startY;
	
	public CAnimatedSprite(float x, float y, GameScene gameScene, PhysicsWorld physics, int Id, ITiledTextureRegion region) {
		this.startX = x;
		this.startY = y;
		this.Id = Id;
		this.gameScene = gameScene;
		final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		this.sprite = gameScene.createAnimatedSprite(x, y, region, gameScene.getVbom());
		this.body = PhysicsFactory.createBoxBody(physics, this.sprite, BodyType.DynamicBody, playerFixtureDef);
		physics.registerPhysicsConnector(new PhysicsConnector(this.sprite, this.body, true, false));
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public Body getBody() {
		return this.body;
	}
	
	public int getId() {
		return Id;
	}
	
	public void setPosition(float x, float y) {
		this.body.setTransform(x/32f, y/32f, this.body.getAngle());
	}
	
	abstract public void andengineOnUpdate(float lastTime, float secondsElapsed);
}
