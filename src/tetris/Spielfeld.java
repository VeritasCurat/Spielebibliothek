package tetris;

import java.awt.Color;

public class Spielfeld {

static boolean spielfeld_belegung[][]; //overlay: true, dieser Block ist belegt, false sonst
static Color spielfeld_farben[][]; //s: schwarz, g: gelb, b: blau, 
static Figur f;
static int breite, hoehe;

static boolean spielende = false;
	/* 
	 * s00 ... sbreite0
	 * ...
	 * shoehe0 ... shoehebreite
	 */
	

	public Spielfeld(int hoehe, int breite) {
		Spielfeld.breite = breite;
		Spielfeld.hoehe = hoehe;
		spielfeld_belegung = new boolean[hoehe][breite];
		spielfeld_farben = new Color[hoehe][breite];
		
		for(int i=0; i<hoehe; i++) {
			for(int j=0; j<breite; j++) {
				spielfeld_belegung[i][j] = false; spielfeld_farben[i][j] = Color.BLACK;
			}
		}
	}
	
	public static void print() {
		System.out.println("------------------");
		for(int i=0; i<hoehe; i++) {
			for(int j=0; j<breite; j++) {
				if(spielfeld_belegung[i][j])System.out.print("X");
				else System.out.print(" ");
			}
			System.out.println("");
		}
		System.out.println("------------------");

	}
	
	public static boolean belegung(int y, int x) {
		if(y<0 || x<0 || y>hoehe-1||x >breite-1) {
			System.out.println("FEHLER bei Index: "+y+" "+x); return true;
		}
		else {
			return spielfeld_belegung[y][x];
		}
	}
	
	public static boolean spawn_figure(String typ) {
		if(typ.equals("T")){f = new T("T", 0,0,hoehe, breite); }
		if(typ.equals("Quadrat")) {f = new Quadrat("Quadrat", 0,0,hoehe, breite); }
		if(typ.equals("Linie")) {f = new Linie("Linie", 0,0,hoehe, breite); }
		if(typ.equals("S")) {f = new S("S", 0,0,hoehe, breite); }
		if(typ.equals("S_")) {f = new S_("S_", 0,0,hoehe, breite); }
		if(typ.equals("L")) {f = new L("L", 0,0,hoehe, breite); }
		if(typ.equals("L_")) {f = new L_("L_", 0,0,hoehe, breite); }
		
		System.out.println(f.valid()+": "+f.farbe.toString());
		//Wenn spawn (0,0) belegt => Spielende //TODO
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(spielfeld_belegung[i][j] && f.form[i][j])spielende = true;
			}
		}					
		return spielende;
	}
	
	/**
	 * Wenn die Drehung geht, ausführen; sonst abbrechen
	 * @return true wenn bewegung geht, sonst false
	 */
	public static boolean bewegung_prüfen() {
		//System.out.println("y: "+f.y+"/"+hoehe+"; x: "+f.x+"/"+breite);

		
		return true;
	}
	
	public static boolean bewegen(int y, int x, int drehung) {
		if(x==1) f.schiebe_rechts();
		else if(x==-1) f.schiebe_links();
		else if(y==-1) f.schiebe_unten();
		else if(drehung==1)f.drehen_rechts();
		else if(drehung==-1)f.drehen_links();
		if(bewegung_prüfen()) return true;
		else {
			System.out.println("BACKTRACKING");
			if(x==1) f.schiebe_links();
			else if(x==-1) f.schiebe_rechts();
			else if(y==-1) f.schiebe_oben();
			else if(drehung==1)f.drehen_links();
			else if(drehung==-1)f.drehen_rechts();
			return false;
		}
	}
	
	public static void ablegen() {
			
	}
	
}
