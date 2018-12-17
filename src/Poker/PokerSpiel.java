package Poker;

import java.util.ArrayList;
import java.util.Comparator;

public class PokerSpiel{
	
	
	static String[] kombinationen = {"Highcard", "Pair", "Double Pair", "Triple", "Straight", "Flush", "Full House", "Four of a kind", "Straight flush", "Royal Flush"};
	ArrayList<PokerSpieler> spieler;
	ArrayList<PokerKarte> communitykarten = new ArrayList<PokerKarte>();

	boolean alive = true;
	boolean vergeben[][] = new boolean[4][13];

	
	PokerSpiel(int Spieler, int geld, String[] namensliste){
		spieler =  new ArrayList<PokerSpieler>();
		for(int i=0; i<Spieler; i++) {
			spieler.add(new PokerSpieler(namensliste[i], geld));
		}
		for(int i=0; i<4; i++) { //Farbe
			for(int j=0; j<13; j++) { //Wert
				vergeben[i][j] = false;
			}
		}
		
		
	}
	
	/**
	 * 
	 * @param hand
	 * @param community
	 * @return int[1] = kombination, int[1] = wert
	 */
	static int highcard(ArrayList<PokerKarte> hand) {
		
		int highcard = 2;
		for(int i=0; i<hand.size(); i++) {
			if(highcard < PokerKarte.karte_wert(hand.get(i)))highcard =  PokerKarte.karte_wert(hand.get(i));
		}
		return highcard;
	}

	
	/**
	 * 
	 * @param hand
	 * @param überspringen
	 * @return Indezes der in hand Pokerkarten, wenn es ein Paar gibt, sonst null
	 */
	static ArrayList<Integer> paar(ArrayList<PokerKarte> hand) {	
		ArrayList<Integer> ret = new ArrayList<>();
		for(int i=0; i<hand.size(); i++) {	
			 for(int j=0; j<hand.size(); j++) {		
				if(i==j)continue;

				s: if(hand.get(i).wert.equals(hand.get(j).wert) && (i==0 || i==1 || j==0 || j==1)) {
					for(int l: ret) {
						if(l==i || l==j)break s;
					}
					ret.add(i); ret.add(j);
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param hand
	 * @param überspringen
	 * @return Indezes der in hand Pokerkarten, wenn es ein Triple gibt, sonst null
	 */
	static ArrayList<Integer> triple(ArrayList<PokerKarte> hand) {	
		ArrayList<Integer> ret = new ArrayList<>();
		for(int i=0; i<hand.size(); i++) {	
			 for(int j=0; j<hand.size(); j++) {		
				if(i==j)continue;
				for(int k=0; k<hand.size(); k++) {		
					if(k==j || k==i || i==j)continue;
					
					s: if(hand.get(i).wert.equals(hand.get(j).wert) && hand.get(i).wert.equals(hand.get(k).wert)  && (i==0 || i==1 || j==0 || j==1 || k==0 || k==1)) {
						for(int l: ret) {
							if(l==i || l==j || l==k)break s;
						}
						ret.add(i); ret.add(j); ret.add(k);
					}
				}
				
			}
		}
		return ret;
	}
	
	static ArrayList<PokerKarte> sort(ArrayList<PokerKarte> hand){
		hand.sort(new Comparator<PokerKarte>() {

			@Override
			public int compare(PokerKarte arg0, PokerKarte arg1) {
				// TODO Auto-generated method stub
				if(PokerKarte.karte_wert(arg0) < PokerKarte.karte_wert(arg0))return 1; //TODO: richtig?
				return 0;
			}
		});
		return hand;
	}
	
	/**
	 * @param hand
	 * @return wert der Straße (Highcard), sonst -1
	 */
	static int straight(ArrayList<PokerKarte> hand) {	
		if(hand.size() < 5)return -1;
		hand = sort(hand);
		int serie=0; int max=-1;
		for(int i=1; i<hand.size(); i++) {
			if(serie==5) {
				max = PokerKarte.karte_wert(hand.get(i));
			}
			if(PokerKarte.karte_wert(hand.get(i-1)) == PokerKarte.karte_wert(hand.get(i))+1) {
				++serie;
				continue;
			}
			else serie = 0;
		}
		return max;
	}
	
	/**
	 * 
	 * @param hand
	 * @return wert des flush (Highcard), sonst -1
	 */
	static int flush(ArrayList<PokerKarte> hand) {	
		if(hand.size() < 5)return -1;
		int farben[] = new int[4];
		
		for(int i=0; i<hand.size(); i++) {
			switch(hand.get(i).farbe) {
				case "Piek": {
					++farben[0];
					break;
				}
				case "Caro": {
					++farben[1];
					break;
				}
				case "Kreuz": {
					++farben[2];
					break;
				}
				case "Herz": {
					++farben[3];
					break;
				}
			}
		}
		//suche Wert (Highcard)
		for(int i=0; i<4; i++) {
			if(farben[i] >= 5) {
				String f="";
				switch(i) {	
					case 0:{f = "Piek"; break;}
					case 1:{f = "Caro"; break;}
					case 2:{f = "Kreuz"; break;}
					case 3:{f = "Herz"; break;}
				}
				
				int max=0;
				for(int j=0; j<hand.size(); j++) {
					if(hand.get(j).farbe.equals(f)){
						if(max < PokerKarte.karte_wert(hand.get(j)))max = PokerKarte.karte_wert(hand.get(j));
					}
				}
				return max;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param hand
	 * @param überspringen
	 * @return
	 */
	static int poker(ArrayList<PokerKarte> hand) {		
		int ret=-1;
		for(int i=0; i<hand.size(); i++) {	
			 for(int j=0; j<hand.size(); j++) {		
				if(i==j)continue;
				for(int k=0; k<hand.size(); k++) {		
					if(k==j || k==i || i==j)continue;
					for(int l=0; l<hand.size(); l++) {		
						if(l==j || l==i || l==k)continue;
						if(hand.get(i).wert.equals(hand.get(j).wert) && hand.get(i).wert.equals(hand.get(k).wert) && hand.get(i).wert.equals(hand.get(l).wert) && (i==0 || i==1 || j==0 || j==1 || k==0 || k==1 || l==0 || k==0)) {
							ret = PokerKarte.karte_wert(hand.get(i));
						}
					}
				}				
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param hand
	 * @return
	 */
	static int straight_flush(ArrayList<PokerKarte> hand) {	
		if(hand.size() < 5)return -1;
		hand = sort(hand);
		int serie=0; int max=-1;
		for(int i=1; i<hand.size(); i++) {
			if(serie==5) {
				max = PokerKarte.karte_wert(hand.get(i));
			}
			if(PokerKarte.karte_wert(hand.get(i-1)) == PokerKarte.karte_wert(hand.get(i))+1 && hand.get(i).farbe.equals(hand.get(i-1).farbe)) {
				++serie;
				continue;
			}
			else serie = 0;
		}
		return max;
	}
	
	/**
	 * 
	 * @param hand
	 * @return
	 */
	static int royal_flush(ArrayList<PokerKarte> hand) {	
		if(hand.size() < 5)return -1;
		hand = sort(hand);
		int serie=0; int max=-1;
		if(PokerKarte.karte_wert(hand.get(hand.size()-5)) != 9)return -1;
		for(int i=hand.size()-5; i<hand.size(); i++) {
			
			if(serie==5) {
				max = PokerKarte.karte_wert(hand.get(i));
			}
			if(PokerKarte.karte_wert(hand.get(i-1)) == PokerKarte.karte_wert(hand.get(i))+1 && hand.get(i).farbe.equals(hand.get(i-1).farbe)) {
				++serie;
				continue;
			}
			else serie = 0;
		}
		return max;
	}
	
	/**
	 * 
	 * @param paare
	 * @param triple
	 * @param community
	 * @return
	 */
	static int fullhouse(ArrayList<Integer> paare, ArrayList<Integer> triple, ArrayList<PokerKarte> community) {
		 int maxFH=-1; 
		
		 if(paare.size() > 0 && triple.size() > 0) {
				//triple die nicht in paar sind 
				ArrayList<Integer> sym_differenz_triple = new ArrayList<Integer>();
				s: for(int i=0; i<triple.size();i+=3) {
					//ist i,i+1 oder i+2 in paare
					for(int j=0; j<paare.size();j++) {
						if(triple.get(i) == triple.get(j) || triple.get(i+1) == triple.get(j) || triple.get(i+2) == triple.get(j))continue s;
					}
					sym_differenz_triple.add(i); sym_differenz_triple.add(i+1); sym_differenz_triple.add(i+2); 
				}
				
				ArrayList<Integer> sym_differenz_paare= new ArrayList<Integer>();
				s: for(int i=0; i<paare.size();i+=2) {
					//ist i,i+1 in paare
					for(int j=0; j<triple.size();j++) {
						if(triple.get(i) == triple.get(j) || triple.get(i+1) == triple.get(j))continue s;
					}
					sym_differenz_triple.add(i); sym_differenz_triple.add(i+1); 
				}
				
				//gehe durch alle Paare und Triple und finde maximum 
				int maxp = 0; int maxt = 0;
				for(int p=0; p<paare.size(); p+=2) {
					if(PokerKarte.karte_wert(community.get(paare.get(p))) > maxp) maxp = PokerKarte.karte_wert(community.get(paare.get(p)));
				}
				
				for(int t=0; t<paare.size(); t+=3) {
					if(PokerKarte.karte_wert(community.get(paare.get(t))) > maxt) maxt = PokerKarte.karte_wert(community.get(paare.get(t)));
				}
				
				
				maxFH = 1 + maxt + maxp;
		 }
		
		return maxFH;
	}
	
	static int[] pokerhand(ArrayList<PokerKarte> hand, ArrayList<PokerKarte> community) {
		int[] pokerhand = new int[2]; pokerhand[0] = 0; pokerhand[1] = 0;
		int max = 0;
		
		ArrayList<PokerKarte> hand_community = new ArrayList<PokerKarte>();
			for(int i=0; i<hand.size(); i++)hand_community.add(hand.get(i));
			for(int i=0; i<community.size(); i++)hand_community.add(community.get(i));
			
		System.out.print("Blatt: ");
		for(int i=0; i<hand_community.size(); i++)System.out.print(hand_community.get(i).farbe+" "+hand_community.get(i).wert+" ");	
		System.out.println();	
		//highcard
			pokerhand[1] = highcard(hand);
		
		//pair
			ArrayList<Integer> paare = new ArrayList<Integer>(); 
			int max_paar=0;
			if((paare = paar(hand_community)).size() > 0 ) {
				pokerhand[0] = 1;	
				max_paar = 0;
				for(int i=0; i<paare.size(); i+=2) {
					if(PokerKarte.karte_wert(hand_community.get(paare.get(i))) > max_paar)max_paar =PokerKarte.karte_wert(hand_community.get(paare.get(i)));
				}
				pokerhand[1] = max_paar;
			}
		
		//double pair
			if(paare.size() >= 4) {
				pokerhand[0] = 2; pokerhand[1] = 0;
				//suche höchstes doppelpaar
				int max1=max, max2; 
				for(int i=0; i<paare.size(); i+=2) {
					if(pokerhand[1] < (max2=PokerKarte.karte_wert(hand_community.get(paare.get(i)))) && max2 != max1)pokerhand[1] = max2;
				}
				pokerhand[1] += max1;
			}

		
		//triple
			ArrayList<Integer> triple = new ArrayList<Integer>(); 
			int max_triple = 0;
			if((triple = triple(hand_community)).size() > 0 ) {
				pokerhand[0] = 3;	
				max_triple = 0;
				for(int i=0; i<triple.size(); i+=3) {
					if(PokerKarte.karte_wert(hand_community.get(triple.get(i))) > max_triple)max_triple =PokerKarte.karte_wert(hand_community.get(triple.get(i)));
				}
				pokerhand[1] = max_triple;
			}
	
		//straight
			if((max = straight(hand_community)) > -1) {
				pokerhand[0] = 4;
				pokerhand[1] = max;
			}
		
		//flush
			if((max = flush(hand_community)) > -1) {
				pokerhand[0] = 5;
				pokerhand[1] = max;
			}
		
		//full house
			//TODO: triple => full house, weil triple auch als paar gewertet werden; 
			//TODO: ein triple oder paar darf vollständig in der community sein (nur eine Karte muss in hand sein)
			
			
			
		//poker
			if((max = poker(hand_community)) > -1) {
				pokerhand[0] = 7;
				pokerhand[1] = max;
			}
			
		//straight flush
			if((max = straight_flush(hand_community)) > -1) {
				pokerhand[0] = 8;
				pokerhand[1] = max;
			}
			
		//royal flush
			if((max = royal_flush(hand_community)) > -1) {
				pokerhand[0] = 9;
				pokerhand[1] = max;
			}
		
		
		return pokerhand;
	}
}