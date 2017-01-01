package hu.szaray.huskyadventures.googleservices;

import hu.szaray.huskyadventures.MainActivity;
import hu.szaray.huskyadventures.Messages;
import hu.szaray.huskyadventures.scene.GameScene;
import hu.szaray.huskyadventures.scene.SceneManager;

import java.nio.ByteBuffer;

public class Multiplayer {
	
	private MainActivity mainActivity;	
	private GameScene gameScene;
	private static Multiplayer multiplayer = new Multiplayer();
	private boolean isMaster;
	
	private byte[] MessageBuffer = new byte[21];
	private byte[] MessageBufferSmall = new byte[13];
	private byte[] ReadBuffer = new byte[4];
	
	
	public void setMainActivity (MainActivity main) {
		mainActivity=main;
	}
	public void setGameScene (GameScene game) {
		gameScene = game;
	}
	
	public static Multiplayer getInstance () {
		return multiplayer;
	}
	
	private byte[] floattoByte (float f) {
		 return ByteBuffer.allocate(4).putFloat(f).array();
	}
	
	private float bytetoFloat (byte [] b) {		
		return ByteBuffer.wrap(b).getFloat();		
	}
	
	private byte[] inttoByte (int f) {
		 return ByteBuffer.allocate(4).putInt(f).array();
	}
	
	private int bytetoInt (byte [] b) {		
		return ByteBuffer.wrap(b).getInt();		
	}
	
	public void readMessage (byte [] m) {

		if (m[0]==0) {
			ReadBuffer[0]= m[1];
			ReadBuffer[1]= m[2];
			ReadBuffer[2]= m[3];
			ReadBuffer[3]= m[4];
			int temp = bytetoInt(ReadBuffer);
			
			ReadBuffer[0]= m[5];
			ReadBuffer[1]= m[6];
			ReadBuffer[2]= m[7];
			ReadBuffer[3]= m[8];
			
			int value0 = bytetoInt(ReadBuffer);
			
			ReadBuffer[0]= m[9];
			ReadBuffer[1]= m[10];
			ReadBuffer[2]= m[11];
			ReadBuffer[3]= m[12];
			
			int value1 = bytetoInt(ReadBuffer);		
			if (temp==Messages.CAN_START_GAME) {
				SceneManager.getInstance().setOtherPlayerReady();
				return;
			}
			
			gameScene.readMessage(temp, value0, value1);	
		} else if (m[0]==1) {
			ReadBuffer[0]= m[1];
			ReadBuffer[1]= m[2];
			ReadBuffer[2]= m[3];
			ReadBuffer[3]= m[4];
			float x = bytetoFloat(ReadBuffer);
			
			ReadBuffer[0]= m[5];
			ReadBuffer[1]= m[6];
			ReadBuffer[2]= m[7];
			ReadBuffer[3]= m[8];
			float y = bytetoFloat(ReadBuffer);
			
			ReadBuffer[0]= m[9];
			ReadBuffer[1]= m[10];
			ReadBuffer[2]= m[11];
			ReadBuffer[3]= m[12];
			float vx = bytetoFloat(ReadBuffer);
			
			ReadBuffer[0]= m[13];
			ReadBuffer[1]= m[14];
			ReadBuffer[2]= m[15];
			ReadBuffer[3]= m[16];
			float vy = bytetoFloat(ReadBuffer);
			
			ReadBuffer[0]= m[17];
			ReadBuffer[1]= m[18];
			ReadBuffer[2]= m[19];
			ReadBuffer[3]= m[20];
			int charID = bytetoInt(ReadBuffer);
			
			if (this.gameScene!=null) {
				gameScene.readPosition(x, y, vx, vy, charID);
			}
		} else	if (m[0]==3) {
			ReadBuffer[0]= m[1];
			ReadBuffer[1]= m[2];
			ReadBuffer[2]= m[3];
			ReadBuffer[3]= m[4];
			int bark = bytetoInt(ReadBuffer);
			
			ReadBuffer[0]= m[5];
			ReadBuffer[1]= m[6];
			ReadBuffer[2]= m[7];
			ReadBuffer[3]= m[8];
			
			float barkx = bytetoFloat(ReadBuffer);
			
			ReadBuffer[0]= m[9];
			ReadBuffer[1]= m[10];
			ReadBuffer[2]= m[11];
			ReadBuffer[3]= m[12];
			
			float barky = bytetoFloat(ReadBuffer);	
			
			gameScene.readMessage(bark, barkx, barky);
		}
	}
	
