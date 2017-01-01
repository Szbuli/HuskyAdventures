package hu.szaray.huskyadventures.graphics;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Tile extends Sprite {
	private int X, Y;

	public Tile(float x, float y, ITextureRegion texture, VertexBufferObjectManager vbom, int X, int Y) {
		super(x, y, texture, vbom);
		this.X = X;
		this.Y = Y;
	}
	
	public int getMapX() {
		return this.X;
	}
	
	public int getMapY() {
		return this.Y;
	}
}
