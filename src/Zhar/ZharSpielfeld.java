package Zhar;

/**
 * alle felder im Kartesichen Koordinatensyste:
 * als Koordinate (x,y) oder 
 * als Dimension (breite,hoehe) 
 * 
 */

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZharSpielfeld {

static boolean spielfeld_belegung[][]; //overlay: true, dieser Block ist belegt, false sonst
static Color spielfeld_farben[][]; //s: schwarz, g: gelb, b: blau, 

//Liste von Figuren
static ArrayList<ZharFigur> zharfiguren = new ArrayList<ZharFigur>(); static int auswahl=-1; //figur die von Spieler ausgewählt wird



//static TetrisFigur Nextfigur; 

static int breite, hoehe;

static boolean spielende = false;
	/* 
	 * s00 ... sbreite0
	 * ...
	 * shoehe0 ... shoehebreite
	 */
	

	
	public static void init(int breite, int hoehe) {
		ZharFigur.init(breite, hoehe);
		
		
		ZharSpielfeld.breite = breite;
		ZharSpielfeld.hoehe = hoehe;
		spielfeld_belegung = new boolean[breite][hoehe];
		spielfeld_farben = new Color[breite][hoehe];
		
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				spielfeld_belegung[b][h] = false; spielfeld_farben[b][h] = Color.BLACK;
			}
		}
		
		ZharSpielfeld.spawnFigur("Bauer", 0, 1, 1);
		ZharSpielfeld.spawnFigur("Sammler", 0, 2, 1);

		ZharSpielfeld.spawnFigur("Bauer", 1, 3, 5);
	}
	
	public static void print() {
		System.out.println("------------------");
		for(int h=0; h<hoehe; h++) {
			for(int b=0; b<breite; b++) {
				if(spielfeld_belegung[b][h])System.out.print("X");
				else System.out.print(" ");
			}
			System.out.println("");
		}
		System.out.println("------------------");

	}
	
	public static boolean belegung(int x, int y) {
		if(x<0 || y<0 || y>hoehe-1||x >breite-1) {
			System.out.println("FEHLER bei Index: "+x+" "+y); return true;
		}
		else {
			return spielfeld_belegung[x][y];
		}
	}
	
	public static boolean bewegen(List<Integer[]> l) throws InterruptedException {
		for(int i=1; i<l.size(); i++) {
			if(!bewegung_prüfen(l.get(i)[0], l.get(i)[1]))return false;
		}
		if(auswahl>-1 && zharfiguren.get(auswahl).spieler != ZharController.currentSpieler){
			ShowCanvas.setzeNachricht("Das ist nicht deine Figur: "+ZharSpielfeld.zharfiguren.get(auswahl));
			return false;
		}
		if(auswahl>-1 && zharfiguren.get(auswahl) instanceof Fahrzeug) {//nur Fahrzeuge können sich bewegen		
			ShowCanvas.setzeNachricht("Bewege "+ZharSpielfeld.zharfiguren.get(auswahl)+" von ("+l.get(0)[0]+","+l.get(0)[1]+" nach "+l.get(l.size()-1)[0]+","+l.get(l.size()-1)[1]+")");
			((Fahrzeug) zharfiguren.get(auswahl)).neueBewegung(l);
		}
		return true;
	}
	
	public static boolean bewegen(int x, int y) {
		if(bewegung_prüfen(x, y))return true;
		return false;
	}
	
	/**
	 * Wenn die Drehung geht, ausführen; sonst abbrechen
	 * @return true wenn bewegung geht, sonst false
	 */
	public static boolean bewegung_prüfen(int x, int y) {
		for(ZharFigur z: zharfiguren) {
			if(z.belegt(x,y))return false;
		}
		ZharLevel.auswahlPrüfen(x, y);
		return true;
	}
	
	public static void auswahlFigur(int x, int y) {
		auswahl =-1;
		if(zharfiguren.size() > 0) {
			for(int i=0; i<zharfiguren.size(); i++) {
				if(zharfiguren.get(i).belegt(x,y)) {
					auswahl = i;
				}
			}
		}
		if(auswahl!=-1) {
			if(ZharSpielfeld.zharfiguren.get(auswahl) instanceof Fahrzeug) {//bei auswahl bewegung stoppen
				//((Fahrzeug) ZharSpielfeld.zharfiguren.get(auswahl)).bewegen = new ArrayList<Integer[]>();
			}
			if(ZharSpielfeld.zharfiguren.get(auswahl).spieler != ZharController.currentSpieler) {
				ShowCanvas.setzeNachricht("Das ist nicht deine Figur: "+ZharSpielfeld.zharfiguren.get(auswahl));
				return;
			}
			ShowCanvas.setzeNachricht("Auswahl von "+ZharSpielfeld.zharfiguren.get(auswahl));
		}
		else ShowCanvas.setzeNachricht("");
	}
	
	/**
	 * @return true, wenn aktueller Tetraid auf Boden ist oder darunter block; sonst false
	 */
	public static void spawnFigur(String typ, int Spieler,  int x, int y) {
		//nur auf 
		switch(typ) {
			case "Jäger":{		
				if((ZharController.spielerliste.get(Spieler).ressourcen - Jäger.preis) >= 0) {	
					System.out.println(ZharController.spielerliste.get(Spieler).ressourcen);
					System.out.println(Jäger.preis);
					ZharFigur neu = new Jäger(typ, x, y, Spieler);
					zharfiguren.add(neu);
					ZharController.spielerliste.get(Spieler).ressourcen -= Jäger.preis;
				}
				else {
					ShowCanvas.setzeNachricht("Zu wenig Geld!");
				}
				break;
			}
			case "Bauer":{	
				int k = (ZharController.spielerliste.get(Spieler).ressourcen - Bauer.preis);
				if(k >= 0) {	
					zharfiguren.add(new Bauer(typ, x, y, Spieler)); 
					ZharController.spielerliste.get(Spieler).ressourcen -= Bauer.preis;
				}
				else {
					ShowCanvas.setzeNachricht("Zu wenig Geld!");
				}
				break;
			} 
			case "Sammler":{	
				if((ZharController.spielerliste.get(Spieler).ressourcen - Sammler.preis) >= 0) {	
					ZharFigur neu = new Sammler(typ, x, y, Spieler);
					zharfiguren.add(neu);
					ZharController.spielerliste.get(Spieler).ressourcen -= Sammler.preis;
				}
				else {
					ShowCanvas.setzeNachricht("Zu wenig Geld!");
				}
				break;
			}
			case "Sfabrik":{		
				if((ZharController.spielerliste.get(Spieler).ressourcen - Fabrik.preis) >= 0) {	
					zharfiguren.add(new SammlerFabrik(typ, x, y, Spieler)); 
					ZharController.spielerliste.get(Spieler).ressourcen -= Fabrik.preis;
				}
				else {
					ShowCanvas.setzeNachricht("Zu wenig Geld!");
				}
				break;
			}
			case "Jfabrik":{		
				if((ZharController.spielerliste.get(Spieler).ressourcen - Fabrik.preis) >= 0) {	
					zharfiguren.add(new JägerFabrik(typ, x, y, Spieler)); 
					ZharController.spielerliste.get(Spieler).ressourcen -= Fabrik.preis;
				}
				else {
					ShowCanvas.setzeNachricht("Zu wenig Geld!");
				}
				break;
			}
			default:{
				System.out.println("Diese Figur existiert nicht: "+typ);
				System.exit(0);
			}
		}
		return ;
	}
	
	public static boolean spawn_fabrik(int x, int y) {
		boolean baubar = true;
		if(x<0 || y<0 || x+1>=breite || x+1>=hoehe)baubar= false;
		//man kann Fabrik nur auf eigener Landefläche bauen
		if(baubar) {			
			for(int b=0; b<2; b++) {
				for(int h=0; h<2; h++) {
					if(ZharLevel.fields[x+b][y+h] instanceof Landefläche) {
						if(((Landefläche) ZharLevel.fields[x+b][y+h]).spieler.equals(ZharController.spielerliste.get(ZharController.currentSpieler).name)) {
							continue;
						}
						else baubar= false;
					}
					else baubar= false;
				}
			}
		}
		//man kann Fabrik NICHT auf Figuren bauen
		if(baubar) {			
			for(ZharFigur z: ZharSpielfeld.zharfiguren) {
				if(z.anfang_x == x || z.anfang_y == y)baubar= false;
			}
		}
		if(!baubar)ShowCanvas.setzeNachricht("Du kannst hier keine Fabrik bauen!");
		return baubar;
	}
	
//	public static void bewegen(int y, int x, int drehung) {
//		if(auswahl != -1 && zharfiguren.size() > 0) {
//			if(x==1) zharfiguren.get(auswahl).schiebe_rechts();
//			else if(x==-1) zharfiguren.get(auswahl).schiebe_links();
//			else if(y==-1) zharfiguren.get(auswahl).schiebe_unten();
//			else if(y==1) zharfiguren.get(auswahl).schiebe_oben();
//			else if(drehung==1)zharfiguren.get(auswahl).drehen_rechts();
//			else if(drehung==-1)zharfiguren.get(auswahl).drehen_links();
//		}
//		
//	}
}
