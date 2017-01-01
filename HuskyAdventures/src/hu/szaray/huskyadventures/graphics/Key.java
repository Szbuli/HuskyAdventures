package hu.szaray.huskyadventures.graphics;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Key extends Tile {

	public Key(float x, float y, ITextureRegion texture, VertexBufferObjectManager vbom, int X, int Y) {
		super(x, y, texture, vbom, X, Y);
	}
}
