package schach;

import java.util.ArrayList;
import java.util.List;

public class SchachKI2 {
	static int KI_x1=-1; static int KI_x2=-1; static int KI_y1=-1; static int KI_y2=-1;
	static final int bauer_wert = 10; 	static final int pferd_wert = 30; 	static final int l�ufer_wert = 30; 	static final int turm_wert = 50;	static final int dame_wert = 90; 	static final int k�nig_wert = 900;

	static String KIfarbe = "schwarz";

	Schachbrett schattenbrett;
	Schachbrett S;
	static String Strategie = "test";

	static int rekursionstiefe = 2; //Bester wert: 3
	static int alphabeta=0;
	
	List<Integer[]> zuege;
	public SchachKI2() {
		zuege = new ArrayList<Integer[]>();
		schattenbrett = new Schachbrett();
	}
	
	public void zug_generieren() {
		Schachbrett.write_ab(S, schattenbrett);
		schattenbrett.print();
		Integer[] anfang = {0,0,0,0,0};
		System.out.println("---------------------------------------\n\n\n");
		Integer[] a = minimax(rekursionstiefe, "schwarz", anfang, schattenbrett);
		System.out.println("Endergebnis: "+a[0]+" "+a[1]+" => "+a[2]+" "+a[3]+": "+a[4]);
		KI_x1=a[0];
		KI_y1=a[1];
		KI_x2=a[2];
		KI_y2=a[3];
	}
	
	private Integer[] minimax(int tiefe, String farbe, Integer[] zug, Schachbrett sb) {
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(sb, kopie);
		//System.out.println(zug[0]+", "+zug[1]+" => "+zug[2]+" "+zug[3]);
		kopie.bewegungAusf�hren(zug[0], zug[1], zug[2], zug[3], invFarbe(farbe));
		
		//Basisschritt: return wert des erstens zugs (oder: eines zuf�lligen)
		if(tiefe == 0) {
			Integer a[] = new Integer[5];  
			if(invFarbe(farbe).equals("wei�")) a[4] =  -(kopie.figurenpunkte_wei�() - kopie.figurenpunkte_schwarz());
			else if(invFarbe(farbe).equals("schwarz")) a[4] = kopie.figurenpunkte_schwarz() - kopie.figurenpunkte_wei�(); 
			return a;
		}
		
			
		//Rekursionsschritt: f�hre die Bewegung aus; finde das Maximum unter den z�gen
		List<Integer[]> max_zuege = m�gliche_zuege(farbe, kopie);
		if(max_zuege.isEmpty()) {
			Integer[] a = {-1,-1,-1,-1, Integer.MIN_VALUE};
			return a;
		}
		
		
		//wenn invFarbe(farbe)=schwarz: wert maximieren, sonst: minimieren
		int best_wert = 0;int best_index=0;
		if(farbe.equals("schwarz")) best_wert =Integer.MIN_VALUE; 
		if(farbe.equals("wei�")) best_wert =Integer.MAX_VALUE; 
		
		List<Integer[]> best_zuege = new ArrayList<Integer[]>();
		
		for(int i=0; i<max_zuege.size(); i++) {
				int wert = minimax(tiefe-1, invFarbe(farbe), max_zuege.get(i), kopie)[4];
				//if(tiefe==2)System.out.println(max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+": "+wert);;
				//if(tiefe==1)System.out.println("    "+max_zuege.get(i)[0]+" "+max_zuege.get(i)[1]+" => "+max_zuege.get(i)[2]+" "+max_zuege.get(i)[3]+" "+farbe+" "+sb.figuren[max_zuege.get(i)[0]][max_zuege.get(i)[1]].typ+" : "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].farbe+" "+sb.figuren[max_zuege.get(i)[2]][max_zuege.get(i)[3]].typ+" "+wert);;
			
				if(!kopie.bewegungPr�fen(max_zuege.get(i)[0],max_zuege.get(i)[1],max_zuege.get(i)[2],max_zuege.get(i)[3],farbe))continue;
				
				if(farbe.equals("schwarz")) {
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
				if(farbe.equals("wei�")) {
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
	
	
	private List<Integer[]> m�gliche_zuege(String farbe, Schachbrett b) {
		b.automatic = true;	
		List<Integer[]> best_zuege = new ArrayList<>(); 
		
		Schachbrett kopie = new Schachbrett();
		Schachbrett.write_ab(b, kopie);
						
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(!b.figuren[x1][y1].farbe.equals(farbe))continue;
				for(int x2=0; x2<8; x2++) {
					for(int y2=0; y2<8; y2++) {
						if(x1==x2 && y1==y2)continue;
						if(!b.bewegungPr�fen(x1,y1,x2,y2,farbe))continue;
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
	 * @param farbe = wei� => return schwarz; und umgekehrt
	 * @return
	 */
	public static String invFarbe(String farbe) {
		if(farbe.equals("wei�"))return "schwarz";
		else if(farbe.equals("schwarz"))return "wei�";
		else return "";
	}
}
