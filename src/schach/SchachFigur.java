 
package schach;

public class SchachFigur {
	boolean automatic = true;
	boolean bewegt = false;
	
	String typ="";
	String farbe="";
	String darstellung="";
	int X=-1;
	int Y=-1;
	
	public SchachFigur(int x, int y, String farbe, boolean Bewegt) {
		bewegt = Bewegt;
		this.X = x;
		this.Y = y;
		this.darstellung = " ";
		this.farbe = "";
		this.typ ="";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	boolean move(int i, int j) {
		System.out.println("FEHLER!");
		return false;
	}

}


class Bauer extends  SchachFigur{

	public Bauer(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "Bauer";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "B";
		else darstellung = "b";
	}
	
	//verifiziert ob Bewegung dem Muster dieser Figur entspricht
	boolean move(int x, int y) {
		if(x == 0 || x ==  -1 || x == 1) {
			if(farbe.equals("weiﬂ")) {
				if(Y == 1 && y == 2 && x == 0) return true;
				if(y == 1) return true;
			}
			if(farbe.equals("schwarz")) {
				if(Y == 6 && y == -2 && x==0) return true;
				if(y == -1) return true;
			}
		}
		if(!automatic)System.out.println("Du kannst den Bauern nicht so bewegen!");
		return false;
	}
	
}

class Turm extends  SchachFigur{


	public Turm(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "Turm";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "T";
		else darstellung = "t";
	}
	
	boolean move(int x, int y) {
		if(x == 0 || y == 0) {
			return true;
		}
		if(!automatic)System.out.println("Du kannst den Turm nicht so bewegen!");
		return false;
	}
	
}

class Pferd extends  SchachFigur{

	public Pferd(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "Pferd";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "P";
		else darstellung = "p";
	}
	
	boolean move(int x, int y) {
		if((Math.abs(x) == 2 || Math.abs(y) == 2) && Math.abs(x)+Math.abs(y) == 3) { 
			return true;
		}
		if(!automatic)System.out.println("Du kannst das Pferd nicht so bewegen!");
		return false;
	}
	
}

class L‰ufer extends  SchachFigur{

	public L‰ufer(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "L‰ufer";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "L";
		else darstellung = "l";
	}
	
	boolean move(int x, int y) {
		if(x != 0 && y != 0 && (Math.abs(x) == Math.abs(y))) {
			return true;
		}
		if(!automatic)System.out.println("Du kannst den L‰ufer nicht so bewegen!");
		return false;
	}
}

class Dame extends  SchachFigur{

	public Dame(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "Dame";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "D";
		else darstellung = "d";
	}
	
	boolean move(int x, int y) {
		if(x == 0 || y == 0) {
			return true;
		}
		if(x != 0 && y != 0 && (Math.abs(x) == Math.abs(y))) {
			return true;
		}
		if(!automatic)System.out.println("Du kannst die Dame nicht so bewegen!");
		return false;
	}
}

class Kˆnig extends SchachFigur{
		
	public Kˆnig(int x, int y,String farbe, Boolean bewegt) {
		super(x, y,farbe,bewegt);
		typ = "Kˆnig";
		this.farbe= farbe; 
		if(farbe.equals("weiﬂ"))darstellung = "K";
		else darstellung = "k";
	}
	
	boolean move(int x, int y) {
		if(Math.abs(x) <= 1 && Math.abs(y) <= 1) {
			return true;
		}
		if(!automatic)System.out.println("Du kannst den Kˆnig nicht so bewegen!");
		return false;
	}
}



