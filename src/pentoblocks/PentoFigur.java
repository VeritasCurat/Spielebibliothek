package pentoblocks;

import java.awt.Color;

public class PentoFigur {
	int anfang_x; int anfang_y; //base point: obere linke Ecke
	int ende_x; int ende_y; //end point: untere rechte ecke
	String typ;
	static boolean form[][]; //overlay: true, dieser Block gehˆrt zur Figur, false sonst
	static int hoehe; static int breite;
	static Color farbe; Color farbenkasten[] = {Color.BLUE, Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED, Color.YELLOW};
	/*
	 * f00 f01 f02 f03
	 * ...
	 * f30 f31 f32 f33
	 */
	
	public PentoFigur(String typ, int anfang_x, int anfang_y, int Hoehe, int Breite) {
		this.typ = typ;
		this.anfang_x = anfang_x; 
		this.anfang_y = anfang_y;
		hoehe = Hoehe; breite = Breite;
		if(!(typ.equals("Haken") || typ.equals("Haken_") || typ.equals("GroﬂesL") ||typ.equals("GroﬂesL_") ||typ.equals("GroﬂesS") || typ.equals("GroﬂesS_"))) {
			this.ende_x = anfang_x+3; 
			this.ende_y = anfang_y+3;
		}
		else{
			this.ende_x = anfang_x+4; 
			this.ende_y = anfang_y+4;
		}
		form = new boolean[hoehe][breite];
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				form[h][b] = false;
			}
		}
		farbe = farbenkasten[(int) (Math.random()*farbenkasten.length)];
	}
	
	public static boolean valid() {
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(form[h][b] && PentoSpielfeld.belegung(h, b))return false;
			}
		}
		
		int counter=0;
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(form[h][b])++counter;
			}
		}
		if(counter != 5)return false;
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
		int Offset =3 ;
		if(typ.equals("Haken") || typ.equals("Haken_") || typ.equals("GroﬂesL") ||typ.equals("GroﬂesL_") ||typ.equals("GroﬂesS") || typ.equals("GroﬂesS_")) {
			Offset = 4;
		}	

		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[Offset][Offset];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[Offset][Offset];	
		for(int h=0; h<Offset; h++) {
			for(int b=0; b<Offset; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<Offset; h++) {
			for(int b=0; b<Offset; b++) {
				drehung[h][b] = kopie[b][(Offset-1)-h]; 
			}
		}
		
		//kopiere Teilmatrix in groﬂe Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
	}
	
	public void drehen_rechts() {
		int offset = 3;
		if(typ.equals("Haken") || typ.equals("Haken_") || typ.equals("GroﬂesL") ||typ.equals("GroﬂesL_") ||typ.equals("GroﬂesS") || typ.equals("GroﬂesS_")) {
				offset = 4;
		}		
			
		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[offset][offset];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[offset][offset];	
		for(int h=0; h<offset; h++) {
			for(int b=0; b<offset; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<offset; h++) {
			for(int b=0; b<offset; b++) {
				drehung[h][b] = kopie[(offset-1)-b][h]; 
			}
		}
		
		//kopiere Teilmatrix in groﬂe Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
}
	
	public void spiegeln_y() {
		int Offset =3 ;
		if(typ.equals("Haken") || typ.equals("Haken_") || typ.equals("GroﬂesL") ||typ.equals("GroﬂesL_") ||typ.equals("GroﬂesS") || typ.equals("GroﬂesS_")) {
			Offset = 4;
		}	
		
		//kopiere Teil indem das Tetraid ist in kleinere Matrix
		boolean kopie[][] = new boolean[Offset][Offset];
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				kopie[h-anfang_y][b-anfang_x] = form[h][b];
			}
		}
			
		//drehe diese Teilmatrix
		boolean drehung[][] = new boolean[Offset][Offset];	
		for(int h=0; h<Offset; h++) {
			for(int b=0; b<Offset; b++) {
				drehung[h][b] = kopie[b][h]; 
			}
		}
		
		for(int h=0; h<Offset; h++) {
			for(int b=0; b<Offset; b++) {
				drehung[h][b] = kopie[h][(Offset-1)-b]; 
			}
		}
		
		//kopiere Teilmatrix in groﬂe Matrix
		for(int h=anfang_y; h<ende_y; h++) {
			for(int b=anfang_x; b<ende_x; b++) {
				form[h][b] = drehung[h-anfang_y][b-anfang_x];
			}
		}
	}
}

