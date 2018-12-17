package pong;

import java.util.ArrayList;

public class PongKelle{
    static boolean ball_klebt = true;
	
	static int xm=10, y=20;
	static int sizeX=150;
	static int sizeY=20;
	
	int x1, x2, y1, y2;
	static int vx;
	
	
	public static void init(int X, int Y, int SizeX, int SizeY) {
		xm = X; y = Y; sizeX = SizeX; sizeY = SizeY;
	}
	
	public static int geschwindikeit_berechnen() {
		int VX = vx;
		vx=0;
		return VX;
	}
	
	public static double schneiden() {
		if(vx==0)return 1;
		return Math.log10(Math.log10(vx))+1;
	}
	
	public static void moveX(int X) {
		if(X>xm && X+sizeX/2>PongSpielfeld.x)return;
		if(X<xm && X-sizeX/2<0)return;
		
		if(ball_klebt) {
			PongBall.x = xm+20;
		}
		
		vx+= X-xm;
		xm = X;
	}
}