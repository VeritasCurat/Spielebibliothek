package pong;

import java.awt.Color;

public class PongBall {
	
	static double x, y, vx, vy, geschwindigkeit=500;
	static int size = 20;
	
	static int cnt=0;
	

	static Color Main = new Color(255, 20, 20);
	
	static Color Fade[] = {new Color(255, 150, 150), new Color(255, 190, 190), new Color(255, 230, 230), new Color(255, 240, 240), new Color(255, 250, 250)};

	
	
	public static void init(double X, double Y, double VX, double VY) {
		size = 20;
		x = X; y = Y; vx = VX; vy = VY;
	}
	
	public static void updatePosition(double time) throws InterruptedException {
		System.out.println(++cnt);
		PongSpielfeld.Kollisionstest();
		if(y>=PongSpielfeld.getY()) {
			if(PongController.getLeben() > 0) {
				PongController.setLeben(PongController.getLeben() - 1);
				PongController.neuesSpiel();
			}
			else {
				System.out.println("Spiel zuende");
				PongController.hauptmenü(-1);				
			}
		}
		x+=vx*(time/1000)*4;
		y+=vy*(time/1000)*4;
		
	}


	public static void vx_verändern(double summe) {
		vx=0.3*vx+0.7*summe;
		vy=Math.abs(geschwindigkeit-Math.abs(vx))*Math.signum(vy);
	}
	
	public static void invertVX() {
		vx*=-1;
		vx+=20*Math.signum(vx);
		vy=Math.abs(geschwindigkeit-Math.abs(vx))*Math.signum(vy);
		vy=Math.signum(vy)*Math.max(100,Math.abs(vy));
	}

	public static void invertVY() {
		vy*=-1;
		vy+=Math.signum(vx);
		vx=Math.abs(geschwindigkeit-Math.abs(vy))*Math.signum(vx);
	}
}
