 
package go;

public class GoFigur {
	boolean automatic = true;
	
	String farbe="";
	int X=-1;
	int Y=-1;
	int freiheiten=0;
	int nachbarn=0;
	
	public GoFigur(int x, int y, String farbe) {
		this.X = x;
		this.Y = y;
		this.farbe = farbe;
	}

	boolean move(int i, int j) {
		if(i<0 || j<0 || i >=19  || j>=19)return false;
		return true;
	}
}