class Vogel extends PentoFigur{
	/*
	 * 0XX..
	 * XXO
	 * OXO
	 * ...
	 */

	public Vogel(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class Vogel_ extends Vogel{

	public Vogel_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();
	}
}

class Sch¸ssel extends PentoFigur{
	/*
	 * OOO...
	 * XOX
	 * XXX
	 * ...
	 */

	
	public Sch¸ssel(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+2] = true;
	}

	
}

class Ecke extends PentoFigur{
	/*
	 * OOX
	 * OOX
	 * XXX
	 */

	public Ecke(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+2] = true;
	}
}

class Treppe extends PentoFigur{
	/*
	 * OOX
	 * OXX
	 * XXO
	 */

	public Treppe(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class Kreuz extends PentoFigur{
	/*
	 * OXO
	 * XXX
	 * OXO
	 */

	public Kreuz(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y+1][anfang_x] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;
	}
}

class S extends PentoFigur{
	/*
	 * OXX
	 * OXO
	 * XXO
	 */

	public S(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x] = true;
		form[anfang_y+2][anfang_x+1] = true;	
	}
}

class S_ extends S{

	public S_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();
	}
}

class P extends PentoFigur{
	/*
	 * OXX
	 * OXX
	 * OXO
	 */

	public P(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+1] = true;	
	}
}


class P_ extends P{
	public P_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();	
	}
}

class T extends PentoFigur{
	/*
	 * XXX
	 * OXO
	 * OXO
	 */

	public T(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x] = true;
		form[anfang_y][anfang_x+1] = true;
		form[anfang_y][anfang_x+2] = true;
		form[anfang_y+1][anfang_x+1] = true;
		form[anfang_y+2][anfang_x+1] = true;	
	}
}

//4x4 Feld
class GroﬂesL extends PentoFigur{
	/*
	 * OOOX
	 * OOOX
	 * OOOX
	 * OOXX
	 */

	public GroﬂesL(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+3] = true;
		form[anfang_y+1][anfang_x+3] = true;
		form[anfang_y+2][anfang_x+3] = true;
		form[anfang_y+3][anfang_x+2] = true;
		form[anfang_y+3][anfang_x+3] = true;	
	}
}

class GroﬂesL_ extends GroﬂesL{
	public GroﬂesL_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();	
	}
}



class GroﬂesS extends PentoFigur{
	/*
	 * OOOX
	 * OOOX
	 * OOXX
	 * OOXO
	 */

	public GroﬂesS(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+3] = true;
		form[anfang_y+1][anfang_x+3] = true;
		form[anfang_y+2][anfang_x+3] = true;
		form[anfang_y+2][anfang_x+2] = true;
		form[anfang_y+3][anfang_x+2] = true;
	}
}


class GroﬂesS_ extends GroﬂesS{
	public GroﬂesS_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();	
	}
}

class Haken extends PentoFigur{
	/*
	 * OOOX
	 * OOXX
	 * OOOX
	 * OOOO
	 */

	public Haken(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		form[anfang_y][anfang_x+3] = true;
		form[anfang_y+1][anfang_x+3] = true;
		form[anfang_y+1][anfang_x+2] = true;
		form[anfang_y+2][anfang_x+3] = true;
		form[anfang_y+3][anfang_x+3] = true;
	}
}

class Haken_ extends Haken{
	public Haken_(String typ, int anfang_x, int anfang_y, int hoehe, int breite) {
		super(typ, anfang_x, anfang_y, hoehe,breite);
		spiegeln_y();	
	}
}