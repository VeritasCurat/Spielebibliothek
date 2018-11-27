package tetris;

import java.awt.Color;

public class Figur {
	int anfang_x; int anfang_y; //base point: obere linke Ecke
	int ende_x; int ende_y; //end point: untere rechte ecke
	String typ;
	boolean form[][]; //overlay: true, dieser Block gehört zur Figur, false sonst
	int hoehe; int breite;
	Color farbe; Color farbenkasten[] = {Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED, Color.YELLOW};
	/*
	 * f00 f01 f02 f03
	 * ...
	 * f30 f31 f32 f33
	 */
	
	public Figur(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		this.typ = typ;
		this.anfang_x = anfang_x; 
		this.anfang_y = anfang_y;
		this.hoehe = hoehe; this.breite = breite;
		if(!typ.equals("Linie")) {
			this.ende_x = anfang_x+3; 
			this.ende_y = anfang_y+3;
		}
		form = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				form[h][b] = false;
			}
		}
		farbe = farbenkasten[(int) (Math.random()*farbenkasten.length)];
	}
	
	public boolean valid() {
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
				form[h][b] = kopie[h+1][b];
			}
		}
		for(int b=0; b<breite; b++) {
			form[0][b] = false;
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
			System.out.println(h+"/"+hoehe+" "+breite);
			form[h][0] = false;
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
			form[h][0] = false;
		}
		--anfang_x; --ende_x;
	}
	
	public boolean belegt(int y, int x) {
		if(form[y][x])return true;
		else return false;
	}
	
	public void drehen_links() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}

		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = kopie[ende_x-(b-anfang_x)][h]; 
			}
		}
	}
	
	public void drehen_rechts() {
		boolean kopie[][] = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				kopie[h][b] = form[h][b];
			}
		}
		
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = kopie[b][ende_y-(h-anfang_y)]; 
			}
		}
	}
}

class T extends Figur{
	/*
	 * XOO..
	 * XXO
	 * XOO
	 * ...
	 */

	public T(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x] = true;
	}
}

class Quadrat extends Figur{
	/*
	 * XXOO...
	 * XXOO
	 * OOOO
	 * ...
	 */

	
	public Quadrat(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x] = true;
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
	}

	
}

class Linie extends Figur{
	/*
	 * OOOO
	 * OOOO
	 * XXXX
	 * OOOO
	 */

	public Linie(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y+3][0] = true;
		form[anfang_y+3][01] = true;
		form[anfang_y+3][02] = true;
		form[anfang_y+3][03] = true;
	}
}

class S extends Figur{
	/*
	 * XOO
	 * XXO
	 * OXO
	 */

	public S(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class S_ extends Figur{
	/*
	 * OXO
	 * XXO
	 * XOO
	 */

	public S_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class L extends Figur{
	/*
	 * XOO
	 * XOO
	 * XXO
	 */

	public L(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y+0][anfang_x] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class L_ extends Figur{
	/*
	 * OOX
	 * OOX
	 * OXX
	 */

	public L_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y+0][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}