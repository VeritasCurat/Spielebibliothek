package mühle;

import java.io.IOException;
import javax.swing.JPanel;

public class Controller extends JPanel{
	static GUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= false;
		static boolean farbe_KI_schwarz = true;
		
	private static final long serialVersionUID = 1L;
	static Spielfeld S = new Spielfeld();
	static String farbe="weiß";
	int mouseX =0; int mouseY=0; static String auswahl=""; static int auswahlX = -1; static int auswahlY=-1; static int auswahlRing = -1;
	static boolean markiert = false;
	
	static int phaseWeiß=0; static int phaseSchwarz=0;//0: setzen, 1: 4-9 Figuren, 2: 1-3 Figuren
	
	public static void changeFarbe() {
		if(farbe.equals("weiß"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "weiß";
	}
	
	
	private static int koordinateInRing(int x, int y) {
		if(x*y == 9)return -1;
		else if(Math.abs(3-x)==Math.abs(3-y)) return Math.abs(3-y);
		else if(x+y == 6)return Math.abs(x-y)/2;
		else if(x==3 || y==3) return 3-Math.abs(3-x) + 3-Math.abs(3-y);
		else return -1;
	}
	
	private static int[] koordinateInRingKoordinate(int x, int y) {
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
		
		String f = Spielfeld.figuren[k[0]][k[1]][k[2]].farbe.substring(0,1);
		String t = Spielfeld.figuren[k[0]][k[1]][k[2]].typ.substring(0,1);
		System.out.println(x+" "+y+" "+k[2]+": "+f+t);
		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}
		auswahl = f+t; auswahlX = k[0]; auswahlY = k[1]; auswahlRing = k[2];
		return f+t;
	}
	
	public static Figur getFigur(int x, int y, int ring) {
		return Spielfeld.figuren[x][y][ring];
	}

	public static void setFigure(int x, int y, int ring, String farbe) {
		System.out.println("GUI bewegung: "+auswahl+": "+auswahlX+" "+auswahlY+" => "+x+" "+y);
		if(!Spielfeld.bewegungAusführen(auswahlX, auswahlY, auswahlRing, x, y, ring, farbe))return;	
		
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
		if(!Spielfeld.spiel_aktiv) {
			System.exit(0);
		}
		if(farbe.equals("weiß") && phaseWeiß==0) {
			Spielfeld.setFigure(auswahlX, auswahlY, auswahlRing, farbe);
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			return;
		}
		if(farbe.equals("schwarz") && phaseSchwarz==0) {
			Spielfeld.setFigure(auswahlX, auswahlY, auswahlRing, farbe);
			auswahlRing = auswahlX = auswahlY = -1;
			G.repaint();
			return;
		}
		if(!Controller.markiert) {
			Controller.auswahl = Controller.markFigure((int) ShowCanvas.x, (int) (ShowCanvas.y));
			if(Controller.auswahl.equals(""))return;
			Controller.markiert = true;
			System.out.println(Controller.auswahl);
			G.repaint();
		}
		else if(Controller.markiert) {
			Controller.markiert =false;
			Controller.setFigure(auswahlX,auswahlY,auswahlRing,farbe);
			Controller.auswahlX = Controller.auswahlY = Controller.auswahlRing=-1; Controller.auswahl = "";
			System.out.println(Controller.auswahl);
			G.repaint();
		}	
		
		return;
	}
	
	
	public static void spiel_gegen_menschen() {
		Spielfeld.init();
		
		G = new GUI();
		G.repaint();
	}
	
	public static void spiel_gegen_KI() {
	
		G = new GUI();
		G.repaint();
		
	}
}
