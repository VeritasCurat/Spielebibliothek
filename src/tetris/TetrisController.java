package tetris;

import java.awt.Color;

public class TetrisController {
	
	static int schwierigkeit=2;
		static String schwierigkeitNamen[] = {"sehr einfach", "einfach", "mittel", "schwer", "sehr schwer"};
		static double tickzeiten[] =  {2.0, 1.5, 1, 0.8, 0.5}; 
	static int score=0;
		static int score_unit = 3*(schwierigkeit+1);
	
	
	static boolean spiel_modus_KI= false;
	static boolean farbe_KI_schwarz = true;
	static int hoehe=18; static int breite=10; //12,6
	static TetrisGUI G = new TetrisGUI(hoehe, breite);	
	
	
	static double lasttick = System.currentTimeMillis();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stubsss
		TetrisSpielfeld.init(hoehe,breite);
		neuesSpiel();
	}
	
	public static void bewegung(int y, int x, int d) {
		TetrisSpielfeld.bewegen(y, x, d);
		G.repaint();
	}
	
	public static void tick() {
		if(System.currentTimeMillis()-lasttick > 1000*tickzeiten[schwierigkeit]) {
			lasttick = System.currentTimeMillis();
			TetrisSpielfeld.bewegen(-1, 0, 0);
		}
		G.repaint();
	}
	
	public static void neuesSpiel() throws InterruptedException {
		String figuren[] = {"L","L_","S","S_", "Quadrat","T", "Linie"};
		TetrisSpielfeld.currentfigur = figuren[(int) (Math.random()*figuren.length)];
		TetrisSpielfeld.currentColor = TetrisFigur.farbenkasten[(int) (Math.random()*TetrisFigur.farbenkasten.length)];
				
		TetrisSpielfeld.nextfigur = figuren[(int) (Math.random()*figuren.length)];
		TetrisSpielfeld.nextColor = TetrisFigur.farbenkasten[(int) (Math.random()*TetrisFigur.farbenkasten.length)];
		TetrisSpielfeld.nextdar();
		
		TetrisSpielfeld.spawn_figure(TetrisSpielfeld.currentfigur ,TetrisSpielfeld.currentColor);;
		

		G.repaint();
		while(true) {
			Thread.sleep(1000);
			tick();
		}
	}
	
	public static void repaint() {
		G.repaint();
	}

}




		