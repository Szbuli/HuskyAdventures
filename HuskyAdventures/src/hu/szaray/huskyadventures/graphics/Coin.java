package hu.szaray.huskyadventures.graphics;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Coin extends Tile {
	private int value;

	public Coin(float x, float y, ITextureRegion texture, VertexBufferObjectManager vbom, int X, int Y, int value) {
		super(x, y, texture, vbom, X, Y);
		this.value = 1;
	}

	public int getValue() {
		return this.value;
	}
}
