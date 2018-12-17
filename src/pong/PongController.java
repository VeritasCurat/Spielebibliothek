package pong;

public class PongController {
	
	static int windowX=1000; static int windowY=1000;
	static PongGUI G = new PongGUI(windowY, windowX);
	
	static int level = 1; static boolean change_level=false; static boolean hauptmenü=true;
	private static int leben = 3;

	public static void main(String[] args) throws InterruptedException {
		hauptmenü(-1);
	}
	
	
	public static void hauptmenü(int lvl) throws InterruptedException{
		setLeben(3);
		if(lvl < 0) {
			hauptmenü = true;
		}
		while(hauptmenü) {
			Thread.sleep(20);
			G.repaint();
		}
			
			
		hauptmenü = false;
		neuesSpiel();
	}

	public static void neuesSpiel() throws InterruptedException {
		change_level=false;
		PongKelle.ball_klebt=true;
		PongSpielfeld.init(windowX, windowY);
		PongBall.init(PongKelle.xm,windowY-100,0,-0);
		PongKelle.init(windowX/2, windowY-70, 150, 20);
		if(getLeben()==3)PongLevel.init(level,windowX, windowY);
		G.repaint();
		
		while(!change_level) {
			Thread.sleep(20);
			PongBall.updatePosition(5);
			PongSpielfeld.effektliste_akt();
			PongKelle.geschwindikeit_berechnen();
			G.repaint();
		}
		hauptmenü(-1);
	}


	public static int getLeben() {
		return leben;
	}


	public static void setLeben(int leben) {
		PongController.leben = leben;
	}
	
	
}
