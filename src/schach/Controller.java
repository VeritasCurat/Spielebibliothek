package schach;

import java.io.IOException;
import javax.swing.JPanel;

public class Controller extends JPanel{
	static GUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= true;
		static boolean farbe_KI_schwarz = true;
		static SimpleKI k;
		
	private static final long serialVersionUID = 1L;
	static Schachbrett S = new Schachbrett();
	static String farbe="weiß";
	int mouseX =0; int mouseY=0; static String auswahl=""; static isnt auswahlX = -1; static int auswahlY=-1;
	static boolean markiert = false;
	static boolean[][] vorschläge = new boolean[8][8];

	
	public static void changeFarbe() {
		if(farbe.equals("weiß"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "weiß";
	}
	
	public static void zuruecksetzenVorschlaege() {
		for(int x=0; x<8;x++) {
			  for(int y=0; y<8;y++) {
				  vorschläge[x][y] = false;
			  }
		}
	}
	
	public static void generiereVorschlaege(int x, int y) {
		Schachbrett.figuren[x][y].automatic=true;
		Schachbrett.automatic=true;
		for(int a=0; a<8;a++) {
			  for(int b=0; b<8;b++) {
				  if(Schachbrett.figuren[a][b].farbe.equals(farbe))continue;
				  if(Schachbrett.bewegungPrüfen(x, y, a, b, farbe)) {
					  vorschläge[a][b] = true;
					  continue;
				  }				  
				  vorschläge[x][y] = false;
			  }
		}
		Schachbrett.figuren[x][y].automatic=false;
		Schachbrett.automatic = false;
	}
	
	public static String markFigure(int x, int y) {
		String f = Schachbrett.figuren[x][y].farbe.substring(0,1);
		String t = Schachbrett.figuren[x][y].typ.substring(0,1);
		System.out.println(x+" "+y+": "+f+t);
		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}
		generiereVorschlaege(x, y);
		auswahl = f+t; auswahlX = x; auswahlY = y;
		return f+t;
	}
	
	public static Figur getFigur(int x, int y) {
		return Schachbrett.figuren[x][y];
	}

	public static void setFigure(int x, int y) {
		System.out.println("GUI bewegung: "+auswahl+": "+auswahlX+" "+auswahlY+" => "+x+" "+y);
		zuruecksetzenVorschlaege();

		if(!Schachbrett.bewegungAusführen(auswahlX, auswahlY, x, y, farbe))return;	
		
		//wenn KI eingeschaltet reagiert jetzt die KI
				if(spiel_modus_KI) {
					Schachbrett.automatic = true;
					SimpleKI.ziehen();
					if(!SimpleKI.FEHLER)Schachbrett.bewegungAusführen(SimpleKI.KI_x1, SimpleKI.KI_y1, SimpleKI.KI_x2, SimpleKI.KI_y2, SimpleKI.farbe);
					Schachbrett.automatic = false;
					changeFarbe();

				}
		
		auswahl = ""; auswahlX = -1; auswahlY = -1;
		markiert = false;
		S.print();	
		changeFarbe();
	}
	
	public static void main(String[] args) throws IOException {	
		if(spiel_modus_KI)spiel_gegen_KI();
		else spiel_gegen_menschen();
	}
	
	public static void spieler_aktion() {
		if(!Schachbrett.spiel_aktiv) {
			System.exit(0);
		}
		if(!Controller.markiert) {
			Controller.auswahl = Controller.markFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
			if(Controller.auswahl.equals(""))return;
			Controller.markiert = true;
			System.out.println(Controller.auswahl);
			G.repaint();
		}
		else if(Controller.markiert) {
			Controller.markiert =false;
			Controller.setFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
			Controller.auswahlX = Controller.auswahlY = -1; Controller.auswahl = "";
			System.out.println(Controller.auswahl);
			G.repaint();
		}	
		
		return;
	}
	
	public static void spiel_gegen_menschen() {
		S.print();
		zuruecksetzenVorschlaege();
	
		G = new GUI();
		G.repaint();
	}
	
	public static void spiel_gegen_KI() {
		S.print();
		zuruecksetzenVorschlaege();
	
		G = new GUI();
		G.repaint();
		
	}
}
