 
package mühle;

public class MühleFigur {
	boolean automatic = true;
	
	String typ="NULL";
	String farbe="";
	int Ring = -1;
	int X=-1;
	int Y=-1;
	
	public MühleFigur(int x, int y, int Ring, String farbe) {
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


class Läufer extends  MühleFigur{

	public Läufer(int x, int y, int Ring, String farbe) {
		super(x, y,Ring, farbe);
		typ = "Läufer";
		this.farbe= farbe; 
	}
	
	//verifiziert ob Bewegung dem Muster dieser Figur entspricht
	boolean move(int a, int b, int ring) {
		System.out.println("Läuferbewegung prüfen: ("+X+","+Y+","+Ring+")"+" => "+"("+a+","+b+","+ring+")");
		int x = a-X; int y = b-Y;
		//interring bewegung
		if(Math.abs(x)+Math.abs(y) == 0) {
			System.out.println("Ring: "+Ring+" => "+ring);
				if(Ring==1)return true;
				if(Ring==0 && ring == 1)return true;
				if(Ring==2 && ring == 1)return true;
		}		
		//extraring bewegung
		else if(Math.abs(x+y) == 1) {
			System.out.println("X: "+X+", Y: "+Y+", a: "+a+", b:"+b);
				if(X==2 && Y==2 || X==0 && Y==0 || X+Y==2 && X!=Y) {//Ecke
					if(a==1 || b==1)return true;
					else return false;
				}
				else if(X==1) {
					if(a==0 || a==2)return true;
					else return false;
				}
				else if(Y==1) {
					if(b==0 || b==2)return true;
					else return false;
				}
		}
		return false;
	}
	
}

//jetzt können die Steine Springen
class Springer extends  MühleFigur{

	public Springer(int x, int y, int Ring, String farbe) {
		super(x, y, Ring, farbe);
		typ = "Springer";
		this.farbe= farbe; 
	}
	
	boolean move(int x, int y, int ring) {
		return true;
	}
	
}
