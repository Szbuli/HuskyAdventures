package hu.szaray.huskyadventures.graphics;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class StableTile extends Tile {
	protected Body body;
	protected PhysicsWorld physics;
	protected PhysicsConnector connector;
	protected FixtureDef playerFixtureDef;

	public StableTile(float x, float y, ITextureRegion texture, VertexBufferObjectManager vbom, PhysicsWorld physics, int X, int Y) {
		super(x, y, texture, vbom, X, Y);
		this.physics = physics;
		this.playerFixtureDef = PhysicsFactory.createFixtureDef(0f, .0f, .75f);
		this.body = PhysicsFactory.createBoxBody(physics, this, BodyType.StaticBody, playerFixtureDef);
		this.body.setUserData(this);
		this.connector = new PhysicsConnector(this, body, true, false);
		physics.registerPhysicsConnector(this.connector);
	}

}
