package schach;

import java.util.ArrayList;
import com.sun.glass.ui.Size;

public class SchachKI {
	static boolean syso1=false;
	
	
	static int KI_x1=-1; static int KI_x2=-1; static int KI_y1=-1; static int KI_y2=-1;
	static int[][][][] Zug = new int[8][8][8][8];
	static boolean FEHLER = false;
	
	//Konstanten für Wertformel
	static int wert_bauer=1;
	static int wert_pferd=3;
	static int wert_läufer=3;
	static int wert_turm=5;
	static int wert_dame=9;
	static int wert_könig=40;
	double wert_schutz=0.2;
	
	int bedrohung_abzug; //figur wird bedroht, 
	
		
	
	int Tiefe = 1;
	
	static int strafpunkte_geschlagenbonus= -1000;
	static int punkte_schlagbonus = 1000;
	static int strafpunkte_ZugNichtMöglich = Integer.MIN_VALUE;
	
	static int strafpunkte_bewegungsfreiheit_blockieren = 100;
	static int punkte_gegner_blockieren = 100;
	static int punkte_deckung = 500; //punkte die Figur bekommt wenn sie deckt, gedeckt wird
	static int punkte_raum = 500;
	
	static String farbe="schwarz";
	static String farbe_gegner = "weiß";
	

	public static void main(String[] args) {
		
	}
	
	private static int getMin(ArrayList<Integer> A) {
		int min = Integer.MAX_VALUE;
		int min_index = 0;
		for(int i=0; i<A.size(); i++) {
			if(A.get(i) < min) {
				min = A.get(i);
				min_index = i;
			}
		}
		return min_index;
	}
	
	private static ArrayList<Integer> sort(ArrayList<Integer> A) {
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		while(A.size() > 0) {
			sorted.add(A.get(getMin(A)));
			A.remove(getMin(A));
		}
		return sorted;
	}
	
