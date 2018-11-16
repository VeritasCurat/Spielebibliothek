package mühle;

import java.io.IOException;
import javax.swing.JPanel;

public class MühleController extends JPanel{
	static MühleGUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= false;
		static boolean farbe_KI_schwarz = true;
		
	private static final long serialVersionUID = 1L;
	static MühleSpielfeld S = new MühleSpielfeld();
	static String farbe="weiß";
	static String auswahl=""; static int auswahlX = -1; static int auswahlY=-1; static int auswahlRing = -1;
	static boolean markiert = false;
	
	static int phaseWeiß=0; static int phaseSchwarz=0;//0: setzen, 1: 4-9 Figuren, 2: 1-3 Figuren
	
	public static void changeFarbe() {
		if(farbe.equals("weiß"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "weiß";
	}
	
	
	private static int koordinateInRing(int x, int y) {
		if(x*y == 9)return -1;
		else if(Math.abs(3-x)==Math.abs(3-y)) return Math.abs(y%3);
		else if(x+y == 6)return Math.abs(x-y)/2;
		else if(x==3 || y==3) return 3-Math.abs(3-x) + 3-Math.abs(y);
		else return -1;
	}
	
	private static int[] koordinateInRingKoordinate(int x, int y) {
		System.out.println(x+" "+y);
		int[] k = new int[3];
		k[2] = koordinateInRing(x, y);
		if(k[2] == 0) {
			k[0] = x/3; k[1] = y/3;
			
		}
		else if(k[2] == 1) {
			k[0] = (x-1)/2; k[1] = (y-1)/2;
		}
		else if(k[2] == 2) {
			k[0] = x-2; k[1] = y-2;
		}
		return k;
	}
	
	public static String markFigure(int x, int y) {
		
		int[] k = koordinateInRingKoordinate(x, y);
		
		String f = MühleSpielfeld.figuren[k[0]][k[1]][k[2]].farbe.substring(0,1);
		String t = MühleSpielfeld.figuren[k[0]][k[1]][k[2]].typ.substring(0,1);
		System.out.println(x+" "+y+" "+k[2]+": "+f+t);
		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}
		auswahl = f+t; auswahlX = k[0]; auswahlY = k[1]; auswahlRing = k[2];
		return f+t;
	}
	
	public static MühleFigur getFigur(int x, int y, int ring) {
		return MühleSpielfeld.figuren[x][y][ring];
	}
	
	

	public static void setFigure(int x, int y, int ring, String farbe) {
		System.out.println("GUI bewegung: "+auswahl+": "+auswahlX+" "+auswahlY+" => "+x+" "+y);
		if(!MühleSpielfeld.bewegungAusführen(auswahlX, auswahlY, auswahlRing, x, y, ring, farbe))return;	
		
		//wenn KI eingeschaltet reagiert jetzt die KI
//				if(spiel_modus_KI) {
//					Spielfeld.automatic = true;
//					SimpleKI.ziehen();
//					if(!SimpleKI.FEHLER)Spielfeld.bewegungAusführen(SimpleKI.KI_x1, SimpleKI.KI_y1, SimpleKI.KI_x2, SimpleKI.KI_y2, SimpleKI.farbe);
//					Spielfeld.automatic = false;
//					changeFarbe();
//
//				}
		
		auswahl = ""; auswahlX = -1; auswahlY = -1; auswahlRing =-1;
		markiert = false;
		changeFarbe();
	}
	
	public static void main(String[] args) throws IOException {	
		if(spiel_modus_KI)spiel_gegen_KI();
		else spiel_gegen_menschen();
	}
	
	public static void spieler_aktion() {
		int[] k = koordinateInRingKoordinate(ShowCanvas.x, ShowCanvas.y);
		auswahlX = k[0]; auswahlY = k[1]; auswahlRing = k[2];
		System.out.println(auswahlX+" "+auswahlY+" "+auswahlRing);
		if(!MühleSpielfeld.spiel_aktiv) {
			System.exit(0);
		}
		if(farbe.equals("weiß") && phaseWeiß==0) {
			System.out.println("setze Stein auf:"+k[0]+" "+k[1]+" "+k[2]);
			MühleSpielfeld.setFigure(auswahlX, auswahlY, auswahlRing, farbe);
			System.out.println("Farbe:" +MühleSpielfeld.figuren[k[0]][k[1]][k[2]].farbe);
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			return;
		}
		if(farbe.equals("schwarz") && phaseSchwarz==0) {
			MühleSpielfeld.setFigure(auswahlX, auswahlY, auswahlRing, farbe);
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			return;
		}
		if(!MühleController.markiert) {
			MühleController.auswahl = MühleController.markFigure((int) ShowCanvas.x, (int) (ShowCanvas.y));
			if(MühleController.auswahl.equals(""))return;
			MühleController.markiert = true;
			System.out.println(MühleController.auswahl);
			G.repaint();
		}
		else if(MühleController.markiert) {
			MühleController.markiert =false;
			MühleController.setFigure(auswahlX,auswahlY,auswahlRing,farbe);
			MühleController.auswahlX = MühleController.auswahlY = MühleController.auswahlRing=-1; MühleController.auswahl = "";
			System.out.println(MühleController.auswahl);
			G.repaint();
		}	
		
		return;
	}
	
	
	public static void spiel_gegen_menschen() {
		MühleSpielfeld.init();
		
		G = new mühle.MühleGUI();
		G.repaint();
	}
	
	public static void spiel_gegen_KI() {
	
		G = new mühle.MühleGUI();
		G.repaint();
		
	}
}
