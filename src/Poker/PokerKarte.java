package Poker;

public class PokerKarte {
	
	static String[] farben = {"Piek", "Caro", "Kreuz", "Herz"};
	static String[] werte = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Bube", "Dame", "König", "Ass"};
	
	String farbe;
	String wert;
	
	public PokerKarte(String Farbe, String Wert) {
		farbe = Farbe; wert = Wert;
	}
	
	public static int karte_wert(PokerKarte k) {
		for(int i=0; i<werte.length; i++) {
			if(k.wert.equals(werte[i]))return i+2;
		}
		System.out.println("falscher Wert!");
		System.exit(-1);
		return 0;
	}
	
	public void print() {
		System.out.println(farbe+" "+wert);
	}
	
	
}
