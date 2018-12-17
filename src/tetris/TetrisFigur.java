package tetris;

import java.awt.Color;

public class TetrisFigur {
	int anfang_x; int anfang_y; //base point: obere linke Ecke
	int ende_x; int ende_y; //end point: untere rechte ecke
	String typ;
	static boolean form[][]; //overlay: true, dieser Block gehört zur Figur, false sonst
	static int hoehe; static int breite;
	static Color farbe; static Color farbenkasten[] = {Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED, Color.YELLOW};
	/*
	 * f00 f01 f02 f03
	 * ...
	 * f30 f31 f32 f33
	 */
	
	
	public TetrisFigur(String typ, int anfang_x, int anfang_y, int Hoehe, int Breite, Color f) {
		this.typ = typ;
		this.anfang_x = anfang_x; 
		this.anfang_y = anfang_y;
		hoehe = Hoehe; breite = Breite;
		if(!typ.equals("Linie")) {
			this.ende_x = anfang_x+3; 
			this.ende_y = anfang_y+3;
		}
		else if (typ.equals("Linie")) {
			this.ende_x = anfang_x+3; 
			this.ende_y = anfang_y+3;
		}
		form = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				form[h][b] = false;
			}
		}
		farbe = f;
	}
	
	public static boolean valid() {
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(form[h][b] && TetrisSpielfeld.belegung(h, b))return false;
			}
		}
		
		int counter=0;
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(form[h][b])++counter;
			}
		}
		if(counter != 4)return false;
		else return true;
	}
	
	public void schiebe_unten() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}
		
		for(int h=0; h<hoehe-1; h++) {
			for(int b=0; b<breite; b++) {
				form[h+1][b] = kopie[h][b];
			}
		}
		for(int b=0; b<breite; b++) {
			form[0][b] = false;
		}
		
		if(!valid()) {
			for(int h=0; h<hoehe; h++) {
				for(int b=0; b<breite; b++) {
					form[h][b] = kopie[h][b];
				}
			}
			return;
		}
		
		++anfang_y; ++ende_y;
	}
	
	public void schiebe_oben() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}
		
		for(int h=1; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				form[h][b] = kopie[h-1][b];
			}
		}
		for(int b=0; b<breite; b++) {
			form[hoehe-1][b] = false;
		}
		
		if(!valid()) {
			for(int h=0; h<hoehe; h++) {
				for(int b=0; b<breite; b++) {
					form[h][b] = kopie[h][b];
				}
			}
			return;
		}
		
		--anfang_y; --ende_y;
	}
	
	public void schiebe_rechts() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}
		
		for(int h=0; h<hoehe; h++) {
			for(int b=1; b<breite; b++) {
				form[h][b] = kopie[h][b-1];
			}
		}
		
		for(int h=0; h<hoehe; h++) {
			form[h][0] = false;
		}
		
		if(!valid()) {
			for(int h=0; h<hoehe; h++) {
				for(int b=0; b<breite; b++) {
					form[h][b] = kopie[h][b];
				}
			}
			return;
		}
		
		++anfang_x; ++ende_x;
	}
	
	public void schiebe_links() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}
		
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite-1; b++) {
				form[h][b] = kopie[h][b+1];
			}
		}
		
		for(int h=0; h<hoehe; h++) {
			form[h][breite-1] = false;
		}
		
		if(!valid()) {
			for(int h=0; h<hoehe; h++) {
				for(int b=0; b<breite; b++) {
					form[h][b] = kopie[h][b];
				}
			}
			return;
		}
		
		--anfang_x; --ende_x;
	}
	
	public boolean belegt(int y, int x) {
		if(form[y][x])return true;
		else return false;
	}
	
	public void linie_drehen() {
		if(form[anfang_y][anfang_x] && form[anfang_y][anfang_x+1] && form[anfang_y][anfang_x+2] && form[anfang_y][anfang_x+3]) {//horizontal => vertikal	
			form[anfang_y+1][anfang_x] = true; form[anfang_y+2][anfang_x] = true; form[anfang_y+3][anfang_x] = true;	
			form[anfang_y][anfang_x+1] = false; form[anfang_y][anfang_x+2] = false; form[anfang_y][anfang_x+3] = false;
			return;
		}
		if(form[anfang_y][anfang_x] && form[anfang_y+1][anfang_x] && form[anfang_y+2][anfang_x] && form[anfang_y+3][anfang_x] ) {//vertikal => horizontal	
			form[anfang_y][anfang_x+1] = true; form[anfang_y][anfang_x+2] = true; form[anfang_y][anfang_x+3] = true;
			form[anfang_y+1][anfang_x] = false; form[anfang_y+2][anfang_x] = false; form[anfang_y+3][anfang_x] =false;
			return;
		}
	}

	public void drehen_links() {
		//TODO:
			if(typ.equals("Linie")) {
				linie_drehen(); return;
			}
		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[3][3];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[3][3];	
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[b][2-h]; 
			}
		}
		
		//kopiere Teilmatrix in große Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
	}
	
	public void drehen_rechts() {
			//TODO:
			if(typ.equals("Linie")) {
				linie_drehen(); return;
			}		
			
		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[3][3];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[3][3];	
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[2-b][h]; 
			}
		}
		
		//kopiere Teilmatrix in große Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
}
	
	public void spiegeln_y() {
			//TODO:
			if(typ.equals("Linie"))return;
		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[3][3];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[3][3];	
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<3; h++) {
			for(int b=0; b<3; b++) {
				drehung[h][b] = kopie[h][2-b]; 
			}
		}
		
		//kopiere Teilmatrix in große Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
	}
}

class T extends TetrisFigur{
	/*
	 * XOO..
	 * XXO
	 * XOO
	 * ...
	 */

	public T(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x] = true;
	}
}

class Quadrat extends TetrisFigur{
	/*
	 * XXOO...
	 * XXOO
	 * OOOO
	 * ...
	 */

	
	public Quadrat(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y][anfang_x] = true;
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
	}

	
}

class Linie extends TetrisFigur{
	/*
	 * OOOO
	 * OOOO
	 * XXXX
	 * OOOO
	 */

	public Linie(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y][anfang_x] = true;
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y][anfang_x+3] = true;
	}
}

class S extends TetrisFigur{
	/*
	 * XOO
	 * XXO
	 * OXO
	 */

	public S(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class S_ extends TetrisFigur{
	/*
	 * OXO
	 * XXO
	 * XOO
	 */

	public S_(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class L extends TetrisFigur{
	/*
	 * XOO
	 * XOO
	 * XXO
	 */

	public L(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y+0][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class L_ extends TetrisFigur{
	/*
	 * OOX
	 * OOX
	 * OXX
	 */

	public L_(String typ, int anfang_x, int anfang_y, int hoehe, int breite, Color f) {
		super(typ, anfang_x, anfang_y, hoehe,breite,f);
		form[anfang_y+0][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}