	public void sendMessage (int message, int value0, int value1) {
		MessageBufferSmall[0]=0;	//normál üzenet küldése jelzõ
		byte[] temp = inttoByte(message);
		MessageBufferSmall[1]=temp[0];
		MessageBufferSmall[2]=temp[1];
		MessageBufferSmall[3]=temp[2];
		MessageBufferSmall[4]=temp[3];
		
		temp = inttoByte(value0);
		MessageBufferSmall[5]=temp[0];
		MessageBufferSmall[6]=temp[1];
		MessageBufferSmall[7]=temp[2];
		MessageBufferSmall[8]=temp[3];
		
		temp = inttoByte(value1);
		MessageBufferSmall[9]=temp[0];
		MessageBufferSmall[10]=temp[1];
		MessageBufferSmall[11]=temp[2];
		MessageBufferSmall[12]=temp[3];		
		
		mainActivity.sendRealTimeMessageRealiable(MessageBufferSmall);
		
	}
	
	public void sendPosition (float x, float y, float vx, float vy, int charID) {
		MessageBuffer[0]=1;	//pozició küldése jelzõ
		byte[] temp = floattoByte(x);
		MessageBuffer[1]=temp[0];
		MessageBuffer[2]=temp[1];
		MessageBuffer[3]=temp[2];
		MessageBuffer[4]=temp[3];
		
		temp = floattoByte(y);
		MessageBuffer[5]=temp[0];
		MessageBuffer[6]=temp[1];
		MessageBuffer[7]=temp[2];
		MessageBuffer[8]=temp[3];
				
		temp = floattoByte(vx);
		MessageBuffer[9]=temp[0];
		MessageBuffer[10]=temp[1];
		MessageBuffer[11]=temp[2];
		MessageBuffer[12]=temp[3];
		
		temp = floattoByte(vy);
		MessageBuffer[13]=temp[0];
		MessageBuffer[14]=temp[1];
		MessageBuffer[15]=temp[2];
		MessageBuffer[16]=temp[3];
		
		temp = inttoByte(charID);
		MessageBuffer[17]=temp[0];
		MessageBuffer[18]=temp[1];
		MessageBuffer[19]=temp[2];
		MessageBuffer[20]=temp[3];
		
		mainActivity.sendRealTimeMessageUnRealiable(MessageBuffer);
	}
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	public boolean isMaster() {
		//Log.d(Globals.TAG, "LEFUT_ISMASTER");
		return this.isMaster;
	}
	public void connectionLost() {
		if (Game.getInstance().getGameType()) {
			if (this.gameScene != null) {
				this.gameScene.frinedLeft();
			}
		}
	}
	
	public boolean isInternetConnected () {
		return this.mainActivity.isInternetConnected();		
	}
	public void sendMessage(int bark, float barkx, float barky) {
		MessageBufferSmall[0]=3;	//bark üzenet küldése jelzõ
		byte[] temp = inttoByte(bark);
		MessageBufferSmall[1]=temp[0];
		MessageBufferSmall[2]=temp[1];
		MessageBufferSmall[3]=temp[2];
		MessageBufferSmall[4]=temp[3];
		
		temp = floattoByte(barkx);
		MessageBufferSmall[5]=temp[0];
		MessageBufferSmall[6]=temp[1];
		MessageBufferSmall[7]=temp[2];
		MessageBufferSmall[8]=temp[3];
		
		temp = floattoByte(barky);
		MessageBufferSmall[9]=temp[0];
		MessageBufferSmall[10]=temp[1];
		MessageBufferSmall[11]=temp[2];
		MessageBufferSmall[12]=temp[3];		
		
		mainActivity.sendRealTimeMessageRealiable(MessageBufferSmall);
	}	
}
