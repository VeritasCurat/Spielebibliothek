package Poker;

import java.util.ArrayList;

public class PokerController {
	
	
	PokerSpiel S;
	static PokerController P;
	PokerGUI G;
	
	int anz_Spieler=2;
	
	public PokerController() {
		
	}
	
	public static void main(String[] args) {
		P = new PokerController();

		P.neuesSpiel();
	}
	
	public static String spielerkarte(String farbe, String wert) {
		if(wert.equals("10"))return farbe.substring(0, 1).toLowerCase()+wert.substring(0,2);
		return farbe.substring(0, 1).toLowerCase()+wert.substring(0,1);
	}
	
	public void neuesSpiel() {

		String[] namensliste = {"spieler1", "spieler2"};
		S = new PokerSpiel(2, 10000, namensliste);
		G = new PokerGUI(2);
		
		G.repaint();
		//while(S.alive) {
			//Blinds
			
			
			
			//Karten ausgeben
				for(int s=0; s<anz_Spieler; s++) {
					int farbe, wert;
					//1 Karte
						do {
							farbe = (int) (Math.random()*4);
							wert = (int) (Math.random()*13);	
						} while(S.vergeben[farbe][wert]);
						PokerKarte zufall = new PokerKarte(PokerKarte.farben[farbe],PokerKarte.werte[wert]);
						S.vergeben[farbe][wert]= true;
						S.spieler.get(s).KarteZuBlatt(zufall);
						switch(s) {
							case 0: {								
								G.canvas.darstellungS1.add(spielerkarte(zufall.farbe, zufall.wert));
								break;
							}
							case 1: {								
								G.canvas.darstellungS2.add(spielerkarte(zufall.farbe, zufall.wert));
								break;
							}
							case 2: {								
								G.canvas.darstellungS3.add(spielerkarte(zufall.farbe, zufall.wert));
								break;
							}
							case 3: {								
								G.canvas.darstellungS4.add(spielerkarte(zufall.farbe, zufall.wert));
								break;
							}
						}
					
					//2 Karte
						do {
							farbe = (int) (Math.random()*4);
							wert = (int) (Math.random()*13);	
						} while(S.vergeben[farbe][wert]);
						zufall = new PokerKarte(PokerKarte.farben[farbe],PokerKarte.werte[wert]);
						S.vergeben[farbe][wert]= true;
						S.spieler.get(s).KarteZuBlatt(zufall);	
						switch(s) {
						case 0: {								
							G.canvas.darstellungS1.add(spielerkarte(zufall.farbe, zufall.wert));
							break;
						}
						case 1: {								
							G.canvas.darstellungS2.add(spielerkarte(zufall.farbe, zufall.wert));
							break;
						}
						case 2: {								
							G.canvas.darstellungS3.add(spielerkarte(zufall.farbe, zufall.wert));
							break;
						}
						case 3: {								
							G.canvas.darstellungS4.add(spielerkarte(zufall.farbe, zufall.wert));
							break;
						}
					}
				}
				
				//2 CommunityKarten legen
					int farbe, wert;
					//1 Karte
						do {
							farbe = (int) (Math.random()*4);
							wert = (int) (Math.random()*13);	
						} while(S.vergeben[farbe][wert]);
						PokerKarte zufall = new PokerKarte(PokerKarte.farben[farbe],PokerKarte.werte[wert]);
						S.vergeben[farbe][wert]= true;
						S.communitykarten.add(zufall);
						G.canvas.darstellungC.add(spielerkarte(zufall.farbe, zufall.wert));
				
					//2 Karte
						do {
							farbe = (int) (Math.random()*4);
							wert = (int) (Math.random()*13);	
						} while(S.vergeben[farbe][wert]);
						zufall = new PokerKarte(PokerKarte.farben[farbe],PokerKarte.werte[wert]);
						S.vergeben[farbe][wert]= true;
						S.communitykarten.add(zufall);
						G.canvas.darstellungC.add(spielerkarte(zufall.farbe, zufall.wert));
					
				
				int hand[] = new int[2];	
				//spieler 1
					hand = PokerSpiel.pokerhand(S.spieler.get(0).Blatt, S.communitykarten);
					G.canvas.HandSpieler[0] = PokerSpiel.kombinationen[hand[0]]+" ("+hand[1]+")";
					G.canvas.Budget[0] = S.spieler.get(0).geld;
						
				//spieler 2
					hand = PokerSpiel.pokerhand(S.spieler.get(1).Blatt, S.communitykarten);
					G.canvas.HandSpieler[1] = PokerSpiel.kombinationen[hand[0]]+" ("+hand[1]+")";
					G.canvas.Budget[1] = S.spieler.get(1).geld;
					
				for(int i=0; i<=anz_Spieler; i++) {					
					G.canvas.Einsatz[0]	+= G.canvas.Einsatz[i+1];	
				}
				G.repaint();
				
				
				
				//1. Wettrunde

				//TODO: Einsatz von Spielern abfragen,
				//TODO: 
				
		//}
		
				
	}

}

