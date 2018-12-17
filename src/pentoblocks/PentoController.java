package pentoblocks;

public class PentoController {
	
	static int schwierigkeit=2;
		static String schwierigkeitNamen[] = {"sehr einfach", "einfach", "mittel", "schwer", "sehr schwer"};
		static double tickzeiten[] =  {2.0, 1.5, 1, 0.8, 0.5}; 
	static int score=0;
		static int score_unit = 3*(schwierigkeit+1);
	
	
	static boolean spiel_modus_KI= false;
	static boolean farbe_KI_schwarz = true;
	static int hoehe=18; static int breite=12; //12,6
	static PentoGUI G = new PentoGUI(hoehe, breite);	
	
	static PentoSpielfeld S = new PentoSpielfeld(hoehe, breite);
	
	static double lasttick = System.currentTimeMillis();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		neuesSpiel();
	}
	
	public static void bewegung(int y, int x, int d) {
		PentoSpielfeld.bewegen(y, x, d);
		G.repaint();
	}
	
	public static void tick() {
		if(System.currentTimeMillis()-lasttick > 1000*tickzeiten[schwierigkeit]) {
			lasttick = System.currentTimeMillis();
			if(PentoGUI.currentkey=='a') PentoSpielfeld.bewegen(0, -1, 0);
			else if(PentoGUI.currentkey=='d') PentoController.bewegung(0,1,0);
			else PentoSpielfeld.bewegen(-1, 0, 0);
		}
		G.repaint();
	}
	

	
	public static void neuesSpiel() throws InterruptedException {
		String figuren[] = {"Vogel", "Vogel_", "P", "P_", "T", "Sch¸ssel", "Ecke", "Treppe", "Kreuz", "S", "S_", "Haken", "Haken_", "GroﬂesS", "GroﬂesS_", "GroﬂesL", "GroﬂesL_"};
		String auswahl = figuren[(int) (Math.random()*figuren.length-1)];
		PentoSpielfeld.spawn_figure(auswahl);
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




		