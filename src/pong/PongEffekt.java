package pong;

public class PongEffekt{
	String name="";
	double einl�se_zeitpunkt=0;
	double zeitDif;
	
	int x=0; int y=0; int sizeX=50; int sizeY=50;
	
	public PongEffekt(String name, double einl�se_zeit) {
		this.name = name;
		this.einl�se_zeitpunkt = einl�se_zeit;
		zeitDif = einl�se_zeitpunkt-System.currentTimeMillis();
	}
	
	public void zeit_aktualisieren() {
		zeitDif = einl�se_zeitpunkt-System.currentTimeMillis();
	}
	
}