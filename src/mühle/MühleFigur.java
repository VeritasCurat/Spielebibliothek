 
package m�hle;

public class M�hleFigur {
	boolean automatic = true;
	
	String typ="NULL";
	String farbe="";
	int Ring = -1;
	int X=-1;
	int Y=-1;
	
	public M�hleFigur(int x, int y, int Ring, String farbe) {
		this.X = x;
		this.Y = y;
		this.Ring = Ring;
		this.farbe = "";
		this.typ ="NULL";
	}

	boolean move(int i, int j) {
		return false;
	}
}


class L�ufer extends  M�hleFigur{

	public L�ufer(int x, int y, int Ring, String farbe) {
		super(x, y,Ring, farbe);
		typ = "L�ufer";
		this.farbe= farbe; 
	}
	
	//verifiziert ob Bewegung dem Muster dieser Figur entspricht
	boolean move(int a, int b) {
		
		int x = X-a; int y = Y-b;
		
		if(Math.abs(x+y) == 1) {
			//extraring bewegung
				if(Math.abs(X+Y)==1) { 
					if(Ring==1)return true;
					if(Ring==0 && (X+Y+x+y)==2)return true;
					if(Ring==2 && (X+Y+x+y)==0)return true;
				}
			//interring bewegung
				if(X==0 && Y==0) {//linke, obere Ecke
					if(x==1 || y==1)return true;
					else return false;
				}
				else if(X==2 && Y==0) {
					if(x==-1 || y==1)return true;
					else return false;
				}
				else if(X==2 && Y==0) {
					if(x==1 || y==-1)return true;
					else return false;
				}
				else if(X==2 && Y==2) {
					if(x==-1 || y==-1)return true;
					else return false;
				}
				else if(X==1) {
					if(y==-1 || y==1)return true;
					else return false;
				}
				else if(Y==1) {
					if(x==1 || x==-1)return true;
					else return false;
				}
		}
		return false;
	}
	
}

//jetzt k�nnen die Steine Springen
class Springer extends  M�hleFigur{

	public Springer(int x, int y, int Ring, String farbe) {
		super(x, y, Ring, farbe);
		typ = "Springer";
		this.farbe= farbe; 
	}
	
	boolean move(int x, int y) {
		return true;
	}
	
}
