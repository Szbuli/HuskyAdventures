package hu.szaray.huskyadventures;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import android.graphics.Typeface;

public class ResourceManager {
	
	private static final ResourceManager instance = new ResourceManager();
	
	public Engine engine;
	public MainActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;
	
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	public ITextureRegion startBtnRegion;
	public ITextureRegion startmpBtnRegion;
	public ITextureRegion signInBtnRegion;
	public ITextureRegion signOutBtnRegion;
	public ITextureRegion optionsBtnRegion;
	public ITextureRegion achiBtnRegion;
	public ITextureRegion leaderBtnRegion;
	public ITextureRegion exitBtnRegion;
	
	private BuildableBitmapTextureAtlas mapTextureAtlas;
	public Font mapFont;
	public ITextureRegion mapboxRegion;
	public ITextureRegion mapboxmpRegion;
	
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	public Font font;
	public ITiledTextureRegion playerRegion1;
	public ITiledTextureRegion playerRegion2;
	public ITiledTextureRegion playerRegion3;
	public ITiledTextureRegion playerRegion4;
	public ITiledTextureRegion playerRegion5;
	public ITiledTextureRegion playerRegion6;
	public ITiledTextureRegion ufoRegion;
	public ITextureRegion wallRegion;
	public ITextureRegion keyRegion;
	public ITextureRegion doorRegion;
	public ITextureRegion platformRegion;
	public ITextureRegion coinRegion;
	public ITextureRegion trapRegion;
	public ITextureRegion heartRegion;
	public ITextureRegion enddoorRegion;
	public ITextureRegion yesnobgRegion;
	public ITextureRegion yesRegion;
	public ITextureRegion noRegion;
	public ITextureRegion barkRegion;
	
	private BuildableBitmapTextureAtlas bgTextureAtlas;
	public ITextureRegion backgroundRegion;

	public void loadMapResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mapTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.mapboxRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mapTextureAtlas, this.activity, "mapbox.png");
		this.mapboxmpRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mapTextureAtlas, this.activity, "mapboxmp.png");
		
		try {
			this.mapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mapTextureAtlas.load();
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	public void unloadMapResources() {
		this.mapTextureAtlas.unload();
		this.mapTextureAtlas = null;
	}
	
	public void loadBgResources() {
		this.mapFont = FontFactory.create(this.activity.getFontManager(), this.activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 35, true, Color.BLACK);
		this.mapFont.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.bgTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bgTextureAtlas, this.activity, "background.png");
		
		try {
			this.bgTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.bgTextureAtlas.load();
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	public void loadMenuResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.startBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "start.png");
		this.startmpBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "startmulti.png");
		this.signInBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "SignIn.png");
		this.signOutBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "SignOut.png");
		this.optionsBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "options.png");
		this.achiBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "achi.png");
		this.leaderBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "leaderboard.png");
		this.exitBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTextureAtlas, this.activity, "exit_sign2.png");
		
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	public void unloadMenuResources() {
		this.menuTextureAtlas.unload();
		this.menuTextureAtlas = null;
	}
	
	public void loadGameResources() {
		this.font = FontFactory.create(this.activity.getFontManager(), this.activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 22, true, Color.WHITE);
		this.font.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.playerRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_blue.png", 6, 1);
		this.playerRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_green.png", 6, 1);
		this.playerRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_pink.png", 6, 1);
		this.playerRegion4 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_red.png", 6, 1);
		this.playerRegion5 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_yellow.png", 6, 1);
		this.playerRegion6 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "wolfsprite_v3_white.png", 6, 1);
		this.coinRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "snowflake11.png");
		this.wallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "platform_no_snowy.png");
		this.keyRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "key2.png");
		this.doorRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "door.png");
		this.platformRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "platform_snowy.png");
		this.ufoRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.gameTextureAtlas, this.activity, "penguin.png", 3, 1);
		this.trapRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "csapda.png");
		this.heartRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "heart.png");
		this.enddoorRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "end_door.png");
		this.yesnobgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "yesnobg.png");
		this.yesRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "Yes.png");
		this.noRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "No.png");
		this.barkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.gameTextureAtlas, this.activity, "bark.png");
		
		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	public void unloadGameResources() {
		this.gameTextureAtlas.unload();
		this.gameTextureAtlas = null;
	}
	
	public static void initialize(Engine engine, MainActivity activity, BoundCamera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		getInstance().loadBgResources();
	}
	
	public static ResourceManager getInstance() {
		return instance;
	}
}

