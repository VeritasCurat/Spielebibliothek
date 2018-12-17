package schach;
public class Schachbrett {
		
	static boolean automatic = true;
	static boolean spiel_aktiv=true;
	static SchachFigur[][] figuren = new SchachFigur[8][8];
	static boolean ghosting = false; //Für KI: falls Zug (X,Y) => (x,y) analysiert werden soll, soll (X,Y) als nicht belegt gelten
	static int ghostX=-1; static int ghostY=-1;
	
	int[][] raumwert = {{0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0},
						{1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1},
						{2,2,2,2,2,2,2,2},
						{2,2,2,2,2,2,2,2}};
	//Variablen für Rochade

		
	public Schachbrett() {
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				//weiße Figure immer oben
				if(y==0 && (x==0 || x== 7))figuren[x][y] = new Turm(x, y, "weiß",false);
				else if(y==0 && (x==1 || x== 6))figuren[x][y] = new Pferd(x, y, "weiß",false);
				else if(y==0 && (x==2 || x== 5))figuren[x][y] = new Läufer(x, y, "weiß",false);
				else if(y==0 && (x==4))figuren[x][y] = new Dame(x, y, "weiß",false);
				else if(y==0 && (x==3))figuren[x][y] = new König(x, y, "weiß",false);
				else if(y==1)figuren[x][y] = new Bauer(x, y, "weiß",false);
				
				else if(y==7 && (x==0 || x== 7))figuren[x][y] = new Turm(x, y, "schwarz",false);
				else if(y==7 && (x==1 || x== 6))figuren[x][y] = new Pferd(x, y, "schwarz",false);
				else if(y==7 && (x==2 || x== 5))figuren[x][y] = new Läufer(x, y, "schwarz",false);
				else if(y==7 && (x==4))figuren[x][y] = new Dame(x, y, "schwarz",false);
				else if(y==7 && (x==3))figuren[x][y] = new König(x, y, "schwarz",false);
				else if(y==6)figuren[x][y] = new Bauer(x, y, "schwarz",false);
				
				else {
					figuren[x][y] = new SchachFigur(x, y, "NULL",false);
				}

			}
		}
	}
	
	public static void ghosting_einfügen(int x2, int y2, String typ, String farbe) {
		switch(typ) {
			case "Bauer": {figuren[x2][y2] = new Bauer(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Läufer": {figuren[x2][y2] = new Läufer(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Pferd": {figuren[x2][y2] = new Pferd(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Turm": {figuren[x2][y2] = new Turm(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Dame": {figuren[x2][y2] = new Dame(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "König": {figuren[x2][y2] = new König(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
		}
	}
	
	/**
	 * Für KI: beide FIguren werden gelöscht: (x1,y1) und (x2,y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void ghosting_löschen(int x1, int y1, int x2, int y2) {
		ghostX = x2; ghostY = y2;
		figuren[x1][y1] = new SchachFigur(x1, y1, "",figuren[x1][y1].bewegt);
		figuren[x2][y2] = new SchachFigur(x2, y2, "",figuren[x2][y2].bewegt);
	}
	
	/**
	 * Für KI: beide Figuren werden wiederhergestellt: (x1,y1) und (x2,y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void ghosting_zurückziehen(int x1, int y1, int x2, int y2, String gegnerfigur, String figur, String farbe) {
		switch(figur) {
			case "Bauer": {figuren[x2][y2] = new Bauer(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Läufer": {figuren[x2][y2] = new Läufer(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Pferd": {figuren[x2][y2] = new Pferd(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Turm": {figuren[x2][y2] = new Turm(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "Dame": {figuren[x2][y2] = new Dame(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
			case "König": {figuren[x2][y2] = new König(x2, y2, farbe,figuren[x2][y2].bewegt); break;}
		}
		
		switch(gegnerfigur) {
			case "Bauer": {figuren[x1][y1] = new Bauer(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			case "Läufer": {figuren[x1][y1] = new Läufer(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			case "Pferd": {figuren[x1][y1] = new Pferd(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			case "Turm": {figuren[x1][y1] = new Turm(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			case "Dame": {figuren[x1][y1] = new Dame(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			case "König": {figuren[x1][y1] = new König(x1, y1, "weiß",figuren[x1][y1].bewegt); break;}
			default: {figuren[x1][y1] = new SchachFigur(x1, y1, "NULL",figuren[x1][y1].bewegt); break;}
		}
		ghostX = ghostY = -1;

	}
	
	public static boolean bewegungPrüfen(int x1,int y1, int x2, int y2, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 7 || y1 > 7) {
			if(automatic == false)System.out.println("Das ist garkein Feld");
			return false;
		}	
		//auf dem Feld steht keine Figur
		if(figuren[x1][y1].typ.equals("")) {
			if(automatic == false)System.out.println("auf dem Feld steht keine Figur");
			return false;
		}
		//das ist nicht ihre Figur
		if(!figuren[x1][y1].farbe.equals(farbe)) {
			if(automatic == false)System.out.println("das ist nicht ihre Figur: "+x1+", "+y1);
			return false;
		}
		//außerhalb des Feldes => nein
		if(x2 < 0 || y2 < 0 || x2 > 7 || y2 > 7) {
			if(automatic == false)System.out.println("Diese Bewegung führt außerhalb des Feldes ");
			return false;
		}
		//eigene Figur => nein 
		if(figuren[x2][y2].farbe.equals(figuren[x1][y1].farbe)) {
			if(automatic == false)System.out.println("Hier steht schon einer ihrer Figuren: "+figuren[x2][y2].typ);
			return false;
		}
		//Rochade
		if(figuren[x1][y1].typ.equals("König") && !((König)figuren[x1][y1]).bewegt && y1==y2 && Math.abs(x2-x1) > 1) {
			if(x1 == 4 && x2 == 6 && figuren[7][y1].typ.equals("Turm"))return !((Turm)figuren[7][y1]).bewegt;
			else if(x1 == 4 && x2 == 2 && figuren[0][y1].typ.equals("Turm"))return !((Turm)figuren[0][y1]).bewegt;
		}
	    //System.out.println(figuren[x1][y1].typ+": legale Syntax...prüfe Zugsemantik");
		if(figuren[x1][y1].move(x2-x1,y2-y1)) {
			//steht eine Figur dazwischen?
			//Läufer
				if(figuren[x1][y1].typ.equals("Läufer")) {
					int sigX = (int) Math.signum(x2-x1);
					int sigY = (int) Math.signum(y2-y1);
					int difX = sigX;
					int difY = sigY;
					while(x2 != x1+difY && y2 != y1+difY) {
						if(!figuren[x1+difX][y1+difY].typ.equals("")) {
							if(automatic == false) {
								System.out.println("Du kannst den Läufer nicht so bewegen ... eine Figur steht im Weg");
							}
							return false;
						}
						difX += sigX; difY += sigY;
					}
				}
			//Turm
			if(figuren[x1][y1].typ.equals("Turm")) {
				if(x1 == x2) {
					int sig = (int) Math.signum(y2-y1);
					int dif = sig;
					while(y2 != y1+dif) {
						if(!figuren[x1][y1+dif].typ.equals("")) {
							if(automatic == false) {
								System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+x1+" "+(y1+dif)+": "+Schachbrett.figuren[x1][y1+dif].typ);
							}
							return false;
						}
						dif += sig;
					}
				}
				else if(y1 == y2){
					int sig = (int) Math.signum(x2-x1);
					int dif = sig;
					while(x2 != x1+dif) {
						if(!figuren[x1+dif][y1].typ.equals("")) {
							if(automatic == false) {
								System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+(x1+dif)+" "+y1+": "+Schachbrett.figuren[x1+dif][y1].typ);
							}
							return false;
						}
						dif += sig;
					}
				}
				else return false;
			}
			//Dame
			if(figuren[x1][y1].typ.equals("Dame")) {
				if(x1 == x2) {
					int sig = (int) Math.signum(y2-y1);
					int dif = sig;
					while(y2 != y1+dif) {
						if(!figuren[x1][y1+dif].typ.equals("")) {
							if(automatic == false) {
								System.out.println("Du kannst die Dame nicht so bewegen ... eine Figur steht im Weg");
							}
							return false;
						}
						dif += sig;
					}
				}
				else if(y1 == y2){
					int sig = (int) Math.signum(x2-x1);
					int dif = sig;
					while(x2 != x1+dif) {
						if(!figuren[x1+dif][y1].typ.equals("")) {
							if(automatic == false) {
								System.out.println("Du kannst die Dame nicht so bewegen ... eine Figur steht im Weg");
							}
							return false;
						}
						dif += sig;
					}
				}
				else if(Math.abs(x2-x1) == Math.abs(y2-y1)) {
						int sigX = (int) Math.signum(x2-x1);
						int sigY = (int) Math.signum(y2-y1);
						int difX = sigX;
						int difY = sigY;
						while(x2 != x1+difY && y2 != y1+difY) {
							if(!figuren[x1+difX][y1+difY].typ.equals("")) {
								if(automatic == false) {
									System.out.println("Du kannst den Läufer nicht so bewegen ... eine Figur steht im Weg");
								}
								return false;
							}
							difX += sigX; difY += sigY;
						}
				}
			}
			//Bauer
			if(figuren[x1][y1].typ.equals("Bauer")) {
					int dif;
					if((dif = x2-x1) != 0) {
						//System.out.println("TESTUS:" +x1+" "+y1+": "+figuren[x2][y2].farbe);
						if(!ghosting && (figuren[x2][y2].farbe.equals("") || figuren[x2][y2].farbe.equals(SchachController.farbe))) {
							if(automatic == false) {
								System.out.println("Du kannst den Bauern nicht so bewegen ... keine Figur zum schlagen");
							}
							return false;							
						}
						if(ghosting && !(figuren[x2][y2].farbe.equals("") || figuren[x2][y2].farbe.equals(SchachController.farbe))) {
							if(ghostX == x2 && ghostY == y2);
							else return false;
						}
					}
					else if(x2-x1 == 0) {
						if(!figuren[x2][y2].typ.equals("") || (ghosting && ghostX==x2 && ghostY==y2))return false;
						int sig = (int) Math.signum(y2-y1);
						dif = sig;
						while(y2 != y1+dif) {
							if(!figuren[x1][y1+dif].typ.equals("") || (ghosting && ghostX==x2 && ghostY==y2)) {
								if(automatic == false) {
									System.out.println("Du kannst den Bauern nicht so bewegen ... eine Figur steht im Weg");
								}
								return false;
							}
							dif += sig;
						}
					}					
			}
			return true;			
		}
		return false;
	}
	
	
	
	public static boolean bewegungAusführen(int x1,int y1, int x2, int y2, String farbe){
		if(bewegungPrüfen(x1,y1,x2,y2,farbe) || ghosting){
			//Rochade
			if(figuren[x1][y1].typ.equals("König") && !((König)figuren[x1][y1]).bewegt && y1==y2 && Math.abs(x2-x1) > 1) {
				if(x1 == 4 && x2 == 6 && figuren[7][y1].typ.equals("Turm")) { //kurze Rochade			
					System.out.println("kurze Rochade");
					figuren[x2][y2] = new König(x2, y2, farbe,true);
					figuren[x2-1][y2] = new Turm(x2-1, y2, farbe,true);
					figuren[7][y2] = new SchachFigur(7, y2, "NULL",false);	
				}
				else if(x1 == 4 && x2 == 2 && figuren[0][y1].typ.equals("Turm")) { //lange Rochade
					System.out.println("lange Rochade");
					figuren[x2][y2] = new König(x2, y2, farbe,true);
					figuren[x2+1][y2] = new Turm(x2+1, y2, farbe,true);
					figuren[0][y2] = new SchachFigur(0, y2, "NULL",false);	
				}
				figuren[x1][y1] = new SchachFigur(x1, y1, "NULL",false);
				return true;
			}
			//Spielende
			if(figuren[x2][y2].typ.equals("König") && !ghosting)spiel_aktiv = false;
			//Zug Gültig
			switch(figuren[x1][y1].typ) {
				case "Bauer": {figuren[x2][y2] = new Bauer(x2, y2, farbe,true); break;}
				case "Läufer": {figuren[x2][y2] = new Läufer(x2, y2, farbe,true); break;}
				case "Pferd": {figuren[x2][y2] = new Pferd(x2, y2, farbe,true); break;}
				case "Turm": {figuren[x2][y2] = new Turm(x2, y2, farbe,true); break;}
				case "Dame": {figuren[x2][y2] = new Dame(x2, y2, farbe,true); break;}
				case "König": {figuren[x2][y2] = new König(x2, y2, farbe,true); break;}
			}
			
			figuren[x1][y1] = new SchachFigur(x1, y1, "NULL",false);
			return true;
		}
		return false;
	}
	
	public static void print() {
		System.out.println("Y X| A | B | C | D | E | F | G | H |");

		for(int y=0; y<8; y++) {
			System.out.println("---+---+---+---+---+---+---+---+---+");
			System.out.print(" "+y+" ");
			System.out.print("|");
			for(int x=0; x<8; x++) {
				System.out.print(" "+figuren[x][y].darstellung+" ");
				System.out.print("|");
			}
			System.out.println();
		}
		System.out.println("---+---+---+---+---+---+---+---+---+");
	}

	
	//TODO: NEU: in andere Version einfügen
	/** wieviele Felder kontrolliert der Spieler mit Farbe?
	 * @return 
	 */
	public int feldpunkte(String Farbe) {
		int punkte=0;
		int[][] p = new int[8][8];		
		
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(Farbe.equals("weiß") && !figuren[x1][y1].farbe.equals("weiß"))continue;
				else if(Farbe.equals("schwarz") && !figuren[x1][y1].farbe.equals("schwarz"))continue;
				for(int x2=0; x2<8; x2++) {
					for(int y2=0; y2<8; y2++) {
						if(Farbe.equals("weiß") && bewegungPrüfen(x1, y1, x2, y2, "weiß")) {
							++p[x2][y2];
							continue;
						}
						else if(Farbe.equals("schwarz") && !bewegungPrüfen(x1, y1, x2, y2, "schwarz")) {
							--p[x2][y2];
							continue;
						}
					}
				}
			}
		}
		
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(Farbe.equals("weiß") && p[x1][y1]>0)punkte+=raumwert[x1][y1];
				if(Farbe.equals("schwarz") && p[x1][y1]<0)punkte+=raumwert[x1][7-y1];
			}
		}
		return punkte;
	}
}