 
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
		System.out.println("FALSCHER CAST!");
		System.exit(-1);
		return false;
	}
}


class L�ufer extends  M�hleFigur{

	public L�ufer(int x, int y, int Ring, String farbe) {
		super(x, y,Ring, farbe);
		typ = "L�ufer";
		this.farbe= farbe; 
		System.out.println(this.farbe);
	}
	
	//verifiziert ob Bewegung dem Muster dieser Figur entspricht
	boolean move(int a, int b, int ring) {//TODO: effizienter schreiben	
		if(this.Ring == ring) {
			//interring bewegung
				//Bedingung1: Abstand=1
				if(Math.abs(X-a)+Math.abs(Y-b) == 1)return true;
		}
		//extraring bewegung
		if(this.Ring != ring) { 
			if(Math.abs(Ring-ring) == 1 && X==a && Y==b)return true;
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
	
	boolean move(int x, int y,int ring) {

		return true;
	}
	
}
