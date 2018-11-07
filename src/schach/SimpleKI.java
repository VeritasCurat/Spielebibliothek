package schach;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.glass.ui.Size;

public class SimpleKI {
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
		int max = Integer.MIN_VALUE;
		for(int x1=0; x1<8; x1++) {
			for(int y1=0; y1<8; y1++) {
				if(!Schachbrett.figuren[x1][y1].farbe.equals(SimpleKI.farbe))continue;
				for(int x2=0; x2<8; x2++) {
					for(int y2=0; y2<8; y2++) {
						Zug[x1][y1][x2][y2] = SimpleKI.bewertung_Zug(x1,y1,x2,y2);
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
		System.out.println("davon Angriffsbonus: "+ schlagbonus(KI_x2,KI_y2)+", Schlagabtausch:"+punkte_deckung*schlagabtausch(KI_x1,KI_y1,KI_x2,KI_y2)+", Deckung anderer: "+punkte_deckung*deckungsabtausch_ohneWertung(KI_x1,KI_y1,KI_x2, KI_y2));
		 syso1 = false;
	}
		
	private static int bewertung_Zug(int x1, int y1, int x2, int y2) {
		int bewertung=0;
		if(!Schachbrett.bewegungPrüfen(x1, y1, x2, y2, farbe))return strafpunkte_ZugNichtMöglich;
		bewertung += punkte_deckung*schlagabtausch(x1,y1,x2,y2); //TODO: problemkind
		//System.out.println(x1+" "+y1+" => "+x2+" "+y2+" : "+punkte_deckung*schlagabtausch(x1,y1,x2,y2));
		bewertung += punkte_deckung*deckungsabtausch_ohneWertung(x1,y1,x2, y2);
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
	
	private static int deckungsabtausch_ohneWertung(int x1, int y1, int x2, int y2) {
		int deckungen=0;
	
		
		for(int a=0; a<8; a++) {
			for(int b=0; b<8; b++) {
				if(Schachbrett.figuren[x1][y1].farbe.equals(farbe) && Schachbrett.bewegungPrüfen(x1, y1, x2, y2, farbe)) {
					switch(Schachbrett.figuren[x2][y2].typ) {
						case "Dame":{deckungen += wert_dame;break;}
						case "Turm":{deckungen +=  wert_turm;break;}
						case "Läufer": {deckungen += wert_läufer;break;}
						case "Pferd":{deckungen += wert_pferd;break;}
						case "Bauer":{deckungen += wert_bauer;break;}
						default: return 0;
					}
				}
			}
		}	
		return deckungen=0; //TODO
	}
	
	private static int gefährdung(int x1, int y1) {
		int gefährungen=0;
		for(int x2=0; x2<8; x2++) {
			for(int y2=0; y2<8; y2++) {
					if(Schachbrett.figuren[x2][y2].farbe.equals(farbe_gegner) && Schachbrett.bewegungPrüfen(x1, y1, x2, y2, farbe) && Schachbrett.bewegungPrüfen(x2, y2, x1, y1, farbe_gegner))++gefährungen;
			}
		}
		return gefährungen;
	}
	
	
	/**
	 * gibt anzahl der figuren an die nach abgleich bleiben (ohne wertung)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static int schlagabtausch_ohnewertung(int x1, int y1, int x2, int y2) {
		int abgleich = 0;
		
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
						boolean bauernbug2 = Schachbrett.figuren[a][b].typ.equals("Bauer") && Math.abs(a-x2) == 1 &&  b-y2 == -1;//TODO: nur lösung für farbe = "schwarz" 
						if((Schachbrett.figuren[a][b].farbe.equals(farbe_gegner) && (bauernbug2 ||  Schachbrett.bewegungPrüfen(a, b, x2, y2, farbe_gegner)))) {
							//Bauernbugs: Gegnerbauer bewegt NICHTDIAGONAL sich auf "leeres Feld" x2,y2 obwohl dort jemand steht => illegaler Zug; der symmetrische Zug ist legal statt illegal
							if(Schachbrett.figuren[a][b].typ.equals("Bauer") && a==x2)continue;
							--abgleich;
						}
			}
		}
		
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {		
					boolean bauernbug2 = Schachbrett.figuren[a][b].typ.equals("Bauer") && Math.abs(a-x2) == 1 &&  b-y2 == 1;//TODO: nur lösung für farbe = "schwarz" 
					if(Schachbrett.figuren[a][b].farbe.equals(farbe) && (bauernbug2 || Schachbrett.bewegungPrüfen(a, b, x2, y2, farbe))) {
						++abgleich;
					}
			}
		}
		
		return abgleich;
	}

	
	/**
	 *Gibt an mit wievielen Punkten FIgur diesen Schlagabtausch verliert
	 */
	private static int schlagabtausch(int x1, int y1, int x2, int y2) {
		int wert=0;
		int eigener_wert = 0;		 //Wert der betrachteten Figur
		int anderer_wert = 0;
		
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
		
		ArrayList<Integer> Deckung_Gegner = new ArrayList<Integer>(); //Punkte die ich gewinne
		ArrayList<Integer> Deckung_Selbst= new ArrayList<Integer>(); //Punkte die ich verliere
	
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
							boolean bauernbug2 = Schachbrett.figuren[a][b].typ.equals("Bauer") && Math.abs(a-x2) == 1 &&  b-y2 == -1;//TODO: nur lösung für farbe = "schwarz" 
						if((Schachbrett.figuren[a][b].farbe.equals(farbe_gegner) && ((a==x2 && b==y2) || bauernbug2 ||  Schachbrett.bewegungPrüfen(a, b, x2, y2, farbe_gegner)))) {
							//Bauernbugs: Gegnerbauer bewegt NICHTDIAGONAL sich auf "leeres Feld" x2,y2 obwohl dort jemand steht => illegaler Zug; der symmetrische Zug ist legal statt illegal
							if(Schachbrett.figuren[a][b].typ.equals("Bauer") && a==x2)continue;
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
		
		for(int a=0;a<8;a++) {
			for(int b=0; b<8; b++) {
					boolean bauernbug2 = Schachbrett.figuren[a][b].typ.equals("Bauer") && Math.abs(a-x2) == 1 &&  b-y2 == 1;//TODO: nur lösung für farbe = "schwarz" 
					if(a==x1 && b==y1)continue;
					if(Schachbrett.figuren[a][b].farbe.equals(farbe) && (bauernbug2 || Schachbrett.bewegungPrüfen(a, b, x2, y2, farbe))) {
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
		

		Deckung_Gegner = sort(Deckung_Gegner);
		Deckung_Selbst = sort(Deckung_Selbst);
		
		if(syso1)System.out.println(Deckung_Gegner +"-"+ Deckung_Selbst);
		
		
		//TODO: Gegner würde hier das maximum nehmen und nicht unbedingt alle Züge machen
		//		int min_bedrohung=Gewinn.get(0)-Verlust.get(0);
		//		for(int i=1; i<Math.min(Gewinn.size(), Verlust.size()); i++) {
		//			if((bedrohungen += Gewinn.get(i)-Verlust.get(i)) > max_bedrohung)max_bedrohung = bedrohungen;
		//		}
		
		int zug=0;
		int g=0;
		int v=0;
		while(true) {
			System.out.println("wert: "+wert);
			if(zug == 0) {
				wert+=anderer_wert; 
				++zug;
				continue;
			}
			else if(zug == 1 && Deckung_Gegner.size()>0) {
				wert-=eigener_wert;
				++zug;
				continue;
			}
//			else if(zug > 1 && zug%2==0 && Gewinn.size()==0) break;
//			else if(zug > 1 && zug%2==1 && Verlust.size()==0) break;
			
			else if(zug > 1 && zug%2==0) { //ich bin dran mit schlagen
				if(Deckung_Selbst.size() > 0) { //hab ich noch etwas zum schlagen? => Ja, schlage Gegner
					wert+=Deckung_Gegner.get(g);
					Deckung_Gegner.remove(g); g++;
					++zug;
					continue;
				}
				else break; //=> Nein, Ende!
			}
			else if(zug > 1 && zug%2==1) { //Gegner dran mit schlagen
				if(Deckung_Gegner.size() > 0) { //hat er noch etwas zum schlagen? => Ja, schlage mich
					wert+=Deckung_Gegner.get(v);
					Deckung_Gegner.remove(v); v++;
					++zug;
					continue;
				}
				else break; //=> Nein, Ende!
			}
			else break;
		}
		
		
		return wert;
	}
	

}
