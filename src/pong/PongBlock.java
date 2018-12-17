package pong;

import java.awt.Color;

public class PongBlock {
	int x, y, sizeX, sizeY;
	
	int leben=3;  boolean explosiv =false;  boolean alive = true;
	
	String effekt=""; String typ="";
	
	double rückschlag_einfluss=1; //wenn 1 dann 
	
	static Color[] farbenkasten = {Color.RED, Color.BLUE, Color.GREEN, Color.cyan, Color.yellow, Color.ORANGE}; 
	Color c;

	public PongBlock(int x, int y, int sizeX, int sizeY, String typ) {
		this.x = x; this.y = y;
		this.sizeX = sizeX; this.sizeY = sizeY;
		
		switch(typ) {
			case "normal":{
				c = Color.LIGHT_GRAY;
				leben = 1;
				break;
			}
			case "hart":{
				c = Color.DARK_GRAY;
				leben = 3;
				rückschlag_einfluss=1.2;
				break;
			}
			case "spezial":{
				c = Color.ORANGE;
				leben = 1;
				rückschlag_einfluss=1;
				String effektliste[] = {"ball verkleinern", "ball vergrößern", "kelle verkleinern", "kelle vergrößern", "geschwindigkeit verkleinern", "geschwindigkeit vergrößern"};
				effekt = effektliste[(int) (Math.random()*effektliste.length-1)];
				//effekt = "ball verkleinern";
				break;
			}
			case "explosiv":{
				c = Color.YELLOW;
				leben = 1;
				rückschlag_einfluss=1;
				explosiv = true;
				break;
			}
		}
	}
	
	public void getroffen() {
		--leben;
		c = c.brighter();
		if(!effekt.equals("")) {
			PongSpielfeld.effekt_einfügen(effekt);
		}
		if(leben <= 0) {
			alive = false;
		}
	}
}
