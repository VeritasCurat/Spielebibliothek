package schach;

import java.io.IOException;
import javax.swing.JPanel;

public class SchachController extends JPanel{
	static SchachGUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= true;
				
	private static final long serialVersionUID = 1L;
	static Schachbrett S = new Schachbrett();
	static SchachKI2 ki;
	
	static String farbe="weiß";
	static String farbe_mensch;
	static String farbe_ki;

	static boolean hinweise=false;

	int mouseX =0; int mouseY=0; static String auswahl=""; static int auswahlX = -1; static int auswahlY=-1;
	static boolean markiert = false;
	static boolean[][] vorschläge = new boolean[8][8];

	/**
	 *Ändert die Farbe des aktuellen Spielers.
	 */
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
		for(int a=0; a<8;a++) {
			  for(int b=0; b<8;b++) {
				  if(S.figuren[a][b].farbe.equals(farbe))continue;
				  if(S.bewegungPrüfen(x, y, a, b, farbe)) {
					  vorschläge[a][b] = true;
					  continue;
				  }				  
				  vorschläge[x][y] = false;
			  }
		}
	}
	
	public static String markFigure(int x, int y) {
		String f = S.figuren[x][y].farbe.substring(0,1);
		String t = S.figuren[x][y].typ.substring(0,1);
		if(!f.equals(farbe.substring(0,1))) {
			System.out.println("falsche Farbe: "+f+"!="+farbe.substring(0,1));
			return "";
		}
		hinweise=false;
		generiereVorschlaege(x, y);
		auswahl = f+t; auswahlX = x; auswahlY = y;
		return f+t;
	}
	
	public static SchachFigur getFigur(int x, int y) {
		return S.figuren[x][y];
	}
	
	public static void bauerntausch(int x2, int y2, String figur, String Farbe) {			
		switch(figur) {
			case "Läufer": {S.figuren[x2][y2] = new Läufer(x2, y2, farbe_mensch,true); break;}
			case "Pferd": {S.figuren[x2][y2] = new Pferd(x2, y2, farbe_mensch,true); break;}
			case "Turm": {S.figuren[x2][y2] = new Turm(x2, y2, farbe_mensch,true); break;}
			case "Dame": {S.figuren[x2][y2] = new Dame(x2, y2, farbe_mensch,true); break;}
		}
		auswahl = ""; auswahlX = -1; auswahlY = -1;
		markiert = false;
		S.print();	
		System.out.println(S.figuren[x2][y2].typ);
		G.repaint();
		
		SchachController.auswahlX = SchachController.auswahlY = -1; SchachController.auswahl = "";
	}

	public static void setFigure(int x, int y) {
		zuruecksetzenVorschlaege();
		hinweise=false;
		if(!S.bewegungAusführen(auswahlX, auswahlY, x, y, farbe))return;	
		
		//Bauer gegen Dame tauschen
		if(S.figuren[x][y].typ.equals("Bauer")) {
			if((farbe.equals("schwarz") && y==0) || (farbe.equals("weiß") && y==7)) {
				
				if(spiel_modus_KI && farbe.equals(farbe_ki)) { //KI wählt Dame
					bauerntausch(x,y, "Dame", farbe);				
				}
				else SchachGUI.figurenauswahl(x,y,farbe);
			}
		}
		
		auswahl = ""; auswahlX = -1; auswahlY = -1;
		markiert = false;
		changeFarbe();
		
		if(!spiel_modus_KI) {
			System.out.println("schwarz-weiß punkte: "+(S.figurenpunkte_schwarz()-S.figurenpunkte_weiß()));
		}
		
	
	}
	
	public static void main(String[] args) throws IOException {	
		zuruecksetzenVorschlaege();
	
		G = new SchachGUI();
		G.repaint();
		
		
		if(spiel_modus_KI)spiel_gegen_KI();
		else spiel_gegen_menschen();
	}
	
	public static void spieler_aktion() {
		if(!S.spiel_aktiv) {
			System.exit(0);
		}
		if((spiel_modus_KI==true && farbe.equals(farbe_mensch)) || spiel_modus_KI==false) {			
			if(!SchachController.markiert) {
				SchachController.auswahl = SchachController.markFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
				if(SchachController.auswahl.equals(""))return;
				SchachController.markiert = true;
				System.out.println(SchachController.auswahl);
				G.repaint();
			}
			else if(SchachController.markiert) {
				SchachController.markiert =false;
				hinweise = true;
				SchachController.setFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
				hinweise = false;
				SchachController.auswahlX = SchachController.auswahlY = -1; SchachController.auswahl = "";
				System.out.println(SchachController.auswahl);
			}	
		}
		if(spiel_modus_KI==true && farbe.equals(farbe_ki)) {
			System.out.println("zug ki");
			hinweise=false;
			//wenn KI eingeschaltet reagiert jetzt die KI
			ki.zug_generieren();
			S.bewegungAusführen(ki.KI_x1, ki.KI_y1, ki.KI_x2, ki.KI_y2, ki.eigenefarbe);
			hinweise=true;
			changeFarbe();
		}
		G.repaint();
		return;
	}
	
	public static void spiel_gegen_menschen() {
		
	}
	
	public static void spiel_gegen_KI() {
		farbe_mensch = "weiß";
		farbe_ki = "schwarz";
		ki = new SchachKI2(farbe_ki,"alphabeta",4);
		G.canvas.lichtspiel = ki.S = S;
		spieler_aktion();
	}
}