	public static void ziehen() {
		Schachbrett.automatic = true;
		int max = Integer.MIN_VALUE;
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(!Schachbrett.figuren[x1][y1].farbe.equals(SchachKI.farbe))continue;
				for(int x2=0; x2<8; x2++) {
					for(int y2=0; y2<8; y2++) {
						Zug[x1][y1][x2][y2] = SchachKI.bewertung_Zug(x1,y1,x2,y2);
						if(Zug[x1][y1][x2][y2] > max) {
							max = Zug[x1][y1][x2][y2];
							KI_x1 = x1;
							KI_y1 = y1;
							KI_x2 = x2;
							KI_y2 = y2;
						}
					}	
				}	
			}
		}
		syso1 = true; 
		System.out.println("Bewegung: "+KI_x1+" "+KI_y1+" => "+KI_x2+" "+KI_y2+" ("+Zug[KI_x1][KI_y1][KI_x2][KI_y2]+" Punkte)");
		System.out.println("davon Angriffsbonus: "+ schlagbonus(KI_x2,KI_y2)+", Schlagabtausch:"+punkte_deckung*schlagabtausch(KI_x1,KI_y1,KI_x2,KI_y2));
		syso1 = false;
	}
		
	private static int bewertung_Zug(int x1, int y1, int x2, int y2) {
		int bewertung=0;
		if(!Schachbrett.bewegungPrüfen(x1, y1, x2, y2, farbe))return strafpunkte_ZugNichtMöglich;
		bewertung += punkte_deckung*schlagabtausch(x1,y1,x2,y2); //TODO: problemkind
		bewertung += punkte_deckung*deckungstausch(x1, y1, x2, y2);
		System.out.println(x1+" "+y1+" => "+x2+" "+y2+" schlagabtausch:"+punkte_deckung*schlagabtausch(x1,y1,x2,y2)+", deckungstausch: "+deckungstausch(x1, y1, x2, y2));
		//bewertung += punkte_deckung*deckungsabtausch_ohneWertung(x1,y1,x2, y2);
		return bewertung;
	}
	
	private static int schlagbonus(int x2, int y2) {
		switch(Schachbrett.figuren[x2][y2].typ) {
			case "König":{return punkte_schlagbonus*wert_könig;}
			case "Dame":{return punkte_schlagbonus*wert_dame;}
			case "Turm":{return punkte_schlagbonus*wert_turm;}
			case "Läufer": {return punkte_schlagbonus*wert_läufer;}
			case "Pferd":{return punkte_schlagbonus*wert_pferd;}
			case "Bauer":{return punkte_schlagbonus*wert_bauer;}
			default: return 0;
		}
	}
	
	
	/*//TODO
	private static int gefährdung(int x1, int y1) {
		int gefährungen=0;
		for(int x2=0; x2<8; x2++) {
			for(int y2=0; y2<8; y2++) {
					if(Schachbrett.figuren[x2][y2].farbe.equals(farbe_gegner) && Schachbrett.bewegungPrüfen(x1, y1, x2, y2, farbe) && Schachbrett.bewegungPrüfen(x2, y2, x1, y1, farbe_gegner))++gefährungen;
			}
		}
		return gefährungen;
	}
	*/
	
	
	
	/**
	 * Gibt eine aufsteigend sortierte Liste, der Gegnerfiguren zurück, die das Feld (x,y) decken
	 * @param x
	 * @param y
	 * @return
	 */
	private static ArrayList<Integer> gegnerfiguren_feld(int x, int y){
		ArrayList<Integer> Deckung_Gegner = new ArrayList<>();
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
						if((Schachbrett.figuren[a][b].farbe.equals(farbe_gegner) && ((a==x && b==y) ||  Schachbrett.bewegungPrüfen(a, b, x, y, farbe_gegner)))) {	
						switch(Schachbrett.figuren[a][b].typ) {
							case "König":{Deckung_Gegner.add((wert_könig));break;}
							case "Dame":{Deckung_Gegner.add(wert_dame);break;}
							case "Turm":{Deckung_Gegner.add(wert_turm);break;}
							case "Läufer": {Deckung_Gegner.add(wert_läufer);break;}
							case "Pferd":{Deckung_Gegner.add(wert_pferd);break;}
							case "Bauer":{Deckung_Gegner.add(wert_bauer);break;}
							default: break;
						}
					}
			}
		}
		Deckung_Gegner = sort(Deckung_Gegner);
		return Deckung_Gegner;
	}
	
	/**
	 * Wieviel
	 * @param x
	 * @param y
	 * @return
	 */
	private static int deckungen_der_figur(int x, int y){
		int deckungen = 0;	
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
					if(a==x && b==x)continue;
					if(Schachbrett.figuren[a][b].farbe.equals(farbe) && (Schachbrett.bewegungPrüfen(x, y, a, b, farbe))) {
						switch(Schachbrett.figuren[a][b].typ) {
							case "König":{deckungen+=wert_könig;break;}
							case "Dame":{deckungen+=wert_dame;break;}
							case "Turm":{deckungen+=wert_turm;break;}
							case "Läufer": {deckungen+=wert_läufer;break;}
							case "Pferd":{deckungen+=wert_pferd;break;}
							case "Bauer":{deckungen+=wert_bauer;break;}
							default: break;
						}
					}
			}
		}
		return deckungen;
	}

	/**
	 * Gibt eine aufsteigend sortierte Liste, der Figuren zurück, die das Feld (x,y) decken
	 * @param x
	 * @param y
	 * @return
	 */
		private static ArrayList<Integer> deckungsfiguren_feld(int aktuelleFigurX, int aktuelleFigurY, int x, int y){
		ArrayList<Integer> Deckung_Selbst = new ArrayList<>();
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
					if(a==aktuelleFigurX && b==aktuelleFigurY)continue;
					if(Schachbrett.figuren[a][b].farbe.equals(farbe) && (Schachbrett.bewegungPrüfen(a, b, x, y, farbe))) {
						switch(Schachbrett.figuren[a][b].typ) {
							case "König":{Deckung_Selbst.add((wert_könig));break;}
							case "Dame":{Deckung_Selbst.add(wert_dame);break;}
							case "Turm":{Deckung_Selbst.add(wert_turm);break;}
							case "Läufer": {Deckung_Selbst.add(wert_läufer);break;}
							case "Pferd":{Deckung_Selbst.add(wert_pferd);break;}
							case "Bauer":{Deckung_Selbst.add(wert_bauer);break;}
							default: break;
						}
					}
			}
		}
		Deckung_Selbst = sort(Deckung_Selbst);
		return Deckung_Selbst;
	}
	
	/**
	 * Vergleich: wie viel Deckung wird gewonnen / geht verloren
	 * @return
	 */
	private static int deckungstausch(int x1, int y1, int x2, int y2) {
		System.out.println("deckungsana: "+Schachbrett.automatic);
		int deckungsdifferenz=0;
		String typ= Schachbrett.figuren[x1][y1].typ;
		Schachbrett.ghosting_löschen(x1, y1, x2, y2); Schachbrett.ghosting_einfügen(x2, y2, typ, "schwarz");
		
		deckungsdifferenz = deckungen_der_figur(x2, y2) - deckungen_der_figur(x1, y1);
		Schachbrett.ghosting_löschen(x1, y1, x2, y2); Schachbrett.ghosting_einfügen(x1, y1, typ, "schwarz");
		if(syso1)System.out.println(deckungsdifferenz);
		return deckungsdifferenz;
	}
	
	
	/**
	 *Gibt an mit wievielen Punkten FIgur diesen Schlagabtausch verliert
	 */
	private static int schlagabtausch(int x1, int y1, int x2, int y2) {
		//System.out.println(x1+" "+y1+" => "+x2+" "+y2);
		System.out.println("schlagana: "+Schachbrett.automatic);

		int wert=0;
		int eigener_wert = 0;		 //Wert der betrachteten Figur
		int anderer_wert = 0;
		
		String gegnerfigur = Schachbrett.figuren[x2][y2].typ;
		String figur = Schachbrett.figuren[x1][y1].typ;
		switch(Schachbrett.figuren[x1][y1].typ) {
			case "König":{eigener_wert = wert_könig;break;}
			case "Dame":{eigener_wert =wert_dame;break;}
			case "Turm":{eigener_wert =wert_turm;break;}
			case "Läufer": {eigener_wert =wert_läufer;break;}
			case "Pferd":{eigener_wert =wert_pferd;break;}
			case "Bauer":{eigener_wert =wert_bauer;break;}
			default: break;
		}
	
		switch(Schachbrett.figuren[x2][y2].typ) {
			case "König":{anderer_wert = wert_könig;break;}
			case "Dame":{anderer_wert =wert_dame;break;}
			case "Turm":{anderer_wert =wert_turm;break;}
			case "Läufer": {anderer_wert =wert_läufer;break;}
			case "Pferd":{anderer_wert =wert_pferd;break;}
			case "Bauer":{anderer_wert =wert_bauer;break;}
			default: break;
		}
	
		Schachbrett.ghosting = true;
		Schachbrett.ghosting_löschen(x1, y1, x2, y2);

		ArrayList<Integer> Deckung_Selbst = deckungsfiguren_feld(x1, y1, x2, y2); //Punkte die ich gewinne
		ArrayList<Integer> Deckung_Gegner= gegnerfiguren_feld(x2, y2); //Punkte die ich verliere

		//System.out.println(Deckung_Selbst+" "+Deckung_Gegner);
		if(syso1)System.out.println(figur+": "+eigener_wert +"-"+gegnerfigur+": "+anderer_wert);
		if(syso1)System.out.println(Deckung_Selbst +"-"+ Deckung_Gegner);

		int maxzuege = 1;
		//Gegner würde hier das maximum nehmen und nicht unbedingt alle Züge machen
		int max_bedrohung=0;
		int bedrohung = 0;
		for(int zuege=1; zuege<Math.min(Deckung_Gegner.size(), Deckung_Selbst.size()); zuege++) {
			if((bedrohung += Deckung_Gegner.get(zuege)-Deckung_Selbst.get(zuege)) > max_bedrohung) {
				max_bedrohung = bedrohung;
				maxzuege=zuege;
			}
		}
		
		if(Deckung_Gegner.size()>0)wert-=eigener_wert; wert+=anderer_wert;
		for(int i=0; i<maxzuege-1; i++) {
			if(i<Deckung_Selbst.size()-1) {
				wert-=Deckung_Selbst.get(i);
			}
			if(i<Deckung_Gegner.size()-1) {
				wert+=Deckung_Gegner.get(i);
			}		
		}
		
		Schachbrett.ghosting_zurückziehen(x2, y2, x1, y1, gegnerfigur, figur, farbe);
		Schachbrett.ghosting = false;
		return wert;

	}
	

}
