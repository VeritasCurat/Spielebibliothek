package m�hle;

import java.io.IOException;
import javax.swing.JPanel;

public class M�hleController extends JPanel{
	static M�hleGUI G;
	
	//Variablen f�r KI
		static boolean spiel_modus_KI= false;
		static boolean farbe_KI_schwarz = true;
		
	private static final long serialVersionUID = 1L;
	static M�hleSpielfeld S = new M�hleSpielfeld();
	static String farbe="wei�";
	static String auswahl=""; static int auswahlX = -1; static int auswahlY=-1; static int auswahlRing = -1;
	static boolean markiert = false;
	
	static int phaseWei�=0; static int phaseSchwarz=0;//0: setzen, 1: 4-9 Figuren, 2: 1-3 Figuren
	
	static int anz_wei�=0;
	static int anz_schwarz=0;
	
	public static void changeFarbe() {
		if(farbe.equals("wei�"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "wei�";
	}
	
	
	private static int koordinateInRing(int x, int y) {
		if(x*y == 9)return -1;
		else if(x==0 || y==0 || x==6 || y==6)return 0;
		else if(x==1 || y==1 || x==5 || y==5)return 1;
		else if(x==2 || y==2 || x==4 || y==4)return 2;
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
	
	public static String markFigure(int x, int y, int ring) {
				
		String f = M�hleSpielfeld.figuren[x][y][ring].farbe.substring(0,1);
		String t = M�hleSpielfeld.figuren[x][y][ring].typ.substring(0,1);
		System.out.println(x+" "+y+" "+ring+": "+f+t);
		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}
		auswahl = f+t; auswahlX = x; auswahlY =y; auswahlRing = ring;
		return f+t;
	}
	
	public static M�hleFigur getFigur(int x, int y, int ring) {
		return M�hleSpielfeld.figuren[x][y][ring];
	}
	
	

	public static void setFigure(int x, int y, int ring, String farbe) {
		if(!M�hleSpielfeld.bewegungAusf�hren(auswahlX, auswahlY, auswahlRing, x, y, ring, farbe))return;	
		System.out.println("GUI bewegung: "+auswahl+": "+auswahlX+" "+auswahlY+" => "+x+" "+y);
		
		//wenn KI eingeschaltet reagiert jetzt die KI
//				if(spiel_modus_KI) {
//					Spielfeld.automatic = true;
//					SimpleKI.ziehen();
//					if(!SimpleKI.FEHLER)Spielfeld.bewegungAusf�hren(SimpleKI.KI_x1, SimpleKI.KI_y1, SimpleKI.KI_x2, SimpleKI.KI_y2, SimpleKI.farbe);
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
		if(ShowCanvas.x==-1 || ShowCanvas.y==-1)return;
		int[] k = koordinateInRingKoordinate(ShowCanvas.x, ShowCanvas.y);
		System.out.println(auswahlX+" "+auswahlY+" "+auswahlRing);
		if(!M�hleSpielfeld.spiel_aktiv) {
			System.exit(0);
		}
		if(farbe.equals("wei�") && phaseWei�==0) {
			if(!M�hleSpielfeld.setFigure(k[0], k[1], k[2], farbe, "L�ufer"))return;
			G.repaint();
			if(M�hleSpielfeld.m�hle().equals("wei�"))System.out.println("M�hle f�r wei�!");
			changeFarbe();
			++anz_wei�;
			if(anz_wei� == 4)phaseWei�=1;
			return;
		}
		if(farbe.equals("schwarz") && phaseSchwarz==0) {
			if(!M�hleSpielfeld.setFigure(k[0], k[1], k[2], farbe, "L�ufer"))return;
			G.repaint();
			if(M�hleSpielfeld.m�hle().equals("schwarz"))System.out.println("M�hle f�r schwarz!");
			changeFarbe();
			++anz_schwarz;
			if(anz_schwarz == 4)phaseSchwarz=1;
			return;
		}
		if(!M�hleController.markiert) {
			M�hleController.auswahl = M�hleController.markFigure(k[0],k[1],k[2]);
			if(M�hleController.auswahl.equals(""))return;
			M�hleController.markiert = true;
			System.out.println(M�hleController.auswahl);
			G.repaint();
		}
		else if(M�hleController.markiert) {
			M�hleController.markiert =false;
			M�hleController.setFigure(k[0],k[1],k[2],farbe);
			//if(M�hleSpielfeld.m�hle().equals("schwarz"))System.out.println("M�hle f�r schwarz!");
			//if(M�hleSpielfeld.m�hle().equals("wei�"))System.out.println("M�hle f�r wei�!");

			M�hleController.auswahlX = M�hleController.auswahlY = M�hleController.auswahlRing=-1; M�hleController.auswahl = "";
			System.out.println(M�hleController.auswahl);
			G.repaint();
		}	
		
		return;
	}
	
	
	public static void spiel_gegen_menschen() {
		M�hleSpielfeld.init();
		
		G = new m�hle.M�hleGUI();
		G.repaint();
	}
	
	public static void spiel_gegen_KI() {
	
		G = new m�hle.M�hleGUI();
		G.repaint();
		
	}
}
