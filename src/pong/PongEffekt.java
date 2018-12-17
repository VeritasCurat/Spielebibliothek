package pong;

public class PongEffekt{
	String name="";
	double einlöse_zeitpunkt=0;
	double zeitDif;
	
	int x=0; int y=0; int sizeX=50; int sizeY=50;
	
	public PongEffekt(String name, double einlöse_zeit) {
		this.name = name;
		this.einlöse_zeitpunkt = einlöse_zeit;
		zeitDif = einlöse_zeitpunkt-System.currentTimeMillis();
	}
	
	public void zeit_aktualisieren() {
		zeitDif = einlöse_zeitpunkt-System.currentTimeMillis();
	}
	
}