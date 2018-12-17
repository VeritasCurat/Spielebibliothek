package go;

import java.io.IOException;
import javax.swing.JPanel;

public class GoController extends JPanel{
	static GoGUI G;
	
	//Variablen für KI
		static boolean spiel_modus_KI= false;
		static boolean farbe_KI_schwarz = true;
		
	private static final long serialVersionUID = 1L;
	static String farbe="weiß";
	static String auswahl=""; static int auswahlX = -1; static int auswahlY=-1;
	static boolean markiert = false;

	static int breite=10;
	
	public static void changeFarbe() {
		if(farbe.equals("weiß"))farbe = "schwarz";
		else if(farbe.equals("schwarz"))farbe = "weiß";
	}
	
	

	public static void setFigure(int x, int y) {
		if(!GoSpielfeld.setFigure(x,y, farbe))return;			
		changeFarbe();
	}
	
	public static void main(String[] args) throws IOException {	
		GoSpielfeld.init(breite);
		G = new go.GoGUI(breite);
		G.repaint();
	}
	
	public static void spieler_aktion(int x, int y) {
		if(!GoSpielfeld.spiel_aktiv) {
			System.exit(0);
		}
		int X = x; int Y = y;
		if(X>=0 && X<breite && Y>=0 && Y<breite ) {
			GoController.setFigure(X,Y);
			G.repaint();
			return;			
		}
		return;
	}
	
}	
