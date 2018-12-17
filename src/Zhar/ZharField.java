package Zhar;

import java.awt.Color;
import java.util.List;

public class ZharField {
	int x,y;
	Color farbe; static Color farbenkasten[] = {Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED, Color.YELLOW};
	
	public ZharField() {
		farbe = Color.WHITE;
	}
	
}

class Boden extends ZharField{
	String spieler = "";

	public Boden(String spieler) {
		super();
		this.spieler = spieler;
		farbe = Color.GRAY;
	}

}

class Landefläche extends ZharField{
	String spieler = "";
	static int[][] landefläche_klein = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};

	public Landefläche(String spieler) {
		super();
		this.spieler = spieler;
		farbe = Color.GRAY;
	}
}

class Taramit extends ZharField{
	
	static int[][] tar_kreuz= {{0,1},{1,0},{1,1},{1,2},{2,1}};
	static int[][] tar_dia= {{0,2},{1,1},{1,2},{1,3},{2,0},{2,1},{2,2},{2,3},{2,4},{3,1},{3,2},{3,3},{4,2}};

	double units=4000;
	int i=0;
	
	public void einsammeln(int k) {
		units -= k;
		i+=30;
		i = Math.min(i, 250);
		farbe = new Color(250,i,i);
		if(units==0)farbe = Color.white;

	}
		
	public Taramit() {
		super();
		farbe = Color.magenta;
	}
}

class Stein extends ZharField{
	
	static int[][] rock1 = {{0,0},{0,1},{1,0},{1,1},{1,2},{2,2}};
	
	public Stein() {
		super();
		farbe = Color.ORANGE;
	}
}