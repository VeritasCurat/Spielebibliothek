package schach;
public class Schachbrett {
	/**
	 * Klasse die 
	 */
		
	 boolean spiel_aktiv=true;
	 SchachFigur[][] figuren = new SchachFigur[8][8];
	static final int bauer_wert = 10; 	static final int pferd_wert = 30; 	static final int läufer_wert = 30; 	static final int turm_wert = 50;	static final int dame_wert = 90; 	static final int könig_wert = 900;

	String sieger="";
	
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
	
	
	public boolean bewegungPrüfen(int x1,int y1, int x2, int y2, String farbe) {
//		if(x1==0 && x2==1 && y1==4 && y2==3) {
//			System.out.println("TEST1: "+x1+","+y1+" => "+x2+","+y2);
//			System.out.println(figuren[x2][y2].farbe);
//			System.out.println(farbe);
//			System.out.println((figuren[x2][y2].farbe.equals("")+" "+figuren[x2][y2].farbe.equals(farbe)));
//			print();
//		}

		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 7 || y1 > 7) {
			if(SchachController.hinweise == true)System.out.println("Das ist garkein Feld");
			return false;
		}	
		//auf dem Feld steht keine Figur
		if(figuren[x1][y1].typ.equals("NULL") || figuren[x1][y1].typ.equals("") || figuren[x1][y1].farbe.equals("")) {
			if(SchachController.hinweise == true)System.out.println("auf dem Feld steht keine Figur");
			return false;
		}
		//das ist nicht ihre Figur
		if(!figuren[x1][y1].farbe.equals(farbe)) {
			if(SchachController.hinweise == true)System.out.println("das ist nicht ihre Figur: "+x1+", "+y1);
			return false;
		}
		//außerhalb des Feldes => nein
		if(x2 < 0 || y2 < 0 || x2 > 7 || y2 > 7) {
			if(SchachController.hinweise == true)System.out.println("Diese Bewegung führt außerhalb des Feldes ");
			return false;
		}
		//eigene Figur => nein 
		if(figuren[x2][y2].farbe.equals(figuren[x1][y1].farbe)) {
			if(SchachController.hinweise == true)System.out.println("Hier steht schon einer ihrer Figuren: "+figuren[x2][y2].typ);
			return false;
		}
		//Rochade
		if(figuren[x1][y1] instanceof König && !((König)figuren[x1][y1]).bewegt && y1==y2 && Math.abs(x2-x1) > 1) {
			if(x1 == 4 && x2 == 6 && figuren[7][y1].typ.equals("Turm"))return !((Turm)figuren[7][y1]).bewegt;
			else if(x1 == 4 && x2 == 2 && figuren[0][y1].typ.equals("Turm"))return !((Turm)figuren[0][y1]).bewegt;
		}
	    //System.out.println(figuren[x1][y1].typ+": legale Syntax...prüfe Zugsemantik");
		
		
		
		if(figuren[x1][y1] instanceof Bauer && !((Bauer) figuren[x1][y1]).move(x2-x1,y2-y1))return false;
		if(figuren[x1][y1] instanceof Pferd && !((Pferd) figuren[x1][y1]).move(x2-x1,y2-y1))return false;
		if(figuren[x1][y1] instanceof Läufer && !((Läufer) figuren[x1][y1]).move(x2-x1,y2-y1))return false;
		if(figuren[x1][y1] instanceof Turm && !((Turm) figuren[x1][y1]).move(x2-x1,y2-y1))return false;
		if(figuren[x1][y1] instanceof Dame && !((Dame) figuren[x1][y1]).move(x2-x1,y2-y1))return false;
		if(figuren[x1][y1] instanceof König && !((König) figuren[x1][y1]).move(x2-x1,y2-y1))return false;

		
		
