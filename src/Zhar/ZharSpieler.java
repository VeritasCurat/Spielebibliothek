package Zhar;

public class ZharSpieler {
	String name="";
	int punkte; 
	int ressourcen;
	int bewegungspunkte;
	
	public ZharSpieler(String Name, int ressourcen) {
		this.name = Name;
		this.ressourcen = ressourcen;
		this.punkte = 0;
		this.bewegungspunkte = 0;
	}
}
