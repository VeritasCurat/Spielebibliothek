package pong;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.glass.ui.Size;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class PongSpielfeld {
	
	static int x; private static int y; 
	static ArrayList<PongEffekt> effektliste = new ArrayList<>();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void init(int X, int Y) {
		x = X; setY(Y);
	}
	
	private static int nextVX(int entfernung) {
		return entfernung*6;
	}
	
	public static void explosion(int x, int y) throws InterruptedException {
		if(x<0 || x>PongLevel.sizeX-1 || y<0 || y>PongLevel.sizeY-1)return;
		if(PongLevel.b[x][y].explosiv && PongLevel.b[x][y].alive) {
			System.out.println("explosion: "+x+" "+y);
			PongLevel.b[x][y].alive=false;
			explosion(x-1,y-1);
			explosion(x,y-1);
			explosion(x+1,y-1);
			explosion(x-1,y);
			explosion(x+1,y);
			explosion(x-1,y+1);
			explosion(x,y+1);
			explosion(x+1,y+1);
		}
	}
	
	public static void Kollisionstest() throws InterruptedException {
		if(PongBall.x+PongBall.size/2>=PongSpielfeld.x-20)PongBall.invertVX();
		if(PongBall.x-PongBall.size/2<0)PongBall.invertVX();
		if(PongBall.y-PongBall.size/2<0)PongBall.invertVY();
		
		
		
		int bx=PongKelle.xm-PongKelle.sizeX/2;
		int by=PongKelle.y;
		int bSX=PongKelle.sizeX;
		int bSY=PongKelle.sizeY;
		
		boolean getroffen = (PongBall.x+PongBall.size >= bx && PongBall.x <= bx+bSX && PongBall.y+PongBall.size >= by && PongBall.y <= by+bSY);

		if(getroffen) {
			boolean linkeSeite = PongBall.vx>=0 && Math.abs(PongBall.x-bx)<=PongBall.size;
			boolean rechteSeite = PongBall.vx<=0 && Math.abs(PongBall.x-(bx+bSX))<=PongBall.size;
			boolean obereSeite =  PongBall.vy>=0 && Math.abs(PongBall.y-by)<=PongBall.size;
			boolean untereSeite =  PongBall.vy<=0 && Math.abs(PongBall.y-(by+bSY))<=PongBall.size;
			
			if(linkeSeite || rechteSeite) {
				PongBall.invertVX();
				PongBall.invertVY();	
			}
			else if(obereSeite || untereSeite) {
				int entfernung_mittelpunkt = (int) PongBall.x - PongKelle.xm;				
				PongBall.vx_verändern(nextVX(entfernung_mittelpunkt));

				PongBall.invertVY();	
			}
		}	
		else {
			a:for(int i=0; i<PongLevel.sizeX; i++) {
				for(int j=0; j<PongLevel.sizeY; j++) {
					if(!PongLevel.b[i][j].alive || getroffen)continue;
					
					 bx=PongLevel.b[i][j].x;
					 by=PongLevel.b[i][j].y;
					 bSX=PongLevel.b[i][j].sizeX;
					 bSY=PongLevel.b[i][j].sizeY;
					
					
					 getroffen = (PongBall.x+PongBall.size >= bx && PongBall.x <= bx+bSX && PongBall.y+PongBall.size >= by && PongBall.y <= by+bSY);
					
					 if(getroffen) {
						boolean linkeSeite = PongBall.vx>=0 && Math.abs(PongBall.x-bx)<=PongBall.size;
						boolean rechteSeite = PongBall.vx<=0 && Math.abs(PongBall.x-(bx+bSX))<=PongBall.size;
						boolean obereSeite =  PongBall.vy>=0 && Math.abs(PongBall.y-by)<=PongBall.size;
						boolean untereSeite =  PongBall.vy<=0 && Math.abs(PongBall.y-(by+bSY))<=PongBall.size;
						
						if(linkeSeite || rechteSeite) {
							explosion(i,j);
							PongLevel.b[i][j].getroffen();
							PongBall.invertVX();
							continue;
						}
						if(obereSeite || untereSeite) {
							explosion(i,j);
							PongLevel.b[i][j].getroffen();
							PongBall.invertVY();	
							continue;
						}
					}
					if(getroffen)break a;
				}
			}
		}
		
	}
	
	public static void moveKelle(int x) {
		if(x > PongKelle.xm && x+PongKelle.sizeX/2 > x)return;
		else if(x < PongKelle.xm && x-PongKelle.sizeX/2 < 0)return;
		else PongKelle.xm = x;
	}

	public static void effektliste_akt() {
		for(int i=0; i<effektliste.size(); i++) {
			effektliste.get(i).zeit_aktualisieren();
			if(effektliste.get(i).zeitDif<=0) {
				effekt_ausführen(effektliste.get(i).name);
				effektliste.remove(i);
			}
		}
		
	}
	
	public static void effekt_ausführen(String e) {
		switch(e) {
		case "ball vergrößern":{
			PongBall.size*=1.5;
			break;
		}
		case "ball verkleinern":{
			PongBall.size/=1.5;
			break;
		}
		case "kelle vergrößern":{
			PongKelle.sizeX*=1.5;
			break;
		}
		case "kelle verkleinern":{
			PongKelle.sizeX/=1.5;
			break;
		}
		case "geschwindigkeit vergrößern":{
			PongBall.vx*=1.5;
			PongBall.vy*=1.5;
			break;
		}
		case "geschwindigkeit verkleinern":{
			PongBall.vx/=1.5;
			PongBall.vy/=1.5;
			break;
		}
	}
	}
	
	public static void effekt_einfügen(String e) {
		switch(e) {
			case "ball vergrößern":{
				effekt_ausführen("ball vergrößern");				
				effektliste.add(new PongEffekt("ball verkleinern", System.currentTimeMillis()+5000));
				break;
			}
			case "ball verkleinern":{
				effekt_ausführen("ball verkleinern");						
				effektliste.add(new PongEffekt("ball vergrößern", System.currentTimeMillis()+5000));
				break;
			}
			case "kelle vergrößern":{
				effekt_ausführen("kelle vergrößern");						
				effektliste.add(new PongEffekt("kelle verkleinern", System.currentTimeMillis()+5000));
				break;
			}
			case "kelle verkleinern":{
				effekt_ausführen("kelle verkleinern");		
				effektliste.add(new PongEffekt("kelle vergrößern", System.currentTimeMillis()+5000));
				break;
			}
			case "geschwindigkeit vergrößern":{
				effekt_ausführen("geschwindigkeit vergrößern");		
				effektliste.add(new PongEffekt("geschwindigkeit verkleinern", System.currentTimeMillis()+5000));
				break;
			}
			case "geschwindigkeit verkleinern":{
				effekt_ausführen("geschwindigkeit verkleinern");		
				effektliste.add(new PongEffekt("geschwindigkeit vergrößern", System.currentTimeMillis()+5000));
				break;
			}
		}
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		PongSpielfeld.y = y;
	}
}