		//steht eine Figur dazwischen?
		//Läufer
		if(figuren[x1][y1].typ.equals("Läufer")) {
			int sigX = (int) Math.signum(x2-x1);
			int sigY = (int) Math.signum(y2-y1);
			int difX = sigX;
			int difY = sigY;
			while(x2 != x1+difY && y2 != y1+difY) {
				if(!figuren[x1+difX][y1+difY].typ.equals("")) {
					if(SchachController.hinweise == true) {
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
						if(SchachController.hinweise == true) {
							System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+x1+" "+(y1+dif)+": "+figuren[x1][y1+dif].typ);
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
						if(SchachController.hinweise == true) {
							System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+(x1+dif)+" "+y1+": "+figuren[x1+dif][y1].typ);
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
						if(SchachController.hinweise == true) {
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
						if(SchachController.hinweise == true) {
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
							if(SchachController.hinweise == true) {
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
						
					if((figuren[x2][y2].farbe.equals("") || figuren[x2][y2].farbe.equals(farbe))) {
						if(SchachController.hinweise == true) {
							System.out.println("Du kannst den Bauern nicht so bewegen ... keine Figur zum schlagen");
						}
						return false;							
					}
					}
						if(x2-x1 == 0) {
							if(!figuren[x2][y2].typ.equals(""))return false;
							int sig = (int) Math.signum(y2-y1);
							
							dif = sig;
							while(y2 != y1+dif) {
								if(!figuren[x1][y1+dif].typ.equals("")) {
									if(SchachController.hinweise == true) {
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
	
	
	
	public boolean bewegungAusführen(int x1,int y1, int x2, int y2, String farbe){
		if(x1==x2 && y1==y2) {
			spiel_aktiv=false;
			return true;
		}
		if(x1==0 && x2==0 && y1==0 && y2==0)return true;
		if(bewegungPrüfen(x1,y1,x2,y2,farbe)){
			//Rochade
			if(figuren[x1][y1] instanceof König && !((König)figuren[x1][y1]).bewegt && y1==y2 && Math.abs(x2-x1) > 1) {
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
			if(figuren[x2][y2] instanceof König) {
				if(figuren[x2][y2].farbe.equals("weiß"))sieger="schwarz";
				if(figuren[x2][y2].farbe.equals("schwarz"))sieger="weiß";
				spiel_aktiv = false;
			}
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

	public void print() {
		System.out.println("Y X| 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |");

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
	
	public static void write_ab(Schachbrett a, Schachbrett b) {
		if(a==null) {
			System.out.println("SChachbrett a NULL");
			System.exit(0);
		}
		if(b==null) {
			System.out.println("SChachbrett b NULL");
			System.exit(0);
		}
		for(int i=0; i<a.figuren.length; i++) {
			for(int j=0; j<a.figuren[0].length; j++) {
				if(a.figuren[i][j] instanceof Bauer) {
					b.figuren[i][j] = new Bauer(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				if(a.figuren[i][j] instanceof Pferd) {
					b.figuren[i][j] = new Pferd(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				if(a.figuren[i][j] instanceof Läufer) {
					b.figuren[i][j] = new Läufer(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				if(a.figuren[i][j] instanceof Turm) {
					b.figuren[i][j] = new Turm(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				if(a.figuren[i][j] instanceof Dame) {
					b.figuren[i][j] = new Dame(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				if(a.figuren[i][j] instanceof König) {
					b.figuren[i][j] = new König(a.figuren[i][j].X, a.figuren[i][j].Y, a.figuren[i][j].farbe, a.figuren[i][j].bewegt);
				}
				
				b.figuren[i][j].typ = a.figuren[i][j].typ;
				b.figuren[i][j].darstellung = a.figuren[i][j].darstellung;
			}
		}
	}
	
	public int figurenpunkte_schwarz() {
		int punkte=0;
		for(int a=0; a<8; a++) {
			for(int b=0; b<8; b++) {
				 if(figuren[a][b].farbe.equals("schwarz")) {
					switch(figuren[a][b].typ) {
						case "Bauer": {
							punkte+=bauer_wert;
							break;
						}
						case "Pferd": {
							punkte+=pferd_wert;
							break;
						}	
						case "Läufer": {
							punkte+=läufer_wert;
							break;
						}
						case "Turm": {
							punkte+=turm_wert;
							break;
						}
						case "Dame": {
							punkte+=dame_wert;
							break;
						}
						case "König": {
							punkte+=könig_wert;
							break;
						}
					}
				}
				else continue;
				
			}
		}
		return punkte;
	}
	
	public int figurenpunkte_weiß() {
		int punkte=0;
		for(int a=0; a<8; a++) {
			for(int b=0; b<8; b++) {
				if(figuren[a][b].farbe.equals("weiß")) {
					switch(figuren[a][b].typ) {
						case "Bauer": {
							punkte+=bauer_wert;
							break;
						}
						case "Pferd": {
							punkte+=pferd_wert;
							break;
						}	
						case "Läufer": {
							punkte+=läufer_wert;
							break;
						}
						case "Turm": {
							punkte+=turm_wert;
							break;
						}
						case "Dame": {
							punkte+=dame_wert;
							break;
						}
						case "König": {
							punkte+=könig_wert;
							break;
						}
					}
				}
			}
		}
		return punkte;
	}
	

}