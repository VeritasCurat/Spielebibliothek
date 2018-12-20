package schach;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SchachKI2 {
	int cnt_pruning; int cnt_gesamt; //TODO: löschen wenn alphabeta verifiziert wurde

	public String eigenefarbe, gegnerfarbe;
	int KI_x1=-1; int KI_x2=-1; int KI_y1=-1; int KI_y2=-1;
	static final int bauer_wert = 10; 	static final int pferd_wert = 30; 	static final int läufer_wert = 30; 	static final int turm_wert = 50;	static final int dame_wert = 90; 	static final int könig_wert = 900;

	int gegnerPunkteMIN; int eigenePunkteMAX;
	
	Schachbrett schattenbrett;
	Schachbrett S;
	static String Strategie = "zufall";

	public int rekursionstiefe = 4; //Bester wert: 3
	static int alphabeta=0;
	String algorithmus="";
	
	List<Integer[]> zuege;
	public SchachKI2(String farbe, String algo,int rekur) {
		zuege = new ArrayList<Integer[]>();
		schattenbrett = new Schachbrett();
		eigenefarbe = farbe; 
		gegnerfarbe = invFarbe(eigenefarbe);
		this.algorithmus = algo;
		this.rekursionstiefe = rekur;
	}
	
	public void zug_generieren() {
		Schachbrett.write_ab(S, schattenbrett);
		//schattenbrett.print();
		Integer[] anfang = {0,0,0,0,0};
		//System.out.println("---------------------------------------\n\n\n");
		//minimax-Algorithmus
			Integer[] a = null;
			if(algorithmus.equals("minimax")) a = minimax(rekursionstiefe, eigenefarbe, anfang, schattenbrett);
		//minimax-Algorithmus + alphabeta-pruning
			if(algorithmus.equals("alphabeta")) {
				a = alphabeta(rekursionstiefe, "schwarz", anfang, schattenbrett, Integer.MIN_VALUE, Integer.MAX_VALUE);
				//System.out.println(cnt_pruning+" / "+cnt_gesamt+" = "+(cnt_pruning/cnt_gesamt));
			}
		
		//System.out.println("Endergebnis: "+a[0]+" "+a[1]+" => "+a[2]+" "+a[3]+": "+a[4]);
		KI_x1=a[0];
		KI_y1=a[1];
		KI_x2=a[2];
		KI_y2=a[3];
	}
	
	
	
	
	private Integer[] alphabeta(int tiefe, String farbe, Integer[] zug, Schachbrett sb, int eigenePunkteMAX, int gegnerPunkteMIN) {
		++cnt_gesamt;
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(sb, kopie);
		kopie.bewegungAusführen(zug[0], zug[1], zug[2], zug[3], invFarbe(farbe));
		//System.out.println(zug[0]+", "+zug[1]+" => "+zug[2]+" "+zug[3]);
		
//		int wert = 0;
//		if(eigenefarbe.equals("schwarz"))wert = kopie.figurenpunkte_schwarz() - kopie.figurenpunkte_weiß(); 
//		if(eigenefarbe.equals("weiß"))wert = kopie.figurenpunkte_weiß() - kopie.figurenpunkte_schwarz(); 
		
		
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
		
		String neuefarbe =  invFarbe(farbe);
		f: for(int i=0; i<max_zuege.size(); i++) {
				int neuerwert = alphabeta(tiefe-1,neuefarbe, max_zuege.get(i), kopie, eigenePunkteMAX, gegnerPunkteMIN)[4];
				//if(tiefe==2)System.out.println(max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+": "+wert);;
				//if(tiefe==1)System.out.println("    "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+" "+wert);;
				
				//alpha pruning
				if(farbe.equals(eigenefarbe)) {
					if(neuerwert > eigenePunkteMAX) {
						//System.out.println(tiefe+" "+eigenefarbe+": "+wert+" : "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]);
						eigenePunkteMAX=neuerwert;
					}
					if(neuerwert < eigenePunkteMAX) {
						//System.out.println(tiefe+": "+invFarbe(farbe)+" "+neuerwert+" < "+eigenePunkteMAX);		
						++cnt_pruning;
						continue f;
					}	
				}
				
				//beta pruning
				if(farbe.equals(gegnerfarbe)) {
					if(neuerwert < gegnerPunkteMIN) {
						//System.out.println(tiefe+" "+gegnerfarbe+": "+wert+" : "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]);
						gegnerPunkteMIN=neuerwert;
					}
					if(neuerwert > gegnerPunkteMIN) {
						++cnt_pruning;
						continue;		
					}
				}
				
				
				if(farbe.equals(eigenefarbe)) {
					if(neuerwert > best_wert) {
						best_wert = neuerwert;
						best_index = i;
						max_zuege.get(i)[4] = neuerwert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(neuerwert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
				if(farbe.equals(gegnerfarbe)) {
					if(neuerwert < best_wert) {
						best_wert = neuerwert;
						best_index = i;
						max_zuege.get(i)[4] = neuerwert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(neuerwert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}		
		}
//		System.out.println("    "+max_zuege.get(best_index)[0]+" "+max_zuege.get(best_index)[1]+" => "+max_zuege.get(best_index)[2]+" "+max_zuege.get(best_index)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(best_index)[0]][max_zuege.get(best_index)[1]].typ+" : "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].farbe+" "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].typ+" "+best_wert);;
//		System.out.println("    "+max_zuege.get(best_index)[0]+" "+max_zuege.get(best_index)[1]+" => "+max_zuege.get(best_index)[2]+" "+max_zuege.get(best_index)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(best_index)[0]][max_zuege.get(best_index)[1]].typ+" : "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].farbe+" "+sb.figuren[max_zuege.get(best_index)[2]][max_zuege.get(best_index)[3]].typ+" "+best_wert);;

		Integer[] pseudo = {0,0,0,0,0};
		if(best_zuege.size()==0 && farbe.equals(eigenefarbe)) {
			pseudo[4] = eigenePunkteMAX;
			return pseudo;
		}
		if(best_zuege.size()==0 && farbe.equals(gegnerfarbe)) {
			pseudo[4] = gegnerPunkteMIN;
			return pseudo;
		}
		
		return heuristik(best_zuege); //max_zuege.get(best_index);
	}
	
	private Integer[] minimax(int tiefe, String farbe, Integer[] zug, Schachbrett sb) {
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(sb, kopie);
		kopie.bewegungAusführen(zug[0], zug[1], zug[2], zug[3], invFarbe(farbe));
		//System.out.println(zug[0]+", "+zug[1]+" => "+zug[2]+" "+zug[3]);
		
		int wert = 0;
		if(eigenefarbe.equals("schwarz"))wert = kopie.figurenpunkte_schwarz() - kopie.figurenpunkte_weiß(); 
		if(eigenefarbe.equals("weiß"))wert = kopie.figurenpunkte_weiß() - kopie.figurenpunkte_schwarz(); 
		
		
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
				int neuerwert = minimax(tiefe-1, invFarbe(farbe), max_zuege.get(i), kopie)[4];
				//if(tiefe==2)System.out.println(max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+": "+wert);;
				//if(tiefe==1)System.out.println("    "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+" "+wert);;
							
				if(farbe.equals(eigenefarbe)) {
					if(neuerwert > best_wert) {
						best_wert = neuerwert;
						best_index = i;
						max_zuege.get(i)[4] = neuerwert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(neuerwert == best_wert) {
						best_zuege.add(max_zuege.get(i));
					}
				}
				if(farbe.equals(gegnerfarbe)) {
					if(neuerwert < best_wert) {
						best_wert = neuerwert;
						best_index = i;
						max_zuege.get(i)[4] = neuerwert;
						best_zuege = new ArrayList<Integer[]>();
					}
					if(neuerwert == best_wert) {
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
	
	private static Integer[] zugsortierung(List<Integer[]> zuege, Schachbrett b, String farbe) {
		Schachbrett[] z = new Schachbrett[zuege.size()];
		for(int index=0; index<zuege.size(); index++) {
			z[index].bewegungAusführen(zuege.get(index)[0], zuege.get(index)[1], zuege.get(index)[2], zuege.get(index)[3], farbe);
		}
		
		//TODO:
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
