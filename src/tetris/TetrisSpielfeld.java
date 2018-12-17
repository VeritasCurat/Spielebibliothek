package tetris;

import java.awt.Color;
import java.io.File;

public class TetrisSpielfeld {

static boolean spielfeld_belegung[][]; //overlay: true, dieser Block ist belegt, false sonst
static Color spielfeld_farben[][]; //s: schwarz, g: gelb, b: blau, 
static TetrisFigur tetrisfigur; static String currentfigur=""; static Color currentColor;
//static TetrisFigur Nextfigur; 
static String nextfigur=""; static Color nextColor; static boolean nextdarstellung[][] = new boolean[3][4];

static int breite, hoehe;

static boolean spielende = false;
	/* 
	 * s00 ... sbreite0
	 * ...
	 * shoehe0 ... shoehebreite
	 */
	

	
	public static void init(int hoehe, int breite) {
		TetrisSpielfeld.breite = breite;
		TetrisSpielfeld.hoehe = hoehe;
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
	
	public static void highscore_speichern() {
		//TODO
			 
	}
	
	public static boolean spawn_figure(String typ, Color f) {
		if(typ.equals("T")){tetrisfigur = new T("T",breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("Quadrat")) {tetrisfigur = new Quadrat("Quadrat",breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("Linie")) {tetrisfigur = new Linie("Linie", breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("S")) {tetrisfigur = new S("S", breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("S_")) {tetrisfigur = new S_("S_", breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("L")) {tetrisfigur = new L("L", breite/2-1,0,hoehe, breite,f); }
		if(typ.equals("L_")) {tetrisfigur = new L_("L_", breite/2-1,0,hoehe, breite,f); }
		
		//Wenn spawn (0,0) belegt => Spielende //TODO
		for(int i=0; i<4; i++) {
			for(int j=breite/2-1; j<breite/2+3; j++) {
				if(spielfeld_belegung[i][j] && TetrisFigur.form[i][j]) {
					System.exit(0);
				}
			}
		}					
		return spielende;
	}
	
	public static void nextdar() {
		for(int zeile=0; zeile<3; zeile++) {
			for(int spalte=0; spalte<4; spalte++) {
				nextdarstellung[zeile][spalte] = false;
			}
		}	
		
		if(nextfigur.equals("T")){
			nextdarstellung[0][0] = true;
			nextdarstellung[0+1][0] = true;
			nextdarstellung[0+1][0+1] = true;
			nextdarstellung[0+2][0] = true;
		}			
		if(nextfigur.equals("Quadrat")) {
			nextdarstellung[0][0] = true;
			nextdarstellung[0][0+1] = true;
			nextdarstellung[0+1][0] = true;
			nextdarstellung[0+1][0+1] = true;
		}
		if(nextfigur.equals("Linie")) {
			nextdarstellung[0][0] = true;
			nextdarstellung[0][0+1] = true;
			nextdarstellung[0][0+2] = true;
			nextdarstellung[0][0+3] = true;
		}
		if(nextfigur.equals("S")) {
			nextdarstellung[0][0] = true;
			nextdarstellung[0+1][0] = true;
			nextdarstellung[0+1][0+1] = true;
			nextdarstellung[0+2][0+1] = true;
		}
		if(nextfigur.equals("S_")) {
			nextdarstellung[0][0+2] = true;
			nextdarstellung[0+1][0+1] = true;
			nextdarstellung[0+1][0+2] = true;
			nextdarstellung[0+2][0+1] = true;
		}
		if(nextfigur.equals("L")) {
			nextdarstellung[0+0][0] = true;
			nextdarstellung[0+1][0] = true;
			nextdarstellung[0+2][0] = true;
			nextdarstellung[0+2][0+1] = true;
		}
		if(nextfigur.equals("L_")) {
			nextdarstellung[0+0][0+2] = true;
			nextdarstellung[0+1][0+2] = true;
			nextdarstellung[0+2][0+2] = true;
			nextdarstellung[0+2][0+1] = true;
		}
		
	
		
	}
	
	/**
	 * Wenn die Drehung geht, ausführen; sonst abbrechen
	 * @return true wenn bewegung geht, sonst false
	 */
	public static boolean bewegung_prüfen() {
		return TetrisFigur.valid();
	}
	
	/**
	 * @return true, wenn aktueller Tetraid auf Boden ist oder darunter block; sonst false
	 */
	public static boolean boden_erreicht() {
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(TetrisFigur.form[h][b] && h==hoehe-1)return true;
				if(h<hoehe-1 && TetrisFigur.form[h][b] && spielfeld_belegung[h+1][b])return true;
			}
		}
		return false;
	}
	
	public static void bewegen(int y, int x, int drehung) {
		if(x==1) tetrisfigur.schiebe_rechts();
		else if(x==-1) tetrisfigur.schiebe_links();
		else if(y==-1) tetrisfigur.schiebe_unten();
		else if(drehung==1)tetrisfigur.drehen_rechts();
		else if(drehung==-1)tetrisfigur.drehen_links();
		
		if(y==-1 && boden_erreicht()) {
			ablegen();
		}
	}
	
	/**
	 * löscht Zeilen aus Spielfeld die voll sind und verschiebt das Feld entsprechend
	 */
	public static void vollzeilen_löschen() {
		int cnt=0;
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(spielfeld_belegung[h][b])++cnt;
			}
			if(cnt == breite) {//Zeile löschen
				TetrisController.score+=TetrisController.score_unit*breite;
				System.out.println(TetrisController.score);
				for(int H=h; H>0; H--) {
					for(int B=0; B<breite; B++) {
						spielfeld_belegung[H][B] = spielfeld_belegung[H-1][B];
						spielfeld_farben[H][B] = spielfeld_farben[H-1][B];
					}
				}
			}
			cnt=0;
		}
	}
	
	
	public static String RetString(String a) {return a;}
	
	public static void ablegen() {
		if(TetrisGUI.currentkey=='a') {
			TetrisController.bewegung(0,-1,0);
		}
		else if(TetrisGUI.currentkey=='d') {
			TetrisController.bewegung(0,1,0);
		}
	
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(TetrisFigur.form[h][b]) {
					spielfeld_farben[h][b] = TetrisFigur.farbe;
					spielfeld_belegung[h][b]= true;
				}
				
			}
		}
		
		vollzeilen_löschen();
				
		currentfigur = String.copyValueOf(nextfigur.toCharArray()); currentColor = nextColor;
		TetrisSpielfeld.spawn_figure(TetrisSpielfeld.currentfigur,TetrisSpielfeld.currentColor);	
		TetrisController.repaint();

		
		String figuren[] = {"L","L_","S","S_", "Quadrat","T", "Linie"};
		TetrisSpielfeld.nextfigur = figuren[(int) (Math.random()*figuren.length)];
		TetrisSpielfeld.nextColor = TetrisFigur.farbenkasten[(int) (Math.random()*TetrisFigur.farbenkasten.length)];
		TetrisSpielfeld.nextdar();
		
		
	}
	
}
