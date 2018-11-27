package schach;

import java.io.IOException;
import javax.swing.JPanel;

public class SchachController extends JPanel{
	static SchachGUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= true;
		static boolean farbe_KI_schwarz = true;
		static SchachKI k;
		
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
	
	public static SchachFigur getFigur(int x, int y) {
		return Schachbrett.figuren[x][y];
	}
	
	public static void bauerntausch(int x2, int y2, String figur, String Farbe) {

		System.out.println("tausche gegen "+figur);
		
		changeFarbe();
		switch(figur) {
			case "Läufer": {Schachbrett.figuren[x2][y2] = new Läufer(x2, y2, farbe,true); break;}
			case "Pferd": {Schachbrett.figuren[x2][y2] = new Pferd(x2, y2, farbe,true); break;}
			case "Turm": {Schachbrett.figuren[x2][y2] = new Turm(x2, y2, farbe,true); break;}
			case "Dame": {Schachbrett.figuren[x2][y2] = new Dame(x2, y2, farbe,true); break;}
		}
		auswahl = ""; auswahlX = -1; auswahlY = -1;
		markiert = false;
		Schachbrett.print();	
		System.out.println(Schachbrett.figuren[x2][y2].typ);
		G.repaint();
		
		SchachController.auswahlX = SchachController.auswahlY = -1; SchachController.auswahl = "";
		changeFarbe();
	}

	public static void setFigure(int x, int y) {
		System.out.println("GUI bewegung: "+auswahl+": "+auswahlX+" "+auswahlY+" => "+x+" "+y);
		zuruecksetzenVorschlaege();

		if(!Schachbrett.bewegungAusführen(auswahlX, auswahlY, x, y, farbe))return;	
		
		//wenn KI eingeschaltet reagiert jetzt die KI
		if(spiel_modus_KI) {
			Schachbrett.automatic = true;
			SchachKI.ziehen();
			if(!SchachKI.FEHLER)Schachbrett.bewegungAusführen(SchachKI.KI_x1, SchachKI.KI_y1, SchachKI.KI_x2, SchachKI.KI_y2, SchachKI.farbe);
			Schachbrett.automatic = false;
			changeFarbe();

		}
				
		//Bauer gegen Dame tauschen
		if(Schachbrett.figuren[x][y].typ.equals("Bauer")) {
			if((farbe.equals("schwarz") && y==0) || (farbe.equals("weiß") && y==7)) {
				
				if(Schachbrett.automatic == false)SchachGUI.figurenauswahl(x,y,farbe);
				else { //KI wählt Dame
					bauerntausch(x,y, "Dame", farbe);				
				}
			}
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
		if(!SchachController.markiert) {
			SchachController.auswahl = SchachController.markFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
			if(SchachController.auswahl.equals(""))return;
			SchachController.markiert = true;
			System.out.println(SchachController.auswahl);
			G.repaint();
		}
		else if(SchachController.markiert) {
			SchachController.markiert =false;
			SchachController.setFigure((int) (ShowCanvas.mouseX/100), (int) (ShowCanvas.mouseY/100));
			SchachController.auswahlX = SchachController.auswahlY = -1; SchachController.auswahl = "";
			System.out.println(SchachController.auswahl);
			G.repaint();
		}	
		return;
	}
	
	public static void spiel_gegen_menschen() {
		S.print();
		zuruecksetzenVorschlaege();
	
		G = new SchachGUI();
		G.repaint();
	}
	
	public static void spiel_gegen_KI() {
		S.print();
		zuruecksetzenVorschlaege();
	
		G = new SchachGUI();
		G.repaint();
		
	}
}
