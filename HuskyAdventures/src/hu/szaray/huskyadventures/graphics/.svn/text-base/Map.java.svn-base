package hu.szaray.huskyadventures.graphics;

import hu.szaray.huskyadventures.Globals;
import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.Preferences;
import hu.szaray.huskyadventures.R;
import hu.szaray.huskyadventures.ResourceManager;
import hu.szaray.huskyadventures.googleservices.Game;
import hu.szaray.huskyadventures.googleservices.Multiplayer;
import hu.szaray.huskyadventures.scene.GameScene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class Map {
	private ArrayList<Tile> tiles;
	private ArrayList<Tile> hitTiles;
	private ArrayList<CAnimatedSprite> aSprites;
	private int bottomline;

	public Map(GameScene gameScene, VertexBufferObjectManager vbom, PhysicsWorld physics) {
		this.tiles = new ArrayList<Tile>();
		this.hitTiles = new ArrayList<Tile>();
		this.aSprites = new ArrayList<CAnimatedSprite>();
		
		if (false) {
			;
		}
		/*
		 int mapVersion=-1;
		 mapVersion=Integer.parseInt(Game.getInstance().readFromInternalStorage("list.txt").get(0));
	     Log.d("Husky adv", "mapVersion"+mapVersion);
		 if (Game.getInstance().getMapVersion()==mapVersion) {
		 
			Log.d(Globals.TAG, "VAN INTERNAL STORAGE");			
		} /*else if (!Multiplayer.getInstance().isInternetConnected()) {
			//Toast.makeText(this., "Error! No Internet connection available!", Toast.LENGTH_LONG).show();
			//TODO ERROR, NINCS NET
		}*/else {
			try {
			    // Create a URL for the desired page
			    URL url;
			    if (Game.getInstance().getGameType()) {
			    	url= new URL( Game.getInstance().getMpMapList().get(Game.getInstance().getMapNumber()) );
			    } else {
			    	url= new URL( Game.getInstance().getSpMapList().get(Game.getInstance().getMapNumber()) );
			    }
	
			    // Read all the text returned by the server
			    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			    this.createMap(in, gameScene, vbom, physics);	    

			} catch (Exception e) {
				MainActivity activity = ResourceManager.getInstance().activity;
				Game.getInstance().showToast(activity.getString(R.string.ERROR_while_downloading));
				Log.d(Globals.TAG, e.toString());
			} 
		}
	}
	
	public Tile getTile(int x, int y) {
		for (int i = 0 ; i < tiles.size() ; i++) {
			Tile tile = this.tiles.get(i);
			if (tile.getMapX() == x && tile.getMapY() == y) return tile;
		}
		return null;
	}
	
	public CAnimatedSprite getCAnimatedSprite(int Id) {
		for(int i = 0 ; i < this.aSprites.size() ; i++) {
			if (this.aSprites.get(i).getId() == Id) return this.aSprites.get(i);
		}
		return null;
	}
	public Player getPlayer0() {
		return (Player) this.getCAnimatedSprite(0);
	}

	public Player getPlayer1() {
		return (Player) this.getCAnimatedSprite(1);
	}

	public void andengineOnUpdate(float lastTime, float secondsElapsed) {
		for (int i = 0 ; i < this.aSprites.size() ; i++) {
			this.aSprites.get(i).andengineOnUpdate(lastTime, secondsElapsed);
		}
		for (int n = 0 ; n < this.hitTiles.size() ; n++) {
			if ( !Game.getInstance().getGameType() || Multiplayer.getInstance().isMaster() ) {
				((Player) this.getCAnimatedSprite(0)).hitTestForPickup(this.hitTiles.get(n));
			} else {
				((Player) this.getCAnimatedSprite(1)).hitTestForPickup(this.hitTiles.get(n));
			}
		}
	}

	public void removeTile(Tile tile) {
		this.hitTiles.remove(tile);
		this.tiles.remove(tile);
	}
	private void createMap(BufferedReader in, GameScene gameScene, VertexBufferObjectManager vbom, PhysicsWorld physics) {
		String str;
		int Id = 2;
		int x = 0, y = 0;
	    try {
			for (y = 0 ; (str = in.readLine()) != null ; y++) {
				
				String[] split = str.split("\t");
				for (x = 0 ; x < split.length ; x++) {
					if (split[x].equals("")) {
						;
					} else if (split[x].charAt(0) == '0') {// Nothing
						
					} else if (split[x].charAt(0) == '1') {// Wall
						Tile tile = new StableTile(16 + x * 32, - y * 32 - 16, ResourceManager.getInstance().platformRegion, vbom, physics, x, y);
						this.tiles.add(tile);
						gameScene.attachChild(tile);
					} else if (split[x].charAt(0) == '2') {// Coin
						Tile tile = new Coin(16 + x * 32, - y * 32 - 16, ResourceManager.getInstance().coinRegion, vbom, x, y, 1);
						this.hitTiles.add(tile);
						this.tiles.add(tile);
						gameScene.attachChild(tile);
					} else if (split[x].charAt(0) == '3') {// Key
						Tile tile = new Key(16 + x * 32, - y * 32 - 16, ResourceManager.getInstance().keyRegion, vbom, x, y);
						this.hitTiles.add(tile);
						this.tiles.add(tile);
						gameScene.attachChild(tile);
					} else if (split[x].charAt(0) == '4') {// Door
						if (!(this.getTile(x, y - 1) instanceof Door)) {
							Tile tile = new Door(16 + x * 32, - y * 32 - 32, ResourceManager.getInstance().doorRegion, vbom, physics, x, y);
							this.tiles.add(tile);
			    			gameScene.attachChild(tile);
						}
					} else if (split[x].charAt(0) == '5') {// Trap
						Tile tile = new Trap(16 + x * 32, - y * 32 - 16, ResourceManager.getInstance().trapRegion, vbom, x, y);
						this.hitTiles.add(tile);
						this.tiles.add(tile);
						gameScene.attachChild(tile);
					} else if (split[x].charAt(0) == '6') {// UFO
						Enemy enemy = new Enemy(16 + x * 32, - y * 32 - 16, gameScene, physics, Id++, Character.getNumericValue(split[x].charAt(2)));
						this.aSprites.add(enemy);
						gameScene.attachChild(enemy.getSprite());
					} else if (split[x].charAt(0) == 'a') {// Player0
						int color = Preferences.getInstance().getColor();
						Player player;
						if ( !Game.getInstance().getGameType() ) {
							player = this.getPlayer(16 + x * 32, - y * 32 - 16, gameScene, physics, 0, color, true);
						} else {
							if (Multiplayer.getInstance().isMaster()) {
								player = this.getPlayer(16 + x * 32, - y * 32 - 16, gameScene, physics, 0, color, true);
							} else {
								Random r = new Random();
								int c = r.nextInt(4) + 1;
								if (color <= c) c++;
								player = this.getPlayer(16 + x * 32, - y * 32 - 16, gameScene, physics, 0, c, false);
							}
						}
						this.aSprites.add(player);
						gameScene.attachChild(player.getSprite());
					} else if (split[x].charAt(0) == 'b') {// Player1
						int color = Preferences.getInstance().getColor();
						Player player;
						if (!Multiplayer.getInstance().isMaster()) {
							player = this.getPlayer(16 + x * 32, - y * 32 - 16, gameScene, physics, 1, color, true);
						} else {
							Random r = new Random();
							int c = r.nextInt(4) + 1;
							if (color <= c) c++;
							player = this.getPlayer(16 + x * 32, - y * 32 - 16, gameScene, physics, 1, c, false);
						}
						this.aSprites.add(player);
						gameScene.attachChild(player.getSprite());
					} else if (split[x].charAt(0) == '9') {// ?
						Tile tile = new StableTile(16 + x * 32, - y * 32 - 16, ResourceManager.getInstance().wallRegion, vbom, physics, x, y);
						this.tiles.add(tile);
						gameScene.attachChild(tile);
					} else if (split[x].charAt(0) == '7') {// End door
						if ( !(this.getTile(x - 1, y) instanceof EndDoor || this.getTile(x, y - 1) instanceof EndDoor || this.getTile(x - 1, y - 1) instanceof EndDoor)) {
			    			Tile tile = new EndDoor(32 + x * 32, - y * 32 - 32, ResourceManager.getInstance().enddoorRegion, vbom, x, y);
			    			this.hitTiles.add(tile);
			    			this.tiles.add(tile);
			    			gameScene.attachChild(tile);
						}
					} else {
						Log.d(Globals.TAG, "Map.Map(): extra Tile");
					}
				}
				
			}
			in.close();
			} catch (Exception e) {
				MainActivity activity = ResourceManager.getInstance().activity;
				Game.getInstance().showToast(activity.getString(R.string.ERROR_while_creating_map));
				Log.d(Globals.TAG, e.toString());
				e.printStackTrace();
			}
		    gameScene.setCameraBounds(0, -y * 32, x * 32, 32);
		    this.bottomline = -y * 32;
	}
	
	private Player getPlayer(float x, float y, GameScene gameScene, PhysicsWorld physics, int Id, int color, boolean master) {
		if (color == 1) {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion1, master);
		} else if (color == 2) {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion2, master);
		} else if (color == 3) {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion3, master);
		} else if (color == 4) {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion4, master);
		} else if (color == 5) {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion5, master);
		} else {
			return new Player(x, y, gameScene, physics, Id, ResourceManager.getInstance().playerRegion6, master);
		}
	}
	
	public int getBottomLine() {
		return this.bottomline;
	}

	public void fireHitTest(float x, float y) {
		for (int i = 0 ; i < this.aSprites.size() ; i++) {
			if (this.aSprites.get(i) instanceof Enemy ) {
				((Enemy) this.aSprites.get(i)).fireHitTest(x, y);
			}
		}
	}
}
