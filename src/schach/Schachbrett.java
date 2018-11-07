package schach;
public class Schachbrett {
		
	static boolean automatic = true;
	static boolean spiel_aktiv=true;
	static Figur[][] figuren = new Figur[8][8];
		
	public Schachbrett() {
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				//weiße Figure immer oben
				if(y==0 && (x==0 || x== 7))figuren[x][y] = new Turm(x, y, "weiß");
				else if(y==0 && (x==1 || x== 6))figuren[x][y] = new Pferd(x, y, "weiß");
				else if(y==0 && (x==2 || x== 5))figuren[x][y] = new Läufer(x, y, "weiß");
				else if(y==0 && (x==3))figuren[x][y] = new Dame(x, y, "weiß");
				else if(y==0 && (x==4))figuren[x][y] = new König(x, y, "weiß");
				else if(y==1)figuren[x][y] = new Bauer(x, y, "weiß");
				
				else if(y==7 && (x==0 || x== 7))figuren[x][y] = new Turm(x, y, "schwarz");
				else if(y==7 && (x==1 || x== 6))figuren[x][y] = new Pferd(x, y, "schwarz");
				else if(y==7 && (x==2 || x== 5))figuren[x][y] = new Läufer(x, y, "schwarz");
				else if(y==7 && (x==4))figuren[x][y] = new Dame(x, y, "schwarz");
				else if(y==7 && (x==3))figuren[x][y] = new König(x, y, "schwarz");
				else if(y==6)figuren[x][y] = new Bauer(x, y, "schwarz");
				
				else {
					figuren[x][y] = new Figur(x, y, "NULL");
				}

			}
		}
	}
	
	public static boolean bewegungPrüfen(int x1,int y1, int x2, int y2, String farbe) {
		//Das ist garkein Feld
		if(x1 < 0 || y1 < 0 || x1 > 7 || y1 > 7) {
			if(!automatic)System.out.println("Das ist garkein Feld");
			return false;
		}	
		//auf dem Feld steht keine Figur
		if(figuren[x1][y1].typ.equals("")) {
			if(!automatic)System.out.println("auf dem Feld steht keine Figur");
			return false;
		}
		//das ist nicht ihre Figur
		if(!figuren[x1][y1].farbe.equals(farbe)) {
			if(!automatic)System.out.println("das ist nicht ihre Figur: "+x1+", "+y1);
			return false;
		}
		//außerhalb des Feldes => nein
		if(x2 < 0 || y2 < 0 || x2 > 7 || y2 > 7) {
			if(!automatic)System.out.println("Diese Bewegung führt außerhalb des Feldes ");
			return false;
		}
		//eigene Figur => nein 
		if(figuren[x2][y2].farbe.equals(figuren[x1][y1].farbe)) {
			if(!automatic)System.out.println("Hier steht schon einer ihrer Figuren: "+figuren[x2][y2].typ);
			return false;
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
							if(!automatic)System.out.println("Du kannst den Läufer nicht so bewegen ... eine Figur steht im Weg");
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
							if(!automatic)System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+x1+" "+(y1+dif)+": "+Schachbrett.figuren[x1][y1+dif].typ);
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
							if(!automatic)System.out.println("Du kannst den Turm nicht so bewegen ... eine Figur steht im Weg: "+(x1+dif)+" "+y1+": "+Schachbrett.figuren[x1+dif][y1].typ);
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
							if(!automatic)System.out.println("Du kannst die Dame nicht so bewegen ... eine Figur steht im Weg");
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
							if(!automatic)System.out.println("Du kannst die Dame nicht so bewegen ... eine Figur steht im Weg");
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
								if(!automatic)System.out.println("Du kannst den Läufer nicht so bewegen ... eine Figur steht im Weg");
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
						String farbE;
						if((farbE = figuren[x2][y2].farbe).equals("") || farbE.equals(farbe)) {
							if(!automatic)System.out.println("Du kannst den Bauern nicht so bewegen ... keine Figur zum schlagen");
							return false;							
						}
			
					}
					else if(x2-x1 == 0) {
						if(!figuren[x2][y2].typ.equals(""))return false;
						int sig = (int) Math.signum(y2-y1);
						dif = sig;
						while(y2 != y1+dif) {
							if(!figuren[x1][y1+dif].typ.equals("")) {
								if(!automatic)System.out.println("Du kannst den Bauern nicht so bewegen ... eine Figur steht im Weg");
								return false;
							}
							dif += sig;
						}
					}
					//Bauerndame
					//if()
					
			}
			return true;			
		}
		return false;
	}
	
	public static boolean bewegungAusführen(int x1,int y1, int x2, int y2, String farbe){
		if(bewegungPrüfen(x1,y1,x2,y2,farbe)){
			//Spielende
			if(figuren[x2][y2].typ.equals("König"))spiel_aktiv = false;
			//Zug Gültig
			switch(figuren[x1][y1].typ) {
				case "Bauer": {figuren[x2][y2] = new Bauer(x2, y2, farbe); break;}
				case "Läufer": {figuren[x2][y2] = new Läufer(x2, y2, farbe); break;}
				case "Pferd": {figuren[x2][y2] = new Pferd(x2, y2, farbe); break;}
				case "Turm": {figuren[x2][y2] = new Turm(x2, y2, farbe); break;}
				case "Dame": {figuren[x2][y2] = new Dame(x2, y2, farbe); break;}
				case "König": {figuren[x2][y2] = new König(x2, y2, farbe); break;}
			}
			
			figuren[x1][y1] = new Figur(x1, y1, "NULL");
			return true;
		}
		return false;
	}
	
	public void print() {
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

}