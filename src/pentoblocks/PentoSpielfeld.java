package pentoblocks;

import java.awt.Color;

public class PentoSpielfeld {

static boolean spielfeld_belegung[][]; //overlay: true, dieser Block ist belegt, false sonst
static Color spielfeld_farben[][]; //s: schwarz, g: gelb, b: blau, 
static PentoFigur f;
static int breite, hoehe;

static boolean spielende = false;
	/* 
	 * s00 ... sbreite0
	 * ...
	 * shoehe0 ... shoehebreite
	 */
	

	public PentoSpielfeld(int hoehe, int breite) {
		PentoSpielfeld.breite = breite;
		PentoSpielfeld.hoehe = hoehe;
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
		if(typ.equals("Vogel")){f = new Vogel("Vogel", 0,0,hoehe, breite); }
		if(typ.equals("Vogel_")) {f = new Vogel_("Vogel_", 0,0,hoehe, breite); }
		if(typ.equals("P")) {f = new P("P", 0,0,hoehe, breite); }
		if(typ.equals("P_")) {f = new P("P_", 0,0,hoehe, breite); }
		if(typ.equals("T")) {f = new T("T", 0,0,hoehe, breite); }
		if(typ.equals("Schüssel")) {f = new Schüssel("Schüssel", 0,0,hoehe, breite); }
		if(typ.equals("Ecke")) {f = new Ecke("Ecke", 0,0,hoehe, breite); }
		if(typ.equals("Treppe")) {f = new Treppe("Treppe", 0,0,hoehe, breite); }
		if(typ.equals("Kreuz")) {f = new Kreuz("Kreuz", 0,0,hoehe, breite); }
		if(typ.equals("S")) {f = new S("S", 0,0,hoehe, breite); }
		if(typ.equals("S_")) {f = new S_("S_", 0,0,hoehe, breite); }

		if(typ.equals("Haken")) {f = new Haken("Haken", 0,0,hoehe, breite); }
		if(typ.equals("Haken_")) {f = new Haken_("Haken_", 0,0,hoehe, breite); }
		if(typ.equals("GroßesS")) {f = new GroßesS("GroßesS", 0,0,hoehe, breite); }
		if(typ.equals("GroßesS_")) {f = new GroßesS_("GroßesS_", 0,0,hoehe, breite); }
		if(typ.equals("GroßesL")) {f = new GroßesL("GroßesL", 0,0,hoehe, breite); }
		if(typ.equals("GroßesL_")) {f = new GroßesL_("GroßesL_", 0,0,hoehe, breite); }
		
		//Wenn spawn (0,0) belegt => Spielende //TODO
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(spielfeld_belegung[i][j] && PentoFigur.form[i][j]) {
					System.exit(0);
				}
			}
		}					
		return spielende;
	}
	
	/**
	 * Wenn die Drehung geht, ausführen; sonst abbrechen
	 * @return true wenn bewegung geht, sonst false
	 */
	public static boolean bewegung_prüfen() {
		return PentoFigur.valid();
	}
	
	/**
	 * @return true, wenn aktueller Tetraid auf Boden ist oder darunter block; sonst false
	 */
	public static boolean boden_erreicht() {
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(PentoFigur.form[h][b] && h==hoehe-1)return true;
				if(h<hoehe-1 && PentoFigur.form[h][b] && spielfeld_belegung[h+1][b])return true;
			}
		}
		return false;
	}
	
	public static void bewegen(int y, int x, int drehung) {
		System.out.println(x+" "+y+" "+drehung);
		if(x==1) f.schiebe_rechts();
		else if(x==-1) f.schiebe_links();
		else if(y==-1) f.schiebe_unten();
		else if(drehung==1)f.drehen_rechts();
		else if(drehung==-1)f.drehen_links();
		
		if(y==-1 && boden_erreicht())ablegen();
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
				PentoController.score+=PentoController.score_unit*breite;
				System.out.println(PentoController.score);
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
	
	public static void ablegen() {
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(PentoFigur.form[h][b]) {
					spielfeld_farben[h][b] = PentoFigur.farbe;
					spielfeld_belegung[h][b]= true;
				}
				
			}
		}
		
		vollzeilen_löschen();

		String figuren[] = {"Vogel", "Vogel_", "P", "P_", "T", "Schüssel", "Ecke", "Treppe", "Kreuz", "S", "S_", "Haken", "Haken_", "GroßesS", "GroßesS_", "GroßesL", "GroßesL_"};
		String auswahl = figuren[(int) (Math.random()*figuren.length-1)];
		
		
		
		PentoSpielfeld.spawn_figure(auswahl);
		PentoController.repaint();
	}
	
}
