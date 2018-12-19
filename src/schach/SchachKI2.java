package schach;

import java.util.ArrayList;
import java.util.List;

public class SchachKI2 {
	public String eigenefarbe, gegnerfarbe;
	int KI_x1=-1; int KI_x2=-1; int KI_y1=-1; int KI_y2=-1;
	static final int bauer_wert = 10; 	static final int pferd_wert = 30; 	static final int läufer_wert = 30; 	static final int turm_wert = 50;	static final int dame_wert = 90; 	static final int könig_wert = 900;

	int MINpunkte_weiß; int MAXpunkte_schwarz;
	
	Schachbrett schattenbrett;
	Schachbrett S;
	static String Strategie = "zufall";

	public int rekursionstiefe = 4; //Bester wert: 3
	static int alphabeta=0;
	
	
	List<Integer[]> zuege;
	public SchachKI2(String farbe) {
		zuege = new ArrayList<Integer[]>();
		schattenbrett = new Schachbrett();
		eigenefarbe = farbe; 
		gegnerfarbe = invFarbe(eigenefarbe);
	}
	
	public void zug_generieren() {
		Schachbrett.write_ab(S, schattenbrett);
		//schattenbrett.print();
		Integer[] anfang = {0,0,0,0,0};
		//System.out.println("---------------------------------------\n\n\n");
		//minimax-Algorithmus
			Integer[] a = minimax(rekursionstiefe, eigenefarbe, anfang, schattenbrett);
		//minimax-Algorithmus + alphabeta-pruning
			//MAXpunkte_schwarz = Integer.MIN_VALUE; MINpunkte_weiß = Integer.MAX_VALUE;
			//Integer[] a = alphabeta(rekursionstiefe, "schwarz", anfang, schattenbrett);
		
		//System.out.println("Endergebnis: "+a[0]+" "+a[1]+" => "+a[2]+" "+a[3]+": "+a[4]);
		KI_x1=a[0];
		KI_y1=a[1];
		KI_x2=a[2];
		KI_y2=a[3];
	}
	
