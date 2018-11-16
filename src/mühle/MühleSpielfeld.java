package m�hle;
public class M�hleSpielfeld {
		
	static boolean automatic = true;
	static boolean spiel_aktiv=true;
	static M�hleFigur[][][] figuren = new M�hleFigur[3][3][3];
	
	static int anz_wei�=9;
	static int anz_schwarz=9;
		
	/* (XY) Koordinaten innerhalb von Ring
	 * 00 - 01 - 02
	 * |		 |
	 * 10		 12
	 * |		 |
	 * 20 - 21 - 20
	 * Dritte Koordinate Ringnummer, von au�en nach innen: 0,1,2
	 */
	
	
	public static void init() {
		for(int x=0; x<3; x++) {
			for(int y=0; y<3; y++) {
				for(int z=0; z<3; z++) {
					figuren[x][y][z] = new M�hleFigur(x, y, z, "NULL");			
				}
			}
		}
	}
	
	public static boolean setFigure(int x1, int y1, int ring, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 2 || y1 > 2) {
			System.out.println("Das ist garkein Feld");
			return false;
		}	
		//das ist nicht ihre Figur
		if(!figuren[x1][y1][ring].farbe.equals("NULL")) {
			System.out.println("Hier steht schon eine Figur von: "+figuren[x1][y1][ring].farbe);
			return false;
		}
		System.out.println("TEST");
		figuren[x1][y1][ring].typ="L�ufer"; figuren[x1][y1][ring].farbe=farbe;
		System.out.println(figuren[x1][y1][ring].farbe);
		return true;
	}
	
	public static boolean bewegungPr�fen(int x1,int y1, int z1, int x2, int y2, int z2, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 7 || y1 > 7) {
			if(!automatic)System.out.println("Das ist garkein Feld");
			return false;
		}	
		//auf dem Feld steht keine Figur
		if(figuren[x1][y1][z1].typ.equals("")) {
			if(!automatic)System.out.println("auf dem Feld steht keine Figur");
			return false;
		}
		//das ist nicht ihre Figur
		if(!figuren[x1][y1][z1].farbe.equals(farbe)) {
			if(!automatic)System.out.println("das ist nicht ihre Figur: "+x1+", "+y1);
			return false;
		}
		//eigene Figur => nein 
			//int z2 = z + (Math.abs(x1+y1) % 2)*(1-(x1+y1+(x1-x2)+(y1-y2))); 
		if(figuren[x2][y2][z2].farbe.equals(figuren[x1][y1][z1].farbe)) {
			if(!automatic)System.out.println("Hier steht schon einer ihrer Figuren: "+figuren[x2][y2][z2].typ);
			return false;
		}
		return figuren[x1][y1][z1].move(x2, y2);			
	}
	
	//wechselt f�r den Spieler der farbe f: L�ufer => Springer
	public static void tausch(String f) {
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				for(int z=0; z<3; z++) {
					if(figuren[x][y][z].farbe.equals(f))figuren[x][y][z] = new L�ufer(x, y, z, f);			
				}
			}
		}
	}
	
	public static boolean bewegungAusf�hren(int x1,int y1, int z1, int x2, int y2, int z2, String farbe){
		if(bewegungPr�fen(x1,y1,z1,x2,y2,z2,farbe)){
			//Spielende
			if(anz_wei� == 0||anz_schwarz==0)spiel_aktiv = false;
			//Zug G�ltig
			//int z2 = z + (Math.abs(x1+y1) % 2)*(1-(x1+y1+dx+dy)); 

			switch(figuren[x1][y1][z1].typ) {
				case "L�ufer": { figuren[x2][y2][z2] = new L�ufer(x2, y2, z2, farbe); break;}
				case "Springer": {figuren[x2][y2][z2] = new Springer(x2, y2, z2, farbe); break;}
			}
			
			figuren[x1][y1][z1] = new M�hleFigur(x1, y1, z1, "NULL");
			return true;
		}
		return false;
	}
	
}