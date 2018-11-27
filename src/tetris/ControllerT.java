package tetris;

public class ControllerT {
	
	static boolean spiel_modus_KI= false;
	static boolean farbe_KI_schwarz = true;
	static int hoehe=12; static int breite=6;
	static GUI G = new GUI(hoehe, breite);	
	
	static Spielfeld S = new Spielfeld(hoehe, breite);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		neuesSpiel();
	}
	
	public static void bewegung(int y, int x, int d) {
		Spielfeld.bewegen(y, x, d);
		G.repaint();
	}
	
	public static void neuesSpiel() {
		String figuren[] = {"L","L_","S","S_", "Quadrat","T"}; //"Linie"
		String auswahl = figuren[(int) (Math.random()*6)];System.out.println(auswahl);
		Spielfeld.spawn_figure(auswahl);
		G.repaint();
	}

}




		