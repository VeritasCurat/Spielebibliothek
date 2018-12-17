package Poker;

import java.util.ArrayList;

public class PokerSpieler {
	String name = "";
	boolean alive = true;
	int geld;
	ArrayList<PokerKarte> Blatt;
	
	public PokerSpieler(String Name, int geld) {
		Blatt = new ArrayList<PokerKarte>();
		this.name = Name; this.geld = geld;
	}
	
	public void KarteZuBlatt(PokerKarte k) {
		Blatt.add(k);
	}
	
	public PokerKarte getKarte(int i) {
		if(Blatt.size()-1 >= i)return Blatt.get(i);
		else return null;
	}
	
	public void blatt_loeschen() {
		Blatt = new ArrayList<PokerKarte>();
	}
	
	
}