	private Integer[] alphabeta(int tiefe, String farbe, Integer[] zug, Schachbrett sb) {
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(sb, kopie);
		kopie.bewegungAusführen(zug[0], zug[1], zug[2], zug[3], invFarbe(farbe));
		
		//Basisschritt: return wert des erstens zugs (oder: eines zufälligen)
		if(tiefe == 0) {
			Integer a[] = new Integer[5];  
			if(eigenefarbe.equals("schwarz"))a[4] = kopie.figurenpunkte_schwarz() - kopie.figurenpunkte_weiß(); 
			if(eigenefarbe.equals("weiß"))a[4] = kopie.figurenpunkte_weiß() - kopie.figurenpunkte_schwarz(); 
			return a;
		}		
			
		//Rekursionsschritt: führe die Bewegung aus; finde das Maximum unter den zügen
		List<Integer[]> max_zuege = mögliche_zuege(farbe, kopie);
		if(max_zuege.isEmpty()) {
			Integer[] a = {-1,-1,-1,-1, Integer.MIN_VALUE};
			return a;
		}
		
		
		//wenn invFarbe(farbe)=schwarz: wert maximieren, sonst: minimieren
		int best_wert = 0;int best_index=0;
		if(farbe.equals("schwarz")) best_wert =Integer.MIN_VALUE; 
		if(farbe.equals("weiß")) best_wert =Integer.MAX_VALUE; 
		
		List<Integer[]> best_zuege = new ArrayList<Integer[]>();
		
		for(int i=0; i<max_zuege.size(); i++) {
				int wert = alphabeta(tiefe-1, invFarbe(farbe), max_zuege.get(i), kopie)[4];
				
				if(farbe.equals("schwarz")) {
					//alpha pruning
					if(wert > MAXpunkte_schwarz) {
						System.out.println("schwarz="+wert+" : "+zug[0]+" "+zug[1]+" => "+zug[2]+" "+zug[3]);
						MAXpunkte_schwarz=wert;
					}
					if(wert < MAXpunkte_schwarz) {
						max_zuege.get(i)[4] = wert;
						return max_zuege.get(i);
					}
					if(wert > best_wert) {
						best_wert = wert;
						best_index = i;
						max_zuege.get(i)[4] = wert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(wert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
				if(farbe.equals("weiß")) {
					//beta pruning
//					if(wert < MINpunkte_weiß) {
//						System.out.println("weiß="+wert+" : "+zug[0]+" "+zug[1]+" => "+zug[2]+" "+zug[3]);
//						MINpunkte_weiß=wert;
//					}
//					if(wert > MINpunkte_weiß) {
//						max_zuege.get(i)[4] = wert;
//						return max_zuege.get(i);
//					}
					if(wert < best_wert) {
						best_wert = wert;
						best_index = i;
						max_zuege.get(i)[4] = wert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(wert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
					
		}		
		return heuristik(best_zuege); //max_zuege.get(best_index);
	}
	
	private Integer[] minimax(int tiefe, String farbe, Integer[] zug, Schachbrett sb) {
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(sb, kopie);
		//System.out.println(zug[0]+", "+zug[1]+" => "+zug[2]+" "+zug[3]);
		kopie.bewegungAusführen(zug[0], zug[1], zug[2], zug[3], invFarbe(farbe));
		
		//Basisschritt: return wert des erstens zugs (oder: eines zufälligen)
		if(tiefe == 0) {
			Integer a[] = new Integer[5];  
			if(eigenefarbe.equals("schwarz"))a[4] = kopie.figurenpunkte_schwarz() - kopie.figurenpunkte_weiß(); 
			if(eigenefarbe.equals("weiß"))a[4] = kopie.figurenpunkte_weiß() - kopie.figurenpunkte_schwarz(); 
			return a;
		}
		
			
		//Rekursionsschritt: führe die Bewegung aus; finde das Maximum unter den zügen
		List<Integer[]> max_zuege = mögliche_zuege(farbe, kopie);
		if(max_zuege.isEmpty()) {
			Integer[] a = {-1,-1,-1,-1, Integer.MIN_VALUE};
			return a;
		}
		
		
		//wenn invFarbe(farbe)=schwarz: wert maximieren, sonst: minimieren
		int best_wert = 0;
		int best_index=0;
		if(farbe.equals(eigenefarbe)) best_wert =Integer.MIN_VALUE; 
		if(farbe.equals(gegnerfarbe)) best_wert =Integer.MAX_VALUE; 
		
		List<Integer[]> best_zuege = new ArrayList<Integer[]>();
		
		for(int i=0; i<max_zuege.size(); i++) {
				int wert = minimax(tiefe-1, invFarbe(farbe), max_zuege.get(i), kopie)[4];
				//if(tiefe==2)System.out.println(max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+": "+wert);;
				//if(tiefe==1)System.out.println("    "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+" "+wert);;
							
				if(farbe.equals(eigenefarbe)) {
					if(wert > best_wert) {
						best_wert = wert;
						best_index = i;
						max_zuege.get(i)[4] = wert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(wert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
				if(farbe.equals(gegnerfarbe)) {
					if(wert < best_wert) {
						best_wert = wert;
						best_index = i;
						max_zuege.get(i)[4] = wert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(wert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
					
		}
//		System.out.println("    "+max_zuege.get(best_index)[0]+" "+max_zuege.get(best_index)[1]+" => "+max_zuege.get(best_index)[2]+" "+max_zuege.get(best_index)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(best_index)[0]][max_zuege.get(best_index)[1]].typ+" : "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].farbe+" "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].typ+" "+best_wert);;
//		System.out.println("    "+max_zuege.get(best_index)[0]+" "+max_zuege.get(best_index)[1]+" => "+max_zuege.get(best_index)[2]+" "+max_zuege.get(best_index)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(best_index)[0]][max_zuege.get(best_index)[1]].typ+" : "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].farbe+" "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].typ+" "+best_wert);;

		
		return heuristik(best_zuege); //max_zuege.get(best_index);
	}
	
	
	private List<Integer[]> mögliche_zuege(String farbe, Schachbrett b) {
		List<Integer[]> best_zuege = new ArrayList<>(); 
								
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(!b.figuren[x1][y1].farbe.equals(farbe))continue;
				for(int x2=0; x2<8; x2++) {
					for(int y2=0; y2<8; y2++) {
						if(x1==x2 && y1==y2)continue;
						if(!b.bewegungPrüfen(x1,y1,x2,y2,farbe))continue;
						Integer[] a = {x1,y1,x2,y2,0};
						best_zuege.add(a);
					}	
				}	
			}
		}
		return best_zuege;
	}
	
	/**
	 * zuege sind gleichwertig nach minmax. Entscheide mithilfe von Strategie.
	 * @param zuege
	 */
	private static Integer[] heuristik(List<Integer[]> zuege) {
		//1. figuren entwickeln => raum ist wertvoll
		 
		
		if(Strategie.equals("test")) {
			return zuege.get(0);
		}
		if(Strategie.equals("zufall")) {
			return zuege.get((int) (Math.random()*zuege.size()));
		}
		
		return null;
	}
	
	/**
	 * @param farbe = weiß => return schwarz; und umgekehrt
	 * @return
	 */
	public static String invFarbe(String farbe) {
		if(farbe.equals("weiß"))return "schwarz";
		else if(farbe.equals("schwarz"))return "weiß";
		else return "";
	}
	
}
